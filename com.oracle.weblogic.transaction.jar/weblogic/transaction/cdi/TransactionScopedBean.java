package weblogic.transaction.cdi;

import java.util.Collections;
import java.util.Set;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Synchronization;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.Xid;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;

public class TransactionScopedBean implements Synchronization {
   private Object contextualInstance;
   private Contextual contextual;
   private CreationalContext creationalContext;
   private TransactionScopedContextImpl transactionScopedContext;

   public TransactionScopedBean(Contextual contextual, CreationalContext creationalContext, TransactionScopedContextImpl transactionScopedContext) {
      this.contextual = contextual;
      this.creationalContext = creationalContext;
      this.transactionScopedContext = transactionScopedContext;
      this.contextualInstance = contextual.create(creationalContext);
   }

   public Object getContextualInstance() {
      return this.contextualInstance;
   }

   public void afterCompletion(int i) {
      this.contextual.destroy(this.contextualInstance, this.creationalContext);
   }

   public void beforeCompletion() {
      try {
         TransactionSynchronizationRegistry transactionSynchronizationRegistry = this.getTransactionSynchronizationRegistry();
         Transaction currentTrx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
         Xid currentTxXid = currentTrx.getXID();
         if (transactionSynchronizationRegistry != null) {
            Set transactionScopedBeanSet = (Set)transactionSynchronizationRegistry.getResource(currentTxXid);
            if (transactionScopedBeanSet != null) {
               transactionScopedBeanSet = Collections.synchronizedSet(transactionScopedBeanSet);
               if (transactionScopedBeanSet.contains(this)) {
                  transactionScopedBeanSet.remove(this);
               }

               if (transactionScopedBeanSet.size() == 0) {
                  TransactionScopedCDIUtil.fireEvent("DESTORYED_EVENT");
                  transactionSynchronizationRegistry.putResource(currentTxXid, (Object)null);
               }
            }
         }
      } catch (NamingException var5) {
         TransactionScopedCDIUtil.log("Can't get instance of TransactionSynchronizationRegistry to process TransactionScoped Destroyed CDI Event: " + var5.getMessage());
      }

   }

   private TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() throws NamingException {
      try {
         InitialContext initialContext = new InitialContext();
         TransactionSynchronizationRegistry transactionSynchronizationRegistry = (TransactionSynchronizationRegistry)initialContext.lookup("java:comp/TransactionSynchronizationRegistry");
         return transactionSynchronizationRegistry;
      } catch (NamingException var3) {
         throw var3;
      }
   }
}
