package weblogic.management.descriptors.weblogic;

public interface StatefulSessionDescriptorMBean extends WeblogicBeanDescriptorMBean {
   StatefulSessionCacheMBean getStatefulSessionCache();

   void setStatefulSessionCache(StatefulSessionCacheMBean var1);

   String getPersistentStoreDir();

   void setPersistentStoreDir(String var1);

   StatefulSessionClusteringMBean getStatefulSessionClustering();

   void setStatefulSessionClustering(StatefulSessionClusteringMBean var1);

   boolean getAllowConcurrentCalls();

   void setAllowConcurrentCalls(boolean var1);

   boolean getAllowRemoveDuringTransaction();

   void setAllowRemoveDuringTransaction(boolean var1);
}
