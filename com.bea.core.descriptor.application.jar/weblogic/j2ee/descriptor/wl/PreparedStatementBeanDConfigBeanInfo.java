package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(PreparedStatementBeanDConfig.class);
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
            PropertyDescriptor pd = new PropertyDescriptor("ProfilingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.PreparedStatementBeanDConfig"), "isProfilingEnabled", "setProfilingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CacheProfilingThreshold", Class.forName("weblogic.j2ee.descriptor.wl.PreparedStatementBeanDConfig"), "getCacheProfilingThreshold", "setCacheProfilingThreshold");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CacheSize", Class.forName("weblogic.j2ee.descriptor.wl.PreparedStatementBeanDConfig"), "getCacheSize", "setCacheSize");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("ParameterLoggingEnabled", Class.forName("weblogic.j2ee.descriptor.wl.PreparedStatementBeanDConfig"), "isParameterLoggingEnabled", "setParameterLoggingEnabled");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("MaxParameterLength", Class.forName("weblogic.j2ee.descriptor.wl.PreparedStatementBeanDConfig"), "getMaxParameterLength", "setMaxParameterLength");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("CacheType", Class.forName("weblogic.j2ee.descriptor.wl.PreparedStatementBeanDConfig"), "getCacheType", "setCacheType");
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
            throw new AssertionError("Failed to create PropertyDescriptors for PreparedStatementBeanDConfigBeanInfo");
         }
      }
   }
}
