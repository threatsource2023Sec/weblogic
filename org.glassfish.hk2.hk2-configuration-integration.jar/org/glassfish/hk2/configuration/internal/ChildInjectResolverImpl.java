package org.glassfish.hk2.configuration.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.configuration.api.ChildInject;
import org.glassfish.hk2.configuration.api.ChildIterable;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class ChildInjectResolverImpl implements InjectionResolver {
   @Inject
   private ServiceLocator locator;
   @Inject
   private InjectionResolver systemResolver;
   @Inject
   private ConfiguredByContext configuredByContext;

   public Object resolve(Injectee injectee, ServiceHandle root) {
      ActiveDescriptor parentDescriptor = injectee.getInjecteeDescriptor();
      if (parentDescriptor == null) {
         return this.systemResolver.resolve(injectee, root);
      } else {
         parentDescriptor = this.configuredByContext.getWorkingOn();
         if (parentDescriptor == null) {
            return this.systemResolver.resolve(injectee, root);
         } else {
            Type requiredType = injectee.getRequiredType();
            Class requiredClass = ReflectionHelper.getRawClass(requiredType);
            if (requiredClass == null) {
               return this.systemResolver.resolve(injectee, root);
            } else {
               ChildInject childInject = getInjectionAnnotation(injectee.getParent(), injectee.getPosition());
               String prefixName = parentDescriptor.getName();
               if (prefixName == null) {
                  prefixName = "";
               }

               String separator = childInject.separator();
               prefixName = prefixName + childInject.value();
               if (ChildIterable.class.equals(requiredClass) && requiredType instanceof ParameterizedType) {
                  ParameterizedType pt = (ParameterizedType)requiredType;
                  requiredType = pt.getActualTypeArguments()[0];
                  requiredClass = ReflectionHelper.getRawClass(requiredType);
                  return requiredClass == null ? this.systemResolver.resolve(injectee, root) : new ChildIterableImpl(this.locator, requiredType, prefixName, separator);
               } else {
                  List matches = this.locator.getDescriptors(new ChildFilter(requiredType, prefixName));
                  if (matches.isEmpty()) {
                     if (injectee.isOptional()) {
                        return null;
                     } else {
                        throw new IllegalStateException("Could not find a child injection point for " + injectee);
                     }
                  } else {
                     return this.locator.getServiceHandle((ActiveDescriptor)matches.get(0)).getService();
                  }
               }
            }
         }
      }
   }

   private static ChildInject getInjectionAnnotation(AnnotatedElement element, int position) {
      if (element instanceof Field) {
         Field field = (Field)element;
         return (ChildInject)field.getAnnotation(ChildInject.class);
      } else {
         Annotation[] annotations;
         if (element instanceof Constructor) {
            Constructor constructor = (Constructor)element;
            annotations = constructor.getParameterAnnotations()[position];
         } else {
            if (!(element instanceof Method)) {
               throw new IllegalArgumentException();
            }

            Method method = (Method)element;
            annotations = method.getParameterAnnotations()[position];
         }

         Annotation[] var9 = annotations;
         int var4 = annotations.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Annotation annotation = var9[var5];
            if (annotation.annotationType().equals(ChildInject.class)) {
               return (ChildInject)annotation;
            }
         }

         throw new IllegalArgumentException();
      }
   }

   public boolean isConstructorParameterIndicator() {
      return true;
   }

   public boolean isMethodParameterIndicator() {
      return true;
   }
}
