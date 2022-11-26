package weblogic.connector.transaction.outbound;

import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnection;
import javax.transaction.Transaction;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.outbound.ConnectionHandlerBaseImpl;
import weblogic.connector.outbound.ConnectionInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;

public abstract class TxConnectionHandler extends ConnectionHandlerBaseImpl {
   protected Transaction transaction;
   private boolean globalTransactionInProgress = false;
   private boolean localTransactionInProgress = false;
   static final long serialVersionUID = 8998879429507410876L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.transaction.outbound.TxConnectionHandler");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Destroy_Connection_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   TxConnectionHandler(ManagedConnection mc, ConnectionPool connPool, SecurityContext secCtx, ConnectionInfo connectionInfo, String transSupport) {
      super(mc, connPool, secCtx, connectionInfo, transSupport);
   }

   public void destroy() {
      LocalHolder var1;
      if ((var1 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var1.argsCapture) {
            var1.args = new Object[1];
            var1.args[0] = this;
         }

         if (var1.monitorHolder[1] != null) {
            var1.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.preProcess(var1);
         }

         if (var1.monitorHolder[2] != null) {
            var1.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
         }

         if (var1.monitorHolder[3] != null) {
            var1.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var1);
            InstrumentationSupport.process(var1);
         }

         var1.resetPostBegin();
      }

      try {
         this.connPool.getResourceRegistrationManager().removeResource(this);
         super.destroy();
      } catch (Throwable var3) {
         if (var1 != null) {
            var1.th = var3;
            if (var1.monitorHolder[1] != null) {
               var1.monitorIndex = 1;
               InstrumentationSupport.postProcess(var1);
            }

            if (var1.monitorHolder[0] != null) {
               var1.monitorIndex = 0;
               InstrumentationSupport.process(var1);
            }
         }

         throw var3;
      }

      if (var1 != null) {
         if (var1.monitorHolder[1] != null) {
            var1.monitorIndex = 1;
            InstrumentationSupport.postProcess(var1);
         }

         if (var1.monitorHolder[0] != null) {
            var1.monitorIndex = 0;
            InstrumentationSupport.process(var1);
         }
      }

   }

   synchronized void notifyConnPoolOfTransCompletion(Transaction tx) {
      if (tx != null && !tx.equals(this.getTransaction())) {
         if (Debug.isXAoutEnabled()) {
            Debug.xaOut(this.connPool, "notifyConnPoolOfTransCompletion ( " + this.toString() + ", tx=" + tx + " ) - this handler has been released for this tx in another thread. current tx is " + this.getTransaction());
         }

         Debug.concurrentlogger.debug("For pool '" + (this.connPool != null ? this.connPool.getName() : "<null>") + " notifyConnPoolOfTransCompletion ( " + this.toString() + ", tx=" + tx + " ) - this handler has been released for this tx in another thread. current tx is " + this.getTransaction());
      } else {
         this.setGlobalTransactionInProgress(false);
         this.transaction = null;
         if (!this.isDestroyed) {
            this.connPool.releaseOnTransactionCompleted(this.getConnectionInfo());
            this.dissociateHandles();
         } else {
            this.destroyConnection();
         }

      }
   }

   public void setLocalTransactionInProgress(boolean localTransactionInProgress, String event) {
      if (this.isGlobalTransactionInProgress()) {
         String msg = ConnectorLogger.logAdapterShouldnotSendLocalTxEvent(event);
         throw new IllegalStateException(msg);
      } else {
         this.setLocalTransactionInProgress(localTransactionInProgress);
      }
   }

   public void setLocalTransactionInProgress(boolean localTransactionInProgress) {
      if (!this.localTransactionInProgress && localTransactionInProgress) {
         XANotificationListener.getInstance().registerNotification(this);
      } else if (this.localTransactionInProgress && !localTransactionInProgress) {
         XANotificationListener.getInstance().deregisterNotification(this);
      }

      this.localTransactionInProgress = localTransactionInProgress;
   }

   public synchronized void setGlobalTransactionInProgress(boolean globalTransactionInProgress) {
      this.globalTransactionInProgress = globalTransactionInProgress;
   }

   public synchronized boolean isGlobalTransactionInProgress() {
      return this.globalTransactionInProgress;
   }

   public boolean isLocalTransactionInProgress() {
      return this.localTransactionInProgress;
   }

   public Transaction getTransaction() {
      return this.transaction;
   }

   public void cleanup() throws ResourceException {
      this.globalTransactionInProgress = false;
      this.localTransactionInProgress = false;
      super.cleanup();
   }

   public synchronized boolean isInTransaction() {
      return this.isLocalTransactionInProgress() || this.isGlobalTransactionInProgress();
   }

   static {
      _WLDF$INST_FLD_Connector_After_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Outbound");
      _WLDF$INST_FLD_Connector_Around_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Outbound");
      _WLDF$INST_FLD_Connector_Before_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Outbound");
      _WLDF$INST_FLD_Connector_Destroy_Connection_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Destroy_Connection_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "TxConnectionHandler.java", "weblogic.connector.transaction.outbound.TxConnectionHandler", "destroy", "()V", 63, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Destroy_Connection_Low", "Connector_After_Outbound", "Connector_Around_Outbound", "Connector_Before_Outbound"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionHandlerPoolRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), null, null, null}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Destroy_Connection_Low};
   }
}
