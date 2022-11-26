package weblogic.jms.safclient.transaction.jta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.transaction.HeuristicMixedException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.transaction.nonxa.NonXAResource;

public final class SimpleTransaction implements Transaction, weblogic.transaction.Transaction {
   private String name;
   private SimpleTransactionManager manager;
   private SimpleXid xid = new SimpleXid();
   private int status = 0;
   private boolean suspended;
   private ArrayList resources;
   private LinkedHashSet synchronizations;
   private HashMap properties;
   private Throwable rollbackReason;
   private long startTime;

   SimpleTransaction(String name, SimpleTransactionManager manager) {
      this.name = name;
      this.manager = manager;
      this.startTime = System.currentTimeMillis();
      manager.addTransaction(this);
   }

   public void setName(String n) {
      this.name = n;
   }

   public String getName() {
      return this.name;
   }

   public Xid getXID() {
      return this.xid;
   }

   public Xid getXid() {
      return this.xid;
   }

   public long getMillisSinceBegin() {
      return System.currentTimeMillis() - this.startTime;
   }

   public long getTimeToLiveMillis() {
      return Long.MAX_VALUE;
   }

   public boolean isTimedOut() {
      return false;
   }

   public boolean isTxAsyncTimeout() {
      return false;
   }

   private synchronized void setPropertyInternal(String key, Object value) {
      if (this.properties == null) {
         this.properties = new HashMap();
      }

      this.properties.put(key, value);
   }

   public void setProperty(String key, Serializable value) {
      this.setPropertyInternal(key, value);
   }

   public void setLocalProperty(String key, Object value) {
      this.setPropertyInternal(key, value);
   }

   public synchronized void addProperties(Map props) {
      if (this.properties == null) {
         this.properties = new HashMap();
      }

      this.properties.putAll(props);
   }

   public void addLocalProperties(Map props) {
      this.addProperties(props);
   }

   private synchronized Object getPropertyInternal(String key) {
      return this.properties == null ? null : this.properties.get(key);
   }

   public Serializable getProperty(String key) {
      return (Serializable)this.getPropertyInternal(key);
   }

   public Object getLocalProperty(String key) {
      return this.getPropertyInternal(key);
   }

   public synchronized Map getProperties() {
      return new HashMap(this.properties);
   }

   public Map getLocalProperties() {
      return this.getProperties();
   }

   public synchronized int getStatus() {
      return this.status;
   }

   private synchronized void setStatus(int status) {
      this.status = status;
   }

   synchronized boolean isSuspended() {
      return this.suspended;
   }

   private synchronized void setSuspended(boolean suspended) {
      this.suspended = suspended;
   }

   public synchronized void setRollbackOnly() {
      if (this.status == 0) {
         this.status = 1;
      } else {
         throw new IllegalStateException("Transaction is not active");
      }
   }

   public synchronized void setRollbackOnly(Throwable reason) {
      this.setRollbackOnly();
      this.rollbackReason = reason;
   }

   public synchronized void setRollbackOnly(String msg, Throwable reason) {
      this.setRollbackOnly();
      this.rollbackReason = new Exception(msg, reason);
   }

   public void setRollbackOnly(String msg) {
      this.setRollbackOnly();
      this.rollbackReason = new Exception(msg);
   }

   public synchronized Throwable getRollbackReason() {
      return this.rollbackReason;
   }

   public void commit() throws RollbackException, HeuristicMixedException, SystemException {
      ArrayList syncList = null;
      ArrayList resourceList = null;
      synchronized(this) {
         if (this.status == 1) {
            this.rollback();
            throw new RollbackException();
         }

         if (this.status != 0 && this.status != 2) {
            throw new IllegalStateException("Transaction not active");
         }

         this.status = 7;
         if (this.synchronizations != null) {
            syncList = new ArrayList(this.synchronizations);
         }

         if (this.resources != null) {
            resourceList = new ArrayList(this.resources);
         }
      }

      if (syncList != null) {
         this.beforeCompletion(syncList);
      }

      if (resourceList != null && resourceList.size() > 1) {
         this.driveTwoPhaseCommit(syncList, resourceList);
      } else {
         this.driveOnePhaseCommit(syncList, resourceList);
      }

      this.manager.removeTransaction(this);
   }

