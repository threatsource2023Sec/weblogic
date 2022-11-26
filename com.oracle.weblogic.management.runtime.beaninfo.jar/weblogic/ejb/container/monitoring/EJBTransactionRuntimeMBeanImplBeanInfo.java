package weblogic.ejb.container.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.EJBTransactionRuntimeMBean;

public class EJBTransactionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBTransactionRuntimeMBean.class;

   public EJBTransactionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBTransactionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.ejb.container.monitoring.EJBTransactionRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.ejb.container.monitoring");
      String description = (new String("This interface contains accessor methods for all transaction runtime information collected for an EJB. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.EJBTransactionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("TransactionsCommittedTotalCount")) {
         getterName = "getTransactionsCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionsCommittedTotalCount", EJBTransactionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionsCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of transactions that have been committed for this EJB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionsRolledBackTotalCount")) {
         getterName = "getTransactionsRolledBackTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionsRolledBackTotalCount", EJBTransactionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionsRolledBackTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of transactions that have been rolled back for this EJB.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionsTimedOutTotalCount")) {
         getterName = "getTransactionsTimedOutTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionsTimedOutTotalCount", EJBTransactionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionsTimedOutTotalCount", currentResult);
         currentResult.setValue("description", "<p>Provides a count of the total number of transactions that have timed out for this EJB.</p> ");
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
