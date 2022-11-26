package weblogic.ejb.container.internal;

import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.Name;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.ejb20.interfaces.RemoteHome;
import weblogic.ejb20.internal.HomeHandleImpl;
import weblogic.j2ee.MethodInvocationHelper;
import weblogic.jndi.OpaqueReference;
import weblogic.logging.Loggable;
import weblogic.rmi.SupportsInterfaceBasedCallByReference;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.service.ContextHandler;
import weblogic.transaction.AppSetRollbackOnlyException;
import weblogic.transaction.RollbackException;
import weblogic.utils.StackTraceUtils;

public abstract class BaseEJBHome implements BaseEJBRemoteHomeIntf, RemoteHome, SupportsInterfaceBasedCallByReference {
   protected static final DebugLogger debugLogger;
   protected Class eoClass;
   protected DeploymentInfo deploymentInfo;
   protected ClientDrivenBeanInfo beanInfo;
   protected BeanManager beanManager;
   private CBVHomeRef cbvHomeRef;
   private EJBMetaData ejbMetaData;

   BaseEJBHome(Class eoClass) {
      this.eoClass = eoClass;
   }

   public void setup(BeanInfo bi, BeanManager bm) throws WLDeploymentException {
      this.beanInfo = (ClientDrivenBeanInfo)bi;
      this.deploymentInfo = this.beanInfo.getDeploymentInfo();
      this.beanManager = bm;
      if (!bi.useCallByReference()) {
         this.cbvHomeRef = new CBVHomeRef(this);
      }

   }

   public void unprepare() {
      if (!this.beanInfo.useCallByReference()) {
         this.cbvHomeRef.removeResolvedRef();
      }

   }

   public BeanInfo getBeanInfo() {
      return this.beanInfo;
   }

   public BeanManager getBeanManager() {
      return this.beanManager;
   }

   public String getJNDINameAsString() {
      return this.beanInfo.getJNDINameAsString();
   }

   public abstract EJBMetaData getEJBMetaData() throws RemoteException;

   protected abstract EJBMetaData getEJBMetaDataInstance();

   protected EJBMetaData getEJBMetaData(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      if (this.ejbMetaData == null) {
         this.ejbMetaData = this.getEJBMetaDataInstance();
      }

      return this.ejbMetaData;
   }

   public HomeHandle getHomeHandle(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      if (this.beanInfo.getJNDINameAsString() == null) {
         Loggable l = EJBLogger.logneedJNDINameForHomeHandlesLoggable(this.beanInfo.getDisplayName());
         throw new RemoteException(l.getMessage());
      } else {
         return new HomeHandleImpl(this, this.beanInfo.getJNDIName(), URLDelegateProvider.getURLDelegate(this.isHomeClusterable()));
      }
   }

   public abstract void remove(MethodDescriptor var1, Object var2) throws RemoteException, RemoveException;

   public abstract void remove(MethodDescriptor var1, Handle var2) throws RemoteException, RemoveException;

   public abstract EJBObject allocateEO(Object var1);

   public abstract EJBObject allocateEO();

   public EJBHome getCBVHomeStub() {
      return this.cbvHomeRef.getStub();
   }

   public Object getReferenceToBind() {
      return !this.beanInfo.useCallByReference() ? this.cbvHomeRef : this;
   }

   public EJBObject allocateEJBObject() throws RemoteException {
      return this.allocateEO();
   }

   public EJBObject allocateEJBObject(Object pk) throws RemoteException {
      return this.allocateEO(pk);
   }

   public String getIsIdenticalKey() {
      return this.beanInfo.getFullyQualifiedName();
   }

