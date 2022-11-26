package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class PointbaseDictionaryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(PointbaseDictionaryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("CharTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getCharTypeName", "setCharTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClobTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getClobTypeName", "setClobTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithDistinctClause", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getSupportsLockingWithDistinctClause", "setSupportsLockingWithDistinctClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LongVarbinaryTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getLongVarbinaryTypeName", "setLongVarbinaryTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithMultipleTables", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getSupportsLockingWithMultipleTables", "setSupportsLockingWithMultipleTables");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DoubleTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getDoubleTypeName", "setDoubleTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BitTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getBitTypeName", "setBitTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsAutoAssign", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getSupportsAutoAssign", "setSupportsAutoAssign");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AutoAssignTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getAutoAssignTypeName", "setAutoAssignTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsMultipleNontransactionalResultSets", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getSupportsMultipleNontransactionalResultSets", "setSupportsMultipleNontransactionalResultSets");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IntegerTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getIntegerTypeName", "setIntegerTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BlobTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getBlobTypeName", "setBlobTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Platform", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getPlatform", "setPlatform");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BigintTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getBigintTypeName", "setBigintTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LastGeneratedKeyQuery", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getLastGeneratedKeyQuery", "setLastGeneratedKeyQuery");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsDeferredConstraints", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getSupportsDeferredConstraints", "setSupportsDeferredConstraints");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RealTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getRealTypeName", "setRealTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RequiresAliasForSubselect", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getRequiresAliasForSubselect", "setRequiresAliasForSubselect");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TinyintTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getTinyintTypeName", "setTinyintTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SmallintTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getSmallintTypeName", "setSmallintTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FloatTypeName", Class.forName("kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanDConfig"), "getFloatTypeName", "setFloatTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for PointbaseDictionaryBeanDConfigBeanInfo");
         }
      }
   }
}
