package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class TCPRemoteCommitProviderBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(TCPRemoteCommitProviderBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("MaxIdle", Class.forName("kodo.conf.descriptor.TCPRemoteCommitProviderBeanDConfig"), "getMaxIdle", "setMaxIdle");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("NumBroadcastThreads", Class.forName("kodo.conf.descriptor.TCPRemoteCommitProviderBeanDConfig"), "getNumBroadcastThreads", "setNumBroadcastThreads");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RecoveryTimeMillis", Class.forName("kodo.conf.descriptor.TCPRemoteCommitProviderBeanDConfig"), "getRecoveryTimeMillis", "setRecoveryTimeMillis");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxActive", Class.forName("kodo.conf.descriptor.TCPRemoteCommitProviderBeanDConfig"), "getMaxActive", "setMaxActive");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Port", Class.forName("kodo.conf.descriptor.TCPRemoteCommitProviderBeanDConfig"), "getPort", "setPort");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", true);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Addresses", Class.forName("kodo.conf.descriptor.TCPRemoteCommitProviderBeanDConfig"), "getAddresses", "setAddresses");
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
            throw new AssertionError("Failed to create PropertyDescriptors for TCPRemoteCommitProviderBeanDConfigBeanInfo");
         }
      }
   }
}
