package weblogic.management.commo;

import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class AbstractCommoConfigurationBeanImplBeanInfo extends BeanInfoImpl {
   public AbstractCommoConfigurationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AbstractCommoConfigurationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = StandardInterface.class.getMethod("wls_getDisplayName");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "");
         currentResult.setValue("role", "getter");
      }

      mth = AbstractDescriptorBean.class.getMethod("isSet", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("propertyName", "property to check ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns true if the specified attribute has been set explicitly in this MBean instance. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "info");
      }

      mth = AbstractDescriptorBean.class.getMethod("unSet", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("propertyName", "property to restore ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("UnsupportedOperationException if called on a runtime          implementation.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Restore the given property to its default value. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("allowSecurityOperations", Boolean.TRUE);
      }

   }
}
