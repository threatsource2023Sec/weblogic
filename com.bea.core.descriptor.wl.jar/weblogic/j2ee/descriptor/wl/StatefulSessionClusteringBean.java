package weblogic.j2ee.descriptor.wl;

public interface StatefulSessionClusteringBean {
   boolean isHomeIsClusterable();

   void setHomeIsClusterable(boolean var1);

   String getHomeLoadAlgorithm();

   void setHomeLoadAlgorithm(String var1);

   String getHomeCallRouterClassName();

   void setHomeCallRouterClassName(String var1);

   boolean isUseServersideStubs();

   void setUseServersideStubs(boolean var1);

   String getReplicationType();

   void setReplicationType(String var1);

   boolean isPassivateDuringReplication();

   void setPassivateDuringReplication(boolean var1);

   boolean isCalculateDeltaUsingReflection();

   void setCalculateDeltaUsingReflection(boolean var1);

   String getId();

   void setId(String var1);
}
