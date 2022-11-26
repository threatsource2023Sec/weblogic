package weblogic.jms.adapter;

import javax.resource.ResourceException;
import javax.resource.spi.EISSystemException;
import weblogic.jms.JMSLogger;
import weblogic.jms.bridge.AdapterConnection;

public class LocalTransactionImpl {
   private JMSManagedConnection mc;

   public LocalTransactionImpl(JMSManagedConnection mc) {
      this.mc = mc;
   }

   public void begin() throws ResourceException {
      try {
         AdapterConnection con = this.mc.getJMSBaseConnection();
         ((JMSBaseConnection)con).createTransactedSession();
         this.mc.sendEvent(2, (Exception)null);
      } catch (Exception var3) {
         JMSLogger.logStackTrace(var3);
         ResourceException re = new EISSystemException("Failed to begin a local transaction");
         re.setLinkedException(var3);
         throw re;
      }
   }

   public void commit() throws ResourceException {
      AdapterConnection con = null;

      try {
         con = this.mc.getJMSBaseConnection();
         ((JMSBaseConnection)con).commit();
         this.mc.sendEvent(3, (Exception)null);
      } catch (Exception var4) {
         ResourceException re = new EISSystemException("Failed to commit a local transaction");
         re.setLinkedException(var4);
         throw re;
      }
   }

   public void rollback() throws ResourceException {
      AdapterConnection con = null;

      try {
         con = this.mc.getJMSBaseConnection();
         ((JMSBaseConnection)con).rollback();
         this.mc.sendEvent(4, (Exception)null);
      } catch (Exception var4) {
         ResourceException re = new EISSystemException("Failed to roll back a local transaction");
         re.setLinkedException(var4);
         throw re;
      }
   }
}
