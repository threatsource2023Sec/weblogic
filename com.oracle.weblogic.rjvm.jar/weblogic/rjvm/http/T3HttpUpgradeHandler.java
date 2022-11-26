package weblogic.rjvm.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.security.cert.X509Certificate;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.WebConnection;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMLogger;
import weblogic.rjvm.WebRjvmSupport;
import weblogic.security.service.ContextHandler;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.server.channels.SocketRuntimeImpl;
import weblogic.utils.Debug;
import weblogic.utils.io.Chunk;

public class T3HttpUpgradeHandler extends MsgAbbrevJVMConnection implements HttpUpgradeHandler, ReadListener {
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("T3HttpUpgradeHandler");
   private InputStream inputStream;
   private OutputStream outputStream;
   private WebConnection wc;
   private int abbrevTableSize = 0;
   private int headerLength = 0;
   private int peerChannelMaxMessageSize = -1;
   private String partitionUrl;
   private boolean closed = false;
   private int localPort;
   private InetAddress localAddress = null;
   private SocketRuntime sockRuntime;
   private ContextHandler contextHandler;
   private ServerChannel serverChannel;
   private X509Certificate[] javaChain = null;

   public void init(WebConnection wc) {
      try {
         this.wc = wc;
         this.inputStream = wc.getInputStream();
         this.outputStream = wc.getOutputStream();
         this.outputStream.flush();
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }

   public T3HttpUpgradeHandler acceptJVMConnection(HttpServletRequest req, int abbrevTableSize, int headerLength, int peerChannelMaxMessageSize, HttpServletResponse rsp, String partitionUrl) throws ProtocolException {
      this.serverChannel = WebRjvmSupport.getWebRjvmSupport().getChannel(req);
      if (!this.serverChannel.isTunnelingEnabled()) {
         throw new ProtocolException("HTTP is disabled");
      } else {
         this.sockRuntime = new SocketRuntimeImpl(WebRjvmSupport.getWebRjvmSupport().getSocketRuntime(req));
         this.peerChannelMaxMessageSize = peerChannelMaxMessageSize;
         this.abbrevTableSize = abbrevTableSize;
         this.headerLength = headerLength;
         this.partitionUrl = partitionUrl;
         this.setLocalPort(req.getServerPort());
         this.contextHandler = WebRjvmSupport.getWebRjvmSupport().getContextHandler(req);
         if (partitionUrl == null) {
            this.partitionUrl = req.getRequestURL().toString();
         }

         if (ChannelHelper.isAdminChannel(this.serverChannel)) {
            this.setAdminQOS();
         }

         if (rsp != null) {
            this.setLocalAddress(WebRjvmSupport.getWebRjvmSupport().getSocketLocalAddress(req));
         }

         String protocol = req.getScheme();
         if ("https".equals(protocol)) {
            this.javaChain = (X509Certificate[])((X509Certificate[])req.getAttribute("javax.servlet.request.X509Certificate"));
         }

         ((ServletInputStream)this.inputStream).setReadListener(this);
         return this;
      }
   }

   void setHeaderLength(int headerLength) {
      this.headerLength = headerLength;
   }

   void setPeerChannelMaxMessageSize(int peerChannelMaxMessageSize) {
      this.peerChannelMaxMessageSize = peerChannelMaxMessageSize;
   }

   void setPartitionUrl(String partitionUrl) {
      this.partitionUrl = partitionUrl;
   }

   public X509Certificate[] getJavaCertChain() {
      return this.javaChain;
   }

   public X509Certificate getClientJavaCert() {
      return this.javaChain != null && this.javaChain.length > 0 ? this.javaChain[0] : null;
   }

   public void onDataAvailable() throws IOException {
      throw new UnsupportedOperationException("onDataAvailable() not supported");
   }

   public void onAllDataRead() throws IOException {
      Chunk head = Chunk.getChunk();
      Debug.assertion(this.inputStream != null);
      int read = Chunk.chunkFully(head, this.inputStream);
      if (read < 4) {
         throw new ProtocolException("Fewer than: '4' bytes read: '" + read);
      }
   }

   public void onError(Throwable throwable) {
      try {
         this.close();
      } catch (Exception var3) {
         if (debugConnection.isDebugEnabled()) {
            debugConnection.debug("Error while closing WebConnection", var3);
         }
      }

   }

   public void destroy() {
      this.close();
   }

   public void connect(String host, InetAddress address, int port, int connectTimeout) throws IOException {
      throw new UnsupportedOperationException("T3HttpUpgradeHandler does not support outgoing connections");
   }

   protected void sendMsg(OutgoingMessage msg) throws IOException {
      if (this.closed) {
         throw new IOException("T3HttpUpgradeHandler closed");
      } else {
         msg.writeTo(this.outputStream);
      }
   }

   public void close() {
      if (!this.closed) {
         this.closed = true;
         if (debugConnection.isDebugEnabled()) {
            RJVMLogger.logDebug("Closing JVM socket: '" + this + '\'' + new Throwable("Stack trace"));
         }

         ServerChannel c = this.getChannel();
         if (c instanceof ServerChannelImpl && ((ServerChannelImpl)c).getRuntime() != null) {
            ((ServerChannelImpl)c).getRuntime().removeServerConnectionRuntime(this.sockRuntime);
         }

         try {
            this.wc.close();
         } catch (Exception var3) {
            RJVMLogger.logDebug("Closing T3HttpUpgradeHandler: '" + this + '\'' + var3);
         }

      }
   }

   public final InetAddress getLocalAddress() {
      return this.localAddress;
   }

   final void setLocalAddress(InetAddress address) {
      this.localAddress = address;
   }

   public final ServerChannel getChannel() {
      return this.serverChannel;
   }

   public InetAddress getInetAddress() {
      return null;
   }

   public final ContextHandler getContextHandler() {
      return this.contextHandler;
   }

   public final int getLocalPort() {
      return this.localPort == 0 ? -1 : this.localPort;
   }

   final void setLocalPort(int localPort) {
      this.localPort = localPort;
   }
}
