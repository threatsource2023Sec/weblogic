package com.bea.httppubsub.internal;

import com.bea.httppubsub.Client;

public interface ClientPersistenceManager {
   PersistedClientRecord fetchClientRecord(Client var1);

   void storeClientRecord(Client var1);

   void init();

   void destroy();
}
