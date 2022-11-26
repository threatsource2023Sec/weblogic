package weblogic.cluster.leasing.databaseless;

import weblogic.cluster.messaging.internal.ServerInformation;

public interface DisconnectActionListener {
   void OnBecomingSeniorMostMember();

   void OnLosingServerReachabilityMajority();

   void onLosingLeader();

   void onLosingMember(ServerInformation var1);
}
