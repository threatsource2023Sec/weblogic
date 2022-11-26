package weblogic.management.descriptors.weblogic;

public interface EntityClusteringMBean extends WeblogicBeanDescriptorMBean {
   boolean getHomeIsClusterable();

   void setHomeIsClusterable(boolean var1);

   void setHomeLoadAlgorithm(String var1);

   String getHomeLoadAlgorithm();

   String getHomeCallRouterClassName();

   void setHomeCallRouterClassName(String var1);

   boolean getUseServersideStubs();

   void setUseServersideStubs(boolean var1);
}
