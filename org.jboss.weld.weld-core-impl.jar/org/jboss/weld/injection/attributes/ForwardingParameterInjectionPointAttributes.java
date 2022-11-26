package org.jboss.weld.injection.attributes;

import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.reflection.Reflections;

public class ForwardingParameterInjectionPointAttributes extends AbstractForwardingInjectionPointAttributes implements ParameterInjectionPointAttributes {
   private static final long serialVersionUID = 6109999203440035470L;

   public static ForwardingParameterInjectionPointAttributes of(InjectionPoint ip) {
      if (ip instanceof ForwardingParameterInjectionPointAttributes) {
         return (ForwardingParameterInjectionPointAttributes)Reflections.cast(ip);
      } else if (!(ip.getAnnotated() instanceof AnnotatedParameter)) {
         throw BeanLogger.LOG.invalidInjectionPointType(ForwardingParameterInjectionPointAttributes.class, ip.getAnnotated());
      } else {
         return new ForwardingParameterInjectionPointAttributes(ip);
      }
   }

   protected ForwardingParameterInjectionPointAttributes(InjectionPoint delegate) {
      super(delegate);
   }

   public AnnotatedParameter getAnnotated() {
      return (AnnotatedParameter)Reflections.cast(this.delegate().getAnnotated());
   }
}
