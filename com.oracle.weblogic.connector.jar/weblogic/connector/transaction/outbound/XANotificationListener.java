package weblogic.connector.transaction.outbound;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import weblogic.connector.common.Debug;
import weblogic.connector.outbound.ConnectionHandler;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.transaction.BeginNotificationListener;
import weblogic.transaction.ServerTransactionManager;
import weblogic.transaction.TransactionHelper;

public final class XANotificationListener implements BeginNotificationListener {
   private static XANotificationListener xaNotifListenerSingleton = new XANotificationListener();
   private boolean jtaRegistered = false;
   private int registedCount = 0;
   private AuditableThreadLocal xaThreadLocal = AuditableThreadLocalFactory.createThreadLocal(new XANotificationThreadLocal());

   static final XANotificationListener getInstance() {
      return xaNotifListenerSingleton;
   }

   public void beginNotification(Object unused) throws NotSupportedException, SystemException {
      if (!this.getRegistedHandlers().isEmpty()) {
         String exMsg = Debug.getExceptionXAStartInLocalTxIllegal();
         throw new NotSupportedException(exMsg);
      }
   }

   void registerNotification(ConnectionHandler connHandler) {
      synchronized(xaNotifListenerSingleton) {
         if (!this.jtaRegistered) {
            ((ServerTransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerBeginNotificationListener(xaNotifListenerSingleton, (Object)null);
            this.jtaRegistered = true;
         }

         ++this.registedCount;
      }

      this.getRegistedHandlers().add(connHandler);
   }

   void deregisterNotification(ConnectionHandler connHandler) {
      List connHandlers = this.getRegistedHandlers();

      for(int idx = 0; idx < connHandlers.size(); ++idx) {
         if (connHandlers.get(idx) == connHandler) {
            connHandlers.remove(idx);
            synchronized(xaNotifListenerSingleton) {
               --this.registedCount;
               this.unregisterFromJTAIfNeeded();
               break;
            }
         }
      }

   }

   private void unregisterFromJTAIfNeeded() {
      if (this.jtaRegistered && this.registedCount <= 0) {
         ((ServerTransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterBeginNotificationListener(xaNotifListenerSingleton);
         this.jtaRegistered = false;
         this.registedCount = 0;
      }

   }

   private List getRegistedHandlers() {
      return (List)this.xaThreadLocal.get();
   }

   private class XANotificationThreadLocal extends ThreadLocalInitialValue {
      private XANotificationThreadLocal() {
      }

      protected final Object initialValue() {
         return new ArrayList();
      }

      protected final Object resetValue(Object currentValue) {
         ArrayList list = (ArrayList)currentValue;
         if (!list.isEmpty()) {
            synchronized(XANotificationListener.xaNotifListenerSingleton) {
               XANotificationListener.this.registedCount = XANotificationListener.this.registedCount - list.size();
               XANotificationListener.this.unregisterFromJTAIfNeeded();
            }

            list.clear();
         }

         return list;
      }

      // $FF: synthetic method
      XANotificationThreadLocal(Object x1) {
         this();
      }
   }
}
