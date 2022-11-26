package weblogic.socket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.net.SocketFactory;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;
import weblogic.utils.PropertyHelper;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.concurrent.Latch;
import weblogic.utils.io.Chunk;

public abstract class BaseAbstractMuxableSocket implements MuxableSocket, SocketRuntime, ContextHandler, Serializable {
   private static final long serialVersionUID = 7960171920400419300L;
   private static AtomicBoolean exceededDumpDone = new AtomicBoolean(false);
   private static final String DUMP_EXCEEDED_MESSAGE_FILE = PropertyHelper.getProperty("weblogic.socket.DumpExceedMessageFileName", "ExceededMessageBuffer.out");
   private static final boolean DUMP_EXCEEDED_MESSAGE = PropertyHelper.getBoolean("weblogic.socket.DumpExceedMessageToFile", false);
   private static final int DEFAULT_CONNECTION_TIMEOUT = 0;
   private static final boolean DEBUG = false;
   protected final ServerChannel channel;
   protected Socket socket;
   protected int soTimeout;
   protected InputStream sis;
   protected OutputStream sos;
   protected SocketInfo info;
   protected MuxableSocket filter;
   protected SocketFactory socketFactory;
   protected final Latch closeLatch;
   protected final int maxMessageSize;
   protected Chunk head;
   protected Chunk tail;
   protected int availBytes;
   protected int msgLength;
   private final long connectTime;
   private long messagesReceived;
   private long bytesReceived;
   private transient boolean sawEndOfStream;
   private transient boolean timedOut;
   private transient Throwable hadException;
   private transient Throwable whoClosedMe;
   private static boolean gatherWhoClosedMe = false;
   private transient boolean isCleanup;
   private static final String[] KEYS = new String[]{"com.bea.contextelement.channel.Port", "com.bea.contextelement.channel.PublicPort", "com.bea.contextelement.channel.RemotePort", "com.bea.contextelement.channel.Protocol", "com.bea.contextelement.channel.Address", "com.bea.contextelement.channel.PublicAddress", "com.bea.contextelement.channel.RemoteAddress", "com.bea.contextelement.channel.ChannelName", "com.bea.contextelement.channel.Secure"};

   protected BaseAbstractMuxableSocket(Chunk headChunk, ServerChannel networkChannel) {
      this.filter = null;
      this.closeLatch = new Latch();
      this.availBytes = 0;
      this.msgLength = -1;
      this.connectTime = System.currentTimeMillis();
      this.messagesReceived = 0L;
      this.bytesReceived = 0L;
      this.sawEndOfStream = false;
      this.timedOut = false;
      this.hadException = null;
      this.whoClosedMe = null;
      this.channel = networkChannel;
      Debug.assertion(this.channel != null);
      this.socketFactory = new ChannelSocketFactory(this.channel);
      this.maxMessageSize = networkChannel.getMaxMessageSize();
      this.setSocketFilter(this);
      this.tail = this.head = headChunk;
   }

   protected BaseAbstractMuxableSocket(ServerChannel networkChannel) {
      this(Chunk.getChunk(), networkChannel);
   }

   public final void connect(Socket s) throws IOException {
      this.socket = s;
      this.sis = s.getInputStream();
      this.sos = s.getOutputStream();
   }

   public void connect(InetAddress address, int port) throws IOException {
      this.connect(this.createSocket(address, port));
   }

   public void connect(InetAddress address, int port, int connectionTimeoutMillis) throws IOException {
      this.connect(this.createSocket(address, port, connectionTimeoutMillis));
   }

   protected Socket createSocket(InetAddress host, int port) throws IOException {
      return this.createSocket(host, port, 0);
   }

   protected Socket createSocket(InetAddress host, int port, int connectionTimeout) throws IOException {
      try {
         WeblogicSocketFactory wlsSF = (WeblogicSocketFactory)this.socketFactory;
         return wlsSF.createSocket(host, port, connectionTimeout);
      } catch (ClassCastException var5) {
         return this.socketFactory.createSocket(host, port);
      }
   }

   protected static void p(String msg) {
      System.out.println("<BaseAbstractMuxableSocket>: " + msg);
   }

   protected void setSocketFactory(SocketFactory factory) {
      this.socketFactory = factory;
   }

   public final Chunk getChunk() {
      return this.head;
   }

   public byte[] getBuffer() {
      this.findAndSetTail();
      this.tail = Chunk.ensureCapacity(this.tail);
      return this.tail.buf;
   }

   public int getBufferOffset() {
      this.findAndSetTail();
      return this.tail.end;
   }

   public void incrementBufferOffset(int bytesRead) throws MaxMessageSizeExceededException {
      this.incrementBufferOffset((Chunk)null, bytesRead);
   }

