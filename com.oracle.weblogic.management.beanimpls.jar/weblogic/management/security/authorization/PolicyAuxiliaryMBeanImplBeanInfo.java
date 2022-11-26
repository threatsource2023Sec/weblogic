package weblogic.management.security.authorization;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class PolicyAuxiliaryMBeanImplBeanInfo extends PolicyEditorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PolicyAuxiliaryMBean.class;

   public PolicyAuxiliaryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PolicyAuxiliaryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authorization.PolicyAuxiliaryMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authorization");
      String description = (new String("Provides a set of auxiliary methods for creating, editing, and removing policies. An Authorization-provider MBean can optionally extend this MBean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authorization.PolicyAuxiliaryMBean");
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
      Method mth = PolicyAuxiliaryMBean.class.getMethod("exportResource", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("filename", "- The full path to the filename used to write data. "), createParameterDescriptor("cn", "- The LDAP CN to be used when exporting data. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Exports provider specific policy data using an LDAP CN search filter to specify the resources for export. When errors occur, the MBean throws an ErrorCollectionException containing a list of &lt;code&gt;java.lang.Exceptions&lt;/code;&gt;. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyAuxiliaryMBean.class.getMethod("listAllURIs", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("application", "- The name of the deployed application. "), createParameterDescriptor("contextPath", "- The context path for the application. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "List all URI strings that have policy defined for the specified application and context path. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyAuxiliaryMBean.class.getMethod("getResourceNames", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceType", "- The resource type. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns resource names of policies associated with resources of the given type. ");
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
