package weblogic.deploy.api.tools.remote;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SessionHelperRuntimeMBean;

public class SessionHelperRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SessionHelperRuntimeMBean.class;

   public SessionHelperRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SessionHelperRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.deploy.api.tools.remote.SessionHelperRuntime");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.1.2");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.deploy.api.tools.remote");
      String description = (new String("This class allows EditAccessImpl operations to affect the deployment configuration.  EditAccessImpl invokes operations on a registered callback class, when then uses SessionHelperRuntimeMBean to call methods on this class ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SessionHelperRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationBean")) {
         getterName = "getApplicationBean";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationBean", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationBean", currentResult);
         currentResult.setValue("description", "Return the configuration class for application.xml ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Changes")) {
         getterName = "getChanges";
         setterName = null;
         currentResult = new PropertyDescriptor("Changes", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Changes", currentResult);
         currentResult.setValue("description", "Return the difference between the saved configuration and the current configuration that is not activated. This is called as a side effect of getting the ConfigurationManagerMBean Changes attribute. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectorBeans")) {
         getterName = "getConnectorBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorBeans", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectorBeans", currentResult);
         currentResult.setValue("description", "Return the configuration classes for ra.xml & weblogic-ra.xml ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EjbJarBeans")) {
         getterName = "getEjbJarBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("EjbJarBeans", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EjbJarBeans", currentResult);
         currentResult.setValue("description", "Return the configuration classes for ejb-jar.xml & weblogic-ejb-jar.xml ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("GarBeans")) {
         getterName = "getGarBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("GarBeans", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GarBeans", currentResult);
         currentResult.setValue("description", "Return the configuration classes for coherence-application.xml ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ModuleBeans")) {
         getterName = "getModuleBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleBeans", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleBeans", currentResult);
         currentResult.setValue("description", "Return the configuration classes for modules (*-jms.xml, *-jdbc.xml) ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnactivatedChanges")) {
         getterName = "getUnactivatedChanges";
         setterName = null;
         currentResult = new PropertyDescriptor("UnactivatedChanges", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UnactivatedChanges", currentResult);
         currentResult.setValue("description", "Return the difference between the original configuration and the current configuration that is not activated. This is called as a side effect of getting the ConfigurationManagerMBean Changes attribute. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebAppBeans")) {
         getterName = "getWebAppBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("WebAppBeans", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WebAppBeans", currentResult);
         currentResult.setValue("description", "Return the configuration classes for web.xml & weblogic.xml ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WeblogicApplicationBean")) {
         getterName = "getWeblogicApplicationBean";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicApplicationBean", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WeblogicApplicationBean", currentResult);
         currentResult.setValue("description", "Return the configuration class for weblogic-application.xml ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Modified")) {
         getterName = "isModified";
         setterName = null;
         currentResult = new PropertyDescriptor("Modified", SessionHelperRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Modified", currentResult);
         currentResult.setValue("description", "Return whether the current configuration has been changed. This is called as a side effect of invoking the ConfigurationManagerMBean haveUnactivatedChanges method. ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
      Method mth = SessionHelperRuntimeMBean.class.getMethod("savePlan");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Save the current configuration to the pending directory. This is called as a side effect of a call to the ConfigurationManagerMBean save operation. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = SessionHelperRuntimeMBean.class.getMethod("activateChanges");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Activate the changes. This is called as a side effect of a call to the ConfigurationManagerMBean activate operation. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = SessionHelperRuntimeMBean.class.getMethod("updateApplication");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Update the deployment plan from the activated configuration. This is called as a side effect of a call to the ConfigurationManagerMBean activate operation. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = SessionHelperRuntimeMBean.class.getMethod("undoUnsavedChanges");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Undo the configuration back to the last save or to the original if no save has been done. This is called as a side effect of a call to the ConfigurationManagerMBean undo operation. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = SessionHelperRuntimeMBean.class.getMethod("undoUnactivatedChanges");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Undo the configuration back to the last activation. Saved, but unactivated changes are lost. This is called as a side effect of a call to the ConfigurationManagerMBean undoUnactivated operation. ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
