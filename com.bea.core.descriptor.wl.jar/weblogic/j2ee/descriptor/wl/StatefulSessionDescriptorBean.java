package weblogic.j2ee.descriptor.wl;

public interface StatefulSessionDescriptorBean {
   StatefulSessionCacheBean getStatefulSessionCache();

   boolean isStatefulSessionCacheSet();

   String getPersistentStoreDir();

   void setPersistentStoreDir(String var1);

   StatefulSessionClusteringBean getStatefulSessionClustering();

   boolean isStatefulSessionClusteringSet();

   boolean isAllowRemoveDuringTransaction();

   void setAllowRemoveDuringTransaction(boolean var1);

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
