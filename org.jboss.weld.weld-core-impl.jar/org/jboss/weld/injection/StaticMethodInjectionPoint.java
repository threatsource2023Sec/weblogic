package org.jboss.weld.injection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.TransientReference;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.reflection.Reflections;

class StaticMethodInjectionPoint extends MethodInjectionPoint {
   private final int specialInjectionPointIndex;
   private final AnnotatedMethod annotatedMethod;
   final Method accessibleMethod;

   StaticMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType methodInjectionPointType, EnhancedAnnotatedMethod enhancedMethod, Bean declaringBean, Class declaringComponentClass, Set specialParameterMarkers, InjectionPointFactory factory, BeanManagerImpl manager) {
      super(methodInjectionPointType, enhancedMethod, declaringBean, declaringComponentClass, factory, manager);
      this.accessibleMethod = SecurityActions.getAccessibleCopyOfMethod(enhancedMethod.getJavaMember());
      this.annotatedMethod = enhancedMethod.slim();
      this.specialInjectionPointIndex = initSpecialInjectionPointIndex(enhancedMethod, specialParameterMarkers);
   }

   private static int initSpecialInjectionPointIndex(EnhancedAnnotatedMethod enhancedMethod, Set specialParameterMarkers) {
      if (specialParameterMarkers != null && !specialParameterMarkers.isEmpty()) {
         List parameters = Collections.emptyList();
         Iterator var3 = specialParameterMarkers.iterator();

         while(var3.hasNext()) {
            Class marker = (Class)var3.next();
            parameters = enhancedMethod.getEnhancedParameters(marker);
            if (!parameters.isEmpty()) {
               break;
            }
         }

         if (parameters.isEmpty()) {
            throw new IllegalArgumentException("Not a disposer nor observer method: " + enhancedMethod);
         } else {
            return ((EnhancedAnnotatedParameter)parameters.get(0)).getPosition();
         }
      } else {
         return -1;
      }
   }

   public Object invoke(Object receiver, Object specialValue, BeanManagerImpl manager, CreationalContext ctx, Class exceptionTypeToThrow) {
      CreationalContext transientReferenceContext = null;
      if (this.hasTransientReferenceParameter) {
         transientReferenceContext = manager.createCreationalContext((Contextual)null);
      }

      Object var7;
      try {
         var7 = this.invoke(receiver, this.getParameterValues(specialValue, manager, ctx, transientReferenceContext), exceptionTypeToThrow);
      } finally {
         if (this.hasTransientReferenceParameter) {
            transientReferenceContext.release();
         }

      }

      return var7;
   }

   public Object invoke(Object receiver, Object[] parameters, Class exceptionTypeToThrow) {
      try {
         return Reflections.cast(this.getMethod(receiver).invoke(receiver, parameters));
      } catch (java.lang.IllegalArgumentException var5) {
         Exceptions.rethrowException(var5, exceptionTypeToThrow);
      } catch (SecurityException var6) {
         Exceptions.rethrowException(var6, exceptionTypeToThrow);
      } catch (IllegalAccessException var7) {
         Exceptions.rethrowException(var7, exceptionTypeToThrow);
      } catch (InvocationTargetException var8) {
         Exceptions.rethrowException(var8, exceptionTypeToThrow);
      } catch (NoSuchMethodException var9) {
         Exceptions.rethrowException(var9, exceptionTypeToThrow);
      }

      return null;
   }

   protected Object[] getParameterValues(Object specialVal, BeanManagerImpl manager, CreationalContext ctx, CreationalContext transientReferenceContext) {
      if (this.getInjectionPoints().isEmpty()) {
         return this.specialInjectionPointIndex == -1 ? Arrays2.EMPTY_ARRAY : new Object[]{specialVal};
      } else {
         Object[] parameterValues = new Object[this.getParameterInjectionPoints().size()];
         List parameters = this.getParameterInjectionPoints();

         for(int i = 0; i < parameterValues.length; ++i) {
            ParameterInjectionPoint param = (ParameterInjectionPoint)parameters.get(i);
            if (i == this.specialInjectionPointIndex) {
               parameterValues[i] = specialVal;
            } else if (this.hasTransientReferenceParameter && param.getAnnotated().isAnnotationPresent(TransientReference.class)) {
               parameterValues[i] = param.getValueToInject(manager, transientReferenceContext);
            } else {
               parameterValues[i] = param.getValueToInject(manager, ctx);
            }
         }

         return parameterValues;
      }
   }

   protected Method getMethod(Object receiver) throws NoSuchMethodException {
      return this.accessibleMethod;
   }

   public AnnotatedMethod getAnnotated() {
      return this.annotatedMethod;
   }
}
