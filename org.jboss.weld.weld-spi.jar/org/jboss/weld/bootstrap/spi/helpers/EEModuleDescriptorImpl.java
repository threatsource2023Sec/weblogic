package org.jboss.weld.bootstrap.spi.helpers;

import org.jboss.weld.bootstrap.spi.EEModuleDescriptor;

public class EEModuleDescriptorImpl implements EEModuleDescriptor {
   private final String id;
   private final EEModuleDescriptor.ModuleType moduleType;

   public EEModuleDescriptorImpl(String id, EEModuleDescriptor.ModuleType moduleType) {
      this.id = id;
      this.moduleType = moduleType;
   }

   public String getId() {
      return this.id;
   }

   public EEModuleDescriptor.ModuleType getType() {
      return this.moduleType;
   }

   public void cleanup() {
   }
}
