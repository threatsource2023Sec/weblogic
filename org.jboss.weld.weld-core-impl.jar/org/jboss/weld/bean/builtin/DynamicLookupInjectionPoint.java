package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.ForwardingInjectionPoint;

public class DynamicLookupInjectionPoint extends ForwardingInjectionPoint implements Serializable {
   private static final long serialVersionUID = -4102173765226078459L;
   private final InjectionPoint injectionPoint;
   private final Type type;
   private final Set qualifiers;

   public DynamicLookupInjectionPoint(InjectionPoint injectionPoint, Type type, Set qualifiers) {
      this.injectionPoint = injectionPoint;
      this.type = type;
      this.qualifiers = qualifiers;
   }

   protected InjectionPoint delegate() {
      return this.injectionPoint;
   }

   public Type getType() {
      return this.type;
   }

   public Set getQualifiers() {
      return this.qualifiers;
   }
}