   public boolean prepare() throws RollbackException, SystemException {
      ArrayList syncList = null;
      ArrayList resourceList = null;
      synchronized(this) {
         if (this.status == 1) {
            this.rollback();
            throw new RollbackException();
         }

         if (this.status != 0) {
            throw new IllegalStateException("Transaction not active");
         }

         this.status = 7;
         if (this.synchronizations != null) {
            syncList = new ArrayList(this.synchronizations);
         }

         if (this.resources != null) {
            resourceList = new ArrayList(this.resources);
         }
      }

      if (syncList != null) {
         this.beforeCompletion(syncList);
      }

      boolean ret = this.drivePrepare(resourceList);
      this.setStatus(2);
      return ret;
   }

   public void rollback() throws SystemException {
      ArrayList syncList = null;
      ArrayList resourceList = null;
      synchronized(this) {
         if (this.status != 0 && this.status != 1 && this.status != 2) {
            throw new IllegalStateException("Transaction not active");
         }

         this.status = 9;
         if (this.synchronizations != null) {
            syncList = new ArrayList(this.synchronizations);
         }

         if (this.resources != null) {
            resourceList = new ArrayList(this.resources);
         }
      }

      if (this.status != 2 && syncList != null) {
         this.beforeCompletion(syncList);
      }

      this.driveRollback(syncList, resourceList);
      this.manager.removeTransaction(this);
   }

   private void driveOnePhaseCommit(ArrayList syncList, ArrayList resList) throws SystemException {
      this.setStatus(8);
      if (resList != null) {
         Iterator i = resList.iterator();

         while(i.hasNext()) {
            XAResourceHolder holder = (XAResourceHolder)i.next();

            try {
               this.delist(holder);
               holder.getResource().commit(this.xid, true);
            } catch (XAException var7) {
               SystemException se = new SystemException("XA error on commit: " + var7.errorCode);
               se.initCause(var7);
               throw se;
            }
         }
      }

      this.setStatus(3);
      if (syncList != null) {
         this.afterCompletion(syncList, 3);
      }

   }

   private boolean drivePrepare(ArrayList resList) throws SystemException {
      SystemException lastException = null;
      if (resList != null) {
         Iterator i = resList.iterator();

         while(i.hasNext()) {
            XAResourceHolder holder = (XAResourceHolder)i.next();

            try {
               this.delist(holder);
            } catch (XAException var8) {
               lastException = new SystemException("XA error preparing transaction: " + var8.errorCode);
               lastException.initCause(var8);
            }
         }
      }

      if (lastException != null) {
         throw lastException;
      } else {
         boolean prepareFailure = false;
         if (resList != null) {
            Iterator i = resList.iterator();

            while(i.hasNext()) {
               XAResourceHolder holder = (XAResourceHolder)i.next();

               try {
                  if (holder.getResource().prepare(this.xid) == 3) {
                     holder.setReadOnly(true);
                  }
               } catch (XAException var7) {
                  prepareFailure = true;
               }
            }
         }

         return !prepareFailure;
      }
   }

   private void driveTwoPhaseCommit(ArrayList syncList, ArrayList resList) throws SystemException, HeuristicMixedException, RollbackException {
      boolean prepareFailure = !this.drivePrepare(resList);
      if (prepareFailure) {
         this.driveRollback(syncList, resList);
         throw new RollbackException();
      } else {
         XAException lastXAException = null;
         Iterator i = resList.iterator();

         while(i.hasNext()) {
            XAResourceHolder holder = (XAResourceHolder)i.next();

            try {
               if (!holder.isReadOnly()) {
                  holder.getResource().commit(this.xid, false);
               }
            } catch (XAException var8) {
               lastXAException = var8;
            }
         }

         if (lastXAException != null) {
            this.setStatus(5);
            HeuristicMixedException hme = new HeuristicMixedException("Error commiting XA transaction: " + lastXAException.errorCode);
            hme.initCause(lastXAException);
            throw hme;
         } else {
            this.setStatus(3);
            if (syncList != null) {
               this.afterCompletion(syncList, 3);
            }

         }
      }
   }

