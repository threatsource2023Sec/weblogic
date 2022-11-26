package weblogic.transaction.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.TransactionResourceRuntimeMBean;

public class TransactionResourceRuntimeImplBeanInfo extends JTAStatisticsImplBeanInfo {
   public static final Class INTERFACE_CLASS = TransactionResourceRuntimeMBean.class;

   public TransactionResourceRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TransactionResourceRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.transaction.internal.TransactionResourceRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.transaction.internal");
      String description = (new String("This interface represents runtime statistics for a transactional resource. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.TransactionResourceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of the Resource.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceName")) {
         getterName = "getResourceName";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceName", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceName", currentResult);
         currentResult.setValue("description", "<p>The resource name.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionCommittedTotalCount")) {
         getterName = "getTransactionCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionCommittedTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionHeuristicCommitTotalCount")) {
         getterName = "getTransactionHeuristicCommitTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicCommitTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicCommitTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions for which this resource has returned a heuristic commit decision.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionHeuristicHazardTotalCount")) {
         getterName = "getTransactionHeuristicHazardTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicHazardTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicHazardTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions for which this resource has reported a heuristic hazard decision.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionHeuristicMixedTotalCount")) {
         getterName = "getTransactionHeuristicMixedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicMixedTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicMixedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions for which this resource has reported a heuristic mixed decision.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionHeuristicRollbackTotalCount")) {
         getterName = "getTransactionHeuristicRollbackTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicRollbackTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicRollbackTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions for which this resource has returned a heuristic rollback decision.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionHeuristicsTotalCount")) {
         getterName = "getTransactionHeuristicsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicsTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that completed with a heuristic status since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackTotalCount")) {
         getterName = "getTransactionRolledBackTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionTotalCount")) {
         getterName = "getTransactionTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTotalCount", TransactionResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions processed. This total includes all committed, rolled back, and heuristic transaction completions since the server was started.</p> ");
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
