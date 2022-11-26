package weblogic.connector.transaction.outbound;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.connector.common.Debug;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionManager;

public final class ResourceRegistrationManager {
   private TxConnectionHandler registeredHandler = null;
   private HashSet hashRegistrations = new HashSet();
   static final long serialVersionUID = -4902494837820608521L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.transaction.outbound.ResourceRegistrationManager");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Unregister_Resource_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Register_Resource_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Enlist_Resource_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;
   static final JoinPoint _WLDF$INST_JPFLD_2;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_2;

   public synchronized void addResource(TxConnectionHandler transHdlr) throws SystemException {
      if (this.registeredHandler == null) {
         this.registerResource(transHdlr);
         this.registeredHandler = transHdlr;
      }

      this.hashRegistrations.add(transHdlr);
   }

   public synchronized void enlistResource(TxConnectionHandler transHdlr, Transaction tx) throws RollbackException, IllegalStateException, SystemException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[3];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = transHdlr;
            var10000[2] = tx;
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.preProcess(var4);
         }

         if (var4.monitorHolder[1] != null) {
            var4.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[3] != null) {
            var4.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var4);
            InstrumentationSupport.process(var4);
         }

         var4.resetPostBegin();
      }

      try {
         if (this.registeredHandler == null) {
            this.registerResource(transHdlr);
            this.registeredHandler = transHdlr;
         }

         if (transHdlr instanceof XATxConnectionHandler) {
            tx.enlistResource(((XATxConnectionHandler)transHdlr).getXAResource());
         } else {
            if (!(transHdlr instanceof LocalTxConnectionHandler)) {
               String exMsg = Debug.getExceptionEnlistResourceIllegalType();
               throw new SystemException(exMsg);
            }

            ((weblogic.transaction.Transaction)tx).enlistResource(((LocalTxConnectionHandler)transHdlr).getNonXAResource());
         }
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            if (var4.monitorHolder[2] != null) {
               var4.monitorIndex = 2;
               InstrumentationSupport.process(var4);
            }

            if (var4.monitorHolder[0] != null) {
               var4.monitorIndex = 0;
               InstrumentationSupport.postProcess(var4);
            }
         }

         throw var6;
      }

      if (var4 != null) {
         if (var4.monitorHolder[2] != null) {
            var4.monitorIndex = 2;
            InstrumentationSupport.process(var4);
         }

         if (var4.monitorHolder[0] != null) {
            var4.monitorIndex = 0;
            InstrumentationSupport.postProcess(var4);
         }
      }

   }

   public synchronized void removeResource(TxConnectionHandler transHdlr) {
      this.hashRegistrations.remove(transHdlr);
      if (this.registeredHandler == transHdlr) {
         this.unregisterResource(transHdlr);
         if (this.hashRegistrations.isEmpty()) {
            this.registeredHandler = null;
         } else {
            Iterator iter = this.hashRegistrations.iterator();
            TxConnectionHandler newHandler = (TxConnectionHandler)iter.next();

            try {
               this.registerResource(newHandler);
               this.registeredHandler = newHandler;
            } catch (SystemException var5) {
               if (Debug.isXAoutEnabled()) {
                  Debug.xaOut(newHandler.getPool(), "WARNING:  Failed to switch registration to other available resource:  " + var5);
               }

               this.registeredHandler = null;
            }
         }
      }

   }

   private void registerResource(TxConnectionHandler transHandler) throws SystemException {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[2];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = transHandler;
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.preProcess(var3);
         }

         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[3] != null) {
            var3.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      try {
         if (transHandler instanceof XATxConnectionHandler) {
            Hashtable props = new Hashtable();
            props.put("weblogic.transaction.registration.type", "standard");
            props.put("weblogic.transaction.registration.settransactiontimeout", "true");
            ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerResource(transHandler.getPool().getNameWithPartitionName(), ((XATxConnectionHandler)transHandler).getXAResource(), props);
         } else {
            if (!(transHandler instanceof LocalTxConnectionHandler)) {
               String exMsg = Debug.getExceptionRegisterResourceIllegalType(transHandler.getClass().getName());
               throw new SystemException(exMsg);
            }

            ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).registerDynamicResource(transHandler.getPool().getNameWithPartitionName(), ((LocalTxConnectionHandler)transHandler).getNonXAResource());
         }
      } catch (Throwable var5) {
         if (var3 != null) {
            var3.th = var5;
            if (var3.monitorHolder[1] != null) {
               var3.monitorIndex = 1;
               InstrumentationSupport.process(var3);
            }

            if (var3.monitorHolder[0] != null) {
               var3.monitorIndex = 0;
               InstrumentationSupport.postProcess(var3);
            }
         }

         throw var5;
      }

      if (var3 != null) {
         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.postProcess(var3);
         }
      }

   }

   private void unregisterResource(TxConnectionHandler transHandler) {
      LocalHolder var3;
      if ((var3 = LocalHolder.getInstance(_WLDF$INST_JPFLD_2, _WLDF$INST_JPFLD_JPMONS_2)) != null) {
         if (var3.argsCapture) {
            var3.args = new Object[2];
            Object[] var10000 = var3.args;
            var10000[0] = this;
            var10000[1] = transHandler;
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.preProcess(var3);
         }

         if (var3.monitorHolder[1] != null) {
            var3.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[3] != null) {
            var3.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var3);
            InstrumentationSupport.process(var3);
         }

         var3.resetPostBegin();
      }

      try {
         try {
            ((TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager()).unregisterResource(transHandler.getPool().getNameWithPartitionName());
         } catch (SystemException var6) {
            if (Debug.isXAoutEnabled()) {
               Debug.xaOut(transHandler.getPool(), "WARNING:  Failed to unregister resource for destroyed connection:  " + var6);
            }
         }
      } catch (Throwable var7) {
         if (var3 != null) {
            var3.th = var7;
            if (var3.monitorHolder[2] != null) {
               var3.monitorIndex = 2;
               InstrumentationSupport.process(var3);
            }

            if (var3.monitorHolder[0] != null) {
               var3.monitorIndex = 0;
               InstrumentationSupport.postProcess(var3);
            }
         }

         throw var7;
      }

      if (var3 != null) {
         if (var3.monitorHolder[2] != null) {
            var3.monitorIndex = 2;
            InstrumentationSupport.process(var3);
         }

         if (var3.monitorHolder[0] != null) {
            var3.monitorIndex = 0;
            InstrumentationSupport.postProcess(var3);
         }
      }

   }

   static {
      _WLDF$INST_FLD_Connector_Around_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Tx");
      _WLDF$INST_FLD_Connector_Unregister_Resource_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Unregister_Resource_Low");
      _WLDF$INST_FLD_Connector_After_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Tx");
      _WLDF$INST_FLD_Connector_Before_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Tx");
      _WLDF$INST_FLD_Connector_Register_Resource_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Register_Resource_Low");
      _WLDF$INST_FLD_Connector_Enlist_Resource_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Enlist_Resource_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ResourceRegistrationManager.java", "weblogic.connector.transaction.outbound.ResourceRegistrationManager", "enlistResource", "(Lweblogic/connector/transaction/outbound/TxConnectionHandler;Ljavax/transaction/Transaction;)V", 85, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Around_Tx", "Connector_Enlist_Resource_Low", "Connector_Before_Tx", "Connector_After_Tx"}, new PointcutHandlingInfo[]{null, InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionHandlerPoolRenderer", false, true), null}), null, null}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_Enlist_Resource_Low, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Before_Tx};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ResourceRegistrationManager.java", "weblogic.connector.transaction.outbound.ResourceRegistrationManager", "registerResource", "(Lweblogic/connector/transaction/outbound/TxConnectionHandler;)V", 170, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Around_Tx", "Connector_Register_Resource_Low", "Connector_Before_Tx", "Connector_After_Tx"}, new PointcutHandlingInfo[]{null, InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionHandlerPoolRenderer", false, true)}), null, null}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Register_Resource_Low, _WLDF$INST_FLD_Connector_Before_Tx};
      _WLDF$INST_JPFLD_2 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "ResourceRegistrationManager.java", "weblogic.connector.transaction.outbound.ResourceRegistrationManager", "unregisterResource", "(Lweblogic/connector/transaction/outbound/TxConnectionHandler;)V", 207, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Around_Tx", "Connector_Before_Tx", "Connector_After_Tx", "Connector_Unregister_Resource_Low"}, new PointcutHandlingInfo[]{null, null, null, InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionHandlerPoolRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_2 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_Unregister_Resource_Low, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Before_Tx};
   }
}
