package weblogic.cacheprovider.coherence.management;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.CoherenceClusterRuntimeMBean;

public class CoherenceClusterRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceClusterRuntimeMBean.class;

   public CoherenceClusterRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceClusterRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cacheprovider.coherence.management.CoherenceClusterRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.cacheprovider.coherence.management");
      String description = (new String("Coherence cluster run-time information. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.CoherenceClusterRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ClusterName")) {
         getterName = "getClusterName";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterName", CoherenceClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterName", currentResult);
         currentResult.setValue("description", "<p>The name of the Coherence cluster. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterSize")) {
         getterName = "getClusterSize";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterSize", CoherenceClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClusterSize", currentResult);
         currentResult.setValue("description", "<p>The size of the Coherence cluster. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LicenseMode")) {
         getterName = "getLicenseMode";
         setterName = null;
         currentResult = new PropertyDescriptor("LicenseMode", CoherenceClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LicenseMode", currentResult);
         currentResult.setValue("description", "<p>The license mode for the Coherence cluster. Possible values are Evaluation, Development, or Production.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Members")) {
         getterName = "getMembers";
         setterName = null;
         currentResult = new PropertyDescriptor("Members", CoherenceClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Members", currentResult);
         currentResult.setValue("description", "<p>Identifiers for the available Coherence cluster members. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("Version", CoherenceClusterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "<p>The Coherence cluster version. </p> ");
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
