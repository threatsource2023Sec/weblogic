package org.jboss.weld.annotated.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedMethod;
import org.jboss.weld.util.annotated.ForwardingAnnotatedMethod;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Reflections;

public class InvokableAnnotatedMethod extends ForwardingAnnotatedMethod {
   private final AnnotatedMethod annotatedMethod;
   private volatile Map methods;

   public static InvokableAnnotatedMethod of(AnnotatedMethod delegate) {
      return new InvokableAnnotatedMethod(delegate);
   }

   public InvokableAnnotatedMethod(AnnotatedMethod annotatedMethod) {
      this.annotatedMethod = annotatedMethod;
      this.methods = Collections.singletonMap(annotatedMethod.getJavaMember().getDeclaringClass(), annotatedMethod.getJavaMember());
      SecurityActions.ensureAccessible(annotatedMethod.getJavaMember());
   }

   public Object invoke(Object instance, Object... parameters) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
      return Reflections.cast(this.annotatedMethod.getJavaMember().invoke(instance, parameters));
   }

   public Object invokeOnInstance(Object instance, Object... parameters) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      Map methods = this.methods;
      Method method = (Method)methods.get(instance.getClass());
      if (method == null) {
         Method delegate = this.annotatedMethod.getJavaMember();
         method = SecurityActions.lookupMethod(instance.getClass(), delegate.getName(), delegate.getParameterTypes());
         SecurityActions.ensureAccessible(method);
         synchronized(this) {
            Map newMethods = new HashMap(methods);
            newMethods.put(instance.getClass(), method);
            this.methods = WeldCollections.immutableMapView(newMethods);
         }
      }

      return Reflections.cast(method.invoke(instance, parameters));
   }

   public AnnotatedMethod delegate() {
      return this.annotatedMethod;
   }
}
