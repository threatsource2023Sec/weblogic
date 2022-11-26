package weblogic.ejb.container.internal;

import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttributeType;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.Ejb3LocalHome;
import weblogic.ejb.container.interfaces.Ejb3RemoteHome;
import weblogic.ejb.container.interfaces.Ejb3StatefulHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.interfaces.WLSessionEJBContext;
import weblogic.ejb.container.internal.usertransactioncheck.SLSBUserTransactionProxy;
import weblogic.logging.Loggable;
import weblogic.utils.StackTraceUtilsClient;

public final class SessionEJBContextImpl extends BaseEJBContext implements SessionContext, WLSessionEJBContext {
   private final SessionBeanInfo sbi;
   private MessageContext messageContext;
   private Object primaryKey;

   public SessionEJBContextImpl(Object bean, BeanManager bm, BaseEJBHome remoteHome, BaseEJBLocalHome localHome, EJBObject eo, EJBLocalObject elo) {
      super(bean, bm, remoteHome, localHome, eo, elo);
      this.sbi = (SessionBeanInfo)bm.getBeanInfo();
   }

   public void setPrimaryKey(Object primaryKey) {
      this.primaryKey = primaryKey;
   }

   public MessageContext getMessageContext() {
      this.checkAllowedToGetMessageContext();
      return this.messageContext;
   }

   public void setMessageContext(MessageContext m) {
      this.messageContext = m;
   }

   public EJBHome getEJBHome() throws IllegalStateException {
      Loggable l;
      if (this.sbi.isSingleton()) {
         l = EJBLogger.log2xClientViewNotSupportedForSingletonsLoggable(this.sbi.getDisplayName());
         throw new IllegalStateException(l.getMessageText());
      } else if (this.ejbObject == null) {
         l = EJBLogger.logEjbBeanWithoutHomeInterfaceLoggable(this.sbi.getDisplayName(), "");
         throw new IllegalStateException(l.getMessage());
      } else {
         return super.getEJBHome();
      }
   }

   public EJBLocalHome getEJBLocalHome() throws IllegalStateException {
      Loggable l;
      if (this.sbi.isSingleton()) {
         l = EJBLogger.log2xClientViewNotSupportedForSingletonsLoggable(this.sbi.getDisplayName());
         throw new IllegalStateException(l.getMessageText());
      } else if (this.ejbLocalObject == null) {
         l = EJBLogger.logEjbBeanWithoutHomeInterfaceLoggable(this.sbi.getDisplayName(), "local");
         throw new IllegalStateException(l.getMessage());
      } else {
         return super.getEJBLocalHome();
      }
   }

   private boolean needConsiderReplicationService() throws IllegalStateException {
      if (this.remoteHome instanceof Ejb3StatefulHome) {
         try {
            return ((Ejb3StatefulHome)this.remoteHome).needToConsiderReplicationService();
         } catch (RemoteException var2) {
            throw new IllegalStateException(var2);
         }
      } else {
         return false;
      }
   }

   public EJBObject getEJBObject() throws IllegalStateException {
      Loggable l;
      if (this.sbi.isSingleton()) {
         l = EJBLogger.log2xClientViewNotSupportedForSingletonsLoggable(this.sbi.getDisplayName());
         throw new IllegalStateException(l.getMessageText());
      } else if (this.needConsiderReplicationService()) {
         try {
            return (EJBObject)((Ejb3StatefulHome)this.remoteHome).getComponentImpl(this.primaryKey);
         } catch (RemoteException var2) {
            throw new IllegalStateException(var2);
         }
      } else if (this.ejbObject == null) {
         l = EJBLogger.logEjbBeanWithoutHomeInterfaceLoggable(this.sbi.getDisplayName(), "");
         throw new IllegalStateException(l.getMessage());
      } else {
         return super.getEJBObject();
      }
   }

