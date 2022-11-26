package weblogic.transaction.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JTAPartitionRuntimeMBean;

public class JTAPartitionRuntimeImplBeanInfo extends JTATransactionStatisticsImplBeanInfo {
   public static final Class INTERFACE_CLASS = JTAPartitionRuntimeMBean.class;

   public JTAPartitionRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JTAPartitionRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.transaction.internal.JTAPartitionRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.transaction.internal");
      String description = (new String("This interface is used for accessing transaction runtime characteristics within a WebLogic server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JTAPartitionRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ActiveTransactionsTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveTransactionsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of active transactions on the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JTATransactions")) {
         getterName = "getJTATransactions";
         setterName = null;
         currentResult = new PropertyDescriptor("JTATransactions", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JTATransactions", currentResult);
         currentResult.setValue("description", "<p>An array of <code>JTATransaction</code> objects. Each object provides detailed information regarding an active transaction.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for JTATransaction");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("NonXAResourceRuntimeMBeans")) {
         getterName = "getNonXAResourceRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("NonXAResourceRuntimeMBeans", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NonXAResourceRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>An array of <code>NonXAResourceRuntimeMBeans</code> that each represents the statistics for a non-XA resource.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.NonXAResourceRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegisteredNonXAResourceNames")) {
         getterName = "getRegisteredNonXAResourceNames";
         setterName = null;
         currentResult = new PropertyDescriptor("RegisteredNonXAResourceNames", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RegisteredNonXAResourceNames", currentResult);
         currentResult.setValue("description", "<p>An array of NonXA resource names that are registered with the transaction manager.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegisteredResourceNames")) {
         getterName = "getRegisteredResourceNames";
         setterName = null;
         currentResult = new PropertyDescriptor("RegisteredResourceNames", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RegisteredResourceNames", currentResult);
         currentResult.setValue("description", "<p>An array of XA resource names that are registered with the transaction manager.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondsActiveTotalCount")) {
         getterName = "getSecondsActiveTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondsActiveTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondsActiveTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of seconds that transactions were active for all committed transactions.</p> ");
      }

      if (!descriptors.containsKey("TransactionAbandonedTotalCount")) {
         getterName = "getTransactionAbandonedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionAbandonedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionAbandonedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions that were abandoned since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionCommittedTotalCount")) {
         getterName = "getTransactionCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionCommittedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionHeuristicsTotalCount")) {
         getterName = "getTransactionHeuristicsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicsTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that completed with a heuristic status since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionLLRCommittedTotalCount")) {
         getterName = "getTransactionLLRCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionLLRCommittedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionLLRCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of LLR transactions that were committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionNameRuntimeMBeans")) {
         getterName = "getTransactionNameRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionNameRuntimeMBeans", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionNameRuntimeMBeans", currentResult);
         currentResult.setValue("description", "<p>An array of <code>TransactionNameRuntimeMBeans</code> that represent statistics for all transactions in the partition, categorized by transaction name.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.TransactionNameRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionNoResourcesCommittedTotalCount")) {
         getterName = "getTransactionNoResourcesCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionNoResourcesCommittedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionNoResourcesCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with no enlisted resources that were committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionOneResourceOnePhaseCommittedTotalCount")) {
         getterName = "getTransactionOneResourceOnePhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionOneResourceOnePhaseCommittedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionOneResourceOnePhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with only one enlisted resource that were one-phase committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionReadOnlyOnePhaseCommittedTotalCount")) {
         getterName = "getTransactionReadOnlyOnePhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionReadOnlyOnePhaseCommittedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionReadOnlyOnePhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with more than one enlisted resource that were one-phase committed due to read-only optimization since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionResourceRuntimeMBeans")) {
         getterName = "getTransactionResourceRuntimeMBeans";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionResourceRuntimeMBeans", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
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
         currentResult = new PropertyDescriptor("TransactionRolledBackAppTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackAppTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to an application error.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackResourceTotalCount")) {
         getterName = "getTransactionRolledBackResourceTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackResourceTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackResourceTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to a resource error.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackSystemTotalCount")) {
         getterName = "getTransactionRolledBackSystemTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackSystemTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackSystemTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to an internal system error.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackTimeoutTotalCount")) {
         getterName = "getTransactionRolledBackTimeoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTimeoutTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTimeoutTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to a timeout expiration.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackTotalCount")) {
         getterName = "getTransactionRolledBackTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionTotalCount")) {
         getterName = "getTransactionTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions processed. This total includes all committed, rolled back, and heuristic transaction completions since the server was started.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionTwoPhaseCommittedLoggedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedLoggedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedLoggedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedLoggedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of two phase commit transactions that were committed with TLog since the server was started.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionTwoPhaseCommittedNotLoggedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedNotLoggedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedNotLoggedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedNotLoggedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of two phase commited transactions that were committed without TLog since the server was started.</p> ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TransactionTwoPhaseCommittedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedTotalCount", JTAPartitionRuntimeMBean.class, getterName, (String)setterName);
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
      Method mth = JTAPartitionRuntimeMBean.class.getMethod("getTransactionsOlderThan", Integer.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("seconds", "The transaction duration in seconds qualifier. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for JTATransaction");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>An array of <code>JTATransaction</code> objects. Each object provides detailed information regarding an active transaction that has existed for a period longer than the time specified.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
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
