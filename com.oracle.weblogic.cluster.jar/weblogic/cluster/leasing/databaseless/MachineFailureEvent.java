package weblogic.cluster.leasing.databaseless;

import java.util.List;

public interface MachineFailureEvent {
   String getMachineName();

   List getFailedServers();
}
