package weblogic.j2ee.descriptor.wl;

public interface StatelessSessionDescriptorBean {
   PoolBean getPool();

   boolean isPoolSet();

   TimerDescriptorBean getTimerDescriptor();

   boolean isTimerDescriptorSet();

   StatelessClusteringBean getStatelessClustering();

   boolean isStatelessClusteringSet();

   /** @deprecated */
   @Deprecated
   BusinessInterfaceJndiNameMapBean[] getBusinessInterfaceJndiNameMaps();

   /** @deprecated */
   @Deprecated
   BusinessInterfaceJndiNameMapBean createBusinessInterfaceJndiNameMap();

   /** @deprecated */
   @Deprecated
   void destroyBusinessInterfaceJndiNameMap(BusinessInterfaceJndiNameMapBean var1);

   /** @deprecated */
   @Deprecated
   BusinessInterfaceJndiNameMapBean lookupBusinessInterfaceJndiNameMap(String var1);

   String getId();

   void setId(String var1);
}
