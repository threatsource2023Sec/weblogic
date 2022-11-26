package org.apache.openjpa.persistence;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.event.LifecycleCallbacks;
import org.apache.openjpa.event.MethodLifecycleCallbacks;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataDefaults;
import org.apache.openjpa.util.UserException;

class MetaDataParsers {
   private static final Localizer _loc = Localizer.forPackage(MetaDataParsers.class);

   public static int[] getEventTypes(MetaDataTag tag) {
      switch (tag) {
         case PRE_PERSIST:
            return new int[]{0};
         case POST_PERSIST:
            return new int[]{18};
         case PRE_REMOVE:
            return new int[]{7};
         case POST_REMOVE:
            return new int[]{19};
         case PRE_UPDATE:
            return new int[]{20};
         case POST_UPDATE:
            return new int[]{21};
         case POST_LOAD:
            return new int[]{2};
         default:
            return null;
      }
   }

   public static void validateMethodsForSameCallback(Class cls, Collection callbacks, Method method, MetaDataTag tag, MetaDataDefaults def, Log log) {
      if (callbacks != null && !callbacks.isEmpty()) {
         Iterator i$ = callbacks.iterator();

         while(i$.hasNext()) {
            LifecycleCallbacks lc = (LifecycleCallbacks)i$.next();
            if (lc instanceof MethodLifecycleCallbacks) {
               Method exists = ((MethodLifecycleCallbacks)lc).getCallbackMethod();
               if (exists.getDeclaringClass().equals(method.getDeclaringClass())) {
                  PersistenceMetaDataDefaults defaults = getPersistenceDefaults(def);
                  Localizer.Message msg = _loc.get("multiple-methods-on-callback", new Object[]{method.getDeclaringClass().getName(), method.getName(), exists.getName(), tag.toString()});
                  if (defaults != null && !defaults.getAllowsMultipleMethodsForSameCallback()) {
                     throw new UserException(msg);
                  }

                  log.warn(msg);
               }
            }
         }

      }
   }

   private static PersistenceMetaDataDefaults getPersistenceDefaults(MetaDataDefaults def) {
      return def instanceof PersistenceMetaDataDefaults ? (PersistenceMetaDataDefaults)def : null;
   }
}
