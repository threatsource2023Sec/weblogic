package weblogic.management.descriptors.weblogic;

public interface StatelessSessionDescriptorMBean extends WeblogicBeanDescriptorMBean {
   PoolMBean getPool();

   void setPool(PoolMBean var1);

   StatelessClusteringMBean getStatelessClustering();

   void setStatelessClustering(StatelessClusteringMBean var1);
}
