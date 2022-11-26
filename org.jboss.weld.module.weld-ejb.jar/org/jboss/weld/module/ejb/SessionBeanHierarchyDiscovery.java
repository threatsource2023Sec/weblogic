package org.jboss.weld.module.ejb;

import java.lang.reflect.Type;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.reflection.HierarchyDiscovery;

class SessionBeanHierarchyDiscovery extends HierarchyDiscovery {
   SessionBeanHierarchyDiscovery(Type type) {
      super(Types.getCanonicalType(type));
   }

   protected void discoverFromClass(Class clazz, boolean rawGeneric) {
      if (clazz.getSuperclass() != null) {
         this.discoverTypes(this.processAndResolveType(clazz.getGenericSuperclass(), clazz.getSuperclass()), rawGeneric);
      } else if (clazz.isInterface()) {
         this.discoverInterfaces(clazz, rawGeneric);
      }

   }
}
