package weblogic.management.provider.internal;

import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.management.configuration.ConfigurationExtensionMBean;

public class DescriptorInfo {
   public static final String DESCRIPTOR_INFO = "DescriptorInfo";
   public static final String DELETED_DESCRIPTOR_INFO = "DeletedDescriptorInfo";
   public static final String TRANSIENT_DESCRIPTOR_INFO = "TransientDescriptorInfo";
   public static final String NOT_FOUND_DESCRIPTOR_INFO = "NotFoundDescriptorInfo";
   public static final String DESCRIPTOR_EXTENSION_LOAD = "DescriptorExtensionLoad";
   public static final String EXTENSION_TEMPORARY_FILES = "ExtensionTemporaryFiles";
   public static final String DESCRIPTOR_CONFIG_EXTENSION = "DescriptorConfigExtension";
   public static final String DESCRIPTOR_CONFIG_EXTENSION_ATTRIBUTE = "DescriptorConfigExtensionAttribute";
   public static final String DESCRIPTOR_CONFIG_APPLICATION_NAME = "ApplicationName";
   public static final String DESCRIPTOR_CONFIG_MODULE_NAME = "ModuleName";
   public static final String DESCRIPTOR_CONFIG_MODULE_TYPE = "ModuleType";
   private Descriptor descriptor = null;
   private Class descriptorClass = null;
   private DescriptorBean descriptorBean = null;
   private DescriptorManager descriptorManager = null;
   private ConfigurationExtensionMBean configurationExtension = null;

   public DescriptorInfo(Descriptor desc, Class descClass, DescriptorBean descBean, DescriptorManager descMgr, ConfigurationExtensionMBean configExt) {
      this.descriptor = desc;
      this.descriptorClass = descClass;
      this.descriptorBean = descBean;
      this.descriptorManager = descMgr;
      this.configurationExtension = configExt;
   }

   public Descriptor getDescriptor() {
      return this.descriptor;
   }

   public Class getDescriptorClass() {
      return this.descriptorClass;
   }

   public DescriptorBean getDescriptorBean() {
      return this.descriptorBean;
   }

   public DescriptorManager getDescriptorManager() {
      return this.descriptorManager;
   }

   public ConfigurationExtensionMBean getConfigurationExtension() {
      return this.configurationExtension;
   }
}
