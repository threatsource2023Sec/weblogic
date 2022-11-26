package weblogic.transaction.cdi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.TransactionScoped;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.xa.Xid;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;

public class TransactionScopedContextImpl implements Context {
   public static final String TRANSACTION_SYNCHRONIZATION_REGISTRY_JNDI_NAME = "java:comp/TransactionSynchronizationRegistry";

   public Class getScope() {
      return TransactionScoped.class;
   }

   public Object get(Contextual contextual, CreationalContext creationalContext) {
      TransactionSynchronizationRegistry transactionSynchronizationRegistry = this.getTransactionSynchronizationRegistry();
      Object beanId = this.getContextualId(contextual);
      Object contextualInstance = this.getContextualInstance(beanId, transactionSynchronizationRegistry);
      if (contextualInstance == null) {
         contextualInstance = this.createContextualInstance(contextual, beanId, creationalContext, transactionSynchronizationRegistry);
      }

      return contextualInstance;
   }

   public Object get(Contextual contextual) {
      TransactionSynchronizationRegistry transactionSynchronizationRegistry = this.getTransactionSynchronizationRegistry();
      Object beanKey = this.getContextualId(contextual);
      return this.getContextualInstance(beanKey, transactionSynchronizationRegistry);
   }

   public boolean isActive() {
      try {
         if (this.getTransactionSynchronizationRegistry() != null) {
            return true;
         }
      } catch (ContextNotActiveException var2) {
      }

      return false;
   }

   private Object getContextualId(Contextual contextual) {
      if (contextual instanceof PassivationCapable) {
         PassivationCapable passivationCapable = (PassivationCapable)contextual;
         return passivationCapable.getId();
      } else {
         return contextual;
      }
   }

   private Object createContextualInstance(Contextual contextual, Object contextualId, CreationalContext creationalContext, TransactionSynchronizationRegistry transactionSynchronizationRegistry) {
      TransactionScopedBean transactionScopedBean = new TransactionScopedBean(contextual, creationalContext, this);
      transactionSynchronizationRegistry.putResource(contextualId, transactionScopedBean);
      transactionSynchronizationRegistry.registerInterposedSynchronization(transactionScopedBean);
      Transaction currentTrx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
      Xid currentTxXid = currentTrx != null ? currentTrx.getXID() : null;
      Set transactionScopedBeanSet = (Set)transactionSynchronizationRegistry.getResource(currentTxXid);
      if (transactionScopedBeanSet != null) {
         transactionScopedBeanSet = Collections.synchronizedSet(transactionScopedBeanSet);
      } else {
         transactionScopedBeanSet = Collections.synchronizedSet(new HashSet());
         TransactionScopedCDIUtil.fireEvent("INITIALIZED_EVENT");
      }

      transactionScopedBeanSet.add(transactionScopedBean);
      transactionSynchronizationRegistry.putResource(currentTxXid, transactionScopedBeanSet);
      return transactionScopedBean.getContextualInstance();
   }

   private Object getContextualInstance(Object beanId, TransactionSynchronizationRegistry transactionSynchronizationRegistry) {
      Object obj = transactionSynchronizationRegistry.getResource(beanId);
      TransactionScopedBean transactionScopedBean = (TransactionScopedBean)obj;
      return transactionScopedBean != null ? transactionScopedBean.getContextualInstance() : null;
   }

   private TransactionSynchronizationRegistry getTransactionSynchronizationRegistry() {
      TransactionSynchronizationRegistry transactionSynchronizationRegistry;
      try {
         InitialContext initialContext = new InitialContext();
         transactionSynchronizationRegistry = (TransactionSynchronizationRegistry)initialContext.lookup("java:comp/TransactionSynchronizationRegistry");
      } catch (NamingException var3) {
         throw new ContextNotActiveException("Could not get TransactionSynchronizationRegistry", var3);
      }

      int status = transactionSynchronizationRegistry.getTransactionStatus();
      if (status != 0 && status != 1 && status != 2 && status != 5 && status != 7 && status != 8 && status != 9) {
         throw new ContextNotActiveException("TransactionSynchronizationRegistry status is not active.");
      } else {
         return transactionSynchronizationRegistry;
      }
   }
}
