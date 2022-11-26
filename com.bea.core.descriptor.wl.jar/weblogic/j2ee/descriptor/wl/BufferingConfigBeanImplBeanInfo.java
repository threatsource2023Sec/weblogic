package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class BufferingConfigBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = BufferingConfigBean.class;

   public BufferingConfigBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public BufferingConfigBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.BufferingConfigBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("since", "10.3.2.1");
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Represents buffering configuration for web services.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.BufferingConfigBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("RequestQueue")) {
         getterName = "getRequestQueue";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestQueue", BufferingConfigBean.class, getterName, setterName);
         descriptors.put("RequestQueue", currentResult);
         currentResult.setValue("description", "Configuration for the request buffering queue ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResponseQueue")) {
         getterName = "getResponseQueue";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseQueue", BufferingConfigBean.class, getterName, setterName);
         descriptors.put("ResponseQueue", currentResult);
         currentResult.setValue("description", "Configuration for the response buffering queue ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryCount")) {
         getterName = "getRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryCount";
         }

         currentResult = new PropertyDescriptor("RetryCount", BufferingConfigBean.class, getterName, setterName);
         descriptors.put("RetryCount", currentResult);
         currentResult.setValue("description", "The number of times a buffered request or response can be retried before it is abandoned (and moved to any error queue defined for the buffer queue) ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryDelay")) {
         getterName = "getRetryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryDelay";
         }

         currentResult = new PropertyDescriptor("RetryDelay", BufferingConfigBean.class, getterName, setterName);
         descriptors.put("RetryDelay", currentResult);
         currentResult.setValue("description", "The amount time between retries of a buffered request and response. Note, this value is only applicable when RetryCount > 0. String value in Duration format. Defaults to P0DT30S (30 seconds). ");
         setPropertyDescriptorDefault(currentResult, "P0DT30S");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Customized")) {
         getterName = "isCustomized";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomized";
         }

         currentResult = new PropertyDescriptor("Customized", BufferingConfigBean.class, getterName, setterName);
         descriptors.put("Customized", currentResult);
         currentResult.setValue("description", "A boolean flag indicating whether the config described by this bean has been customized and should be considered active for use at runtime. Defaults to true. If false, none of the values on this bean will be used, and the server-wide defaults for these values will take effect. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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
