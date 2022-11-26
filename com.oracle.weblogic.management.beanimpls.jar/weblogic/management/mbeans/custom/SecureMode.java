package weblogic.management.mbeans.custom;

import weblogic.descriptor.Descriptor;
import weblogic.management.configuration.SecureModeMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.DescriptorHelper;

public final class SecureMode extends ConfigurationMBeanCustomizer {
   private boolean secureModeEnabled = false;

   public SecureMode(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public boolean isSecureModeEnabled() {
      return this.secureModeEnabled;
   }

   public void setSecureModeEnabled(boolean secureModeEnabled) {
      this.secureModeEnabled = secureModeEnabled;
      Descriptor rootDescriptor = ((SecureModeMBean)this.getMbean()).getDescriptor();
      DescriptorHelper.setDescriptorTreeSecureMode(rootDescriptor, secureModeEnabled);
   }
}
