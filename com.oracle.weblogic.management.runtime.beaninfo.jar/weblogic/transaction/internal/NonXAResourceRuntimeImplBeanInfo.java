package weblogic.transaction.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.NonXAResourceRuntimeMBean;

public class NonXAResourceRuntimeImplBeanInfo extends JTAStatisticsImplBeanInfo {
   public static final Class INTERFACE_CLASS = NonXAResourceRuntimeMBean.class;

   public NonXAResourceRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public NonXAResourceRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.transaction.internal.NonXAResourceRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.transaction.internal");
      String description = (new String("This represents runtime statistical information about a NonXAResource ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.NonXAResourceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("NonXAResourceName")) {
         getterName = "getNonXAResourceName";
         setterName = null;
         currentResult = new PropertyDescriptor("NonXAResourceName", NonXAResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NonXAResourceName", currentResult);
         currentResult.setValue("description", "<p>Returns the resource name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionCommittedTotalCount")) {
         getterName = "getTransactionCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionCommittedTotalCount", NonXAResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions committed since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionHeuristicsTotalCount")) {
         getterName = "getTransactionHeuristicsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionHeuristicsTotalCount", NonXAResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionHeuristicsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that completed with a heuristic status since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionRolledBackTotalCount")) {
         getterName = "getTransactionRolledBackTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionRolledBackTotalCount", NonXAResourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionRolledBackTotalCount", currentResult);
         currentResult.setValue("description", "<p>The number of transactions that were rolled back since the server was started.</p> ");
      }

      if (!descriptors.containsKey("TransactionTotalCount")) {
         getterName = "getTransactionTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionTotalCount", NonXAResourceRuntimeMBean.class, getterName, (String)setterName);
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
