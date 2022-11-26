package org.glassfish.hk2.configuration.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.configuration.api.Configured;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class ConfiguredByInjectionResolver implements InjectionResolver {
   @Inject
   @Named("SystemInjectResolver")
   private InjectionResolver systemResolver;
   @Inject
   private ConfiguredByContext context;
   private final ConcurrentHashMap beanMap = new ConcurrentHashMap();

   private static String getParameterNameFromConstructor(Constructor cnst, int position) {
      Annotation[] paramAnnotations = cnst.getParameterAnnotations()[position];
      Configured c = null;
      Annotation[] var4 = paramAnnotations;
      int var5 = paramAnnotations.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Annotation anno = var4[var6];
         if (Configured.class.equals(anno.annotationType())) {
            c = (Configured)anno;
            break;
         }
      }

      if (c == null) {
         return null;
      } else {
         String key = c.value();
         if (BeanUtilities.isEmpty(key)) {
            throw new AssertionError("Not enough in @Configured annotation in constructor " + cnst + " at parameter index " + position);
         } else {
            return key;
         }
      }
   }

   private static String getParameterNameFromMethod(Method method, int position) {
      Annotation[] paramAnnotations = method.getParameterAnnotations()[position];
      Configured c = null;
      Annotation[] var4 = paramAnnotations;
      int var5 = paramAnnotations.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Annotation anno = var4[var6];
         if (Configured.class.equals(anno.annotationType())) {
            c = (Configured)anno;
            break;
         }
      }

      if (c == null) {
         return null;
      } else {
         String key = c.value();
         if (BeanUtilities.isEmpty(key)) {
            throw new AssertionError("Not enough in @Configured annotation in method " + method + " at parameter index " + position);
         } else {
            return key;
         }
      }
   }

   public synchronized Object resolve(Injectee injectee, ServiceHandle root) {
      ActiveDescriptor injecteeParent = injectee.getInjecteeDescriptor();
      if (injecteeParent == null) {
         return this.systemResolver.resolve(injectee, root);
      } else {
         AnnotatedElement ae = injectee.getParent();
         if (ae == null) {
            return this.systemResolver.resolve(injectee, root);
         } else {
            String parameterName = null;
            if (ae instanceof Field) {
               parameterName = BeanUtilities.getParameterNameFromField((Field)ae, false);
            } else if (ae instanceof Constructor) {
               parameterName = getParameterNameFromConstructor((Constructor)ae, injectee.getPosition());
            } else {
               if (!(ae instanceof Method)) {
                  return this.systemResolver.resolve(injectee, root);
               }

               parameterName = getParameterNameFromMethod((Method)ae, injectee.getPosition());
            }

            if (parameterName == null) {
               return this.systemResolver.resolve(injectee, root);
            } else {
               ActiveDescriptor workingOn = this.context.getWorkingOn();
               if (workingOn == null) {
                  return this.systemResolver.resolve(injectee, root);
               } else {
                  BeanInfo beanInfo = (BeanInfo)this.beanMap.get(workingOn);
                  if (beanInfo == null) {
                     throw new IllegalStateException("Could not find a configuration bean for " + injectee + " with descriptor " + workingOn);
                  } else {
                     return BeanUtilities.getBeanPropertyValue(injectee.getRequiredType(), parameterName, beanInfo);
                  }
               }
            }
         }
      }
   }

   public boolean isConstructorParameterIndicator() {
      return true;
   }

   public boolean isMethodParameterIndicator() {
      return true;
   }

   synchronized BeanInfo addBean(ActiveDescriptor descriptor, Object bean, String type, Object metadata) {
      BeanInfo retVal = new BeanInfo(type, descriptor.getName(), bean, metadata);
      this.beanMap.put(descriptor, retVal);
      return retVal;
   }

   synchronized void removeBean(ActiveDescriptor descriptor) {
      this.beanMap.remove(descriptor);
   }

   public String toString() {
      return "ConfiguredByInjectionResolver(" + System.identityHashCode(this) + ")";
   }
}
