package org.glassfish.hk2.configuration.internal;

import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.configuration.api.ConfiguredBy;

class NoNameTypeFilter implements IndexedFilter {
   private final ServiceLocator locator;
   private final String typeName;
   private final String instanceName;

   NoNameTypeFilter(ServiceLocator locator, String typeName, String instanceName) {
      this.locator = locator;
      this.typeName = typeName;
      this.instanceName = instanceName;
   }

   public boolean matches(Descriptor d) {
      if (this.instanceName == null) {
         if (this.typeName == null) {
            return d.getName() == null;
         }

         if (d.getName() != null) {
            return false;
         }
      } else {
         if (d.getName() == null) {
            return false;
         }

         if (!this.instanceName.equals(d.getName())) {
            return false;
         }
      }

      ActiveDescriptor reified;
      try {
         reified = this.locator.reifyDescriptor(d);
      } catch (MultiException var5) {
         return false;
      }

      Class implClass = reified.getImplementationClass();
      ConfiguredBy configuredBy = (ConfiguredBy)implClass.getAnnotation(ConfiguredBy.class);
      return configuredBy == null ? false : configuredBy.value().equals(this.typeName);
   }

   public String getAdvertisedContract() {
      return ConfiguredBy.class.getName();
   }

   public String getName() {
      return null;
   }

   public String toString() {
      return "NoNameTypeFilter(" + this.typeName + "," + this.instanceName + "," + System.identityHashCode(this) + ")";
   }
}
