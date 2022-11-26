package org.glassfish.tyrus.gf.cdi;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.glassfish.tyrus.core.ComponentProvider;

public class CdiComponentProvider extends ComponentProvider {
   private final BeanManager beanManager;
   private static final Logger LOGGER = Logger.getLogger(CdiComponentProvider.class.getName());
   private final boolean managerRetrieved;
   private final Map cdiBeanToContext = new ConcurrentHashMap();

   public CdiComponentProvider() throws NamingException {
      InitialContext ic = new InitialContext();
      BeanManager manager = null;

      try {
         manager = (BeanManager)ic.lookup("java:comp/BeanManager");
      } catch (Exception var7) {
         LOGGER.fine(var7.getMessage());
      } finally {
         this.beanManager = manager;
         this.managerRetrieved = this.beanManager != null;
      }

   }

   public boolean isApplicable(Class c) {
      Annotation[] annotations = c.getAnnotations();
      Annotation[] var3 = annotations;
      int var4 = annotations.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Annotation annotation = var3[var5];
         String annotationClassName = annotation.annotationType().getCanonicalName();
         if (annotationClassName.equals("javax.ejb.Singleton") || annotationClassName.equals("javax.ejb.Stateful") || annotationClassName.equals("javax.ejb.Stateless")) {
            return false;
         }
      }

      return this.managerRetrieved;
   }

   public Object create(Class c) {
      if (this.managerRetrieved) {
         synchronized(this.beanManager) {
            AnnotatedType annotatedType = this.beanManager.createAnnotatedType(c);
            InjectionTarget it = this.beanManager.createInjectionTarget(annotatedType);
            CreationalContext cc = this.beanManager.createCreationalContext((Contextual)null);
            Object managedObject = it.produce(cc);
            it.inject(managedObject, cc);
            it.postConstruct(managedObject);
            this.cdiBeanToContext.put(managedObject, new CdiInjectionContext(it, cc));
            return managedObject;
         }
      } else {
         return null;
      }
   }

   public boolean destroy(Object o) {
      if (this.cdiBeanToContext.containsKey(o)) {
         ((CdiInjectionContext)this.cdiBeanToContext.get(o)).cleanup(o);
         this.cdiBeanToContext.remove(o);
         return true;
      } else {
         return false;
      }
   }

   private static class CdiInjectionContext {
      final InjectionTarget it;
      final CreationalContext cc;

      CdiInjectionContext(InjectionTarget it, CreationalContext cc) {
         this.it = it;
         this.cc = cc;
      }

      public void cleanup(Object instance) {
         this.it.preDestroy(instance);
         this.it.dispose(instance);
         this.cc.release();
      }
   }
}
