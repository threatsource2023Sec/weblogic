package weblogic.management.runtime;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;

public class ReplicationRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ReplicationRuntimeMBean.class;

   public ReplicationRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ReplicationRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.runtime.ReplicationRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "weblogic.management.runtime");
      String description = (new String("Common interface for different replication runtime mbeans within WebLogic Server ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ReplicationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DetailedSecondariesDistribution")) {
         getterName = "getDetailedSecondariesDistribution";
         setterName = null;
         currentResult = new PropertyDescriptor("DetailedSecondariesDistribution", ReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DetailedSecondariesDistribution", currentResult);
         currentResult.setValue("description", "<p>Provides the names of the remote servers (such as myserver) for which the local server is hosting secondary objects. The name is appended with a number to indicate the number of secondaries hosted on behalf of that server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryCount")) {
         getterName = "getPrimaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrimaryCount", ReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrimaryCount", currentResult);
         currentResult.setValue("description", "<p>Provides the number of object that the local server hosts as primaries.</p>  <p>Answer the number of object that the local server hosts as primaries.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondaryCount")) {
         getterName = "getSecondaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryCount", ReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryCount", currentResult);
         currentResult.setValue("description", "<p>Answer the number of object that the local server hosts as secondaries.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondaryServerDetails")) {
         getterName = "getSecondaryServerDetails";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryServerDetails", ReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryServerDetails", currentResult);
         currentResult.setValue("description", " ");
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
