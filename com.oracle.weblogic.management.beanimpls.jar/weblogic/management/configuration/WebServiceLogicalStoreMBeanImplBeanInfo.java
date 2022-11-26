package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebServiceLogicalStoreMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServiceLogicalStoreMBean.class;

   public WebServiceLogicalStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServiceLogicalStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServiceLogicalStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Represents a logical store for web services.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServiceLogicalStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CleanerInterval")) {
         getterName = "getCleanerInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCleanerInterval";
         }

         currentResult = new PropertyDescriptor("CleanerInterval", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("CleanerInterval", currentResult);
         currentResult.setValue("description", "<p>Get the interval at which the persistent store will be cleaned</p> ");
         setPropertyDescriptorDefault(currentResult, "PT10M");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultMaximumObjectLifetime")) {
         getterName = "getDefaultMaximumObjectLifetime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultMaximumObjectLifetime";
         }

         currentResult = new PropertyDescriptor("DefaultMaximumObjectLifetime", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("DefaultMaximumObjectLifetime", currentResult);
         currentResult.setValue("description", "<p>Get the default max time an object can remain in the store. This can be overridden on individual objects placed in the store (internally, but not via this API).</p> ");
         setPropertyDescriptorDefault(currentResult, "P1D");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>Get the name of this logical store.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceStrategy")) {
         getterName = "getPersistenceStrategy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistenceStrategy";
         }

         currentResult = new PropertyDescriptor("PersistenceStrategy", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("PersistenceStrategy", currentResult);
         currentResult.setValue("description", "<p>Get the persistence strategy in use by this logical store. Any physical store configured for use with this logical store should support this strategy.</p> ");
         setPropertyDescriptorDefault(currentResult, "LOCAL_ACCESS_ONLY");
         currentResult.setValue("legalValues", new Object[]{"LOCAL_ACCESS_ONLY", "IN_MEMORY"});
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("PhysicalStoreName")) {
         getterName = "getPhysicalStoreName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPhysicalStoreName";
         }

         currentResult = new PropertyDescriptor("PhysicalStoreName", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("PhysicalStoreName", currentResult);
         currentResult.setValue("description", "<p>Get the name of the physical store to be used by this logical store. This property is recommended for use only when running off server or in other cases where a buffering queue JNDI name cannot be set via setBufferingQueueJndiName. If a buffering queue JNDI name is set to a non-null/non-empty value, this property is ignored. Defaults to \"\" to indicate the default WLS file store should be used.</p>  <p>This property is ignored if persistence strategy is IN_MEMORY.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setRequestBufferingQueueJndiName(String)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequestBufferingQueueJndiName")) {
         getterName = "getRequestBufferingQueueJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequestBufferingQueueJndiName";
         }

         currentResult = new PropertyDescriptor("RequestBufferingQueueJndiName", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("RequestBufferingQueueJndiName", currentResult);
         currentResult.setValue("description", "<p>Get the JNDI name of the buffering queue that web services should use. (Both for buffering and to  find the physical store for this logical store). Defaults to \"\" to indicate the PhysicalStoreName property should be used.</p>  <p>This property is ignored if persistence strategy is IN_MEMORY.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setRequestBufferingQueueJndiName(String)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseBufferingQueueJndiName")) {
         getterName = "getResponseBufferingQueueJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResponseBufferingQueueJndiName";
         }

         currentResult = new PropertyDescriptor("ResponseBufferingQueueJndiName", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("ResponseBufferingQueueJndiName", currentResult);
         currentResult.setValue("description", "<p>Get the JNDI name of the response buffering queue that web services should use. If this is null, the request buffering queue is used.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRequestBufferingQueueJndiName()")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", WebServiceLogicalStoreMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebServiceLogicalStoreMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WebServiceLogicalStoreMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebServiceLogicalStoreMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WebServiceLogicalStoreMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
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
