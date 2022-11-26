package weblogic.servlet.spi;

import weblogic.application.AppClassLoaderManager;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesInvocationContext;
import weblogic.protocol.ServerChannel;

public interface ClusterProvider {
   void start();

   void stop();

   String getHash();

   String[] getClusterList(ServerChannel var1);

   boolean shouldDetectSessionCompatiblity();

   String getCompatibleFailoverServerList(ServerChannel var1, String var2);

   ClusterServices getClusterService();

   AppClassLoaderManager getApplicationClassLoaderManager();

   ClusterServicesInvocationContext getClusterServicesInvocationContext();
}
