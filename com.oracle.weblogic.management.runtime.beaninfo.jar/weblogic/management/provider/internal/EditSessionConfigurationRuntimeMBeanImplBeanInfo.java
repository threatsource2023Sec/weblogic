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
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;

public class EditSessionConfigurationRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EditSessionConfigurationRuntimeMBean.class;

   public EditSessionConfigurationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EditSessionConfigurationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.provider.internal.EditSessionConfigurationRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.provider.internal");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EditSessionConfigurationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Creator")) {
         getterName = "getCreator";
         setterName = null;
         currentResult = new PropertyDescriptor("Creator", EditSessionConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Creator", currentResult);
         currentResult.setValue("description", "<p>The original creator of the edit session.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentEditor")) {
         getterName = "getCurrentEditor";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentEditor", EditSessionConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentEditor", currentResult);
         currentResult.setValue("description", "<p>The current editor of the edit session. The editor owns the lock.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", EditSessionConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>The description of the edit session configuration.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EditSessionName")) {
         getterName = "getEditSessionName";
         setterName = null;
         currentResult = new PropertyDescriptor("EditSessionName", EditSessionConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EditSessionName", currentResult);
         currentResult.setValue("description", "<p>The name of the edit session configuration.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EditSessionServerJndi")) {
         getterName = "getEditSessionServerJndi";
         setterName = null;
         currentResult = new PropertyDescriptor("EditSessionServerJndi", EditSessionConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EditSessionServerJndi", currentResult);
         currentResult.setValue("description", "<p>The JNDI name of the related <code>EditSessionServer</code>.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PartitionName")) {
         getterName = "getPartitionName";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionName", EditSessionConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionName", currentResult);
         currentResult.setValue("description", "<p>The partition name to which this edit session belongs.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (!descriptors.containsKey("MergeNeeded")) {
         getterName = "isMergeNeeded";
         setterName = null;
         currentResult = new PropertyDescriptor("MergeNeeded", EditSessionConfigurationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MergeNeeded", currentResult);
         currentResult.setValue("description", "<p>Returns <code>true</code> if another edit session activates its changes after the last activation of this edit session or after its creation but before this edit session was ever activated.</p>  <p>It indicates that this edit session configuration is probably not the actual runtime configuration. Use the resolve method to merge changes to this configuration.</p>  <p>If this edit session configuration was also modified then there can be conflicts between this session and the runtime configuration. Use the resolve method to remove possible inconsistencies.</p> ");
         currentResult.setValue("owner", "");
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
      Method mth = EditSessionConfigurationRuntimeMBean.class.getMethod("containsUnactivatedChanges");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns <code>true</code> if there are changes saved in the pending directory or there are changes in memory. To apply these changes, activate the session.</p> ");
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
