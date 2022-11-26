package weblogic.j2ee.descriptor.wl;

public interface StatelessClusteringBean {
   boolean isHomeIsClusterable();

   void setHomeIsClusterable(boolean var1);

   String getHomeLoadAlgorithm();

   void setHomeLoadAlgorithm(String var1);

   String getHomeCallRouterClassName();

   void setHomeCallRouterClassName(String var1);

   boolean isUseServersideStubs();

   void setUseServersideStubs(boolean var1);

   boolean isStatelessBeanIsClusterable();

   void setStatelessBeanIsClusterable(boolean var1);

   String getStatelessBeanLoadAlgorithm();

   void setStatelessBeanLoadAlgorithm(String var1);

   String getStatelessBeanCallRouterClassName();

   void setStatelessBeanCallRouterClassName(String var1);

   String getId();

   void setId(String var1);
}
