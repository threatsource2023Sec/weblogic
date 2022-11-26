package weblogic.connector.common;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.XATerminator;
import javax.resource.spi.work.WorkManager;
import javax.transaction.TransactionSynchronizationRegistry;
import weblogic.connector.extensions.ExtendedBootstrapContext;

@Singleton
public class ManagedBeanProducer {
   private ExtendedBootstrapContext extendedBootstrapContext;
   private ResourceAdapter resourceAdapter;

   @Produces
   @Dependent
   public ExtendedBootstrapContext getExtendedBootstrapContext() {
      return this.extendedBootstrapContext;
   }

   public void setExtendedBootstrapContext(ExtendedBootstrapContext extendedBootstrapContext) {
      this.extendedBootstrapContext = extendedBootstrapContext;
   }

   @Produces
   @Dependent
   public WorkManager getWorkManager() {
      return this.extendedBootstrapContext == null ? null : this.extendedBootstrapContext.getWorkManager();
   }

   @Produces
   @Dependent
   public ResourceAdapter getResourceAdapter() {
      return this.resourceAdapter;
   }

   public void setResourceAdapter(ResourceAdapter resourceAdapter) {
      this.resourceAdapter = resourceAdapter;
   }

   @Produces
   @Dependent
   public XATerminator getXaTerminator() {
      return this.extendedBootstrapContext == null ? null : this.extendedBootstrapContext.getXATerminator();
   }

   @Produces
   @Dependent
   public TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
      return this.extendedBootstrapContext == null ? null : this.extendedBootstrapContext.getTransactionSynchronizationRegistry();
   }
}
