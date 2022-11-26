package weblogic.management.utils;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ListerMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ListerMBean.class;

   public ListerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ListerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.utils.ListerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.utils");
      String description = (new String("<p>The ListerMBean interface provides a general mechanism for returning lists.</p>  <p>Derived MBeans extend this interface to add methods that access the data of the current object in the list. This is different from the iterator model (where the next method returns the next object in the list) because creating MBeans for each object returned in a list (for example, one MBean per user) is too expensive. In addition, it is not appropriate to return non-MBean or non-primitive types from an MBean.</p>  <p>The general iteration pattern is as follows:</p>  <p><code> FooListerMBean fooLister = ...;<br> String cursor = lister.listFoo(...);<br> try {<br> while (fooLister.haveCurrent(cursor)) {<br> Foo currentVal = fooLister.getCurrentFoo(cursor);<br> fooLister.advance(cursor);<br> }<br> } finally { <br> fooLister.close(cursor); </code></p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.utils.ListerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ListerMBean.class.getMethod("haveCurrent", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor returned from a previous list method. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns true if there are more objects in the list, and false otherwise.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ListerMBean.class.getMethod("advance", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor returned from a previous list method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Advances the list to the next element in the list.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ListerMBean.class.getMethod("close", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cursor", "- The cursor returned from a previous list method. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Indicates that the caller is finished using the list, and that the resources held on behalf of the list may be released.  If the caller traverses through all the elements in the list, the caller need not call this method. In other words, it is used to let the caller close the list without reading each element that is returned.</p> ");
         currentResult.setValue("role", "operation");
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