   public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
      Loggable l;
      if (this.sbi.isSingleton()) {
         l = EJBLogger.log2xClientViewNotSupportedForSingletonsLoggable(this.sbi.getDisplayName());
         throw new IllegalStateException(l.getMessage());
      } else if (this.ejbLocalObject == null) {
         l = EJBLogger.logEjbBeanWithoutHomeInterfaceLoggable(this.sbi.getDisplayName(), "local");
         throw new IllegalStateException(l.getMessage());
      } else {
         return super.getEJBLocalObject();
      }
   }

   public Object getBusinessObject(Class busIntf) throws IllegalStateException {
      Loggable l;
      if (!(this.remoteHome instanceof Ejb3RemoteHome) && !(this.localHome instanceof Ejb3LocalHome)) {
         l = EJBLogger.logBeanIsNotEJB3BeanLoggable(this.sbi.getDisplayName());
         throw new IllegalStateException(l.getMessage());
      } else {
         Object val;
         if (busIntf != null && this.remoteHome instanceof Ejb3RemoteHome) {
            try {
               val = ((Ejb3RemoteHome)this.remoteHome).getBusinessImpl(this.primaryKey, busIntf);
               if (val != null) {
                  return val;
               }
            } catch (RemoteException var3) {
               throw new AssertionError("RemoteException occured when getting the business object for : " + busIntf + ". " + StackTraceUtilsClient.throwable2StackTrace(var3));
            }
         }

         if (busIntf != null && this.localHome instanceof Ejb3LocalHome) {
            val = ((Ejb3LocalHome)this.localHome).getBusinessImpl(this.primaryKey, busIntf);
            if (val != null) {
               return val;
            }
         }

         l = EJBLogger.logEjbNoImplementBusinessInterfaceLoggable(this.sbi.getDisplayName(), busIntf == null ? "null" : busIntf.toString());
         throw new IllegalStateException(l.getMessage());
      }
   }

   public Class getInvokedBusinessInterface() throws IllegalStateException {
      this.checkAllowedMethod(128);
      InvocationWrapper iw = (InvocationWrapper)InvocationContextStack.get();
      ClientViewDescriptor cw = iw.getMethodDescriptor().getClientViewDescriptor();
      if (cw.isBusinessView()) {
         return cw.getViewClass();
      } else {
         throw new IllegalStateException(EJBLogger.logBeanNotInvokedThroughBusinessInterfaceLoggable().getMessage());
      }
   }

   protected void checkAllowedToGetTimerService() {
      if (this.sbi.isStateful()) {
         Loggable l = EJBLogger.logStatefulSessionBeanAttemptToAccessTimerServiceLoggable();
         throw new IllegalStateException(l.getMessage());
      } else {
         super.checkAllowedToGetTimerService();
      }
   }

   protected void checkAllowedToGetMessageContext() {
      if (!this.sbi.isStateless()) {
         Loggable l = EJBLogger.logIllegalCallEJBContextMethodLoggable();
         throw new IllegalStateException(l.getMessage());
      } else {
         super.checkAllowedToGetMessageContext();
      }
   }

   public boolean wasCancelCalled() throws IllegalStateException {
      this.checkAllowedMethod(128);
      InvocationWrapper iw = (InvocationWrapper)InvocationContextStack.get();
      if (iw.getCancelRunning() != null) {
         return iw.getCancelRunning().get();
      } else {
         Loggable l = EJBLogger.logInvalidWasCancelCalledInvocationLoggable();
         throw new IllegalStateException(l.getMessageText());
      }
   }

   protected int getCallerIdentityMethodCode() {
      if (this.sbi.isStateful()) {
         return 526324;
      } else {
         return this.sbi.isStateless() ? 196736 : 196736;
      }
   }

   protected int getCallerPrincipalMethodCode() {
      if (this.sbi.isStateful()) {
         return 526324;
      } else {
         return this.sbi.isStateless() ? 196736 : 196736;
      }
   }

   protected int getIsCallerInRoleMethodCode() {
      if (this.sbi.isStateful()) {
         return 526324;
      } else {
         return this.sbi.isStateless() ? 196736 : 196736;
      }
   }

   protected int getGetEJBObjectMethodCode() {
      return 722932;
   }

   protected int getGetEJBLocalObjectMethodCode() {
      return 722932;
   }

   protected int getGetRollbackOnlyMethodCode() {
      return 983936;
   }

   protected int getSetRollbackOnlyMethodCode() {
      return 983936;
   }

   protected boolean legalToCallRollbackOnlyMethods(Transaction tx) {
      boolean isValidCall = true;
      int beanState = WLEnterpriseBeanUtils.getCurrentState(this.getBean());
      if (beanState == 128 || beanState == 131072) {
         MethodDescriptor md = ((InvocationWrapper)InvocationContextStack.get()).getMethodDescriptor();
         isValidCall = md.getTransactionPolicy().getTxAttribute() != TransactionAttributeType.SUPPORTS;
      }

      return isValidCall;
   }

   protected UserTransaction createUserTransactionProxy(UserTransaction ut) {
      return (UserTransaction)(!this.sbi.isStateless() ? ut : new SLSBUserTransactionProxy(ut, (WLEnterpriseBean)this.getBean()));
   }

   public Map getContextData() {
      this.checkAllowedMethod(983284);
      return InvocationContextStack.get().getContextData();
   }
}
