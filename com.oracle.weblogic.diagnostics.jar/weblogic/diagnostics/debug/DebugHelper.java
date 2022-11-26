package weblogic.diagnostics.debug;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import weblogic.common.internal.VersionInfo;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.ServerDebugMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceClient;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

public class DebugHelper {
   private static final Set EXCLUDED_ATTRS = new HashSet() {
      {
         this.add("PartitionDebugLoggingEnabled");
      }
   };

   public static DebugScopeUtil getDebugScopeUtil() {
      return ServerDebugService.getInstance();
   }

   public static void validatePartitionDebugAttributes(String[] debugAttrs) {
      if (debugAttrs != null) {
         Set validAttrs = getPartitionScopedDebugAttributes();
         String[] var2 = debugAttrs;
         int var3 = debugAttrs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String debugAttr = var2[var4];
            if (!validAttrs.contains(debugAttr)) {
               throw new IllegalArgumentException("Invalid PartitionDebugAttribute:" + debugAttr + " name");
            }
         }
      }

   }

   public static Set getPartitionScopedDebugAttributes() {
      Set debugAttrs = new TreeSet();
      BeanInfo beanInfo = getServerDebugBeanInfo();
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      PropertyDescriptor[] var3 = propertyDescriptors;
      int var4 = propertyDescriptors.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyDescriptor pd = var3[var5];
         String name = pd.getName();
         if (!EXCLUDED_ATTRS.contains(name)) {
            Class type = pd.getPropertyType();
            boolean booleanType = type.isAssignableFrom(Boolean.TYPE);
            if (booleanType) {
               debugAttrs.add(name);
            }
         }
      }

      return debugAttrs;
   }

   private static BeanInfo getServerDebugBeanInfo() {
      BeanInfoAccess beanInfoAccess = Kernel.isServer() ? ManagementService.getBeanInfoAccess() : ManagementServiceClient.getBeanInfoAccess();
      String version = VersionInfo.theOne().getReleaseVersion();
      BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInterface(ServerDebugMBean.class.getName(), true, version);
      return beanInfo;
   }
}
