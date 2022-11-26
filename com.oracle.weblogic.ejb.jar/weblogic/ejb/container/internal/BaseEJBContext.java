package weblogic.ejb.container.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.Identity;
import java.security.Principal;
import java.util.Properties;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.TimerService;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import weblogic.ejb.WLTimerService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEJBContext;
import weblogic.ejb.container.manager.BaseEJBManager;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.logging.Loggable;
import weblogic.utils.StackTraceUtilsClient;

public abstract class BaseEJBContext implements WLEJBContext {
   protected final EJBHome remoteHome;
   protected final BaseEJBLocalHome localHome;
   protected final BeanManager beanManager;
   protected EJBObject ejbObject;
   protected EJBLocalObject ejbLocalObject;
   private TimerService timerService;
   private Object bean;

   BaseEJBContext(Object bean, BeanManager bm, BaseEJBHome rh, BaseEJBLocalHome lh, EJBObject eo, EJBLocalObject elo) {
      this.bean = bean;
      this.beanManager = bm;
      this.localHome = lh;
      this.ejbObject = eo;
      this.ejbLocalObject = elo;
      if (rh != null && !rh.getBeanInfo().useCallByReference()) {
         this.remoteHome = rh.getCBVHomeStub();
      } else {
         this.remoteHome = rh;
      }

   }

   protected void checkAllowedMethod(int flag) throws IllegalStateException {
      int beanState = WLEnterpriseBeanUtils.getCurrentState(this.bean);
      if ((beanState & flag) == 0) {
         Loggable l = EJBLogger.logillegalCallToEJBContextMethodLoggable(WLEnterpriseBeanUtils.getEJBStateAsString(beanState), WLEnterpriseBeanUtils.getEJBOperationAsString(flag));
         throw new IllegalStateException(l.getMessageText());
      }
   }

   public void setBean(Object bean) {
      this.bean = bean;
   }

   protected Object getBean() {
      return this.bean;
   }

   public UserTransaction getUserTransaction() throws IllegalStateException {
      if (!this.getBeanInfo().usesBeanManagedTx()) {
         Loggable l = EJBLogger.logbmtCanUseUserTransactionLoggable();
         throw new IllegalStateException(l.getMessageText());
      } else {
         this.checkAllowedMethod(983284);
         return this.createUserTransactionProxy(TransactionService.getUserTransaction());
      }
   }

   public Identity getCallerIdentity() {
      this.checkAllowedMethod(this.getCallerIdentityMethodCode());
      return new MyUser(this.getCallerPrincipal().getName());
   }

   public Principal getCallerPrincipal() {
      this.checkAllowedMethod(this.getCallerPrincipalMethodCode());

      try {
         return SecurityHelper.getCallerPrincipal();
      } catch (PrincipalNotFoundException var2) {
         throw new EJBException(var2);
      }
   }

   public EJBHome getEJBHome() {
      this.checkAllowedMethod(782335);
      return this.remoteHome;
   }

   public EJBLocalHome getEJBLocalHome() {
      this.checkAllowedMethod(782335);
      return this.localHome;
   }

   public EJBObject getEJBObject() {
      if (this.remoteHome == null) {
         Loggable l = EJBLogger.logonlyRemoteCanInvokeGetEJBObjectLoggable();
         throw new IllegalStateException(l.getMessage());
      } else {
         this.checkAllowedMethod(this.getGetEJBObjectMethodCode());
         return this.ejbObject;
      }
   }

   public EJBLocalObject getEJBLocalObject() {
      if (this.localHome == null) {
         Loggable l = EJBLogger.logonlyLocalCanInvokeGetEJBObjectLoggable();
         throw new IllegalStateException(l.getMessage());
      } else {
         this.checkAllowedMethod(this.getGetEJBLocalObjectMethodCode());
         return this.ejbLocalObject;
      }
   }

   public void setEJBObject(EJBObject eo) {
      this.ejbObject = eo;
   }

   public void setEJBLocalObject(EJBLocalObject elo) {
      this.ejbLocalObject = elo;
   }

   public String getComponentName() {
      return this.getBeanInfo().getDeploymentInfo().getModuleName();
   }

   public String getComponentURI() {
      return this.getBeanInfo().getDeploymentInfo().getModuleURI();
   }

   public String getEJBName() {
      return this.getBeanInfo().getEJBName();
   }

   public Properties getEnvironment() {
      throw new RuntimeException(EJBLogger.logEnvironmentDeprecatedLoggable().getMessageText());
   }

   public boolean getRollbackOnly() throws IllegalStateException {
      if (this.getBeanInfo().usesBeanManagedTx()) {
         Loggable l = EJBLogger.logonlyCMTBeanCanInvokeGetRollbackOnlyLoggable();
         throw new IllegalStateException(l.getMessage());
      } else {
         this.checkAllowedMethod(this.getGetRollbackOnlyMethodCode());

         try {
            Transaction tx = TransactionService.getTransaction();
            if (tx != null && this.legalToCallRollbackOnlyMethods(tx)) {
               int txState = tx.getStatus();
               return txState == 1 || txState == 9 || txState == 4;
            } else {
               Loggable l = EJBLogger.logillegalCallToGetRollbackOnlyLoggable();
               throw new IllegalStateException(l.getMessage());
            }
         } catch (SystemException var3) {
            EJBLogger.logExcepLookingUpXn2(StackTraceUtilsClient.throwable2StackTrace(var3));
            return false;
         }
      }
   }

