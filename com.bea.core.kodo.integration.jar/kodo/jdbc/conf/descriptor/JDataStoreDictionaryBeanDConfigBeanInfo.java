package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDataStoreDictionaryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDataStoreDictionaryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ClobTypeName", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getClobTypeName", "setClobTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsLockingWithDistinctClause", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getSupportsLockingWithDistinctClause", "setSupportsLockingWithDistinctClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsQueryTimeout", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getSupportsQueryTimeout", "setSupportsQueryTimeout");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxConstraintNameLength", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getMaxConstraintNameLength", "setMaxConstraintNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SearchStringEscape", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getSearchStringEscape", "setSearchStringEscape");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxColumnNameLength", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getMaxColumnNameLength", "setMaxColumnNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseGetStringForClobs", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getUseGetStringForClobs", "setUseGetStringForClobs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsAutoAssign", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getSupportsAutoAssign", "setSupportsAutoAssign");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AllowsAliasInBulkClause", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getAllowsAliasInBulkClause", "setAllowsAliasInBulkClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AutoAssignClause", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getAutoAssignClause", "setAutoAssignClause");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseGetBytesForBlobs", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getUseGetBytesForBlobs", "setUseGetBytesForBlobs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BlobTypeName", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getBlobTypeName", "setBlobTypeName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseSetStringForClobs", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getUseSetStringForClobs", "setUseSetStringForClobs");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Platform", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getPlatform", "setPlatform");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LastGeneratedKeyQuery", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getLastGeneratedKeyQuery", "setLastGeneratedKeyQuery");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SupportsDeferredConstraints", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getSupportsDeferredConstraints", "setSupportsDeferredConstraints");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxIndexNameLength", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getMaxIndexNameLength", "setMaxIndexNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxTableNameLength", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getMaxTableNameLength", "setMaxTableNameLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JoinSyntax", Class.forName("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanDConfig"), "getJoinSyntax", "setJoinSyntax");
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
            throw new AssertionError("Failed to create PropertyDescriptors for JDataStoreDictionaryBeanDConfigBeanInfo");
         }
      }
   }
}
