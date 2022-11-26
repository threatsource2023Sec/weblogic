package weblogic.transaction.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import javax.transaction.xa.Xid;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JTARuntimeMBean;

public class JTARuntimeImplBeanInfo extends JTATransactionStatisticsImplBeanInfo {
   public static final Class INTERFACE_CLASS = JTARuntimeMBean.class;

   public JTARuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JTARuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.transaction.internal.JTARuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.transaction.internal");
      String description = (new String("This interface is used for accessing transaction runtime characteristics within a WebLogic server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JTARuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveTransactionsTotalCount")) {
         getterName = "getActiveTransactionsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveTransactionsTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveTransactionsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of active transactions on the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DBPassiveModeState")) {
         getterName = "getDBPassiveModeState";
         setterName = null;
         currentResult = new PropertyDescriptor("DBPassiveModeState", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DBPassiveModeState", currentResult);
         currentResult.setValue("description", "Returns the JTA Passive Mode state as a string.  The state values are (ACTIVE, PASSIVE, ACTIVATING, PASSIVATING) ");
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of the JTA subsystem. for state values.  </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JTATransactions")) {
         getterName = "getJTATransactions";
         setterName = null;
         currentResult = new PropertyDescriptor("JTATransactions", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JTATransactions", currentResult);
         currentResult.setValue("description", "<p>An array of <code>JTATransaction</code> objects. Each object provides detailed information regarding an active transaction.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for JTATransaction");
      }

      if (!descriptors.containsKey("NonXAResourceRuntimeMBeans")) {
         getterName = "getNonXAResourceRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("NonXAResourceRuntimeMBeans", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NonXAResourceRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>An array of <code>NonXAResourceRuntimeMBeans</code> that each represents the statistics for a non-XA resource.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.NonXAResourceRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecoveryRuntimeMBeans")) {
         getterName = "getRecoveryRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("RecoveryRuntimeMBeans", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecoveryRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>Returns the runtime MBeans for the Transaction Recovery Services that were deployed on this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegisteredNonXAResourceNames")) {
         getterName = "getRegisteredNonXAResourceNames";
         setterName = null;
         currentResult = new PropertyDescriptor("RegisteredNonXAResourceNames", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RegisteredNonXAResourceNames", currentResult);
         currentResult.setValue("description", "<p>An array of NonXA resource names that are registered with the transaction manager.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegisteredResourceNames")) {
         getterName = "getRegisteredResourceNames";
         setterName = null;
         currentResult = new PropertyDescriptor("RegisteredResourceNames", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RegisteredResourceNames", currentResult);
         currentResult.setValue("description", "<p>An array of XA resource names that are registered with the transaction manager.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondsActiveTotalCount")) {
         getterName = "getSecondsActiveTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondsActiveTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondsActiveTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of seconds that transactions were active for all committed transactions.</p> ");
      }

      if (!descriptors.containsKey("TransactionAbandonedTotalCount")) {
         getterName = "getTransactionAbandonedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionAbandonedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionAbandonedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions that were abandoned since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionCommittedTotalCount")) {
         getterName = "getTransactionCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionCommittedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionHeuristicsTotalCount")) {
         getterName = "getTransactionHeuristicsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicsTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that completed with a heuristic status since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionLLRCommittedTotalCount")) {
         getterName = "getTransactionLLRCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionLLRCommittedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionLLRCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of LLR transactions that were committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionLogStoreRuntimeMBean")) {
         getterName = "getTransactionLogStoreRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionLogStoreRuntimeMBean", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionLogStoreRuntimeMBean", currentResult);
         currentResult.setValue("description", "<p>Returns the runtime MBean for the primary TLOG persistent store, regardless of it is default store or JDBC store. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionNameRuntimeMBeans")) {
         getterName = "getTransactionNameRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionNameRuntimeMBeans", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionNameRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>An array of <code>TransactionNameRuntimeMBeans</code> that represent statistics for all transactions in the server, categorized by transaction name.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.TransactionNameRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionNoResourcesCommittedTotalCount")) {
         getterName = "getTransactionNoResourcesCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionNoResourcesCommittedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionNoResourcesCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with no enlisted resources that were committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionOneResourceOnePhaseCommittedTotalCount")) {
         getterName = "getTransactionOneResourceOnePhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionOneResourceOnePhaseCommittedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionOneResourceOnePhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with only one enlisted resource that were one-phase committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionReadOnlyOnePhaseCommittedTotalCount")) {
         getterName = "getTransactionReadOnlyOnePhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionReadOnlyOnePhaseCommittedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionReadOnlyOnePhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with more than one enlisted resource that were one-phase committed due to read-only optimization since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionResourceRuntimeMBeans")) {
         getterName = "getTransactionResourceRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionResourceRuntimeMBeans", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionResourceRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>An array of <code>TransactionResourceRuntimeMBeans</code> that each represents the statistics for a transaction resource.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.TransactionResourceRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRolledBackAppTotalCount")) {
         getterName = "getTransactionRolledBackAppTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackAppTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackAppTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to an application error.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackResourceTotalCount")) {
         getterName = "getTransactionRolledBackResourceTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackResourceTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackResourceTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to a resource error.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackSystemTotalCount")) {
         getterName = "getTransactionRolledBackSystemTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackSystemTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackSystemTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to an internal system error.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackTimeoutTotalCount")) {
         getterName = "getTransactionRolledBackTimeoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTimeoutTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTimeoutTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to a timeout expiration.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackTotalCount")) {
         getterName = "getTransactionRolledBackTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionServiceState")) {
         getterName = "getTransactionServiceState";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionServiceState", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionServiceState", currentResult);
         currentResult.setValue("description", "Returns the lifecycle state of the JTA Transaction Service as a string ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionTotalCount")) {
         getterName = "getTransactionTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions processed. This total includes all committed, rolled back, and heuristic transaction completions since the server was started.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionTwoPhaseCommittedLoggedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedLoggedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedLoggedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedLoggedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of two phase commit transactions that were committed with TLog since the server was started.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionTwoPhaseCommittedNotLoggedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedNotLoggedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedNotLoggedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedNotLoggedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of two phase commited transactions that were committed without TLog since the server was started.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TransactionTwoPhaseCommittedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedTotalCount", JTARuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with more than one enlisted resource that were two-phase committed since the server was started.</p> ");
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
      Method mth = JTARuntimeMBean.class.getMethod("getTransactionsOlderThan", Integer.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("seconds", "The transaction duration in seconds qualifier. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for JTATransaction");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>An array of <code>JTATransaction</code> objects. Each object provides detailed information regarding an active transaction that has existed for a period longer than the time specified.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
         currentResult.setValue("excludeFromRest", "No default REST mapping for JTATransaction");
      }

      mth = JTARuntimeMBean.class.getMethod("getRecoveryRuntimeMBean", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the runtime MBean for the Transaction Recovery Service of the specified server. If the Transaction Recovery Service of the specified server is not deployed on this server, null will be returned.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JTARuntimeMBean.class.getMethod("forceLocalRollback", Xid.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Forces the transaction represented by xid to be rolled-back at the local SubCoordinator only.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
      }

      mth = JTARuntimeMBean.class.getMethod("forceGlobalRollback", Xid.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Forces the transaction represented by xid to be rolled-back at all participating SubCoordinators. If the server on which the method is invoked is not the coordinating server then the coordinating server will be notified to process the rollback.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
      }

      mth = JTARuntimeMBean.class.getMethod("forceLocalCommit", Xid.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Forces the transaction represented by xid to be committed at the local SubCoordinator only.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
      }

      mth = JTARuntimeMBean.class.getMethod("forceGlobalCommit", Xid.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Forces the transaction represented by xid to be committed at all participating SubCoordinators. If the server on which the method is invoked is not the coordinating server then the coordinating server will be notified to process the commit.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Xid");
      }

      mth = JTARuntimeMBean.class.getMethod("getJTATransaction", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("xid", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for JTATransaction");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the JTATransaction information object for the specified Xid. If the transaction represented by xid does not exist on the server, then the method will return null.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for JTATransaction");
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
