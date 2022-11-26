package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class KodoBrokerBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(KodoBrokerBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("LargeTransaction", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getLargeTransaction", "setLargeTransaction");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AutoClear", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getAutoClear", "setAutoClear");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DetachState", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getDetachState", "setDetachState");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NontransactionalRead", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getNontransactionalRead", "setNontransactionalRead");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RetainState", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getRetainState", "setRetainState");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EvictFromDataCache", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getEvictFromDataCache", "setEvictFromDataCache");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DetachedNew", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getDetachedNew", "setDetachedNew");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Optimistic", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getOptimistic", "setOptimistic");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NontransactionalWrite", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getNontransactionalWrite", "setNontransactionalWrite");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("SyncWithManagedTransactions", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getSyncWithManagedTransactions", "setSyncWithManagedTransactions");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Multithreaded", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getMultithreaded", "setMultithreaded");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("PopulateDataCache", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getPopulateDataCache", "setPopulateDataCache");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("IgnoreChanges", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getIgnoreChanges", "setIgnoreChanges");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("AutoDetach", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getAutoDetach", "setAutoDetach");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RestoreState", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getRestoreState", "setRestoreState");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("OrderDirtyObjects", Class.forName("kodo.conf.descriptor.KodoBrokerBeanDConfig"), "getOrderDirtyObjects", "setOrderDirtyObjects");
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
            throw new AssertionError("Failed to create PropertyDescriptors for KodoBrokerBeanDConfigBeanInfo");
         }
      }
   }
}
