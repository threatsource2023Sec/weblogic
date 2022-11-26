package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class CompatibilityBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = CompatibilityBean.class;

   public CompatibilityBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CompatibilityBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.CompatibilityBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.CompatibilityBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", CompatibilityBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AllowReadonlyCreateAndRemove")) {
         getterName = "isAllowReadonlyCreateAndRemove";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAllowReadonlyCreateAndRemove";
         }

         currentResult = new PropertyDescriptor("AllowReadonlyCreateAndRemove", CompatibilityBean.class, getterName, setterName);
         descriptors.put("AllowReadonlyCreateAndRemove", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DisableStringTrimming")) {
         getterName = "isDisableStringTrimming";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDisableStringTrimming";
         }

         currentResult = new PropertyDescriptor("DisableStringTrimming", CompatibilityBean.class, getterName, setterName);
         descriptors.put("DisableStringTrimming", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FindersReturnNulls")) {
         getterName = "isFindersReturnNulls";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFindersReturnNulls";
         }

         currentResult = new PropertyDescriptor("FindersReturnNulls", CompatibilityBean.class, getterName, setterName);
         descriptors.put("FindersReturnNulls", currentResult);
         currentResult.setValue("description", "http://bugs.bea.com/WebClarify/CRView?CR=CR200747 ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadRelatedBeansFromDbInPostCreate")) {
         getterName = "isLoadRelatedBeansFromDbInPostCreate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadRelatedBeansFromDbInPostCreate";
         }

         currentResult = new PropertyDescriptor("LoadRelatedBeansFromDbInPostCreate", CompatibilityBean.class, getterName, setterName);
         descriptors.put("LoadRelatedBeansFromDbInPostCreate", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SerializeByteArrayToOracleBlob")) {
         getterName = "isSerializeByteArrayToOracleBlob";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSerializeByteArrayToOracleBlob";
         }

         currentResult = new PropertyDescriptor("SerializeByteArrayToOracleBlob", CompatibilityBean.class, getterName, setterName);
         descriptors.put("SerializeByteArrayToOracleBlob", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SerializeCharArrayToBytes")) {
         getterName = "isSerializeCharArrayToBytes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSerializeCharArrayToBytes";
         }

         currentResult = new PropertyDescriptor("SerializeCharArrayToBytes", CompatibilityBean.class, getterName, setterName);
         descriptors.put("SerializeCharArrayToBytes", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
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
