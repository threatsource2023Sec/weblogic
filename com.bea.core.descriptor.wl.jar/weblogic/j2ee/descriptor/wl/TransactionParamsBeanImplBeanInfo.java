package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class TransactionParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = TransactionParamsBean.class;

   public TransactionParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public TransactionParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.TransactionParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("The transaction parameters bean controls how transactions are handled. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.TransactionParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("TransactionTimeout")) {
         getterName = "getTransactionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionTimeout";
         }

         currentResult = new PropertyDescriptor("TransactionTimeout", TransactionParamsBean.class, getterName, setterName);
         descriptors.put("TransactionTimeout", currentResult);
         currentResult.setValue("description", "<p>The timeout value (in seconds) for all transactions on connections created with this connection factory.</p>  <p>If a transacted session is still active after the timeout has elapsed, the transaction is rolled back. A value of 0 indicates that the default value will be used. If you have long-running transactions, you might want to adjust the value of this attribute to allow transactions to complete.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(3600L));
         currentResult.setValue("legalMax", new Long(2147483647L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XAConnectionFactoryEnabled")) {
         getterName = "isXAConnectionFactoryEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXAConnectionFactoryEnabled";
         }

         currentResult = new PropertyDescriptor("XAConnectionFactoryEnabled", TransactionParamsBean.class, getterName, setterName);
         descriptors.put("XAConnectionFactoryEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether a XA queue or XA topic connection factory is returned, instead of a queue or topic connection factory. An XA connection factory can be used to create an <code>XAConnection</code>, which in turn may be used to create an <code>XASession</code>, which in turn may be used to obtain an <code>XAResource</code> for use inside a transaction manager. </p>  <p>In addition, this attribute indicates whether or not a connection factory creates sessions that are JTA aware. If true, the associated message producers and message consumers look into the running thread for a transaction context. Otherwise, the current JTA transaction will be ignored. </p>  <p><i>Note:</i> Transacted sessions ignore the current threads transaction context in favor of their own internal transaction, regardless of the setting. This setting only affects non-transacted sessions. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
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
