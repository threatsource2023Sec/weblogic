package weblogic.socket;

import java.io.IOException;
import java.net.Socket;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;

public class SNIFilter extends JSSEFilterImpl {
   private static final Charset ASCII_CHARSET = Charset.forName("US-ASCII");
   private static final int SSLV3_MAX_RECORD_SIZE = 16384;
   private static final byte HANDSHAKE_TYPE = 22;
   private static final int MIN_TLS_VERSION = 769;
   private static final int SSLV3_RECORD_HEADER_SIZE = 5;
   private static final int CLIENT_HELLO_HST = 1;
   private static final int DEFAULT_NET_BUFFER_SIZE = 20000;
   private static final int DEFAULT_APP_BUFFER_SIZE = 20000;
   private static final int BUFFER_SIZE_EXTRA_BYTES = 50;
   private volatile int maxKnownNetBufferSize = -1;
   private volatile int maxKnownAppBufferSize = -1;
   private boolean isSniCheckCompleted;
   private SNISocket sniSocket;
   private final SNISecureConfigFactory defaultFactory;
   private final SNISecureConfigFactory factory;

   public SNIFilter(Socket socket, SNISecureConfigFactory factory, SNISecureConfigFactory defaultFactory) throws IOException {
      super(socket, false);
      if (factory == null) {
         throw new IllegalArgumentException("The factory parameter can't be null");
      } else if (defaultFactory == null) {
         throw new IllegalArgumentException("The default factory parameter can't be null");
      } else {
         this.factory = factory;
         this.defaultFactory = defaultFactory;
         int netBufferSize = this.maxKnownNetBufferSize != -1 ? this.maxKnownNetBufferSize : 20000;
         int appBufferSize = this.maxKnownAppBufferSize != -1 ? this.maxKnownAppBufferSize : 20000;
         this.readNWDataInBuf = allocate(netBufferSize);
         this.writeNWDataOutBuf = allocate(netBufferSize);
         this.clearTextBuf = allocate(appBufferSize);
         if (isLogDebug()) {
            SocketLogger.logDebug("SNIFilter initialized buffers. netBufferSize=" + netBufferSize + " appBufferSize=" + appBufferSize);
         }

      }
   }

   public void setSNISocket(SNISocket sniSocket) {
      this.sniSocket = sniSocket;
   }

   public boolean isMessageComplete() {
      boolean isLogDebug = isLogDebug();
      if (!this.isSniCheckCompleted) {
         int bytesAvailable = this.readNWDataInBuf.position();
         if (bytesAvailable < 5) {
            return false;
         }

         int pos = 0;
         byte byte0 = this.readNWDataInBuf.get(pos++);
         byte byte1 = this.readNWDataInBuf.get(pos++);
         byte byte2 = this.readNWDataInBuf.get(pos++);
         String hostName = null;
         if (checkTlsVersion(byte0, byte1, byte2)) {
            byte byte3 = this.readNWDataInBuf.get(pos++);
            byte byte4 = this.readNWDataInBuf.get(pos);
            int len = ((byte3 & 255) << 8) + (byte4 & 255) + 5;
            if (len <= 16384) {
               if (bytesAvailable < len) {
                  return false;
               }

               hostName = this.getHostName(this.readNWDataInBuf, len);
               if (isLogDebug) {
                  SocketLogger.logDebug("SNIFilter hostname=" + hostName);
               }
            } else if (isLogDebug) {
               SocketLogger.logDebug("SNIFilter SSLV3 record size is greater than max(16384): " + len);
            }
         } else if (isLogDebug) {
            SocketLogger.logDebug("SNIFilter unknown TLS version");
         }

         SSLEngine sslEngine = null;

         try {
            if (hostName != null) {
               sslEngine = this.factory.createSSLEngine(this.getSocket(), hostName);
               if (isLogDebug) {
                  SocketLogger.logDebug("SNIFilter sslEngine=" + sslEngine);
               }
            }

            if (sslEngine == null) {
               sslEngine = this.defaultFactory.createSSLEngine(this.getSocket(), hostName);
               if (isLogDebug) {
                  SocketLogger.logDebug("SNIFilter default sslEngine=" + sslEngine);
               }
            }
         } catch (SSLException var11) {
            throw new IllegalStateException(var11);
         }

         assert sslEngine != null;

         this.adjustBuffersIfNeeded(sslEngine);
         this.sslEngine = sslEngine;
         this.sniSocket.initialize();
      }

      this.isSniCheckCompleted = true;
      return super.isMessageComplete();
   }

