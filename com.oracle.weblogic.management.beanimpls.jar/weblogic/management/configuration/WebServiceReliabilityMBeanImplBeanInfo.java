package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class WebServiceReliabilityMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebServiceReliabilityMBean.class;

   public WebServiceReliabilityMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebServiceReliabilityMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebServiceReliabilityMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.3.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Represents reliability configuration for web services.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebServiceReliabilityMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AcknowledgementInterval")) {
         getterName = "getAcknowledgementInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAcknowledgementInterval";
         }

         currentResult = new PropertyDescriptor("AcknowledgementInterval", WebServiceReliabilityMBean.class, getterName, setterName);
         descriptors.put("AcknowledgementInterval", currentResult);
         currentResult.setValue("description", "The maximum time a pending acknowledgement (set after the destination accepts a message) can wait before being delivered back to the RM source. String value in \"Duration\" format. Defaults to \"P0DT0.2S\" (200 milliseconds). Set at sequence creation time, and cannot be reset. ");
         setPropertyDescriptorDefault(currentResult, "P0DT0.2S");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BaseRetransmissionInterval")) {
         getterName = "getBaseRetransmissionInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBaseRetransmissionInterval";
         }

         currentResult = new PropertyDescriptor("BaseRetransmissionInterval", WebServiceReliabilityMBean.class, getterName, setterName);
         descriptors.put("BaseRetransmissionInterval", currentResult);
         currentResult.setValue("description", "The interval of time that must pass before a message will be retransmitted to the RM destination (in the event a prior transmission failed). String value in \"Duration\" format. Defaults to \"P0DT3S\" (3 seconds). Set at sequence creation time, and cannot be reset. ");
         setPropertyDescriptorDefault(currentResult, "P0DT3S");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InactivityTimeout")) {
         getterName = "getInactivityTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInactivityTimeout";
         }

         currentResult = new PropertyDescriptor("InactivityTimeout", WebServiceReliabilityMBean.class, getterName, setterName);
         descriptors.put("InactivityTimeout", currentResult);
         currentResult.setValue("description", "If during this duration, an endpoint (RM source or RM destination) has received no application or control messages, the endpoint MAY consider the RM Sequence to have been terminated due to inactivity. String value in \"Duration\" format. Defaults to \"P0DT600S\" (600 seconds). Implementations of RM source and RM destination are free to manage resources associated with the sequence as they please, but in general, there are no guarantees that the sequence will be useable by either party after the inactivity timeout expires. Set at sequence creation time, and cannot be reset. ");
         setPropertyDescriptorDefault(currentResult, "P0DT600S");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SequenceExpiration")) {
         getterName = "getSequenceExpiration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSequenceExpiration";
         }

         currentResult = new PropertyDescriptor("SequenceExpiration", WebServiceReliabilityMBean.class, getterName, setterName);
         descriptors.put("SequenceExpiration", currentResult);
         currentResult.setValue("description", "This is the maximum lifetime of a sequence. If this limit is reached before the sequence naturally completes, it will be forcibly terminated. String value in \"Duration\" format. Defaults to \"P1D\" (1 day). Set at sequence creation time, and cannot be reset. ");
         setPropertyDescriptorDefault(currentResult, "P1D");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NonBufferedDestination")) {
         getterName = "isNonBufferedDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonBufferedDestination";
         }

         currentResult = new PropertyDescriptor("NonBufferedDestination", WebServiceReliabilityMBean.class, getterName, setterName);
         descriptors.put("NonBufferedDestination", currentResult);
         currentResult.setValue("description", "A boolean flag indicating that RM destinations, by default, will receive non-buffered. Defaults to true. Note, changes to this default will only be picked up by new reliable sequences. Existing reliable sequences have their persistence handling set at creation time and these values will not change. ");
         setPropertyDescriptorDefault(currentResult, true);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NonBufferedSource")) {
         getterName = "isNonBufferedSource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonBufferedSource";
         }

         currentResult = new PropertyDescriptor("NonBufferedSource", WebServiceReliabilityMBean.class, getterName, setterName);
         descriptors.put("NonBufferedSource", currentResult);
         currentResult.setValue("description", "A boolean flag indicating that RM sources, by default, will send non-buffered. Defaults to true. Note, changes to this default will only be picked up by new reliable sequences. Existing reliable sequences have their persistence handling set at creation time and these values will not change. ");
         setPropertyDescriptorDefault(currentResult, true);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetransmissionExponentialBackoff")) {
         getterName = "isRetransmissionExponentialBackoff";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetransmissionExponentialBackoff";
         }

         currentResult = new PropertyDescriptor("RetransmissionExponentialBackoff", WebServiceReliabilityMBean.class, getterName, setterName);
         descriptors.put("RetransmissionExponentialBackoff", currentResult);
         currentResult.setValue("description", "A boolean flag indicating that the retransmission interval will be adjusted using the exponential backoff algorithm ([Tanenbaum]). Defaults to false. Set at sequence creation time, and cannot be reset. ");
         setPropertyDescriptorDefault(currentResult, false);
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
