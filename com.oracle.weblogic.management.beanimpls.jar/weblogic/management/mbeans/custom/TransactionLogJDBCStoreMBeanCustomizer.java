package weblogic.management.mbeans.custom;

import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class TransactionLogJDBCStoreMBeanCustomizer extends ConfigurationMBeanCustomizer {
   private transient String prefixName = null;

   public TransactionLogJDBCStoreMBeanCustomizer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public String getPrefixName() {
      if (this.prefixName != null) {
         return this.prefixName;
      } else {
         Object bean = this.getMbean().getParent();
         if (bean != null) {
            return bean instanceof ServerTemplateMBean && !(bean instanceof ServerMBean) ? "TLOG_${serverName}_" : "TLOG_" + ((ServerMBean)bean).getName() + "_";
         } else {
            return null;
         }
      }
   }

   public void setPrefixName(String name) {
      if (name != null) {
         this.prefixName = name;
      }

   }
}