   private String getHostName(ByteBuffer input, int len) {
      int oldPos = input.position();
      int oldLim = input.limit();

      Object var6;
      try {
         input.limit(len);
         input.position(5);
         int handshakeType = input.get();
         if (handshakeType == 1) {
            skipBytes(input, 37);
            int sessionIDLength = input.get() & 255;
            skipBytes(input, sessionIDLength);
            int cipherSuiteLength = input.getShort() & '\uffff';
            skipBytes(input, cipherSuiteLength);
            int compressionMethodLength = input.get() & 255;
            skipBytes(input, compressionMethodLength);
            if (!input.hasRemaining()) {
               Object var20 = null;
               return (String)var20;
            }

            skipBytes(input, 2);

            while(input.hasRemaining()) {
               int extensionType = input.getShort() & '\uffff';
               int extensionDataLength = input.getShort() & '\uffff';
               if (extensionType == 0) {
                  int namesCount = input.getShort() & '\uffff';

                  for(int i = 0; i < namesCount; ++i) {
                     int nameType = input.get() & 255;
                     int nameLen = input.getShort() & '\uffff';
                     if (nameType == 0) {
                        String var15 = this.getAsciiString(input, nameLen);
                        return var15;
                     }

                     skipBytes(input, nameLen);
                  }
               } else {
                  skipBytes(input, extensionDataLength);
               }
            }

            return null;
         }

         var6 = null;
      } finally {
         input.limit(oldLim);
         input.position(oldPos);
      }

      return (String)var6;
   }

   static boolean checkTlsVersion(byte byte0, byte major, byte minor) {
      return byte0 == 22 && (major << 8 | minor & 255) >= 769;
   }

   private String getAsciiString(ByteBuffer input, int len) {
      int position = input.position();
      if (position + len > input.limit()) {
         throw new BufferOverflowException();
      } else {
         byte[] array;
         int offs;
         if (input.hasArray()) {
            array = input.array();
            offs = input.arrayOffset() + position;
            input.position(position + len);
         } else {
            array = new byte[len];
            int oldLim = input.limit();
            input.limit(position + len);
            input.position(position);
            input.get(array);
            input.limit(oldLim);
            offs = 0;
         }

         return new String(array, offs, len, ASCII_CHARSET);
      }
   }

   private void adjustBuffersIfNeeded(SSLEngine sslEngine) {
      int netBufferSize = sslEngine.getSession().getPacketBufferSize();
      int appBufferSize = sslEngine.getSession().getApplicationBufferSize();
      boolean isLogDebug = isLogDebug();
      if (this.readNWDataInBuf.capacity() < netBufferSize) {
         if (isLogDebug) {
            SocketLogger.logDebug("SNIFilter adjust readNWDataInBuf. newsize=" + netBufferSize);
         }

         this.readNWDataInBuf = this.reallocate(this.readNWDataInBuf, netBufferSize);
      }

      if (this.writeNWDataOutBuf.capacity() < netBufferSize) {
         if (isLogDebug) {
            SocketLogger.logDebug("SNIFilter adjust writeNWDataOutBuf. newsize=" + netBufferSize);
         }

         this.writeNWDataOutBuf = this.reallocate(this.writeNWDataOutBuf, netBufferSize);
      }

      if (this.clearTextBuf.capacity() < appBufferSize) {
         if (isLogDebug) {
            SocketLogger.logDebug("SNIFilter adjust clearTextBuf. newsize=" + appBufferSize);
         }

         this.clearTextBuf = this.reallocate(this.clearTextBuf, appBufferSize);
      }

      if (netBufferSize + 50 > this.maxKnownNetBufferSize) {
         this.maxKnownNetBufferSize = netBufferSize + 50;
      }

      if (appBufferSize + 50 > this.maxKnownAppBufferSize) {
         this.maxKnownAppBufferSize = appBufferSize + 50;
      }

   }

   private ByteBuffer reallocate(ByteBuffer origBuffer, int newSize) {
      assert origBuffer.capacity() < newSize;

      ByteBuffer newBuffer = allocate(newSize);
      int origLim = origBuffer.limit();
      origBuffer.flip();
      newBuffer.put(origBuffer);
      origBuffer.limit(origLim);
      return newBuffer;
   }

   private static ByteBuffer allocate(int size) {
      return ByteBuffer.allocate(size);
   }

   private static ByteBuffer skipBytes(ByteBuffer byteBuffer, int bytes) {
      byteBuffer.position(byteBuffer.position() + bytes);
      return byteBuffer;
   }
}
