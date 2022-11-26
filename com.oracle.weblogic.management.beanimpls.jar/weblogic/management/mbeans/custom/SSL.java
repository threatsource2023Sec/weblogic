package weblogic.management.mbeans.custom;

import weblogic.management.configuration.SSLMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class SSL extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = -2131237960306760262L;

   public SSL(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public boolean isListenPortEnabled() {
      return ((SSLMBean)this.getMbean()).isEnabled();
   }
}
