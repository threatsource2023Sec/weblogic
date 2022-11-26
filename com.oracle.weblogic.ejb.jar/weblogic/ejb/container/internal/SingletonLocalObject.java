package weblogic.ejb.container.internal;

import javax.ejb.ConcurrentAccessException;
import javax.ejb.EJBException;
import javax.ejb.EJBTransactionRolledbackException;
import weblogic.ejb.container.interfaces.LocalHandle30;
import weblogic.security.service.ContextHandler;

public class SingletonLocalObject extends BaseLocalObject {
   protected void __WL_preInvoke(InvocationWrapper wrap, ContextHandler ch) throws EJBException {
      try {
         super.__WL_preInvoke(wrap, ch);
      } catch (EJBTransactionRolledbackException var5) {
         Throwable eCause = var5.getCause();
         if (eCause != null && eCause instanceof ConcurrentAccessException) {
            throw EJBRuntimeUtils.asEJBException("EJB Exception: ", eCause);
         } else {
            throw var5;
         }
      }
   }

   public final LocalHandle30 getLocalHandle30Object(Object businessLocalObject) {
      return new LocalHandle30Impl(this, businessLocalObject);
   }
}
