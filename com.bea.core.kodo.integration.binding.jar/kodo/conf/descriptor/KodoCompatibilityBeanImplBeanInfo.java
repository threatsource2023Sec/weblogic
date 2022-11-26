package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class KodoCompatibilityBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = KodoCompatibilityBean.class;

   public KodoCompatibilityBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KodoCompatibilityBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.KodoCompatibilityBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.KodoCompatibilityBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CloseOnManagedCommit")) {
         getterName = "getCloseOnManagedCommit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCloseOnManagedCommit";
         }

         currentResult = new PropertyDescriptor("CloseOnManagedCommit", KodoCompatibilityBean.class, getterName, setterName);
         descriptors.put("CloseOnManagedCommit", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CopyObjectIds")) {
         getterName = "getCopyObjectIds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCopyObjectIds";
         }

         currentResult = new PropertyDescriptor("CopyObjectIds", KodoCompatibilityBean.class, getterName, setterName);
         descriptors.put("CopyObjectIds", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QuotedNumbersInQueries")) {
         getterName = "getQuotedNumbersInQueries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQuotedNumbersInQueries";
         }

         currentResult = new PropertyDescriptor("QuotedNumbersInQueries", KodoCompatibilityBean.class, getterName, setterName);
         descriptors.put("QuotedNumbersInQueries", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StrictIdentityValues")) {
         getterName = "getStrictIdentityValues";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStrictIdentityValues";
         }

         currentResult = new PropertyDescriptor("StrictIdentityValues", KodoCompatibilityBean.class, getterName, setterName);
         descriptors.put("StrictIdentityValues", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidateFalseReturnsHollow")) {
         getterName = "getValidateFalseReturnsHollow";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidateFalseReturnsHollow";
         }

         currentResult = new PropertyDescriptor("ValidateFalseReturnsHollow", KodoCompatibilityBean.class, getterName, setterName);
         descriptors.put("ValidateFalseReturnsHollow", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidateTrueChecksStore")) {
         getterName = "getValidateTrueChecksStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidateTrueChecksStore";
         }

         currentResult = new PropertyDescriptor("ValidateTrueChecksStore", KodoCompatibilityBean.class, getterName, setterName);
         descriptors.put("ValidateTrueChecksStore", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
