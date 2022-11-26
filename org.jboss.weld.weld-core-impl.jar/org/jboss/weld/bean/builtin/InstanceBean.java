package org.jboss.weld.bean.builtin;

import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.util.collections.Arrays2;
import org.jboss.weld.util.reflection.Reflections;

public class InstanceBean extends AbstractFacadeBean {
   private static final Type INSTANCE_TYPE = (new TypeLiteral() {
      private static final long serialVersionUID = -1246199714407637856L;
   }).getType();
   private static final Type PROVIDER_TYPE = (new TypeLiteral() {
      private static final long serialVersionUID = -5256050387550468441L;
   }).getType();
   private static final Type WELD_INSTANCE_TYPE = (new TypeLiteral() {
      private static final long serialVersionUID = -1246199714407637856L;
   }).getType();
   private static final Set DEFAULT_TYPES;

   public InstanceBean(BeanManagerImpl manager) {
      super(manager, (Class)Reflections.cast(Instance.class));
   }

   public Class getBeanClass() {
      return InstanceImpl.class;
   }

   public Set getTypes() {
      return DEFAULT_TYPES;
   }

   protected Instance newInstance(InjectionPoint injectionPoint, CreationalContext creationalContext) {
      return InstanceImpl.of(injectionPoint, creationalContext, this.getBeanManager());
   }

   public String toString() {
      return "Implicit Bean [javax.enterprise.inject.Instance] with qualifiers [@Default]";
   }

   protected Type getDefaultType() {
      return INSTANCE_TYPE;
   }

   public boolean isDependentContextOptimizationAllowed() {
      return false;
   }

   static {
      DEFAULT_TYPES = Arrays2.asSet(INSTANCE_TYPE, WELD_INSTANCE_TYPE, PROVIDER_TYPE, Object.class);
   }
}
