package weblogic.spring.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SpringRuntimeMBean;

public class SpringRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SpringRuntimeMBean.class;

   public SpringRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SpringRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.spring.monitoring.SpringRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "weblogic.spring.monitoring");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SpringRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SpringApplicationContextRuntimeMBeans")) {
         getterName = "getSpringApplicationContextRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringApplicationContextRuntimeMBeans", SpringRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringApplicationContextRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>The Spring ApplicationContext runtime bean definitions.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (!descriptors.containsKey("SpringBeanDefinitionRuntimeMBeans")) {
         getterName = "getSpringBeanDefinitionRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringBeanDefinitionRuntimeMBeans", SpringRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringBeanDefinitionRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>The Spring bean definitions.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SpringTransactionManagerRuntimeMBeans")) {
         getterName = "getSpringTransactionManagerRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringTransactionManagerRuntimeMBeans", SpringRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringTransactionManagerRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>The Spring TransactionManager runtime bean definitions.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SpringTransactionTemplateRuntimeMBeans")) {
         getterName = "getSpringTransactionTemplateRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringTransactionTemplateRuntimeMBeans", SpringRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringTransactionTemplateRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>The Spring TransactionTemplate runtime bean definitions.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (!descriptors.containsKey("SpringVersion")) {
         getterName = "getSpringVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringVersion", SpringRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringVersion", currentResult);
         currentResult.setValue("description", "<p>Get the version number of the Spring Framework.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SpringViewResolverRuntimeMBeans")) {
         getterName = "getSpringViewResolverRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringViewResolverRuntimeMBeans", SpringRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringViewResolverRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>The Spring ViewResolver runtime bean definitions.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SpringViewRuntimeMBeans")) {
         getterName = "getSpringViewRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("SpringViewRuntimeMBeans", SpringRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpringViewRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>The Spring View runtime bean definitions.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
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
