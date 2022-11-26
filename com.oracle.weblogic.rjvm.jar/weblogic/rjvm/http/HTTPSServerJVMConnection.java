package weblogic.rjvm.http;

import java.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.ServerChannel;
import weblogic.security.service.ContextHandler;

class HTTPSServerJVMConnection extends HTTPServerJVMConnection {
   private final X509Certificate[] javaChain;

   HTTPSServerJVMConnection(HttpServletRequest req, int abbrevTableSize, int headerLength, int peerChannelMaxMessageSize, SocketRuntime runtime, ServerChannel channel, ContextHandler contextHandler, String partitionUrl, String remotePartitionName) {
      super(abbrevTableSize, headerLength, peerChannelMaxMessageSize, runtime, channel, contextHandler, partitionUrl, remotePartitionName);
      this.setLocalPort(req.getServerPort());
      this.javaChain = (X509Certificate[])((X509Certificate[])req.getAttribute("javax.servlet.request.X509Certificate"));
   }

   public X509Certificate[] getJavaCertChain() {
      return this.javaChain;
   }

   public X509Certificate getClientJavaCert() {
      return this.javaChain != null && this.javaChain.length > 0 ? this.javaChain[0] : null;
   }
}
