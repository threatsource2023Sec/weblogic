package weblogic.jdbc.wrapper;

import java.util.Properties;
import javax.transaction.xa.Xid;
import weblogic.jdbc.common.internal.ConnectionEnv;

public class TxInfo {
   private volatile XAConnection xaConn;
   private volatile boolean ended = true;
   private volatile boolean cancelStmtCalled = true;
   private volatile boolean cancelStmtCompleted = true;
   private volatile boolean aborted = true;
   private volatile Properties props;
   private volatile Xid xid = null;
   private volatile boolean enlisted = false;
   private volatile boolean rollbackCalled = false;
   private static final String XA_RETRY_TIMEOUT = "XARetryTimeout";

   public TxInfo(XAConnection xaConn) {
      this.xaConn = xaConn;
      this.props = new Properties();
   }

   public synchronized void setXARetryTimeout(long t) {
      this.props.setProperty("XARetryTimeout", String.valueOf(t));
   }

   public synchronized long getXARetryTimeout() {
      return Long.parseLong(this.props.getProperty("XARetryTimeout", "0"));
   }

   public synchronized Properties getProperties() {
      return this.props;
   }

   public synchronized void setProperties(Properties props) {
      this.props = props;
   }

   public synchronized boolean isEnded() {
      return this.ended;
   }

   public synchronized void setEnded(boolean b) {
      this.ended = b;
   }

   public synchronized XAConnection getXAConnection() {
      return this.xaConn;
   }

   public synchronized void setXAConnection(XAConnection xaconn) {
      this.xaConn = xaconn;
   }

   public synchronized boolean isEnlisted() {
      return this.enlisted;
   }

   public synchronized void setEnlisted(boolean b) {
      this.enlisted = b;
   }

   public String toString() {
      ConnectionEnv env = null;
      if (this.xaConn != null) {
         env = this.xaConn.getConnectionEnv();
      }

      return env != null ? "[" + env.toString() + "]" : "[ No XAConnection is attached to this TxInfo ]";
   }

   public synchronized boolean isAborted() {
      return this.aborted;
   }

   public synchronized void setAborted(boolean b) {
      this.aborted = b;
   }

   public synchronized boolean isRollbackCalled() {
      return this.rollbackCalled;
   }

   public synchronized void setRollbackCalled(boolean b) {
      this.rollbackCalled = b;
   }

   public synchronized boolean isCancelStmtCompleted() {
      return this.cancelStmtCompleted;
   }

   public synchronized void setCancelStmtCompleted(boolean b) {
      this.cancelStmtCompleted = b;
   }

   public synchronized void setTxInfoXid(Xid xid) {
      this.xid = xid;
   }

   public synchronized Xid getTxInfoXid() {
      return this.xid;
   }
}
