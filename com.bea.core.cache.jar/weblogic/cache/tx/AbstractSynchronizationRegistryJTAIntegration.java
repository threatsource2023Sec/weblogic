package weblogic.cache.tx;

import javax.transaction.Synchronization;
import javax.transaction.TransactionSynchronizationRegistry;
import weblogic.cache.CacheRuntimeException;

abstract class AbstractSynchronizationRegistryJTAIntegration implements JTAIntegration {
   public int getTransactionStatus() {
      try {
         return this.getSynchronizationRegistry().getTransactionStatus();
      } catch (RuntimeException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new CacheRuntimeException(var3);
      }
   }

   public void registerSynchronization(Synchronization sync) {
      try {
         this.getSynchronizationRegistry().registerInterposedSynchronization(sync);
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new CacheRuntimeException(var4);
      }
   }

   protected abstract TransactionSynchronizationRegistry getSynchronizationRegistry() throws Exception;
}
