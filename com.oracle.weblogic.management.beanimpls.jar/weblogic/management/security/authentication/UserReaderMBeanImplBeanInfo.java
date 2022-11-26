package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.utils.NameListerMBeanImplBeanInfo;

public class UserReaderMBeanImplBeanInfo extends NameListerMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UserReaderMBean.class;

   public UserReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UserReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.UserReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("Provides a set of methods for reading data about users. An Authentication provider MBean can optionally implement this MBean. The WebLogic Server Administration Console detects when an Authentication provider implements this MBean and automatically provides a tab for using these methods. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.UserReaderMBean");
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
      Method mth = UserReaderMBean.class.getMethod("listUsers", String.class, Integer.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userNameWildcard", "- The pattern for which this method searches. The pattern can end with an <code>*</code> (asterisk) as a wildcard, which matches any string of characters. The search is not case-sensitive.  <p>For example, a pattern of <code>abc</code> matches exactly one user name that contains only <code>abc</code>, a pattern of <code>ab*</code> matches all user names that start with <code>ab</code>, and a pattern of <code>*</code> matches all user names.</p> "), createParameterDescriptor("maximumToReturn", "- The maximum number of user names that this method returns. If there are more matches than this maximum, then the returned results are arbitrary because this method does not sort results. If the parameter is set to 0 there is no maximum and all results are returned. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Searches for a user name that matches a pattern.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.NameListerMBean</code> (which this MBean extends) to iterate through the returned list.</p>  <p>This method does not sort the results.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = UserReaderMBean.class.getMethod("userExists", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- The name that this method evaluates. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates whether the specified user exists. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = UserReaderMBean.class.getMethod("getUserDescription", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("userName", "- The name of an existing user. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a user's description. ");
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
