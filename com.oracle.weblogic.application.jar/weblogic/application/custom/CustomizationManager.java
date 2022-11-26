package weblogic.application.custom;

import javax.enterprise.deploy.shared.ModuleType;

public abstract class CustomizationManager {
   protected static final CustomizationManagerInternal instance = new CustomizationManagerInternal();

   public static CustomizationManager getInstance() {
      return instance;
   }

   public abstract DescriptorLookup registerCustomDescriptor(ModuleType var1, String var2, Class var3, DescriptorRegistration var4) throws CustomizationException;
}
