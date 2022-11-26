package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class KodoCompatibilityBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(KodoCompatibilityBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("CopyObjectIds", Class.forName("kodo.conf.descriptor.KodoCompatibilityBeanDConfig"), "getCopyObjectIds", "setCopyObjectIds");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CloseOnManagedCommit", Class.forName("kodo.conf.descriptor.KodoCompatibilityBeanDConfig"), "getCloseOnManagedCommit", "setCloseOnManagedCommit");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ValidateTrueChecksStore", Class.forName("kodo.conf.descriptor.KodoCompatibilityBeanDConfig"), "getValidateTrueChecksStore", "setValidateTrueChecksStore");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ValidateFalseReturnsHollow", Class.forName("kodo.conf.descriptor.KodoCompatibilityBeanDConfig"), "getValidateFalseReturnsHollow", "setValidateFalseReturnsHollow");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("StrictIdentityValues", Class.forName("kodo.conf.descriptor.KodoCompatibilityBeanDConfig"), "getStrictIdentityValues", "setStrictIdentityValues");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("QuotedNumbersInQueries", Class.forName("kodo.conf.descriptor.KodoCompatibilityBeanDConfig"), "getQuotedNumbersInQueries", "setQuotedNumbersInQueries");
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
            throw new AssertionError("Failed to create PropertyDescriptors for KodoCompatibilityBeanDConfigBeanInfo");
         }
      }
   }
}
