package weblogic.management.security.authorization;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class RoleAuxiliaryMBeanImplBeanInfo extends RoleEditorMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RoleAuxiliaryMBean.class;

   public RoleAuxiliaryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RoleAuxiliaryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authorization.RoleAuxiliaryMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authorization");
      String description = (new String("Provides a set of auxiliary methods for creating, editing, and removing role assignment policies. An RoleMapping-provider MBean can optionally extend this MBean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authorization.RoleAuxiliaryMBean");
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
      Method mth = RoleAuxiliaryMBean.class.getMethod("createRole", String.class, String.class, String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource that scopes the new role. Each resource has its predefined 'hierachy'. This new role is applicable to all of the given resouce's descendants if any. A null indicates a global role, no scoping resource, which applies to all resources within the container. "), createParameterDescriptor("roleName", "- The name of the role that this method creates. A null value will trigger NullPointerException. "), createParameterDescriptor("expression", "- The expression policy designates which user or group having this named 'role'. A null value indicates this role is not granted to anyone. "), createParameterDescriptor("auxiliary", "- Auxiliary data to support WLP. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates role for a resource. ");
         currentResult.setValue("role", "operation");
      }

      mth = RoleAuxiliaryMBean.class.getMethod("setRoleAuxiliary", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource that scopes the role. "), createParameterDescriptor("roleName", "- The name of the role that this method sets the auxiliary on. A null value will trigger NullPointerException. "), createParameterDescriptor("auxiliary", "- Auxiliary data to support WLP. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Set auxiliary role for a resource. ");
         currentResult.setValue("role", "operation");
      }

      mth = RoleAuxiliaryMBean.class.getMethod("getRoleAuxiliary", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceId", "- The resource that scopes the role. "), createParameterDescriptor("roleName", "- The name of the role that this method sets the auxiliary on. A null value will trigger NullPointerException. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get auxiliary role for a resource. ");
         currentResult.setValue("role", "operation");
      }

      mth = RoleAuxiliaryMBean.class.getMethod("exportResource", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("filename", "- The full path to the filename used to write data. "), createParameterDescriptor("cn", "- The LDAP CN to be used when exporting data. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Exports provider specific role data using an LDAP CN search filter to specify the resources for export. When errors occur, the MBean throws an ErrorCollectionException containing a list of &lt;code&gt;java.lang.Exceptions&lt;/code;&gt;. ");
         currentResult.setValue("role", "operation");
      }

      mth = RoleAuxiliaryMBean.class.getMethod("listAllRolesAndURIs", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("application", "- The name of the deployed application. "), createParameterDescriptor("contextPath", "- The context path for the application. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "List all Role and URI pairings that have policy defined for the specified application and context path. ");
         currentResult.setValue("role", "operation");
      }

      mth = RoleAuxiliaryMBean.class.getMethod("getRoleNames", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceType", "- The resource type, or null to get global roles ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns array of resource/role name tuples of roles defined in the scope of resources of the given type or global roles, when the type is null. ");
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
