package weblogic.ejb.container.deployer;

import javax.ejb.EJBContext;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.ejb.container.internal.EJBContextManager;
import weblogic.jndi.OpaqueReference;

final class EjbContextFactory implements OpaqueReference {
   private static final EjbContextFactory INSTANCE = new EjbContextFactory();

   public static EjbContextFactory getInstance() {
      return INSTANCE;
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      EJBContext ejbCtx = EJBContextManager.getEJBContext();
      if (ejbCtx == null) {
         throw new IllegalStateException("Cannot find EJBContext!");
      } else {
         return ejbCtx;
      }
   }
}
