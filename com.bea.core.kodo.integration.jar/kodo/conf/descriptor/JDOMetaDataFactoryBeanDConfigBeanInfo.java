package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDOMetaDataFactoryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDOMetaDataFactoryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("UseSchemaValidation", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getUseSchemaValidation", "setUseSchemaValidation");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("URLs", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getURLs", "setURLs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Files", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getFiles", "setFiles");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClasspathScan", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getClasspathScan", "setClasspathScan");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConstraintNames", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getConstraintNames", "setConstraintNames");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Types", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getTypes", "setTypes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StoreMode", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getStoreMode", "setStoreMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Strict", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getStrict", "setStrict");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Resources", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getResources", "setResources");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ScanTopDown", Class.forName("kodo.conf.descriptor.JDOMetaDataFactoryBeanDConfig"), "getScanTopDown", "setScanTopDown");
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
            throw new AssertionError("Failed to create PropertyDescriptors for JDOMetaDataFactoryBeanDConfigBeanInfo");
         }
      }
   }
}
