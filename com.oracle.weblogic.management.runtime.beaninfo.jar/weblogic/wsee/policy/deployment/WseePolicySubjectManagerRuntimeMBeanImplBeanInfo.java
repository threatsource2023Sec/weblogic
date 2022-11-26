package weblogic.wsee.policy.deployment;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WseePolicySubjectManagerRuntimeMBean;

public class WseePolicySubjectManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseePolicySubjectManagerRuntimeMBean.class;

   public WseePolicySubjectManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseePolicySubjectManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.policy.deployment.WseePolicySubjectManagerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("package", "weblogic.wsee.policy.deployment");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseePolicySubjectManagerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("getPolicyReferenceInfos", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
      }

      mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("setPolicyReferenceInfos", String.class, TabularData.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policySubject", (String)null), createParameterDescriptor("policyReferences", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for TabularData");
      }

      mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("setPolicyReferenceInfo", String.class, CompositeData.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policySubject", (String)null), createParameterDescriptor("policyReference", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("attachPolicyReference", String.class, CompositeData.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policySubject", (String)null), createParameterDescriptor("policyReference", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
      }

      mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("removePolicyReference", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policySubject", (String)null), createParameterDescriptor("policyReferenceURI", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("getPolicyRefStatus", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policySubject", (String)null), createParameterDescriptor("policyReferenceURI", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("setPolicyRefStatus", String.class, String.class, Boolean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policySubject", (String)null), createParameterDescriptor("policyReferenceURI", (String)null), createParameterDescriptor("enable", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
      }

      mth = WseePolicySubjectManagerRuntimeMBean.class.getMethod("isOWSMAttachable", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policySubject", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("owner", "Context");
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
