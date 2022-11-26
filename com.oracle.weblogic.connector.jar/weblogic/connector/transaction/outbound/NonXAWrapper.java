package weblogic.connector.transaction.outbound;

import java.security.AccessController;
import java.util.Map;
import javax.resource.ResourceException;
import javax.resource.spi.LocalTransaction;
import javax.transaction.xa.Xid;
import weblogic.connector.common.Debug;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.nonxa.NonXAException;
import weblogic.transaction.nonxa.NonXAResource;

public final class NonXAWrapper implements NonXAResource {
   LocalTransaction localTransaction;
   LocalTxConnectionHandler connectionHandler;
   static final long serialVersionUID = -2969897281728638615L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.transaction.outbound.NonXAWrapper");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Tx;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   NonXAWrapper(LocalTransaction localTx, LocalTxConnectionHandler connectionHandler) {
      this.localTransaction = localTx;
      this.connectionHandler = connectionHandler;
   }

   public void commit(Xid xid, boolean var2) throws NonXAException {
      LocalHolder var9;
      if ((var9 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var9.argsCapture) {
            var9.args = InstrumentationSupport.toSensitive(3);
         }

         if (var9.monitorHolder[0] != null) {
            var9.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var9);
            InstrumentationSupport.preProcess(var9);
         }

         if (var9.monitorHolder[1] != null) {
            var9.monitorIndex = 1;
            InstrumentationSupport.createDynamicJoinPoint(var9);
            InstrumentationSupport.process(var9);
         }

         if (var9.monitorHolder[3] != null) {
            var9.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var9);
            InstrumentationSupport.process(var9);
         }

         var9.resetPostBegin();
      }

      try {
         this.debug(" - commit request for xid: " + xid);
         if (this.connectionHandler.isConnectionErrorOccurred()) {
            this.debug(" - connectionHandler.isConnectionErrorOccurred is true; not issuing commit");
            throw new NonXAException("The underlying ManagedConnection met error and is unusable; cannot commit");
         }

         if (this.localTransaction == null) {
            this.debug(" - localTransaction is null; not issuing commit");
            throw new NonXAException("The localTransaction is null; cannot commit");
         }

         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

         try {
            this.debug(" - issuing commit");
            this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().commit(this.localTransaction, kernelId);
         } catch (ResourceException var12) {
            String resExMsg = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(var12, kernelId);
            this.debug(" ResourceException during commit request: " + resExMsg);
            Throwable causeEx = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().getCause(var12, kernelId);
            String exMsg;
            if (null != causeEx) {
               exMsg = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(causeEx, kernelId);
               this.debug("ResourceException has Linked Exception : " + exMsg);
            }

            exMsg = Debug.getExceptionCommitFailed(resExMsg, this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().throwable2StackTrace(var12, kernelId));
            NonXAException nonXaEx = new NonXAException(exMsg);
            nonXaEx.initCause(var12);
            throw nonXaEx;
         }
      } catch (Throwable var13) {
         if (var9 != null) {
            var9.th = var13;
            if (var9.monitorHolder[2] != null) {
               var9.monitorIndex = 2;
               InstrumentationSupport.process(var9);
            }

            if (var9.monitorHolder[0] != null) {
               var9.monitorIndex = 0;
               InstrumentationSupport.postProcess(var9);
            }
         }

         throw var13;
      }

      if (var9 != null) {
         if (var9.monitorHolder[2] != null) {
            var9.monitorIndex = 2;
            InstrumentationSupport.process(var9);
         }

         if (var9.monitorHolder[0] != null) {
            var9.monitorIndex = 0;
            InstrumentationSupport.postProcess(var9);
         }
      }

   }

   public void rollback(Xid xid) throws NonXAException {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var8.argsCapture) {
            var8.args = InstrumentationSupport.toSensitive(2);
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.preProcess(var8);
         }

         if (var8.monitorHolder[2] != null) {
            var8.monitorIndex = 2;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         if (var8.monitorHolder[3] != null) {
            var8.monitorIndex = 3;
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.process(var8);
         }

         var8.resetPostBegin();
      }

      label206: {
         label207: {
            try {
               this.debug(" - rollback request for xid: " + xid);
               if (this.connectionHandler.isConnectionErrorOccurred()) {
                  this.debug(" - isConnectionErrorOccurred is true; not issuing rollback");
                  break label206;
               }

               if (this.localTransaction == null) {
                  this.debug(" - localTransaction is null; not issuing rollback");
                  break label207;
               }

               AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

               try {
                  this.debug(" - issuing rollback");
                  this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().rollback(this.localTransaction, kernelId);
               } catch (ResourceException var11) {
                  String exString = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(var11, kernelId);
                  this.debug(" - rollback for xid: " + xid + " ResourceException : " + exString);
                  Throwable causeEx = this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().getCause(var11, kernelId);
                  if (null != causeEx) {
                     this.debug(" - rollback for xid: " + xid + " ResourceException has Linked Exception : " + this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().toString(causeEx, kernelId));
                  }

                  String exMsg = Debug.getExceptionRollbackFailed(exString, this.connectionHandler.getPool().getRAInstanceManager().getAdapterLayer().throwable2StackTrace(var11, kernelId));
                  NonXAException nonXaEx = new NonXAException(exMsg);
                  nonXaEx.initCause(var11);
                  throw nonXaEx;
               }
            } catch (Throwable var12) {
               if (var8 != null) {
                  var8.th = var12;
                  if (var8.monitorHolder[1] != null) {
                     var8.monitorIndex = 1;
                     InstrumentationSupport.process(var8);
                  }

                  if (var8.monitorHolder[0] != null) {
                     var8.monitorIndex = 0;
                     InstrumentationSupport.postProcess(var8);
                  }
               }

               throw var12;
            }

            if (var8 != null) {
               if (var8.monitorHolder[1] != null) {
                  var8.monitorIndex = 1;
                  InstrumentationSupport.process(var8);
               }

               if (var8.monitorHolder[0] != null) {
                  var8.monitorIndex = 0;
                  InstrumentationSupport.postProcess(var8);
               }
            }

            return;
         }

         if (var8 != null) {
            if (var8.monitorHolder[1] != null) {
               var8.monitorIndex = 1;
               InstrumentationSupport.process(var8);
            }

            if (var8.monitorHolder[0] != null) {
               var8.monitorIndex = 0;
               InstrumentationSupport.postProcess(var8);
            }
         }

         return;
      }

      if (var8 != null) {
         if (var8.monitorHolder[1] != null) {
            var8.monitorIndex = 1;
            InstrumentationSupport.process(var8);
         }

         if (var8.monitorHolder[0] != null) {
            var8.monitorIndex = 0;
            InstrumentationSupport.postProcess(var8);
         }
      }

   }

   void disable() {
      this.localTransaction = null;
   }

   private void debug(String msg) {
      if (Debug.isLocalOutEnabled()) {
         Debug.localOut(this.connectionHandler.getPool(), "NonXAWrapper: " + this + msg);
      }

   }

   public boolean isSameRM(NonXAResource nonXAresource) throws NonXAException {
      if (!(nonXAresource instanceof NonXAWrapper)) {
         return false;
      } else {
         return this.connectionHandler.getManagedConnection() == ((NonXAWrapper)nonXAresource).connectionHandler.getManagedConnection();
      }
   }

   static {
      _WLDF$INST_FLD_Connector_Around_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Tx");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Commmit_Medium");
      _WLDF$INST_FLD_Connector_After_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Tx");
      _WLDF$INST_FLD_Connector_Before_Tx = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Tx");
      _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Outbound_Transaction_Rollback_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "NonXAWrapper.java", "weblogic.connector.transaction.outbound.NonXAWrapper", "commit", "(Ljavax/transaction/xa/Xid;Z)V", 62, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_Outbound_Transaction_Commmit_Medium, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Before_Tx};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "NonXAWrapper.java", "weblogic.connector.transaction.outbound.NonXAWrapper", "rollback", "(Ljavax/transaction/xa/Xid;)V", 124, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_Around_Tx, _WLDF$INST_FLD_Connector_After_Tx, _WLDF$INST_FLD_Connector_Outbound_Transaction_Rollback_Low, _WLDF$INST_FLD_Connector_Before_Tx};
   }
}
