package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class GroupEditorMBeanImplBeanInfo extends GroupReaderMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = GroupEditorMBean.class;

   public GroupEditorMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public GroupEditorMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.GroupEditorMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("Provides a set of methods for creating, editing, and removing groups. An Authentication provider MBean can optionally implement this MBean. The WebLogic Server Administration Console detects when an Authentication provider implements this MBean and automatically provides a tab for using these methods. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.GroupEditorMBean");
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
      Method mth = GroupEditorMBean.class.getMethod("createGroup", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupName", "- The name of the new group. The name cannot be the name of an existing user or group. The Authentication provider determines syntax requirements for the group name. "), createParameterDescriptor("description", "- The description of the group. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a group. ");
         currentResult.setValue("role", "operation");
      }

      mth = GroupEditorMBean.class.getMethod("removeGroup", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupName", "- The name of an existing group. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a group. If the group contains members, the members are not removed. ");
         currentResult.setValue("role", "operation");
      }

      mth = GroupEditorMBean.class.getMethod("setGroupDescription", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupName", "- The name of an existing group. "), createParameterDescriptor("description", "- The description of the group. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Sets the description for an existing group. ");
         currentResult.setValue("role", "operation");
      }

      mth = GroupEditorMBean.class.getMethod("addMemberToGroup", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupName", "- The name of an existing group to which this method adds a member. "), createParameterDescriptor("memberUserOrGroupName", "- The name of the member, which must be an existing user or group. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds a user or group (member) to a group. If the member already belongs to the group, this method does nothing. ");
         currentResult.setValue("role", "operation");
      }

      mth = GroupEditorMBean.class.getMethod("removeMemberFromGroup", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("groupName", "- The name of an existing group from which this method removes a member. "), createParameterDescriptor("memberUserOrGroupName", "- The name of the member, which must be an existing user or group. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a user or group (member) from a group. If the member is not in the group, this method does nothing. ");
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
