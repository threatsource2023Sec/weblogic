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

public class ModuleOverrideBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ModuleOverrideBean.class;

   public ModuleOverrideBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ModuleOverrideBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ModuleOverrideBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML module-overrideType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ModuleOverrideBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ModuleDescriptors")) {
         getterName = "getModuleDescriptors";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleDescriptors", ModuleOverrideBean.class, getterName, setterName);
         descriptors.put("ModuleDescriptors", currentResult);
         currentResult.setValue("description", "Gets array of all \"module-descriptor\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createModuleDescriptor");
         currentResult.setValue("destroyer", "destroyModuleDescriptor");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleName")) {
         getterName = "getModuleName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setModuleName";
         }

         currentResult = new PropertyDescriptor("ModuleName", ModuleOverrideBean.class, getterName, setterName);
         descriptors.put("ModuleName", currentResult);
         currentResult.setValue("description", "Gets the \"module-name\" element ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleType")) {
         getterName = "getModuleType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setModuleType";
         }

         currentResult = new PropertyDescriptor("ModuleType", ModuleOverrideBean.class, getterName, setterName);
         descriptors.put("ModuleType", currentResult);
         currentResult.setValue("description", "Gets the \"module-type\" element ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ModuleOverrideBean.class.getMethod("createModuleDescriptor");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ModuleDescriptors");
      }

      mth = ModuleOverrideBean.class.getMethod("destroyModuleDescriptor", ModuleDescriptorBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ModuleDescriptors");
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
