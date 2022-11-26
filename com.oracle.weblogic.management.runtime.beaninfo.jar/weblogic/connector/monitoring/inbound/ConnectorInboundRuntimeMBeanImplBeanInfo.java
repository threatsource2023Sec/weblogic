package weblogic.connector.monitoring.inbound;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.ConnectorInboundRuntimeMBean;

public class ConnectorInboundRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectorInboundRuntimeMBean.class;

   public ConnectorInboundRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectorInboundRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.connector.monitoring.inbound.ConnectorInboundRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.connector.monitoring.inbound");
      String description = (new String("This class is used for monitoring inbound connections of resource adapters. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConnectorInboundRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActivationSpecClass")) {
         getterName = "getActivationSpecClass";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivationSpecClass", ConnectorInboundRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActivationSpecClass", currentResult);
         currentResult.setValue("description", "<p>The activation spec class.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MDBRuntimes")) {
         getterName = "getMDBRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MDBRuntimes", ConnectorInboundRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MDBRuntimes", currentResult);
         currentResult.setValue("description", "<p>Return all MDB pools that were activated for the resource adapter.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MsgListenerType")) {
         getterName = "getMsgListenerType";
         setterName = null;
         currentResult = new PropertyDescriptor("MsgListenerType", ConnectorInboundRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MsgListenerType", currentResult);
         currentResult.setValue("description", "<p>The message listener type.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ConnectorInboundRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>Return the state of the Inbound connection. The values that are returned are : \"Running\" and \"Suspended\".</p> ");
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
