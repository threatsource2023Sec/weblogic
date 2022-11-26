package weblogic.j2ee.descriptor.wl;

public interface EntityClusteringBean {
   boolean isHomeIsClusterable();

   void setHomeIsClusterable(boolean var1);

   String getHomeLoadAlgorithm();

   void setHomeLoadAlgorithm(String var1);

   String getHomeCallRouterClassName();

   void setHomeCallRouterClassName(String var1);

   boolean isUseServersideStubs();

   void setUseServersideStubs(boolean var1);

   String getId();

   void setId(String var1);
}
