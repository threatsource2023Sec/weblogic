package weblogic.jms.safclient.transaction.jta;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.transaction.HeuristicMixedException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.transaction.CoordinatorService;
import weblogic.transaction.TransactionInterceptor;
import weblogic.transaction.UserTransaction;
import weblogic.transaction.loggingresource.LoggingResource;
import weblogic.transaction.nonxa.NonXAResource;

public class SimpleTransactionManager implements TransactionManager, weblogic.transaction.TransactionManager, UserTransaction {
   private final ThreadLocal tls = new ThreadLocal();
   private final Map transactions = new HashMap();
   private final Map resources = new LinkedHashMap();

   public void registerStaticResource(String name, XAResource xar) throws SystemException {
      throw new SystemException("Not implemented");
   }

   public void registerStaticResource(String name, XAResource xar, Hashtable properties) throws SystemException {
      throw new SystemException("Not implemented");
   }

   public void registerDynamicResource(String name, XAResource xar, Hashtable properties) {
      this.registerResource(name, xar);
   }

   public void registerDynamicResource(String name, XAResource xar) {
      this.registerResource(name, xar);
   }

   public void registerResource(String name, XAResource xar, Hashtable properties, boolean localResourceAssignment) {
      this.registerResource(name, xar);
   }

   public void registerResource(String name, XAResource xar, boolean localResourceAssignment) {
      this.registerResource(name, xar);
   }

   public void registerResource(String name, XAResource xar, Hashtable properties) {
      this.registerResource(name, xar);
   }

   public synchronized void registerResource(String name, XAResource xar) {
      ResourceRec rec = (ResourceRec)this.resources.get(name);
      if (rec == null) {
         rec = new ResourceRec(name, xar);
         this.resources.put(name, rec);
      } else {
         rec.setResource(xar);
      }

   }

   public void registerDynamicResource(String name, NonXAResource nxar) throws SystemException {
      throw new SystemException("Not implemented");
   }

   public void unregisterResource(String name) {
      this.unregisterResource(name, false);
   }

   public synchronized void unregisterResource(String name, boolean blocking) {
      ResourceRec rec = (ResourceRec)this.resources.get(name);
      rec.setResource((XAResource)null);
   }

   public void setTransactionTimeout(int seconds) {
   }

   private SimpleTransaction getTran() {
      return (SimpleTransaction)this.tls.get();
   }

   public Transaction getTransaction() {
      return this.getTran();
   }

   public Transaction getTransaction(Xid xid) {
      return null;
   }

   public int getStatus() {
      SimpleTransaction tran = this.getTran();
      return tran == null ? 6 : tran.getStatus();
   }

   public void setRollbackOnly() {
      SimpleTransaction tran = this.getTran();
      if (tran == null) {
         throw new IllegalStateException();
      } else {
         tran.setRollbackOnly();
      }
   }

   public void begin() throws NotSupportedException {
      this.begin("Client SAF", 0);
   }

   public void begin(String name) throws NotSupportedException {
      this.begin(name, 0);
   }

   public void begin(int timeoutSeconds) throws NotSupportedException {
      this.begin("Client SAF", timeoutSeconds);
   }

   public void begin(Map properties) throws NotSupportedException {
      boolean isTimeoutPropertySet = properties != null && properties.get("transaction-timeout") != null && properties.get("transaction-timeout") instanceof Integer;
      this.begin("Client SAF", isTimeoutPropertySet ? (Integer)properties.get("transaction-timeout") : 0);
   }

   public void begin(String name, int timeoutSeconds) throws NotSupportedException {
      if (this.getTran() != null) {
         throw new NotSupportedException();
      } else {
         SimpleTransaction tran = new SimpleTransaction(name, this);
         this.tls.set(tran);
      }
   }

   public void commit() throws RollbackException, HeuristicMixedException, SystemException {
      SimpleTransaction tran = this.getTran();
      if (tran == null) {
         throw new IllegalStateException();
      } else {
         try {
            tran.commit();
         } finally {
            this.tls.set((Object)null);
         }

      }
   }

   public void rollback() throws SystemException {
      SimpleTransaction tran = this.getTran();
      if (tran == null) {
         throw new IllegalStateException();
      } else {
         try {
            tran.rollback();
         } finally {
            this.tls.set((Object)null);
         }

      }
   }

   public Transaction suspend() throws SystemException {
      SimpleTransaction tran = this.getTran();
      if (tran != null) {
         tran.suspendAll();
         this.tls.set((Object)null);
      }

      return tran;
   }

   public Transaction forceSuspend() {
      SimpleTransaction tran = this.getTran();
      if (tran != null) {
         this.tls.set((Object)null);
      }

      return tran;
   }

   public void resume(Transaction t) throws SystemException, InvalidTransactionException {
      if (this.getTran() != null) {
         throw new IllegalStateException();
      } else {
         try {
            SimpleTransaction tran = (SimpleTransaction)t;
            if (tran.isSuspended()) {
               tran.resumeAll();
            }

            this.tls.set(tran);
         } catch (ClassCastException var3) {
            throw new InvalidTransactionException();
         }
      }
   }

   public void forceResume(Transaction t) {
      if (this.getTran() != null) {
         throw new IllegalStateException();
      } else {
         SimpleTransaction tran = (SimpleTransaction)t;
         this.tls.set(tran);
      }
   }

   public void registerLoggingResourceTransactions(LoggingResource lr) throws SystemException {
      throw new SystemException("Not implemented");
   }

   public void registerFailedLoggingResource(Throwable t) {
   }

   public void registerCoordinatorService(String serviceName, CoordinatorService cs) {
   }

   public TransactionInterceptor getInterceptor() {
      return null;
   }

   synchronized void addTransaction(SimpleTransaction tran) {
      this.transactions.put(tran.getXID(), tran);
   }

   synchronized void removeTransaction(SimpleTransaction tran) {
      this.transactions.remove(tran.getXID());
      this.tls.set((Object)null);
   }

   synchronized ResourceRec findResource(XAResource xar) throws XAException {
      Iterator i = this.resources.values().iterator();

      ResourceRec rec;
      do {
         if (!i.hasNext()) {
            return null;
         }

         rec = (ResourceRec)i.next();
      } while(rec.getResource() != xar && !rec.getResource().isSameRM(xar));

      return rec;
   }

   public void setResourceHealthy(String name) {
   }

   static final class ResourceRec {
      private String name;
      private XAResource resource;

      ResourceRec(String name, XAResource resource) {
         this.name = name;
         this.resource = resource;
      }

      XAResource getResource() {
         return this.resource;
      }

      void setResource(XAResource res) {
         this.resource = res;
      }

      public boolean equals(Object o) {
         if (o == null) {
            return false;
         } else {
            try {
               return this.name.equals(((ResourceRec)o).name);
            } catch (ClassCastException var3) {
               return false;
            }
         }
      }

      public int hashCode() {
         return this.name.hashCode();
      }
   }
}
