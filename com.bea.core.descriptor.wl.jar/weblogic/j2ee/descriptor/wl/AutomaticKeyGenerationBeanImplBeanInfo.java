package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class AutomaticKeyGenerationBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = AutomaticKeyGenerationBean.class;

   public AutomaticKeyGenerationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AutomaticKeyGenerationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.AutomaticKeyGenerationBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.AutomaticKeyGenerationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("GeneratorName")) {
         getterName = "getGeneratorName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGeneratorName";
         }

         currentResult = new PropertyDescriptor("GeneratorName", AutomaticKeyGenerationBean.class, getterName, setterName);
         descriptors.put("GeneratorName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GeneratorType")) {
         getterName = "getGeneratorType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGeneratorType";
         }

         currentResult = new PropertyDescriptor("GeneratorType", AutomaticKeyGenerationBean.class, getterName, setterName);
         descriptors.put("GeneratorType", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"Identity", "Sequence", "SequenceTable"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", AutomaticKeyGenerationBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeyCacheSize")) {
         getterName = "getKeyCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeyCacheSize";
         }

         currentResult = new PropertyDescriptor("KeyCacheSize", AutomaticKeyGenerationBean.class, getterName, setterName);
         descriptors.put("KeyCacheSize", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SelectFirstSequenceKeyBeforeUpdate")) {
         getterName = "getSelectFirstSequenceKeyBeforeUpdate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSelectFirstSequenceKeyBeforeUpdate";
         }

         currentResult = new PropertyDescriptor("SelectFirstSequenceKeyBeforeUpdate", AutomaticKeyGenerationBean.class, getterName, setterName);
         descriptors.put("SelectFirstSequenceKeyBeforeUpdate", currentResult);
         currentResult.setValue("description", " ");
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
