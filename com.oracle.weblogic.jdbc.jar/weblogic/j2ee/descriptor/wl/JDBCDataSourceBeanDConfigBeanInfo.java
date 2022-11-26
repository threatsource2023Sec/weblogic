package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class JDBCDataSourceBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(JDBCDataSourceBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Name", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getName", "setName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DatasourceType", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getDatasourceType", "setDatasourceType");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("InternalProperties", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getInternalProperties", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("JDBCDriverParams", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getJDBCDriverParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JDBCConnectionPoolParams", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getJDBCConnectionPoolParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JDBCDataSourceParams", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getJDBCDataSourceParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JDBCXAParams", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getJDBCXAParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("JDBCOracleParams", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getJDBCOracleParams", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Version", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getVersion", "setVersion");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getId", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SecondaryDescriptors", Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig"), "getSecondaryDescriptors", (String)null);
            pd.setValue("dependency", Boolean.FALSE);
            pd.setValue("declaration", Boolean.FALSE);
            pd.setValue("configurable", Boolean.FALSE);
            pd.setValue("key", Boolean.FALSE);
            pd.setValue("dynamic", Boolean.FALSE);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for JDBCDataSourceBeanDConfigBeanInfo");
         }
      }
   }
}
