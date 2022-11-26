package org.glassfish.grizzly.ssl;

import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.ByteBufferWrapper;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;

public final class SSLUtils {
   private static final boolean ANDROID_WORKAROUND_NEEDED;
   private static final int LOLLIPOP_VER = 21;
   private static final String SSL_CONNECTION_CTX_ATTR_NAME;
   static final Attribute SSL_CTX_ATTR;
   private static final SSLConnectionContext.Allocator HS_UNWRAP_ALLOCATOR;
   private static final SSLConnectionContext.Allocator HS_WRAP_ALLOCATOR;
   private static final byte CHANGE_CIPHER_SPECT_CONTENT_TYPE = 20;
   private static final byte APPLICATION_DATA_CONTENT_TYPE = 23;
   private static final int SSLV3_RECORD_HEADER_SIZE = 5;
   private static final int SSL20_HELLO_VERSION = 2;
   private static final int MIN_VERSION = 768;
   private static final int MAX_MAJOR_VERSION = 3;
   private static final ThreadCache.CachedTypeIndex SSL_OUTPUT_BUFFER_IDX;

   public static SSLConnectionContext getSslConnectionContext(Connection connection) {
      return (SSLConnectionContext)SSL_CTX_ATTR.get((AttributeStorage)connection);
   }

   public static SSLEngine getSSLEngine(Connection connection) {
      SSLConnectionContext sslCtx = getSslConnectionContext(connection);
      return sslCtx == null ? null : sslCtx.getSslEngine();
   }

   public static void setSSLEngine(Connection connection, SSLEngine sslEngine) {
      SSLConnectionContext ctx = getSslConnectionContext(connection);
      if (ctx == null) {
         ctx = new SSLConnectionContext(connection);
         SSL_CTX_ATTR.set((AttributeStorage)connection, ctx);
      }

      ctx.configure(sslEngine);
   }

   public static int getSSLPacketSize(Buffer buf) throws SSLException {
      if (buf.remaining() < 5) {
         return -1;
      } else {
         byte byte0;
         byte byte1;
         byte byte2;
         byte byte3;
         byte byte4;
         int len;
         if (buf.hasArray()) {
            byte[] array = buf.array();
            int pos = buf.arrayOffset() + buf.position();
            byte0 = array[pos++];
            byte1 = array[pos++];
            byte2 = array[pos++];
            byte3 = array[pos++];
            byte4 = array[pos];
         } else {
            len = buf.position();
            byte0 = buf.get(len++);
            byte1 = buf.get(len++);
            byte2 = buf.get(len++);
            byte3 = buf.get(len++);
            byte4 = buf.get(len);
         }

         if (byte0 >= 20 && byte0 <= 23) {
            int v = byte1 << 8 | byte2 & 255;
            if (v < 768 || byte1 > 3) {
               throw new SSLException("Unsupported record version major=" + byte1 + " minor=" + byte2);
            }

            len = ((byte3 & 255) << 8) + (byte4 & 255) + 5;
         } else {
            boolean isShort = (byte0 & 128) != 0;
            if (!isShort || byte2 != 1 && byte2 != 4) {
               throw new SSLException("Unrecognized SSL message, plaintext connection?");
            }

            int v = byte3 << 8 | byte4 & 255;
            if ((v < 768 || byte3 > 3) && v != 2) {
               throw new SSLException("Unsupported record version major=" + byte3 + " minor=" + byte4);
            }

            int mask = 127;
            len = ((byte0 & mask) << 8) + (byte1 & 255) + 2;
         }

         return len;
      }
   }

   public static void executeDelegatedTask(SSLEngine sslEngine) {
      Runnable runnable;
      while((runnable = sslEngine.getDelegatedTask()) != null) {
         runnable.run();
      }

   }

