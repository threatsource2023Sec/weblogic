package weblogic.connector.outbound;

import java.util.HashMap;
import java.util.Iterator;
import javax.transaction.Transaction;
import weblogic.common.ResourceException;
import weblogic.connector.common.Debug;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.StackTraceUtils;

public class ConnectionSharingManager {
   private HashMap transaction2ConnectionMap = new HashMap();
   private HashMap connection2TransactionMap = new HashMap();
   private HashMap releasingConnections = new HashMap();
   private HashMap reservingConnections = new HashMap();
   private String poolName;

   public ConnectionSharingManager(String poolName) {
      this.poolName = poolName;
   }

   public synchronized void addSharedConnection(ConnectionInfo connectionInfo) throws ResourceException {
      if (Debug.verbose) {
         Debug.enter(this, "addSharedConnection()");
      }

      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose("on entering addSharedConnection() ConnectionInfo = " + connectionInfo.toString());
         this.dumpSharedConnectionsTable("On entry to addSharedConnection()");
      }

      try {
         Transaction transaction = TransactionHelper.getTransactionHelper().getTransaction();
         Transaction residualTransaction = null;
         ConnectionInfo tmpConnectionInfo = null;
         if (transaction != null) {
            if (Debug.isPoolVerboseEnabled()) {
               this.debugVerbose("tx is non-null, tx.hashcode() = " + transaction.hashCode() + ", txid = " + ((weblogic.transaction.Transaction)transaction).getXID() + " -- adding to sharedConnections");
            }

            tmpConnectionInfo = (ConnectionInfo)this.transaction2ConnectionMap.get(transaction);
            if (tmpConnectionInfo == null) {
               residualTransaction = (Transaction)this.connection2TransactionMap.get(connectionInfo);
               if (residualTransaction != null) {
                  if (Debug.isPoolVerboseEnabled()) {
                     this.debugVerbose("Removing residual tx, tx.hashcode() = " + transaction.hashCode() + ", txid = " + ((weblogic.transaction.Transaction)residualTransaction).getXID());
                  }

                  this.transaction2ConnectionMap.remove(residualTransaction);
                  this.connection2TransactionMap.remove(connectionInfo);
               }

               this.transaction2ConnectionMap.put(transaction, connectionInfo);
               this.connection2TransactionMap.put(connectionInfo, transaction);
            } else if (tmpConnectionInfo.equals(connectionInfo)) {
               this.debugVerbose("*** attempt to add same connection/tx again -- doing nothing***");
            } else {
               this.debugVerbose("*** new connection created with tx which already had a conneciton assoc. w/it.  Shouldn't happen!!!");
            }
         } else if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("tx is null - not doing anything.");
         }

