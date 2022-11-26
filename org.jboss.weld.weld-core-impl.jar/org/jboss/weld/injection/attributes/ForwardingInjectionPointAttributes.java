package org.jboss.weld.injection.attributes;

import java.lang.annotation.Annotation;
import org.jboss.weld.injection.ForwardingInjectionPoint;

public abstract class ForwardingInjectionPointAttributes extends ForwardingInjectionPoint implements WeldInjectionPointAttributes {
   public Annotation getQualifier(Class annotationType) {
      return this.delegate().getQualifier(annotationType);
   }

   protected abstract WeldInjectionPointAttributes delegate();
}
