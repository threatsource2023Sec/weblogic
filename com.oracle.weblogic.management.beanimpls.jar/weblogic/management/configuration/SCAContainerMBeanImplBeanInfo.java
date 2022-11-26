package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class SCAContainerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SCAContainerMBean.class;

   public SCAContainerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SCAContainerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SCAContainerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SCAContainerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MaxAge")) {
         getterName = "getMaxAge";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxAge";
         }

         currentResult = new PropertyDescriptor("MaxAge", SCAContainerMBean.class, getterName, setterName);
         descriptors.put("MaxAge", currentResult);
         currentResult.setValue("description", "Get max of a stateful service ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIdleTime")) {
         getterName = "getMaxIdleTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIdleTime";
         }

         currentResult = new PropertyDescriptor("MaxIdleTime", SCAContainerMBean.class, getterName, setterName);
         descriptors.put("MaxIdleTime", currentResult);
         currentResult.setValue("description", "Get max idle time of the a stateful service ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Timeout")) {
         getterName = "getTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeout";
         }

         currentResult = new PropertyDescriptor("Timeout", SCAContainerMBean.class, getterName, setterName);
         descriptors.put("Timeout", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowsPassByReference")) {
         getterName = "isAllowsPassByReference";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowsPassByReference";
         }

         currentResult = new PropertyDescriptor("AllowsPassByReference", SCAContainerMBean.class, getterName, setterName);
         descriptors.put("AllowsPassByReference", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Autowire")) {
         getterName = "isAutowire";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutowire";
         }

         currentResult = new PropertyDescriptor("Autowire", SCAContainerMBean.class, getterName, setterName);
         descriptors.put("Autowire", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Remotable")) {
         getterName = "isRemotable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemotable";
         }

         currentResult = new PropertyDescriptor("Remotable", SCAContainerMBean.class, getterName, setterName);
         descriptors.put("Remotable", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SinglePrincipal")) {
         getterName = "isSinglePrincipal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSinglePrincipal";
         }

         currentResult = new PropertyDescriptor("SinglePrincipal", SCAContainerMBean.class, getterName, setterName);
         descriptors.put("SinglePrincipal", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
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
