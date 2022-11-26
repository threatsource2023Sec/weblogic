package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ClassTableJDBCSeqBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ClassTableJDBCSeqBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Type", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getType", "setType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Allocate", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getAllocate", "setAllocate");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TableName", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getTableName", "setTableName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IgnoreVirtual", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getIgnoreVirtual", "setIgnoreVirtual");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IgnoreUnmapped", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getIgnoreUnmapped", "setIgnoreUnmapped");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Table", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getTable", "setTable");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PrimaryKeyColumn", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getPrimaryKeyColumn", "setPrimaryKeyColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseAliases", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getUseAliases", "setUseAliases");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SequenceColumn", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getSequenceColumn", "setSequenceColumn");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Increment", Class.forName("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanDConfig"), "getIncrement", "setIncrement");
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
            throw new AssertionError("Failed to create PropertyDescriptors for ClassTableJDBCSeqBeanDConfigBeanInfo");
         }
      }
   }
}
