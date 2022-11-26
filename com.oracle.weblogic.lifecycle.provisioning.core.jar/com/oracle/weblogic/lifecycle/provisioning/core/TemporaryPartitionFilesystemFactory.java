package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.WebLogicPartitionProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ApplicationServer;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Partition;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ProxyCtl;
import org.glassfish.hk2.api.messaging.MessageReceiver;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.RuntimeDir;

@MessageReceiver({ProvisioningOperationCompletionEvent.class})
@Service
@Singleton
public class TemporaryPartitionFilesystemFactory implements Factory {
   private final Provider providerRepresentingMe;
   private final WebLogicPartitionProvisioningContext provisioningContext;

   @Inject
   public TemporaryPartitionFilesystemFactory(WebLogicPartitionProvisioningContext provisioningContext, @ApplicationServer @Partition Provider providerRepresentingMe) {
      Objects.requireNonNull(provisioningContext);
      Objects.requireNonNull(providerRepresentingMe);
      this.provisioningContext = provisioningContext;
      this.providerRepresentingMe = providerRepresentingMe;
   }

   @ApplicationServer
   @Partition
   @ProvisioningOperationScoped
   public RuntimeDir provide() {
      String className = TemporaryPartitionFilesystemFactory.class.getName();
      String methodName = "provide";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "provide");
      }

      if (this.provisioningContext == null) {
         throw new IllegalStateException("this.provisioningContext == null");
      } else {
         String partitionName = this.provisioningContext.getPartitionName();
         if (partitionName == null) {
            throw new IllegalStateException("this.provisioningContext.getPartitionName() == null");
         } else {
            TemporaryPartitionFilesystem returnValue;
            try {
               returnValue = new TemporaryPartitionFilesystem(partitionName);
            } catch (IOException var7) {
               throw new IllegalStateException(var7);
            }

            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.exiting(className, "provide", returnValue);
            }

            return returnValue;
         }
      }
   }

   public void provisioningOperationCompleted(@SubscribeTo ProvisioningOperationCompletionEvent event) throws IOException {
      String className = TemporaryPartitionFilesystemFactory.class.getName();
      String methodName = "provisioningOperationCompleted";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "provisioningOperationCompleted", event);
      }

      if (this.providerRepresentingMe != null) {
         Object temporaryPartitionFilesystem = this.providerRepresentingMe.get();
         if (temporaryPartitionFilesystem instanceof ProxyCtl) {
            temporaryPartitionFilesystem = ((ProxyCtl)temporaryPartitionFilesystem).__make();
         }

         if (temporaryPartitionFilesystem instanceof TemporaryPartitionFilesystem) {
            ((TemporaryPartitionFilesystem)temporaryPartitionFilesystem).overlay();
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "provisioningOperationCompleted");
      }

   }

   public void dispose(RuntimeDir temporaryPartitionFilesystem) {
      String className = TemporaryPartitionFilesystemFactory.class.getName();
      String methodName = "dispose";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "dispose", temporaryPartitionFilesystem);
      }

      if (temporaryPartitionFilesystem != null) {
         if (temporaryPartitionFilesystem instanceof ProxyCtl) {
            temporaryPartitionFilesystem = (RuntimeDir)((ProxyCtl)temporaryPartitionFilesystem).__make();
         }

         if (temporaryPartitionFilesystem instanceof TemporaryPartitionFilesystem) {
            try {
               ((TemporaryPartitionFilesystem)temporaryPartitionFilesystem).delete();
            } catch (IOException var6) {
               throw new IllegalStateException(var6);
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "dispose");
      }

   }
}
