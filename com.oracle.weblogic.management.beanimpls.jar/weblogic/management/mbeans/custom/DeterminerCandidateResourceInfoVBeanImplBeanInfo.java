package weblogic.management.mbeans.custom;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.configuration.DeterminerCandidateResourceInfoVBean;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class DeterminerCandidateResourceInfoVBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = DeterminerCandidateResourceInfoVBean.class;

   public DeterminerCandidateResourceInfoVBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DeterminerCandidateResourceInfoVBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeans.custom.DeterminerCandidateResourceInfoVBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("valueObject", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.mbeans.custom");
      String description = (new String("DeterminerCandidateResourceInfoVBean interface ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DeterminerCandidateResourceInfoVBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DisplayName")) {
         getterName = "getDisplayName";
         setterName = null;
         currentResult = new PropertyDescriptor("DisplayName", DeterminerCandidateResourceInfoVBean.class, getterName, (String)setterName);
         descriptors.put("DisplayName", currentResult);
         currentResult.setValue("description", "<p>display name</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InternalName")) {
         getterName = "getInternalName";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalName", DeterminerCandidateResourceInfoVBean.class, getterName, (String)setterName);
         descriptors.put("InternalName", currentResult);
         currentResult.setValue("description", "<p>internal name</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceType")) {
         getterName = "getResourceType";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceType", DeterminerCandidateResourceInfoVBean.class, getterName, (String)setterName);
         descriptors.put("ResourceType", currentResult);
         currentResult.setValue("description", "<p>resource type</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Determiner")) {
         getterName = "isDeterminer";
         setterName = null;
         currentResult = new PropertyDescriptor("Determiner", DeterminerCandidateResourceInfoVBean.class, getterName, (String)setterName);
         descriptors.put("Determiner", currentResult);
         currentResult.setValue("description", "<p>determiner indicator</p> ");
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
