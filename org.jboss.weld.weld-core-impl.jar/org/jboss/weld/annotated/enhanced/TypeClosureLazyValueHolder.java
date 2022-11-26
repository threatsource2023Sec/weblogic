package org.jboss.weld.annotated.enhanced;

import java.lang.reflect.Type;
import java.util.Set;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.reflection.HierarchyDiscovery;

public class TypeClosureLazyValueHolder extends LazyValueHolder {
   private final Type type;

   public TypeClosureLazyValueHolder(Type type) {
      this.type = type;
   }

   protected Set computeValue() {
      return (new HierarchyDiscovery(this.type)).getTypeClosure();
   }
}
