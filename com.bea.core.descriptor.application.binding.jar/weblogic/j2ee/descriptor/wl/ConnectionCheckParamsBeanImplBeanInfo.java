package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ConnectionCheckParamsBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ConnectionCheckParamsBean.class;

   public ConnectionCheckParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionCheckParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionCreationRetryFrequencySeconds")) {
         getterName = "getConnectionCreationRetryFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionCreationRetryFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ConnectionCreationRetryFrequencySeconds", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionCreationRetryFrequencySeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionReserveTimeoutSeconds")) {
         getterName = "getConnectionReserveTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionReserveTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("ConnectionReserveTimeoutSeconds", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionReserveTimeoutSeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InactiveConnectionTimeoutSeconds")) {
         getterName = "getInactiveConnectionTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInactiveConnectionTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("InactiveConnectionTimeoutSeconds", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("InactiveConnectionTimeoutSeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitSql")) {
         getterName = "getInitSql";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitSql";
         }

         currentResult = new PropertyDescriptor("InitSql", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("InitSql", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RefreshMinutes")) {
         getterName = "getRefreshMinutes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRefreshMinutes";
         }

         currentResult = new PropertyDescriptor("RefreshMinutes", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("RefreshMinutes", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableName")) {
         getterName = "getTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTableName";
         }

         currentResult = new PropertyDescriptor("TableName", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("TableName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestFrequencySeconds")) {
         getterName = "getTestFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("TestFrequencySeconds", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("TestFrequencySeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CheckOnCreateEnabled")) {
         getterName = "isCheckOnCreateEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCheckOnCreateEnabled";
         }

         currentResult = new PropertyDescriptor("CheckOnCreateEnabled", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("CheckOnCreateEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CheckOnReleaseEnabled")) {
         getterName = "isCheckOnReleaseEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCheckOnReleaseEnabled";
         }

         currentResult = new PropertyDescriptor("CheckOnReleaseEnabled", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("CheckOnReleaseEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CheckOnReserveEnabled")) {
         getterName = "isCheckOnReserveEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCheckOnReserveEnabled";
         }

         currentResult = new PropertyDescriptor("CheckOnReserveEnabled", ConnectionCheckParamsBean.class, getterName, setterName);
         descriptors.put("CheckOnReserveEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
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
