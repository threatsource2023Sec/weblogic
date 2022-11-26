package weblogic.management.mbeans.custom;

import java.io.File;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.management.configuration.CustomResourceMBean;
import weblogic.management.internal.ManagementTextTextFormatter;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.internal.DescriptorInfoUtils;

public class CustomResource extends ConfigurationExtension {
   private String _DescriptorFileName;

   public CustomResource(ConfigurationMBeanCustomized customized) {
      super(customized);
   }

   public String getDescriptorFileName() {
      return this._DescriptorFileName;
   }

   public void setDescriptorFileName(String descriptorFileName) {
      File f = null;
      if (descriptorFileName != null) {
         f = new File(descriptorFileName);
         if (f.isAbsolute()) {
            throw new IllegalArgumentException(ManagementTextTextFormatter.getInstance().getFileCannotBeAbsolute(descriptorFileName));
         }
      }

      String prefix = "custom/";
      if (descriptorFileName != null && this.isEdit() && f.getParent() == null) {
         this._DescriptorFileName = prefix + descriptorFileName;
      } else {
         this._DescriptorFileName = descriptorFileName;
      }

   }

   public DescriptorBean getResource() {
      ClassLoader dcl = DescriptorClassLoader.getClassLoader();
      CustomResourceMBean customResource = (CustomResourceMBean)this.getMbean();
      String interfaceClassName = customResource.getDescriptorBeanClass();

      Class interfaceClass;
      try {
         interfaceClass = dcl.loadClass(interfaceClassName);
      } catch (ClassNotFoundException var6) {
         throw new AssertionError(var6);
      }

      return this.getExtensionRoot(interfaceClass, "Resource", "custom");
   }

   public DescriptorBean getCustomResource() {
      return this.getResource();
   }

   public void _postCreate() {
      this.getResource();
   }

   public void _preDestroy() {
      DescriptorBean bean = this.getMbean();
      DescriptorInfoUtils.removeDescriptorInfo(this.getResource(), bean.getDescriptor());
   }
}
