package org.glassfish.hk2.configuration.internal;

import java.lang.reflect.Type;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;

class ChildFilter implements IndexedFilter {
   private final String requiredType;
   private final String requiredPrefix;
   private final String requiredSuffix;

   ChildFilter(Type type, String prefix, String suffix) {
      Class requiredTypeClass = ReflectionHelper.getRawClass(type);
      this.requiredType = requiredTypeClass.getName();
      this.requiredPrefix = prefix;
      this.requiredSuffix = suffix;
   }

   ChildFilter(Type type, String prefix) {
      this(type, prefix, (String)null);
   }

   public boolean matches(Descriptor d) {
      if (d.getName() == null) {
         return false;
      } else if (!d.getName().startsWith(this.requiredPrefix)) {
         return false;
      } else {
         return this.requiredSuffix == null ? true : d.getName().endsWith(this.requiredSuffix);
      }
   }

   public String getAdvertisedContract() {
      return this.requiredType;
   }

   public String getName() {
      return null;
   }
}
