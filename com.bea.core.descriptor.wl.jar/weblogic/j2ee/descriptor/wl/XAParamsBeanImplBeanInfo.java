package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class XAParamsBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = XAParamsBean.class;

   public XAParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XAParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.XAParamsBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.XAParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DebugLevel")) {
         getterName = "getDebugLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDebugLevel";
         }

         currentResult = new PropertyDescriptor("DebugLevel", XAParamsBean.class, getterName, setterName);
         descriptors.put("DebugLevel", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PreparedStatementCacheSize")) {
         getterName = "getPreparedStatementCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPreparedStatementCacheSize";
         }

         currentResult = new PropertyDescriptor("PreparedStatementCacheSize", XAParamsBean.class, getterName, setterName);
         descriptors.put("PreparedStatementCacheSize", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XaRetryDurationSeconds")) {
         getterName = "getXaRetryDurationSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaRetryDurationSeconds";
         }

         currentResult = new PropertyDescriptor("XaRetryDurationSeconds", XAParamsBean.class, getterName, setterName);
         descriptors.put("XaRetryDurationSeconds", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XaTransactionTimeout")) {
         getterName = "getXaTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("XaTransactionTimeout", XAParamsBean.class, getterName, setterName);
         descriptors.put("XaTransactionTimeout", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EndOnlyOnceEnabled")) {
         getterName = "isEndOnlyOnceEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEndOnlyOnceEnabled";
         }

         currentResult = new PropertyDescriptor("EndOnlyOnceEnabled", XAParamsBean.class, getterName, setterName);
         descriptors.put("EndOnlyOnceEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepConnUntilTxCompleteEnabled")) {
         getterName = "isKeepConnUntilTxCompleteEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepConnUntilTxCompleteEnabled";
         }

         currentResult = new PropertyDescriptor("KeepConnUntilTxCompleteEnabled", XAParamsBean.class, getterName, setterName);
         descriptors.put("KeepConnUntilTxCompleteEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepLogicalConnOpenOnRelease")) {
         getterName = "isKeepLogicalConnOpenOnRelease";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepLogicalConnOpenOnRelease";
         }

         currentResult = new PropertyDescriptor("KeepLogicalConnOpenOnRelease", XAParamsBean.class, getterName, setterName);
         descriptors.put("KeepLogicalConnOpenOnRelease", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalTransactionSupported")) {
         getterName = "isLocalTransactionSupported";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalTransactionSupported";
         }

         currentResult = new PropertyDescriptor("LocalTransactionSupported", XAParamsBean.class, getterName, setterName);
         descriptors.put("LocalTransactionSupported", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NewConnForCommitEnabled")) {
         getterName = "isNewConnForCommitEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNewConnForCommitEnabled";
         }

         currentResult = new PropertyDescriptor("NewConnForCommitEnabled", XAParamsBean.class, getterName, setterName);
         descriptors.put("NewConnForCommitEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecoverOnlyOnceEnabled")) {
         getterName = "isRecoverOnlyOnceEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecoverOnlyOnceEnabled";
         }

         currentResult = new PropertyDescriptor("RecoverOnlyOnceEnabled", XAParamsBean.class, getterName, setterName);
         descriptors.put("RecoverOnlyOnceEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceHealthMonitoringEnabled")) {
         getterName = "isResourceHealthMonitoringEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceHealthMonitoringEnabled";
         }

         currentResult = new PropertyDescriptor("ResourceHealthMonitoringEnabled", XAParamsBean.class, getterName, setterName);
         descriptors.put("ResourceHealthMonitoringEnabled", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RollbackLocaltxUponConnclose")) {
         getterName = "isRollbackLocaltxUponConnclose";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRollbackLocaltxUponConnclose";
         }

         currentResult = new PropertyDescriptor("RollbackLocaltxUponConnclose", XAParamsBean.class, getterName, setterName);
         descriptors.put("RollbackLocaltxUponConnclose", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TxContextOnCloseNeeded")) {
         getterName = "isTxContextOnCloseNeeded";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTxContextOnCloseNeeded";
         }

         currentResult = new PropertyDescriptor("TxContextOnCloseNeeded", XAParamsBean.class, getterName, setterName);
         descriptors.put("TxContextOnCloseNeeded", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XaSetTransactionTimeout")) {
         getterName = "isXaSetTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaSetTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("XaSetTransactionTimeout", XAParamsBean.class, getterName, setterName);
         descriptors.put("XaSetTransactionTimeout", currentResult);
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
