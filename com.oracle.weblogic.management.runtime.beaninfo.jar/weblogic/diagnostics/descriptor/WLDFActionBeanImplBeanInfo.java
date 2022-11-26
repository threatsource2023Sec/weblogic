package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFActionBeanImplBeanInfo extends WLDFNotificationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFActionBean.class;

   public WLDFActionBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFActionBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFActionBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>WLDF action configuration bean.  This is a configuration point for action extensions developed outside of WebLogic.</p>  <p>A WLDF action extension is an action that is managed by, but not owned by, to WLDF.  It must have a \"type\" associated with it, which must be provided to the {@link WLDFWatchNotificationBean#createAction(String, String)} method when an instance of this object is created.  An instance of a {@link WLDFActionBean} defines a single instance configuration of an action extension, which can be referenced from WLDFWatchBean instances like any other action/notification type.</p>  <p>It acts as a container of an action properties, each property being a name/value pair.  An action can have simple properties of intrinsic Java types (String, int, float, etc), encrypted properties, Map properties, and array properties.</p>  <p><code>Map</code> and array properties can not contain nested collections or complex objects; that is, the leaf values of those objects must be simple values represented as String values.</p>  <p>WLDF will map these properties to the proper configuration points on the target action instance.  Each action type will have different configuration points, so consult the documentation for those actions to see what the valid configuration properties are for that action type.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFActionBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ArrayProperties")) {
         getterName = "getArrayProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ArrayProperties", WLDFActionBean.class, getterName, (String)setterName);
         descriptors.put("ArrayProperties", currentResult);
         currentResult.setValue("description", "<p>Returns all the array properties of the action configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyArrayProperty");
         currentResult.setValue("creator", "createArrayProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigurationProperties")) {
         getterName = "getConfigurationProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationProperties", WLDFActionBean.class, getterName, (String)setterName);
         descriptors.put("ConfigurationProperties", currentResult);
         currentResult.setValue("description", "<p>Returns the list of all WLDFConfigurationPropertyBean objects that are associated with this container object.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EncryptedProperties")) {
         getterName = "getEncryptedProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("EncryptedProperties", WLDFActionBean.class, getterName, (String)setterName);
         descriptors.put("EncryptedProperties", currentResult);
         currentResult.setValue("description", "Returns all the encrypted properties. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyEncryptedProperty");
         currentResult.setValue("creator", "createEncryptedProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MapProperties")) {
         getterName = "getMapProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("MapProperties", WLDFActionBean.class, getterName, (String)setterName);
         descriptors.put("MapProperties", currentResult);
         currentResult.setValue("description", "<p>Returns the map properties of the action configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMapProperty");
         currentResult.setValue("creator", "createMapProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", WLDFActionBean.class, getterName, (String)setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Returns all of the simple properties for this configured action. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createProperty");
         currentResult.setValue("destroyer", "destroyProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", WLDFActionBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>Defines the action type.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFActionBean.class.getMethod("createProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of WLDFConfigurationPropertyBean ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an unencrypted WLDFConfigurationPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = WLDFActionBean.class.getMethod("destroyProperty", WLDFPropertyBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("propertyBean", "The WLDFConfigurationPropertyBean instance to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys a WLDFConfigurationPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = WLDFActionBean.class.getMethod("createEncryptedProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of WLDFConfigurationPropertyBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an encrypted WLDFConfigurationPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EncryptedProperties");
      }

      mth = WLDFActionBean.class.getMethod("destroyEncryptedProperty", WLDFEncryptedPropertyBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("propertyBean", "The WLDFConfigurationPropertyBean instance to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys a WLDFConfigurationPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EncryptedProperties");
      }

      mth = WLDFActionBean.class.getMethod("createMapProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a named map property object, similar to a nested map of values for the action configuration.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MapProperties");
      }

      mth = WLDFActionBean.class.getMethod("destroyMapProperty", WLDFConfigurationPropertiesBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("property", "The map properties bean to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys the child map property.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MapProperties");
      }

      mth = WLDFActionBean.class.getMethod("createArrayProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a named array property bean.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ArrayProperties");
      }

      mth = WLDFActionBean.class.getMethod("destroyArrayProperty", WLDFArrayPropertyBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("property", "The array property bean to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys the array property bean.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ArrayProperties");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFActionBean.class.getMethod("lookupProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of WLDFConfigurationPropertyBean to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up WLDFConfigurationPropertyBean.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Properties");
      }

      mth = WLDFActionBean.class.getMethod("lookupEncryptedProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of WLDFConfigurationPropertyBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up WLDFConfigurationPropertyBean.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "EncryptedProperties");
      }

      mth = WLDFActionBean.class.getMethod("lookupMapProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the existing map property corresponding to the specified name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MapProperties");
      }

      mth = WLDFActionBean.class.getMethod("lookupArrayProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the existing array property corresponding to the specified name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ArrayProperties");
      }

      mth = WLDFActionBean.class.getMethod("lookupConfigurationProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the configuration property being requested ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up a WLDFConfigurationPropertyBean by name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ConfigurationProperties");
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
