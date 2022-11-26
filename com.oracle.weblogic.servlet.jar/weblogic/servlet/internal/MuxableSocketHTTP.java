package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLSocket;
import weblogic.management.DeploymentException;
import weblogic.protocol.ServerChannel;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.servlet.HTTPLogger;
import weblogic.socket.AbstractMuxableSocket;
import weblogic.socket.JSSEFilterImpl;
import weblogic.socket.JSSESocket;
import weblogic.socket.MaxMessageSizeExceededException;
import weblogic.socket.MuxableSocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.SocketInfo;
import weblogic.socket.SocketMuxer;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.io.Chunk;
import weblogic.work.WorkManagerFactory;

public class MuxableSocketHTTP extends AbstractMuxableSocket implements HttpSocket {
   final HttpConnectionHandler connHandler;
   private MuxableSocket replacement = null;

   public MuxableSocketHTTP(Chunk head, Socket s, boolean secure, ServerChannel channel) throws IOException {
      super(head, channel);
      this.connect(s);
      this.setSoTimeout(this.getSocket().getSoTimeout());
      this.connHandler = new HttpConnectionHandler(this, head, secure);
      this.addSenderStatistics(this.connHandler.getServletResponse());
   }

   public boolean isSecure() {
      return this.connHandler.isSecure();
   }

   public String toString() {
      return super.toString() + " - idle timeout: '" + this.getIdleTimeoutMillis() + "' ms, socket timeout: '" + this.getSoTimeout() + "' ms";
   }

   public int getIdleTimeoutMillis() {
      return this.connHandler.getKeepAliveSecs();
   }

   public byte[] getBuffer() {
      return this.connHandler.getBuffer();
   }

   public int getBufferOffset() {
      return this.connHandler.getBufferOffset();
   }

   public void incrementBufferOffset(int i) throws MaxMessageSizeExceededException {
      this.connHandler.incrementBufferOffset(i);
   }

   public ByteBuffer getByteBuffer() {
      return this.connHandler.getByteBuffer();
   }

   public void incrementBufferOffset(Chunk c, int availBytes) throws MaxMessageSizeExceededException {
      this.connHandler.incrementBufferOffset(c, availBytes);
   }