   public static boolean isHandshaking(SSLEngine sslEngine) {
      SSLEngineResult.HandshakeStatus handshakeStatus = sslEngine.getHandshakeStatus();
      return handshakeStatus != HandshakeStatus.FINISHED && handshakeStatus != HandshakeStatus.NOT_HANDSHAKING;
   }

   public static SSLEngineResult handshakeUnwrap(int length, SSLConnectionContext sslCtx, Buffer inputBuffer, Buffer tmpOutputBuffer) throws SSLException {
      SSLConnectionContext.SslResult result = sslCtx.unwrap(length, inputBuffer, tmpOutputBuffer, HS_UNWRAP_ALLOCATOR);
      Buffer output = result.getOutput();

      assert !output.isComposite();

      if (output != tmpOutputBuffer) {
         output.dispose();
      }

      if (result.isError()) {
         throw result.getError();
      } else {
         return result.getSslEngineResult();
      }
   }

   public static Buffer handshakeWrap(Connection connection, SSLConnectionContext sslCtx, Buffer netBuffer) throws SSLException {
      int packetBufferSize = sslCtx.getNetBufferSize();
      Buffer buffer;
      if (netBuffer != null && !netBuffer.isComposite() && netBuffer.capacity() - netBuffer.limit() >= packetBufferSize) {
         netBuffer.position(netBuffer.limit());
         netBuffer.limit(netBuffer.capacity());
         buffer = netBuffer;
      } else {
         buffer = allocateOutputBuffer(packetBufferSize * 2);
      }

      SSLConnectionContext.SslResult result = sslCtx.wrap(Buffers.EMPTY_BUFFER, buffer, HS_WRAP_ALLOCATOR);
      Buffer output = result.getOutput();
      output.flip();
      if (buffer != output && netBuffer != null && buffer == netBuffer) {
         netBuffer.flip();
      }

      if (result.isError()) {
         if (output != netBuffer) {
            output.dispose();
         }

         throw result.getError();
      } else {
         if (output != netBuffer) {
            output = allowDispose(Buffers.appendBuffers(connection.getMemoryManager(), netBuffer, output));
         }

         return output;
      }
   }

   static Buffer allocateOutputBuffer(int size) {
      Buffer buffer = (Buffer)ThreadCache.takeFromCache(SSL_OUTPUT_BUFFER_IDX);
      boolean hasBuffer = buffer != null;
      if (!hasBuffer || ((Buffer)buffer).remaining() < size) {
         ByteBuffer byteBuffer = ByteBuffer.allocate(size);
         buffer = new ByteBufferWrapper(byteBuffer) {
            public void dispose() {
               this.clear();
               ThreadCache.putToCache(SSLUtils.SSL_OUTPUT_BUFFER_IDX, this);
            }
         };
      }

      return (Buffer)buffer;
   }

   public static Buffer allocateInputBuffer(SSLConnectionContext sslCtx) {
      SSLEngine sslEngine = sslCtx.getSslEngine();
      return sslEngine == null ? null : allocateOutputBuffer(sslCtx.getNetBufferSize() * 2);
   }

   static Buffer makeInputRemainder(SSLConnectionContext sslCtx, FilterChainContext context, Buffer buffer) {
      if (buffer == null) {
         return null;
      } else if (!buffer.hasRemaining()) {
         buffer.tryDispose();
         return null;
      } else {
         Buffer inputBuffer = sslCtx.resetLastInputBuffer();
         if (inputBuffer == null) {
            Buffer remainder = buffer.split(buffer.position());
            buffer.tryDispose();
            return remainder;
         } else {
            return move(context.getMemoryManager(), buffer);
         }
      }
   }

   static Buffer copy(MemoryManager memoryManager, Buffer buffer) {
      Buffer tmpBuf = memoryManager.allocate(buffer.remaining());
      tmpBuf.put(buffer);
      return tmpBuf.flip();
   }

