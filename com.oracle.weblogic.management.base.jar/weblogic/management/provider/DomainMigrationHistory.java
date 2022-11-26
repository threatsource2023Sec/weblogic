package weblogic.management.provider;

import java.rmi.Remote;
import weblogic.management.runtime.MigrationDataRuntimeMBean;
import weblogic.management.runtime.ServiceMigrationDataRuntimeMBean;

public interface DomainMigrationHistory extends Remote {
   void update(MigrationData var1);

   MigrationDataRuntimeMBean[] getMigrationDataRuntimes();

   ServiceMigrationDataRuntimeMBean[] getServiceMigrationDataRuntimes();

   ServerMachineMigrationData[] getServerMachineMigrations();
}
