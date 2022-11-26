package org.jboss.weld.resolution;

import java.lang.reflect.Type;
import java.util.Set;

public interface AssignabilityRules {
   boolean matches(Set var1, Set var2);

   boolean matches(Type var1, Set var2);

   boolean matches(Type var1, Type var2);
}
