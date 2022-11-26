package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class ExecutionContextNameProviderBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(ExecutionContextNameProviderBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("StackExecutionContextNameProvider", Class.forName("kodo.conf.descriptor.ExecutionContextNameProviderBeanDConfig"), "getStackExecutionContextNameProvider", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("TransactionNameExecutionContextNameProvider", Class.forName("kodo.conf.descriptor.ExecutionContextNameProviderBeanDConfig"), "getTransactionNameExecutionContextNameProvider", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("UserObjectExecutionContextNameProvider", Class.forName("kodo.conf.descriptor.ExecutionContextNameProviderBeanDConfig"), "getUserObjectExecutionContextNameProvider", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ExecutionContextNameProviderTypes", Class.forName("kodo.conf.descriptor.ExecutionContextNameProviderBeanDConfig"), "getExecutionContextNameProviderTypes", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ExecutionContextNameProvider", Class.forName("kodo.conf.descriptor.ExecutionContextNameProviderBeanDConfig"), "getExecutionContextNameProvider", (String)null);
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
            throw new AssertionError("Failed to create PropertyDescriptors for ExecutionContextNameProviderBeanDConfigBeanInfo");
         }
      }
   }
}
