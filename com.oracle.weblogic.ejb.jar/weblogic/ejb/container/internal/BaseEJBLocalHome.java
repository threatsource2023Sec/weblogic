package weblogic.ejb.container.internal;

import javax.ejb.AccessLocalException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.RemoveException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.LocalHomeHandle;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.internal.LocalHomeHandleImpl;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.security.service.ContextHandler;
import weblogic.transaction.AppSetRollbackOnlyException;
import weblogic.transaction.RollbackException;
import weblogic.utils.StackTraceUtilsClient;

public abstract class BaseEJBLocalHome implements BaseEJBLocalHomeIntf {
   protected static final DebugLogger debugLogger;
   protected Class eloClass;
   protected DeploymentInfo deploymentInfo;
   protected ClientDrivenBeanInfo beanInfo;
   protected BeanManager beanManager;

   BaseEJBLocalHome(Class eloClass) {
      this.eloClass = eloClass;
   }

   public void setup(BeanInfo bi, BeanManager bm) throws WLDeploymentException {
      this.beanInfo = (ClientDrivenBeanInfo)bi;
      this.deploymentInfo = this.beanInfo.getDeploymentInfo();
      this.beanManager = bm;
   }

   public BeanInfo getBeanInfo() {
      return this.beanInfo;
   }

   protected boolean isIdenticalTo(EJBLocalHome elh) {
      try {
         return this.beanInfo.getFullyQualifiedName().equals(((BaseEJBLocalHome)elh).beanInfo.getFullyQualifiedName());
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public String getJNDINameAsString() {
      return this.beanInfo.getLocalJNDINameAsString();
   }

   public abstract void remove(MethodDescriptor var1, Object var2) throws RemoveException;

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   public void undeploy() {
      String n = this.getJNDINameAsString();
      if (n != null) {
         try {
            (new InitialContext()).unbind(n);
         } catch (NamingException var3) {
            throw new AssertionError("Unbind of " + n + " failed. " + StackTraceUtilsClient.throwable2StackTrace(var3));
         }
      }

   }

   public abstract BaseEJBLocalObjectIntf allocateELO(Object var1);

   public abstract BaseEJBLocalObjectIntf allocateELO();

   void handleSystemException(InvocationWrapper wrap, Throwable ee) throws EJBException {
      BaseLocalObject.handleSystemException(wrap, this.beanInfo.usesBeanManagedTx(), ee);
   }

   protected InvocationWrapper preHomeInvoke(MethodDescriptor md, ContextHandler ch) throws EJBException {
      InvocationWrapper wrap = InvocationWrapper.newInstance(md);
      wrap.setCIC(this.beanInfo);

      try {
         wrap.checkMethodPermissionsLocal(ch);
      } catch (AccessLocalException var7) {
         wrap.unsetCIC();
         throw var7;
      }

      wrap.pushSecurityContext(ch);

      try {
         wrap.enforceTransactionPolicy();
      } catch (InternalException var8) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[BaseEJBLocalHome] Failed to enforce t/x policy", var8);
         }

         try {
            Transaction tx = TransactionService.getTransaction();
            if (tx != null) {
               tx.setRollbackOnly();
            }
         } catch (Exception var6) {
            EJBLogger.logErrorMarkingForRollback(var6);
         }

         wrap.unsetCIC();
         wrap.popSecurityContext();
         throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var8);
      }

      MethodInvocationHelper.pushMethodObject(this.getBeanInfo());
      SecurityHelper.pushCallerPrincipal();
      wrap.pushRunAsIdentity();
      return wrap;
   }

   private void postHomeInvokeNoInvokeTx(MethodDescriptor md, InvocationWrapper wrap) throws EJBException {
      if (wrap.getInvokeTx() == null) {
         try {
            this.getBeanManager().beforeCompletion(wrap);
            this.getBeanManager().afterCompletion(wrap);
         } catch (InternalException var4) {
            if (EJBRuntimeUtils.isAppException(md.getMethod(), var4)) {
               throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var4);
            }

            this.handleSystemException(wrap, var4);
            throw new AssertionError("Should never have reached here");
         }
      }

   }

   protected void postHomeInvoke(InvocationWrapper wrap, Throwable ee) throws EJBException {
      Transaction invokeTx = wrap.getInvokeTx();
      Transaction callerTx = wrap.getCallerTx();
      MethodDescriptor md = wrap.getMethodDescriptor();

      try {
         this.postHomeInvokeNoInvokeTx(md, wrap);
         if (invokeTx != null && !invokeTx.equals(callerTx)) {
            int st = -1;

            try {
               st = invokeTx.getStatus();
            } catch (SystemException var21) {
            }

            switch (st) {
               case 0:
                  try {
                     if (ee != null && this.beanInfo.isEJB30() && this.deploymentInfo.getExceptionInfo(md.getMethod(), ee).isRollback()) {
                        try {
                           invokeTx.rollback();
                        } catch (Exception var20) {
                           EJBLogger.logErrorDuringRollback1(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var20));
                        }
                        break;
                     }

                     invokeTx.commit();
                     break;
                  } catch (Exception var22) {
                     Exception e = var22;
                     if (var22 instanceof RollbackException && ((RollbackException)var22).getNested() instanceof AppSetRollbackOnlyException) {
                        break;
                     }

                     try {
                        this.getBeanManager().destroyInstance(wrap, e);
                     } catch (InternalException var17) {
                        EJBLogger.logErrorDuringCommit(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var17));
                     }

                     throw EJBRuntimeUtils.asEJBException("Error committing transaction:", var22);
                  }
               case 1:
                  try {
                     invokeTx.rollback();
                  } catch (Exception var19) {
                     EJBLogger.logErrorDuringRollback(invokeTx.toString(), StackTraceUtilsClient.throwable2StackTrace(var19));
                  }
            }
         } else if (invokeTx != null && invokeTx.equals(callerTx) && ee != null && this.beanInfo.isEJB30() && this.deploymentInfo.getExceptionInfo(md.getMethod(), ee).isRollback()) {
            try {
               callerTx.setRollbackOnly();
            } catch (Exception var18) {
               EJBLogger.logExcepDuringSetRollbackOnly(var18);
            }
         }
      } finally {
         this.postHomeInvokeCleanup(wrap);
      }

   }

   protected void postHomeInvokeCleanup(InvocationWrapper wrap) throws EJBException {
      Transaction invokeTx = wrap.getInvokeTx();
      Transaction callerTx = wrap.getCallerTx();
      wrap.popSecurityContext();
      wrap.popRunAsIdentity();

      try {
         SecurityHelper.popCallerPrincipal();
      } catch (PrincipalNotFoundException var9) {
         EJBLogger.logErrorPoppingCallerPrincipal(var9);
      }

      try {
         if (MethodInvocationHelper.popMethodObject(this.getBeanInfo())) {
            this.getBeanManager().handleUncommittedLocalTransaction(wrap);
         }

         TransactionService.resumeCallersTransaction(callerTx, invokeTx);
      } catch (InternalException var10) {
         throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var10);
      } finally {
         wrap.unsetCIC();
      }

   }

   public LocalHomeHandle getLocalHomeHandle(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.getLocalHomeHandleObject();
   }

   public LocalHomeHandle getLocalHomeHandleObject() {
      return new LocalHomeHandleImpl(this, this.beanInfo.getLocalJNDIName());
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
   }
}
