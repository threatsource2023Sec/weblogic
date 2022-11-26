package weblogic.management.mbeans.custom;

import weblogic.management.configuration.XMLEntitySpecRegistryEntryMBean;
import weblogic.management.configuration.XMLParserSelectRegistryEntryMBean;
import weblogic.management.configuration.XMLRegistryMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class XMLRegistry extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = 3074913268437602278L;

   public XMLRegistry(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public XMLParserSelectRegistryEntryMBean findParserSelectMBeanByKey(String publicID, String systemID, String rootTag) {
      XMLRegistryMBean reg = (XMLRegistryMBean)((XMLRegistryMBean)this.getMbean());
      XMLParserSelectRegistryEntryMBean[] entries = reg.getParserSelectRegistryEntries();

      for(int i = 0; i < entries.length; ++i) {
         if (publicID != null && publicID.equals(entries[i].getPublicId())) {
            return entries[i];
         }

         if (systemID != null && systemID.equals(entries[i].getSystemId())) {
            return entries[i];
         }

         if (rootTag != null && rootTag.equals(entries[i].getRootElementTag())) {
            return entries[i];
         }
      }

      return null;
   }

   public XMLEntitySpecRegistryEntryMBean findEntitySpecMBeanByKey(String publicID, String systemID) {
      XMLRegistryMBean reg = (XMLRegistryMBean)((XMLRegistryMBean)this.getMbean());
      XMLEntitySpecRegistryEntryMBean[] entries = reg.getEntitySpecRegistryEntries();

      for(int i = 0; i < entries.length; ++i) {
         if (publicID != null && publicID.equals(entries[i].getPublicId())) {
            return entries[i];
         }

         if (systemID != null && systemID.equals(entries[i].getSystemId())) {
            return entries[i];
         }
      }

      return null;
   }

   public XMLParserSelectRegistryEntryMBean[] getParserSelectRegistryEntries() {
      return ((XMLRegistryMBean)((XMLRegistryMBean)this.getMbean())).getXMLParserSelectRegistryEntries();
   }

   public XMLEntitySpecRegistryEntryMBean[] getEntitySpecRegistryEntries() {
      return ((XMLRegistryMBean)((XMLRegistryMBean)this.getMbean())).getXMLEntitySpecRegistryEntries();
   }
}
