package weblogic.ejb.container.internal;

import javax.ejb.AccessLocalException;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.NoSuchEJBException;
import javax.ejb.RemoveException;
import javax.transaction.Transaction;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb20.interfaces.LocalHandle;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.internal.LocalHandleImpl;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.security.service.ContextHandler;
import weblogic.utils.StackTraceUtilsClient;

public abstract class EntityEJBLocalObject extends BaseLocalObject implements BaseEJBLocalObjectIntf {
   private BaseEJBLocalHome ejbLocalHome;
   private Object primaryKey;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = 9206079926341096859L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.EntityEJBLocalObject");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   void setEJBLocalHome(BaseEJBLocalHome h) {
      this.ejbLocalHome = h;
   }

   protected final boolean isIdentical(MethodDescriptor md, EJBLocalObject o) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[]{o}));

      try {
         return o != null && this.primaryKey.equals(((EntityEJBLocalObject)o).getPrimaryKey()) && this.ejbLocalHome.isIdenticalTo(o.getEJBLocalHome());
      } catch (ClassCastException var4) {
         return false;
      }
   }

   protected final EJBLocalHome getEJBLocalHome(MethodDescriptor md) {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.ejbLocalHome;
   }

   protected final Object getPrimaryKey(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.primaryKey;
   }

   public void setPrimaryKey(Object pk) {
      if (!$assertionsDisabled && pk == null) {
         throw new AssertionError();
      } else {
         this.primaryKey = pk;
      }
   }

   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws EJBException {
      if (!$assertionsDisabled && this.primaryKey == null) {
         throw new AssertionError();
      } else {
         wrap.setPrimaryKey(this.primaryKey);
         super.__WL_preInvoke(wrap, ch);
      }
   }

   protected void __WL_preInvokeLite(InvocationWrapper wrap, ContextHandler ch) throws EJBException {
      if (!$assertionsDisabled && this.primaryKey == null) {
         throw new AssertionError();
      } else {
         wrap.setPrimaryKey(this.primaryKey);
         super.preInvoke(wrap, ch, true, true);
      }
   }

   protected final void remove(MethodDescriptor md) throws RemoveException {
      LocalHolder var16;
      if ((var16 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var16.argsCapture) {
            var16.args = new Object[2];
            Object[] var10000 = var16.args;
            var10000[0] = this;
            var10000[1] = md;
         }

         InstrumentationSupport.createDynamicJoinPoint(var16);
         InstrumentationSupport.preProcess(var16);
         var16.resetPostBegin();
      }

      try {
         BeanInfo bi = ((BaseEJBLocalHome)this.getEJBLocalHome()).getBeanInfo();
         Transaction callerTx = null;
         Transaction invokeTx = null;
         RemoveException removeException = null;
         InvocationWrapper wrap = InvocationWrapper.newInstance(md);
         wrap.setPrimaryKey(this.primaryKey);

         try {
            ManagedInvocationContext mic = bi.setCIC();
            Throwable var8 = null;

            try {
               wrap.pushEnvironment(this.getBeanManager().getEnvironmentContext());
               if (!wrap.checkMethodPermissionsLocal(EJBContextHandler.EMPTY)) {
                  SecurityException se = new SecurityException("Security violation: insufficient permission to access method");
                  throw new AccessLocalException(se.getMessage(), se);
               }

               try {
                  MethodInvocationHelper.pushMethodObject(bi);
                  SecurityHelper.pushCallerPrincipal();
                  wrap.pushRunAsIdentity();
                  wrap.enforceTransactionPolicy();
                  invokeTx = wrap.getInvokeTx();
                  callerTx = wrap.getCallerTx();

                  try {
                     this.getBeanManager().remove(wrap);
                  } catch (InternalException var82) {
                     if (!(var82.getCause() instanceof NoSuchEJBException) && !(var82.getCause() instanceof ConcurrentAccessException)) {
                        throw var82;
                     }

                     throw new InternalException(var82.getMessage());
                  }
               } catch (InternalException var84) {
                  if (!(var84.detail instanceof RemoveException)) {
                     this.handleSystemException(wrap, var84);
                     throw new AssertionError("Should not reach here");
                  }

                  removeException = (RemoveException)var84.detail;
               } finally {
                  wrap.popRunAsIdentity();

                  try {
                     SecurityHelper.popCallerPrincipal();
                  } catch (PrincipalNotFoundException var80) {
                     EJBLogger.logErrorPoppingCallerPrincipal(var80);
                  }

               }

               try {
                  if (MethodInvocationHelper.popMethodObject(bi)) {
                     this.getBeanManager().handleUncommittedLocalTransaction(wrap);
                  }
               } catch (InternalException var83) {
                  throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var83);
               }

               if (wrap.runningInOurTx()) {
                  try {
                     if (debugLogger.isDebugEnabled()) {
                        debug("Committing tx: " + invokeTx);
                     }

                     invokeTx.commit();
                  } catch (Exception var81) {
                     EJBLogger.logErrorDuringCommit2(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var81));
                     throw EJBRuntimeUtils.asEJBException("Exception during commit:", var81);
                  }
               }

               if (removeException != null) {
                  throw removeException;
               }
            } catch (Throwable var86) {
               var8 = var86;
               throw var86;
            } finally {
               if (mic != null) {
                  if (var8 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var79) {
                        var8.addSuppressed(var79);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } finally {
            wrap.popEnvironment();

            try {
               TransactionService.resumeCallersTransaction(callerTx, invokeTx);
            } catch (InternalException var78) {
               throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var78);
            }
         }
      } catch (Throwable var89) {
         if (var16 != null) {
            var16.th = var89;
            InstrumentationSupport.postProcess(var16);
         }

         throw var89;
      }

      if (var16 != null) {
         InstrumentationSupport.postProcess(var16);
      }

   }

   protected final LocalHandle getLocalHandle(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.getLocalHandleObject();
   }

   public final LocalHandle getLocalHandleObject() {
      return new LocalHandleImpl(this, this.primaryKey);
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return o instanceof EntityEJBLocalObject ? this.isIdentical((EntityEJBLocalObject)o) : false;
      }
   }

   public int hashCode() {
      return this.primaryKey.hashCode();
   }

   public void operationsComplete() {
      Transaction tx = TransactionService.getTransaction();
      ((BaseEntityManager)this.getBeanManager()).operationsComplete(tx, this.primaryKey);
   }

   private static void debug(String s) {
      debugLogger.debug("[EntityEJBLocalObject] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBLocalObject.java", "weblogic.ejb.container.internal.EntityEJBLocalObject", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;)V", 89, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High};
      $assertionsDisabled = !EntityEJBLocalObject.class.desiredAssertionStatus();
   }
}
