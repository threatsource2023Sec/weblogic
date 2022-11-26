package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicRdbmsBeanBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicRdbmsBeanBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("EjbName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getEjbName", "setEjbName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DataSourceJNDIName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getDataSourceJNDIName", "setDataSourceJNDIName");
            pd.setValue("dependency", true);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UnknownPrimaryKeyField", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getUnknownPrimaryKeyField", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TableMaps", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getTableMaps", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("FieldGroups", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getFieldGroups", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RelationshipCachings", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getRelationshipCachings", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SqlShapes", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getSqlShapes", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("WeblogicQueries", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getWeblogicQueries", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DelayDatabaseInsertUntil", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getDelayDatabaseInsertUntil", "setDelayDatabaseInsertUntil");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseSelectForUpdate", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "isUseSelectForUpdate", "setUseSelectForUpdate");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("LockOrder", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getLockOrder", "setLockOrder");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("InstanceLockOrder", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getInstanceLockOrder", "setInstanceLockOrder");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AutomaticKeyGeneration", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getAutomaticKeyGeneration", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CheckExistsOnMethod", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "isCheckExistsOnMethod", "setCheckExistsOnMethod");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getId", "setId");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ClusterInvalidationDisabled", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "isClusterInvalidationDisabled", "setClusterInvalidationDisabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UseInnerJoin", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "isUseInnerJoin", "setUseInnerJoin");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CategoryCmpField", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBeanDConfig"), "getCategoryCmpField", "setCategoryCmpField");
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
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicRdbmsBeanBeanDConfigBeanInfo");
         }
      }
   }
}
