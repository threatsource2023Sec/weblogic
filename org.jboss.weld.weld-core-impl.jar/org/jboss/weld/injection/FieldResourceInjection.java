package org.jboss.weld.injection;

import java.lang.reflect.Field;
import java.security.AccessController;
import org.jboss.weld.bean.proxy.DecoratorProxy;
import org.jboss.weld.injection.spi.ResourceReferenceFactory;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.reflection.Reflections;

class FieldResourceInjection extends AbstractResourceInjection {
   private final Field accessibleField;

   FieldResourceInjection(FieldInjectionPoint fieldInjectionPoint, ResourceReferenceFactory factory) {
      super(factory);
      this.accessibleField = (Field)AccessController.doPrivileged(new GetAccessibleCopyOfMember(fieldInjectionPoint.getAnnotated().getJavaMember()));
   }

   protected void injectMember(Object declaringInstance, Object reference) {
      try {
         Object instanceToInject = declaringInstance;
         if (!(declaringInstance instanceof DecoratorProxy) && declaringInstance instanceof TargetInstanceProxy) {
            instanceToInject = ((TargetInstanceProxy)Reflections.cast(declaringInstance)).weld_getTargetInstance();
         }

         this.accessibleField.set(instanceToInject, reference);
      } catch (IllegalArgumentException var4) {
         Exceptions.rethrowException(var4);
      } catch (IllegalAccessException var5) {
         Exceptions.rethrowException(var5);
      }

   }

   Field getMember() {
      return this.accessibleField;
   }
}
