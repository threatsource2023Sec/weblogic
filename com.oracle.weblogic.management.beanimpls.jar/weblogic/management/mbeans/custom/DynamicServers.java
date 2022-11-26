package weblogic.management.mbeans.custom;

import weblogic.management.configuration.ConfigurationValidator;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class DynamicServers extends ConfigurationMBeanCustomizer {
   private String _ServerNamePrefix;

   public DynamicServers(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setServerNamePrefix(String serverNamePrefix) {
      if (serverNamePrefix != null && serverNamePrefix.length() > 0) {
         ConfigurationValidator.validateName(serverNamePrefix);
      }

      this._ServerNamePrefix = serverNamePrefix;
   }

   public String getServerNamePrefix() {
      return this._ServerNamePrefix;
   }

   public int getDynamicClusterSize() {
      DynamicServersMBean dynServersMBean = (DynamicServersMBean)this.getMbean();
      return dynServersMBean.getMaximumDynamicServerCount();
   }

   public void setDynamicClusterSize(int size) {
      DynamicServersMBean dynServersMBean = (DynamicServersMBean)this.getMbean();
      dynServersMBean.setMaximumDynamicServerCount(size);
   }

   public String[] getDynamicServerNames() {
      int numServers = this.getDynamicClusterSize();
      if (numServers <= 0) {
         return new String[0];
      } else {
         String serverNamePrefix = this.getServerNamePrefix();
         int serverNameStartingIndex = ((DynamicServersMBean)this.getMbean()).getServerNameStartingIndex();
         String[] serverNames = new String[numServers];

         for(int i = 0; i < serverNames.length; ++i) {
            serverNames[i] = serverNamePrefix + (i + serverNameStartingIndex);
         }

         return serverNames;
      }
   }
}
