package org.jboss.weld.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any.Literal;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bootstrap.MissingDependenciesRegistry;
import org.jboss.weld.injection.FieldInjectionPoint;
import org.jboss.weld.injection.MethodInjectionPoint;
import org.jboss.weld.injection.ParameterInjectionPoint;
import org.jboss.weld.injection.ParameterInjectionPointImpl;
import org.jboss.weld.injection.attributes.ForwardingFieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ForwardingParameterInjectionPointAttributes;
import org.jboss.weld.injection.attributes.SpecialParameterInjectionPoint;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public class InjectionPoints {
   private InjectionPoints() {
   }

   public static Set flattenInjectionPoints(List fieldInjectionPoints) {
      Set injectionPoints = new HashSet();
      Iterator var2 = fieldInjectionPoints.iterator();

      while(var2.hasNext()) {
         Set i = (Set)var2.next();
         injectionPoints.addAll(i);
      }

      return injectionPoints;
   }

   public static Set flattenParameterInjectionPoints(List methodInjectionPoints) {
      Set injectionPoints = new HashSet();
      Iterator var2 = methodInjectionPoints.iterator();

      while(var2.hasNext()) {
         Set i = (Set)var2.next();
         Iterator var4 = i.iterator();

         while(var4.hasNext()) {
            MethodInjectionPoint method = (MethodInjectionPoint)var4.next();
            Iterator var6 = method.getParameterInjectionPoints().iterator();

            while(var6.hasNext()) {
               ParameterInjectionPoint parameter = (ParameterInjectionPoint)var6.next();
               injectionPoints.add(parameter);
            }
         }
      }

      return injectionPoints;
   }

   public static Set filterOutSpecialParameterInjectionPoints(List injectionPoints) {
      ImmutableSet.Builder filtered = ImmutableSet.builder();
      Iterator var2 = injectionPoints.iterator();

      while(var2.hasNext()) {
         ParameterInjectionPoint parameter = (ParameterInjectionPoint)var2.next();
         if (!(parameter instanceof SpecialParameterInjectionPoint)) {
            filtered.add(parameter);
         }
      }

      return filtered.build();
   }

   public static WeldInjectionPointAttributes getWeldInjectionPoint(InjectionPoint injectionPoint) {
      if (injectionPoint instanceof WeldInjectionPointAttributes) {
         return (WeldInjectionPointAttributes)Reflections.cast(injectionPoint);
      } else {
         return (WeldInjectionPointAttributes)(injectionPoint.getAnnotated() instanceof AnnotatedField ? FieldInjectionPoint.silent(ForwardingFieldInjectionPointAttributes.of(injectionPoint)) : ParameterInjectionPointImpl.silent(ForwardingParameterInjectionPointAttributes.of(injectionPoint)));
      }
   }

   public static boolean isInjectableReferenceLookupOptimizationAllowed(Bean bean, Bean resolvedBean) {
      Preconditions.checkArgumentNotNull(resolvedBean, "resolvedBean");
      return bean != null && (RequestScoped.class.equals(bean.getScope()) && Beans.hasBuiltinScope(resolvedBean) || ApplicationScoped.class.equals(bean.getScope()) && ApplicationScoped.class.equals(resolvedBean.getScope()));
   }

   public static String getUnsatisfiedDependenciesAdditionalInfo(InjectionPoint ij, BeanManagerImpl beanManager) {
      Set beansMatchedByType = beanManager.getBeans(ij.getType(), Literal.INSTANCE);
      if (beansMatchedByType.isEmpty()) {
         Class rawType = Reflections.getRawType(ij.getType());
         if (rawType != null) {
            MissingDependenciesRegistry missingDependenciesRegistry = (MissingDependenciesRegistry)beanManager.getServices().get(MissingDependenciesRegistry.class);
            String missingDependency = missingDependenciesRegistry.getMissingDependencyForClass(rawType.getName());
            if (missingDependency != null) {
               return ValidatorLogger.LOG.unsatisfiedDependencyBecauseClassIgnored(rawType.getName(), missingDependency);
            }
         }

         return "";
      } else {
         return ValidatorLogger.LOG.unsatisfiedDependencyBecauseQualifiersDontMatch(WeldCollections.toMultiRowString(beansMatchedByType));
      }
   }
}
