package weblogic.ejb;

import java.io.IOException;
import java.io.ObjectInputStream;
import javax.ejb.EJBException;
import javax.ejb.EnterpriseBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import weblogic.ejb.container.internal.TransactionService;
import weblogic.logging.LogOutputStream;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionManager;

public abstract class GenericEnterpriseBean implements EnterpriseBean {
   private static final long serialVersionUID = 2011822578998863408L;
   private transient Context ic;
   private boolean tracingEnabled = false;
   private final String beanClassName = this.getClass().getName();
   private transient LogOutputStream log;

   public GenericEnterpriseBean() {
      this.log = new LogOutputStream(this.beanClassName);

      try {
         this.ic = new InitialContext();
      } catch (NamingException var4) {
         throw new EJBException(var4);
      }

      try {
         Boolean b = (Boolean)this.ic.lookup("java:/comp/env/_WL_TracingEnabled");
         this.tracingEnabled = b;
      } catch (NamingException var2) {
         this.tracingEnabled = false;
      } catch (ClassCastException var3) {
         throw new EJBException("_WL_TracingEnabled was found in your  environment entries, but it's type was not Boolean. Please modify your ejb-jar.xml and change the env-entry-type to be java.lang.Boolean.");
      }

   }

   protected final boolean isTracingEnabled() {
      return this.tracingEnabled;
   }

   protected void setTracingEnabled(boolean b) {
      this.tracingEnabled = b;
   }

   private String buildTraceString(String methodName) {
      return "Method: " + methodName + " called in bean class: " + this.beanClassName + " SystemIdentityHashCode: " + System.identityHashCode(this) + " Trasaction ID: " + this.getCurrentXID();
   }

   protected void trace(String methodName) {
      this.logDebugMessage(this.buildTraceString(methodName));
   }

   protected void trace(String methodName, Object pk) {
      this.logDebugMessage(this.buildTraceString(methodName) + " Primary Key: " + pk);
   }

   protected void logDebugMessage(String msg) {
      this.log.debug(msg);
   }

   protected void logDebugMessage(String msg, Throwable th) {
      this.log.debug(msg, th);
   }

   protected void logErrorMessage(String msg) {
      this.log.error(msg);
   }

   protected void logErrorMessage(String msg, Throwable th) {
      this.log.error(msg, th);
   }

   protected Context getInitialContext() {
      return this.ic;
   }

   protected Object getEnvEntry(String name) {
      try {
         return this.ic.lookup("java:comp/env/" + name);
      } catch (NamingException var3) {
         throw new EJBException(var3);
      }
   }

   protected Transaction getCurrentTransaction() {
      return TransactionService.getWeblogicTransaction();
   }

   protected String getCurrentXID() {
      Transaction tx = this.getCurrentTransaction();
      return tx == null ? TransactionService.status2String(6) : tx.getXID().toString();
   }

   protected TransactionManager getTransactionManager() {
      return TransactionService.getWeblogicTransactionManager();
   }

   protected String getCurrentTransactionStatus() {
      Transaction tx = TransactionService.getWeblogicTransaction();
      if (tx == null) {
         return TransactionService.status2String(6);
      } else {
         try {
            return TransactionService.status2String(tx.getStatus());
         } catch (SystemException var3) {
            throw new EJBException(var3);
         }
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();

      try {
         this.ic = new InitialContext();
      } catch (NamingException var3) {
         throw new EJBException(var3);
      }

      this.log = new LogOutputStream(this.beanClassName);
   }
}