   public void hasException(Throwable t) {
      if (t instanceof MaxMessageSizeExceededException) {
         HTTPLogger.logConnectionFailure((MaxMessageSizeExceededException)t);
      } else if (t instanceof IOException) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("IO issue", (IOException)t);
         }
      } else {
         HTTPLogger.logConnectionFailure(t);
      }

      this.connHandler.blowAllChunks();
      super.hasException(t);
   }

   public boolean timeout() {
      this.deliverEndOfStream();
      return super.timeout();
   }

   public void endOfStream() {
      this.connHandler.blowAllChunks();
      super.endOfStream();
   }

   public boolean isMessageComplete() {
      return this.connHandler.isMessageComplete();
   }

   public void dispatch() {
      if (this.getSocketFilter() instanceof SSLFilter && this.connHandler.isSecure()) {
         ((SSLFilter)this.getSocketFilter()).asyncOff();
      }

      this.connHandler.dispatch();
   }

   void handleSyncOnDemandLoad(final OnDemandManager odm, final OnDemandContext ctx, final String uri, final MuxableSocketHTTP sckt) {
      Runnable runnable = new Runnable() {
         public void run() {
            try {
               odm.loadOnDemandURI(ctx, false);
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("About to perform rest of servlet context processing for " + ctx.getAppName());
               }

               sckt.connHandler.resolveServletContext(uri);
            } catch (IOException var2) {
               HTTPLogger.logDispatchError(var2);
               MuxableSocketHTTP.this.deliverEndOfStream();
            } catch (DeploymentException var3) {
               HTTPLogger.logDispatchError(var3);
               MuxableSocketHTTP.this.deliverEndOfStream();
            }

         }
      };
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Scheduling runnable for on demand load of " + ctx.getAppName());
      }

      WorkManagerFactory.getInstance().getDefault().schedule(runnable);
   }

   public long getMessagesReceivedCount() {
      return this.connHandler.getMessagesReceivedCount();
   }

   public long getBytesReceivedCount() {
      return this.connHandler.getBytesReceivedCount();
   }

   public void registerForReadEvent() {
      if (this.getSocketFilter() instanceof SSLFilter && this.connHandler.isSecure()) {
         ((SSLFilter)this.getSocketFilter()).asyncOn();
      }

      SocketMuxer.getMuxer().read(this.getSocketFilter());
   }

   public void closeConnection(Throwable throwable) {
      if (throwable == null) {
         SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
      } else {
         SocketMuxer.getMuxer().deliverHasException(this.getSocketFilter(), throwable);
      }

   }

   public InputStream getInputStream() {
      return this.getSocketInputStream();
   }

   public OutputStream getOutputStream() {
      return this.getSocketOutputStream();
   }

   public ServerChannel getServerChannel() {
      return this.channel;
   }

   public boolean handleOnDemandContext(ServletRequestImpl request, String uri) throws IOException {
      OnDemandManager odm = this.connHandler.getHttpServer().getOnDemandManager();
      OnDemandContext odc = odm.lookupOnDemandContext(uri);
      if (odc == null) {
         return false;
      } else {
         try {
            if (odc.isDisplayRefresh()) {
               odm.loadOnDemandURI(odc, true);
               this.connHandler.sendRefreshPage(uri, odc.updateProgressIndicator());
               return true;
            } else {
               this.handleSyncOnDemandLoad(odm, odc, uri, this);
               return true;
            }
         } catch (DeploymentException var6) {
            this.connHandler.sendError(503);
            HTTPLogger.logDispatchError(var6);
            return true;
         }
      }
   }

   public Chunk getHeadChunk() {
      return this.head;
   }

   public void setHeadChunk(Chunk c) {
      this.head = c;
   }

   public void setSocketReadTimeout(int timeout) throws SocketException {
      this.setSoTimeout(timeout);
   }

   public void setMuxableSocketToSocketInfo() {
      if (this.replacement != null && !this.isSecure()) {
         this.reRegister(this.replacement, this);
         this.replacement = null;
      }

   }

   public void requeue() {
      this.registerForReadEvent();
   }

   protected void setReadListener(ReadListenerStateContext readStateHandler, ServletInputStreamImpl servletInputStream, boolean isAsync, boolean isChunk, long clen) {
      MuxableSocketNonBlocking ms = this.createMuxableSocketNonBlocking(this.connHandler, readStateHandler, servletInputStream.getInputStream(), isAsync, isChunk, clen);
      this.replacement = ms;
      readStateHandler.setHttpSocket(ms);
      servletInputStream.setInputStream(ms.getBufferInputStream());
      if (this.isSecure()) {
         SSLSocket sslSock = (SSLSocket)this.getSocket();
         JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSock);
         if (jsseSock != null) {
            JSSEFilterImpl filter = (JSSEFilterImpl)this.getSocketFilter();
            this.setSocketFilter(filter);
            filter.setDelegate(this);
         } else {
            SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSock);
            if (sslIOCtx != null) {
               SSLFilter sslf = (SSLFilter)sslIOCtx.getFilter();
               this.setSocketFilter(sslf);
               sslf.setDelegate(this);

               try {
                  sslf.activateNoRegister();
               } catch (IOException var13) {
                  HTTPLogger.logError("setReadListener Error", "SSL activateNoRegister failed!");
               }
            } else {
               HTTPLogger.logError("setReadListener Error", "SSL transport layer closed the socket!");
            }
         }
      } else {
         SocketInfo info = this.getSocketInfo();
         this.reRegister(this, ms);
         this.setSocketInfo(info);
      }

   }

   MuxableSocketNonBlocking createMuxableSocketNonBlocking(HttpConnectionHandler connHandler, ReadListenerStateContext readStateHandler, InputStream inputStream, boolean isAsync, boolean isChunk, long clen) {
      return new MuxableSocketNonBlocking(connHandler, readStateHandler, inputStream, isAsync, isChunk, clen);
   }

   void reRegister(MuxableSocket oldSock, MuxableSocket newSock) {
      SocketMuxer.getMuxer().reRegister(oldSock, newSock);
   }

   void deliverEndOfStream() {
      SocketMuxer.getMuxer().deliverEndOfStream(this.getSocketFilter());
   }
}
