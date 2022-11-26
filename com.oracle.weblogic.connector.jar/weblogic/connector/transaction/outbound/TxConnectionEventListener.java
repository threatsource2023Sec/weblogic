package weblogic.connector.transaction.outbound;

import java.util.Map;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.transaction.Transaction;
import weblogic.connector.common.Debug;
import weblogic.connector.lifecycle.CheckPartitionProxy;
import weblogic.connector.outbound.NoTxConnectionEventListener;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;

public class TxConnectionEventListener extends NoTxConnectionEventListener {
   static final long serialVersionUID = 1869277906630107518L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.transaction.outbound.TxConnectionEventListener");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Connection_Error_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Tx;
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

   public static ConnectionEventListener create(TxConnectionHandler connectionHandler, String listenerName, String partitionName) {
      TxConnectionEventListener listener = new TxConnectionEventListener(connectionHandler, listenerName);
      return CheckPartitionProxy.wrapConnectionEventListener(listener, partitionName);
   }

   private TxConnectionEventListener(TxConnectionHandler connectionHandler, String listenerName) {
      super(connectionHandler, listenerName);
   }

   public void localTransactionStarted(ConnectionEvent var1) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(2);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[4] != null) {
            var4.monitorIndex = 4;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[5] != null) {
            var4.monitorIndex = 5;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[6] != null) {
            var4.monitorIndex = 6;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         var4.resetPostBegin();
      }

      try {
         this.debugConnEvent("LOCAL_TRANSACTION_STARTED event received");
         synchronized(this.connectionHandler) {
            ((TxConnectionHandler)this.connectionHandler).setLocalTransactionInProgress(true, "LOCAL_TRANSACTION_STARTED");
         }

         Debug.localOut(this.connectionHandler.getPool(), "Local Transaction Started");
      } catch (Throwable var8) {
         if (var4 != null) {
            var4.th = var8;
            if (var4.monitorHolder[3] != null) {
               var4.monitorIndex = 3;
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[2] != null) {
               var4.monitorIndex = 2;
               InstrumentationSupport.postProcess(var4);
            }

            if (var4.monitorHolder[1] != null) {
               var4.monitorIndex = 1;
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[0] != null) {
               var4.monitorIndex = 0;
               InstrumentationSupport.postProcess(var4);
            }
         }

         throw var8;
      }

      if (var4 != null) {
         if (var4.monitorHolder[3] != null) {
            var4.monitorIndex = 3;
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.postProcess(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.postProcess(var4);
         }
      }

   }

   public void connectionErrorOccurred(ConnectionEvent connEvt) {
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
         super.connectionErrorOccurred(connEvt);
         ((TxConnectionHandler)this.connectionHandler).setLocalTransactionInProgress(false);
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
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(2);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[4] != null) {
            var4.monitorIndex = 4;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[5] != null) {
            var4.monitorIndex = 5;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[6] != null) {
            var4.monitorIndex = 6;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         var4.resetPostBegin();
      }

      try {
         this.debugConnEvent("LOCAL_TRANSACTION_ROLLEDBACK event received");
         synchronized(this.connectionHandler) {
            ((TxConnectionHandler)this.connectionHandler).setLocalTransactionInProgress(false, "LOCAL_TRANSACTION_ROLLEDBACK");
            ((TxConnectionHandler)this.connectionHandler).notifyConnPoolOfTransCompletion((Transaction)null);
         }
      } catch (Throwable var8) {
         if (var4 != null) {
            var4.th = var8;
            if (var4.monitorHolder[3] != null) {
               var4.monitorIndex = 3;
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[2] != null) {
               var4.monitorIndex = 2;
               InstrumentationSupport.postProcess(var4);
            }

            if (var4.monitorHolder[1] != null) {
               var4.monitorIndex = 1;
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[0] != null) {
               var4.monitorIndex = 0;
               InstrumentationSupport.postProcess(var4);
            }
         }

         throw var8;
      }

      if (var4 != null) {
         if (var4.monitorHolder[3] != null) {
            var4.monitorIndex = 3;
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.postProcess(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.postProcess(var4);
         }
      }

   }

   public void localTransactionCommitted(ConnectionEvent var1) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_3, _WLDF$INST_JPFLD_JPMONS_3)) != null) {
         if (var4.argsCapture) {
            var4.args = InstrumentationSupport.toSensitive(2);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[3] != null) {
            var4.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[5] != null) {
            var4.monitorIndex = 5;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[6] != null) {
            var4.monitorIndex = 6;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         var4.resetPostBegin();
      }

      try {
         this.debugConnEvent("LOCAL_TRANSACTION_COMMITTED event received");
         synchronized(this.connectionHandler) {
            ((TxConnectionHandler)this.connectionHandler).setLocalTransactionInProgress(false, "LOCAL_TRANSACTION_COMMITTED");
            ((TxConnectionHandler)this.connectionHandler).notifyConnPoolOfTransCompletion((Transaction)null);
         }
      } catch (Throwable var8) {
         if (var4 != null) {
            var4.th = var8;
            if (var4.monitorHolder[4] != null) {
               var4.monitorIndex = 4;
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[3] != null) {
               var4.monitorIndex = 3;
               InstrumentationSupport.postProcess(var4);
            }

            if (var4.monitorHolder[1] != null) {
               var4.monitorIndex = 1;
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[0] != null) {
               var4.monitorIndex = 0;
               InstrumentationSupport.postProcess(var4);
            }
         }

         throw var8;
      }

      if (var4 != null) {
         if (var4.monitorHolder[4] != null) {
            var4.monitorIndex = 4;
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[3] != null) {
            var4.monitorIndex = 3;
            InstrumentationSupport.postProcess(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.postProcess(var4);
         }
      }

   }

   static {
      _WLDF$INST_FLD_Connector_After_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Outbound");
      _WLDF$INST_FLD_Connector_Around_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Outbound");
      _WLDF$INST_FLD_Connector_Connection_Error_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Connection_Error_Low");
      _WLDF$INST_FLD_Connector_Before_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Outbound");
      _WLDF$INST_FLD_Connector_Around_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Tx");
      _WLDF$INST_FLD_Connector_After_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Tx");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Rollback_Low");
      _WLDF$INST_FLD_Connector_Before_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Tx");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Start_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Start_Medium");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Commmit_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "TxConnectionEventListener.java", "weblogic.connector.transaction.outbound.TxConnectionEventListener", "localTransactionStarted", "(Ljavax/resource/spi/ConnectionEvent;)V", 43, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Outbound_Transaction_Start_Medium, _WLDF$INST_FLD_Connector_Before_Tx};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "TxConnectionEventListener.java", "weblogic.connector.transaction.outbound.TxConnectionEventListener", "connectionErrorOccurred", "(Ljavax/resource/spi/ConnectionEvent;)V", 59, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Connection_Error_Low, _WLDF$INST_FLD_Connector_Before_Outbound};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "TxConnectionEventListener.java", "weblogic.connector.transaction.outbound.TxConnectionEventListener", "localTransactionRolledback", "(Ljavax/resource/spi/ConnectionEvent;)V", 72, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low, _WLDF$INST_FLD_Connector_Before_Tx};
      _WLDF$INST_JPFLD_3 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "TxConnectionEventListener.java", "weblogic.connector.transaction.outbound.TxConnectionEventListener", "localTransactionCommitted", "(Ljavax/resource/spi/ConnectionEvent;)V", 88, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_3 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Before_Tx};
   }
}
