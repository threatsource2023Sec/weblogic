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

public class ConfigResourceOverrideBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ConfigResourceOverrideBean.class;

   public ConfigResourceOverrideBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConfigResourceOverrideBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ConfigResourceOverrideBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML config-resource-overrideType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ConfigResourceOverrideBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ResourceName")) {
         getterName = "getResourceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceName";
         }

         currentResult = new PropertyDescriptor("ResourceName", ConfigResourceOverrideBean.class, getterName, setterName);
         descriptors.put("ResourceName", currentResult);
         currentResult.setValue("description", "Gets the \"resource-name\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceType")) {
         getterName = "getResourceType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceType";
         }

         currentResult = new PropertyDescriptor("ResourceType", ConfigResourceOverrideBean.class, getterName, setterName);
         descriptors.put("ResourceType", currentResult);
         currentResult.setValue("description", "Gets the \"resource-type\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VariableAssignments")) {
         getterName = "getVariableAssignments";
         setterName = null;
         currentResult = new PropertyDescriptor("VariableAssignments", ConfigResourceOverrideBean.class, getterName, setterName);
         descriptors.put("VariableAssignments", currentResult);
         currentResult.setValue("description", "Gets array of all \"variable-declaration\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createVariableAssignment");
         currentResult.setValue("destroyer", "destroyVariableAssignment");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConfigResourceOverrideBean.class.getMethod("createVariableAssignment");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "VariableAssignments");
      }

      mth = ConfigResourceOverrideBean.class.getMethod("destroyVariableAssignment", VariableAssignmentBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "VariableAssignments");
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
