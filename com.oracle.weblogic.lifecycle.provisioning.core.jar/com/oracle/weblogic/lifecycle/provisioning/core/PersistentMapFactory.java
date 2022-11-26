package com.oracle.weblogic.lifecycle.provisioning.core;

import java.util.Objects;
import javax.inject.Inject;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Self;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreException;

public final class PersistentMapFactory implements Factory {
   private final ActiveDescriptor myDescriptor;
   private final PersistentStore persistentStore;

   @Inject
   public PersistentMapFactory(@Self ActiveDescriptor myDescriptor, PersistentStore persistentStore) {
      Objects.requireNonNull(myDescriptor);
      Objects.requireNonNull(persistentStore);
      this.myDescriptor = myDescriptor;
      this.persistentStore = persistentStore;
   }

   public final PersistentMap provide() {
      PersistentMap returnValue = null;
      String name = this.myDescriptor.getName();

      try {
         returnValue = this.persistentStore.createPersistentMap(name);
         return returnValue;
      } catch (PersistentStoreException var4) {
         throw new IllegalStateException(var4);
      }
   }

   public final void dispose(PersistentMap persistentMap) {
   }
}
