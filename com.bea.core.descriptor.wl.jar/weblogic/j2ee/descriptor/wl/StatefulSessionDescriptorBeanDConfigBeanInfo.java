package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class StatefulSessionDescriptorBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(StatefulSessionDescriptorBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("StatefulSessionCache", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "getStatefulSessionCache", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatefulSessionCacheSet", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "isStatefulSessionCacheSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PersistentStoreDir", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "getPersistentStoreDir", "setPersistentStoreDir");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatefulSessionClustering", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "getStatefulSessionClustering", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StatefulSessionClusteringSet", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "isStatefulSessionClusteringSet", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AllowRemoveDuringTransaction", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "isAllowRemoveDuringTransaction", "setAllowRemoveDuringTransaction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("BusinessInterfaceJndiNameMaps", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "getBusinessInterfaceJndiNameMaps", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBeanDConfig"), "getId", "setId");
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
            throw new AssertionError("Failed to create PropertyDescriptors for StatefulSessionDescriptorBeanDConfigBeanInfo");
         }
      }
   }
}
