package org.jboss.weld.security;

import java.lang.reflect.AccessibleObject;
import java.security.PrivilegedAction;

public class SetAccessibleAction implements PrivilegedAction {
   private final AccessibleObject object;
   private final boolean value;

   public static SetAccessibleAction of(AccessibleObject object) {
      return of(object, true);
   }

   public static SetAccessibleAction of(AccessibleObject object, boolean value) {
      return new SetAccessibleAction(object, value);
   }

   private SetAccessibleAction(AccessibleObject object, boolean value) {
      this.object = object;
      this.value = value;
   }

   public AccessibleObject run() {
      this.object.setAccessible(this.value);
      return this.object;
   }
}
