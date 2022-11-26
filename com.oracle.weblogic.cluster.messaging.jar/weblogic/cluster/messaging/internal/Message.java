package weblogic.cluster.messaging.internal;

import java.io.Externalizable;

public interface Message extends Externalizable {
   String PROTOCOL_NAME = "CLUSTER-BROADCAST";
   String SSL_PROTOCOL_NAME = "CLUSTER-BROADCAST-SECURE";
   int HEADER_LENGTH = "CLUSTER-BROADCAST".length() + 4;
   int SSL_HEADER_LENGTH = "CLUSTER-BROADCAST-SECURE".length() + 4;
   String VERSION = "9.5";

   String getVersion();

   String getServerName();

   long getServerStartTime();

   ServerConfigurationInformation getSenderConfiguration();

   ServerConfigurationInformation getForwardingServer();

   byte[] getData();
}
