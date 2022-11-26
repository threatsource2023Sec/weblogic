package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ReliabilityConfigBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ReliabilityConfigBean.class;

   public ReliabilityConfigBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ReliabilityConfigBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ReliabilityConfigBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML reliabilityConfigType. This is configuration used by the reliability subsystem on the server-side (RM Destination) and the client-side (RM source). Properties specified for RM source will be used as defaults for any client handle (e.g. Stub or SoapDispatch instance) that is used from inside the service containing this configuration. (@http://www.bea.com/ns/weblogic/weblogic-webservices). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ReliabilityConfigBean");
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

         currentResult = new PropertyDescriptor("AcknowledgementInterval", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("AcknowledgementInterval", currentResult);
         currentResult.setValue("description", "String value in Duration format. Defaults to P0DT0.2S' (200 milliseconds). Applies to RM source and RM destination. ");
         setPropertyDescriptorDefault(currentResult, "P0DT0.2S");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BaseRetransmissionInterval")) {
         getterName = "getBaseRetransmissionInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBaseRetransmissionInterval";
         }

         currentResult = new PropertyDescriptor("BaseRetransmissionInterval", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("BaseRetransmissionInterval", currentResult);
         currentResult.setValue("description", "String value in Duration format. The interval of time that must pass before a message will be retransmitted to the RM destination (in the event a prior transmission failed). Defaults to P0DT8S (8 seconds). Applies to RM source. ");
         setPropertyDescriptorDefault(currentResult, "P0DT8S");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BufferRetryCount")) {
         getterName = "getBufferRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBufferRetryCount";
         }

         currentResult = new PropertyDescriptor("BufferRetryCount", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("BufferRetryCount", currentResult);
         currentResult.setValue("description", "Used only for JAX-RPC services. JAX-WS services should set this on BufferingConfigBean instead. <p> Number of times the service will attempt to retry processing of a reliable request. Note that this processing refers to the incoming request for a service operation, and not any outgoing request to an RM destination (as is the case with BaseRetransmissionInterval, etc.). Defaults to 3. Applies to RM destination. ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BufferRetryDelay")) {
         getterName = "getBufferRetryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBufferRetryDelay";
         }

         currentResult = new PropertyDescriptor("BufferRetryDelay", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("BufferRetryDelay", currentResult);
         currentResult.setValue("description", "Used only for JAX-RPC services. JAX-WS services should set this on BufferingConfigBean instead. <p> String value in Duration format defining an amount of time to wait between retries of an incoming reliable request. Defaults to P0DT5S (5 seconds). Applies to RM destination. ");
         setPropertyDescriptorDefault(currentResult, "P0DT5S");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InactivityTimeout")) {
         getterName = "getInactivityTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInactivityTimeout";
         }

         currentResult = new PropertyDescriptor("InactivityTimeout", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("InactivityTimeout", currentResult);
         currentResult.setValue("description", "String value in Duration format. If during this duration, an endpoint (RM source or RM destination) has received no application or control messages, the endpoint MAY consider the RM Sequence to have been terminated due to inactivity.  Defaults to P0DT600S (600 seconds). Implementations of RM source and RM destination are free to manage resources associated with the sequence as they please, but in general, there are no guarantees that the sequence will be useable by either party after the inactivity timeout expires. Applies to RM source and RM destination. ");
         setPropertyDescriptorDefault(currentResult, "P0DT600S");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NonBufferedDestination")) {
         getterName = "getNonBufferedDestination";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonBufferedDestination";
         }

         currentResult = new PropertyDescriptor("NonBufferedDestination", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("NonBufferedDestination", currentResult);
         currentResult.setValue("description", "A boolean flag indicating that the RM destination represented by the current service will receive non-buffered. Defaults to false. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NonBufferedSource")) {
         getterName = "getNonBufferedSource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonBufferedSource";
         }

         currentResult = new PropertyDescriptor("NonBufferedSource", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("NonBufferedSource", currentResult);
         currentResult.setValue("description", "A boolean flag indicating that any RM source hosted within the current service will send non-buffered. Defaults to false. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetransmissionExponentialBackoff")) {
         getterName = "getRetransmissionExponentialBackoff";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetransmissionExponentialBackoff";
         }

         currentResult = new PropertyDescriptor("RetransmissionExponentialBackoff", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("RetransmissionExponentialBackoff", currentResult);
         currentResult.setValue("description", "A boolean flag indicating that the retransmission interval will be adjusted using the exponential backoff algorithm [Tanenbaum]. Defaults to false. Applies to RM source. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SequenceExpiration")) {
         getterName = "getSequenceExpiration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSequenceExpiration";
         }

         currentResult = new PropertyDescriptor("SequenceExpiration", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("SequenceExpiration", currentResult);
         currentResult.setValue("description", "Duration expression (String), defaults to P1D (1 day). This is the expiration time for a sequence regardless of activity. Applies to RM source and RM destination. ");
         setPropertyDescriptorDefault(currentResult, "P1D");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Customized")) {
         getterName = "isCustomized";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCustomized";
         }

         currentResult = new PropertyDescriptor("Customized", ReliabilityConfigBean.class, getterName, setterName);
         descriptors.put("Customized", currentResult);
         currentResult.setValue("description", "A boolean flag indicating whether the reliability config described by this bean has been customized and should be considered active for use at runtime. Defaults to true. If false, none of the values on this bean will be used, and the server-wide defaults for these values will take effect. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
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
