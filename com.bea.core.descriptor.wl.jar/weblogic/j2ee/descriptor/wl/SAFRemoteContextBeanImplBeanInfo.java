package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SAFRemoteContextBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFRemoteContextBean.class;

   public SAFRemoteContextBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SAFRemoteContextBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SAFRemoteContextBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Store-and-Forward (SAF) Remote Context specifies the SAF Login Context that the SAF Imported Queue or Topic use to connect to the remote Destination. SAF Remote Context also specifies the SAF Remote Context configured in the remote cluster or server that is used by the producer of the reply-to JMS Destination specified in the message sent to a SAF Imported Destination. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SAFRemoteContextBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CompressionThreshold")) {
         getterName = "getCompressionThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompressionThreshold";
         }

         currentResult = new PropertyDescriptor("CompressionThreshold", SAFRemoteContextBean.class, getterName, setterName);
         descriptors.put("CompressionThreshold", currentResult);
         currentResult.setValue("description", "<p>The number of bytes for a serialized message body so that any message that exceeds this limit triggers message compression when the message is about to be sent across a SAF agent's JVM boundary.</p>  <p>The compression will occur on the sending-side SAF agent's JVM if the message body size exceeds the threshold limit.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplyToSAFRemoteContextName")) {
         getterName = "getReplyToSAFRemoteContextName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplyToSAFRemoteContextName";
         }

         currentResult = new PropertyDescriptor("ReplyToSAFRemoteContextName", SAFRemoteContextBean.class, getterName, setterName);
         descriptors.put("ReplyToSAFRemoteContextName", currentResult);
         currentResult.setValue("description", "<p>Specifies the SAF Remote Context Name used by the replyTo destination in the remote cluster or server.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFLoginContext")) {
         getterName = "getSAFLoginContext";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFLoginContext", SAFRemoteContextBean.class, getterName, setterName);
         descriptors.put("SAFLoginContext", currentResult);
         currentResult.setValue("description", "<p>Defines the parameters needed to get a login context from a remote server.</p> ");
         currentResult.setValue("relationship", "containment");
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
