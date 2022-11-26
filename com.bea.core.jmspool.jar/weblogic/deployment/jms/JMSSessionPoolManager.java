package weblogic.deployment.jms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.XASession;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class JMSSessionPoolManager {
   private static HashMap poolManagers = new HashMap();
   private static final String TIMER_MANAGER_NAME = "weblogic.deployment.jms";
   private HashMap pools = new HashMap();
   private TimerManager timerManager = TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.deployment.jms");
   private WorkManager workManager = WorkManagerFactory.getInstance().getSystem();
   private ComponentInvocationContext mycic = null;

   private JMSSessionPoolManager(ComponentInvocationContext cic) {
      this.mycic = cic;
   }

   public static JMSSessionPoolManager getSessionPoolManager() {
      String partitionName = "DOMAIN";
      ComponentInvocationContextManager cim = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = cim.getCurrentComponentInvocationContext();
      if (cic != null) {
         partitionName = cic.getPartitionName();
      }

      Class var3 = JMSSessionPoolManager.class;
      synchronized(JMSSessionPoolManager.class) {
         JMSSessionPoolManager manager = (JMSSessionPoolManager)poolManagers.get(partitionName);
         if (manager == null) {
            manager = new JMSSessionPoolManager(cic);
            poolManagers.put(partitionName, manager);
         }

         return manager;
      }
   }

   public static void removeSessionPoolManager(String partitionName, boolean force) {
      Class var3 = JMSSessionPoolManager.class;
      JMSSessionPoolManager pm;
      synchronized(JMSSessionPoolManager.class) {
         pm = (JMSSessionPoolManager)poolManagers.remove(partitionName);
      }

      if (pm != null) {
         Iterator var9 = pm.getSessionPools().keySet().iterator();

         while(var9.hasNext()) {
            Object pn = var9.next();

            try {
               pm.destroyPool((String)pn, 0L, force);
            } catch (Exception var8) {
               JMSException je;
               if (var8 instanceof JMSException) {
                  je = (JMSException)var8;
               } else {
                  je = new JMSException(var8.getMessage());
                  je.setLinkedException(var8);
               }

               JMSPoolLogger.logJMSSessionPoolCloseError(JMSPoolDebug.getWholeJMSException(je));
            }
         }

      }
   }

   public static void destroySessionPool(String partitionName, String poolName, boolean force) throws JMSException {
      Class var4 = JMSSessionPoolManager.class;
      JMSSessionPoolManager pm;
      synchronized(JMSSessionPoolManager.class) {
         pm = (JMSSessionPoolManager)poolManagers.remove(partitionName);
      }

      if (pm != null) {
         pm.destroyPool(poolName, 0L, force);
      }
   }

   public HashMap getSessionPools() {
      HashMap jmsSessionPools = new HashMap();
      Iterator it = this.pools.keySet().iterator();

      while(it.hasNext()) {
         String poolName = (String)it.next();
         PoolEntry pool = (PoolEntry)this.pools.get(poolName);
         if (pool != null) {
            JMSSessionPool jmsp = pool.getPool();
            if (jmsp != null) {
               jmsSessionPools.put(poolName, jmsp);
            }
         }
      }

      return jmsSessionPools;
   }

   private PoolEntry getPoolEntry(String poolName) {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Lookup JMSSessionPool " + poolName + "[cic=" + this.mycic + "]");
      }

      PoolEntry entry = (PoolEntry)this.pools.get(poolName);
      if (entry == null) {
         entry = new PoolEntry();
         this.pools.put(poolName, entry);
      }

      return entry;
   }

   public synchronized JMSSessionPool findOrCreate(String poolName, Map poolProps, WrappedClassManager wrapperManager) throws JMSException {
      PoolEntry entry = this.getPoolEntry(poolName);
      JMSSessionPool pool = entry.getPool();
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("findOrCreate() pool: " + poolName + " pool: " + pool);
      }

      if (pool != null && pool.isClosed()) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("findOrCreate() pool " + poolName + " is closed  clean it up");
         }

         this.poolDestroyed(pool);
         pool = null;
      }

      if (pool == null) {
         pool = this.createSessionPool(poolName, poolProps, wrapperManager);
         entry.setPool(pool);
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Created new JMSSessionPool named " + poolName);
         }
      }

      return pool;
   }

   public synchronized JMSSessionPool find(String poolName) throws JMSException {
      PoolEntry entry = this.getPoolEntry(poolName);
      JMSSessionPool pool = entry.getPool();
      if (pool != null) {
         return pool;
      } else {
         throw new JMSException("JMS session pool " + poolName + " not found");
      }
   }

   public void destroyPoolUncalled(String poolName, long waitTime) throws JMSException {
      this.destroyPool(poolName, waitTime, false);
   }

   public void destroyPool(String poolName, long waitTime, boolean force) throws JMSException {
      PoolEntry entry = (PoolEntry)this.pools.get(poolName);
      if (entry != null) {
         JMSSessionPool pool = entry.getPool();
         if (pool != null) {
            if (JMSPoolDebug.logger.isDebugEnabled()) {
               JMSPoolDebug.logger.debug("JMSSessoinPoolManager.destroyPool(poolName=" + poolName + ", waitTime=" + waitTime + ", force=" + force + ")[" + this + "]");
            }

            try {
               pool.doClose(waitTime, force);
            } catch (JMSException var8) {
               JMSPoolLogger.logJMSSessionPoolCloseError(var8.toString());
            }
         }

         this.pools.remove(poolName);
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Destroyed JMSSessionPool " + poolName);
         }

      }
   }

   public synchronized String getDebugState() {
      StringBuffer buf = new StringBuffer();
      Iterator keys = this.pools.keySet().iterator();

      while(keys.hasNext()) {
         String key = (String)keys.next();
         buf.append("** JMSSessionPool ");
         buf.append(key);
         buf.append('\n');
         PoolEntry entry = (PoolEntry)this.pools.get(key);
         buf.append(entry.toString());
      }

      return buf.toString() + "[cic=" + this.mycic + "]";
   }

   public String toString() {
      return "pools@" + this.pools.hashCode() + "[cic=" + this.mycic + "]";
   }

   protected synchronized void poolDestroyed(JMSSessionPool pool) {
      PoolEntry entry = (PoolEntry)this.pools.get(pool.getName());
      if (entry != null && entry.getPool() == pool) {
         entry.setPool((JMSSessionPool)null);
         if (entry.getReferenceCount() <= 0) {
            this.pools.remove(pool.getName());
         }
      }

   }

   public static MDBSession getWrappedMDBSession(Session origSession, int sessionType, WrappedClassManager manager) throws JMSException {
      byte wrappedClass;
      switch (sessionType) {
         case 1:
            wrappedClass = 4;
            break;
         case 2:
            wrappedClass = 5;
            break;
         default:
            wrappedClass = 3;
      }

      MDBSession ret = (MDBSession)manager.getWrappedInstance(wrappedClass, origSession);
      ret.init(origSession, 3, manager);
      return ret;
   }

   public static WrappedTransactionalSession getWrappedMDBPollerSession(Session origSession, XASession xaSession, int sessionType, boolean hasNativeTransactions, String resourceName, WrappedClassManager manager) throws JMSException {
      byte wrappedClass;
      switch (sessionType) {
         case 1:
            wrappedClass = 7;
            break;
         case 2:
            wrappedClass = 8;
            break;
         default:
            wrappedClass = 6;
      }

      WrappedTransactionalSession ret = (WrappedTransactionalSession)manager.getWrappedInstance(wrappedClass, origSession);
      ret.init(origSession, xaSession, 0, manager, resourceName, hasNativeTransactions);
      return ret;
   }

   public synchronized void incrementReferenceCount(String poolName) {
      PoolEntry entry = this.getPoolEntry(poolName);
      int newCount = entry.incrementReferenceCount();
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Reference count for JMSSessionPool " + poolName + " incremented to " + newCount + "[cic=" + this.mycic + "]");
      }

   }

   public synchronized void decrementReferenceCount(String poolName, long waitTime) throws JMSException {
      this.decrementReferenceCount(poolName, waitTime, false);
   }

   public synchronized void decrementReferenceCount(String poolName, long waitTime, boolean force) throws JMSException {
      PoolEntry entry = this.getPoolEntry(poolName);
      int newCount = entry.decrementReferenceCount();
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Reference count for JMSSessionPool " + poolName + " decremented to " + newCount + "[cic=" + this.mycic + "]");
      }

      if (newCount <= 0) {
         this.destroyPool(poolName, waitTime, force);
      }

   }

   private JMSSessionPool createSessionPool(String poolName, Map poolProps, WrappedClassManager wrapperManager) throws JMSException {
      return new JMSSessionPool(this, poolName, poolProps, wrapperManager);
   }

   TimerManager getTimerManager() {
      return this.timerManager;
   }

   WorkManager getWorkManager() {
      return this.workManager;
   }

   protected synchronized boolean markPoolClosing(JMSSessionPool pool) {
      return pool.markClosing();
   }

   protected class PoolEntry {
      private int refCount;
      private JMSSessionPool pool;

      protected JMSSessionPool getPool() {
         return this.pool;
      }

      protected void setPool(JMSSessionPool p) {
         this.pool = p;
      }

      protected synchronized int incrementReferenceCount() {
         return ++this.refCount;
      }

      protected synchronized int decrementReferenceCount() {
         return --this.refCount;
      }

      protected synchronized void setReferenceCount(int newCount) {
         this.refCount = newCount;
      }

      protected synchronized int getReferenceCount() {
         return this.refCount;
      }

      public synchronized String toString() {
         StringBuffer buf = new StringBuffer();
         buf.append("  Reference count = ");
         buf.append(String.valueOf(this.refCount));
         buf.append('\n');
         if (this.pool != null) {
            buf.append(this.pool.toString());
         }

         return buf.toString();
      }
   }
}
