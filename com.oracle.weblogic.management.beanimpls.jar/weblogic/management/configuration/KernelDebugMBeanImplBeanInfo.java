package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class KernelDebugMBeanImplBeanInfo extends DebugMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KernelDebugMBean.class;

   public KernelDebugMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KernelDebugMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.KernelDebugMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Defines the debug attributes that are common to WebLogic Server and Client.</p> <p>While all attributes will be supported in adherence with the standard WebLogic Server deprecation policies, the resultant debug content is free to change in both form and content across releases.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.KernelDebugMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DebugAbbreviation")) {
         getterName = "getDebugAbbreviation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugAbbreviation";
         }

         currentResult = new PropertyDescriptor("DebugAbbreviation", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugAbbreviation", currentResult);
         currentResult.setValue("description", "<p>Debug abbreviations over JVM to JVM connections</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugConnection")) {
         getterName = "getDebugConnection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugConnection";
         }

         currentResult = new PropertyDescriptor("DebugConnection", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugConnection", currentResult);
         currentResult.setValue("description", "<p>Debug JVM to JVM connections</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugDGCEnrollment")) {
         getterName = "getDebugDGCEnrollment";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugDGCEnrollment";
         }

         currentResult = new PropertyDescriptor("DebugDGCEnrollment", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugDGCEnrollment", currentResult);
         currentResult.setValue("description", "<p>Debug each DGC enrollment.</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugFailOver")) {
         getterName = "getDebugFailOver";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugFailOver";
         }

         currentResult = new PropertyDescriptor("DebugFailOver", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugFailOver", currentResult);
         currentResult.setValue("description", "<p>Debug stub-level fail-over processing</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOP")) {
         getterName = "getDebugIIOP";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOP";
         }

         currentResult = new PropertyDescriptor("DebugIIOP", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOP", currentResult);
         currentResult.setValue("description", "<p>Debug IIOP processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOPConnection")) {
         getterName = "getDebugIIOPConnection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOPConnection";
         }

         currentResult = new PropertyDescriptor("DebugIIOPConnection", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOPConnection", currentResult);
         currentResult.setValue("description", "<p>Debug IIOP connection management processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOPMarshal")) {
         getterName = "getDebugIIOPMarshal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOPMarshal";
         }

         currentResult = new PropertyDescriptor("DebugIIOPMarshal", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOPMarshal", currentResult);
         currentResult.setValue("description", "<p>Debug buffer-level IIOP processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOPOTS")) {
         getterName = "getDebugIIOPOTS";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOPOTS";
         }

         currentResult = new PropertyDescriptor("DebugIIOPOTS", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOPOTS", currentResult);
         currentResult.setValue("description", "<p>Debug IIOP Object Transaction Service (OTS) processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOPReplacer")) {
         getterName = "getDebugIIOPReplacer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOPReplacer";
         }

         currentResult = new PropertyDescriptor("DebugIIOPReplacer", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOPReplacer", currentResult);
         currentResult.setValue("description", "<p>Debug IIOP object replacement processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOPSecurity")) {
         getterName = "getDebugIIOPSecurity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOPSecurity";
         }

         currentResult = new PropertyDescriptor("DebugIIOPSecurity", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOPSecurity", currentResult);
         currentResult.setValue("description", "<p>Debug IIOP security processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOPStartup")) {
         getterName = "getDebugIIOPStartup";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOPStartup";
         }

         currentResult = new PropertyDescriptor("DebugIIOPStartup", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOPStartup", currentResult);
         currentResult.setValue("description", "<p>Debug IIOP startup processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugIIOPTransport")) {
         getterName = "getDebugIIOPTransport";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugIIOPTransport";
         }

         currentResult = new PropertyDescriptor("DebugIIOPTransport", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugIIOPTransport", currentResult);
         currentResult.setValue("description", "<p>Debug IIOP message processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugLoadBalancing")) {
         getterName = "getDebugLoadBalancing";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugLoadBalancing";
         }

         currentResult = new PropertyDescriptor("DebugLoadBalancing", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugLoadBalancing", currentResult);
         currentResult.setValue("description", "<p>Debug stub-level load-balancing processing</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DebugLocalRemoteJVM")) {
         getterName = "getDebugLocalRemoteJVM";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugLocalRemoteJVM";
         }

         currentResult = new PropertyDescriptor("DebugLocalRemoteJVM", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugLocalRemoteJVM", currentResult);
         currentResult.setValue("description", "<p>Debug how is LocalRemoteJVM working.</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DebugMessaging")) {
         getterName = "getDebugMessaging";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugMessaging";
         }

         currentResult = new PropertyDescriptor("DebugMessaging", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugMessaging", currentResult);
         currentResult.setValue("description", "<p>Debug messages sent over JVM to JVM connections</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugMuxer")) {
         getterName = "getDebugMuxer";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugMuxer";
         }

         currentResult = new PropertyDescriptor("DebugMuxer", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugMuxer", currentResult);
         currentResult.setValue("description", "<p>Debug Muxer processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugMuxerConnection")) {
         getterName = "getDebugMuxerConnection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugMuxerConnection";
         }

         currentResult = new PropertyDescriptor("DebugMuxerConnection", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugMuxerConnection", currentResult);
         currentResult.setValue("description", "<p>Debug Muxer connection processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugMuxerDetail")) {
         getterName = "getDebugMuxerDetail";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugMuxerDetail";
         }

         currentResult = new PropertyDescriptor("DebugMuxerDetail", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugMuxerDetail", currentResult);
         currentResult.setValue("description", "<p>Detailed debug for Muxer processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugMuxerException")) {
         getterName = "getDebugMuxerException";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugMuxerException";
         }

         currentResult = new PropertyDescriptor("DebugMuxerException", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugMuxerException", currentResult);
         currentResult.setValue("description", "<p>Debug Muxer exception processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugMuxerTimeout")) {
         getterName = "getDebugMuxerTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugMuxerTimeout";
         }

         currentResult = new PropertyDescriptor("DebugMuxerTimeout", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugMuxerTimeout", currentResult);
         currentResult.setValue("description", "<p>Debug Muxer timeout processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("DebugParameters")) {
         getterName = "getDebugParameters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugParameters";
         }

         currentResult = new PropertyDescriptor("DebugParameters", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugParameters", currentResult);
         currentResult.setValue("description", "<p>Parameters for debug logging defined as name value pairs. The parameters are prefixed with the name of the debug flag. The parameter names supported for a debug flag is specific to the implementation of the debug output.</p> ");
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (!descriptors.containsKey("DebugRC4")) {
         getterName = "getDebugRC4";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugRC4";
         }

         currentResult = new PropertyDescriptor("DebugRC4", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugRC4", currentResult);
         currentResult.setValue("description", "<p>Debug RC4 cipher processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugRSA")) {
         getterName = "getDebugRSA";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugRSA";
         }

         currentResult = new PropertyDescriptor("DebugRSA", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugRSA", currentResult);
         currentResult.setValue("description", "<p>Debug RSA security processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugRouting")) {
         getterName = "getDebugRouting";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugRouting";
         }

         currentResult = new PropertyDescriptor("DebugRouting", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugRouting", currentResult);
         currentResult.setValue("description", "<p>Debug routing of messages over JVM to JVM connections</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugSSL")) {
         getterName = "getDebugSSL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugSSL";
         }

         currentResult = new PropertyDescriptor("DebugSSL", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugSSL", currentResult);
         currentResult.setValue("description", "<p>Debug SSL processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugSelfTuning")) {
         getterName = "getDebugSelfTuning";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugSelfTuning";
         }

         currentResult = new PropertyDescriptor("DebugSelfTuning", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugSelfTuning", currentResult);
         currentResult.setValue("description", "<p>Debug WorkManager self-tuning processing</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DebugWorkContext")) {
         getterName = "getDebugWorkContext";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugWorkContext";
         }

         currentResult = new PropertyDescriptor("DebugWorkContext", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("DebugWorkContext", currentResult);
         currentResult.setValue("description", "<p>Debug Work context (out of band data propagation)</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForceGCEachDGCPeriod")) {
         getterName = "getForceGCEachDGCPeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForceGCEachDGCPeriod";
         }

         currentResult = new PropertyDescriptor("ForceGCEachDGCPeriod", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("ForceGCEachDGCPeriod", currentResult);
         currentResult.setValue("description", "<p>Force VM garbage collection on each DGC interval</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogDGCStatistics")) {
         getterName = "getLogDGCStatistics";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogDGCStatistics";
         }

         currentResult = new PropertyDescriptor("LogDGCStatistics", KernelDebugMBean.class, getterName, setterName);
         descriptors.put("LogDGCStatistics", currentResult);
         currentResult.setValue("description", "<p>Debug DGC with Statistics</p> ");
         currentResult.setValue("secureValue", new Boolean(false));
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
