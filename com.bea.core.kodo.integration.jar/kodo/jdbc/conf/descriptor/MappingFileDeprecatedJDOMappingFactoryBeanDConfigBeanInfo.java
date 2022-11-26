package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class MappingFileDeprecatedJDOMappingFactoryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(MappingFileDeprecatedJDOMappingFactoryBeanDConfig.class);
   static PropertyDescriptor[] pds = null;

   public BeanDescriptor getBeanDescriptor() {
      return this.bd;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      if (pds != null) {
         return pds;
      } else {
         List plist = new ArrayList();

         try {
            PropertyDescriptor pd = new PropertyDescriptor("UseSchemaValidation", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getUseSchemaValidation", "setUseSchemaValidation");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("URLs", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getURLs", "setURLs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Files", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getFiles", "setFiles");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClasspathScan", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getClasspathScan", "setClasspathScan");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SingleFile", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getSingleFile", "setSingleFile");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Types", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getTypes", "setTypes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FileName", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getFileName", "setFileName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StoreMode", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getStoreMode", "setStoreMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Strict", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getStrict", "setStrict");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Resources", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getResources", "setResources");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ScanTopDown", Class.forName("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanDConfig"), "getScanTopDown", "setScanTopDown");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for MappingFileDeprecatedJDOMappingFactoryBeanDConfigBeanInfo");
         }
      }
   }
}
