package weblogic.diagnostics.descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.utils.codegen.ImplementationFactory;
import weblogic.utils.codegen.RoleInfoImplementationFactory;

public class BeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(29);
   private static final ArrayList roleInfoList;
   private static final BeanInfoFactory SINGLETON;

   public static final ImplementationFactory getInstance() {
      return SINGLETON;
   }

   public String getImplementationClassName(String interfaceName) {
      return (String)interfaceMap.get(interfaceName);
   }

   public String[] getInterfaces() {
      Set keySet = interfaceMap.keySet();
      return (String[])((String[])keySet.toArray(new String[keySet.size()]));
   }

   public String[] getInterfacesWithRoleInfo() {
      return (String[])((String[])roleInfoList.toArray(new String[roleInfoList.size()]));
   }

   public String getRoleInfoImplementationFactoryTimestamp() {
      try {
         InputStream is = this.getClass().getResourceAsStream("BeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFActionBean", "weblogic.diagnostics.descriptor.WLDFActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFArrayPropertyBean", "weblogic.diagnostics.descriptor.WLDFArrayPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFBean", "weblogic.diagnostics.descriptor.WLDFBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFConfigurationPropertiesBean", "weblogic.diagnostics.descriptor.WLDFConfigurationPropertiesBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFConfigurationPropertyBean", "weblogic.diagnostics.descriptor.WLDFConfigurationPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFEncryptedPropertyBean", "weblogic.diagnostics.descriptor.WLDFEncryptedPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFHarvestedTypeBean", "weblogic.diagnostics.descriptor.WLDFHarvestedTypeBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFHarvesterBean", "weblogic.diagnostics.descriptor.WLDFHarvesterBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFHeapDumpActionBean", "weblogic.diagnostics.descriptor.WLDFHeapDumpActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFImageNotificationBean", "weblogic.diagnostics.descriptor.WLDFImageNotificationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFInstrumentationBean", "weblogic.diagnostics.descriptor.WLDFInstrumentationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBean", "weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFJMSNotificationBean", "weblogic.diagnostics.descriptor.WLDFJMSNotificationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFJMXNotificationBean", "weblogic.diagnostics.descriptor.WLDFJMXNotificationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFLogActionBean", "weblogic.diagnostics.descriptor.WLDFLogActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFNotificationBean", "weblogic.diagnostics.descriptor.WLDFNotificationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFPropertyBean", "weblogic.diagnostics.descriptor.WLDFPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFRESTNotificationBean", "weblogic.diagnostics.descriptor.WLDFRESTNotificationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFResourceBean", "weblogic.diagnostics.descriptor.WLDFResourceBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFSMTPNotificationBean", "weblogic.diagnostics.descriptor.WLDFSMTPNotificationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFSNMPNotificationBean", "weblogic.diagnostics.descriptor.WLDFSNMPNotificationBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFScaleDownActionBean", "weblogic.diagnostics.descriptor.WLDFScaleDownActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFScaleUpActionBean", "weblogic.diagnostics.descriptor.WLDFScaleUpActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFScalingActionBean", "weblogic.diagnostics.descriptor.WLDFScalingActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFScheduleBean", "weblogic.diagnostics.descriptor.WLDFScheduleBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFScriptActionBean", "weblogic.diagnostics.descriptor.WLDFScriptActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFThreadDumpActionBean", "weblogic.diagnostics.descriptor.WLDFThreadDumpActionBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFWatchBean", "weblogic.diagnostics.descriptor.WLDFWatchBeanImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.descriptor.WLDFWatchNotificationBean", "weblogic.diagnostics.descriptor.WLDFWatchNotificationBeanImplBeanInfo");
      roleInfoList = new ArrayList(28);
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFArrayPropertyBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFConfigurationPropertiesBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFConfigurationPropertyBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFEncryptedPropertyBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFHarvestedTypeBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFHarvesterBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFHeapDumpActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFImageNotificationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFInstrumentationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFJMSNotificationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFJMXNotificationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFLogActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFNotificationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFPropertyBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFRESTNotificationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFResourceBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFSMTPNotificationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFSNMPNotificationBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFScaleDownActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFScaleUpActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFScalingActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFScheduleBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFScriptActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFThreadDumpActionBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFWatchBean");
      roleInfoList.add("weblogic.diagnostics.descriptor.WLDFWatchNotificationBean");
      SINGLETON = new BeanInfoFactory();
   }
}
