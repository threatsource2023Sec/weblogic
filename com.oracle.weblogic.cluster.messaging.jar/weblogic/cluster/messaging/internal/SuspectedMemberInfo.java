package weblogic.cluster.messaging.internal;

import weblogic.protocol.ServerIdentity;

public interface SuspectedMemberInfo {
   String getServerName();

   String getMachineName();

   ServerInformation getServerInformation();

   ServerConfigurationInformation getServerConfigurationInformation();

   int getPort();

   ServerIdentity getServerIdentity();

   boolean hasVoidedSingletonServices();

   void voidedSingletonServices();

   void setSuspectedStartTime(long var1);

   long getSuspectedStartTime();
}
