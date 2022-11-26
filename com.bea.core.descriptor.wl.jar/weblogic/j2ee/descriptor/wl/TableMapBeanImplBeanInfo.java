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

public class TableMapBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = TableMapBean.class;

   public TableMapBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TableMapBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.TableMapBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.TableMapBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FieldMaps")) {
         getterName = "getFieldMaps";
         setterName = null;
         currentResult = new PropertyDescriptor("FieldMaps", TableMapBean.class, getterName, setterName);
         descriptors.put("FieldMaps", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFieldMap");
         currentResult.setValue("destroyer", "destroyFieldMap");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", TableMapBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OptimisticColumn")) {
         getterName = "getOptimisticColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOptimisticColumn";
         }

         currentResult = new PropertyDescriptor("OptimisticColumn", TableMapBean.class, getterName, setterName);
         descriptors.put("OptimisticColumn", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", TableMapBean.class, getterName, setterName);
         descriptors.put("TableName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VerifyColumns")) {
         getterName = "getVerifyColumns";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVerifyColumns";
         }

         currentResult = new PropertyDescriptor("VerifyColumns", TableMapBean.class, getterName, setterName);
         descriptors.put("VerifyColumns", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"Read", "Modified", "Version", "Timestamp"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VerifyRows")) {
         getterName = "getVerifyRows";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVerifyRows";
         }

         currentResult = new PropertyDescriptor("VerifyRows", TableMapBean.class, getterName, setterName);
         descriptors.put("VerifyRows", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"Modified", "Read"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VersionColumnInitialValue")) {
         getterName = "getVersionColumnInitialValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVersionColumnInitialValue";
         }

         currentResult = new PropertyDescriptor("VersionColumnInitialValue", TableMapBean.class, getterName, setterName);
         descriptors.put("VersionColumnInitialValue", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TriggerUpdatesOptimisticColumn")) {
         getterName = "isTriggerUpdatesOptimisticColumn";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTriggerUpdatesOptimisticColumn";
         }

         currentResult = new PropertyDescriptor("TriggerUpdatesOptimisticColumn", TableMapBean.class, getterName, setterName);
         descriptors.put("TriggerUpdatesOptimisticColumn", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = TableMapBean.class.getMethod("createFieldMap");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FieldMaps");
      }

      mth = TableMapBean.class.getMethod("destroyFieldMap", FieldMapBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FieldMaps");
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
