package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class WeblogicConnectorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WeblogicConnectorBean.class;

   public WeblogicConnectorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicConnectorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML weblogic-connectorType(@http://xmlns.oracle.com/weblogic/weblogic-connector). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WeblogicConnectorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AdminObjects")) {
         getterName = "getAdminObjects";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminObjects", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("AdminObjects", currentResult);
         currentResult.setValue("description", "Gets the \"admin-objects\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectorWorkManager")) {
         getterName = "getConnectorWorkManager";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorWorkManager", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("ConnectorWorkManager", currentResult);
         currentResult.setValue("description", "Gets the \"connector-work-manager\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "Gets the \"jndi-name\" element ");
         currentResult.setValue("declaration", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NativeLibdir")) {
         getterName = "getNativeLibdir";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNativeLibdir";
         }

         currentResult = new PropertyDescriptor("NativeLibdir", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("NativeLibdir", currentResult);
         currentResult.setValue("description", "Gets the \"native-libdir\" element ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutboundResourceAdapter")) {
         getterName = "getOutboundResourceAdapter";
         setterName = null;
         currentResult = new PropertyDescriptor("OutboundResourceAdapter", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("OutboundResourceAdapter", currentResult);
         currentResult.setValue("description", "Gets the \"outbound-resource-adapter\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Gets the \"properties\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Security")) {
         getterName = "getSecurity";
         setterName = null;
         currentResult = new PropertyDescriptor("Security", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("Security", currentResult);
         currentResult.setValue("description", "Gets the \"security\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManager")) {
         getterName = "getWorkManager";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManager", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("WorkManager", currentResult);
         currentResult.setValue("description", "Gets the \"work-manager\" element ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWorkManager");
         currentResult.setValue("creator", "createWorkManager");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeployAsAWhole")) {
         getterName = "isDeployAsAWhole";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployAsAWhole";
         }

         currentResult = new PropertyDescriptor("DeployAsAWhole", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("DeployAsAWhole", currentResult);
         currentResult.setValue("description", "Gets the \"deploy-as-a-whole\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableAccessOutsideApp")) {
         getterName = "isEnableAccessOutsideApp";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableAccessOutsideApp";
         }

         currentResult = new PropertyDescriptor("EnableAccessOutsideApp", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("EnableAccessOutsideApp", currentResult);
         currentResult.setValue("description", "Gets the \"enable-access-outside-app\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableGlobalAccessToClasses")) {
         getterName = "isEnableGlobalAccessToClasses";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableGlobalAccessToClasses";
         }

         currentResult = new PropertyDescriptor("EnableGlobalAccessToClasses", WeblogicConnectorBean.class, getterName, setterName);
         descriptors.put("EnableGlobalAccessToClasses", currentResult);
         currentResult.setValue("description", "Gets the \"enable-global-access-to-classes\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicConnectorBean.class.getMethod("createWorkManager");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WorkManager");
      }

      mth = WeblogicConnectorBean.class.getMethod("destroyWorkManager", WorkManagerBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WorkManager");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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
