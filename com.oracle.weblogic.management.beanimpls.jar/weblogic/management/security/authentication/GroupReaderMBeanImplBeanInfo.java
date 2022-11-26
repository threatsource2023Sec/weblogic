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

public class GroupReaderMBeanImplBeanInfo extends NameListerMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = GroupReaderMBean.class;

   public GroupReaderMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public GroupReaderMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.GroupReaderMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("Provides a set of methods for reading data about groups. An Authentication provider MBean can optionally implement this MBean. The WebLogic Server Administration Console detects when an Authentication provider implements this MBean and automatically provides a tab for using these methods. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.GroupReaderMBean");
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
      Method mth = GroupReaderMBean.class.getMethod("listGroups", String.class, Integer.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupNameWildcard", "- <p>The pattern for which this method searches. The pattern can end with an <code>*</code> (asterisk) as a wildcard, which matches any string of characters. The search is not case-sensitive.</p> <p>For example, a pattern of <code>abc</code> matches exactly one group name that contains only <code>abc</code>, a pattern of <code>ab*</code> matches all group names that start with <code>ab</code>, and a pattern of <code>*</code> matches all group names.</p> "), createParameterDescriptor("maximumToReturn", "- The maximum number of group names that this method returns. If there are more matches than this maximum, then the returned results are arbitrary because this method does not sort results. If the parameter is set to 0 there is no maximum and all results are returned. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Searches for a user name that matches a pattern.</p>  <p>This method returns a cursor that you can pass to the methods from <code>weblogic.management.utils.NameListerMBean</code> (which this MBean extends) to iterate through the returned list.</p>  <p>This method does not sort the results.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = GroupReaderMBean.class.getMethod("groupExists", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupName", "- The name that this method evaluates. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates whether the specified group exists. ");
         currentResult.setValue("role", "operation");
      }

      mth = GroupReaderMBean.class.getMethod("getGroupDescription", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupName", "- The name of an existing group. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Gets a group's description. ");
         currentResult.setValue("role", "operation");
      }

      mth = GroupReaderMBean.class.getMethod("isMember", String.class, String.class, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("parentGroupName", "- The existing group within which this method searches for membership. "), createParameterDescriptor("memberUserOrGroupName", "- The user or group name for which this method searches. "), createParameterDescriptor("recursive", "- If set to <code>true</code>, the criteria for membership extends to any groups within the group that is specified by <code>parentGroupName</code>. <p> If this argument is set to <code>false</code>, then this method checks only for direct membership within the <code>parentGroupName</code>.</p> ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Indicates whether a user or group is a member of the group that you specify. A recursive search returns true if the member belongs to the group that you specify or to any of the groups contained within that group.\" ");
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