   private void driveRollback(ArrayList syncList, ArrayList resList) throws SystemException {
      SystemException lastException = null;
      if (resList != null) {
         Iterator i = resList.iterator();

         while(i.hasNext()) {
            XAResourceHolder holder = (XAResourceHolder)i.next();

            try {
               this.delist(holder);
               holder.getResource().rollback(this.xid);
            } catch (XAException var7) {
               lastException = new SystemException("XA error on rollback: " + var7.errorCode);
               lastException.initCause(var7);
            }
         }
      }

      this.setStatus(4);
      if (syncList != null) {
         this.afterCompletion(syncList, 4);
      }

      if (lastException != null) {
         throw lastException;
      }
   }

   private void delist(XAResourceHolder holder) throws XAException {
      if (holder.isEnlisted()) {
         holder.getResource().end(this.xid, 67108864);
         holder.setEnlisted(false);
      } else if (holder.isSuspended()) {
         holder.getResource().end(this.xid, 67108864);
         holder.setSuspended(false);
      }

   }

   private void beforeCompletion(ArrayList syncList) {
      Iterator i = syncList.iterator();

      while(i.hasNext()) {
         Synchronization sync = (Synchronization)i.next();
         sync.beforeCompletion();
      }

   }

   private void afterCompletion(ArrayList syncList, int status) {
      Iterator i = syncList.iterator();

      while(i.hasNext()) {
         Synchronization sync = (Synchronization)i.next();
         sync.afterCompletion(status);
      }

   }

   public boolean enlistResource(XAResource res) throws RollbackException, SystemException {
      int startFlag = 0;
      XAResourceHolder holder;
      synchronized(this) {
         if (this.status == 1) {
            throw new RollbackException();
         }

         if (this.status != 0) {
            throw new IllegalStateException("Transaction not active");
         }

         if (this.resources == null) {
            this.resources = new ArrayList();
         }

         holder = this.findResource(res);
         if (holder == null) {
            SimpleTransactionManager.ResourceRec resRec = null;

            try {
               resRec = this.manager.findResource(res);
            } catch (XAException var9) {
               throw new SystemException(var9.toString());
            }

            if (resRec == null) {
               throw new SystemException("XAResource was not registered");
            }

            holder = new XAResourceHolder(resRec);
            this.resources.add(holder);
         } else {
            if (holder.isEnlisted()) {
               return true;
            }

            if (holder.isSuspended()) {
               startFlag = 134217728;
            }
         }
      }

      try {
         res.start(this.xid, startFlag);
      } catch (XAException var8) {
         SystemException se = new SystemException("XA failure: " + var8.errorCode);
         se.initCause(var8);
         throw se;
      }

      holder.setSuspended(false);
      holder.setEnlisted(true);
      return true;
   }

   public boolean enlistResource(XAResource xaRes, String resourceNameAlias) throws IllegalStateException, SystemException {
      throw new SystemException("Not supported.");
   }

   public boolean enlistResourceWithProperties(XAResource xaRes, Map properties) throws RollbackException, SystemException {
      return this.enlistResource(xaRes);
   }

   public Set getServerParticipantNames() {
      return null;
   }

   public boolean delistResource(XAResource res, int flag) throws SystemException {
      XAResourceHolder holder;
      synchronized(this) {
         if (this.status != 0 && this.status != 1) {
            throw new IllegalStateException("Transaction not active");
         }

         holder = this.findResource(res);
         if (holder == null || !holder.isEnlisted()) {
            throw new IllegalStateException("Resource not enlisted");
         }
      }

      try {
         res.end(this.xid, flag);
      } catch (XAException var6) {
         SystemException se = new SystemException("XA failure: " + var6.errorCode);
         se.initCause(var6);
         throw se;
      }

      holder.setEnlisted(false);
      if (flag == 33554432) {
         holder.setSuspended(true);
      }

      return true;
   }

   void suspendAll() throws SystemException {
      ArrayList resList;
      synchronized(this) {
         resList = this.resources == null ? new ArrayList() : new ArrayList(this.resources);
      }

      SystemException lastException = null;
      Iterator i = resList.iterator();

      while(i.hasNext()) {
         XAResourceHolder holder = (XAResourceHolder)i.next();

         try {
            if (holder.isEnlisted() && !holder.isSuspended()) {
               holder.getResource().end(this.xid, 33554432);
               holder.setEnlisted(false);
               holder.setSuspended(true);
            }
         } catch (XAException var6) {
            lastException = new SystemException("XA error suspending transaction: " + var6.errorCode);
            lastException.initCause(var6);
         }
      }

      this.setSuspended(true);
      if (lastException != null) {
         throw lastException;
      }
   }

