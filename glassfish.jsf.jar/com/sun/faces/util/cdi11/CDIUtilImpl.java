package com.sun.faces.util.cdi11;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;
import javax.enterprise.util.AnnotationLiteral;

public class CDIUtilImpl implements Serializable, CDIUtil {
   private static final long serialVersionUID = -8101770583567814803L;

   public Bean createHelperBean(BeanManager beanManager, Class beanClass) {
      BeanWrapper result = null;
      AnnotatedType annotatedType = beanManager.createAnnotatedType(beanClass);
      InjectionTargetFactory factory = beanManager.getInjectionTargetFactory(annotatedType);
      result = new BeanWrapper(beanClass);
      InjectionTarget injectionTarget = factory.createInjectionTarget(result);
      result.setInjectionTarget(injectionTarget);
      return result;
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