   static Buffer move(MemoryManager memoryManager, Buffer buffer) {
      Buffer tmpBuf = copy(memoryManager, buffer);
      buffer.tryDispose();
      return tmpBuf;
   }

   public static Buffer allowDispose(Buffer buffer) {
      if (buffer == null) {
         return null;
      } else {
         buffer.allowBufferDispose(true);
         if (buffer.isComposite()) {
            ((CompositeBuffer)buffer).allowInternalBuffersDispose(true);
         }

         return buffer;
      }
   }

   static SSLEngineResult sslEngineWrap(SSLEngine engine, ByteBuffer in, ByteBuffer out) throws SSLException {
      return engine.wrap(in, out);
   }

   static SSLEngineResult sslEngineWrap(SSLEngine engine, ByteBuffer[] in, int inOffs, int inLen, ByteBuffer out) throws SSLException {
      return ANDROID_WORKAROUND_NEEDED ? SSLUtils.AndroidWorkAround.wrapArray(engine, in, inOffs, inLen, out) : engine.wrap(in, inOffs, inLen, out);
   }

   static SSLEngineResult sslEngineUnwrap(SSLEngine engine, ByteBuffer in, ByteBuffer out) throws SSLException {
      return engine.unwrap(in, out);
   }

   static SSLEngineResult sslEngineUnwrap(SSLEngine engine, ByteBuffer in, ByteBuffer[] out, int outOffs, int outLen) throws SSLException {
      return ANDROID_WORKAROUND_NEEDED ? SSLUtils.AndroidWorkAround.unwrapArray(engine, in, out, outOffs, outLen) : engine.unwrap(in, out, outOffs, outLen);
   }

   static {
      boolean isNeedWorkAround = false;
      if ("android runtime".equalsIgnoreCase(System.getProperty("java.runtime.name"))) {
         try {
            int version = Class.forName("android.os.Build$VERSION").getField("SDK_INT").getInt((Object)null);
            isNeedWorkAround = version >= 21;
         } catch (Throwable var2) {
         }
      }

      ANDROID_WORKAROUND_NEEDED = isNeedWorkAround;
      SSL_CONNECTION_CTX_ATTR_NAME = SSLUtils.class + ".ssl-connection-context";
      SSL_CTX_ATTR = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute(SSL_CONNECTION_CTX_ATTR_NAME);
      HS_UNWRAP_ALLOCATOR = new SSLConnectionContext.Allocator() {
         public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
            return SSLUtils.allocateOutputBuffer(newSize);
         }
      };
      HS_WRAP_ALLOCATOR = new SSLConnectionContext.Allocator() {
         public Buffer grow(SSLConnectionContext sslCtx, Buffer oldBuffer, int newSize) {
            return SSLUtils.allocateOutputBuffer(newSize);
         }
      };
      SSL_OUTPUT_BUFFER_IDX = ThreadCache.obtainIndex(SSLBaseFilter.class.getName() + ".output-buffer-cache", Buffer.class, 4);
   }

   private static class AndroidWorkAround {
      public static SSLEngineResult wrapArray(SSLEngine engine, ByteBuffer[] in, int inOffs, int inLen, ByteBuffer out) throws SSLException {
         if (inOffs == 0 && inLen == in.length) {
            return engine.wrap(in, out);
         } else {
            ByteBuffer[] tmp = new ByteBuffer[inLen];
            System.arraycopy(in, inOffs, tmp, 0, inLen);
            return engine.wrap(tmp, out);
         }
      }

      public static SSLEngineResult unwrapArray(SSLEngine engine, ByteBuffer in, ByteBuffer[] out, int outOffs, int outLen) throws SSLException {
         if (outOffs == 0 && outLen == out.length) {
            return engine.unwrap(in, out);
         } else {
            ByteBuffer[] tmp = new ByteBuffer[outLen];
            System.arraycopy(out, outOffs, tmp, 0, outLen);
            return engine.unwrap(in, tmp);
         }
      }
   }
}
