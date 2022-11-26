package weblogic.management.configuration;

public interface JTAMigratableTargetMBean extends MigratableTargetMBean {
   void setUserPreferredServer(ServerMBean var1);

   String getMigrationPolicy();

   void setMigrationPolicy(String var1);

   boolean isStrictOwnershipCheck();

   void setStrictOwnershipCheck(boolean var1);
}
