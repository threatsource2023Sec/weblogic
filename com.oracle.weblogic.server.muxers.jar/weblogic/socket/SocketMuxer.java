package weblogic.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.health.OOMENotifier;
import weblogic.kernel.ExecuteThreadManager;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.platform.VM;
import weblogic.server.ServiceFailureException;
import weblogic.socket.internal.SocketEnvironment;
import weblogic.socket.utils.ProxyUtils;
import weblogic.socket.utils.SDPSocketUtils;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.AssertionError;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.io.Chunk;
import weblogic.work.WorkManagerFactory;

public abstract class SocketMuxer {
   private static final String TIMER_MANAGER_NAME = "MuxerTimerManager";
   private static final int TIMER_MANAGER_INTERVAL = 5000;
   private static final String DELAYPOLLWAKEUPPROP = "weblogic.socket.SocketMuxer.DELAY_POLL_WAKEUP";
   protected static final long DELAYPOLLWAKEUP = initMuxerDelayPollProp();
   private static final int EOS = -1;
   private static final String sockCreateTimeoutProp = "weblogic.client.socket.ConnectTimeout";
   private static final int sockCreateTimeout = initSockCreateTimeoutProp();
   protected static final String SOCKET_READERS_QUEUE_NAME = "weblogic.socket.Muxer";
   protected final ConcurrentHashMap sockets = new ConcurrentHashMap(4096);
   private static final Object ISPRESENT = new Object();
   private static boolean isAvailable = false;
   private static final String osName = initOSNameProp();
   private static final boolean isLinux;
   private static final boolean isAix;
   private static OOMENotifier oomeNotifier;
   protected static int rdrThreads;

