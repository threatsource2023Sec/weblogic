package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SAFImportedDestinationsBeanImplBeanInfo extends TargetableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFImportedDestinationsBean.class;

   public SAFImportedDestinationsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SAFImportedDestinationsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Store-and-Forward (SAF) Imported Destinations is a collection of a SAF queues and topics that reside in a different cluster or server. These sets of SAF queues and topics refer to the same SAF Remote Context. They can also share the same JNDI prefix, Time-To-Live default, and SAF Error Handling name. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ExactlyOnceLoadBalancingPolicy")) {
         getterName = "getExactlyOnceLoadBalancingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExactlyOnceLoadBalancingPolicy";
         }

         currentResult = new PropertyDescriptor("ExactlyOnceLoadBalancingPolicy", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("ExactlyOnceLoadBalancingPolicy", currentResult);
         currentResult.setValue("description", "<p>Controls the load balancing behavior when the SAF service forwards messages to a distributed destination with the <code>Exactly-Once</code> quality of service (QOS).</p>  <p> The valid values are: </p> <ul> <li><p><code>Per-Member</code> - The default value. All active members of the target distributed destination (DD) will be the candidates for load balancing. If there are multiple members of the same DD running on a WebLogic server JVM, these members will all receive forwarded messages.</p> </li>  <li><p><code>Per-JVM</code> - On each WebLogic server JVM, only one of the active members of the target distributed destination(DD) will be the candidate for load balancing. When the DD has members associated with the instances of a cluster-targeted JMS server, the load balancing algorithm will bias to the \"preferred member\", which has natural affinity or preference to a particular JVM. Otherwise, the algorithm will bias to the member whose name is lexicographically smallest among all candidate members on the same JVM. </p> </li> </ul> <p>Notes:</p>  <ul> <li> When each JVM hosts only one member of a DD, the two options behave the same. </li> <li> You can override the <code>Exactly Once Load Balancing Policy</code> on SAF Imported Destinations Beans defined in all JMS modules or a particular JMS module by specifying the system properties <code>weblogic.jms.saf.ExactlyOnceLoadBalancingPolicy</code> or <code>weblogic.jms.saf.ExactlyOnceLoadBalancingPolicy.MODULENAME</code> on every WebLogic Server in a cluster (the latter property takes precedence over the former). If a  SAF Imported Destinations is overridden by one of these system properties, then the host WebLogic Server will log an Info message BEA-281034 with the name of the SAF Imported Destinations, the system property, and the system property value once the first SAF message is forwarded.</li> <li> The setting does not apply to the SAF configurations that use the <code>At-Last-Once</code> or <code>At-Most-Once</code> QOS. Neither does it apply to forwarding messages to a \"standalone\" destination, or forwarding unit-of-order messages. </li> <li> The setting is honored in both server store-and-forward and client store-and-forward. </li> <li> For a similar setting that controls the load balancing of messages on a local distributed Imported Destination, see the Producer Load Balancing Policy attribute on Connection Factory Load Balancing Params Bean. </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER);
         currentResult.setValue("legalValues", new Object[]{JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER, JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_JVM});
         currentResult.setValue("declaration", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIPrefix")) {
         getterName = "getJNDIPrefix";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIPrefix";
         }

         currentResult = new PropertyDescriptor("JNDIPrefix", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("JNDIPrefix", currentResult);
         currentResult.setValue("description", "<p>Specifies the string that will prefix the local JNDI name of a remote destination.</p>  <p>Any change to this prefix affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageLoggingParams")) {
         getterName = "getMessageLoggingParams";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageLoggingParams", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("MessageLoggingParams", currentResult);
         currentResult.setValue("description", "<p>These parameters control how the SAF destination performs message logging.</p>  <p>They allow the adminstrator to configure the SAF destination to change message logging when message life cycle changes are detected.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFErrorHandling")) {
         getterName = "getSAFErrorHandling";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAFErrorHandling";
         }

         currentResult = new PropertyDescriptor("SAFErrorHandling", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("SAFErrorHandling", currentResult);
         currentResult.setValue("description", "<p>Specifies the error handling configuration used for the imported destinations.</p>  <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFQueues")) {
         getterName = "getSAFQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFQueues", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("SAFQueues", currentResult);
         currentResult.setValue("description", "<p>Specifies an array of SAF queues defined in this SAF Imported Destinations bean.</p>  <p>SAF Queue bean defines an imported JMS queue from a remote server in the local server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySAFQueue");
         currentResult.setValue("creator", "createSAFQueue");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFRemoteContext")) {
         getterName = "getSAFRemoteContext";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAFRemoteContext";
         }

         currentResult = new PropertyDescriptor("SAFRemoteContext", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("SAFRemoteContext", currentResult);
         currentResult.setValue("description", "<p>Specifies the remote context used for the imported destinations.</p>  <p>Changing the remote context name affects both stored messages and incoming messages.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFTopics")) {
         getterName = "getSAFTopics";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFTopics", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("SAFTopics", currentResult);
         currentResult.setValue("description", "<p>Specifies an array of SAF topics defined in this SAF Imported Destinations bean.</p>  <p>SAF Topic bean defines an imported JMS topic from a remote server in the local server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSAFTopic");
         currentResult.setValue("destroyer", "destroySAFTopic");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToLiveDefault")) {
         getterName = "getTimeToLiveDefault";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToLiveDefault";
         }

         currentResult = new PropertyDescriptor("TimeToLiveDefault", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("TimeToLiveDefault", currentResult);
         currentResult.setValue("description", "<p>Specifies the default Time-to-Live value (expiration time), in milliseconds, for imported JMS messages. The expiration time set on JMS messages will override this value unless the <code>SAF Default Time-to-Live Enabled</code> field is switched on, which then overrides the expiration time in JMS messages on imported destinations.</p>  <p>Any change to this value affects only incoming messages; stored messages are not affected.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(3600000L));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnitOfOrderRouting")) {
         getterName = "getUnitOfOrderRouting";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnitOfOrderRouting";
         }

         currentResult = new PropertyDescriptor("UnitOfOrderRouting", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("UnitOfOrderRouting", currentResult);
         currentResult.setValue("description", "<p>Specifies the type of routing used to find a SAF agent when using the message Unit-of-Order feature.</p>  <ul> <li><p><code>Hash</code> indicates that message producers use the hash code of a message Unit-of-Order to find a SAF agent.</p> </li>  <li><p><code>PathService</code> indicates that message producers use the Path Service to find a SAF agent.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "Hash");
         currentResult.setValue("legalValues", new Object[]{"Hash", "PathService"});
         currentResult.setValue("declaration", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSAFTimeToLiveDefault")) {
         getterName = "isUseSAFTimeToLiveDefault";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSAFTimeToLiveDefault";
         }

         currentResult = new PropertyDescriptor("UseSAFTimeToLiveDefault", SAFImportedDestinationsBean.class, getterName, setterName);
         descriptors.put("UseSAFTimeToLiveDefault", currentResult);
         currentResult.setValue("description", "<p>Controls whether the Time-to-Live (expiration time) value set on imported JMS messages will be overridden by the value specified in the <code>SAF Default Time-to-Live</code> field.</p>  <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SAFImportedDestinationsBean.class.getMethod("createSAFQueue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name for the SAFQueueBean to be created ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a SAF Queue bean for this SAF Imported Destinations bean.</p>  <p>SAFQueueBean defines an imported JMS queue from a remote server in the local server.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFQueues");
      }

      mth = SAFImportedDestinationsBean.class.getMethod("destroySAFQueue", SAFQueueBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safQueue", "SAFQueueBean to be destroyed in this SAFImportedDestinationsBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys a SAF Queue bean defined in this SAF Imported Destinations bean.</p>  <p>SAFQueueBean defines an imported JMS queue from a remote server in the local server.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFQueues");
      }

      mth = SAFImportedDestinationsBean.class.getMethod("createSAFTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name for the SAFTopicBean to be created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a SAF topic for this SAF Imported Destinations bean.</p>  <p>SAF Topic bean defines an imported JMS topic from a remote server in the local server.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFTopics");
      }

      mth = SAFImportedDestinationsBean.class.getMethod("destroySAFTopic", SAFTopicBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safTopic", "to be destroyed in this SAFImportedDestinationsBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroy a SAF Topic bean for this SAF Imported Destinations bean.</p>  <p>SAF Topic bean defines an imported JMS topic from a remote server in the local server.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFTopics");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SAFImportedDestinationsBean.class.getMethod("lookupSAFQueue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF Queue bean to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Finds a SAF Queue bean with the given name.</p>  <p>SAF Queue bean defines an imported JMS queue from a remote server in the local server.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFQueues");
      }

      mth = SAFImportedDestinationsBean.class.getMethod("lookupSAFTopic", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF Topic bean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Find the SAF Topic bean with the given name.</p>  <p>SAF Topic bean defines an imported JMS topic from a remote server in the local server.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFTopics");
      }

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
