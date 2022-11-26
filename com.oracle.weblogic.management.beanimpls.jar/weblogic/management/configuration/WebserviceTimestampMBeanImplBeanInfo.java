package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebserviceTimestampMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebserviceTimestampMBean.class;

   public WebserviceTimestampMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebserviceTimestampMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebserviceTimestampMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Encapsulates the timestamp information that is associated with a Web Service security configuration.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebserviceTimestampMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClockSkew")) {
         getterName = "getClockSkew";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClockSkew";
         }

         currentResult = new PropertyDescriptor("ClockSkew", WebserviceTimestampMBean.class, getterName, setterName);
         descriptors.put("ClockSkew", currentResult);
         currentResult.setValue("description", "<p>If clocks are synchronized, this attribute describes the accuracy of the synchronization between two clocks: the client and the server.</p>  <p>ClockSkew is expressed in milliseconds. Clock skew is enforced by rendering all times into milliseconds since a common time 0 and using these times for comparisons. For example, if your clocks are accurate to within 1 minute of each other, you would set your skew to 1 minute * 60 seconds * 1000 milliseconds or 60000. </p> ");
         setPropertyDescriptorDefault(currentResult, new Long(60000L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxProcessingDelay")) {
         getterName = "getMaxProcessingDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxProcessingDelay";
         }

         currentResult = new PropertyDescriptor("MaxProcessingDelay", WebserviceTimestampMBean.class, getterName, setterName);
         descriptors.put("MaxProcessingDelay", currentResult);
         currentResult.setValue("description", "<p>Specifies the freshness policy for received messages: the Web Service observes the processing delay by subtracting the Created time in the Timestamp from the current time.</p>  <p>If the observed processing delay is greater than maxProcessingDelay plus clockSkew, then the message is  rejected as stale.</p>  <p>This attribute is specified in milliseconds.</p>  <p>Setting maxProcessingDelay to NO_MAX_PROCESSING_DELAY disables to enforcement of the freshness policy.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValidityPeriod")) {
         getterName = "getValidityPeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValidityPeriod";
         }

         currentResult = new PropertyDescriptor("ValidityPeriod", WebserviceTimestampMBean.class, getterName, setterName);
         descriptors.put("ValidityPeriod", currentResult);
         currentResult.setValue("description", "<p>Represents the length of time the sender wants the outbound message to be valid. </p>  <p>When the validityPeriod is positive, the TimestampHandler inserts an Expires element into the Timestamp header.   The validityPeriod is expressed in seconds:  the Expires time will be that many seconds ahead of the Timestamp's Created time.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClockSynchronized")) {
         getterName = "isClockSynchronized";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClockSynchronized";
         }

         currentResult = new PropertyDescriptor("ClockSynchronized", WebserviceTimestampMBean.class, getterName, setterName);
         descriptors.put("ClockSynchronized", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Web Service assumes synchronized clocks.</p>  <p>If the clockSynchronized attribute is false, the Web Service rejects all inbound messages with that contain expirations, because this is the only safe way to ensure that the message hasn't already expired. In this case, the Web Service also does not enforce a freshness policy.</p>  <p>If this attribute is set to true, then the Web Service enforces expirations on inbound messages to the best of its ability and enforces an optional freshness policy (via maxProcessingDelay).</p>  <p>The default value of this attribute is true.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
