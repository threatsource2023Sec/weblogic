package weblogic.connector.transaction.outbound;

import java.security.AccessController;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.common.ResourceException;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class XAWrapper implements XAResource {
   boolean enlistableXARes = true;
   private Xid xidCached = null;
   private static String ORACLE_THIN_DRIVER_XARESOURCE_CLASSNAME = "oracle.jdbc.xa.client.OracleXAResource";
   private static String ORACLE_THIN_DRIVER_90_XARESOURCE_CLASSNAME = "oracle.jdbc.driver.T4CXAResource";
   protected boolean ended = true;
   protected XAResource xares;
   private XATxConnectionHandler connectionHandler;
   private XATxConnectionHandler connectionHandlerForRetry;
   protected ConnectionPool pool;
   private RAInstanceManager raIM;

   XAWrapper(XAResource xares, XATxConnectionHandler connectionHandler, ConnectionPool pool) {
      this.xares = xares;
      this.connectionHandler = connectionHandler;
      this.pool = pool;
   }

   public void commit(Xid xid, boolean onePhase) throws XAException {
      Utils.startManagement();

      try {
         this.debug(" - commit request for xid: " + xid);
         XAResource xar = this.getUsableXAResource("commit");
         this.debug(" - issuing commit");
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         this.getRAiM().getAdapterLayer().commit(xar, this.getXid(xid), onePhase, kernelId);
         this.clearXid(xid);
      } catch (XAException var8) {
         if (var8.errorCode == -7) {
            this.handleRMFail();
         }

         throw var8;
      } finally {
         this.releaseResourceForRetryIfNecessary();
         Utils.stopManagement();
      }

   }

   public void end(Xid xid, int flags) throws XAException {
      Utils.startManagement();

      try {
         this.debug(" - end request for xid: " + xid + ", flags = " + flags);
         this.validate("end");
         if (this.isUsingOracleThinDriver() && this.ended) {
            this.debug(" - not issuing repeated call to end because of issue with Oracle Thin Driver (CR100269)");
         } else {
            this.debug(" - issuing end");
            this.ended = true;
            AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            this.getRAiM().getAdapterLayer().end(this.xares, this.getXid(xid), flags, kernelId);
         }
      } catch (XAException var7) {
         if (var7.errorCode == -7) {
            this.handleRMFail();
         }

         throw var7;
      } finally {
         Utils.stopManagement();
      }

   }

   public void forget(Xid xid) throws XAException {
      Utils.startManagement();

      try {
         this.debug(" - forget request for xid: " + xid);
         XAResource xar = this.getUsableXAResource("forget");
         this.debug(" - issuing forget");
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         this.getRAiM().getAdapterLayer().forget(xar, this.getXid(xid), kernelId);
         this.clearXid(xid);
      } catch (XAException var7) {
         if (var7.errorCode == -7) {
            this.handleRMFail();
         }

         throw var7;
      } finally {
         this.releaseResourceForRetryIfNecessary();
         Utils.stopManagement();
      }

   }

   public int getTransactionTimeout() throws XAException {
      Utils.startManagement();

      int var2;
      try {
         this.debug(" - getTransactionTimeout request");
         this.validate("getTransactionTimeout");
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         var2 = this.getRAiM().getAdapterLayer().getTransactionTimeout(this.xares, kernelId);
      } catch (XAException var6) {
         if (var6.errorCode == -7) {
            this.handleRMFail();
         }

         throw var6;
      } finally {
         Utils.stopManagement();
      }

      return var2;
   }

   public boolean isSameRM(XAResource xares2) throws XAException {
      Utils.startManagement();

      boolean var5;
      try {
         this.debug(" - isSameRM request, xares2 = " + xares2);
         boolean isSameObject = this == xares2;
         boolean isSamePool = xares2 instanceof XAWrapper && ((XAWrapper)xares2).getConnectionPool().getNameWithPartitionName().equals(this.getConnectionPool().getNameWithPartitionName());
         boolean isSameRM = isSameObject || isSamePool;
         this.debug(" - isSameRM request, returning:  " + isSameRM);
         var5 = isSameRM;
      } finally {
         Utils.stopManagement();
      }

      return var5;
   }

   public int prepare(Xid xid) throws XAException {
      Utils.startManagement();

      int var4;
      try {
         this.debug(" - prepare request for xid: " + xid);
         XAResource xares = this.getUsableXAResource("prepare");
         this.debug(" - issuing prepare");
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         var4 = this.getRAiM().getAdapterLayer().prepare(xares, this.getXid(xid), kernelId);
      } catch (XAException var8) {
         if (var8.errorCode == -7) {
            this.handleRMFail();
         }

         throw var8;
      } finally {
         this.releaseResourceForRetryIfNecessary();
         Utils.stopManagement();
      }

      return var4;
   }

   public Xid[] recover(int flag) throws XAException {
      Utils.startManagement();

      Xid[] var11;
      try {
         this.debug(" - recover request, flag: " + flag);
         XAResource xares = this.getUsableXAResource("recover");
         this.debug(" - issuing recover");
         Xid[] recoverXids = null;
         AuthenticatedSubject kernelId = null;
         if (!this.enlistableXARes) {
            recoverXids = new Xid[0];
         } else {
            kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            if (this.callRecoverOnlyOnce() && (flag & 16777216) == 16777216) {
               recoverXids = this.getRAiM().getAdapterLayer().recover(xares, 25165824, kernelId);
            } else if (!this.callRecoverOnlyOnce()) {
               recoverXids = this.getRAiM().getAdapterLayer().recover(xares, flag, kernelId);
            }
         }

         if (Debug.isXAoutEnabled()) {
            if (recoverXids != null && recoverXids.length > 0) {
               this.debug(" - recover request, returning Xids:  count = " + recoverXids.length);

               for(int idx = 0; idx < recoverXids.length; ++idx) {
                  this.debug("   #" + idx + ":  xid = " + this.getRAiM().getAdapterLayer().toString(recoverXids[idx], kernelId));
               }
            } else {
               this.debug(" - recover request, returning no Xids");
            }
         }

         var11 = recoverXids;
      } catch (XAException var9) {
         if (var9.errorCode == -7) {
            this.handleRMFail();
         }

         throw var9;
      } finally {
         this.releaseResourceForRetryIfNecessary();
         Utils.stopManagement();
      }

      return var11;
   }

   public void rollback(Xid xid) throws XAException {
      Utils.startManagement();

      try {
         this.debug(" - rollback request for xid: " + xid);
         XAResource xares = this.getUsableXAResource("rollback");
         this.debug(" - issuing rollback");
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         this.getRAiM().getAdapterLayer().rollback(xares, this.getXid(xid), kernelId);
         this.clearXid(xid);
      } catch (XAException var7) {
         if (var7.errorCode == -7) {
            this.handleRMFail();
         }

         throw var7;
      } finally {
         this.releaseResourceForRetryIfNecessary();
         Utils.stopManagement();
      }

   }

   public boolean setTransactionTimeout(int seconds) throws XAException {
      Utils.startManagement();

      boolean var3;
      try {
         this.debug(" - setTransactionTimeout request:  seconds = " + seconds);
         this.validate("setTransactionTimeout");
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         var3 = this.getRAiM().getAdapterLayer().setTransactionTimeout(this.xares, seconds, kernelId);
      } catch (XAException var7) {
         if (var7.errorCode == -7) {
            this.handleRMFail();
         }

         throw var7;
      } finally {
         Utils.stopManagement();
      }

      return var3;
   }

   public void start(Xid xid, int flags) throws XAException {
      Utils.startManagement();

      try {
         this.debug(" - start request for xid: " + xid + ", flags: " + flags);
         this.validate("start");
         this.debug(" - issuing start");
         this.xidCached = this.getXid(xid);
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         this.getRAiM().getAdapterLayer().start(this.xares, this.xidCached, flags, kernelId);
         this.ended = false;
      } catch (XAException var7) {
         if (var7.errorCode == -7) {
            this.handleRMFail();
         }

         throw var7;
      } finally {
         Utils.stopManagement();
      }

   }

   protected void debug(String msg) {
      if (Debug.isXAoutEnabled()) {
         Debug.xaOut(this.getConnectionPool(), this.getClass().getName() + ":  " + this + ", " + msg);
      }

   }

   protected void debug(String msg, Throwable ex) {
      if (Debug.isXAoutEnabled()) {
         this.debug(msg + ", Exception:  " + ex);
      }

   }

   protected ConnectionPool getConnectionPool() {
      return this.pool;
   }

   protected boolean isUsingOracleThinDriver() {
      return this.xares != null && this.xares.getClass().getName().equalsIgnoreCase(ORACLE_THIN_DRIVER_XARESOURCE_CLASSNAME);
   }

   protected boolean callRecoverOnlyOnce() {
      return this.xares != null && (this.xares.getClass().getName().equalsIgnoreCase(ORACLE_THIN_DRIVER_90_XARESOURCE_CLASSNAME) || this.xares.getClass().getName().equalsIgnoreCase(ORACLE_THIN_DRIVER_XARESOURCE_CLASSNAME));
   }

   protected void validate(String method) throws XAException {
      if (this.connectionHandler.isConnectionErrorOccurred()) {
         this.debug(" - connectionHandler.isConnectionErrorOccurred is true; not issuing " + method);
         throw new XAException(-3);
      } else if (this.xares == null) {
         this.debug(" - XAResource is null; not issuing " + method);
         throw new XAException(-3);
      }
   }

   protected XAResource getUsableXAResource(String method) throws XAException {
      XAException xae;
      if (this.connectionHandler.isConnectionErrorOccurred()) {
         this.debug(" - connectionHandler.isConnectionErrorOccurred is true; try reserve another connection from pool. operation:" + method);

         try {
            this.connectionHandlerForRetry = this.pool.reserveInternal(this.connectionHandler);
         } catch (ResourceException var4) {
            XAException xae = new XAException("Connection error occured on original connection, and failed to get new connection from pool.");
            xae.errorCode = -3;
            xae.initCause(var4);
            throw xae;
         }

         if (this.connectionHandlerForRetry == null) {
            xae = new XAException("Connection error occured on original connection, and cannot get new connection from pool.");
            xae.errorCode = -3;
            throw xae;
         } else {
            XAResource wrapper = this.connectionHandlerForRetry.getXAResource();
            XAResource res = ((XAWrapper)wrapper).xares;
            this.debug(" get new retry connection from pool: " + this.connectionHandlerForRetry + "; XAWrapper: " + wrapper + "; physical XAResoruce" + res);
            return res;
         }
      } else if (this.xares == null) {
         this.debug(" - XAResource is null; not issuing " + method);
         xae = new XAException("XAResource is null.");
         xae.errorCode = -3;
         throw xae;
      } else {
         return this.xares;
      }
   }

   protected void releaseResourceForRetryIfNecessary() {
      if (this.connectionHandlerForRetry != null) {
         if (this.pool.isWLSMessagingBridgeConnection()) {
            XAResource res = ((XAWrapper)this.connectionHandler.getXAResource()).xares;
            this.debug("isWLSMessagingBridge, destroy retry resource " + this.connectionHandlerForRetry + "; XAWrapper: " + this.connectionHandlerForRetry.getXAResource() + "; physical res " + res);
            this.connectionHandlerForRetry.destroyConnection();
         } else {
            try {
               this.debug("release the retry resource " + this.connectionHandlerForRetry + "; XAWrapper: " + this.connectionHandlerForRetry.getXAResource());
               this.pool.releaseResource(this.connectionHandlerForRetry.getConnectionInfo());
            } catch (Throwable var2) {
               if (Debug.isXAoutEnabled()) {
                  this.debug("Error occured during release the retry connection/resource. ignore it", var2);
               }
            }
         }

         this.connectionHandlerForRetry = null;
      }

   }

   protected RAInstanceManager getRAiM() {
      if (this.raIM == null) {
         this.raIM = this.getConnectionPool().getRAInstanceManager();
      }

      return this.raIM;
   }

   private synchronized Xid getXid(Xid xid) {
      Object returnXid;
      if (this.xidCached != null && this.xidCached.equals(xid)) {
         returnXid = this.xidCached;
      } else {
         returnXid = new XidImpl(xid);
      }

      return (Xid)returnXid;
   }

   private void clearXid(Xid xid) {
      if (this.xidCached != null && this.xidCached.equals(xid)) {
         this.xidCached = null;
      }

   }

   private void handleRMFail() {
      this.pool.setRmHealthy(false);
   }
}
