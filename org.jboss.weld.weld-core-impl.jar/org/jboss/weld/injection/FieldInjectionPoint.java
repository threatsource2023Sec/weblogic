package org.jboss.weld.injection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.AccessController;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.bean.proxy.DecoratorProxy;
import org.jboss.weld.injection.attributes.FieldInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ForwardingInjectionPointAttributes;
import org.jboss.weld.injection.attributes.WeldInjectionPointAttributes;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
   justification = "The bean cache is loaded lazily."
)
public class FieldInjectionPoint extends ForwardingInjectionPointAttributes implements WeldInjectionPointAttributes, Serializable {
   private static final long serialVersionUID = 6645272914499045953L;
   private final boolean cacheable;
   private transient Bean cachedBean;
   private final transient Field accessibleField;
   private final FieldInjectionPointAttributes attributes;

   public static FieldInjectionPoint silent(FieldInjectionPointAttributes attributes) {
      return new FieldInjectionPoint(attributes);
   }

   protected FieldInjectionPoint(FieldInjectionPointAttributes attributes) {
      this.attributes = attributes;
      this.cacheable = isCacheableInjectionPoint(attributes);
      this.accessibleField = (Field)AccessController.doPrivileged(new GetAccessibleCopyOfMember(attributes.getMember()));
   }

   protected static boolean isCacheableInjectionPoint(WeldInjectionPointAttributes attributes) {
      if (attributes.isDelegate()) {
         return false;
      } else {
         Class rawType = Reflections.getRawType(attributes.getType());
         return !InjectionPoint.class.isAssignableFrom(rawType) && !Instance.class.isAssignableFrom(rawType);
      }
   }

   public void inject(Object declaringInstance, BeanManagerImpl manager, CreationalContext creationalContext) {
      try {
         Object instanceToInject = declaringInstance;
         if (!(declaringInstance instanceof DecoratorProxy) && declaringInstance instanceof TargetInstanceProxy) {
            instanceToInject = ((TargetInstanceProxy)Reflections.cast(declaringInstance)).weld_getTargetInstance();
         }

         Object objectToInject;
         if (!this.cacheable) {
            objectToInject = manager.getInjectableReference(this, creationalContext);
         } else {
            if (this.cachedBean == null) {
               this.cachedBean = manager.resolve(manager.getBeans((InjectionPoint)this));
            }

            objectToInject = manager.getInjectableReference(this, this.cachedBean, creationalContext);
         }

         this.accessibleField.set(instanceToInject, objectToInject);
      } catch (IllegalArgumentException var6) {
         Exceptions.rethrowException(var6);
      } catch (IllegalAccessException var7) {
         Exceptions.rethrowException(var7);
      }

   }

   protected FieldInjectionPointAttributes delegate() {
      return this.attributes;
   }

   public AnnotatedField getAnnotated() {
      return this.attributes.getAnnotated();
   }

   private Object readResolve() throws ObjectStreamException {
      return new FieldInjectionPoint(this.attributes);
   }
}
