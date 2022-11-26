package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class AccessDictionaryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(AccessDictionaryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("BinaryTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getBinaryTypeName", "setBinaryTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClobTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getClobTypeName", "setClobTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxConstraintNameLength", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getMaxConstraintNameLength", "setMaxConstraintNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LongVarbinaryTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getLongVarbinaryTypeName", "setLongVarbinaryTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LongVarcharTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getLongVarcharTypeName", "setLongVarcharTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxColumnNameLength", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getMaxColumnNameLength", "setMaxColumnNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SmallintTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getSmallintTypeName", "setSmallintTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsAutoAssign", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getSupportsAutoAssign", "setSupportsAutoAssign");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AutoAssignTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getAutoAssignTypeName", "setAutoAssignTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseGetBytesForBlobs", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getUseGetBytesForBlobs", "setUseGetBytesForBlobs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ValidationSQL", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getValidationSQL", "setValidationSQL");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NumericTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getNumericTypeName", "setNumericTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IntegerTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getIntegerTypeName", "setIntegerTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BlobTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getBlobTypeName", "setBlobTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseGetBestRowIdentifierForPrimaryKeys", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getUseGetBestRowIdentifierForPrimaryKeys", "setUseGetBestRowIdentifierForPrimaryKeys");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsForeignKeys", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getSupportsForeignKeys", "setSupportsForeignKeys");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Platform", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getPlatform", "setPlatform");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LastGeneratedKeyQuery", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getLastGeneratedKeyQuery", "setLastGeneratedKeyQuery");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsDeferredConstraints", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getSupportsDeferredConstraints", "setSupportsDeferredConstraints");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BigintTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getBigintTypeName", "setBigintTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxIndexNameLength", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getMaxIndexNameLength", "setMaxIndexNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxTableNameLength", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getMaxTableNameLength", "setMaxTableNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxIndexesPerTable", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getMaxIndexesPerTable", "setMaxIndexesPerTable");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JoinSyntax", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getJoinSyntax", "setJoinSyntax");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TinyintTypeName", Class.forName("kodo.jdbc.conf.descriptor.AccessDictionaryBeanDConfig"), "getTinyintTypeName", "setTinyintTypeName");
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
            throw new AssertionError("Failed to create PropertyDescriptors for AccessDictionaryBeanDConfigBeanInfo");
         }
      }
   }
}
