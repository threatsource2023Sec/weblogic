package org.jboss.weld.injection.attributes;

import java.lang.reflect.Field;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.reflection.Reflections;

public class ForwardingFieldInjectionPointAttributes extends AbstractForwardingInjectionPointAttributes implements FieldInjectionPointAttributes {
   private static final long serialVersionUID = 427326058103802742L;

   public static FieldInjectionPointAttributes of(InjectionPoint ip) {
      if (ip instanceof FieldInjectionPointAttributes) {
         return (FieldInjectionPointAttributes)Reflections.cast(ip);
      } else if (ip.getAnnotated() instanceof AnnotatedField && ip.getMember() instanceof Field) {
         return new ForwardingFieldInjectionPointAttributes(ip);
      } else {
         throw BeanLogger.LOG.invalidInjectionPointType(ForwardingFieldInjectionPointAttributes.class, ip.getAnnotated());
      }
   }

   protected ForwardingFieldInjectionPointAttributes(InjectionPoint delegate) {
      super(delegate);
   }

   public AnnotatedField getAnnotated() {
      return (AnnotatedField)Reflections.cast(this.delegate().getAnnotated());
   }

   public Field getMember() {
      return (Field)this.delegate().getMember();
   }
}
