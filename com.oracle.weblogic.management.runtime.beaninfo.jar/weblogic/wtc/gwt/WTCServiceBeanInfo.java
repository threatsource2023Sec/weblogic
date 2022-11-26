package weblogic.wtc.gwt;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WTCRuntimeMBean;

public class WTCServiceBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCRuntimeMBean.class;

   public WTCServiceBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCServiceBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wtc.gwt.WTCService");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.wtc.gwt");
      String description = (new String("This class is used to 1. query, stop, and start WTC connections. 2. query, suspend, and resume WTC imported and exported services. 3. retrieve statistics MBean relates to this WTC server. 4. query status of WTC server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WTCRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ServiceStatus")) {
         getterName = "getServiceStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceStatus", WTCRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServiceStatus", currentResult);
         currentResult.setValue("description", "Returns status of all the Import or Export services/resources configured for the targeted WTC server with the specified service name.  This service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for DServiceInfo");
      }

      if (!descriptors.containsKey("WTCServerStartTime")) {
         getterName = "getWTCServerStartTime";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCServerStartTime", WTCRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WTCServerStartTime", currentResult);
         currentResult.setValue("description", "Returns the timestamp when WTC server starts. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCServerStatus")) {
         getterName = "getWTCServerStatus";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCServerStatus", WTCRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WTCServerStatus", currentResult);
         currentResult.setValue("description", "Returns the status of WTC server itself. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCStatisticsRuntimeMBean")) {
         getterName = "getWTCStatisticsRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCStatisticsRuntimeMBean", WTCRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WTCStatisticsRuntimeMBean", currentResult);
         currentResult.setValue("description", "Returns the WTCStatisticsRuntimeMBean associates with this WTC server. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WTCRuntimeMBean.class.getMethod("startConnection", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("LDomAccessPointId", "The local domain access point id. "), createParameterDescriptor("RDomAccessPointId", "The remote domain access point id. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Attempt to start a connection between the specified local and remote domain access points. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("startConnection", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("LDomAccessPointId", "The local domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Attempt to start connections between the specified local domain access point and all remote end points defined for the given local domain domain access point. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("stopConnection", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("LdomAccessPointId", "The local domain access point id. "), createParameterDescriptor("RDomAccessPointId", "The remote domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Attempt to stop the connection between the specified local and remote domain access points. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("stopConnection", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("LdomAccessPointId", "The local domain access point id. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Attempt to stop all remote connections configured for the given local access point id. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("listConnectionsConfigured");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for DSessConnInfo");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Provide a list of the connections configured for this WTCService. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for DSessConnInfo");
      }

      mth = WTCRuntimeMBean.class.getMethod("suspendService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if the service name          is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Suspend all the Import and Export services with the specified service name.  This service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("suspendService", String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. "), createParameterDescriptor("isImport", "The type of service indicate whether it is import or export. If true, then it is imported service, if false it is targeted for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if the service name          is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Suspend all the Import or Export services with the specified service name.  This service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("suspendService", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if the service name          is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Suspend all the Import and Export services with the specified service name configured for the specified local access point. The service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("suspendService", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. "), createParameterDescriptor("isImport", "The type of service indicate whether it is import or export. If true, then it is imported service, if false it is targeted for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if the service name          is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Suspend all the Import or Export services with the specified service name configured for the specified local access point. The service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("suspendService", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("remoteAccessPointList", "The comma separated remote access point names. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if the service name          is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Suspend a specific Import service with the specified service name configured for the specified local access point and remote access point list. The service name is the resource name of the WTCImport and WTCExport. The remote access point list is a comma separated list; for instance, \"TDOM1,TDOM2\". ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("resumeService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if there is no matching           service name found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Resume all the Import and Export services with the specified service name.  This service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("resumeService", String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. "), createParameterDescriptor("isImport", "The type of service indicate whether it is import or export. If true, then it is imported service, if false it is targeted for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if there is no matching           service name found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Resume all the Import or Export services with the specified service name.  This service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("resumeService", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if there is no matching          service name found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Resume all the Import and Export services with the specified service name configured for the specified local access point. The service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("resumeService", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. "), createParameterDescriptor("isImport", "The type of service indicate whether it is import or export. If true, then it is imported service, if false it is targeted for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if there is no matching          service name found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Resume all the Import or Export services with the specified service name configured for the specified local access point. The service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("resumeService", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("remoteAccessPointList", "The comma separated remote access point names. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("TPException throws TPException.TPENOENT if the service name          is not found.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Resume a specific Import service with the specified service name configured for the specified local access point and remote access point list. The service name is the resource name of the WTCImport and WTCExport. The remote access point list is a comma separated list; for instance, \"TDOM1,TDOM2\". ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("getServiceStatus", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns status of the Import and Export service/resource configured for the targeted WTC server with the specified service name.  This service name is the resource name of the WTCImport and WTCExport.  As long as one of the directions is available the returned satatus will be available. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("getServiceStatus", String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. "), createParameterDescriptor("isImport", "The type of service indicate whether it is import or export. If true, then it is imported service, if false it is targeted for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns status of the Import or Export service/resource configured for the targeted WTC server with the specified service name.  This service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("getServiceStatus", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns status of the imported and exported service of the specified service name and provided by the specified local access point. The service name is the resource name of the WTCImport and WTCExport. As long as one of the directions is available then the returned status will be available. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("getServiceStatus", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. "), createParameterDescriptor("isImport", "The type of service indicate whether it is import or export. If true, then it is imported service, if false it is targeted for exported service. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns status of the imported or exported service of the specified service name and provided by the specified local access point. The service name is the resource name of the WTCImport and WTCExport. ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCRuntimeMBean.class.getMethod("getServiceStatus", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("localAccessPoint", "The local access point name. "), createParameterDescriptor("remoteAccessPointList", "The comma separated remote access point names. "), createParameterDescriptor("svcName", "The resource name of imported or exported service/resource name. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns status of a specific imported service provided by the specified local access point and remote access point list. The service name is the resource name of the WTCImport and WTCExport. The remote access point list is a comma separated list; for instance, \"TDOM1,TDOM2\". ");
         currentResult.setValue("role", "operation");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
