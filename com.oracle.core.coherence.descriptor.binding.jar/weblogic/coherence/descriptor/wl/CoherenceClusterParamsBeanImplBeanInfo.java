package weblogic.coherence.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceClusterParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceClusterParamsBean.class;

   public CoherenceClusterParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceClusterParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.CoherenceClusterParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.CoherenceClusterParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClusterListenPort")) {
         getterName = "getClusterListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterListenPort";
         }

         currentResult = new PropertyDescriptor("ClusterListenPort", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("ClusterListenPort", currentResult);
         currentResult.setValue("description", "<p>The cluster listen port used by Coherence; Coherence cluster members use this port to discover and join, or create the cluster. If unspecified, the Coherence cluster multicast listen port will be used.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.CoherenceMemberConfigMBean#getClusterListenPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ClusteringMode")) {
         getterName = "getClusteringMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusteringMode";
         }

         currentResult = new PropertyDescriptor("ClusteringMode", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("ClusteringMode", currentResult);
         currentResult.setValue("description", "<p>Specifies a clustering mode of either Unicast or Multicast. If multicast is undesirable or unavailable in an environment, or when an environment is not properly configured to support multicast, then setting up the Well Known Addresses (WKA) feature is required. All cluster multicast communication is disabled if WKA is enabled.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, "unicast");
         currentResult.setValue("legalValues", new Object[]{"multicast", "unicast"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceCaches")) {
         getterName = "getCoherenceCaches";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceCaches", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("CoherenceCaches", currentResult);
         currentResult.setValue("description", "<p>An array of CoherenceCache beans, each of which represents a Coherence cache.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCoherenceCache");
         currentResult.setValue("creator", "createCoherenceCache");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceClusterWellKnownAddresses")) {
         getterName = "getCoherenceClusterWellKnownAddresses";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterWellKnownAddresses", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterWellKnownAddresses", currentResult);
         currentResult.setValue("description", "<p>The CoherenceClusterWellKnownAddressMBeans that have been defined for this CoherenceClusterBean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceIdentityAsserter")) {
         getterName = "getCoherenceIdentityAsserter";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceIdentityAsserter", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("CoherenceIdentityAsserter", currentResult);
         currentResult.setValue("description", "<p>Gets the Coherence IdentityAsserter.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceKeystoreParams")) {
         getterName = "getCoherenceKeystoreParams";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceKeystoreParams", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("CoherenceKeystoreParams", currentResult);
         currentResult.setValue("description", "<p>Gets the Keystore params for the Coherence Identity.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceServices")) {
         getterName = "getCoherenceServices";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceServices", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("CoherenceServices", currentResult);
         currentResult.setValue("description", "<p>An array of CoherenceService beans, each of which represents a Coherence Service.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCoherenceService");
         currentResult.setValue("creator", "createCoherenceService");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastListenAddress")) {
         getterName = "getMulticastListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastListenAddress";
         }

         currentResult = new PropertyDescriptor("MulticastListenAddress", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("MulticastListenAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address for the cluster multicast listener.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastListenPort")) {
         getterName = "getMulticastListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastListenPort";
         }

         currentResult = new PropertyDescriptor("MulticastListenPort", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("MulticastListenPort", currentResult);
         currentResult.setValue("description", "<p>The port for the cluster multicast listener.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(33387));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.2.1.0.0 Use getClusterListenPort. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToLive")) {
         getterName = "getTimeToLive";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToLive";
         }

         currentResult = new PropertyDescriptor("TimeToLive", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("TimeToLive", currentResult);
         currentResult.setValue("description", "<p>Sets the time-to-live (TTL) setting for the multicast message. The TTL setting designates how far multicast UDP/IP packets can travel on a network. The TTL is expressed in terms of how many hops a packet survives; each network interface, router, and managed switch is considered one hop. The TTL value should be set to the lowest integer value that works. Setting the value too high can use unnecessary bandwidth on other LAN segments and can even cause the operating system or network devices to disable multicast traffic.</p> <p>Typically, setting the TTL value to 1 works on a simple switched backbone. A value of 2 or more may be required on an advanced backbone with intelligent switching. A value of 0 is used for single server clusters that are used for development and testing.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(4));
         currentResult.setValue("legalMax", new Integer(255));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Transport")) {
         getterName = "getTransport";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransport";
         }

         currentResult = new PropertyDescriptor("Transport", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("Transport", currentResult);
         currentResult.setValue("description", "<p>Specify the underlying transport protocol to use for cluster communication. The TMB, SDMB, or IMB are only applicable to Exalogic environment.</p> ");
         setPropertyDescriptorDefault(currentResult, "udp");
         currentResult.setValue("legalValues", new Object[]{"udp", "tcp", "ssl", "tmb", "sdmb", "imb"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnicastListenPort")) {
         getterName = "getUnicastListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastListenPort";
         }

         currentResult = new PropertyDescriptor("UnicastListenPort", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("UnicastListenPort", currentResult);
         currentResult.setValue("description", "<p>The port for the cluster unicast listener.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.CoherenceMemberConfigMBean#getUnicastListenPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "12.2.1.0.0 Use getClusterListenPort ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecurityFrameworkEnabled")) {
         getterName = "isSecurityFrameworkEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecurityFrameworkEnabled";
         }

         currentResult = new PropertyDescriptor("SecurityFrameworkEnabled", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("SecurityFrameworkEnabled", currentResult);
         currentResult.setValue("description", "<p>Check if security framework is enabled</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnicastPortAutoAdjust")) {
         getterName = "isUnicastPortAutoAdjust";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastPortAutoAdjust";
         }

         currentResult = new PropertyDescriptor("UnicastPortAutoAdjust", CoherenceClusterParamsBean.class, getterName, setterName);
         descriptors.put("UnicastPortAutoAdjust", currentResult);
         currentResult.setValue("description", "<p> Specifies whether the unicast port will be automatically incremented if the port cannot be bound because it is already in use. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.CoherenceMemberConfigMBean#getUnicastPortAutoAdjustAttempts")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceClusterParamsBean.class.getMethod("createCoherenceCache", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a new CoherenceCache bean representing the specified cache and adds it to the list of currently existing beans.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceCaches");
      }

      mth = CoherenceClusterParamsBean.class.getMethod("destroyCoherenceCache", CoherenceCacheBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys the bean representing the Coherence cache and removes it from the list of currently existing beans.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceCaches");
      }

      mth = CoherenceClusterParamsBean.class.getMethod("createCoherenceService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a new CoherenceService bean representing the specified Service and adds it to the list of currently existing beans.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceServices");
      }

      mth = CoherenceClusterParamsBean.class.getMethod("destroyCoherenceService", CoherenceServiceBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys the bean representing the Coherence Service and removes it from the list of currently existing beans.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceServices");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceClusterParamsBean.class.getMethod("lookupCoherenceCache", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The bean representing the specified Coherence cache.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherenceCaches");
      }

      mth = CoherenceClusterParamsBean.class.getMethod("lookupCoherenceService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The bean representing the specified Coherence Service.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherenceServices");
      }

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
