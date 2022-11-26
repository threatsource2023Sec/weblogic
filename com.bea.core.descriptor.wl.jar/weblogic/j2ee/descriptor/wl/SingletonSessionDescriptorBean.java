package weblogic.j2ee.descriptor.wl;

public interface SingletonSessionDescriptorBean {
   TimerDescriptorBean getTimerDescriptor();

   boolean isTimerDescriptorSet();

   SingletonClusteringBean getSingletonClustering();

   boolean isSingletonClusteringSet();

   String getId();

   void setId(String var1);
}
