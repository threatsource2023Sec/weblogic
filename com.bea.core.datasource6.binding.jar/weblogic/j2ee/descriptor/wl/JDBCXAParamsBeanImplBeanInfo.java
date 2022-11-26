package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCXAParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCXAParamsBean.class;

   public JDBCXAParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCXAParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Contains the XA-related parameters of a data source.  <p> Configuration parameters for a data source's XA-related behavior are specified using a XA parameters bean. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCXAParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("XaRetryDurationSeconds")) {
         getterName = "getXaRetryDurationSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaRetryDurationSeconds";
         }

         currentResult = new PropertyDescriptor("XaRetryDurationSeconds", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("XaRetryDurationSeconds", currentResult);
         currentResult.setValue("description", "Determines the duration in seconds for which the transaction manager will perform recover operations on the resource.  A value of zero indicates that no retries will be performed. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XaRetryIntervalSeconds")) {
         getterName = "getXaRetryIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaRetryIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("XaRetryIntervalSeconds", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("XaRetryIntervalSeconds", currentResult);
         currentResult.setValue("description", "The number of seconds between XA retry operations if XARetryDurationSeconds is set to a positive value. ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("XaTransactionTimeout")) {
         getterName = "getXaTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("XaTransactionTimeout", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("XaTransactionTimeout", currentResult);
         currentResult.setValue("description", "<p>The number of seconds to set as the transaction branch timeout.</p>  <p>If set, this value is passed as the transaction timeout value in the <code>XAResource.setTransactionTimeout()</code> call on the XA resource manager, typically the JDBC driver. </p>  <p>When this value is set to <code>0</code>, the WebLogic Server Transaction Manager passes the global WebLogic Server transaction timeout in seconds in the method.</p>  <p>If set, this value should be greater than or equal to the global WebLogic Server transaction timeout.</p>  <p><b>Note:</b> You must enable XaSetTransactionTimeout to enable setting the transaction branch timeout.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isXaSetTransactionTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepLogicalConnOpenOnRelease")) {
         getterName = "isKeepLogicalConnOpenOnRelease";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepLogicalConnOpenOnRelease";
         }

         currentResult = new PropertyDescriptor("KeepLogicalConnOpenOnRelease", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("KeepLogicalConnOpenOnRelease", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to keep the logical JDBC connection open for a global transaction when the physical XA connection is returned to the connection pool. </p>  <p>Select this option if the XA driver used to create database connections or the DBMS requires that a logical JDBC connection be kept open while transaction processing continues (although the physical XA connection can be returned to the connection pool).</p>  <p>Only applies to data sources that use an XA driver.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepXaConnTillTxComplete")) {
         getterName = "isKeepXaConnTillTxComplete";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepXaConnTillTxComplete";
         }

         currentResult = new PropertyDescriptor("KeepXaConnTillTxComplete", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("KeepXaConnTillTxComplete", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to associate the same XA database connection from the connection pool with a global transaction until the transaction completes.</p>  <p>Only applies to connection pools that use an XA driver.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NeedTxCtxOnClose")) {
         getterName = "isNeedTxCtxOnClose";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNeedTxCtxOnClose";
         }

         currentResult = new PropertyDescriptor("NeedTxCtxOnClose", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("NeedTxCtxOnClose", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the XA driver requires a distributed transaction context when closing various JDBC objects (result sets, statements, connections, and so forth). Only applies to connection pools that use an XA driver.</p>  <p>When enabled, SQL exceptions that are thrown while closing the JDBC objects without a transaction context will be suppressed.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NewXaConnForCommit")) {
         getterName = "isNewXaConnForCommit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNewXaConnForCommit";
         }

         currentResult = new PropertyDescriptor("NewXaConnForCommit", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("NewXaConnForCommit", currentResult);
         currentResult.setValue("description", "<p>Specifies that a dedicated XA connection is used for commit and rollback processing for a global transaction.</p>  <p>Only applies to data sources that use an XA driver.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecoverOnlyOnce")) {
         getterName = "isRecoverOnlyOnce";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRecoverOnlyOnce";
         }

         currentResult = new PropertyDescriptor("RecoverOnlyOnce", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("RecoverOnlyOnce", currentResult);
         currentResult.setValue("description", "<p>Specifies that the transaction manager calls recover on the resource only once. Only applies to data sources that use an XA driver.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceHealthMonitoring")) {
         getterName = "isResourceHealthMonitoring";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceHealthMonitoring";
         }

         currentResult = new PropertyDescriptor("ResourceHealthMonitoring", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("ResourceHealthMonitoring", currentResult);
         currentResult.setValue("description", "<p>Enables JTA resource health monitoring for an XA data source.  When enabled, if an XA resource fails to respond to an XA call within the period specified in MaxXACallMillis, WebLogic Server  marks the data source as unhealthy and blocks any further calls to the resource.</p>  <p> This property applies to XA data sources only, and is ignored for data sources that use a non-XA driver.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RollbackLocalTxUponConnClose")) {
         getterName = "isRollbackLocalTxUponConnClose";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRollbackLocalTxUponConnClose";
         }

         currentResult = new PropertyDescriptor("RollbackLocalTxUponConnClose", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("RollbackLocalTxUponConnClose", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to call <code>rollback()</code> on the connection before returning the connection to the connection pool.</p>  <p>Enabling this attribute will have a performance impact as the rollback call requires communication with the database server.</p>  This option is deprecated.  Its value is currently ignored. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "10.3.4.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XaEndOnlyOnce")) {
         getterName = "isXaEndOnlyOnce";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaEndOnlyOnce";
         }

         currentResult = new PropertyDescriptor("XaEndOnlyOnce", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("XaEndOnlyOnce", currentResult);
         currentResult.setValue("description", "<p>Specifies that <code>XAResource.end()</code> is called only once for each pending <code>XAResource.start()</code>.</p>  <p>This option prevents the XA driver from calling <code>XAResource.end(TMSUSPEND)</code> and <code>XAResource.end(TMSUCCESS)</code> successively. Only applies to data sources that use an XA driver.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p>  This option is deprecated.  Its value is currently ignored. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XaSetTransactionTimeout")) {
         getterName = "isXaSetTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXaSetTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("XaSetTransactionTimeout", JDBCXAParamsBean.class, getterName, setterName);
         descriptors.put("XaSetTransactionTimeout", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to set a transaction branch timeout based on the value for XaTransactionTimeout.</p>  <p>When enabled, the WebLogic Server Transaction Manager calls <code>XAResource.setTransactionTimeout()</code> before calling <code>XAResource.start</code>, and passes either the XA Transaction Timeout value or the global transaction timeout. </p>  <p>You may want to set a transaction branch timeout if you have long-running transactions that exceed the default timeout value on the XA resource.</p>  <p><b>Note:</b> To use this feature, the resource manager (typically, the JDBC driver) must support the <code>javax.transaction.xa.XAResource.setTransactionTimeout()</code> method. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getXaTransactionTimeout")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
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
