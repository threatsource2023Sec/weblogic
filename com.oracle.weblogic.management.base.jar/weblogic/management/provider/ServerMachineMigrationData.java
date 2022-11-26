package weblogic.management.provider;

import java.io.Serializable;

public interface ServerMachineMigrationData extends Serializable {
   String getServerName();

   String getMachineMigratedTo();
}
