package weblogic.connector.transaction.outbound;

import java.security.AccessController;
import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnection;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;
import weblogic.connector.common.Debug;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.NoEnlistXAResourceException;
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
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.transaction.TxHelper;
import weblogic.utils.StackTraceUtils;

public final class XATxConnectionHandler extends TxConnectionHandler {
   boolean enlistableXARes;
   XAResource xaRes;
   RecoveryOnlyXAWrapper recoveryWrapper = null;
   static final long serialVersionUID = 3242721431232342780L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.connector.transaction.outbound.XATxConnectionHandler");
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_After_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Around_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Before_Outbound;
   static final DelegatingMonitor _WLDF$INST_FLD_Connector_Destroy_Connection_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public XATxConnectionHandler(ManagedConnection mc, ConnectionPool connPool, SecurityContext secCtx, ConnectionInfo connectionInfo) throws ResourceException {
      super(mc, connPool, secCtx, connectionInfo, "XATransaction");
      this.setGlobalTransactionInProgress(false);
      this.enlistableXARes = true;
      this.initializeXAResource();
      this.addConnectionRuntimeMBean();
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
         if (this.recoveryWrapper != null) {
            this.recoveryWrapper.destroy();
            this.recoveryWrapper = null;
         }

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

   public void enListResource() throws ResourceException {
      if (this.transaction == null) {
         Transaction activeTrans = TxHelper.getTransaction();
         if (activeTrans != null && this.enlistableXARes) {
            Exception e = null;
            TxCompletionNotification transNotif = null;
            String exMsg;
            if (!this.connPool.getRwLock4ReregisterXAResource().readLock().tryLock()) {
               if (Debug.isXAoutEnabled()) {
                  this.debug("Failed to read rwLock4ReregisterXAResource of the connection pool " + this.connPool.getName() + ". ConnectionPoolMaintainer is trying to re-register XAResource.\n");
               }

               exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), "Failed to read the lock rwLock4ReregisterXAResource");
               throw new ResourceException(exMsg, e);
            }

            boolean var11 = false;

            label153: {
               AuthenticatedSubject kernelId;
               label152: {
                  try {
                     Exception ex;
                     try {
                        var11 = true;
                        TxCompletionNotification.register(activeTrans, this);
                        ConnectionInfo otherConnection = this.connPool.getConnectionSharingManager().getSharedConnection();
                        if (otherConnection != null && otherConnection != this.connectionInfo) {
                           int altCount = this.connPool.getAlternateCount();
                           ((weblogic.transaction.Transaction)activeTrans).enlistResource(this.xaRes, this.connPool.getName() + "_branchalias_" + altCount);
                        } else {
                           activeTrans.enlistResource(this.xaRes);
                        }

                        this.connPool.getConnectionSharingManager().addSharedConnection(super.getConnectionInfo());
                        var11 = false;
                        break label152;
                     } catch (Exception var13) {
                        ex = var13;
                        e = var13;
                        if (transNotif != null) {
                           ((TxCompletionNotification)transNotif).deregister();
                        }
                     }

                     try {
                        String exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), ex.toString());
                        ((weblogic.transaction.Transaction)activeTrans).setRollbackOnly(exMsg, ex);
                        var11 = false;
                     } catch (Exception var12) {
                        if (Debug.isXAoutEnabled()) {
                           this.debug("Failed to setRollbackOnly after enlistment failure:  " + var12 + StackTraceUtils.throwable2StackTrace(var12));
                           var11 = false;
                        } else {
                           var11 = false;
                        }
                     }
                  } finally {
                     if (var11) {
                        this.connPool.getRwLock4ReregisterXAResource().readLock().unlock();
                        if (e != null) {
                           if (Debug.isXAoutEnabled()) {
                              AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                              this.debug("Failed to setup the connection for transaction enlistment:\n" + this.getRAiM().getAdapterLayer().throwable2StackTrace(e, kernelId));
                           }

                           String exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), e.toString());
                           throw new ResourceException(exMsg, e);
                        }

                     }
                  }

