package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DistributedTopicBeanImplBeanInfo extends DistributedDestinationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DistributedTopicBean.class;

   public DistributedTopicBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DistributedTopicBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DistributedTopicBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("A distributed queue is one that can be load-balanced across a cluster ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DistributedTopicBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("DistributedTopicMembers")) {
         String getterName = "getDistributedTopicMembers";
         String setterName = null;
         currentResult = new PropertyDescriptor("DistributedTopicMembers", DistributedTopicBean.class, getterName, (String)setterName);
         descriptors.put("DistributedTopicMembers", currentResult);
         currentResult.setValue("description", "Gets array of all distributed-topic-member elements <p> Distributed topic members are configuration information about the particular specific topics that make up the aggregate distributed topic. The name of the specific topic and any configuration information about that topic can be configured with a distributed topic member bean </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDistributedTopicMember");
         currentResult.setValue("creator", "createDistributedTopicMember");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DistributedTopicBean.class.getMethod("createDistributedTopicMember", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed topic member bean to add to this distributed topic ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a distributed topic member and adds it to this distributed topic <p> Distributed topic members are configuration information about the particular specific topics that make up the aggregate distributed topic. The name of the specific topic and any configuration information about that topic can be configured with a distributed topic member bean </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedTopicMembers");
      }

      mth = DistributedTopicBean.class.getMethod("destroyDistributedTopicMember", DistributedDestinationMemberBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("distributedDestinationMember", "The particular distributed topic member to remove from this distributed topic ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a distributed topic member from this distributed topic <p> Distributed topic members are configuration information about the particular specific topics that make up the aggregate distributed topic. The name of the specific topic and any configuration information about that topic can be configured with a distributed topic member bean </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DistributedTopicMembers");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DistributedTopicBean.class.getMethod("lookupDistributedTopicMember", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the distributed topic member to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a distributed topic member bean with the given name <p> Distributed topic members are configuration information about the particular specific topics that make up the aggregate distributed topic. The name of the specific topic and any configuration information about that topic can be configured with a distributed topic member bean </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "DistributedTopicMembers");
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
