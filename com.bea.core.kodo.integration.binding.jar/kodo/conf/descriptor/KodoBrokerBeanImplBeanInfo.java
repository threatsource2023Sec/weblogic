package kodo.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class KodoBrokerBeanImplBeanInfo extends BrokerImplBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KodoBrokerBean.class;

   public KodoBrokerBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KodoBrokerBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.conf.descriptor.KodoBrokerBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.conf.descriptor.KodoBrokerBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AutoClear")) {
         getterName = "getAutoClear";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoClear";
         }

         currentResult = new PropertyDescriptor("AutoClear", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("AutoClear", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoDetach")) {
         getterName = "getAutoDetach";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoDetach";
         }

         currentResult = new PropertyDescriptor("AutoDetach", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("AutoDetach", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachState")) {
         getterName = "getDetachState";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachState";
         }

         currentResult = new PropertyDescriptor("DetachState", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("DetachState", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachedNew")) {
         getterName = "getDetachedNew";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDetachedNew";
         }

         currentResult = new PropertyDescriptor("DetachedNew", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("DetachedNew", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EvictFromDataCache")) {
         getterName = "getEvictFromDataCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEvictFromDataCache";
         }

         currentResult = new PropertyDescriptor("EvictFromDataCache", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("EvictFromDataCache", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreChanges")) {
         getterName = "getIgnoreChanges";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreChanges";
         }

         currentResult = new PropertyDescriptor("IgnoreChanges", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("IgnoreChanges", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LargeTransaction")) {
         getterName = "getLargeTransaction";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLargeTransaction";
         }

         currentResult = new PropertyDescriptor("LargeTransaction", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("LargeTransaction", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Multithreaded")) {
         getterName = "getMultithreaded";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMultithreaded";
         }

         currentResult = new PropertyDescriptor("Multithreaded", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("Multithreaded", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NontransactionalRead")) {
         getterName = "getNontransactionalRead";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNontransactionalRead";
         }

         currentResult = new PropertyDescriptor("NontransactionalRead", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("NontransactionalRead", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NontransactionalWrite")) {
         getterName = "getNontransactionalWrite";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNontransactionalWrite";
         }

         currentResult = new PropertyDescriptor("NontransactionalWrite", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("NontransactionalWrite", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Optimistic")) {
         getterName = "getOptimistic";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOptimistic";
         }

         currentResult = new PropertyDescriptor("Optimistic", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("Optimistic", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OrderDirtyObjects")) {
         getterName = "getOrderDirtyObjects";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOrderDirtyObjects";
         }

         currentResult = new PropertyDescriptor("OrderDirtyObjects", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("OrderDirtyObjects", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PopulateDataCache")) {
         getterName = "getPopulateDataCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPopulateDataCache";
         }

         currentResult = new PropertyDescriptor("PopulateDataCache", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("PopulateDataCache", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestoreState")) {
         getterName = "getRestoreState";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestoreState";
         }

         currentResult = new PropertyDescriptor("RestoreState", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("RestoreState", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetainState")) {
         getterName = "getRetainState";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetainState";
         }

         currentResult = new PropertyDescriptor("RetainState", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("RetainState", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SyncWithManagedTransactions")) {
         getterName = "getSyncWithManagedTransactions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSyncWithManagedTransactions";
         }

         currentResult = new PropertyDescriptor("SyncWithManagedTransactions", KodoBrokerBean.class, getterName, setterName);
         descriptors.put("SyncWithManagedTransactions", currentResult);
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