                  this.connPool.getRwLock4ReregisterXAResource().readLock().unlock();
                  if (e != null) {
                     if (Debug.isXAoutEnabled()) {
                        kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                        this.debug("Failed to setup the connection for transaction enlistment:\n" + this.getRAiM().getAdapterLayer().throwable2StackTrace(e, kernelId));
                     }

                     exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), e.toString());
                     throw new ResourceException(exMsg, e);
                  }
                  break label153;
               }

               this.connPool.getRwLock4ReregisterXAResource().readLock().unlock();
               if (e != null) {
                  if (Debug.isXAoutEnabled()) {
                     kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                     this.debug("Failed to setup the connection for transaction enlistment:\n" + this.getRAiM().getAdapterLayer().throwable2StackTrace(e, kernelId));
                  }

                  exMsg = Debug.getExceptionEnlistmentFailed(this.connPool.getKey(), e.toString());
                  throw new ResourceException(exMsg, e);
               }
            }

            this.transaction = activeTrans;
            this.setGlobalTransactionInProgress(true);
         }
      }

   }

   public void initializeXAResource() throws ResourceException {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      String exString;
      String causeExString;
      String msgId;
      try {
         this.xaRes = this.getRAiM().getAdapterLayer().getXAResource(this.managedConnection, kernelId);
      } catch (NoEnlistXAResourceException var7) {
         this.enlistableXARes = false;
         this.debug("Using Unenlistable XA Resource");
      } catch (ResourceException var8) {
         if (Debug.isXAoutEnabled()) {
            this.debug("XATxConnectionHandler:  Resource Adapter with Key = " + this.connPool.getKey() + " threw ResourceException from its implementation of ManagedConnection.getXAResource(), " + this.getRAiM().getAdapterLayer().toString(var8, kernelId) + "\n" + this.getRAiM().getAdapterLayer().throwable2StackTrace(var8, kernelId));
         }

         exString = this.getRAiM().getAdapterLayer().toString(var8, kernelId);
         Throwable causeEx = this.getRAiM().getAdapterLayer().getCause(var8, kernelId);
         if (causeEx != null) {
            causeExString = this.getRAiM().getAdapterLayer().toString(causeEx, kernelId);
            msgId = Debug.logGetXAResourceError(causeExString, this.connPool.getKey());
            Debug.logStackTrace(msgId, causeEx);
            if (Debug.isXAoutEnabled()) {
               this.debug("XATxConnectionHandler:  ResourceException has Linked Exception:\n" + causeExString + "\n" + this.getRAiM().getAdapterLayer().throwable2StackTrace(causeEx, kernelId));
            }
         } else {
            causeExString = Debug.logGetXAResourceError(exString, this.connPool.getKey());
            Debug.logStackTrace(causeExString, var8);
         }

         throw var8;
      } catch (Throwable var9) {
         exString = this.getRAiM().getAdapterLayer().toString(var9, kernelId);
         String stackTraceString = this.getRAiM().getAdapterLayer().throwable2StackTrace(var9, kernelId);
         causeExString = Debug.getExceptionMCGetXAResourceThrewNonResourceException(var9.toString());
         msgId = Debug.logGetXAResourceError(exString, this.connPool.getKey());
         Debug.logStackTraceString(msgId, stackTraceString);
         Utils.throwAsResourceException(causeExString, var9);
      }

      if (this.enlistableXARes) {
         if (this.xaRes == null) {
            String msg = Debug.getExceptionMCGetXAResourceReturnedNull();
            Debug.logGetXAResourceError(msg, this.connPool.getKey());
            throw new ResourceException(msg);
         } else {
            this.xaRes = new XAWrapper(this.xaRes, this, this.connPool);
         }
      }
   }

   private void debug(String msg) {
      if (Debug.isXAoutEnabled()) {
         Debug.xaOut(this.connPool, "XATxConnectionHandler: " + msg);
      }

   }

   public XAResource getXAResource() {
      return this.xaRes;
   }

   boolean isEnlistableXARes() {
      return this.enlistableXARes;
   }

   public void setXARecoveryWrapper(RecoveryOnlyXAWrapper recoveryWrapper) {
      this.recoveryWrapper = recoveryWrapper;
   }

   protected void initializeConnectionEventListener() {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.getRAiM().getAdapterLayer().addConnectionEventListener(this.managedConnection, TxConnectionEventListener.create(this, "XATxConnEventListener", this.getRAiM().getPartitionName()), kernelId);
   }

   static {
      _WLDF$INST_FLD_Connector_After_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_After_Outbound");
      _WLDF$INST_FLD_Connector_Around_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Around_Outbound");
      _WLDF$INST_FLD_Connector_Before_Outbound = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Before_Outbound");
      _WLDF$INST_FLD_Connector_Destroy_Connection_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Connector_Destroy_Connection_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XATxConnectionHandler.java", "weblogic.connector.transaction.outbound.XATxConnectionHandler", "destroy", "()V", 76, "", "", "", InstrumentationSupport.makeMap(new String[]{"Connector_Destroy_Connection_Low", "Connector_After_Outbound", "Connector_Around_Outbound", "Connector_Before_Outbound"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo(InstrumentationSupport.createValueHandlingInfo("pool", "weblogic.diagnostics.instrumentation.gathering.JCAConnectionHandlerPoolRenderer", false, true), (ValueHandlingInfo)null, (ValueHandlingInfo[])null), null, null, null}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Connector_After_Outbound, _WLDF$INST_FLD_Connector_Around_Outbound, _WLDF$INST_FLD_Connector_Before_Outbound, _WLDF$INST_FLD_Connector_Destroy_Connection_Low};
   }
}
