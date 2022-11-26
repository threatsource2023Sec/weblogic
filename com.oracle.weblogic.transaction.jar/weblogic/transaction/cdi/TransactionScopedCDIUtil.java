package weblogic.transaction.cdi;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.util.AnnotationLiteral;
import weblogic.transaction.internal.TxDebug;

public class TransactionScopedCDIUtil {
   public static final String INITIALIZED_EVENT = "INITIALIZED_EVENT";
   public static final String DESTORYED_EVENT = "DESTORYED_EVENT";

   public static void log(String message) {
      if (TxDebug.JTACDI.isDebugEnabled()) {
         TxDebug.JTACDI.debug(message);
      }

   }

   public static Bean createHelperBean(BeanManager beanManager, Class beanClass) {
      BeanWrapper result = null;
      AnnotatedType annotatedType = beanManager.createAnnotatedType(beanClass);
      result = new BeanWrapper(beanClass);
      InjectionTargetFactory factory = beanManager.getInjectionTargetFactory(annotatedType);
      InjectionTarget injectionTarget = factory.createInjectionTarget(result);
      result.setInjectionTarget(injectionTarget);
      return result;
   }

   public static void fireEvent(String eventType) {
      BeanManager beanManager = null;

      try {
         beanManager = CDI.current().getBeanManager();
      } catch (Exception var5) {
         log("Can't get instance of BeanManager to process TransactionScoped CDI Event!");
      }

      if (beanManager != null) {
         Set availableBeans = beanManager.getBeans(TransactionScopedCDIEventHelperImpl.class, new Annotation[0]);
         if (null != availableBeans && !availableBeans.isEmpty()) {
            Bean bean = beanManager.resolve(availableBeans);
            TransactionScopedCDIEventHelper eventHelper = (TransactionScopedCDIEventHelper)beanManager.getReference(bean, bean.getBeanClass(), beanManager.createCreationalContext((Contextual)null));
            if (eventType.equalsIgnoreCase("INITIALIZED_EVENT")) {
               eventHelper.fireInitializedEvent(new TransactionScopedCDIEventPayload());
            } else {
               eventHelper.fireDestroyedEvent(new TransactionScopedCDIEventPayload());
            }
         }
      } else {
         log("Can't get instance of BeanManager to process TransactionScoped CDI Event!");
      }

   }

   private static class BeanWrapper implements Bean {
      private Class beanClass;
      private InjectionTarget injectionTarget = null;

      public BeanWrapper(Class beanClass) {
         this.beanClass = beanClass;
      }

      private void setInjectionTarget(InjectionTarget injectionTarget) {
         this.injectionTarget = injectionTarget;
      }

      public Class getBeanClass() {
         return this.beanClass;
      }

      public Set getInjectionPoints() {
         return this.injectionTarget.getInjectionPoints();
      }

      public String getName() {
         return null;
      }

      public Set getQualifiers() {
         Set qualifiers = new HashSet();
         qualifiers.add(new DefaultAnnotationLiteral());
         qualifiers.add(new AnyAnnotationLiteral());
         return qualifiers;
      }

      public Class getScope() {
         return Dependent.class;
      }

      public Set getStereotypes() {
         return Collections.emptySet();
      }

      public Set getTypes() {
         Set types = new HashSet();
         types.add(this.beanClass);
         types.add(Object.class);
         return types;
      }

      public boolean isAlternative() {
         return false;
      }

      public boolean isNullable() {
         return false;
      }

      public Object create(CreationalContext ctx) {
         Object instance = this.injectionTarget.produce(ctx);
         this.injectionTarget.inject(instance, ctx);
         this.injectionTarget.postConstruct(instance);
         return instance;
      }

      public void destroy(Object instance, CreationalContext ctx) {
         this.injectionTarget.preDestroy(instance);
         this.injectionTarget.dispose(instance);
         ctx.release();
      }

      public static class AnyAnnotationLiteral extends AnnotationLiteral {
         private static final long serialVersionUID = -4700109250603725375L;
      }

      public static class DefaultAnnotationLiteral extends AnnotationLiteral {
         private static final long serialVersionUID = -9065007202240742004L;
      }
   }
}
