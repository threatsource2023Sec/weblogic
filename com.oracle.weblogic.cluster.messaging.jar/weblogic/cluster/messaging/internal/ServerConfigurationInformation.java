package weblogic.cluster.messaging.internal;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public interface ServerConfigurationInformation extends Comparable, Serializable {
   long serialVersionUID = -5667123813958435540L;

   InetAddress getAddress() throws IOException;

   String getAddressHostname();

   int getPort();

   String getServerName();

   long getCreationTime();

   boolean isUsingSSL();

   ServerNameComponents getNameComponents();

   String getClusterName();

   void refreshAddress();
}
