package weblogic.ejb.container.internal;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.NoSuchEJBException;
import javax.ejb.RemoveException;
import javax.transaction.Transaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.StatefulEJBObjectIntf;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.internal.HandleImpl;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.logging.Loggable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.security.service.ContextHandler;

public abstract class StatefulEJBObject extends StatefulRemoteObject implements StatefulEJBObjectIntf {
   private Activator ejbActivator;
   private Object primaryKey;

   public void setPrimaryKey(Object pk) {
      this.primaryKey = pk;
   }

   public Object getPK() {
      return this.primaryKey;
   }

   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws RemoteException {
      wrap.setPrimaryKey(this.primaryKey);
      super.__WL_preInvoke(wrap, ch);
   }

   public Activator getActivator() {
      return this.ejbActivator;
   }

   public void setActivator(Activator ejbActivator) {
      this.ejbActivator = ejbActivator;
   }

   public Object getActivationID() {
      return this.primaryKey;
   }

   protected final Handle getHandle(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      return new HandleImpl(this, this.primaryKey);
   }

   protected final Object getPrimaryKey(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      Loggable l = EJBLogger.logsessionBeanCannotCallGetPrimaryKeyLoggable();
      throw new RemoteException(l.getMessageText());
   }

   protected final EJBHome getEJBHome(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      return this.ejbHome;
   }

   public final boolean isIdentical(MethodDescriptor md, EJBObject o) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[]{o}));

      try {
         return o != null && this.getPK().equals(((StatefulEJBObjectIntf)o).getPK()) && this.ejbHome.isIdenticalTo(o.getEJBHome());
      } catch (ClassCastException var4) {
         return false;
      }
   }

   protected final void remove(MethodDescriptor md) throws RemoteException, RemoveException {
      Transaction callerTx = null;
      InvocationWrapper wrap = InvocationWrapper.newInstance(md, this.primaryKey);

      try {
         ManagedInvocationContext mic = this.beanInfo.setCIC();
         Throwable var5 = null;

         try {
            wrap.pushEnvironment(this.getBeanManager().getEnvironmentContext());
            wrap.checkMethodPermissionsRemote(EJBContextHandler.EMPTY);
            boolean var48 = false;

            try {
               var48 = true;
               MethodInvocationHelper.pushMethodObject(this.beanInfo);
               SecurityHelper.pushCallerPrincipal();
               wrap.pushRunAsIdentity();
               wrap.enforceTransactionPolicy();
               callerTx = wrap.getCallerTx();

               try {
                  this.getBeanManager().remove(wrap);
                  var48 = false;
               } catch (InternalException var53) {
                  if (var53.detail instanceof NoSuchEJBException) {
                     NoSuchObjectException ee = new NoSuchObjectException(var53.getMessage());
                     EJBRuntimeUtils.throwInternalException(var53.getMessage(), ee);
                  } else if (var53.detail instanceof ConcurrentAccessException) {
                     EJBRuntimeUtils.throwInternalException(var53.getMessage(), var53.detail.getCause());
                  }

                  throw var53;
               }
            } catch (InternalException var54) {
               if (var54.detail instanceof RemoveException) {
                  throw (RemoveException)var54.detail;
               }

               if (var54.detail instanceof RemoteException) {
                  throw (RemoteException)var54.detail;
               }

               this.handleSystemException(wrap, var54);
               throw new AssertionError("should not reach");
            } finally {
               if (var48) {
                  StatefulEJBHome sfsbEjbHome = (StatefulEJBHome)this.getEJBHome();
                  if (sfsbEjbHome.getIsNoObjectActivation() || sfsbEjbHome.getIsInMemoryReplication()) {
                     sfsbEjbHome.releaseEO(this.primaryKey);
                     sfsbEjbHome.unexport(this, false);
                  }

                  wrap.popRunAsIdentity();

                  try {
                     SecurityHelper.popCallerPrincipal();
                  } catch (PrincipalNotFoundException var51) {
                     EJBLogger.logErrorPoppingCallerPrincipal(var51);
                  }

                  MethodInvocationHelper.popMethodObject(this.beanInfo);
               }
            }

            StatefulEJBHome sfsbEjbHome = (StatefulEJBHome)this.getEJBHome();
            if (sfsbEjbHome.getIsNoObjectActivation() || sfsbEjbHome.getIsInMemoryReplication()) {
               sfsbEjbHome.releaseEO(this.primaryKey);
               sfsbEjbHome.unexport(this, false);
            }

            wrap.popRunAsIdentity();

            try {
               SecurityHelper.popCallerPrincipal();
            } catch (PrincipalNotFoundException var52) {
               EJBLogger.logErrorPoppingCallerPrincipal(var52);
            }

            MethodInvocationHelper.popMethodObject(this.beanInfo);
         } catch (Throwable var56) {
            var5 = var56;
            throw var56;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var50) {
                     var5.addSuppressed(var50);
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
         } catch (InternalException var49) {
            throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", var49);
         }
      }

   }
}