         if (Debug.isPoolVerboseEnabled()) {
            this.dumpSharedConnectionsTable("On exit from addSharedConnection()");
         }
      } finally {
         if (Debug.verbose) {
            Debug.exit(this, "addSharedConnection()");
         }

      }

   }

   public synchronized boolean releaseSharedConnection(ConnectionInfo connectionInfo) {
      if (Debug.verbose) {
         Debug.enter(this, "releaseSharedConnection()");
      }

      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose("on entering releaseSharedConnection() ConnectionInfo = " + connectionInfo.toString());
      }

      String threadInfo = (String)this.reservingConnections.get(connectionInfo);
      if (threadInfo != null) {
         Debug.concurrentlogger.debug("ConnectionSharingManager.releaseSharedConnection (ConnectionInfo = " + connectionInfo + ") will just return false because Connection is being reserving in another thread " + threadInfo, new Throwable("releaseSharedConnection in " + Thread.currentThread()));
         return false;
      } else if (connectionInfo.isInTransaction()) {
         if (Debug.verbose) {
            Debug.exit(this, "releaseSharedConnection() return false. The connection is still in a transaction");
         }

         return false;
      } else {
         Transaction transaction = (Transaction)this.connection2TransactionMap.get(connectionInfo);
         if (transaction != null) {
            if (Debug.isPoolVerboseEnabled()) {
               this.dumpSharedConnectionsTable("Before Release");
               this.debugVerbose("removing transaction from sharedConnections and releasing back to pool");
            }

            this.transaction2ConnectionMap.remove(transaction);
            this.connection2TransactionMap.remove(connectionInfo);
            if (Debug.isPoolVerboseEnabled()) {
               this.dumpSharedConnectionsTable("After Release");
            }
         }

         if (connectionInfo.getNumActiveConns() > 0) {
            if (Debug.verbose) {
               Debug.exit(this, "releaseSharedConnection() return false. The connection still has " + connectionInfo.getNumActiveConns() + " active handles");
            }

            return false;
         } else {
            if (Debug.isPoolVerboseEnabled()) {
               this.releasingConnections.put(connectionInfo, StackTraceUtils.throwable2StackTrace(new Throwable("releaseSharedConnection in " + Thread.currentThread())));
            } else {
               this.releasingConnections.put(connectionInfo, Thread.currentThread().toString());
            }

            if (Debug.verbose) {
               Debug.exit(this, "releaseSharedConnection(): return true.");
            }

            return true;
         }
      }
   }

   public synchronized void releaseFinished(ConnectionInfo connectionInfo) {
      this.debugVerbose("ConnectionSharingManager.releaseFinished " + connectionInfo);
      this.releasingConnections.remove(connectionInfo);
   }

   public synchronized void clean(ConnectionInfo connectionInfo) {
      this.debugVerbose("ConnectionSharingManager.clean " + connectionInfo);
      this.releasingConnections.remove(connectionInfo);
      this.reservingConnections.remove(connectionInfo);
      Transaction tx = (Transaction)this.connection2TransactionMap.get(connectionInfo);
      if (tx != null) {
         this.transaction2ConnectionMap.remove(tx);
         this.connection2TransactionMap.remove(connectionInfo);
      }

   }

   private void dumpSharedConnectionsTable(String msg) {
      if (Debug.isPoolVerboseEnabled()) {
         this.debugVerbose("*** Dump of sharedConnections " + msg + " ***");
         Iterator var2 = this.transaction2ConnectionMap.keySet().iterator();

         while(var2.hasNext()) {
            Transaction transaction = (Transaction)var2.next();
            ConnectionInfo info = (ConnectionInfo)this.transaction2ConnectionMap.get(transaction);
            this.debugVerbose("[ tx = " + ((weblogic.transaction.Transaction)transaction).getXID() + ", info = " + info.toString() + ", active connection number = " + info.getNumActiveConns() + " ]");
         }
      }

   }

   public synchronized ConnectionInfo getSharedConnection() {
      if (Debug.verbose) {
         Debug.enter(this, ".getSharedConnection");
      }

      ConnectionInfo connectionInfo = null;

      try {
         if (Debug.isPoolVerboseEnabled()) {
            this.dumpSharedConnectionsTable("On entry to getSharedConnection()");
         }

         Transaction transaction = TransactionHelper.getTransactionHelper().getTransaction();
         if (transaction != null) {
            connectionInfo = (ConnectionInfo)this.transaction2ConnectionMap.get(transaction);
         }

         if (Debug.isPoolVerboseEnabled()) {
            this.debugVerbose("Returning " + connectionInfo + "on exit from getSharedConnection() ");
         }
      } finally {
         if (Debug.verbose) {
            Debug.exit(this, "getSharedConnection()");
         }

      }

      return connectionInfo;
   }

   public synchronized ConnectionInfo reserveSharedConnection() throws ResourceException {
      if (Debug.verbose) {
         Debug.enter(this, ".reserveSharedConnection");
      }

      ConnectionInfo var6;
      try {
         ConnectionInfo connectionInfo = this.getSharedConnection();
         if (connectionInfo != null) {
            String threadInfo = (String)this.reservingConnections.get(connectionInfo);
            if (threadInfo != null) {
               Debug.concurrentlogger.debug("ConnectionSharingManager.reserveSharedConnection(): Reserve shared connection failed because Connection is being reserving in another thread " + threadInfo, new Throwable("reserveSharedConnection in " + Thread.currentThread()));
               throw new ResourceException("Reserve shared connection failed because Connection is being reserving in another thread " + threadInfo);
            }

            threadInfo = (String)this.releasingConnections.get(connectionInfo);
            if (threadInfo != null) {
               Debug.concurrentlogger.debug("ConnectionSharingManager.reserveSharedConnection(): Reserve shared connection failed because Connection is being releasing in another thread " + threadInfo, new Throwable("reserveSharedConnection in " + Thread.currentThread()));
               throw new ResourceException("Reserve shared connection failed because Connection is being releasing in another thread " + threadInfo);
            }

            if (Debug.isPoolVerboseEnabled()) {
               this.reservingConnections.put(connectionInfo, StackTraceUtils.throwable2StackTrace(new Throwable("reserveSharedConnection in " + Thread.currentThread())));
            } else {
               this.reservingConnections.put(connectionInfo, Thread.currentThread().toString());
            }
         }

         var6 = connectionInfo;
      } finally {
         if (Debug.verbose) {
            Debug.exit(this, ".reserveSharedConnection()");
         }

      }

      return var6;
   }

   public synchronized void reserveFinished(ConnectionInfo connectionInfo) {
      this.debugVerbose("reserveFinished " + connectionInfo);
      this.reservingConnections.remove(connectionInfo);
   }

   void debugVerbose(String msg) {
      if (Debug.isPoolVerboseEnabled()) {
         Debug.poolVerbose("For pool '" + this.poolName + "' " + msg);
      }

   }
}