   public void incrementBufferOffset(Chunk c, int bytesRead) throws MaxMessageSizeExceededException {
      this.findAndSetTail();
      this.availBytes += bytesRead;
      if (this.availBytes <= this.maxMessageSize) {
         if (c != null) {
            this.tail.end = Chunk.CHUNK_SIZE;
            this.tail.next = c;
         } else {
            Chunk var10000 = this.tail;
            var10000.end += bytesRead;
         }

      } else {
         if (DUMP_EXCEEDED_MESSAGE && !exceededDumpDone.getAndSet(true)) {
            try {
               FileOutputStream fw = new FileOutputStream(DUMP_EXCEEDED_MESSAGE_FILE);
               Throwable var4 = null;

               try {
                  Chunk current = this.head;

                  while(current != null) {
                     byte[] buf = current.buf;
                     current = current.next;
                     if (buf != null) {
                        fw.write(buf);
                        fw.flush();
                     }
                  }
               } catch (Throwable var15) {
                  var4 = var15;
                  throw var15;
               } finally {
                  if (fw != null) {
                     if (var4 != null) {
                        try {
                           fw.close();
                        } catch (Throwable var14) {
                           var4.addSuppressed(var14);
                        }
                     } else {
                        fw.close();
                     }
                  }

               }
            } catch (IOException var17) {
               var17.printStackTrace();
            }
         }

         throw new MaxMessageSizeExceededException(this.availBytes, this.maxMessageSize, this.channel.getConfiguredProtocol());
      }
   }

   public ByteBuffer getByteBuffer() {
      this.findAndSetTail();
      this.tail = Chunk.ensureCapacity(this.tail);
      return this.tail.getReadByteBuffer();
   }

   private void findAndSetTail() {
      if (this.tail == null) {
         this.tail = this.head;
      }

      this.tail = Chunk.tail(this.tail);
   }

   protected Chunk makeChunkList() {
      Chunk oldhead = this.head;
      if (this.availBytes == this.msgLength) {
         this.head = this.tail = Chunk.getChunk();
      } else {
         this.head = Chunk.split(this.head, this.msgLength);
         this.tail = null;
      }

      ++this.messagesReceived;
      this.bytesReceived += (long)this.msgLength;
      this.availBytes -= this.msgLength;
      this.msgLength = -1;
      return oldhead;
   }

   public long getMessagesReceivedCount() {
      return this.messagesReceived;
   }

   public long getBytesReceivedCount() {
      return this.bytesReceived;
   }

   public final long getConnectTime() {
      return this.connectTime;
   }

   public final int getFileDescriptor() {
      if (this.isClosed()) {
         return -1;
      } else {
         SocketInfo sock = this.getSocketInfo();
         return sock == null ? -1 : sock.getFD();
      }
   }

   public final String getLocalAddress() {
      return !this.isClosed() && this.socket != null ? this.socket.getLocalAddress().toString() : "<closed>";
   }

   public final int getLocalPort() {
      return !this.isClosed() && this.socket != null ? this.socket.getLocalPort() : -1;
   }

   public final String getRemoteAddress() {
      return !this.isClosed() && this.socket != null ? this.socket.getInetAddress().toString() : "<closed>";
   }

   public final int getRemotePort() {
      return !this.isClosed() && this.socket != null ? this.socket.getPort() : -1;
   }

   public final Protocol getProtocol() {
      return this.channel.getProtocol();
   }

   private Object writeReplace() {
      return ((SocketRuntimeFactory)GlobalServiceLocator.getServiceLocator().getService(SocketRuntimeFactory.class, new Annotation[0])).createSocketRuntimeImpl(this);
   }

   public boolean isMessageComplete() {
      if (this.msgLength > -1) {
         return this.availBytes >= this.msgLength;
      } else if (this.availBytes < this.getHeaderLength()) {
         return false;
      } else {
         this.msgLength = this.getMessageLength();
         return this.availBytes >= this.msgLength && this.msgLength > -1;
      }
   }

   protected final byte getHeaderByte(int index) {
      if (this.isCleanup) {
         throw new SocketAlreadyClosedException("Socket closed. Please check if it is due to network issues, or Denial of Service Manager's quota limit exceeded");
      } else if (this.head.end > index) {
         return this.head.buf[index];
      } else if (this.head.next != null && this.head.next.end > index - this.head.end) {
         return this.head.next.buf[index - this.head.end];
      } else {
         throw new ArrayIndexOutOfBoundsException("index out of buffer bounds :" + index);
      }
   }

   public void dispatch() {
      while(this.isMessageComplete()) {
         this.dispatch(this.makeChunkList());
      }

      SocketMuxer.getMuxer().read(this.getSocketFilter());
   }

   protected int getMessageLength() {
      throw new UnsupportedOperationException("getMessageLength()");
   }

   protected int getHeaderLength() {
      throw new UnsupportedOperationException("getHeaderLength()");
   }

   protected final int getAvailableBytes() {
      return this.availBytes;
   }

