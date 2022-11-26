package org.apache.openjpa.ee;

import com.ibm.websphere.jtaextensions.SynchronizationCallback;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.NoTransactionException;
import serp.bytecode.BCClass;
import serp.bytecode.Project;

public class WASManagedRuntime extends AbstractManagedRuntime implements ManagedRuntime, Configurable {
   private static final Localizer _loc = Localizer.forPackage(WASManagedRuntime.class);
   private Object _extendedTransaction = null;
   private Method _getGlobalId = null;
   private Method _registerSync = null;
   private OpenJPAConfiguration _conf = null;
   private Log _log = null;
   static final String CLASS = "org.apache.openjpa.ee.WASManagedRuntime$WASSynchronization";
   static final String INTERFACE = "com.ibm.websphere.jtaextensions.SynchronizationCallback";

   public TransactionManager getTransactionManager() throws Exception {
      return new WASTransaction();
   }

   public void setConfiguration(Configuration conf) {
      this._conf = (OpenJPAConfiguration)conf;
      this._log = this._conf.getLog("openjpa.Runtime");
   }

   public void endConfiguration() {
      try {
         Context ctx = new InitialContext();

         try {
            this._extendedTransaction = ctx.lookup("java:comp/websphere/ExtendedJTATransaction");
         } finally {
            ctx.close();
         }

         Class extendedJTATransaction = Class.forName("com.ibm.websphere.jtaextensions.ExtendedJTATransaction");
         Class synchronizationCallback = Class.forName("com.ibm.websphere.jtaextensions.SynchronizationCallback");
         this._registerSync = extendedJTATransaction.getMethod("registerSynchronizationCallbackForCurrentTran", synchronizationCallback);
         this._getGlobalId = extendedJTATransaction.getMethod("getGlobalId", (Class[])null);
      } catch (Exception var7) {
         throw (new InvalidStateException(_loc.get("was-reflection-exception"), var7)).setFatal(true);
      }
   }

   public void startConfiguration() {
   }

   public static void main(String[] args) throws IOException {
      Project project = new Project();
      InputStream in = WASManagedRuntime.class.getClassLoader().getResourceAsStream("org.apache.openjpa.ee.WASManagedRuntime$WASSynchronization".replace('.', '/') + ".class");
      BCClass bcClass = project.loadClass(in);
      String[] interfaces = bcClass.getInterfaceNames();
      if (interfaces != null) {
         for(int i = 0; i < interfaces.length; ++i) {
            if (interfaces[i].equals("com.ibm.websphere.jtaextensions.SynchronizationCallback")) {
               return;
            }
         }
      }

      bcClass.declareInterface("com.ibm.websphere.jtaextensions.SynchronizationCallback");
      bcClass.write();
   }

   public void setRollbackOnly(Throwable cause) throws Exception {
      this.getTransactionManager().getTransaction().setRollbackOnly();
   }

   public Throwable getRollbackCause() throws Exception {
      return null;
   }

   static class WASSynchronization implements SynchronizationCallback {
      Synchronization _sync = null;

      WASSynchronization(Synchronization sync) {
         this._sync = sync;
      }

      public void afterCompletion(int localTransactionId, byte[] globalTransactionId, boolean committed) {
         if (this._sync != null) {
            if (committed) {
               this._sync.afterCompletion(3);
            } else {
               this._sync.afterCompletion(5);
            }
         }

      }

      public void beforeCompletion(int arg0, byte[] arg1) {
         if (this._sync != null) {
            this._sync.beforeCompletion();
         }

      }
   }

   class WASTransaction implements TransactionManager, Transaction {
      public int getStatus() throws SystemException {
         try {
            byte rval;
            if (this.getGlobalId() != null) {
               rval = 0;
            } else {
               rval = 6;
            }

            return rval;
         } catch (Exception var3) {
            throw (new NoTransactionException(WASManagedRuntime._loc.get("was-transaction-id-exception"))).setCause(var3);
         }
      }

      public Transaction getTransaction() throws SystemException {
         return this;
      }

      public void registerSynchronization(Synchronization arg0) throws IllegalStateException, RollbackException, SystemException {
         if (WASManagedRuntime.this._extendedTransaction != null) {
            try {
               WASManagedRuntime.this._registerSync.invoke(WASManagedRuntime.this._extendedTransaction, new WASSynchronization(arg0));
            } catch (Exception var3) {
               throw (new InvalidStateException(WASManagedRuntime._loc.get("was-reflection-exception"))).setCause(var3);
            }
         } else {
            throw new InvalidStateException(WASManagedRuntime._loc.get("was-lookup-error"));
         }
      }

      private byte[] getGlobalId() {
         try {
            return (byte[])((byte[])WASManagedRuntime.this._getGlobalId.invoke(WASManagedRuntime.this._extendedTransaction, (Object[])null));
         } catch (Exception var2) {
            throw (new InvalidStateException(WASManagedRuntime._loc.get("was-reflection-exception"))).setCause(var2);
         }
      }

      public void begin() throws NotSupportedException, SystemException {
         throw new InvalidStateException(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"begin"));
      }

      public void commit() throws HeuristicMixedException, HeuristicRollbackException, IllegalStateException, RollbackException, SecurityException, SystemException {
         throw new InvalidStateException(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"commit"));
      }

      public void resume(Transaction arg0) throws IllegalStateException, InvalidTransactionException, SystemException {
         throw new InvalidStateException(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"resume"));
      }

      public void rollback() throws IllegalStateException, SecurityException, SystemException {
         if (WASManagedRuntime.this._log.isTraceEnabled()) {
            WASManagedRuntime.this._log.trace(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"rollback"));
         }

      }

      public void setRollbackOnly() throws IllegalStateException, SystemException {
         if (WASManagedRuntime.this._log.isTraceEnabled()) {
            WASManagedRuntime.this._log.trace(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"setRollbackOnly"));
         }

      }

      public void setTransactionTimeout(int arg0) throws SystemException {
         throw new InvalidStateException(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"setTransactionTimeout"));
      }

      public Transaction suspend() throws SystemException {
         throw new InvalidStateException(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"suspend"));
      }

      public boolean delistResource(XAResource arg0, int arg1) throws IllegalStateException, SystemException {
         throw new InvalidStateException(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"delistResource"));
      }

      public boolean enlistResource(XAResource arg0) throws IllegalStateException, RollbackException, SystemException {
         throw new InvalidStateException(WASManagedRuntime._loc.get("was-unsupported-op", (Object)"enlistResource"));
      }
   }
}
