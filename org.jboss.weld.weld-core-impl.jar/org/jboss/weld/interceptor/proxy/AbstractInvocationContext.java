package org.jboss.weld.interceptor.proxy;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jboss.weld.interceptor.WeldInvocationContext;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.Primitives;
import org.jboss.weld.util.collections.ImmutableSet;

abstract class AbstractInvocationContext implements WeldInvocationContext {
   protected Map contextData;
   protected final Method method;
   protected Object[] parameters;
   protected final Object target;
   protected final Object timer;
   protected final Constructor constructor;
   protected final Set interceptorBindings;
   protected final Method proceed;
   private static final Map WIDENING_TABLE;

   protected AbstractInvocationContext(Object target, Method method, Method proceed, Object[] parameters, Map contextData, Set interceptorBindings) {
      this(target, method, proceed, (Constructor)null, parameters, (Object)null, contextData, interceptorBindings);
   }

   protected AbstractInvocationContext(Object target, Method method, Method proceed, Constructor constructor, Object[] parameters, Object timer, Map contextData, Set interceptorBindings) {
      this.target = target;
      this.method = method;
      this.proceed = proceed;
      this.constructor = constructor;
      this.parameters = parameters;
      this.timer = timer;
      this.contextData = contextData;
      this.interceptorBindings = interceptorBindings;
   }

   public Map getContextData() {
      if (this.contextData == null) {
         this.contextData = newContextData(this.interceptorBindings);
      }

      return this.contextData;
   }

   protected static Map newContextData(Set interceptorBindings) {
      Map result = new HashMap();
      result.put("org.jboss.weld.interceptor.bindings", interceptorBindings);
      return result;
   }

   public Method getMethod() {
      return this.method;
   }

   @SuppressFBWarnings({"EI_EXPOSE_REP"})
   public Object[] getParameters() {
      if (this.method == null && this.constructor == null) {
         throw new IllegalStateException("Illegal invocation to getParameters() during lifecycle invocation");
      } else {
         return this.parameters;
      }
   }

   public Object getTarget() {
      return this.target;
   }

   private static boolean isWideningPrimitive(Class argumentClass, Class targetClass) {
      return WIDENING_TABLE.containsKey(argumentClass) && ((Set)WIDENING_TABLE.get(argumentClass)).contains(targetClass);
   }

   @SuppressFBWarnings({"EI_EXPOSE_REP"})
   public void setParameters(Object[] params) {
      if (this.method == null && this.constructor == null) {
         throw new IllegalStateException("Illegal invocation to setParameters() during lifecycle invocation");
      } else {
         int newParametersCount = params == null ? 0 : params.length;
         Class[] parameterTypes = null;
         if (this.method != null) {
            parameterTypes = this.method.getParameterTypes();
         } else {
            parameterTypes = this.constructor.getParameterTypes();
         }

         if (parameterTypes.length != newParametersCount) {
            throw new IllegalArgumentException("Wrong number of parameters: method has " + parameterTypes.length + ", attempting to set " + newParametersCount + (params != null ? "" : " (argument was null)"));
         } else {
            if (params != null) {
               for(int i = 0; i < params.length; ++i) {
                  Class methodParameterClass = parameterTypes[i];
                  if (params[i] != null) {
                     Class newArgumentClass = params[i].getClass();
                     if (newArgumentClass.equals(methodParameterClass)) {
                        break;
                     }

                     Class boxedArgumentClass;
                     if (newArgumentClass.isPrimitive()) {
                        if (methodParameterClass.isPrimitive()) {
                           if (!isWideningPrimitive(newArgumentClass, methodParameterClass)) {
                              this.throwIAE(i, methodParameterClass, newArgumentClass);
                           }
                        } else {
                           boxedArgumentClass = Primitives.wrap(newArgumentClass);
                           if (!methodParameterClass.isAssignableFrom(boxedArgumentClass)) {
                              this.throwIAE(i, methodParameterClass, newArgumentClass);
                           }
                        }
                     } else if (methodParameterClass.isPrimitive()) {
                        boxedArgumentClass = Primitives.unwrap(newArgumentClass);
                        if (!boxedArgumentClass.equals(methodParameterClass) && !isWideningPrimitive(boxedArgumentClass, methodParameterClass)) {
                           this.throwIAE(i, methodParameterClass, newArgumentClass);
                        }
                     } else if (!methodParameterClass.isAssignableFrom(newArgumentClass)) {
                        this.throwIAE(i, methodParameterClass, newArgumentClass);
                     }
                  } else if (parameterTypes[i].isPrimitive()) {
                     throw new IllegalArgumentException("Trying to set a null value on a " + parameterTypes[i].getName());
                  }
               }

               this.parameters = params;
            }

         }
      }
   }

   private void throwIAE(int i, Class methodParameterClass, Class newArgumentClass) {
      throw new IllegalArgumentException("Incompatible parameter type on position: " + i + " :" + newArgumentClass + " (expected type was " + methodParameterClass.getName() + ")");
   }

   public Object getTimer() {
      return this.timer;
   }

   public Constructor getConstructor() {
      return this.constructor;
   }

   public Set getInterceptorBindingsByType(Class annotationType) {
      Preconditions.checkArgumentNotNull(annotationType, "annotationType");
      return (Set)this.interceptorBindings.stream().filter((annotation) -> {
         return annotation.annotationType().equals(annotationType);
      }).map((annotation) -> {
         return annotation;
      }).collect(ImmutableSet.collector());
   }

   public Set getInterceptorBindings() {
      return this.interceptorBindings;
   }

   protected Method getProceed() {
      return this.proceed;
   }

   static {
      Map wideningTable = new HashMap();
      wideningTable.put(Byte.TYPE, ImmutableSet.of(Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE));
      wideningTable.put(Short.TYPE, ImmutableSet.of(Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE));
      wideningTable.put(Character.TYPE, ImmutableSet.of(Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE));
      wideningTable.put(Integer.TYPE, ImmutableSet.of(Long.TYPE, Float.TYPE, Double.TYPE));
      wideningTable.put(Long.TYPE, ImmutableSet.of(Float.TYPE, Double.TYPE));
      wideningTable.put(Float.TYPE, Collections.singleton(Double.TYPE));
      WIDENING_TABLE = Collections.unmodifiableMap(wideningTable);
   }
}
