package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceMemberConfigMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceMemberConfigMBean.class;

   public CoherenceMemberConfigMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceMemberConfigMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CoherenceMemberConfigMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This class represents Coherence Operational Configuration for a WLS Server that is part of a Coherence Cluster.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CoherenceMemberConfigMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("RackName")) {
         getterName = "getRackName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRackName";
         }

         currentResult = new PropertyDescriptor("RackName", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("RackName", currentResult);
         currentResult.setValue("description", "<p>Specifies the location within a geographic site where this member is hosted.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RoleName")) {
         getterName = "getRoleName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRoleName";
         }

         currentResult = new PropertyDescriptor("RoleName", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("RoleName", currentResult);
         currentResult.setValue("description", "<p>Specifies a name used to logically group similar members.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SiteName")) {
         getterName = "getSiteName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSiteName";
         }

         currentResult = new PropertyDescriptor("SiteName", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("SiteName", currentResult);
         currentResult.setValue("description", "<p>Specifies the name of the geographic site where this member is hosted.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnicastListenAddress")) {
         getterName = "getUnicastListenAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastListenAddress";
         }

         currentResult = new PropertyDescriptor("UnicastListenAddress", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("UnicastListenAddress", currentResult);
         currentResult.setValue("description", "<p>The IP address for the Coherence unicast listener.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnicastListenPort")) {
         getterName = "getUnicastListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastListenPort";
         }

         currentResult = new PropertyDescriptor("UnicastListenPort", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("UnicastListenPort", currentResult);
         currentResult.setValue("description", "<p>The port for the Coherence unicast listener. A value of 0 indicates that the unicast listen port value will be assigned automatically to an ephemeral port.</p> ");
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("UnicastPortAutoAdjustAttempts")) {
         getterName = "getUnicastPortAutoAdjustAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastPortAutoAdjustAttempts";
         }

         currentResult = new PropertyDescriptor("UnicastPortAutoAdjustAttempts", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("UnicastPortAutoAdjustAttempts", currentResult);
         currentResult.setValue("description", "<p> Get the upper bound of the unicast port. A positive value indicates the unicast port is automatically incremented if the specified port cannot be bound because it is already in use. </p> ");
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceWebFederatedStorageEnabled")) {
         getterName = "isCoherenceWebFederatedStorageEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherenceWebFederatedStorageEnabled";
         }

         currentResult = new PropertyDescriptor("CoherenceWebFederatedStorageEnabled", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("CoherenceWebFederatedStorageEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether Federated Cache Storage is enabled for the Coherence Web cluster member </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("CoherenceWebLocalStorageEnabled")) {
         getterName = "isCoherenceWebLocalStorageEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherenceWebLocalStorageEnabled";
         }

         currentResult = new PropertyDescriptor("CoherenceWebLocalStorageEnabled", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("CoherenceWebLocalStorageEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether Local Storage is enabled for the Coherence Web cluster member </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalStorageEnabled")) {
         getterName = "isLocalStorageEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalStorageEnabled";
         }

         currentResult = new PropertyDescriptor("LocalStorageEnabled", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("LocalStorageEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not this member will contribute storage to the Coherence cluster i.e. maintain partitions. This attribute is used only when the WebLogic Server is not part of a WLS Cluster.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagementProxy")) {
         getterName = "isManagementProxy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagementProxy";
         }

         currentResult = new PropertyDescriptor("ManagementProxy", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("ManagementProxy", currentResult);
         currentResult.setValue("description", "<p>Specifies whether or not this server can act as a Coherence Management node.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "12.2.1.0.0 All Coherence Members can become management nodes; this has been done for high-availability ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnicastPortAutoAdjust")) {
         getterName = "isUnicastPortAutoAdjust";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastPortAutoAdjust";
         }

         currentResult = new PropertyDescriptor("UnicastPortAutoAdjust", CoherenceMemberConfigMBean.class, getterName, setterName);
         descriptors.put("UnicastPortAutoAdjust", currentResult);
         currentResult.setValue("description", "<p> Specifies whether the unicast port will be automatically incremented if the port cannot be bound because it is already in use. </p> ");
         currentResult.setValue("deprecated", "12.2.1.0.0 Use getUnicastPortAutoAdjustAttempts ");
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
