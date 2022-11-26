package org.jboss.weld.bean.builtin;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.ForwardingInjectionPoint;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.Beans;
import org.jboss.weld.util.reflection.ParameterizedTypeImpl;

public class FacadeInjectionPoint extends ForwardingInjectionPoint implements Serializable {
   private static final long serialVersionUID = -4102173765226078459L;
   private final InjectionPoint injectionPoint;
   private final Type type;
   private final Set qualifiers;

   public FacadeInjectionPoint(BeanManagerImpl manager, InjectionPoint injectionPoint, Type rawType, Type subtype, Set existingQualifiers, Annotation[] newQualifiers) {
      this.injectionPoint = injectionPoint;
      this.type = new ParameterizedTypeImpl(rawType, new Type[]{subtype}, (Type)null);
      this.qualifiers = Beans.mergeInQualifiers(manager, existingQualifiers, newQualifiers);
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