   void resumeAll() throws SystemException {
      ArrayList resList;
      synchronized(this) {
         resList = this.resources == null ? new ArrayList() : new ArrayList(this.resources);
      }

      SystemException lastException = null;
      Iterator i = resList.iterator();

      while(i.hasNext()) {
         XAResourceHolder holder = (XAResourceHolder)i.next();

         try {
            if (holder.isSuspended()) {
               holder.getResource().start(this.xid, 134217728);
               holder.setEnlisted(true);
               holder.setSuspended(false);
            }
         } catch (XAException var6) {
            lastException = new SystemException("XA error resuming transaction: " + var6.errorCode);
            lastException.initCause(var6);
         }
      }

      this.setSuspended(false);
      if (lastException != null) {
         throw lastException;
      }
   }

   public synchronized void registerSynchronization(Synchronization sync) throws RollbackException {
      if (this.status == 1) {
         throw new RollbackException();
      } else if (this.status != 0) {
         throw new IllegalStateException("Transaction not active");
      } else {
         if (this.synchronizations == null) {
            this.synchronizations = new LinkedHashSet();
         }

         this.synchronizations.add(sync);
      }
   }

   public String getStatusAsString() {
      switch (this.status) {
         case 0:
            return "Active";
         case 1:
            return "Marked Rollback";
         case 2:
            return "Prepared";
         case 3:
            return "Committed";
         case 4:
            return "Rolled Back";
         case 5:
         default:
            return "Unknown";
         case 6:
            return "No Transaction";
         case 7:
            return "Preparing";
         case 8:
            return "Committing";
         case 9:
            return "Rolling Back";
      }
   }

   public String getHeuristicErrorMessage() {
      return null;
   }

   public Object invokeCoordinatorService(String name, Object data) throws SystemException {
      throw new SystemException("Not implemented");
   }

   public Object invokeCoordinatorService(String name, Object data, boolean useSSL) throws SystemException {
      throw new SystemException("Not implemented");
   }

   public boolean isCoordinatorLocal() {
      return true;
   }

   public boolean isCoordinatorAssigned() {
      return true;
   }

   public boolean enlistResource(NonXAResource nxar) throws SystemException {
      throw new SystemException("Not implemented");
   }

   public boolean delistResourceWithProperties(XAResource xaRes, int flags, Map delistmentProperties) throws IllegalStateException, SystemException {
      throw new SystemException("Not Implemented");
   }

   private synchronized XAResourceHolder findResource(XAResource res) {
      try {
         Iterator i = this.resources.iterator();

         XAResourceHolder holder;
         do {
            if (!i.hasNext()) {
               return null;
            }

            holder = (XAResourceHolder)i.next();
         } while(!holder.isSameRM(res));

         return holder;
      } catch (XAException var4) {
         return null;
      }
   }

   private static final class XAResourceHolder {
      private SimpleTransactionManager.ResourceRec res;
      private boolean suspended;
      private boolean enlisted;
      private boolean readOnly;

      XAResourceHolder(SimpleTransactionManager.ResourceRec res) {
         this.res = res;
      }

      XAResource getResource() {
         return this.res.getResource();
      }

      synchronized boolean isSuspended() {
         return this.suspended;
      }

      synchronized void setSuspended(boolean suspended) {
         this.suspended = suspended;
      }

      synchronized boolean isEnlisted() {
         return this.enlisted;
      }

      synchronized void setEnlisted(boolean enlisted) {
         this.enlisted = enlisted;
      }

      synchronized boolean isReadOnly() {
         return this.readOnly;
      }

      synchronized void setReadOnly(boolean readOnly) {
         this.readOnly = readOnly;
      }

      public boolean isSameRM(XAResource xar) throws XAException {
         return this.res.getResource() == xar || this.res.getResource().isSameRM(xar);
      }
   }
}
