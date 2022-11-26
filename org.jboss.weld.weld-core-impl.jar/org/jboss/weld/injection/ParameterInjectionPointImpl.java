package org.jboss.weld.injection;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.injection.attributes.ForwardingInjectionPointAttributes;
import org.jboss.weld.injection.attributes.ParameterInjectionPointAttributes;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.reflection.Reflections;

@SuppressFBWarnings(
   value = {"SE_TRANSIENT_FIELD_NOT_RESTORED"},
   justification = "cachedBean field is loaded lazily"
)
public class ParameterInjectionPointImpl extends ForwardingInjectionPointAttributes implements ParameterInjectionPoint, Serializable {
   private static final long serialVersionUID = -8354344628345860324L;
   private final boolean cacheable;
   private transient Bean cachedBean;
   private ParameterInjectionPointAttributes attributes;

   public static ParameterInjectionPointImpl silent(ParameterInjectionPointAttributes attributes) {
      return new ParameterInjectionPointImpl(attributes);
   }

   protected ParameterInjectionPointImpl(ParameterInjectionPointAttributes attributes) {
      this.attributes = attributes;
      this.cacheable = FieldInjectionPoint.isCacheableInjectionPoint(attributes);
   }

   protected ParameterInjectionPointAttributes delegate() {
      return this.attributes;
   }

   public void inject(Object declaringInstance, Object value) {
      throw new UnsupportedOperationException();
   }

   public Object getValueToInject(BeanManagerImpl manager, CreationalContext creationalContext) {
      Object objectToInject;
      if (!this.cacheable) {
         objectToInject = Reflections.cast(manager.getInjectableReference(this, creationalContext));
      } else {
         if (this.cachedBean == null) {
            this.cachedBean = manager.resolve(manager.getBeans((InjectionPoint)this));
         }

         objectToInject = Reflections.cast(manager.getInjectableReference(this, this.cachedBean, creationalContext));
      }

      return objectToInject;
   }

   public AnnotatedParameter getAnnotated() {
      return this.attributes.getAnnotated();
   }
}
