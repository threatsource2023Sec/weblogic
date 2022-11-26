package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class SizeParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(SizeParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("InitialCapacity", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "getInitialCapacity", "setInitialCapacity");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxCapacity", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "getMaxCapacity", "setMaxCapacity");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CapacityIncrement", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "getCapacityIncrement", "setCapacityIncrement");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ShrinkingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "isShrinkingEnabled", "setShrinkingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ShrinkPeriodMinutes", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "getShrinkPeriodMinutes", "setShrinkPeriodMinutes");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ShrinkFrequencySeconds", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "getShrinkFrequencySeconds", "setShrinkFrequencySeconds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("HighestNumWaiters", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "getHighestNumWaiters", "setHighestNumWaiters");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("HighestNumUnavailable", Class.forName("weblogic.j2ee.descriptor.wl.SizeParamsBeanDConfig"), "getHighestNumUnavailable", "setHighestNumUnavailable");
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
            throw new AssertionError("Failed to create PropertyDescriptors for SizeParamsBeanDConfigBeanInfo");
         }
      }
   }
}
