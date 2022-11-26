package weblogic.connector.outbound;

import java.security.AccessController;
import java.util.Map;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import weblogic.common.ResourceException;
import weblogic.connector.common.Debug;
import weblogic.connector.lifecycle.CheckPartitionProxy;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class NoTxConnectionEventListener implements ConnectionEventListener {
   protected ConnectionHandler connectionHandler;
   private String listenerName;
   static final long serialVersionUID = 8584223057196420550L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.outbound.NoTxConnectionEventListener");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Connection_Error_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Connection_Closed_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Outbound_Transaction_Start_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;
   static final JoinPoint _WLDF$INST_JPFLD_3;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_3;
   static final JoinPoint _WLDF$INST_JPFLD_4;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_4;

   public static ConnectionEventListener create(ConnectionHandler connectionHandler, String listenerName, String partitionName) {
      NoTxConnectionEventListener listener = new NoTxConnectionEventListener(connectionHandler, listenerName);
      return CheckPartitionProxy.wrapConnectionEventListener(listener, partitionName);
   }

   protected NoTxConnectionEventListener(ConnectionHandler connectionHandler, String listenerName) {
      this.connectionHandler = connectionHandler;
      this.listenerName = listenerName;
   }

   public void localTransactionStarted(ConnectionEvent var1) {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[3] != null) {
            var2.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.debugConnEvent("LOCAL_TRANSACTION_STARTED event received. Ignore");
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.process(var2);
         }
      }

   }

   public void localTransactionRolledback(ConnectionEvent var1) {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[3] != null) {
            var2.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.debugConnEvent("LOCAL_TRANSACTION_ROLLEDBACK event received. Ignore");
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[1] != null) {
               var2.monitorIndex = 1;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.process(var2);
         }
      }

   }

   public void localTransactionCommitted(ConnectionEvent var1) {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         if (var2.monitorHolder[1] != null) {
            var2.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.preProcess(var2);
         }

         if (var2.monitorHolder[3] != null) {
            var2.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var2);
            InstrumentationSupport.process(var2);
         }

         var2.resetPostBegin();
      }

      try {
         this.debugConnEvent("LOCAL_TRANSACTION_COMMITTED event received. Ignore");
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            if (var2.monitorHolder[2] != null) {
               var2.monitorIndex = 2;
               InstrumentationSupport.postProcess(var2);
            }

            if (var2.monitorHolder[0] != null) {
               var2.monitorIndex = 0;
               InstrumentationSupport.process(var2);
            }
         }

         throw var4;
      }

      if (var2 != null) {
         if (var2.monitorHolder[2] != null) {
            var2.monitorIndex = 2;
            InstrumentationSupport.postProcess(var2);
         }

         if (var2.monitorHolder[0] != null) {
            var2.monitorIndex = 0;
            InstrumentationSupport.process(var2);
         }
      }

   }

   public void connectionClosed(ConnectionEvent connEvt) {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var5.argsCapture) {
            var5.args = InstrumentationSupport.toSensitive(2);
         }

         if (var5.monitorHolder[0] != null) {
            var5.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.process(var5);
         }

         if (var5.monitorHolder[2] != null) {
            var5.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.preProcess(var5);
         }

         if (var5.monitorHolder[3] != null) {
            var5.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.process(var5);
         }

         var5.resetPostBegin();
      }

      try {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

         try {
            this.debugConnEvent("CONNECTION_CLOSED event received");
            this.connectionHandler.getPool().incrementCloseCount();
            Object connectionHandle = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().getConnectionHandle(connEvt, kernelId);
            if (connectionHandle == null) {
               String exMsg = Debug.getExceptionHandleNotSet();
               throw new IllegalStateException(exMsg);
            }

            this.connectionHandler.closeConnection(connectionHandle);
         } catch (ResourceException var8) {
            Debug.logCloseConnectionError(this.listenerName, this.connectionHandler.getConnectionInfo(), "close", var8);
         }
      } catch (Throwable var9) {
         if (var5 != null) {
            var5.th = var9;
            if (var5.monitorHolder[2] != null) {
               var5.monitorIndex = 2;
               InstrumentationSupport.postProcess(var5);
            }

            if (var5.monitorHolder[1] != null) {
               var5.monitorIndex = 1;
               InstrumentationSupport.process(var5);
            }
         }

         throw var9;
      }

      if (var5 != null) {
         if (var5.monitorHolder[2] != null) {
            var5.monitorIndex = 2;
            InstrumentationSupport.postProcess(var5);
         }

         if (var5.monitorHolder[1] != null) {
            var5.monitorIndex = 1;
            InstrumentationSupport.process(var5);
         }
      }

   }

   public void connectionErrorOccurred(ConnectionEvent connEvt) {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_4, _WLDF$INST_JPFLD_JPMONS_4)) != null) {
         if (var6.argsCapture) {
            var6.args = InstrumentationSupport.toSensitive(2);
         }

         if (var6.monitorHolder[1] != null) {
            var6.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var6);
            InstrumentationSupport.preProcess(var6);
         }

         if (var6.monitorHolder[2] != null) {
            var6.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var6);
            InstrumentationSupport.process(var6);
         }

         if (var6.monitorHolder[3] != null) {
            var6.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var6);
            InstrumentationSupport.process(var6);
         }

         var6.resetPostBegin();
      }

      try {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

         try {
            this.debugConnEvent("CONNECTION_ERROR_OCCURRED event received");
            this.connectionHandler.setConnectionErrorOccurred(true);
            this.connectionHandler.destroyConnection();
            this.connectionHandler.getPool().incrementConnectionsDestroyedByErrorCount();
            Exception ex = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().getException(connEvt, kernelId);
            String message = "connectionErrorOccurred:  EventId=" + this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().getId(connEvt, kernelId);
            if (ex != null) {
               String exString = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(ex, kernelId);
               message = message + ", Exception information:  " + exString;
            }

            this.debugConnEvent(message);
         } catch (Exception var9) {
            this.debugConnEvent("Exception during connectionErrorOccurred", var9);
         }
      } catch (Throwable var10) {
         if (var6 != null) {
            var6.th = var10;
            if (var6.monitorHolder[1] != null) {
               var6.monitorIndex = 1;
               InstrumentationSupport.postProcess(var6);
            }

            if (var6.monitorHolder[0] != null) {
               var6.monitorIndex = 0;
               InstrumentationSupport.process(var6);
            }
         }

         throw var10;
      }

      if (var6 != null) {
         if (var6.monitorHolder[1] != null) {
            var6.monitorIndex = 1;
            InstrumentationSupport.postProcess(var6);
         }

         if (var6.monitorHolder[0] != null) {
            var6.monitorIndex = 0;
            InstrumentationSupport.process(var6);
         }
      }

   }

   public void cleanup() {
   }

   protected void debugConnEvent(String msg) {
      if (Debug.isConnEventsEnabled()) {
         Debug.connEvent("For the " + this.listenerName + " of pool '" + this.connectionHandler.getPoolName() + "' " + msg);
      }

   }

   protected void debugConnEvent(String msg, Exception ex) {
      if (Debug.isConnEventsEnabled()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         String exString = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(ex, kernelId);
         Debug.connEvent("For the " + this.listenerName + " of pool '" + this.connectionHandler.getPoolName() + "' " + msg + "\n" + exString);
      }

   }

   static {
      _WLDF$INST_FLD_Connector_After_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Outbound");
      _WLDF$INST_FLD_Connector_Around_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Outbound");
      _WLDF$INST_FLD_Connector_Connection_Error_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Connection_Error_Low");
      _WLDF$INST_FLD_Connector_Before_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Outbound");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Rollback_Low");
      _WLDF$INST_FLD_Connector_Connection_Closed_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Connection_Closed_Low");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Start_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Start_Medium");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Commmit_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "NoTxConnectionEventListener.java", "weblogic.connector.outbound.NoTxConnectionEventListener", "localTransactionStarted", "(Ljavax/resource/spi/ConnectionEvent;)V", 58, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Outbound_Transaction_Start_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "NoTxConnectionEventListener.java", "weblogic.connector.outbound.NoTxConnectionEventListener", "localTransactionRolledback", "(Ljavax/resource/spi/ConnectionEvent;)V", 73, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "NoTxConnectionEventListener.java", "weblogic.connector.outbound.NoTxConnectionEventListener", "localTransactionCommitted", "(Ljavax/resource/spi/ConnectionEvent;)V", 88, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "NoTxConnectionEventListener.java", "weblogic.connector.outbound.NoTxConnectionEventListener", "connectionClosed", "(Ljavax/resource/spi/ConnectionEvent;)V", 105, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Connection_Closed_Low, _WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound};
      _WLDF$INST_JPFLD_4 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "NoTxConnectionEventListener.java", "weblogic.connector.outbound.NoTxConnectionEventListener", "connectionErrorOccurred", "(Ljavax/resource/spi/ConnectionEvent;)V", 144, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_4 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Connection_Error_Low, _WLDF$INST_FLD_Connector_Before_Outbound};
   }
}