   public boolean isIdenticalTo(EJBHome eh) throws RemoteException {
      try {
         RemoteHome rh = (RemoteHome)PortableRemoteObject.narrow(eh, RemoteHome.class);
         return this.getIsIdenticalKey().equals(rh.getIsIdenticalKey());
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public boolean usesBeanManagedTx() {
      return this.beanInfo.usesBeanManagedTx();
   }

   public void activate() throws WLDeploymentException {
   }

   protected Object perhapsWrap(Remote r) {
      if (this.beanInfo.useCallByReference()) {
         return r;
      } else {
         try {
            ServerHelper.exportObject(r);
            return ServerHelper.getCBVWrapperObject(r);
         } catch (RemoteException var3) {
            throw new AssertionError(var3);
         }
      }
   }

   public void undeploy() {
      if (this.beanInfo.useCallByReference()) {
         this.unexport(this, true);
      } else {
         this.cbvHomeRef.removeResolvedRef();
      }

   }

   public void unexport(Remote remote) {
      this.unexport(remote, true);
   }

   public void unexport(Remote remote, boolean removeRMIDescriptor) {
      try {
         ServerHelper.unexportObject(remote, true, removeRMIDescriptor);
      } catch (NoSuchObjectException var4) {
      }

   }

   public void unexportEJBActivator(Activator ejbActivator, Class c) {
      try {
         ServerHelper.removeRuntimeDescriptor(c);
         ServerHelper.unexportObject(ejbActivator);
      } catch (NoSuchObjectException var4) {
      }

   }

   void handleSystemException(InvocationWrapper wrap, Throwable ee) throws RemoteException {
      BaseRemoteObject.handleSystemException(wrap, this.usesBeanManagedTx(), ee);
   }

   protected InvocationWrapper preHomeInvoke(MethodDescriptor md, ContextHandler ch) throws RemoteException {
      InvocationWrapper wrap = InvocationWrapper.newInstance(md);
      wrap.setCIC(this.beanInfo);

      try {
         wrap.checkMethodPermissionsRemote(ch);
      } catch (AccessException var7) {
         wrap.unsetCIC();
         throw var7;
      }

      wrap.pushSecurityContext(ch);

      try {
         wrap.enforceTransactionPolicy();
      } catch (InternalException var8) {
         InternalException ie = var8;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("[BaseEJBHome] Failed to enforce t/x policy", var8);
         }

         try {
            Transaction tx = TransactionService.getTransaction();
            if (tx != null) {
               if (tx instanceof weblogic.transaction.Transaction) {
                  ((weblogic.transaction.Transaction)tx).setRollbackOnly(ie);
               } else {
                  tx.setRollbackOnly();
               }
            }
         } catch (Exception var6) {
            EJBLogger.logErrorMarkingForRollback(var6);
         }

         wrap.unsetCIC();
         wrap.popSecurityContext();
         throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", var8);
      }

      MethodInvocationHelper.pushMethodObject(this.getBeanInfo());
      SecurityHelper.pushCallerPrincipal();
      wrap.pushRunAsIdentity();
      return wrap;
   }

   private void postHomeInvokeNoInvokeTx(MethodDescriptor md, InvocationWrapper wrap) throws RemoteException {
      if (wrap.getInvokeTx() == null) {
         try {
            this.getBeanManager().beforeCompletion(wrap);
            this.getBeanManager().afterCompletion(wrap);
         } catch (InternalException var4) {
            if (EJBRuntimeUtils.isAppException(md.getMethod(), var4)) {
               throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", var4);
            }

            this.handleSystemException(wrap, var4);
            throw new AssertionError("Should never have reached here");
         }
      }

   }

   protected void postHomeInvoke(InvocationWrapper wrap, Throwable ee) throws RemoteException {
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
                           EJBLogger.logErrorDuringRollback1(invokeTx.toString(), StackTraceUtils.throwable2StackTrace(var20));
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
                        EJBLogger.logErrorDuringCommit(invokeTx.toString(), StackTraceUtils.throwable2StackTrace(var17));
                     }

                     throw EJBRuntimeUtils.asRemoteException("Error committing transaction:", var22);
                  }
               case 1:
                  try {
                     invokeTx.rollback();
                  } catch (Exception var19) {
                     EJBLogger.logErrorDuringRollback(invokeTx.toString(), StackTraceUtils.throwable2StackTrace(var19));
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

   protected void postHomeInvokeCleanup(InvocationWrapper wrap) throws RemoteException {
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
         throw EJBRuntimeUtils.asRemoteException("EJB Exception: ", var10);
      } finally {
         wrap.unsetCIC();
      }

   }

   protected void validateHandleFromHome(Handle h) throws RemoteException {
      if (h == null) {
         Loggable l = EJBLogger.loghandleNullLoggable();
         throw new RemoteException(l.getMessageText());
      } else {
         EJBObject eo = h.getEJBObject();
         if (eo == null) {
            Loggable l = EJBLogger.logejbObjectNullLoggable();
            throw new RemoteException(l.getMessageText());
         } else {
            EJBHome eh = eo.getEJBHome();
            Loggable l;
            if (eh == null) {
               l = EJBLogger.loghomeWasNullLoggable();
               throw new RemoteException(l.getMessageText());
            } else if (!(eh instanceof RemoteHome)) {
               l = EJBLogger.logejbObjectNotFromThisHomeLoggable();
               throw new RemoteException(l.getMessageText());
            } else {
               RemoteHome yourHome = (RemoteHome)eh;
               String myName = this.getIsIdenticalKey();
               String yourName = yourHome.getIsIdenticalKey();
               if (!myName.equals(yourName)) {
                  Loggable l = EJBLogger.logejbObjectNotFromThisHomeLoggable();
                  throw new RemoteException(l.getMessageText());
               }
            }
         }
      }
   }

   public boolean isHomeClusterable() {
      return ServerHelper.isClusterable(this);
   }

   public Object getInstance() {
      return this;
   }

   static {
      debugLogger = EJBDebugService.invokeLogger;
   }

   private static final class CBVHomeRef implements OpaqueReference {
      private final BaseEJBHome home;
      private EJBHome cbvStub;
      private Object referent;

      CBVHomeRef(BaseEJBHome home) {
         this.home = home;
      }

      public synchronized Object getReferent(Name name, Context ctx) {
         this.ensureResolved();
         return this.referent;
      }

      synchronized EJBHome getStub() {
         this.ensureResolved();
         return this.cbvStub;
      }

      private void ensureResolved() {
         if (this.cbvStub == null) {
            try {
               if (this.home.isHomeClusterable()) {
                  this.cbvStub = (EJBHome)ServerHelper.exportObject(this.home, this.home.getJNDINameAsString());
               } else {
                  this.cbvStub = (EJBHome)ServerHelper.exportObject(this.home);
               }

               this.referent = ServerHelper.getCBVWrapperObject(this.home);
            } catch (RemoteException var2) {
               throw new AssertionError(var2);
            }
         }
      }

      synchronized void removeResolvedRef() {
         this.cbvStub = null;
         this.referent = null;

         try {
            ServerHelper.unexportObject(this.home, true, true);
         } catch (NoSuchObjectException var2) {
         }

      }
   }
}
