package org.jboss.weld.injection.attributes;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.ForwardingInjectionPoint;

public abstract class AbstractForwardingInjectionPointAttributes extends ForwardingInjectionPoint implements WeldInjectionPointAttributes, Serializable {
   private static final long serialVersionUID = -7540261474875045335L;
   private final InjectionPoint delegate;

   public AbstractForwardingInjectionPointAttributes(InjectionPoint delegate) {
      this.delegate = delegate;
   }

   protected InjectionPoint delegate() {
      return this.delegate;
   }

   public Annotation getQualifier(Class annotationType) {
      Iterator var2 = this.getQualifiers().iterator();

      Annotation qualifier;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         qualifier = (Annotation)var2.next();
      } while(!qualifier.annotationType().equals(annotationType));

      return (Annotation)annotationType.cast(qualifier);
   }
}
