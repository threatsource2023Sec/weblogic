package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class UserAttributeReaderMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserAttributeReaderMBean.class;

   public UserAttributeReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserAttributeReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.UserAttributeReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("Provides a set of methods for getting a list of supported users attributes and their types. Methods are also provided to get user attribute values. An Authentication provider MBean can optionally implement this MBean. The WebLogic Server Administration Console detects when an Authentication provider implements this MBean and automatically provides a tab for using these methods. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.UserAttributeReaderMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("SupportedUserAttributeNames")) {
         String getterName = "getSupportedUserAttributeNames";
         String setterName = null;
         currentResult = new PropertyDescriptor("SupportedUserAttributeNames", UserAttributeReaderMBean.class, getterName, (String)setterName);
         descriptors.put("SupportedUserAttributeNames", currentResult);
         currentResult.setValue("description", "Provides a list of supported user attribute names for the provider. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "RealmAdministrator");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "RealmAdministrator");
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
      Method mth = UserAttributeReaderMBean.class.getMethod("isUserAttributeNameSupported", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userAttributeName", "User attribute name ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Checks if a user attribute is supported. ");
         currentResult.setValue("role", "operation");
      }

      mth = UserAttributeReaderMBean.class.getMethod("getSupportedUserAttributeType", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userAttributeName", "User attribute name ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for OpenType");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns the user attribute type ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for OpenType");
      }

      mth = UserAttributeReaderMBean.class.getMethod("getUserAttributeValue", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- The name of an existing user. "), createParameterDescriptor("userAttributeName", "- The name of an existing attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a user attribute value for a user.  If the user attribute is unset,a null would be returned. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
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