   private static int initSockCreateTimeoutProp() {
      if (!KernelStatus.isServer()) {
         try {
            return Integer.getInteger("weblogic.client.socket.ConnectTimeout", 0) * 1000;
         } catch (SecurityException var1) {
            return 0;
         } catch (NumberFormatException var2) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private static long initMuxerDelayPollProp() {
      return KernelStatus.isServer() ? Long.getLong("weblogic.socket.SocketMuxer.DELAY_POLL_WAKEUP", 0L) : 0L;
   }

   private static String initOSNameProp() {
      String s = "UNKNOWN";

      try {
         s = System.getProperty("os.name", "UNKNOWN").toLowerCase(Locale.ENGLISH);
      } catch (SecurityException var2) {
      }

      return s;
   }

   public static SocketMuxer getMuxer() {
      return SocketMuxer.SingletonMaker.singleton;
   }

   public static boolean isAvailable() {
      return isAvailable;
   }

   static SocketMuxer initSocketMuxerOnServer() throws ServiceFailureException {
      return SocketMuxer.SingletonMaker.singleton;
   }

   static void initOOMENotifier(OOMENotifier oomeNotifier) {
      SocketMuxer.oomeNotifier = oomeNotifier;
   }

   private static SocketMuxer makeTheMuxer() {
      try {
         String muxerClassName;
         JavaSocketMuxer var1;
         SocketMuxer var2;
         NIOSocketMuxer var24;
         if (KernelStatus.isServer()) {
            muxerClassName = getNativeMuxerClassName();

            try {
               SocketMuxer mux = (SocketMuxer)Class.forName(muxerClassName).newInstance();
               if (muxerClassName != null && muxerClassName.indexOf("Java") == -1) {
                  SocketLogger.logNativeIOEnabled();
               } else {
                  SocketLogger.logNativeIODisabled();
               }

               var2 = mux;
               return var2;
            } catch (ThreadDeath var19) {
               throw var19;
            } catch (UnsatisfiedLinkError var20) {
               SocketLogger.logMuxerUnsatisfiedLinkError(getLinkError(muxerClassName));
               if (KernelStatus.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                  SocketLogger.logDebugThrowable("Muxer creation failed", var20);
               }
            } catch (Throwable var21) {
               if (muxerClassName.equals("weblogic.socket.DevPollSocketMuxer")) {
                  SocketLogger.logNativeDevPollMuxerError(var21);

                  try {
                     var2 = (SocketMuxer)Class.forName("weblogic.socket.PosixSocketMuxer").newInstance();
                     return var2;
                  } catch (ThreadDeath var17) {
                     throw var17;
                  } catch (Throwable var18) {
                     SocketLogger.logNativeMuxerError(var18);
                  }
               } else {
                  SocketLogger.logNativeMuxerError(var21);
               }
            }

            try {
               var24 = new NIOSocketMuxer();
               return var24;
            } catch (Exception var16) {
               SocketLogger.logNativeMuxerError(var16);
               SocketLogger.logNativeIODisabled();

               try {
                  var1 = new JavaSocketMuxer();
                  return var1;
               } catch (IOException var15) {
                  SocketLogger.logJavaMuxerCreationError2();
                  if (KernelStatus.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                     SocketLogger.logDebugThrowable("Java muxer creation failed", var15);
                  }

                  var2 = null;
                  return var2;
               }
            }
         } else {
            muxerClassName = getNativeMuxerClassName();
            if (muxerClassName != null && muxerClassName.equals("weblogic.socket.NIOSocketMuxer")) {
               try {
                  var24 = new NIOSocketMuxer();
                  return var24;
               } catch (IOException var22) {
                  SocketLogger.logNativeMuxerError2();
                  if (KernelStatus.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                     SocketLogger.logDebugThrowable("NIO muxer creation failed", var22);
                  }
               }
            }

            try {
               var1 = new JavaSocketMuxer();
               return var1;
            } catch (IOException var14) {
               SocketLogger.logJavaMuxerCreationError2();
               if (KernelStatus.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                  SocketLogger.logDebugThrowable("Java muxer creation failed", var14);
               }

               var2 = null;
               return var2;
            }
         }
      } finally {
         isAvailable = true;
      }
   }

   private static String getNativeMuxerClassName() {
      String muxerClassName = null;

      try {
         muxerClassName = Kernel.getConfig().getMuxerClass();
         if (muxerClassName != null && !muxerClassName.isEmpty()) {
            return muxerClassName;
         }

         muxerClassName = chooseMuxerClassBySettings();
      } catch (SecurityException var2) {
         muxerClassName = "weblogic.socket.JavaSocketMuxer";
      }

      SocketLogger.logNoMuxerSpecified(muxerClassName);
      return muxerClassName;
   }

   private static String chooseMuxerClassBySettings() throws SecurityException {
      if (!VM.getVM().isNativeThreads()) {
         return "weblogic.socket.JavaSocketMuxer";
      } else if (!Kernel.getConfig().isNativeIOEnabled()) {
         return "weblogic.socket.JavaSocketMuxer";
      } else if (osName.startsWith("windows")) {
         return "weblogic.socket.NTSocketMuxer";
      } else if (Kernel.getConfig().isDevPollDisabled()) {
         return "weblogic.socket.PosixSocketMuxer";
      } else {
         return !osName.equals("hp-ux") && !osName.equals("sunos") ? "weblogic.socket.PosixSocketMuxer" : "weblogic.socket.DevPollSocketMuxer";
      }
   }

   private static String getLinkError(String muxerClassName) {
      String path = null;

      try {
         path = System.getProperty("java.library.path", "java.library.path");
      } catch (SecurityException var3) {
      }

      if ("weblogic.socket.NTSocketMuxer".equals(muxerClassName)) {
         return "Please ensure that wlntio.dll is in: '" + path + "'";
      } else {
         return "weblogic.socket.PosixSocketMuxer".equals(muxerClassName) ? "Please ensure that libmuxer library is in :'" + path + "'" : "Please ensure that a native performance library is in: '" + path + "'";
      }
   }

   /** @deprecated */
   @Deprecated
   protected void initSocketReaderThreads(int defaultNumberOfReaders, String queueName, String oldQueueName) {
      initThreadCount(defaultNumberOfReaders, queueName, oldQueueName);
      this.startSocketReaderThreads(queueName);
   }

   public int getNumberOfReaders() {
      return rdrThreads;
   }

   protected static void initThreadCount(int defaultNumberOfReaders, String queueName, String oldQueueName) {
      ExecuteThreadManager queue = Kernel.getExecuteThreadManager(queueName);
      int nreaders;
      if (queue != null && queue.getName().equalsIgnoreCase(queueName)) {
         nreaders = queue.getExecuteThreadCount();
      } else {
         nreaders = Kernel.getConfig().getSocketReaders();
         if (nreaders <= 0) {
            nreaders = Integer.getInteger(oldQueueName, -1);
            if (nreaders <= 0) {
               int numberOfCPUs = Runtime.getRuntime().availableProcessors();
               if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
                  SocketLogger.logDebug("Number of CPUs=" + numberOfCPUs);
               }

               if (numberOfCPUs > 0) {
                  nreaders = numberOfCPUs + 1;
               } else {
                  nreaders = defaultNumberOfReaders;
               }

               if (nreaders > 4) {
                  nreaders = 4;
               }
            }
         }

         Kernel.addExecuteQueue(queueName, nreaders);
      }

      rdrThreads = nreaders;
   }

   protected void startSocketReaderThreads(String queueName) {
      if (rdrThreads == -1) {
         throw new IllegalStateException("Socket Reader threads not initialized");
      } else {
         SocketLogger.logAllocSocketReaders(rdrThreads);

         for(int i = 0; i < rdrThreads; ++i) {
            Kernel.execute(new SocketReaderRequest(), queueName);
         }

      }
   }

   public boolean isAsyncMuxer() {
      return false;
   }

   public Socket newSocket(InetAddress address, int port) throws IOException {
      return this.newSocket(address, port, sockCreateTimeout);
   }

   public Socket newSocket(InetAddress address, int port, int timeout) throws IOException {
      Socket sock = new Socket();
      initSocket(sock);
      sock.connect(new InetSocketAddress(address, port), timeout);
      return sock;
   }

   public Socket newClientSocket(InetAddress host, int port, int timeout) throws IOException {
      return ProxyUtils.canProxy(host, false) ? ProxyUtils.getClientProxy(host.getHostAddress(), port, timeout) : this.newSocket(host, port, timeout);
   }

   public Socket newClientSocket(InetAddress host, int port, InetAddress localHost, int localPort, int timeout) throws IOException {
      return ProxyUtils.canProxy(host, false) ? ProxyUtils.getClientProxy(host.getHostAddress(), port, localHost, localPort, timeout) : this.newSocket(host, port, localHost, localPort, timeout);
   }

   public Socket newClientSocket(InetAddress host, int port) throws IOException {
      return this.newClientSocket(host, port, 0);
   }

   public Socket newSSLClientSocket(InetAddress host, int port, int timeout) throws IOException {
      return ProxyUtils.canProxy(host, true) ? ProxyUtils.getSSLClientProxy(host.getHostAddress(), port, timeout) : null;
   }

   public Socket newSSLClientSocket(InetAddress host, int port, InetAddress localHost, int localPort, int timeout) throws IOException {
      return ProxyUtils.canProxy(host, true) ? ProxyUtils.getSSLClientProxy(host.getHostAddress(), port, localHost != null ? localHost.getHostAddress() : null, localPort, timeout) : null;
   }

   public Socket newSSLClientSocket(InetAddress host, int port) throws IOException {
      return this.newSSLClientSocket(host, port, 0);
   }

   public Socket newSocket(InetAddress address, int port, InetAddress localAddr, int localPort, int connectTimeoutMillis) throws IOException {
      Socket sock = new Socket();
      return this.initSocket(sock, address, port, localAddr, localPort, connectTimeoutMillis);
   }

   public Socket newSDPSocket(InetAddress address, int port, InetAddress localAddr, int localPort, int connectTimeoutMillis) throws IOException {
      Socket sock = SDPSocketUtils.createSDPSocket();
      return this.initSocket(sock, address, port, localAddr, localPort, connectTimeoutMillis);
   }

   protected Socket initSocket(Socket sock, InetAddress address, int port, InetAddress localAddr, int localPort, int connectTimeoutMillis) throws IOException {
      initSocket(sock);
      sock.bind(new InetSocketAddress(localAddr, localPort));
      sock.connect(new InetSocketAddress(address, port), connectTimeoutMillis);
      return sock;
   }

   public Socket newProxySocket(InetAddress host, int port, InetAddress localAddr, int localPort, InetAddress proxyHost, int proxyPort, int connectTimeoutMillis) throws IOException {
      Socket sock = this.newSocket(proxyHost, proxyPort, localAddr, localPort, connectTimeoutMillis);
      return ProxyUtils.getProxySocket(sock, host.getHostName(), port, proxyHost.getHostName(), proxyPort);
   }

   public Socket newWeblogicSocket(Socket sock) throws IOException {
      initSocket(sock);
      SocketEnvironment sockEnv = SocketEnvironment.getSocketEnvironment();
      return sockEnv.serverThrottleEnabled() ? sockEnv.getWeblogicSocket(sock) : sock;
   }

   static void initSocket(Socket sock) throws SocketOptionException {
      try {
         sock.setTcpNoDelay(true);
      } catch (SocketException var4) {
         try {
            sock.close();
         } catch (IOException var3) {
         }

         throw new SocketOptionException(var4.getMessage());
      }
   }

   protected void closeSocket(Socket s) {
      this.closeSocket(s, true);
   }

   private void closeSocket(Socket s, boolean shouldCloseSocket) {
      try {
         if (isLinux || isAix) {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerConnection()) {
               SocketLogger.logDebug("Closing input and output of socket " + s);
            }

            try {
               s.shutdownInput();
            } catch (IOException var5) {
            }

            try {
               s.shutdownOutput();
            } catch (IOException var4) {
            }
         }

         if (shouldCloseSocket) {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerConnection()) {
               SocketLogger.logDebug("Closing raw socket " + s);
            }

            s.close();
         }
      } catch (Exception var6) {
      }

   }

   protected SocketMuxer() throws IOException {
      TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager("MuxerTimerManager", WorkManagerFactory.getInstance().getSystem());
      tm.scheduleAtFixedRate(new TimerListenerImpl(), 0L, 5000L);
   }

   public void register(MuxableSocket ms) throws IOException {
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
         SocketLogger.logDebug("register: sockInfo=" + ms.getSocketInfo());
      }

      this.sockets.put(ms, ISPRESENT);
   }

   public void register(Collection muxableSockets) {
      HashMap toRegister = new HashMap();

      MuxableSocket tmp;
      for(Iterator var3 = muxableSockets.iterator(); var3.hasNext(); toRegister.put(tmp, ISPRESENT)) {
         tmp = (MuxableSocket)var3.next();
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebug("register: sockInfo=" + tmp.getSocketInfo());
         }
      }

      this.sockets.putAll(toRegister);
   }

   public void reRegister(MuxableSocket oldSock, MuxableSocket newSock) {
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
         SocketLogger.logDebug("reRegister: oldSockInfo=" + oldSock.getSocketInfo() + ", newSock=" + newSock);
      }

      this.sockets.remove(oldSock);
      SocketInfo info = oldSock.getSocketInfo();
      oldSock.setSocketInfo((SocketInfo)null);
      info.setMuxableSocket(newSock);
      newSock.setSocketInfo(info);
      this.sockets.put(newSock, ISPRESENT);
   }

   public abstract void read(MuxableSocket var1);

   public abstract void read(Collection var1);

   public final void closeSocket(MuxableSocket ms) {
      this.deliverEndOfStream(ms);
   }

   protected abstract void processSockets();

   public final int getNumSockets() {
      return this.sockets.size();
   }

   public final Iterator getSocketsIterator() {
      return this.sockets.keySet().iterator();
   }

   public final MuxableSocket[] getSockets() {
      Set socketSet = this.sockets.keySet();
      MuxableSocket[] ret = new MuxableSocket[socketSet.size()];
      return (MuxableSocket[])socketSet.toArray(ret);
   }

   final boolean initiateIO(SocketInfo info) {
      return info.ioInitiated();
   }

   final boolean completeIO(MuxableSocket ms, SocketInfo info) {
      int status = info.ioCompleted();
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
         SocketLogger.logDebug("completeIO: " + status + ", info=" + info);
      }

      if (status == 1) {
         return false;
      } else if ((status & 2) != 0) {
         if ((status & 8) == 0) {
            this.cleanupSocket(ms, info);
         }

         return false;
      } else {
         return true;
      }
   }

   public final void finishExceptionHandling(MuxableSocket ms) {
      SocketInfo info = ms.getSocketInfo();
      int status = info.exceptionHandlingCompleted();
      if (status == 0) {
         this.cleanupSocket(ms, info);
      } else if (status == 4) {
         this.cancelIo(ms);
      }

   }

   public final void deliverEndOfStream(MuxableSocket ms) {
      this.deliverExceptionAndCleanup(ms, (Throwable)null);
   }

   public final void deliverHasException(MuxableSocket ms, Throwable ex) {
      if (oomeNotifier != null && ex instanceof OutOfMemoryError) {
         oomeNotifier.notifyOOME((OutOfMemoryError)ex);
      }

      this.deliverExceptionAndCleanup(ms, ex);
   }

   private void deliverExceptionAndCleanup(MuxableSocket ms, Throwable ex) {
      if (ms == null) {
         throw new AssertionError(ex);
      } else {
         SocketInfo info = ms.getSocketInfo();
         MuxableSocket socketFilter = ms.getSocketFilter();
         if (info == null && socketFilter != null) {
            info = socketFilter.getSocketInfo();
         }

         if (info == null) {
            if (Kernel.DEBUG && (Kernel.getDebug().getDebugMuxer() || Kernel.getDebug().getDebugMuxerConnection())) {
               SocketLogger.logDebug("Unable to find internal data record for socket " + ms);
            }

         } else {
            int status = info.close();
            if (status != 1) {
               if (Kernel.DEBUG && (Kernel.getDebug().getDebugMuxer() || Kernel.getDebug().getDebugMuxerConnection())) {
                  StringBuffer sb = new StringBuffer(100);
                  sb.append("deliver");
                  if (ex == null) {
                     sb.append("EndOfStream");
                  } else {
                     sb.append("HasException");
                  }

                  sb.append(": sockInfo=").append(info).append("\n");
                  if (ex == null) {
                     sb.append(StackTraceUtils.throwable2StackTrace(new Exception()));
                  } else {
                     sb.append(StackTraceUtils.throwable2StackTrace(ex));
                  }

                  SocketLogger.logDebug(sb.toString());
               }

               MuxableSocket cleanupMS = socketFilter != null ? socketFilter : ms;
               if (ex == null) {
                  cleanupMS.endOfStream();
               } else {
                  cleanupMS.hasException(ex);
               }

               status = info.exceptionHandlingCompleted();
               if (status == 0) {
                  this.cleanupSocket(cleanupMS, info);
               } else if (status == 4) {
                  this.cancelIo(cleanupMS);
               } else {
                  throw new AssertionError("Socket ms=" + info + " in unexpected state: " + status);
               }
            }
         }
      }
   }

   protected void cancelIo(MuxableSocket ms) {
      if (isAix) {
         this.closeSocket(ms.getSocket(), false);
      } else {
         this.closeSocket(ms.getSocket());
      }

      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
         SocketLogger.logDebug("cancelIo: ms=" + ms + ", sockInfo=" + ms.getSocketInfo());
      }

   }

   void cleanupSocket(MuxableSocket ms, SocketInfo info) {
      if (this.sockets.remove(ms) != null) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebug("cleanupSocket: sockInfo=" + ms.getSocketInfo());
         }

         try {
            if (info != null) {
               info.cleanup();
            }
         } finally {
            if (ms.closeSocketOnError()) {
               this.closeSocket(ms.getSocket());
            }

         }

      }
   }

   final void readReadySocket(MuxableSocket ms, SocketInfo info, long timeoutMillis) {
      if (timeoutMillis > 0L) {
         long endOfTimeSliceMillis = System.currentTimeMillis() + timeoutMillis;

         while(this.readReadySocketOnce(ms, info)) {
            if (System.currentTimeMillis() > endOfTimeSliceMillis) {
               this.read(ms);
               break;
            }

            this.initiateIO(info);
         }
      } else if (this.readReadySocketOnce(ms, info)) {
         this.read(ms);
      }

   }

   private final boolean readReadySocketOnce(MuxableSocket ms, SocketInfo info) {
      Socket socket = null;
      int nread = 0;

      try {
         socket = ms.getSocket();
         InputStream sis = ms.getSocketInputStream();
         if (sis == null) {
            this.readCompleted(ms);
            return false;
         }

         nread = this.readFromSocket(ms);
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("read " + nread + " bytes");
         }
      } catch (InterruptedIOException var6) {
         this.handleReadTimeout(ms);
         return false;
      } catch (IOException var7) {
         this.readCompleted(ms);
         if (var7 instanceof MaxMessageSizeExceededException) {
            SocketLogger.logIOException(socket.toString(), var7);
         } else if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebugThrowable("Error reading socket: '" + ms + "'", var7);
         }

         this.deliverHasException(ms, var7);
         return false;
      } catch (Throwable var8) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            var8.printStackTrace();
         }
      }

      this.readCompleted(ms);
      if (nread == -1) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebug("EOF on socket: " + ms.getSocketInfo());
         }

         this.deliverEndOfStream(ms);
         return false;
      } else if (ms.isMessageComplete()) {
         info.messageCompleted();
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("dispatch " + info);
         }

         ms.dispatch();
         return false;
      } else {
         info.messageInitiated();
         return true;
      }
   }

   protected int readFromSocket(MuxableSocket ms) throws IOException {
      byte[] buf = ms.getBuffer();
      int offset = ms.getBufferOffset();
      int availableBuffer = buf.length - offset;
      int read = false;
      InputStream sis = ms.getSocketInputStream();
      if (sis == null) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebug("Socket has been closed and cleanedup: " + ms + " will return EOS from read");
         }

         return -1;
      } else {
         int read;
         if (sis instanceof NIOInputStream) {
            read = ((NIOInputStream)sis).readNonBlocking(buf, offset, availableBuffer);
         } else {
            read = sis.read(buf, offset, availableBuffer);
         }

         if (read > 0) {
            ms.incrementBufferOffset(read);
         }

         return read;
      }
   }

   protected void handleReadTimeout(MuxableSocket ms) {
      this.read(ms);
   }

   protected void readCompleted(MuxableSocket ms) {
   }

   public final void write(AsyncOutputStream s) {
      this.internalWrite(s);
   }

   protected void internalWrite(AsyncOutputStream s) {
      OutputStream os = s.getOutputStream();
      Chunk c = null;

      while(os != null && (c = s.getOutputBuffer()) != null) {
         try {
            os.write(c.buf, 0, c.end);
            s.handleWrite(c);
         } catch (IOException var5) {
            s.handleException(var5);
            return;
         }
      }

   }

   protected TimerListener createTimeoutTrigger() {
      return new TimerListenerImpl();
   }

   static {
      isLinux = "linux".equals(osName);
      isAix = "aix".equals(osName);
      rdrThreads = -1;
   }

   protected class TimerListenerImpl implements TimerListener {
      public void timerExpired(Timer timer) {
         Iterator socks = SocketMuxer.this.getSocketsIterator();

         while(true) {
            MuxableSocket ms;
            SocketInfo info;
            label69:
            while(true) {
               do {
                  if (!socks.hasNext()) {
                     return;
                  }

                  ms = (MuxableSocket)socks.next();
                  info = ms.getSocketInfo();
               } while(info == null);

               long idleTimeout = (long)ms.getIdleTimeoutMillis();
               long msgTimeout = (long)ms.getCompleteMessageTimeoutMillis();
               switch (info.checkTimeout(idleTimeout, msgTimeout)) {
                  case 0:
                  default:
                     break;
                  case 2:
                     if (ms.getSocket().isClosed()) {
                        SocketMuxer.this.cleanupSocket(ms, info);
                     } else if (Kernel.DEBUG && (Kernel.getDebug().getDebugMuxer() || Kernel.getDebug().getDebugMuxerConnection())) {
                        SocketLogger.logSocketMarkedCloseOnly(info.toString(), ms.toString());
                     }
                     break;
                  case 16:
                     if (Kernel.DEBUG && (Kernel.getDebug().getDebugMuxer() || Kernel.getDebug().getDebugMuxerTimeout())) {
                        SocketLogger.logDebug("Timeout on socket: '" + ms + "', sockInfo: " + ms.getSocketInfo() + ", timeout of: '" + idleTimeout / 1000L + " s");
                     }

                     if (ms.timeout()) {
                        break label69;
                     }
                     break;
                  case 32:
                     String msg = "A complete message could not be read on socket: '" + ms + "', in the configured timeout period of '" + msgTimeout / 1000L + "' secs";
                     if (Kernel.DEBUG && (Kernel.getDebug().getDebugMuxer() || Kernel.getDebug().getDebugMuxerTimeout())) {
                        SocketLogger.logDebug(msg + ", sockInfo=" + ms.getSocketInfo());
                     }

                     ms.hasException(new IOException(msg));
                     break label69;
               }
            }

            int status = info.exceptionHandlingCompleted();
            if (status == 0) {
               SocketMuxer.this.cleanupSocket(ms, info);
            } else if (status == 4) {
               SocketMuxer.this.cancelIo(ms);
            }
         }
      }
   }

   protected static final class SingletonMaker {
      protected static final SocketMuxer singleton = SocketMuxer.makeTheMuxer();
   }
}
