package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebServicePersistenceMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServicePersistenceMBean.class;

   public WebServicePersistenceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServicePersistenceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServicePersistenceMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Represents persistence configuration for web services.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServicePersistenceMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DefaultLogicalStoreName")) {
         getterName = "getDefaultLogicalStoreName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultLogicalStoreName";
         }

         currentResult = new PropertyDescriptor("DefaultLogicalStoreName", WebServicePersistenceMBean.class, getterName, setterName);
         descriptors.put("DefaultLogicalStoreName", currentResult);
         currentResult.setValue("description", "Get the name of the logical store to use, by default, for all web services persistent state in this server. ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getWebServiceLogicalStores()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "WseeStore");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServiceLogicalStores")) {
         getterName = "getWebServiceLogicalStores";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServiceLogicalStores", WebServicePersistenceMBean.class, getterName, setterName);
         descriptors.put("WebServiceLogicalStores", currentResult);
         currentResult.setValue("description", "Get an array of all defined logical stores for this VM (non-WLS). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWebServiceLogicalStore");
         currentResult.setValue("destroyer", "destroyWebServiceLogicalStore");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServicePhysicalStores")) {
         getterName = "getWebServicePhysicalStores";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServicePhysicalStores", WebServicePersistenceMBean.class, getterName, setterName);
         descriptors.put("WebServicePhysicalStores", currentResult);
         currentResult.setValue("description", "Get an array of all defined physical stores for this VM (non-WLS). ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWebServicePhysicalStore");
         currentResult.setValue("creator", "createWebServicePhysicalStore");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebServicePersistenceMBean.class.getMethod("createWebServiceLogicalStore", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the new store. Logical store names must start with a letter, and can contain only letters, numbers, spaces and underscores. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new logical store with the given name. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WebServiceLogicalStores");
      }

      mth = WebServicePersistenceMBean.class.getMethod("destroyWebServiceLogicalStore", WebServiceLogicalStoreMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", "The store to destroy/remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy/remove a logical store previously defined by a call to createLogicalStore (or retrieved via a call to getLogicalStores). ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WebServiceLogicalStores");
      }

      mth = WebServicePersistenceMBean.class.getMethod("createWebServicePhysicalStore", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the new store. Physical store names must start with a letter, and can contain only letters, numbers, spaces and underscores. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a new physical store with the given name. Used only for standalone VM (non-WLS). ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WebServicePhysicalStores");
      }

      mth = WebServicePersistenceMBean.class.getMethod("destroyWebServicePhysicalStore", WebServicePhysicalStoreMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", "The store to destroy/remove. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy/remove a physical store previously defined by a call to createPhysicalStore (or retrieved via a call to getPhysicalStores). ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WebServicePhysicalStores");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebServicePersistenceMBean.class.getMethod("lookupWebServiceLogicalStore", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Lookup a logical store by name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WebServiceLogicalStores");
      }

      mth = WebServicePersistenceMBean.class.getMethod("lookupWebServicePhysicalStore", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Get a named physical store for this VM (non-WLS). ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WebServicePhysicalStores");
      }

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
