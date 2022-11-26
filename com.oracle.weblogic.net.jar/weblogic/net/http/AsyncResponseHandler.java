package weblogic.net.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import javax.net.ssl.SSLSocket;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.JSSESocket;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketMuxer;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.Debug;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedInputStream;
import weblogic.work.ContextWrap;
import weblogic.work.Work;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class AsyncResponseHandler {
   private static boolean disableAsyncResponse = Boolean.getBoolean("weblogic.http.DisableAsyncResponse");
   private static boolean disableAsyncResponseForHTTPS = Boolean.getBoolean("weblogic.http.DisableAsyncResponseForHTTPS");

   private AsyncResponseHandler() {
   }

   public static AsyncResponseHandler getInstance() {
      return AsyncResponseHandler.Factory.THE_ONE;
   }

   public static void disableAsyncResponse() {
      disableAsyncResponse = true;
   }

   public static void disableAsyncResponseForHTTPS() {
      disableAsyncResponseForHTTPS = true;
   }

   public void writeRequestAndRegister(HttpURLConnection connection, AsyncResponseCallback callback) throws IOException {
      this.writeRequestAndRegister(connection, callback, WorkManagerFactory.getInstance().getDefault());
   }

   public void writeRequestAndRegister(HttpURLConnection connection, AsyncResponseCallback callback, String workManagerName) throws IOException {
      WorkManager workManager = WorkManagerFactory.getInstance().find(workManagerName);
      if (workManager == null) {
         throw new IllegalArgumentException("WorkManager with name '" + workManagerName + "' cannot be found");
      } else {
         this.writeRequestAndRegister(connection, callback, workManager);
      }
   }

   public void writeRequestAndRegister(HttpURLConnection connection, AsyncResponseCallback callback, WorkManager workManager) throws IOException {
      if (!this.handleSynchronously(connection, callback, workManager)) {
         Debug.assertion(KernelStatus.isServer(), "HTTP async response API cannot be used on clients");
         Debug.assertion(connection.isConnected(), "HTTP async response API cannot be invoked on a disconnected HttpURLConnection");
         if (workManager == null) {
            throw new IllegalArgumentException("WorkManager cannot be null !");
         } else {
            connection.writeRequestForAsyncResponse();
            MuxableSocketHTTPAsyncResponse oldSocket = this.getExistingMuxableSocket(connection);
            if (oldSocket != null) {
               oldSocket.reRegister(connection, callback, workManager);
               reRegisterWithMuxer(oldSocket);
            }

            if (oldSocket == null) {
               this.registerWithMuxer(connection, callback, workManager);
            }

         }
      }
   }

   private void registerWithMuxer(HttpURLConnection connection, AsyncResponseCallback callback, WorkManager workManager) throws IOException {
      MuxableSocketHTTPAsyncResponse muxableSocket = new MuxableSocketHTTPAsyncResponse(connection, callback, workManager);
      connection.setMuxableSocket(muxableSocket);
      if (connection instanceof HttpsURLConnection) {
         SSLSocket sslSocket = (SSLSocket)connection.getSocket();
         JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSocket);
         if (jsseSock != null) {
            JSSEUtils.registerJSSEFilter(jsseSock, muxableSocket);
            if (muxableSocket.isMessageComplete()) {
               muxableSocket.dispatch();
            } else {
               SocketMuxer.getMuxer().read(jsseSock.getFilter());
            }

            connection.setScavenger(new Scavenger(jsseSock.getFilter()));
         } else {
            SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSocket);
            SSLFilter sslf = (SSLFilter)sslIOCtx.getFilter();
            muxableSocket.setSocketFilter(sslf);
            sslf.setDelegate(muxableSocket);
            sslf.activateNoRegister();
            SocketMuxer.getMuxer().register(sslf);
            SocketMuxer.getMuxer().read(sslf);
            connection.setScavenger(new Scavenger(sslf));
         }
      } else {
         SocketMuxer.getMuxer().register(muxableSocket);
         connection.setScavenger(new Scavenger(muxableSocket));
         SocketMuxer.getMuxer().read(muxableSocket);
      }

   }

   private static void reRegisterWithMuxer(MuxableSocket socket) {
      MuxableSocket filter = socket.getSocketFilter();
      if (filter instanceof SSLFilter) {
         ((SSLFilter)filter).asyncOn();
      }

      SocketMuxer.getMuxer().read(filter);
   }

   private MuxableSocketHTTPAsyncResponse getExistingMuxableSocket(HttpURLConnection connection) {
      return (MuxableSocketHTTPAsyncResponse)connection.getMuxableSocket();
   }

   private boolean handleSynchronously(HttpURLConnection connection, AsyncResponseCallback callback, WorkManager workManager) throws IOException {
      if (disableAsyncResponse) {
         this.bypassMuxer(connection, callback, workManager);
         return true;
      } else if (disableAsyncResponseForHTTPS && connection instanceof HttpsURLConnection) {
         this.bypassMuxer(connection, callback, workManager);
         return true;
      } else {
         return false;
      }
   }

   private void bypassMuxer(HttpURLConnection connection, AsyncResponseCallback callback, WorkManager workManager) throws IOException {
      connection.writeRequests();
      workManager.schedule(new MuxableSocketHTTPAsyncResponse.RunnableCallback(callback, connection));
   }

   // $FF: synthetic method
   AsyncResponseHandler(Object x0) {
      this();
   }

   private static class Scavenger implements Runnable {
      private MuxableSocket ms;

      Scavenger(MuxableSocket ms) {
         this.ms = ms;
      }

      public void run() {
         SocketMuxer.getMuxer().closeSocket(this.ms);
      }
   }

   static class MuxableSocketHTTPAsyncResponse extends AbstractMuxableSocket {
      private static final boolean DEBUG = false;
      private AsyncResponseCallback callback;
      private HttpURLConnection connection;
      private InputStream socketInputStream;
      private WorkManager workManager;
      private Runnable runnable;
      private Throwable exception;
      private boolean responseAvailable;

      MuxableSocketHTTPAsyncResponse(HttpURLConnection connection, AsyncResponseCallback callback, WorkManager workManager) throws IOException {
         super(Chunk.getChunk(), connection.getSocket(), ServerChannelManager.findDefaultLocalServerChannel());
         this.init(connection, callback, workManager);
      }

      public synchronized int getIdleTimeoutMillis() {
         return this.connection != null && this.connection.getTimeout() > 0 ? this.connection.getTimeout() : super.getIdleTimeoutMillis();
      }

      public boolean isMessageComplete() {
         return true;
      }

      public synchronized void dispatch() {
         if (this.connection != null && this.workManager != null) {
            this.invokeCallback();
         } else {
            this.responseAvailable = true;
         }

      }

      private void invokeCallback() {
         try {
            if (this.getSocketFilter() instanceof SSLFilter) {
               SSLFilter sslf = (SSLFilter)this.getSocketFilter();
               sslf.asyncOff();
            }

            InputStream inputStream = new SequenceInputStream(new ChunkedInputStream(this.head, 0), this.socketInputStream);
            this.head = Chunk.getChunk();
            this.connection.setInputStream(inputStream);
            this.responseAvailable = false;
            this.workManager.schedule(this.runnable);
         } finally {
            this.reset();
         }

      }

      private synchronized void handleError(Throwable t) {
         if (this.workManager != null) {
            if (t == null) {
               this.invokeCallbackWithErrorStream(new EOFInputStream());
            } else {
               this.invokeCallbackWithErrorStream(new SocketClosedNotification());
            }
         } else {
            this.exception = t;
         }

      }

      private synchronized void handleTimeout() {
         if (this.connection != null && this.workManager != null) {
            this.invokeCallbackWithErrorStream(new SocketTimeoutNotification());
         }
      }

      private void invokeCallbackWithErrorStream(InputStream errorStream) {
         try {
            if (this.getAvailableBytes() > 0) {
               InputStream inputStream = new SequenceInputStream(new ChunkedInputStream(this.head, 0), errorStream);
               this.connection.setInputStream(inputStream);
            } else {
               this.connection.setInputStream(errorStream);
            }

            this.responseAvailable = false;
            this.workManager.schedule(this.runnable);
         } finally {
            this.reset();
         }

      }

      private void init(HttpURLConnection connection, AsyncResponseCallback callback, WorkManager workManager) throws IOException {
         this.callback = callback;
         this.connection = connection;
         this.socketInputStream = new BufferedInputStream(connection.getSocket().getInputStream());
         this.workManager = workManager;
         this.runnable = this.getRunnable();
      }

      public boolean closeSocketOnError() {
         return false;
      }

      private void reset() {
         this.callback = null;
         this.connection = null;
         this.workManager = null;
         this.runnable = null;
         this.socketInputStream = null;
         this.responseAvailable = false;
         this.prepareForReuse();
      }

      synchronized void reRegister(HttpURLConnection connection, AsyncResponseCallback callback, WorkManager workManager) throws IOException {
         if (this.exception != null) {
            String message = this.exception.getMessage();
            this.exception = null;
            throw new IOException(message);
         } else {
            this.init(connection, callback, workManager);
            if (this.responseAvailable) {
               this.invokeCallback();
            }

         }
      }

      public void hasException(Throwable t) {
         super.hasException(t);
         this.handleError(t);
      }

      public void endOfStream() {
         super.endOfStream();
         this.handleError((Throwable)null);
      }

      public boolean timeout() {
         this.handleTimeout();
         return true;
      }

      private Runnable getRunnable() {
         Runnable runnable = new RunnableCallback(this.callback, this.connection);
         if (this.callback instanceof ContextAwareAsyncResponseCallback) {
            return this.callback instanceof Work ? new ContextWrap(runnable, (Work)this.callback, (Work)this.callback) : new ContextWrap(runnable);
         } else {
            return runnable;
         }
      }

      private void debug(String s) {
      }

      private static class EOFInputStream extends InputStream {
         private EOFInputStream() {
         }

         public int read() throws IOException {
            return -1;
         }

         // $FF: synthetic method
         EOFInputStream(Object x0) {
            this();
         }
      }

      private static class RunnableCallback implements Runnable {
         private final AsyncResponseCallback callback;
         private final HttpURLConnection connection;

         RunnableCallback(AsyncResponseCallback callback, HttpURLConnection connection) {
            this.callback = callback;
            this.connection = connection;
         }

         public void run() {
            this.callback.handleResponse(this.connection);
         }
      }
   }

   private static final class Factory {
      static final AsyncResponseHandler THE_ONE = new AsyncResponseHandler();
   }
}
