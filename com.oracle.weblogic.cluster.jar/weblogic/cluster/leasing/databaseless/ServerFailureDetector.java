package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ServerInformation;

public interface ServerFailureDetector {
   void start(ServerInformation var1, ServerFailureListener var2);

   boolean stop();
}
