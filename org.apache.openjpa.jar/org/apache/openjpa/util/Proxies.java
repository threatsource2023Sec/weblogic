package org.apache.openjpa.util;

import java.security.AccessController;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;

public class Proxies {
   private static final Localizer _loc = Localizer.forPackage(Proxies.class);

   public static boolean isOwner(Proxy proxy, OpenJPAStateManager sm, int field) {
      return proxy.getOwner() == sm && proxy.getOwnerField() == field;
   }

   public static void assertAllowedType(Object value, Class allowed) {
      if (value != null && allowed != null && !allowed.isInstance(value)) {
         throw new UserException(_loc.get("bad-elem-type", new Object[]{(ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(allowed)), allowed, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(value.getClass())), value.getClass()}));
      }
   }

   public static void dirty(Proxy proxy, boolean stopTracking) {
      if (proxy.getOwner() != null) {
         proxy.getOwner().dirty(proxy.getOwnerField());
      }

      if (stopTracking && proxy.getChangeTracker() != null) {
         proxy.getChangeTracker().stopTracking();
      }

   }

   public static void removed(Proxy proxy, Object removed, boolean key) {
      if (proxy.getOwner() != null && removed != null) {
         proxy.getOwner().removed(proxy.getOwnerField(), removed, key);
      }

   }

   public static Object writeReplace(Proxy proxy, boolean detachable) {
      return !detachable || proxy != null && proxy.getOwner() != null && !proxy.getOwner().isDetached() ? proxy.copy(proxy) : proxy;
   }
}
