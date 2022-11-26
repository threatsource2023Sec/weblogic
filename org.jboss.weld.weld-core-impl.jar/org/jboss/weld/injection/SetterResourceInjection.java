package org.jboss.weld.injection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import javax.enterprise.inject.spi.AnnotatedMethod;
import org.jboss.weld.bean.proxy.DecoratorProxy;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.reflection.Reflections;

class SetterResourceInjection extends AbstractResourceInjection {
   private final Method accessibleMethod;

   SetterResourceInjection(ParameterInjectionPoint injectionPoint, ResourceReferenceFactory factory) {
      super(factory);
      AnnotatedMethod annotatedMethod = (AnnotatedMethod)injectionPoint.getAnnotated().getDeclaringCallable();
      this.accessibleMethod = (Method)AccessController.doPrivileged(new GetAccessibleCopyOfMember(annotatedMethod.getJavaMember()));
   }

   protected void injectMember(Object declaringInstance, Object reference) {
      try {
         Object instanceToInject = declaringInstance;
         if (!(declaringInstance instanceof DecoratorProxy) && declaringInstance instanceof TargetInstanceProxy) {
            instanceToInject = ((TargetInstanceProxy)Reflections.cast(declaringInstance)).weld_getTargetInstance();
         }

         this.accessibleMethod.invoke(instanceToInject, reference);
      } catch (IllegalArgumentException var4) {
         Exceptions.rethrowException(var4);
      } catch (IllegalAccessException var5) {
         Exceptions.rethrowException(var5);
      } catch (InvocationTargetException var6) {
         Exceptions.rethrowException(var6);
      }

   }

   Method getMember() {
      return this.accessibleMethod;
   }
}
