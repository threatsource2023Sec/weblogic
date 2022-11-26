package weblogic.wsee.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WseeWsrmRuntimeMBean;

public class WseeWsrmRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WseeWsrmRuntimeMBean.class;

   public WseeWsrmRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WseeWsrmRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.wsee.monitoring.WseeWsrmRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.wsee.monitoring");
      String description = (new String("<p>Encapsulates runtime information about WS-RM functionality. If this MBean is parented by a WseePortRuntimeMBean instance, this MBean represents WS-RM resources contained in a particular Web Service or web service client. If this MBean is parented by ServerRuntimeMBean, this MBean represents WS-RM resources for the entire server/VM (spanning across applications, web services, clients, etc.)</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WseeWsrmRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("SequenceIds")) {
         String getterName = "getSequenceIds";
         String setterName = null;
         currentResult = new PropertyDescriptor("SequenceIds", WseeWsrmRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SequenceIds", currentResult);
         currentResult.setValue("description", "<p>A list of sequence IDs representing active sequences being managed on the WS-RM destination (receiving side).</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
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
      Method mth = WseeWsrmRuntimeMBean.class.getMethod("getSequenceInfo", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Given a sequence ID from the sequence ID list, get a data object representing current information for that sequence. The returned CompositeData item is patterned after the WsrmSequenceInfo interface.</p>  <p>We give a summary of the structure of WsrmSequenceInfo here. For more details consult the JavaDoc for WsrmSequenceInfo and WsrmRequestInfo.</p> <ul> <li><code>String id</code></li> <li><code>boolean source</code></li> <li><code>String destinationId</code></li> <li><code>boolean offer</code></li> <li><code>mainSequenceId</code></li> <li><code>String state</code> <ul> <li><code>\"NEW\"</code></li> <li><code>\"CREATING\"</code></li> <li><code>\"CREATED\"</code></li> <li><code>\"LAST_MESSAGE_PENDING\"</code></li> <li><code>\"LAST_MESSAGE\"</code></li> <li><code>\"CLOSING\"</code></li> <li><code>\"CLOSED\"</code></li> <li><code>\"TERMINATING\"</code></li> <li><code>\"TERMINATED\"</code></li> </ul> </li> <li><code>long creationTime</code></li> <li><code>long lastActivityTime</code></li> <li><code>long maxAge</code></li> <li><code>long lastAckdMessageNum</code></li> <li><code>long unackdCount</code></li> <li><code>WsrmRequestInfo[] requests</code> <ul> <li><code>String messageId</code></li> <li><code>long seqNum</code></li> <li><code>String soapAction</code></li> <li><code>long timestamp</code></li> <li><code>boolean ackFlag</code></li> <li><code>String responseMessageId</code></li> <li><code>long responseTimestamp</code></li> </ul> </li> </ul> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for CompositeData");
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
