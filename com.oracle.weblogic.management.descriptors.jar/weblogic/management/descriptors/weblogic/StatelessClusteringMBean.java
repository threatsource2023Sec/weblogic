package weblogic.management.descriptors.weblogic;

public interface StatelessClusteringMBean extends WeblogicBeanDescriptorMBean {
   boolean getHomeIsClusterable();

   void setHomeIsClusterable(boolean var1);

   void setHomeLoadAlgorithm(String var1);

   String getHomeLoadAlgorithm();

   String getHomeCallRouterClassName();

   void setHomeCallRouterClassName(String var1);

   boolean getUseServersideStubs();

   void setUseServersideStubs(boolean var1);

   boolean getStatelessBeanIsClusterable();

   void setStatelessBeanIsClusterable(boolean var1);

   void setStatelessBeanLoadAlgorithm(String var1);

   String getStatelessBeanLoadAlgorithm();

   String getStatelessBeanCallRouterClassName();

   void setStatelessBeanCallRouterClassName(String var1);

   boolean getStatelessBeanMethodsAreIdempotent();

   void setStatelessBeanMethodsAreIdempotent(boolean var1);
}
