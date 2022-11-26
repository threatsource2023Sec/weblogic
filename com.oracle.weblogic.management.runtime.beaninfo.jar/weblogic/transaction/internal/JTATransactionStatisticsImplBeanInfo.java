package weblogic.transaction.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JTATransactionStatisticsRuntimeMBean;

public class JTATransactionStatisticsImplBeanInfo extends JTAStatisticsImplBeanInfo {
   public static final Class INTERFACE_CLASS = JTATransactionStatisticsRuntimeMBean.class;

   public JTATransactionStatisticsImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JTATransactionStatisticsImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.transaction.internal.JTATransactionStatisticsImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.transaction.internal");
      String description = (new String("This interface contains general JTA transaction runtime statistics. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JTATransactionStatisticsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("SecondsActiveTotalCount")) {
         getterName = "getSecondsActiveTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondsActiveTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondsActiveTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of seconds that transactions were active for all committed transactions.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionAbandonedTotalCount")) {
         getterName = "getTransactionAbandonedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionAbandonedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionAbandonedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions that were abandoned since the server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionCommittedTotalCount")) {
         getterName = "getTransactionCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionCommittedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionHeuristicsTotalCount")) {
         getterName = "getTransactionHeuristicsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicsTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that completed with a heuristic status since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionLLRCommittedTotalCount")) {
         getterName = "getTransactionLLRCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionLLRCommittedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionLLRCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of LLR transactions that were committed since the server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionNoResourcesCommittedTotalCount")) {
         getterName = "getTransactionNoResourcesCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionNoResourcesCommittedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionNoResourcesCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with no enlisted resources that were committed since the server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionOneResourceOnePhaseCommittedTotalCount")) {
         getterName = "getTransactionOneResourceOnePhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionOneResourceOnePhaseCommittedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionOneResourceOnePhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with only one enlisted resource that were one-phase committed since the server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionReadOnlyOnePhaseCommittedTotalCount")) {
         getterName = "getTransactionReadOnlyOnePhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionReadOnlyOnePhaseCommittedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionReadOnlyOnePhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with more than one enlisted resource that were one-phase committed due to read-only optimization since the server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRolledBackAppTotalCount")) {
         getterName = "getTransactionRolledBackAppTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackAppTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackAppTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to an application error.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRolledBackResourceTotalCount")) {
         getterName = "getTransactionRolledBackResourceTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackResourceTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackResourceTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to a resource error.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRolledBackSystemTotalCount")) {
         getterName = "getTransactionRolledBackSystemTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackSystemTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackSystemTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to an internal system error.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRolledBackTimeoutTotalCount")) {
         getterName = "getTransactionRolledBackTimeoutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTimeoutTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTimeoutTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back due to a timeout expiration.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionRolledBackTotalCount")) {
         getterName = "getTransactionRolledBackTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionTotalCount")) {
         getterName = "getTransactionTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions processed. This total includes all committed, rolled back, and heuristic transaction completions since the server was started.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionTwoPhaseCommittedLoggedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedLoggedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedLoggedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedLoggedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of two phase commit transactions that were committed with TLog since the server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("TransactionTwoPhaseCommittedNotLoggedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedNotLoggedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedNotLoggedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedNotLoggedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of two phase commited transactions that were committed without TLog since the server was started.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TransactionTwoPhaseCommittedTotalCount")) {
         getterName = "getTransactionTwoPhaseCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTwoPhaseCommittedTotalCount", JTATransactionStatisticsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTwoPhaseCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions with more than one enlisted resource that were two-phase committed since the server was started.</p> ");
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
