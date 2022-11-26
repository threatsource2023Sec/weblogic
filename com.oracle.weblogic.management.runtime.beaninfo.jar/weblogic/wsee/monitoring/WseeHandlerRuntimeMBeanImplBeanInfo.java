package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WseeHandlerRuntimeMBean;

public class WseeHandlerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeHandlerRuntimeMBean.class;

   public WseeHandlerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeHandlerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeHandlerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Provides runtime information about a SOAP message handler that has been associated with a Web service.</p>  <p>SOAP message handlers are used to intercept both the inbound (request) and outbound (response) SOAP messages so that extra processing can be done on the messages. Programmers specify SOAP message handlers for a Web Service using the @SOAPMessageHandlers and @HandlerChain JWS annotations.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeHandlerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("HandlerClass")) {
         getterName = "getHandlerClass";
         setterName = null;
         currentResult = new PropertyDescriptor("HandlerClass", WseeHandlerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HandlerClass", currentResult);
         currentResult.setValue("description", "<p>Specifies the fully qualified name of the class that implements this SOAP handler.</p>  <p>This class implements for JAX-WS the javax.xml.ws.handler.LogicalHandler class; and for JAX-RPC the javax.xml.rpc.handler.Handler interface or javax.xml.rpc.handler.GenericHandler abstract class. These interfaces or classes contain methods that programmers implement to process the request and response SOAP messages resulting from the invoke of a Web service operation. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Class");
      }

      if (!descriptors.containsKey("Headers")) {
         getterName = "getHeaders";
         setterName = null;
         currentResult = new PropertyDescriptor("Headers", WseeHandlerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Headers", currentResult);
         currentResult.setValue("description", "<p>Specifies the SOAP headers that can be processed by this SOAP message handler.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for QName");
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
