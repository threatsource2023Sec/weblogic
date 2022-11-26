package weblogic.management.provider.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;

public class EditSessionConfigurationManagerMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EditSessionConfigurationManagerMBean.class;

   public EditSessionConfigurationManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EditSessionConfigurationManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.provider.internal.EditSessionConfigurationManagerMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("owner", "Context");
      beanDescriptor.setValue("package", "weblogic.management.provider.internal");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EditSessionConfigurationManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("EditSessionConfigurations")) {
         String getterName = "getEditSessionConfigurations";
         String setterName = null;
         currentResult = new PropertyDescriptor("EditSessionConfigurations", EditSessionConfigurationManagerMBean.class, getterName, (String)setterName);
         descriptors.put("EditSessionConfigurations", currentResult);
         currentResult.setValue("description", "<p>Returns a list of edit session configuration runtime MBeans. These MBeans contain information about the edit session configurations in this domain.</p> If running in a partition, then only partition specific configuration will be returned. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyEditSessionConfiguration");
         currentResult.setValue("creator", "createEditSessionConfiguration");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = EditSessionConfigurationManagerMBean.class.getMethod("createEditSessionConfiguration", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the edit session configuration to create. "), createParameterDescriptor("description", "Description of the edit session configuration. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a named edit session configuration. This will result in an edit session specific MBeanServer and associated configuration files in the &lt;domain&gt;/edit/&lt;name&gt; or &lt;domain&gt;/partitions/&lt;name&gt;/edit/&lt;name&gt; directory depending on whether running in a partition or not</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EditSessionConfigurations");
      }

      mth = EditSessionConfigurationManagerMBean.class.getMethod("destroyEditSessionConfiguration", EditSessionConfigurationRuntimeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("editSession", "Edit session to destroy. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys an existing named edit session configuration. This will destroy the associated edit session specific MBeanServer and remove the &lt;domain&gt;/edit/&lt;name&gt; or &lt;domain&gt;/partitions/&lt;name&gt;/edit/&lt;name&gt; directory and subdirectories. </p>  <p>In case of unactivated changes or if the session is created or locked by another user, {@code IllegalArgumentException} is thrown. To successfully complete the operation in such cases, less restrictive counterpart {@link #forceDestroyEditSessionConfiguration(EditSessionConfigurationRuntimeMBean)} of this method has to be used.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EditSessionConfigurations");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = EditSessionConfigurationManagerMBean.class.getMethod("lookupEditSessionConfiguration", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "edit session name. If {@code null} or empty string, default edit session name (global) will be used. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Look-up {@link EditSessionConfigurationRuntimeMBean}. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "EditSessionConfigurations");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = EditSessionConfigurationManagerMBean.class.getMethod("forceDestroyEditSessionConfiguration", EditSessionConfigurationRuntimeMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("editSession", "Edit session to destroy. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys an existing named edit session configuration. This will destroy the associated edit session specific MBeanServer and remove the &lt;domain&gt;/edit/&lt;name&gt; or &lt;domain&gt;/partitions/&lt;name&gt;/edit/&lt;name&gt; directory and subdirectories. </p>  <p>Unlike {@link #destroyEditSessionConfiguration(EditSessionConfigurationRuntimeMBean)}, this operation does not end with error in case that session contains unactivated changes, and allows to destroy even edit sessions not owned by the caller (requires admin privileges).</p> ");
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
