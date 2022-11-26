package weblogic.ejb.container.internal;

import java.util.Map;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.MessageDrivenContext;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.logging.Loggable;

public final class MessageDrivenEJBContextImpl extends BaseEJBContext implements MessageDrivenContext {
   public MessageDrivenEJBContextImpl(BeanManager bm) {
      super((Object)null, bm, (BaseEJBHome)null, (BaseEJBLocalHome)null, (EJBObject)null, (EJBLocalObject)null);
   }

   public boolean isCallerInRole(String roleName) {
      if (this.beanManager.getBeanInfo().isVersionGreaterThan30()) {
         return super.isCallerInRole(roleName);
      } else {
         Loggable l = EJBLogger.logmdbCannotInvokeThisMethodLoggable("isCallerInRole()");
         throw new IllegalStateException(l.getMessage());
      }
   }

   public EJBHome getEJBHome() {
      Loggable l = EJBLogger.logmdbCannotInvokeThisMethodLoggable("getEJBHome()");
      throw new IllegalStateException(l.getMessage());
   }

   public EJBLocalHome getEJBLocalHome() {
      Loggable l = EJBLogger.logmdbCannotInvokeThisMethodLoggable("getEJBLocalHome()");
      throw new IllegalStateException(l.getMessage());
   }

   public Object getPrimaryKey() {
      Loggable l = EJBLogger.logmdbCannotInvokeThisMethodLoggable("getPrimaryKey()");
      throw new IllegalStateException(l.getMessage());
   }

   public Map getContextData() {
      return InvocationContextStack.get().getContextData();
   }

   protected UserTransaction createUserTransactionProxy(UserTransaction ut) {
      return ut;
   }

   protected void checkAllowedMethod(int flag) throws IllegalStateException {
   }

   protected int getCallerIdentityMethodCode() {
      return -1;
   }

   protected int getCallerPrincipalMethodCode() {
      return -1;
   }

   protected int getIsCallerInRoleMethodCode() {
      return -1;
   }

   protected int getGetEJBObjectMethodCode() {
      return -1;
   }

   protected int getGetEJBLocalObjectMethodCode() {
      return -1;
   }

   protected int getGetRollbackOnlyMethodCode() {
      return -1;
   }

   protected int getSetRollbackOnlyMethodCode() {
      return -1;
   }

   protected boolean legalToCallRollbackOnlyMethods(Transaction tx) {
      return true;
   }
}
