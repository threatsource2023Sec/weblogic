package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.jmx.AttributeSpec;
import com.bea.adaptive.harvester.jmx.BaseHarvesterImpl;
import com.bea.adaptive.harvester.jmx.MetricTypeInfo;
import com.bea.adaptive.harvester.jmx.RegistrationManager;
import com.bea.adaptive.mbean.typing.MBeanTypeUtil;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.Service;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class BeanTreeRegistrationManager extends RegistrationManager implements MBeanTypeUtil.RegHandler {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticsHarvesterTreeBeanPlugin");
   private static final String WLS_RUNTIME_MBEAN_REGEX = "weblogic\\.management\\.runtime\\.(.*)MBean";
   private static final Pattern WLS_RUNTIME_MBEAN_PATTERN = Pattern.compile("weblogic\\.management\\.runtime\\.(.*)MBean");
   private BeanTreeRegistrationHandler registrationHandler;

   static BeanInfo getBeanInfoForInterface(String typeName) {
      return TreeBeanHarvestableDataProviderHelper.getBeanInfo(typeName);
   }

   public BeanTreeRegistrationManager(BaseHarvesterImpl harvester) throws Exception {
      super(harvester.getDebugLogger(), harvester, new BeanTreeTypeResolver(getUnharvestableDescriptorTag(), getUnharvestableAttributeDescriptors()));
      this.initKnownTypes();
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Adding registration listener");
      }

      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      this.registrationHandler = new BeanTreeRegistrationHandler("BeanTreeRegistrationHandler", this, (String[])null, HarvesterExecutor.getInstance());
      runtimeAccess.initiateRegistrationHandler(this.registrationHandler);
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Switching to async registration event processing");
      }

   }

   public String getDescriptionForType(String typeName) {
      BeanInfo bi = getBeanInfoForInterface(typeName);
      if (bi != null) {
         BeanDescriptor bd = bi.getBeanDescriptor();
         if (bd != null) {
            return bd.getDisplayName();
         }
      }

      return mtf_base.getUnknownLabel();
   }

   public String[][] getKnownHarvestableTypes(String typeNameRegex) throws IOException {
      String[][] result = super.getKnownHarvestableTypes(typeNameRegex);
      if (result == null || result.length == 0) {
         Matcher m = WLS_RUNTIME_MBEAN_PATTERN.matcher(typeNameRegex);
         if (m.matches()) {
            result = new String[][]{{typeNameRegex, ""}};
         }
      }

      return result;
   }

   public void shutdown(boolean forceShutdown) {
      this.registrationHandler.halt();
      ManagementService.getRuntimeAccess(KERNEL_ID).removeRegistrationHandler(this.registrationHandler);
   }

   Method getReadMethod(String typeName, String attrName) {
      Method m = null;
      MetricTypeInfo typeInfo = this.lookupTypeInfo(typeName);
      if (typeInfo != null) {
         AttributeSpec spec = typeInfo.lookupAttributeSpec(attrName);
         m = spec.getReadMethod();
      }

      return m;
   }

   private void cacheClassNames(String[] types) {
      for(int i = 0; i < types.length; ++i) {
         String type = types[i];

         try {
            this.addKnownTypeToCache(type);
         } catch (Throwable var5) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Error adding type to cache: " + type, var5);
            }
         }
      }

   }

   private void initKnownTypes() throws Exception {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Initializing known WLS RuntimeMBean types");
      }

      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      String[] runtimeTypes = beanInfoAccess.getSubtypes(RuntimeMBean.class.getName());
      this.cacheClassNames(runtimeTypes);
      String[] serviceTypes = beanInfoAccess.getSubtypes(Service.class.getName());
      this.cacheClassNames(serviceTypes);
   }
}
