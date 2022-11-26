package weblogic.rjvm.t3.client;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import weblogic.protocol.ChannelHelperBase;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.socket.SSLFilter;

public final class MuxableSocketT3S extends MuxableSocketT3 {
   private static final long serialVersionUID = -1499853227078510946L;

   MuxableSocketT3S(ServerChannel networkChannel, String partitionUrl) {
      super(networkChannel, partitionUrl);
      this.setupSocketFactory(networkChannel);
   }

   private void setupSocketFactory(ServerChannel networkChannel) {
      SSLSocketFactory sslSocketFactory = null;
      if (RJVMEnvironment.getEnvironment().getSSLContext() != null) {
         sslSocketFactory = ((SSLContext)((SSLContext)RJVMEnvironment.getEnvironment().getSSLContext())).getSocketFactory();
      } else {
         sslSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
      }

      this.setSocketFactory(new T3ClientWeblogicSocketFactory(sslSocketFactory, networkChannel));
      if (ChannelHelperBase.isAdminChannel(networkChannel)) {
         this.connection.setAdminQOS();
      }

   }

   public void ensureForceClose() {
      ((SSLFilter)this.getSocketFilter()).ensureForceClose();
   }
}
