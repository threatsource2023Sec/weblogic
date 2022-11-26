package org.jboss.weld.bean;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.interceptor.InvocationContext;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.bean.interceptor.CdiInterceptorFactory;
import org.jboss.weld.bean.proxy.CombinedInterceptorAndDecoratorStackMethodHandler;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.interceptor.WeldInvocationContext;
import org.jboss.weld.interceptor.proxy.WeldInvocationContextImpl;
import org.jboss.weld.interceptor.reader.InterceptorMetadataImpl;
import org.jboss.weld.interceptor.reader.InterceptorMetadataUtils;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Interceptors;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class InterceptorImpl extends ManagedBean implements Interceptor {
   private final InterceptorClassMetadata interceptorMetadata = this.initInterceptorMetadata();
   private final Set interceptorBindingTypes;
   private final boolean serializable;

   public static InterceptorImpl of(BeanAttributes attributes, EnhancedAnnotatedType type, BeanManagerImpl beanManager) {
      return new InterceptorImpl(attributes, type, beanManager);
   }

   protected InterceptorImpl(BeanAttributes attributes, EnhancedAnnotatedType type, BeanManagerImpl beanManager) {
      super(attributes, type, new StringBeanIdentifier(BeanIdentifiers.forInterceptor(type)), beanManager);
      this.serializable = type.isSerializable();
      this.interceptorBindingTypes = Interceptors.mergeBeanInterceptorBindings(beanManager, this.getEnhancedAnnotated(), this.getStereotypes()).uniqueValues();
   }

   private InterceptorClassMetadata initInterceptorMetadata() {
      CdiInterceptorFactory reference = new CdiInterceptorFactory(this);
      return new InterceptorMetadataImpl(this.getBeanClass(), reference, InterceptorMetadataUtils.buildMethodMap(this.getEnhancedAnnotated(), false, this.getBeanManager()));
   }

   public Set getInterceptorBindings() {
      return this.interceptorBindingTypes;
   }

   public InterceptorClassMetadata getInterceptorMetadata() {
      return this.interceptorMetadata;
   }

   public Object intercept(InterceptionType type, Object instance, InvocationContext ctx) {
      org.jboss.weld.interceptor.spi.model.InterceptionType interceptionType = org.jboss.weld.interceptor.spi.model.InterceptionType.valueOf(type.name());
      List methodInvocations = this.interceptorMetadata.getInterceptorInvocation(instance, interceptionType).getInterceptorMethodInvocations();
      Set interceptorBindings = null;
      if (ctx instanceof WeldInvocationContext) {
         interceptorBindings = ((WeldInvocationContext)Reflections.cast(ctx)).getInterceptorBindings();
      }

      try {
         return (new WeldInvocationContextImpl(ctx, methodInvocations, interceptorBindings, (CombinedInterceptorAndDecoratorStackMethodHandler)null)).proceed();
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new WeldException(var9);
      }
   }

   public boolean intercepts(InterceptionType type) {
      return this.interceptorMetadata.isEligible(org.jboss.weld.interceptor.spi.model.InterceptionType.valueOf(type.name()));
   }

   public boolean isSerializable() {
      return this.serializable;
   }

   public void initializeAfterBeanDiscovery() {
      super.initializeAfterBeanDiscovery();
      this.checkInterceptorBindings();
   }

   private void checkInterceptorBindings() {
      if (this.interceptorMetadata.isEligible(org.jboss.weld.interceptor.spi.model.InterceptionType.POST_CONSTRUCT) || this.interceptorMetadata.isEligible(org.jboss.weld.interceptor.spi.model.InterceptionType.PRE_DESTROY) || this.interceptorMetadata.isEligible(org.jboss.weld.interceptor.spi.model.InterceptionType.POST_ACTIVATE) || this.interceptorMetadata.isEligible(org.jboss.weld.interceptor.spi.model.InterceptionType.PRE_PASSIVATE)) {
         Iterator var1 = this.interceptorBindingTypes.iterator();

         while(true) {
            Annotation interceptorBindingType;
            Target target;
            do {
               if (!var1.hasNext()) {
                  return;
               }

               interceptorBindingType = (Annotation)var1.next();
               target = (Target)interceptorBindingType.annotationType().getAnnotation(Target.class);
            } while(target != null && !this.hasInvalidTargetType(target.value()));

            ReflectionLogger.LOG.lifecycleCallbackInterceptorWithInvalidBindingTarget(this, interceptorBindingType.annotationType().getName(), target != null ? Arrays.toString(target.value()) : "Target meta-annotation is not present");
         }
      }
   }

   private boolean hasInvalidTargetType(ElementType[] elementTypes) {
      ElementType[] var2 = elementTypes;
      int var3 = elementTypes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ElementType elementType = var2[var4];
         if (!ElementType.TYPE.equals(elementType)) {
            return true;
         }
      }

      return false;
   }

   public String toString() {
      return "Interceptor [" + this.getBeanClass() + " intercepts " + Formats.formatAnnotations((Iterable)this.getInterceptorBindings()) + "]";
   }
}
