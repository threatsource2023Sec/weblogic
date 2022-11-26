package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class TransactionLogJDBCStoreMBeanImplBeanInfo extends JDBCStoreMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TransactionLogJDBCStoreMBean.class;

   public TransactionLogJDBCStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TransactionLogJDBCStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.TransactionLogJDBCStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.6.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This class represents a Transaction Log JDBC Store configuration.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.TransactionLogJDBCStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DataSource")) {
         getterName = "getDataSource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSource";
         }

         currentResult = new PropertyDescriptor("DataSource", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("DataSource", currentResult);
         currentResult.setValue("description", "<p>The JDBC data source used by this JDBC store to access its backing table.</p>  <p>The specified data source must use a non-XA JDBC driver since connection pools for XA JDBC drivers are not supported.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
      }

      if (!descriptors.containsKey("MaxRetrySecondsBeforeTLOGFail")) {
         getterName = "getMaxRetrySecondsBeforeTLOGFail";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRetrySecondsBeforeTLOGFail";
         }

         currentResult = new PropertyDescriptor("MaxRetrySecondsBeforeTLOGFail", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("MaxRetrySecondsBeforeTLOGFail", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time, in seconds, WebLogic Server tries to recover from a JDBC TLog store failure. If store remains unusable after this period, WebLogic Server set the health state to <code>HEALTH_FAILED</code>. A value of 0 indicates WebLogic Server does not conduct a retry and and immediately sets the health state as <code>HEALTH_FAILED</code>. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRetrySecondsBeforeTXException")) {
         getterName = "getMaxRetrySecondsBeforeTXException";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRetrySecondsBeforeTXException";
         }

         currentResult = new PropertyDescriptor("MaxRetrySecondsBeforeTXException", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("MaxRetrySecondsBeforeTXException", currentResult);
         currentResult.setValue("description", "<p>The maximum amount of time, in seconds, WebLogic Server waits before trying to recover from a JDBC TLog store failure while processing a transaction. If store remains unusable after this amount of time, WebLogic Server throws an exception the affected transaction. A value of 0 indicates WebLogic Server does not conduct a retry and an exception will thrown immediately. The practical maximum value is a value less than the current value of <code>MaxRetrySecondsBeforeTLogFail</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMax", new Integer(300));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PrefixName")) {
         getterName = "getPrefixName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPrefixName";
         }

         currentResult = new PropertyDescriptor("PrefixName", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("PrefixName", currentResult);
         currentResult.setValue("description", "<p>When using multiple TLOG JDBC stores, use this attribute to create a label ending in \"_\"  that is prepended to the name of the server hosting the JDBC TLOG store and ends in \"_\" to form a unique JDBC TLOG store name for each configured JDBC TLOG store. </p> The default prefix name is \"TLOG_\" . For example, a valid JDBC TLOG store name using the default Prefix Name is <code>TLOG_MyServer_ </code> where TLOG_ is the Prefix Name and MyServer is the name of the server hosting the JDBC TLOG store. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryIntervalSeconds")) {
         getterName = "getRetryIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("RetryIntervalSeconds", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("RetryIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p>The amount of time, in seconds, WebLogic Server waits before attempting to verify the health of the TLOG store after a store failure has occurred. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(60));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", TransactionLogJDBCStoreMBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>When true, TLOGs are logged to a TLOG JDBC Store; otherwise, TLOGs are logged to the server's default store.</p> <p>When using the Administration Console, select <b>JDBC</b> to enable logging of TLOGs to a JDBC store; select <b>Default Store</b> to enable logging of TLOGs to the server's default store. </p> ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = TransactionLogJDBCStoreMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = TransactionLogJDBCStoreMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = TransactionLogJDBCStoreMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = TransactionLogJDBCStoreMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

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
