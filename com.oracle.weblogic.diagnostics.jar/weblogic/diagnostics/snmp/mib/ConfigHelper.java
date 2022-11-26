package weblogic.diagnostics.snmp.mib;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;

public class ConfigHelper {
   private static final String ATTR_CHANGE_RUNTIME_MBEANS = "DeploymentTaskRuntime,JTARuntime,JVMRuntime,ServerLifeCycleRuntime,ServerRuntime";
   /** @deprecated */
   @Deprecated
   private static final String ATTRCHANGERUNTIMEMBEANS = "DeploymentTaskRuntime,JTARuntime,JVMRuntime,ServerLifeCycleRuntime,ServerRuntime";
   private static Set allMBeanTypes = null;
   private static Set attrChangeMBeanTypes = null;
   private static boolean initialized = false;

   public static Set getMonitorMBeanTypes() throws WLSMibMetadataException {
      ensureInitialized();
      return allMBeanTypes;
   }

   public static Set getAttributeChangeMBeanTypes() throws WLSMibMetadataException {
      ensureInitialized();
      return attrChangeMBeanTypes;
   }

   private static void ensureInitialized() throws WLSMibMetadataException {
      if (!initialized) {
         WLSMibMetadata mibMetadata = WLSMibMetadata.loadResource();
         Set wlsTypes = mibMetadata.wlsTypeNameToSNMPTableName.keySet();
         allMBeanTypes = new HashSet();
         attrChangeMBeanTypes = new HashSet();

         String typeName;
         for(Iterator var2 = wlsTypes.iterator(); var2.hasNext(); allMBeanTypes.add(typeName)) {
            Object wlsType = var2.next();
            typeName = wlsType.toString();
            typeName = typeName.replaceAll("[a-z]*\\.", "");
            typeName = typeName.substring(0, typeName.lastIndexOf("MBean"));
            if (!typeName.endsWith("Runtime")) {
               attrChangeMBeanTypes.add(typeName);
            }
         }

         String[] attrChangeRuntimeTypeNames = "DeploymentTaskRuntime,JTARuntime,JVMRuntime,ServerLifeCycleRuntime,ServerRuntime".split(",");
         attrChangeMBeanTypes.addAll(Arrays.asList(attrChangeRuntimeTypeNames));
         initialized = true;
      }

   }

   public static void validateMonitorMBeanType(String value) throws IllegalArgumentException {
   }

   public static boolean isValidRuntimeMBeanType(String value) {
      return false;
   }

   public static boolean isValidConfigMBeanType(String value) {
      return false;
   }

   public static void validateAttributeChangeMBeanType(String value) throws IllegalArgumentException {
   }

   public static void main(String[] args) throws WLSMibMetadataException {
      DebugLogger.println("AttributeChangeMBeanTypes=" + getAttributeChangeMBeanTypes());
      DebugLogger.println("#####");
      DebugLogger.println("MonitorMBeanTypes=" + getMonitorMBeanTypes());
   }
}