   protected void dispatch(Chunk chunk) {
      throw new UnsupportedOperationException("dispatch()");
   }

   public final ServerChannel getChannel() {
      return this.channel;
   }

   public final Socket getSocket() {
      return this.socket;
   }

   public boolean closeSocketOnError() {
      return true;
   }

   public final InputStream getSocketInputStream() {
      return this.sis;
   }

   public final OutputStream getSocketOutputStream() {
      return this.sos;
   }

   public final void setSoTimeout(int to) throws SocketException {
      if (to != this.soTimeout) {
         this.soTimeout = to;
         this.socket.setSoTimeout(to);
      }
   }

   protected final int getSoTimeout() {
      return this.soTimeout;
   }

   protected final boolean isClosed() {
      return this.closeLatch.isLocked();
   }

   public final void close() {
      if (this.closeLatch.tryLock()) {
         if (gatherWhoClosedMe) {
            this.whoClosedMe = new Exception("Debug for socket closures");
         }

         this.cleanup();
      }

   }

   protected String getCloseDebugReasonString() {
      if (this.timedOut) {
         return "reason: timed out";
      } else if (this.sawEndOfStream) {
         return "reason: end of stream seen";
      } else if (this.hadException != null) {
         return "reason exception: " + StackTraceUtils.throwable2StackTrace(this.hadException);
      } else if (this.whoClosedMe != null) {
         gatherWhoClosedMe = false;
         return "reason undetermined, socket closure details: " + StackTraceUtils.throwable2StackTrace(this.whoClosedMe);
      } else {
         gatherWhoClosedMe = true;
         return "reason undetermined, enabling closure tracking to gain more insight";
      }
   }

   protected void cleanup() {
      this.sis = null;
      this.sos = null;
      Chunk.releaseChunks(this.head);
      this.head = null;
      this.isCleanup = true;
   }

   public void hasException(Throwable t) {
      this.close();
      this.hadException = t;
   }

   public void endOfStream() {
      this.close();
      this.sawEndOfStream = true;
   }

   public boolean timeout() {
      this.close();
      this.timedOut = true;
      return true;
   }

   public boolean requestTimeout() {
      return true;
   }

   public int getIdleTimeoutMillis() {
      return this.channel.getIdleConnectionTimeout() * 1000;
   }

   public int getCompleteMessageTimeoutMillis() {
      return this.channel.getCompleteMessageTimeout() * 1000;
   }

   public final MuxableSocket getSocketFilter() {
      return this.filter;
   }

   public final void setSocketFilter(MuxableSocket f) {
      this.filter = f;
   }

   public final SocketInfo getSocketInfo() {
      return this.info;
   }

   public final void setSocketInfo(SocketInfo i) {
      this.info = i;
   }

   public String toString() {
      return super.toString() + ":" + this.socket;
   }

   public int size() {
      return KEYS.length;
   }

   public String[] getNames() {
      return KEYS;
   }

   public Object getValue(String name) {
      if (name.equals("com.bea.contextelement.channel.Port")) {
         return new Integer(this.channel.getPort());
      } else if (name.equals("com.bea.contextelement.channel.PublicPort")) {
         return new Integer(this.channel.getPublicPort());
      } else if (name.equals("com.bea.contextelement.channel.RemotePort")) {
         return new Integer(this.getRemotePort());
      } else if (name.equals("com.bea.contextelement.channel.Protocol")) {
         return this.getProtocol().getAsURLPrefix();
      } else if (name.equals("com.bea.contextelement.channel.Address")) {
         return this.channel.getAddress();
      } else if (name.equals("com.bea.contextelement.channel.PublicAddress")) {
         return this.channel.getPublicAddress();
      } else if (name.equals("com.bea.contextelement.channel.RemoteAddress")) {
         return this.getRemoteAddress();
      } else if (name.equals("com.bea.contextelement.channel.ChannelName")) {
         return this.channel.getChannelName();
      } else {
         return name.equals("com.bea.contextelement.channel.Secure") ? new Boolean(this.channel.supportsTLS()) : null;
      }
   }

   public ContextElement[] getValues(String[] names) {
      ContextElement[] ret = new ContextElement[names.length];

      for(int i = 0; i < names.length; ++i) {
         ret[i] = new ContextElement(names[i], this.getValue(names[i]));
      }

      return ret;
   }

   protected void resetData() {
      this.tail = this.head = Chunk.getChunk();
      this.availBytes = 0;
      this.msgLength = -1;
      this.messagesReceived = this.bytesReceived = 0L;
      this.sawEndOfStream = false;
      this.timedOut = false;
      this.hadException = null;
      this.whoClosedMe = null;
      this.isCleanup = false;
   }

   public boolean supportsScatteredRead() {
      return false;
   }

   public long read(NIOConnection connection) throws IOException {
      throw new UnsupportedOperationException();
   }
}
