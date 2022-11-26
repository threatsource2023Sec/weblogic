package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.store.PersistentMap;
import weblogic.store.PersistentStoreException;

@Service
@Singleton
public class ProvisioningContextImplSerializer implements ProvisioningContext.Serializer {
   private final ReadWriteLock mapLock;
   private final Provider persistentMapProvider;

   @Inject
   public ProvisioningContextImplSerializer(@Named("com.oracle.weblogic.lifecycle.provisioning.core.PartitionProvisioner") Provider persistentMapProvider) {
      Objects.requireNonNull(persistentMapProvider);
      this.mapLock = new ReentrantReadWriteLock();
      this.persistentMapProvider = persistentMapProvider;
   }

   public ProvisioningContextImpl deserialize(ProvisioningOperation provisioningOperation) throws ProvisioningException {
      String className = ProvisioningContextImplSerializer.class.getName();
      String methodName = "deserialize";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "deserialize", provisioningOperation);
      }

      ProvisioningContextImpl returnValue = null;
      if (provisioningOperation != null && isProvisioningContextSerializationEnabled()) {
         Map properties = provisioningOperation.getProperties();
         if (properties != null) {
            String partitionName = (String)properties.get("wlsPartitionName");
            if (partitionName != null) {
               Object mapContents = null;

               try {
                  this.mapLock.readLock().lock();
                  PersistentMap map = (PersistentMap)this.persistentMapProvider.get();

                  assert map != null : "this.persistentMapProvider.get() == null";

                  mapContents = map.get(partitionName);
               } catch (PersistentStoreException var15) {
                  throw new ProvisioningException(var15);
               } finally {
                  this.mapLock.readLock().unlock();
               }

               if (mapContents != null) {
                  try {
                     returnValue = (ProvisioningContextImpl)mapContents;
                  } catch (ClassCastException var14) {
                     throw new ProvisioningException(var14);
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "deserialize", returnValue);
      }

      return returnValue;
   }

   public void serialize(ProvisioningContextImpl ctx) throws ProvisioningException {
      String className = ProvisioningContextImplSerializer.class.getName();
      String methodName = "serialize";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "serialize", ctx);
      }

      if (ctx != null && isProvisioningContextSerializationEnabled()) {
         ProvisioningOperation provisioningOperation = ctx.getProvisioningOperation();
         String partitionName;
         if (provisioningOperation == null) {
            partitionName = null;
         } else {
            Map properties = provisioningOperation.getProperties();
            if (properties == null) {
               partitionName = null;
            } else {
               partitionName = (String)properties.get("wlsPartitionName");
            }
         }

         if (partitionName != null) {
            try {
               this.mapLock.writeLock().lock();
               PersistentMap map = (PersistentMap)this.persistentMapProvider.get();

               assert map != null : "this.persistentMapProvider.get() == null";

               map.put(partitionName, ctx);
            } catch (PersistentStoreException var11) {
               throw new ProvisioningException(var11);
            } finally {
               this.mapLock.writeLock().unlock();
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "serialize");
      }

   }

   public static final boolean isProvisioningContextSerializationEnabled() {
      String propertyValue = (String)AccessController.doPrivileged(new PrivilegedAction() {
         public final String run() {
            return System.getProperty("disableProvisioningContextSerialization");
         }
      });
      boolean returnValue = propertyValue == null || !"true".equalsIgnoreCase(propertyValue);
      return returnValue;
   }
}
