package weblogic.transaction.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JTARecoveryRuntimeMBean;

public class JTARecoveryRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JTARecoveryRuntimeMBean.class;

   public JTARecoveryRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JTARecoveryRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.transaction.internal.JTARecoveryRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.transaction.internal");
      String description = (new String("This interface is used for accessing transaction runtime characteristics for recovered transactions that are associated with a particular Transaction Recovery Service. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JTARecoveryRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("InitialRecoveredTransactionTotalCount")) {
         getterName = "getInitialRecoveredTransactionTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InitialRecoveredTransactionTotalCount", JTARecoveryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InitialRecoveredTransactionTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions that are recovered from the transaction log initially.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InitialRecoveredUnloggedTransactionTotalCount")) {
         getterName = "getInitialRecoveredUnloggedTransactionTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InitialRecoveredUnloggedTransactionTotalCount", JTARecoveryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InitialRecoveredUnloggedTransactionTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of transactions that are recovered from the no transaction log initially.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("RecoveredTransactionCompletionPercent")) {
         getterName = "getRecoveredTransactionCompletionPercent";
         setterName = null;
         currentResult = new PropertyDescriptor("RecoveredTransactionCompletionPercent", JTARecoveryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecoveredTransactionCompletionPercent", currentResult);
         currentResult.setValue("description", "<p>The percentage of transactions that are recovered from the transaction log initially.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RecoveredUnloggedTransactionCompletionPercent")) {
         getterName = "getRecoveredUnloggedTransactionCompletionPercent";
         setterName = null;
         currentResult = new PropertyDescriptor("RecoveredUnloggedTransactionCompletionPercent", JTARecoveryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecoveredUnloggedTransactionCompletionPercent", currentResult);
         currentResult.setValue("description", "<p>The percentage of transactions that are recovered from the no transaction log initially.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Active")) {
         getterName = "isActive";
         setterName = null;
         currentResult = new PropertyDescriptor("Active", JTARecoveryRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Active", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Transaction Recovery Service is currently activated on this server.</p> ");
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
