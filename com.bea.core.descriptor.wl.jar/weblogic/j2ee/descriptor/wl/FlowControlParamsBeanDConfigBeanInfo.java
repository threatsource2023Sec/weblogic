package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class FlowControlParamsBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(FlowControlParamsBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("FlowMinimum", Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanDConfig"), "getFlowMinimum", "setFlowMinimum");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("FlowMaximum", Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanDConfig"), "getFlowMaximum", "setFlowMaximum");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("FlowInterval", Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanDConfig"), "getFlowInterval", "setFlowInterval");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("FlowSteps", Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanDConfig"), "getFlowSteps", "setFlowSteps");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("FlowControlEnabled", Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanDConfig"), "isFlowControlEnabled", "setFlowControlEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("OneWaySendMode", Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanDConfig"), "getOneWaySendMode", "setOneWaySendMode");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pd = new PropertyDescriptor("OneWaySendWindowSize", Class.forName("weblogic.j2ee.descriptor.wl.FlowControlParamsBeanDConfig"), "getOneWaySendWindowSize", "setOneWaySendWindowSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", true);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for FlowControlParamsBeanDConfigBeanInfo");
         }
      }
   }
}
