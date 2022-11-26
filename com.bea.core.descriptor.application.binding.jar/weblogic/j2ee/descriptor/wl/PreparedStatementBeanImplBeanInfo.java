package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class PreparedStatementBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PreparedStatementBean.class;

   public PreparedStatementBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PreparedStatementBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.PreparedStatementBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.PreparedStatementBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CacheProfilingThreshold")) {
         getterName = "getCacheProfilingThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheProfilingThreshold";
         }

         currentResult = new PropertyDescriptor("CacheProfilingThreshold", PreparedStatementBean.class, getterName, setterName);
         descriptors.put("CacheProfilingThreshold", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheSize")) {
         getterName = "getCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheSize";
         }

         currentResult = new PropertyDescriptor("CacheSize", PreparedStatementBean.class, getterName, setterName);
         descriptors.put("CacheSize", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheType")) {
         getterName = "getCacheType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheType";
         }

         currentResult = new PropertyDescriptor("CacheType", PreparedStatementBean.class, getterName, setterName);
         descriptors.put("CacheType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxParameterLength")) {
         getterName = "getMaxParameterLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxParameterLength";
         }

         currentResult = new PropertyDescriptor("MaxParameterLength", PreparedStatementBean.class, getterName, setterName);
         descriptors.put("MaxParameterLength", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParameterLoggingEnabled")) {
         getterName = "isParameterLoggingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParameterLoggingEnabled";
         }

         currentResult = new PropertyDescriptor("ParameterLoggingEnabled", PreparedStatementBean.class, getterName, setterName);
         descriptors.put("ParameterLoggingEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProfilingEnabled")) {
         getterName = "isProfilingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProfilingEnabled";
         }

         currentResult = new PropertyDescriptor("ProfilingEnabled", PreparedStatementBean.class, getterName, setterName);
         descriptors.put("ProfilingEnabled", currentResult);
         currentResult.setValue("description", " ");
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
