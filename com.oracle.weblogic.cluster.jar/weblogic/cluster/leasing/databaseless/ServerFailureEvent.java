package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ServerInformation;

public interface ServerFailureEvent {
   ServerInformation getServerInformation();
}
