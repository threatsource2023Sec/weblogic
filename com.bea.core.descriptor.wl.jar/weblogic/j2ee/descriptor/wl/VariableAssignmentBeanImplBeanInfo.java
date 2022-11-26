package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class VariableAssignmentBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = VariableAssignmentBean.class;

   public VariableAssignmentBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public VariableAssignmentBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.VariableAssignmentBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML variable-assignmentType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.VariableAssignmentBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", VariableAssignmentBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Gets the \"description\" element ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", VariableAssignmentBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "Gets the \"name\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Operation")) {
         getterName = "getOperation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOperation";
         }

         currentResult = new PropertyDescriptor("Operation", VariableAssignmentBean.class, getterName, setterName);
         descriptors.put("Operation", currentResult);
         currentResult.setValue("description", "The type of operation for this assignment. Only applies to array properties. The default is {@link #ADD} ");
         setPropertyDescriptorDefault(currentResult, "add");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Origin")) {
         getterName = "getOrigin";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrigin";
         }

         currentResult = new PropertyDescriptor("Origin", VariableAssignmentBean.class, getterName, setterName);
         descriptors.put("Origin", currentResult);
         currentResult.setValue("description", "Specifies the DescriptorBean represented by the VariableAssignmentBean is \"created\" in the plan and does not exist in an external descriptor Helpful when trying to determine whether the DescriptorBean is removable from the plan. ");
         setPropertyDescriptorDefault(currentResult, "external");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Xpath")) {
         getterName = "getXpath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXpath";
         }

         currentResult = new PropertyDescriptor("Xpath", VariableAssignmentBean.class, getterName, setterName);
         descriptors.put("Xpath", currentResult);
         currentResult.setValue("description", "Gets the \"xpath\" element ");
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
