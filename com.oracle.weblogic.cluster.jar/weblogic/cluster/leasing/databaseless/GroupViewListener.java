package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ServerInformation;

public interface GroupViewListener {
   void memberAdded(ServerInformation var1);

   void memberRemoved(ServerInformation var1);
}
