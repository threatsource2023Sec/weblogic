package weblogic.management.configuration;

@SingleTargetOnly
public interface PathServiceMBean extends DeploymentMBean {
   String getName();

   PersistentStoreMBean getPersistentStore();

   void setPersistentStore(PersistentStoreMBean var1);
}
