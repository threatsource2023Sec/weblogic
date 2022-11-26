package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class EntityCacheBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(EntityCacheBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("MaxBeansInCache", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "getMaxBeansInCache", "setMaxBeansInCache");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxQueriesInCache", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "getMaxQueriesInCache", "setMaxQueriesInCache");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IdleTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "getIdleTimeoutSeconds", "setIdleTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ReadTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "getReadTimeoutSeconds", "setReadTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConcurrencyStrategy", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "getConcurrencyStrategy", "setConcurrencyStrategy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CacheBetweenTransactions", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "isCacheBetweenTransactions", "setCacheBetweenTransactions");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DisableReadyInstances", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "isDisableReadyInstances", "setDisableReadyInstances");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for EntityCacheBeanDConfigBeanInfo");
         }
      }
   }
}
