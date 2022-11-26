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

public class ModuleDescriptorBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ModuleDescriptorBean.class;

   public ModuleDescriptorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ModuleDescriptorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ModuleDescriptorBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML module-descriptorType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ModuleDescriptorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("HashCode")) {
         getterName = "getHashCode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHashCode";
         }

         currentResult = new PropertyDescriptor("HashCode", ModuleDescriptorBean.class, getterName, setterName);
         descriptors.put("HashCode", currentResult);
         currentResult.setValue("description", "Gets the \"hash-code\" element ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RootElement")) {
         getterName = "getRootElement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRootElement";
         }

         currentResult = new PropertyDescriptor("RootElement", ModuleDescriptorBean.class, getterName, setterName);
         descriptors.put("RootElement", currentResult);
         currentResult.setValue("description", "Gets the \"root-element\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Uri")) {
         getterName = "getUri";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUri";
         }

         currentResult = new PropertyDescriptor("Uri", ModuleDescriptorBean.class, getterName, setterName);
         descriptors.put("Uri", currentResult);
         currentResult.setValue("description", "Gets the \"uri\" element ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VariableAssignments")) {
         getterName = "getVariableAssignments";
         setterName = null;
         currentResult = new PropertyDescriptor("VariableAssignments", ModuleDescriptorBean.class, getterName, setterName);
         descriptors.put("VariableAssignments", currentResult);
         currentResult.setValue("description", "Gets array of all \"variable-declaration\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createVariableAssignment");
         currentResult.setValue("destroyer", "destroyVariableAssignment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Changed")) {
         getterName = "isChanged";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setChanged";
         }

         currentResult = new PropertyDescriptor("Changed", ModuleDescriptorBean.class, getterName, setterName);
         descriptors.put("Changed", currentResult);
         currentResult.setValue("description", "Internally indicates whether this module descriptor requires an update ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("External")) {
         getterName = "isExternal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExternal";
         }

         currentResult = new PropertyDescriptor("External", ModuleDescriptorBean.class, getterName, setterName);
         descriptors.put("External", currentResult);
         currentResult.setValue("description", "Indicates whether this descriptor resides outside the archive. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ModuleDescriptorBean.class.getMethod("createVariableAssignment");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "VariableAssignments");
      }

      mth = ModuleDescriptorBean.class.getMethod("destroyVariableAssignment", VariableAssignmentBean.class);
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
