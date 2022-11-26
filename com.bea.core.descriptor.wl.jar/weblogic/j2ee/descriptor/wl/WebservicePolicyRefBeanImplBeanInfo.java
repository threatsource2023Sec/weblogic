package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class WebservicePolicyRefBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WebservicePolicyRefBean.class;

   public WebservicePolicyRefBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebservicePolicyRefBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.WebservicePolicyRefBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML webservice-policy-refType(@http://xmlns.oracle.com/weblogic/webservice-policy-ref). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.WebservicePolicyRefBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("OperationPolicy")) {
         getterName = "getOperationPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("OperationPolicy", WebservicePolicyRefBean.class, getterName, setterName);
         descriptors.put("OperationPolicy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOperationPolicy");
         currentResult.setValue("creator", "createOperationPolicy");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortPolicy")) {
         getterName = "getPortPolicy";
         setterName = null;
         currentResult = new PropertyDescriptor("PortPolicy", WebservicePolicyRefBean.class, getterName, setterName);
         descriptors.put("PortPolicy", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPortPolicy");
         currentResult.setValue("destroyer", "destroyPortPolicy");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RefName")) {
         getterName = "getRefName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRefName";
         }

         currentResult = new PropertyDescriptor("RefName", WebservicePolicyRefBean.class, getterName, setterName);
         descriptors.put("RefName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersion";
         }

         currentResult = new PropertyDescriptor("Version", WebservicePolicyRefBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "Gets the \"version\" attribute ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebservicePolicyRefBean.class.getMethod("createPortPolicy");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PortPolicy");
      }

      mth = WebservicePolicyRefBean.class.getMethod("destroyPortPolicy", PortPolicyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PortPolicy");
      }

      mth = WebservicePolicyRefBean.class.getMethod("createOperationPolicy");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OperationPolicy");
      }

      mth = WebservicePolicyRefBean.class.getMethod("destroyOperationPolicy", OperationPolicyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OperationPolicy");
      }

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
