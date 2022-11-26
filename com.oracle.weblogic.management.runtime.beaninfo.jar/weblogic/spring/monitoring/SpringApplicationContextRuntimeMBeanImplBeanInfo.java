package weblogic.spring.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.SpringApplicationContextRuntimeMBean;

public class SpringApplicationContextRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SpringApplicationContextRuntimeMBean.class;

   public SpringApplicationContextRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SpringApplicationContextRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.spring.monitoring.SpringApplicationContextRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.1.0");
      beanDescriptor.setValue("package", "weblogic.spring.monitoring");
      String description = (new String("This MBean represents instances of class org.springframework.context.support.AbstractApplicationContext and org.springframework.beans.factory.support.AbstractBeanFactory There is a SpringApplicationContextRuntimeMBean for each application context in a deployment. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.SpringApplicationContextRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AverageGetBeanNamesForTypeTime")) {
         getterName = "getAverageGetBeanNamesForTypeTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageGetBeanNamesForTypeTime", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageGetBeanNamesForTypeTime", currentResult);
         currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required for getBeanNamesForType()</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AverageGetBeanTime")) {
         getterName = "getAverageGetBeanTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageGetBeanTime", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageGetBeanTime", currentResult);
         currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required for getBean()</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AverageGetBeansOfTypeTime")) {
         getterName = "getAverageGetBeansOfTypeTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageGetBeansOfTypeTime", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageGetBeansOfTypeTime", currentResult);
         currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required for getBeansOfType()</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AveragePrototypeBeanCreationTime")) {
         getterName = "getAveragePrototypeBeanCreationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AveragePrototypeBeanCreationTime", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AveragePrototypeBeanCreationTime", currentResult);
         currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required to create prototype beans</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AverageRefreshTime")) {
         getterName = "getAverageRefreshTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageRefreshTime", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageRefreshTime", currentResult);
         currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required to perform a refresh</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("AverageSingletonBeanCreationTime")) {
         getterName = "getAverageSingletonBeanCreationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageSingletonBeanCreationTime", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageSingletonBeanCreationTime", currentResult);
         currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required to create singleton beans</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("CustomScopeNames")) {
         getterName = "getCustomScopeNames";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomScopeNames", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CustomScopeNames", currentResult);
         currentResult.setValue("description", "<p>The names of customer scopes that were registered.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (!descriptors.containsKey("DisplayName")) {
         getterName = "getDisplayName";
         setterName = null;
         currentResult = new PropertyDescriptor("DisplayName", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DisplayName", currentResult);
         currentResult.setValue("description", "<p>Display name of the application context</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("GetBeanCount")) {
         getterName = "getGetBeanCount";
         setterName = null;
         currentResult = new PropertyDescriptor("GetBeanCount", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GetBeanCount", currentResult);
         currentResult.setValue("description", "<p>The number of times getBean() was called</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("GetBeanNamesForTypeCount")) {
         getterName = "getGetBeanNamesForTypeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("GetBeanNamesForTypeCount", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GetBeanNamesForTypeCount", currentResult);
         currentResult.setValue("description", "<p>The number of times getBeanNamesForType() was called</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("GetBeansOfTypeCount")) {
         getterName = "getGetBeansOfTypeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("GetBeansOfTypeCount", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("GetBeansOfTypeCount", currentResult);
         currentResult.setValue("description", "<p>The number of times getBeansOfType() was called</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ParentContext")) {
         getterName = "getParentContext";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentContext", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParentContext", currentResult);
         currentResult.setValue("description", "<p>The name of the parent context</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PrototypeBeansCreatedCount")) {
         getterName = "getPrototypeBeansCreatedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrototypeBeansCreatedCount", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrototypeBeansCreatedCount", currentResult);
         currentResult.setValue("description", "<p>The number of Prototype beans created.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("RefreshCount")) {
         getterName = "getRefreshCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RefreshCount", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RefreshCount", currentResult);
         currentResult.setValue("description", "<p>The number of refreshes performed</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("SingletonBeansCreatedCount")) {
         getterName = "getSingletonBeansCreatedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SingletonBeansCreatedCount", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SingletonBeansCreatedCount", currentResult);
         currentResult.setValue("description", "<p>The number of singleton beans created.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("StartupDate")) {
         getterName = "getStartupDate";
         setterName = null;
         currentResult = new PropertyDescriptor("StartupDate", SpringApplicationContextRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StartupDate", currentResult);
         currentResult.setValue("description", "<p>Return the timestamp in milliseconds when this context was first loaded</p> ");
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
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion)) {
         mth = SpringApplicationContextRuntimeMBean.class.getMethod("getAverageCustomScopeBeanCreationTime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("scopeName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>This returns the average elapsed time in milliseconds required to create custom scope beans</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion)) {
         mth = SpringApplicationContextRuntimeMBean.class.getMethod("getCustomScopeBeansCreatedCount", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("scopeName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>The number of custom scope beans created.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.1.0");
         }
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
