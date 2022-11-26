package weblogic.cluster;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.AccessController;
import weblogic.cluster.messaging.internal.server.UnicastFragmentSocket;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

final class FragmentSocketWrapper implements FragmentSocket, BeanUpdateListener {
   private static final boolean DEBUG = true;
   private static final int MAX_RECEIVE_RETRIES = 5;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static FragmentSocketWrapper THE_ONE;
   private final String mcastaddr;
   private final String interfaceAddress;
   private final int port;
   private final byte ttl;
   private final long packetDelay;
   private final int bufSize;
   private FragmentSocket sock;
   private String currentMessagingMode;

   static synchronized FragmentSocketWrapper getInstance(String mcastaddr, String interfaceAddress, int port, byte ttl, long packetDelay, int bufSize) throws IOException {
      if (THE_ONE != null) {
         return THE_ONE;
      } else {
         THE_ONE = new FragmentSocketWrapper(mcastaddr, interfaceAddress, port, ttl, packetDelay, bufSize);
         return THE_ONE;
      }
   }

   private FragmentSocketWrapper(String mcastaddr, String interfaceAddress, int port, byte ttl, long packetDelay, int bufSize) throws IOException {
      this.mcastaddr = mcastaddr;
      this.interfaceAddress = interfaceAddress;
      this.port = port;
      this.ttl = ttl;
      this.packetDelay = packetDelay;
      this.bufSize = bufSize;
      ClusterMBean clusterMBean = ManagementService.getRuntimeAccess(kernelId).getServer().getCluster();
      String clusterName = clusterMBean.getName();
      this.currentMessagingMode = clusterMBean.getClusterMessagingMode();
      if ("unicast".equals(clusterMBean.getClusterMessagingMode())) {
         this.sock = this.getUnicastFragmentSocket();
      } else {
         this.sock = new MulticastFragmentSocket(clusterName, mcastaddr, interfaceAddress, port, ttl, packetDelay, bufSize);
      }

      clusterMBean.addBeanUpdateListener(this);
   }

   private FragmentSocket getUnicastFragmentSocket() {
      return new UnicastFragmentSocket();
   }

   public void start() throws IOException {
      this.sock.start();
   }

   public void send(byte[] buffer, int length) throws IOException {
      this.sock.send(buffer, length);
   }

   public byte[] receive() throws InterruptedIOException, IOException {
      int i = 0;

      while(i < 5) {
         FragmentSocket socketReference = this.sock;

         try {
            return socketReference.receive();
         } catch (IOException var4) {
            if (socketReference == this.sock) {
               throw var4;
            }

            ++i;
         }
      }

      throw new IOException("unable to receive cluster messages after switching messaging mode");
   }

   public void shutdown() {
      this.sock.shutdown();
   }

   public long getFragmentsSentCount() {
      return this.sock.getFragmentsSentCount();
   }

   public long getFragmentsReceivedCount() {
      return this.sock.getFragmentsReceivedCount();
   }

   public void setPacketDelay(long delay) {
      this.sock.setPacketDelay(delay);
   }

   public void shutdownPermanent() {
      throw new AssertionError("shutdownPermanent should not be called on FragmentSocketWrapper!");
   }

   public void prepareUpdate(BeanUpdateEvent event) {
   }

   public void activateUpdate(BeanUpdateEvent event) {
      DescriptorBean bean = event.getProposedBean();
      if (bean instanceof ClusterMBean) {
         String messagingMode = ((ClusterMBean)bean).getClusterMessagingMode();
         String clusterName = ((ClusterMBean)bean).getName();
         if (!messagingMode.equals(this.currentMessagingMode)) {
            this.switchMessagingMode(clusterName, messagingMode);
         }
      }

   }

   private void switchMessagingMode(String clusterName, String newMode) {
      this.debug("switching from " + this.currentMessagingMode + " to " + newMode);
      FragmentSocket oldSock = this.sock;

      try {
         if ("unicast".equals(newMode)) {
            this.sock = this.getUnicastFragmentSocket();
            this.sock.start();
            this.debug("unicast mode started");
         } else {
            this.sock = new MulticastFragmentSocket(clusterName, this.mcastaddr, this.interfaceAddress, this.port, this.ttl, this.packetDelay, this.bufSize);
            this.sock.start();
            this.debug("multicast mode started");
         }
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.currentMessagingMode = newMode;
      oldSock.shutdownPermanent();
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private void debug(String s) {
      System.out.println("[FragmentSocketWrapper] " + s);
   }
}
