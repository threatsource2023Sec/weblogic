package weblogic.ejb.container.internal;

import java.rmi.AccessException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
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
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.EntityEJBObjectIntf;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb.container.manager.BaseEntityManager;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.internal.HandleImpl;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.logging.Loggable;
import weblogic.rmi.extensions.NotificationListener;
import weblogic.security.service.ContextHandler;
import weblogic.utils.StackTraceUtilsClient;

public abstract class EntityEJBObject extends BaseRemoteObject implements EntityEJBObjectIntf, Remote, NotificationListener {
   private Object primaryKey;
   // $FF: synthetic field
   static final boolean $assertionsDisabled;
   static final long serialVersionUID = -7873882532146688354L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.ejb.container.internal.EntityEJBObject");
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High;
   static final DelegatingMonitor _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   protected final Handle getHandle(MethodDescriptor md) throws RemoteException {
      ManagedInvocationContext mic = this.beanInfo.setCIC();
      Throwable var3 = null;

      HandleImpl var4;
      try {
         InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
         var4 = new HandleImpl(this, this.primaryKey);
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

      return var4;
   }

   protected final EJBHome getEJBHome(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      return this.ejbHome;
   }

   protected final boolean isIdentical(MethodDescriptor md, EJBObject o) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[]{o}));
      return o != null && this.primaryKey.equals(o.getPrimaryKey()) && this.ejbHome.isIdenticalTo(o.getEJBHome());
   }

   public void setPrimaryKey(Object pk) {
      LocalHolder var2;
      if ((var2 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var2.argsCapture) {
            var2.args = InstrumentationSupport.toSensitive(2);
         }

         InstrumentationSupport.createDynamicJoinPoint(var2);
         InstrumentationSupport.preProcess(var2);
         var2.resetPostBegin();
      }

      try {
         if (!$assertionsDisabled && pk == null) {
            throw new AssertionError();
         }

         this.primaryKey = pk;
      } catch (Throwable var4) {
         if (var2 != null) {
            var2.th = var4;
            InstrumentationSupport.postProcess(var2);
         }

         throw var4;
      }

      if (var2 != null) {
         InstrumentationSupport.postProcess(var2);
      }

   }

   protected final Object getPrimaryKey(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      return this.primaryKey;
   }

   public Object getActivationID() {
      return this.primaryKey;
   }

   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws RemoteException {
      if (!$assertionsDisabled && this.primaryKey == null) {
         throw new AssertionError();
      } else {
         wrap.setPrimaryKey(this.primaryKey);
         super.__WL_preInvoke(wrap, ch);
      }
   }

   protected final void remove(MethodDescriptor md) throws RemoteException, RemoveException {
      LocalHolder var18;
      if ((var18 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var18.argsCapture) {
            var18.args = new Object[2];
            Object[] var10000 = var18.args;
            var10000[0] = this;
            var10000[1] = md;
         }

         InstrumentationSupport.createDynamicJoinPoint(var18);
         InstrumentationSupport.preProcess(var18);
         var18.resetPostBegin();
      }

      try {
         BaseEJBHome ejbHome = (BaseEJBHome)this.getEJBHome();
         BeanInfo bi = ejbHome.getBeanInfo();
         Transaction callerTx = null;
         Transaction invokeTx = null;
         RemoveException removeException = null;
         InvocationWrapper wrap = InvocationWrapper.newInstance(md);
         wrap.setPrimaryKey(this.primaryKey);

         try {
            ManagedInvocationContext mic = bi.setCIC();
            Throwable var9 = null;

            try {
               wrap.pushEnvironment(this.getBeanManager().getEnvironmentContext());
               if (!wrap.checkMethodPermissionsRemote(EJBContextHandler.EMPTY)) {
                  Loggable l = EJBLogger.loginsufficientPermissionToUserLoggable(SecurityHelper.getCurrentPrincipal().getName(), "remove");
                  SecurityException se = new SecurityException(l.getMessageText());
                  throw new AccessException(se.getMessage(), se);
               }

               try {
                  MethodInvocationHelper.pushMethodObject(bi);
                  SecurityHelper.pushCallerPrincipal();
                  wrap.pushRunAsIdentity();
                  wrap.enforceTransactionPolicy();
                  invokeTx = wrap.getInvokeTx();
                  callerTx = wrap.getCallerTx();
                  this.pushInvocationWrapperInThreadLocal(wrap);

                  try {
                     this.getBeanManager().remove(wrap);
                  } catch (InternalException var84) {
                     if (!(var84.getCause() instanceof NoSuchEJBException) && !(var84.getCause() instanceof ConcurrentAccessException)) {
                        throw var84;
                     }

                     throw new InternalException(var84.getMessage(), var84.getCause().getCause());
                  }
               } catch (InternalException var86) {
                  if (!(var86.detail instanceof RemoveException)) {
                     this.handleSystemException(wrap, var86);
                     throw new AssertionError("Should not reach here");
                  }

                  removeException = (RemoveException)var86.detail;
               } finally {
                  wrap.popRunAsIdentity();
                  if (((EntityEJBHome)ejbHome).getIsNoObjectActivation()) {
                     ((EntityEJBHome)ejbHome).releaseEO(this.primaryKey);
                  }

                  try {
                     SecurityHelper.popCallerPrincipal();
                  } catch (PrincipalNotFoundException var82) {
                     EJBLogger.logErrorPoppingCallerPrincipal(var82);
                  }

               }

               try {
                  if (MethodInvocationHelper.popMethodObject(bi)) {
                     this.getBeanManager().handleUncommittedLocalTransaction(wrap);
                  }
               } catch (InternalException var85) {
                  throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", var85);
               }

               if (wrap.runningInOurTx()) {
                  try {
                     if (debugLogger.isDebugEnabled()) {
                        debug("Committing tx: " + invokeTx);
                     }

                     invokeTx.commit();
                  } catch (Exception var83) {
                     EJBLogger.logErrorDuringCommit2(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var83));
                     throw EJBRuntimeUtils.asRemoteException("Exception during commit:", var83);
                  }
               }

               if (removeException != null) {
                  throw removeException;
               }
            } catch (Throwable var88) {
               var9 = var88;
               throw var88;
            } finally {
               if (mic != null) {
                  if (var9 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var81) {
                        var9.addSuppressed(var81);
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
            } catch (InternalException var80) {
               throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", var80);
            }
         }
      } catch (Throwable var91) {
         if (var18 != null) {
            var18.th = var91;
            InstrumentationSupport.postProcess(var18);
         }

         throw var91;
      }

      if (var18 != null) {
         InstrumentationSupport.postProcess(var18);
      }

   }

   public void notifyRemoteCallBegin() {
      currentInvocationWrapper.push(new BaseRemoteObject.ThreadLocalObject(true, this));
   }

   protected void pushInvocationWrapperInThreadLocal(InvocationWrapper wrap) {
      Object o = currentInvocationWrapper.get();
      if (o instanceof BaseRemoteObject.ThreadLocalObject) {
         BaseRemoteObject.ThreadLocalObject tlo = (BaseRemoteObject.ThreadLocalObject)o;
         if (tlo.isRemote() && tlo.getBaseRemoteObject() == this) {
            currentInvocationWrapper.pop();
            currentInvocationWrapper.push(wrap);
            wrap.setIsRemoteInvocation();
         }
      }

   }

   protected void popInvocationWrapperInThreadLocalOnError(InvocationWrapper wrap) {
   }

   protected void setTxCreateAttributeOnBean(InvocationWrapper wrap) {
      WLEntityBean bean = (WLEntityBean)wrap.getBean();
      if (bean != null && wrap.runningInOurTx()) {
         bean.__WL_setCreatorOfTx(wrap.isRemoteInvocation());
      } else if (bean != null) {
         bean.__WL_setCreatorOfTx(false);
      }

   }

   public void notifyRemoteCallEnd() {
      Object o = currentInvocationWrapper.get();
      if (o != null) {
         if (o instanceof InvocationWrapper) {
            InvocationWrapper invocationWrapper = (InvocationWrapper)currentInvocationWrapper.pop();
            if ((invocationWrapper.runningInOurTx() || invocationWrapper.getInvokeTx() == null) && !invocationWrapper.hasSystemExceptionOccured()) {
               Object eBean = invocationWrapper.getBean();
               Class beanClass = this.beanInfo.getGeneratedBeanClass();
               if (eBean != null && eBean.getClass() == beanClass && (((WLEntityBean)eBean).__WL_isCreatorOfTx() || invocationWrapper.getInvokeTx() == null)) {
                  ((WLEntityBean)eBean).__WL_setCreatorOfTx(false);
                  this.getBeanManager().releaseBean(invocationWrapper);
               }
            }
         } else if (o instanceof BaseRemoteObject.ThreadLocalObject) {
            BaseRemoteObject.ThreadLocalObject tlo = (BaseRemoteObject.ThreadLocalObject)o;
            if (tlo.isRemote()) {
               currentInvocationWrapper.pop();
            }
         }

      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o instanceof EntityEJBObject) {
         boolean returnValue = false;

         try {
            returnValue = this.isIdentical((EntityEJBObject)o);
            return returnValue;
         } catch (RemoteException var4) {
            throw new EJBException(StackTraceUtilsClient.throwable2StackTrace(var4));
         }
      } else {
         return false;
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
      debugLogger.debug("[EntityEJBObject] " + s);
   }

   static {
      _WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Database_Access_Around_High");
      _WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBObject.java", "weblogic.ejb.container.internal.EntityEJBObject", "setPrimaryKey", "(Ljava/lang/Object;)V", 63, "", "", "", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Database_Access_Around_High};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "EntityEJBObject.java", "weblogic.ejb.container.internal.EntityEJBObject", "remove", "(Lweblogic/ejb/container/internal/MethodDescriptor;)V", 88, "", "", "", InstrumentationSupport.makeMap(new String[]{"EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("md", "weblogic.diagnostics.instrumentation.gathering.EJBMethodDescriptorRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_EJB_Diagnostic_Business_Method_Postinvoke_Cleanup_Around_High};
      $assertionsDisabled = !EntityEJBObject.class.desiredAssertionStatus();
   }
}
