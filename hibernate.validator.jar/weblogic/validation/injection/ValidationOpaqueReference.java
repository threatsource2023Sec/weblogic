package weblogic.validation.injection;

import java.lang.annotation.Annotation;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.jndi.OpaqueReference;

class ValidationOpaqueReference implements OpaqueReference {
   private static final String BEAN_MANAGER = "java:comp/BeanManager";
   private ContextProvider contextProvider;
   private Object obj;

   ValidationOpaqueReference(Object defaultObject, ContextProvider contextProvider) {
      this.obj = defaultObject;
      this.contextProvider = contextProvider;
   }

   ValidationOpaqueReference(Object defaultObject) {
      this(defaultObject, new InitialContextProvider());
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      Object target = this.obj;

      try {
         BeanManager beanManager = (BeanManager)this.contextProvider.obtainContext().lookup("java:comp/BeanManager");
         Set beans = beanManager.getBeans(this.obj.getClass(), new Annotation[0]);
         if (!beans.isEmpty()) {
            Bean bean = (Bean)beans.iterator().next();
            target = bean.create((CreationalContext)null);
         }
      } catch (NamingException var7) {
      }

      return target;
   }

   static class InitialContextProvider implements ContextProvider {
      public Context obtainContext() throws NamingException {
         return new InitialContext();
      }
   }

   interface ContextProvider {
      Context obtainContext() throws NamingException;
   }
}
