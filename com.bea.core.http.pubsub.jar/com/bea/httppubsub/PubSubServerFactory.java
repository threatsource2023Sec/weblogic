package com.bea.httppubsub;

import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import java.util.Hashtable;

public interface PubSubServerFactory {
   PubSubServer lookupPubSubServer(String var1);

   PubSubServer createPubSubServer(String var1, WeblogicPubsubBean var2);

   PubSubServer createPubSubServer(Hashtable var1);

   void removePubSubServer(PubSubServer var1);

   void removeAllPubSubServers();
}
