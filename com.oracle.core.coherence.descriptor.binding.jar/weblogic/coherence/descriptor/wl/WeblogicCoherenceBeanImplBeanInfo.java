package weblogic.coherence.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WeblogicCoherenceBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WeblogicCoherenceBean.class;

   public WeblogicCoherenceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicCoherenceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.WeblogicCoherenceBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String("The top of the Coherence Cluster System Resource bean tree. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.WeblogicCoherenceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CoherenceAddressProviders")) {
         getterName = "getCoherenceAddressProviders";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceAddressProviders", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("CoherenceAddressProviders", currentResult);
         currentResult.setValue("description", "<p>The Coherence Address Provider parameters that have been defined for this WebLogicCoherenceBean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceClusterParams")) {
         getterName = "getCoherenceClusterParams";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterParams", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterParams", currentResult);
         currentResult.setValue("description", "<p>The Coherence cluster parameters that have been defined for this WebLogicCoherenceBean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceFederationParams")) {
         getterName = "getCoherenceFederationParams";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceFederationParams", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("CoherenceFederationParams", currentResult);
         currentResult.setValue("description", "<p>The Coherence cluster parameters related to Federation settings.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("CoherenceLoggingParams")) {
         getterName = "getCoherenceLoggingParams";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceLoggingParams", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("CoherenceLoggingParams", currentResult);
         currentResult.setValue("description", "<p>The Coherence logging parameters that have been defined for this WebLogicCoherenceBean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherencePersistenceParams")) {
         getterName = "getCoherencePersistenceParams";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherencePersistenceParams", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("CoherencePersistenceParams", currentResult);
         currentResult.setValue("description", "<p>The Coherence cluster parameters related to Default Persistence settings.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("CustomClusterConfigurationFileLastUpdatedTimestamp")) {
         getterName = "getCustomClusterConfigurationFileLastUpdatedTimestamp";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomClusterConfigurationFileLastUpdatedTimestamp", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("CustomClusterConfigurationFileLastUpdatedTimestamp", currentResult);
         currentResult.setValue("description", "<p>The timestamp at which the custom cluster configuration file was last updated. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomClusterConfigurationFileName")) {
         getterName = "getCustomClusterConfigurationFileName";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomClusterConfigurationFileName", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("CustomClusterConfigurationFileName", currentResult);
         currentResult.setValue("description", "<p>The name of a custom Coherence operational configuration override file. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "A unique name that identifies this system resource in the WebLogic domain. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WeblogicCoherenceBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "<p>The version of this file.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
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
