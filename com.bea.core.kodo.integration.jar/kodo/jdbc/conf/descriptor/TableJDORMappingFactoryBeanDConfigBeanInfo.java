package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class TableJDORMappingFactoryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(TableJDORMappingFactoryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("UseSchemaValidation", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getUseSchemaValidation", "setUseSchemaValidation");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TypeColumn", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getTypeColumn", "setTypeColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ConstraintNames", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getConstraintNames", "setConstraintNames");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Table", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getTable", "setTable");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Types", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getTypes", "setTypes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StoreMode", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getStoreMode", "setStoreMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MappingColumn", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getMappingColumn", "setMappingColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Strict", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getStrict", "setStrict");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NameColumn", Class.forName("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanDConfig"), "getNameColumn", "setNameColumn");
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
            throw new AssertionError("Failed to create PropertyDescriptors for TableJDORMappingFactoryBeanDConfigBeanInfo");
         }
      }
   }
}
