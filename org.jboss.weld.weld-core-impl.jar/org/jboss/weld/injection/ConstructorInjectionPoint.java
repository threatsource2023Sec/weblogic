package org.jboss.weld.injection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.TransientReference;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.annotated.enhanced.ConstructorSignature;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.construction.api.AroundConstructCallback;
import org.jboss.weld.construction.api.ConstructionHandle;
import org.jboss.weld.contexts.CreationalContextImpl;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings({"EQ_DOESNT_OVERRIDE_EQUALS"})
public class ConstructorInjectionPoint extends AbstractCallableInjectionPoint {
   private final AnnotatedConstructor constructor;
   private final ConstructorSignature signature;
   private final Constructor accessibleConstructor;

   protected ConstructorInjectionPoint(EnhancedAnnotatedConstructor constructor, Bean declaringBean, Class declaringComponentClass, InjectionPointFactory factory, BeanManagerImpl manager) {
      super(constructor, declaringBean, declaringComponentClass, false, factory, manager);
      this.constructor = constructor.slim();
      this.signature = constructor.getSignature();
      this.accessibleConstructor = (Constructor)AccessController.doPrivileged(new GetAccessibleCopyOfMember(constructor.getJavaMember()));
   }

   public Object newInstance(BeanManagerImpl manager, CreationalContext ctx) {
      CreationalContext transientReferenceContext = null;
      if (this.hasTransientReferenceParameter) {
         transientReferenceContext = manager.createCreationalContext((Contextual)null);
      }

      Object var6;
      try {
         Object[] parameterValues = this.getParameterValues(manager, ctx, transientReferenceContext);
         if (!(ctx instanceof CreationalContextImpl)) {
            Object var10 = this.newInstance(parameterValues);
            return var10;
         }

         CreationalContextImpl weldCtx = (CreationalContextImpl)Reflections.cast(ctx);
         var6 = this.invokeAroundConstructCallbacks(parameterValues, weldCtx);
      } finally {
         if (this.hasTransientReferenceParameter) {
            transientReferenceContext.release();
         }

      }

      return var6;
   }

   private Object invokeAroundConstructCallbacks(Object[] parameters, CreationalContextImpl ctx) {
      List callbacks = ctx.getAroundConstructCallbacks();
      if (callbacks.isEmpty()) {
         return this.newInstance(parameters);
      } else {
         final Iterator iterator = callbacks.iterator();
         return this.invokeAroundConstructCallback((AroundConstructCallback)iterator.next(), new ConstructionHandle() {
            public Object proceed(Object[] parameters, Map data) {
               return iterator.hasNext() ? ConstructorInjectionPoint.this.invokeAroundConstructCallback((AroundConstructCallback)iterator.next(), this, ConstructorInjectionPoint.this.getComponentConstructor(), parameters, data) : ConstructorInjectionPoint.this.newInstance(parameters);
            }
         }, this.getComponentConstructor(), parameters, new HashMap());
      }
   }

   private Object invokeAroundConstructCallback(AroundConstructCallback callback, ConstructionHandle ctx, AnnotatedConstructor constructor, Object[] parameters, Map data) {
      try {
         return callback.aroundConstruct(ctx, constructor, parameters, data);
      } catch (RuntimeException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new WeldException(var8);
      }
   }

   protected Object newInstance(Object[] parameterValues) {
      try {
         return this.accessibleConstructor.newInstance(parameterValues);
      } catch (IllegalArgumentException var3) {
         Exceptions.rethrowException(var3);
      } catch (InstantiationException var4) {
         Exceptions.rethrowException(var4);
      } catch (IllegalAccessException var5) {
         Exceptions.rethrowException(var5);
      } catch (InvocationTargetException var6) {
         Exceptions.rethrowException(var6);
      }

      return null;
   }

   public Object[] getParameterValues(BeanManagerImpl manager, CreationalContext ctx, CreationalContext transientReference) {
      if (this.getInjectionPoints().isEmpty()) {
         return Arrays2.EMPTY_ARRAY;
      } else {
         Object[] parameterValues = new Object[this.getParameterInjectionPoints().size()];
         List parameters = this.getParameterInjectionPoints();

         for(int i = 0; i < parameterValues.length; ++i) {
            ParameterInjectionPoint param = (ParameterInjectionPoint)parameters.get(i);
            if (this.hasTransientReferenceParameter && param.getAnnotated().isAnnotationPresent(TransientReference.class)) {
               parameterValues[i] = param.getValueToInject(manager, transientReference);
            } else {
               parameterValues[i] = param.getValueToInject(manager, ctx);
            }
         }

         return parameterValues;
      }
   }

   public AnnotatedConstructor getAnnotated() {
      return this.constructor;
   }

   public ConstructorSignature getSignature() {
      return this.signature;
   }

   public AnnotatedConstructor getComponentConstructor() {
      return this.constructor;
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.constructor == null ? 0 : this.constructor.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ConstructorInjectionPoint other = (ConstructorInjectionPoint)obj;
         if (this.constructor == null) {
            if (other.constructor != null) {
               return false;
            }
         } else if (!this.constructor.equals(other.constructor)) {
            return false;
         }

         return true;
      }
   }
}
