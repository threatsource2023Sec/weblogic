package weblogic.transaction;

import java.io.Serializable;

public interface PeerExchangeTransaction {
   boolean isSSLEnabled();

   int getTimeToLiveSeconds();

   Serializable getProperty(String var1);

   Object getLocalProperty(String var1);
}
