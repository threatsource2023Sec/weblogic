package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicQueryBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicQueryBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("Description", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getDescription", "setDescription");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("QueryMethod", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getQueryMethod", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EjbQlQuery", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getEjbQlQuery", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SqlQuery", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getSqlQuery", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxElements", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getMaxElements", "setMaxElements");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IncludeUpdates", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "isIncludeUpdates", "setIncludeUpdates");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IncludeUpdatesSet", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "isIncludeUpdatesSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SqlSelectDistinct", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "isSqlSelectDistinct", "setSqlSelectDistinct");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getId", "setId");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableQueryCaching", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getEnableQueryCaching", "setEnableQueryCaching");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableEagerRefresh", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "getEnableEagerRefresh", "setEnableEagerRefresh");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IncludeResultCacheHint", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicQueryBeanDConfig"), "isIncludeResultCacheHint", "setIncludeResultCacheHint");
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
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicQueryBeanDConfigBeanInfo");
         }
      }
   }
}
