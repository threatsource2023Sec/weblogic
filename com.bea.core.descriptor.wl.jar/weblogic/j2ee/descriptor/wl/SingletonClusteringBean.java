package weblogic.j2ee.descriptor.wl;

public interface SingletonClusteringBean {
   boolean isUseServersideStubs();

   void setUseServersideStubs(boolean var1);

   boolean isSingletonBeanIsClusterable();

   void setSingletonBeanIsClusterable(boolean var1);

   String getSingletonBeanLoadAlgorithm();

   void setSingletonBeanLoadAlgorithm(String var1);

   String getSingletonBeanCallRouterClassName();

   void setSingletonBeanCallRouterClassName(String var1);

   String getId();

   void setId(String var1);
}
