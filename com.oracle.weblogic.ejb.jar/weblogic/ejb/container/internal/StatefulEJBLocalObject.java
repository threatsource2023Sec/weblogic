package weblogic.ejb.container.internal;

import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.NoSuchEJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.RemoveException;
import javax.transaction.Transaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb20.interfaces.LocalHandle;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.internal.LocalHandleImpl;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.logging.Loggable;
import weblogic.security.service.ContextHandler;

public abstract class StatefulEJBLocalObject extends StatefulLocalObject implements BaseEJBLocalObjectIntf {
   private BaseEJBLocalHome ejbLocalHome;
   private Object primaryKey;

   void setEJBLocalHome(BaseEJBLocalHome h) {
      this.ejbLocalHome = h;
   }

   public void setPrimaryKey(Object pk) {
      this.primaryKey = pk;
   }

   public Object getPK() {
      return this.primaryKey;
   }

   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws EJBException {
      wrap.setPrimaryKey(this.primaryKey);
      super.__WL_preInvoke(wrap, ch);
   }

   protected final boolean isIdentical(MethodDescriptor md, EJBLocalObject o) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[]{o}));

      try {
         return o != null && this.getPK().equals(((StatefulEJBLocalObject)o).getPK()) && this.ejbLocalHome.isIdenticalTo(o.getEJBLocalHome());
      } catch (ClassCastException var4) {
         return false;
      }
   }

   protected final EJBLocalHome getEJBLocalHome(MethodDescriptor md) {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.ejbLocalHome;
   }

   protected final void remove(MethodDescriptor md) throws RemoveException, EJBException {
      Transaction callerTx = null;
      InvocationWrapper wrap = InvocationWrapper.newInstance(md, this.primaryKey);

      try {
         ManagedInvocationContext mic = this.beanInfo.setCIC();
         Throwable var5 = null;

         try {
            wrap.pushEnvironment(this.getBeanManager().getEnvironmentContext());
            wrap.checkMethodPermissionsLocal(EJBContextHandler.EMPTY);

            try {
               MethodInvocationHelper.pushMethodObject(this.beanInfo);
               SecurityHelper.pushCallerPrincipal();
               wrap.pushRunAsIdentity();
               wrap.enforceTransactionPolicy();
               callerTx = wrap.getCallerTx();

               try {
                  this.getBeanManager().remove(wrap);
               } catch (InternalException var50) {
                  if (var50.detail instanceof NoSuchEJBException) {
                     NoSuchObjectLocalException ee = new NoSuchObjectLocalException(var50.getMessage());
                     EJBRuntimeUtils.throwInternalException(var50.getMessage(), ee);
                  } else if (var50.detail instanceof ConcurrentAccessException) {
                     throw new InternalException(var50.getMessage());
                  }

                  throw var50;
               }
            } catch (InternalException var51) {
               if (var51.detail instanceof RemoveException) {
                  throw (RemoveException)var51.detail;
               }

               this.handleSystemException(wrap, var51);
               throw new AssertionError("should not reach");
            } finally {
               wrap.popRunAsIdentity();

               try {
                  SecurityHelper.popCallerPrincipal();
               } catch (PrincipalNotFoundException var49) {
                  EJBLogger.logErrorPoppingCallerPrincipal(var49);
               }

               MethodInvocationHelper.popMethodObject(this.beanInfo);
            }
         } catch (Throwable var53) {
            var5 = var53;
            throw var53;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var48) {
                     var5.addSuppressed(var48);
                  }
               } else {
                  mic.close();
               }
            }

         }
      } finally {
         wrap.popEnvironment();

         try {
            TransactionService.resumeCallersTransaction(callerTx, (Transaction)null);
         } catch (InternalException var47) {
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", var47);
         }
      }

   }

   protected final LocalHandle getLocalHandle(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.getLocalHandleObject();
   }

   public final LocalHandle getLocalHandleObject() {
      return new LocalHandleImpl(this, this.primaryKey);
   }

   protected final Object getPrimaryKey(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      Loggable l = EJBLogger.loglocalSessionBeanCannotCallGetPrimaryKeyLoggable();
      throw new EJBException(l.getMessageText());
   }
}
