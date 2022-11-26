package weblogic.spring.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.SpringBeanDefinitionRuntimeMBean;

public class SpringBeanDefinitionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SpringBeanDefinitionRuntimeMBean.class;

   public SpringBeanDefinitionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SpringBeanDefinitionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.spring.monitoring.SpringBeanDefinitionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "weblogic.spring.monitoring");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SpringBeanDefinitionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Aliases")) {
         getterName = "getAliases";
         setterName = null;
         currentResult = new PropertyDescriptor("Aliases", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Aliases", currentResult);
         currentResult.setValue("description", "<p>Get the aliases for this bean definition. Aliases are other names this bean definition is known by.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ApplicationContextDisplayName")) {
         getterName = "getApplicationContextDisplayName";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationContextDisplayName", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationContextDisplayName", currentResult);
         currentResult.setValue("description", "<p>Display name of the application context that this Spring bean is defined in. The application context is the Spring Inversion of Control (IoC) container.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeanClassname")) {
         getterName = "getBeanClassname";
         setterName = null;
         currentResult = new PropertyDescriptor("BeanClassname", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeanClassname", currentResult);
         currentResult.setValue("description", "<p>Class name of this Spring bean, as defined in the application context of the Spring application.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeanId")) {
         getterName = "getBeanId";
         setterName = null;
         currentResult = new PropertyDescriptor("BeanId", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeanId", currentResult);
         currentResult.setValue("description", "<p>Name of the Spring bean.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Dependencies")) {
         getterName = "getDependencies";
         setterName = null;
         currentResult = new PropertyDescriptor("Dependencies", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Dependencies", currentResult);
         currentResult.setValue("description", "<p>Get the names (ids) of other bean definitions that this bean definition depends on.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DependencyValues")) {
         getterName = "getDependencyValues";
         setterName = null;
         currentResult = new PropertyDescriptor("DependencyValues", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DependencyValues", currentResult);
         currentResult.setValue("description", "<p>Dependency values to be injected into this bean.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for SpringBeanDependencyValue");
      }

      if (!descriptors.containsKey("ParentId")) {
         getterName = "getParentId";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentId", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParentId", currentResult);
         currentResult.setValue("description", "<p>Name (Id) of parent bean definition.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceDescription")) {
         getterName = "getResourceDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceDescription", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceDescription", currentResult);
         currentResult.setValue("description", "<p>The name of the resource that this bean definition comes from. May be empty if the bean is implicitly registered.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Role")) {
         getterName = "getRole";
         setterName = null;
         currentResult = new PropertyDescriptor("Role", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Role", currentResult);
         currentResult.setValue("description", "<p>Role hint of this bean definition. The role is one of ROLE_APPLICATION, ROLE_SUPPORT, or ROLE_INFRASTRUCTURE.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Scope")) {
         getterName = "getScope";
         setterName = null;
         currentResult = new PropertyDescriptor("Scope", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Scope", currentResult);
         currentResult.setValue("description", "<p>Scope of this bean. The scope is \"singleton\", \"prototype\", or other web specific or user defined values.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Abstract")) {
         getterName = "isAbstract";
         setterName = null;
         currentResult = new PropertyDescriptor("Abstract", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Abstract", currentResult);
         currentResult.setValue("description", "<p>Whether this Spring bean is \"abstract\". An abstract bean definition can be used as a base for other definitions but cannot be instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutowireCandidate")) {
         getterName = "isAutowireCandidate";
         setterName = null;
         currentResult = new PropertyDescriptor("AutowireCandidate", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AutowireCandidate", currentResult);
         currentResult.setValue("description", "<p>Whether this bean is a candidate to be autowired to other beans.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LazyInit")) {
         getterName = "isLazyInit";
         setterName = null;
         currentResult = new PropertyDescriptor("LazyInit", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LazyInit", currentResult);
         currentResult.setValue("description", "<p>Whether this bean should be lazily initialized. A lazy initialized bean is not created until it is needed.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Singleton")) {
         getterName = "isSingleton";
         setterName = null;
         currentResult = new PropertyDescriptor("Singleton", SpringBeanDefinitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Singleton", currentResult);
         currentResult.setValue("description", "<p>Whether this is a singleton Spring bean. There is just one instance of a singleton bean per bean definition per application context.</p> ");
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
