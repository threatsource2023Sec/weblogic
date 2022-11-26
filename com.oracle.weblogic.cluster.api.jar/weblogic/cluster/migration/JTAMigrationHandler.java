package weblogic.cluster.migration;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface JTAMigrationHandler {
   void deactivateJTA(String var1, String var2) throws MigrationException;

   void migrateJTA(String var1, String var2, boolean var3, boolean var4) throws MigrationException;

   boolean isAvailable(String var1);
}
