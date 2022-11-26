package com.bea.core.repackaged.springframework.jca.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Timer;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;

public class SimpleBootstrapContext implements BootstrapContext {
   @Nullable
   private WorkManager workManager;
   @Nullable
   private XATerminator xaTerminator;
   @Nullable
   private TransactionSynchronizationRegistry transactionSynchronizationRegistry;

   public SimpleBootstrapContext(@Nullable WorkManager workManager) {
      this.workManager = workManager;
   }

   public SimpleBootstrapContext(@Nullable WorkManager workManager, @Nullable XATerminator xaTerminator) {
      this.workManager = workManager;
      this.xaTerminator = xaTerminator;
   }

   public SimpleBootstrapContext(@Nullable WorkManager workManager, @Nullable XATerminator xaTerminator, @Nullable TransactionSynchronizationRegistry transactionSynchronizationRegistry) {
      this.workManager = workManager;
      this.xaTerminator = xaTerminator;
      this.transactionSynchronizationRegistry = transactionSynchronizationRegistry;
   }

   public WorkManager getWorkManager() {
      Assert.state(this.workManager != null, "No WorkManager available");
      return this.workManager;
   }

   @Nullable
   public XATerminator getXATerminator() {
      return this.xaTerminator;
   }

   public Timer createTimer() throws UnavailableException {
      return new Timer();
   }

   public boolean isContextSupported(Class workContextClass) {
      return false;
   }

   @Nullable
   public TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
      return this.transactionSynchronizationRegistry;
   }
}
