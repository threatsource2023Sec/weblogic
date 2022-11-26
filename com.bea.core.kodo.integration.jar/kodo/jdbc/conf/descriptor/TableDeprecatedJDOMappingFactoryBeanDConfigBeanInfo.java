package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class TableDeprecatedJDOMappingFactoryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(TableDeprecatedJDOMappingFactoryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("TableName", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getTableName", "setTableName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("URLs", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getURLs", "setURLs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClasspathScan", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getClasspathScan", "setClasspathScan");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Types", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getTypes", "setTypes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MappingColumn", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getMappingColumn", "setMappingColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StoreMode", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getStoreMode", "setStoreMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Strict", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getStrict", "setStrict");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NameColumn", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getNameColumn", "setNameColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseSchemaValidation", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getUseSchemaValidation", "setUseSchemaValidation");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SingleRow", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getSingleRow", "setSingleRow");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Files", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getFiles", "setFiles");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ScanTopDown", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getScanTopDown", "setScanTopDown");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Resources", Class.forName("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanDConfig"), "getResources", "setResources");
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
            throw new AssertionError("Failed to create PropertyDescriptors for TableDeprecatedJDOMappingFactoryBeanDConfigBeanInfo");
         }
      }
   }
}
