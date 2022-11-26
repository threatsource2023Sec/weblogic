package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.beans.factory.ObjectFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.Scope;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleTransactionScope implements Scope {
   public Object get(String name, ObjectFactory objectFactory) {
      ScopedObjectsHolder scopedObjects = (ScopedObjectsHolder)TransactionSynchronizationManager.getResource(this);
      if (scopedObjects == null) {
         scopedObjects = new ScopedObjectsHolder();
         TransactionSynchronizationManager.registerSynchronization(new CleanupSynchronization(scopedObjects));
         TransactionSynchronizationManager.bindResource(this, scopedObjects);
      }

      Object scopedObject = scopedObjects.scopedInstances.get(name);
      if (scopedObject == null) {
         scopedObject = objectFactory.getObject();
         scopedObjects.scopedInstances.put(name, scopedObject);
      }

      return scopedObject;
   }

   @Nullable
   public Object remove(String name) {
      ScopedObjectsHolder scopedObjects = (ScopedObjectsHolder)TransactionSynchronizationManager.getResource(this);
      if (scopedObjects != null) {
         scopedObjects.destructionCallbacks.remove(name);
         return scopedObjects.scopedInstances.remove(name);
      } else {
         return null;
      }
   }

   public void registerDestructionCallback(String name, Runnable callback) {
      ScopedObjectsHolder scopedObjects = (ScopedObjectsHolder)TransactionSynchronizationManager.getResource(this);
      if (scopedObjects != null) {
         scopedObjects.destructionCallbacks.put(name, callback);
      }

   }

   @Nullable
   public Object resolveContextualObject(String key) {
      return null;
   }

   @Nullable
   public String getConversationId() {
      return TransactionSynchronizationManager.getCurrentTransactionName();
   }

   private class CleanupSynchronization extends TransactionSynchronizationAdapter {
      private final ScopedObjectsHolder scopedObjects;

      public CleanupSynchronization(ScopedObjectsHolder scopedObjects) {
         this.scopedObjects = scopedObjects;
      }

      public void suspend() {
         TransactionSynchronizationManager.unbindResource(SimpleTransactionScope.this);
      }

      public void resume() {
         TransactionSynchronizationManager.bindResource(SimpleTransactionScope.this, this.scopedObjects);
      }

      public void afterCompletion(int status) {
         TransactionSynchronizationManager.unbindResourceIfPossible(SimpleTransactionScope.this);
         Iterator var2 = this.scopedObjects.destructionCallbacks.values().iterator();

         while(var2.hasNext()) {
            Runnable callback = (Runnable)var2.next();
            callback.run();
         }

         this.scopedObjects.destructionCallbacks.clear();
         this.scopedObjects.scopedInstances.clear();
      }
   }

   static class ScopedObjectsHolder {
      final Map scopedInstances = new HashMap();
      final Map destructionCallbacks = new LinkedHashMap();
   }
}
