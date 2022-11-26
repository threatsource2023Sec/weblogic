package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;
import weblogic.management.mbeanservers.edit.Change;

public class ChangeImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = Change.class;

   public ChangeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ChangeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.edit.internal.ChangeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("valueObject", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.edit.internal");
      String description = (new String("Describes a single change to the configuration. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.edit.Change");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AffectedBean")) {
         getterName = "getAffectedBean";
         setterName = null;
         currentResult = new PropertyDescriptor("AffectedBean", Change.class, getterName, (String)setterName);
         descriptors.put("AffectedBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("AttributeName")) {
         getterName = "getAttributeName";
         setterName = null;
         currentResult = new PropertyDescriptor("AttributeName", Change.class, getterName, (String)setterName);
         descriptors.put("AttributeName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Bean")) {
         getterName = "getBean";
         setterName = null;
         currentResult = new PropertyDescriptor("Bean", Change.class, getterName, (String)setterName);
         descriptors.put("Bean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EntityToRestart")) {
         getterName = "getEntityToRestart";
         setterName = null;
         currentResult = new PropertyDescriptor("EntityToRestart", Change.class, getterName, (String)setterName);
         descriptors.put("EntityToRestart", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("NewValue")) {
         getterName = "getNewValue";
         setterName = null;
         currentResult = new PropertyDescriptor("NewValue", Change.class, getterName, (String)setterName);
         descriptors.put("NewValue", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("OldValue")) {
         getterName = "getOldValue";
         setterName = null;
         currentResult = new PropertyDescriptor("OldValue", Change.class, getterName, (String)setterName);
         descriptors.put("OldValue", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      if (!descriptors.containsKey("Operation")) {
         getterName = "getOperation";
         setterName = null;
         currentResult = new PropertyDescriptor("Operation", Change.class, getterName, (String)setterName);
         descriptors.put("Operation", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "modify");
         currentResult.setValue("legalValues", new Object[]{"modify", "create", "destroy", "add", "remove", "unset"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestartRequired")) {
         getterName = "isRestartRequired";
         setterName = null;
         currentResult = new PropertyDescriptor("RestartRequired", Change.class, getterName, (String)setterName);
         descriptors.put("RestartRequired", currentResult);
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
