package weblogic.t3.srvr;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ClassLoaderRuntimeMBean;

public class ClassLoaderRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClassLoaderRuntimeMBean.class;

   public ClassLoaderRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClassLoaderRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.t3.srvr.ClassLoaderRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.t3.srvr");
      String description = (new String("Provides methods for retrieving runtime information about class loading ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ClassLoaderRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AfterIndexingFindClassCount")) {
         getterName = "getAfterIndexingFindClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("AfterIndexingFindClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AfterIndexingFindClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AfterIndexingFindClassTime")) {
         getterName = "getAfterIndexingFindClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AfterIndexingFindClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AfterIndexingFindClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AfterIndexingLoadClassCount")) {
         getterName = "getAfterIndexingLoadClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("AfterIndexingLoadClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AfterIndexingLoadClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AfterIndexingLoadClassTime")) {
         getterName = "getAfterIndexingLoadClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AfterIndexingLoadClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AfterIndexingLoadClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AfterIndexingResourceCount")) {
         getterName = "getAfterIndexingResourceCount";
         setterName = null;
         currentResult = new PropertyDescriptor("AfterIndexingResourceCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AfterIndexingResourceCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AfterIndexingResourceTime")) {
         getterName = "getAfterIndexingResourceTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AfterIndexingResourceTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AfterIndexingResourceTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeforeIndexingFindClassCount")) {
         getterName = "getBeforeIndexingFindClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BeforeIndexingFindClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeforeIndexingFindClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeforeIndexingFindClassTime")) {
         getterName = "getBeforeIndexingFindClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BeforeIndexingFindClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeforeIndexingFindClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeforeIndexingLoadClassCount")) {
         getterName = "getBeforeIndexingLoadClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BeforeIndexingLoadClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeforeIndexingLoadClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeforeIndexingLoadClassTime")) {
         getterName = "getBeforeIndexingLoadClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BeforeIndexingLoadClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeforeIndexingLoadClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeforeIndexingResourceCount")) {
         getterName = "getBeforeIndexingResourceCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BeforeIndexingResourceCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeforeIndexingResourceCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BeforeIndexingResourceTime")) {
         getterName = "getBeforeIndexingResourceTime";
         setterName = null;
         currentResult = new PropertyDescriptor("BeforeIndexingResourceTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BeforeIndexingResourceTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefineClassCount")) {
         getterName = "getDefineClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DefineClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DefineClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefineClassTime")) {
         getterName = "getDefineClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("DefineClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DefineClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DuringIndexingFindClassCount")) {
         getterName = "getDuringIndexingFindClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DuringIndexingFindClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DuringIndexingFindClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DuringIndexingFindClassTime")) {
         getterName = "getDuringIndexingFindClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("DuringIndexingFindClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DuringIndexingFindClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DuringIndexingLoadClassCount")) {
         getterName = "getDuringIndexingLoadClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DuringIndexingLoadClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DuringIndexingLoadClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DuringIndexingLoadClassTime")) {
         getterName = "getDuringIndexingLoadClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("DuringIndexingLoadClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DuringIndexingLoadClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DuringIndexingResourceCount")) {
         getterName = "getDuringIndexingResourceCount";
         setterName = null;
         currentResult = new PropertyDescriptor("DuringIndexingResourceCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DuringIndexingResourceCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DuringIndexingResourceTime")) {
         getterName = "getDuringIndexingResourceTime";
         setterName = null;
         currentResult = new PropertyDescriptor("DuringIndexingResourceTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DuringIndexingResourceTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FindClassCount")) {
         getterName = "getFindClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FindClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FindClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FindClassTime")) {
         getterName = "getFindClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("FindClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FindClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IndexingTime")) {
         getterName = "getIndexingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("IndexingTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("IndexingTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadClassCount")) {
         getterName = "getLoadClassCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LoadClassCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LoadClassCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadClassTime")) {
         getterName = "getLoadClassTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LoadClassTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LoadClassTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentDelegationCount")) {
         getterName = "getParentDelegationCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentDelegationCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParentDelegationCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParentDelegationTime")) {
         getterName = "getParentDelegationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentDelegationTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ParentDelegationTime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceCount")) {
         getterName = "getResourceCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceCount", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceCount", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceTime")) {
         getterName = "getResourceTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceTime", ClassLoaderRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceTime", currentResult);
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