   protected abstract boolean legalToCallRollbackOnlyMethods(Transaction var1);

   public void setRollbackOnly() throws IllegalStateException {
      if (this.getBeanInfo().usesBeanManagedTx()) {
         Loggable l = EJBLogger.logonlyCMTBeanCanInvokeSetRollbackOnlyLoggable();
         throw new IllegalStateException(l.getMessage());
      } else {
         this.checkAllowedMethod(this.getSetRollbackOnlyMethodCode());

         try {
            Transaction tx = TransactionService.getTransaction();
            if (tx == null || !this.legalToCallRollbackOnlyMethods(tx)) {
               Loggable l = EJBLogger.logillegalCallToSetRollbackOnlyLoggable();
               throw new IllegalStateException(l.getMessage());
            }

            tx.setRollbackOnly();
         } catch (SystemException var3) {
            EJBLogger.logExcepLookingUpXn3(StackTraceUtilsClient.throwable2StackTrace(var3));
         }

      }
   }

   public boolean isCallerInRole(String roleName) {
      this.checkAllowedMethod(this.getIsCallerInRoleMethodCode());
      return ((BaseEJBManager)this.beanManager).checkWritable(roleName);
   }

   public boolean isCallerInRole(Identity role) {
      return this.isCallerInRole(role.getName());
   }

   public TimerService getTimerService() {
      this.checkAllowedToGetTimerService();
      BeanInfo bi = this.getBeanInfo();
      if (!bi.isTimerDriven() && !bi.isEntityBean()) {
         Loggable l;
         if (bi.isSessionBean() && ((SessionBeanInfo)bi).isStateful()) {
            l = EJBLogger.logStatefulSessionBeanAttemptToAccessTimerServiceLoggable();
         } else {
            l = EJBLogger.logIllegalAttemptToAccessTimerServiceLoggable();
         }

         throw new IllegalStateException(l.getMessage());
      } else {
         return this.__WL_getTimerService();
      }
   }

   public void checkAllowedToCreateTimer() {
      if (this.getBeanInfo().getTimeoutMethod() == null) {
         throw new IllegalStateException(EJBLogger.logTimeoutMethodNotConfiguredLoggable().getMessage());
      }
   }

   public void checkAllowedToUseTimerService() {
      this.checkAllowedMethod(508056);
   }

   protected void checkAllowedToGetTimerService() {
      this.checkAllowedMethod(516348);
   }

   protected void checkAllowedToGetMessageContext() {
      this.checkAllowedMethod(131072);
   }

   public Object lookup(String name) throws IllegalArgumentException {
      if (name != null) {
         try {
            if (name.startsWith("java:")) {
               return InitialContext.doLookup(name);
            }

            return this.beanManager.getEnvironmentContext().lookup("comp/env/" + name);
         } catch (NamingException var3) {
         }
      }

      throw new IllegalArgumentException("Argument " + name + " is not found in environment context.");
   }

   protected abstract int getCallerIdentityMethodCode();

   protected abstract int getCallerPrincipalMethodCode();

   protected abstract int getIsCallerInRoleMethodCode();

   protected abstract int getGetEJBObjectMethodCode();

   protected abstract int getGetEJBLocalObjectMethodCode();

   protected abstract int getGetRollbackOnlyMethodCode();

   protected abstract int getSetRollbackOnlyMethodCode();

   protected abstract UserTransaction createUserTransactionProxy(UserTransaction var1);

   public TimerService __WL_getTimerService() {
      if (this.timerService == null) {
         BeanInfo bi = this.getBeanInfo();
         if (!bi.isTimerDriven()) {
            this.timerService = (TimerService)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{TimerService.class, WLTimerService.class}, new IllegalTimerServiceHandler(bi.isSessionBean() && ((SessionBeanInfo)bi).isStateful()));
         } else {
            this.timerService = new TimerServiceImpl(this.beanManager.getTimerManager(), this, bi.isClusteredTimers());
         }
      }

      return this.timerService;
   }

   BeanInfo getBeanInfo() {
      return this.beanManager.getBeanInfo();
   }

   private class MyUser extends Identity {
      public MyUser() {
         super((String)null);
      }

      public MyUser(String name) {
         super(name);
      }

      public MyUser(String name, boolean r) {
         super(name);
      }

      public Object getCredential(Object ownerCredential) {
         return null;
      }

      protected boolean identityEquals(Identity other) {
         if (other == null) {
            return false;
         } else {
            String name = this.getName();
            return name == null ? other.getName() == null : name.equals(other.getName());
         }
      }

      public String toString() {
         return this.getName();
      }
   }

   private static final class IllegalTimerServiceHandler implements InvocationHandler {
      private final boolean isStateful;

      IllegalTimerServiceHandler(boolean isStatefulSession) {
         this.isStateful = isStatefulSession;
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws IllegalStateException {
         Loggable l;
         if (!this.isStateful) {
            l = EJBLogger.logIllegalAttemptToAccessTimerServiceLoggable();
         } else {
            l = EJBLogger.logStatefulSessionBeanAttemptToAccessTimerServiceLoggable();
         }

         throw new IllegalStateException(l.getMessage());
      }
   }
}
