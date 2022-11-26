package weblogic.ejb.container.internal;

import java.security.PrivilegedExceptionAction;
import java.util.LinkedList;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.transaction.TransactionManager;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerImpl;

final class ContinuousJMSMessagePoller extends JMSMessagePoller {
   private static final DebugLogger debugLogger;
   private static final String TRANSACTION_NAME_PREFIX = "ContinuousJMSMessagePoller.";
   private final TransactionManager tranManager = TransactionService.getWeblogicTransactionManager();
   private final String transactionName;
   private final LinkedList supendedChildren = new LinkedList();

   ContinuousJMSMessagePoller(String mdbName, JMSConnectionPoller cp, JMSMessagePoller mp, MessageConsumer consumer, MDListener listener, WorkManager wm, int id) {
      super(mdbName, cp, mp, consumer, listener, wm, id, false);
      this.transactionName = "ContinuousJMSMessagePoller." + mdbName;
   }

   boolean processOneMessage(boolean block, int numMsgsInTran) throws JMSException, NotSupportedException, SystemException, JMSProviderReceiveRuntimeException {
      Message jmsMsg = null;
      int lcv = false;
      int tranTimeoutSeconds = this.getMDListener().getTransactionTimeoutSeconds();
      tranTimeoutSeconds += this.getMessageWaitTimeSeconds();
      Transaction tran = this.connectionPoller.beginTransaction(this.tranManager, this.transactionName, tranTimeoutSeconds);

      try {
         for(int lcv = 0; lcv < numMsgsInTran; ++lcv) {
            try {
               jmsMsg = (Message)this.connectionPoller.doPrivilegedJMSAction(new PrivilegedExceptionAction() {
                  public Message run() throws Exception {
                     return ContinuousJMSMessagePoller.this.getConsumer().receive((long)ContinuousJMSMessagePoller.this.getMessageWaitTimeActual());
                  }
               });
            } catch (JMSException var12) {
               throw var12;
            } catch (Throwable var13) {
               throw new JMSProviderReceiveRuntimeException(var13.getMessage(), var13);
            }

            if (jmsMsg == null) {
               if (lcv > 0) {
                  this.getMDListener().transactionalOnMessage((Message)null, true);
                  tran = null;
               }
               break;
            }

            if (this.getDebugLogger().isDebugEnabled()) {
               this.debug(this + " batched message #" + lcv + ". Message is " + jmsMsg.getJMSMessageID());
            }

            if (this.isParent()) {
               this.wakeUpChildContinuousPoller();
            }

            if (lcv >= numMsgsInTran - 1) {
               this.getMDListener().transactionalOnMessage(jmsMsg, true);
               tran = null;
            } else if (!this.getMDListener().transactionalOnMessage(jmsMsg, false)) {
               tran = null;
               break;
            }
         }
      } finally {
         if (tran != null) {
            tran.rollback();
         }

      }

      return jmsMsg != null;
   }

   private void wakeUpChildContinuousPoller() {
      ContinuousJMSMessagePoller child = (ContinuousJMSMessagePoller)this.getChildFromPool();
      if (child != null) {
         if (this.getDebugLogger().isDebugEnabled()) {
            this.debug("Using daemon thread for child :" + child);
         }

         WorkManagerImpl.executeDaemonTask("ContinuousJMSMessagePoller :" + this.getMDBName(), 5, child);
      }

   }

   void pollForChild() {
      this.pollContinuously();
      ContinuousJMSMessagePoller parentPoller = (ContinuousJMSMessagePoller)this.getParentPoller();
      if (this.getDebugLogger().isDebugEnabled()) {
         this.debug("Adding :" + this + " to suspended list");
      }

      parentPoller.addToSuspendedList(this);
   }

   void pollForParent() {
      this.wakeupSuspendedChildren();
      this.pollContinuously();
   }

   private synchronized void wakeupSuspendedChildren() {
      ContinuousJMSMessagePoller child;
      for(; this.supendedChildren.size() > 0; WorkManagerImpl.executeDaemonTask("ContinuousJMSMessagePoller :" + this.getMDBName(), 5, child)) {
         child = (ContinuousJMSMessagePoller)this.supendedChildren.removeFirst();
         if (this.getDebugLogger().isDebugEnabled()) {
            this.debug("Waking up the suspended child :" + child);
         }
      }

   }

   private synchronized void addToSuspendedList(ContinuousJMSMessagePoller ch) {
      this.supendedChildren.addLast(ch);
   }

   public String toString() {
      return "[Continuous] " + super.toString();
   }

   private void debug(String s) {
      debugLogger.debug("[ContinuousJMSMessagePoller] " + s);
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
   }
}
