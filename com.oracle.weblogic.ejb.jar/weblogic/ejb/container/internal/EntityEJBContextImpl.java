package weblogic.ejb.container.internal;

import java.security.Principal;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.EntityContext;
import javax.transaction.Transaction;
import javax.transaction.UserTransaction;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.WLEntityBean;
import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.logging.Loggable;
import weblogic.security.acl.internal.AuthenticatedSubject;

public final class EntityEJBContextImpl extends BaseEJBContext implements EntityContext {
   private Object primaryKey = null;

   public EntityEJBContextImpl(Object b, BeanManager bm, BaseEJBHome remoteHome, BaseEJBLocalHome localHome, EJBObject eo, EJBLocalObject elo) {
      super(b, bm, remoteHome, localHome, eo, elo);
   }

   public Object getPrimaryKey() throws IllegalStateException {
      this.checkAllowedMethod(114936);
      return this.__WL_getPrimaryKey();
   }

   public void __WL_setPrimaryKey(Object pk) {
      this.primaryKey = pk;
   }

   public Object __WL_getPrimaryKey() {
      return this.primaryKey;
   }

   public EJBObject __WL_getEJBObject() {
      return this.ejbObject;
   }

   public EJBLocalObject __WL_getEJBLocalObject() {
      return this.ejbLocalObject;
   }

   public Principal getCallerPrincipal() {
      Object bean = this.getBean();
      if (bean instanceof WLEntityBean && ((WLEntityBean)bean).__WL_getMethodState() == 32768) {
         this.checkAllowedMethod(this.getCallerPrincipalMethodCode());
         AuthenticatedSubject s = (AuthenticatedSubject)((AuthenticatedSubject)((WLEntityBean)bean).__WL_getLoadUser());
         Principal cp = SecurityHelper.getPrincipalFromSubject(s);
         if (cp == null) {
            Loggable l = EJBLogger.logmissingCallerPrincipalLoggable("getCallerPrincipal");
            throw new EJBException(new PrincipalNotFoundException(l.getMessage()));
         } else {
            return cp;
         }
      } else {
         return super.getCallerPrincipal();
      }
   }

   public Map getContextData() {
      throw new IllegalStateException(EJBLogger.logContextDataNotSupportedForEntityBean());
   }

   protected int getCallerIdentityMethodCode() {
      return 125084;
   }

   protected int getCallerPrincipalMethodCode() {
      return 125084;
   }

   protected int getIsCallerInRoleMethodCode() {
      return 125084;
   }

   protected int getGetEJBObjectMethodCode() {
      return 114936;
   }

   protected int getGetEJBLocalObjectMethodCode() {
      return 114936;
   }

   protected int getGetRollbackOnlyMethodCode() {
      return 125084;
   }

   protected int getSetRollbackOnlyMethodCode() {
      return 125084;
   }

   protected boolean legalToCallRollbackOnlyMethods(Transaction tx) {
      return ((weblogic.transaction.Transaction)tx).getProperty("LOCAL_ENTITY_TX") == null;
   }

   protected UserTransaction createUserTransactionProxy(UserTransaction ut) {
      throw new IllegalStateException("Illegal call to getUserTransaction() from an Entity bean");
   }
}
