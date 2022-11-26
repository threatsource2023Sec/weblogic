package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class EntityCacheRefBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(EntityCacheRefBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("EntityCacheName", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanDConfig"), "getEntityCacheName", "setEntityCacheName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IdleTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanDConfig"), "getIdleTimeoutSeconds", "setIdleTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ReadTimeoutSeconds", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanDConfig"), "getReadTimeoutSeconds", "setReadTimeoutSeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("ConcurrencyStrategy", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanDConfig"), "getConcurrencyStrategy", "setConcurrencyStrategy");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CacheBetweenTransactions", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanDConfig"), "isCacheBetweenTransactions", "setCacheBetweenTransactions");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EstimatedBeanSize", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanDConfig"), "getEstimatedBeanSize", "setEstimatedBeanSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.EntityCacheRefBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for EntityCacheRefBeanDConfigBeanInfo");
         }
      }
   }
}
