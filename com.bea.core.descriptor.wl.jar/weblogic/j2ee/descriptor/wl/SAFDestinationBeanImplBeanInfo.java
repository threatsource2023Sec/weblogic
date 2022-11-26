package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class SAFDestinationBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SAFDestinationBean.class;

   public SAFDestinationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SAFDestinationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.SAFDestinationBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Store-and-Forward (SAF) destinations are used for asynchronous and disconnected peer communications. A message delivered to a SAF queue or a SAF topic will be forwarded to a queue or a topic in a remote cluster or server. Aspects of a SAF queues and topics behavior can be configured with a SAF queue or topic bean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.SAFDestinationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("LocalJNDIName")) {
         getterName = "getLocalJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocalJNDIName";
         }

         currentResult = new PropertyDescriptor("LocalJNDIName", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("LocalJNDIName", currentResult);
         currentResult.setValue("description", "<p>The local JNDI name of the remote destination.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageLoggingParams")) {
         getterName = "getMessageLoggingParams";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageLoggingParams", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("MessageLoggingParams", currentResult);
         currentResult.setValue("description", "<p>These parameters control how the SAF destination performs message logging.</p>  <p>They allow the adminstrator to configure the SAF destination to change message logging when message life cycle changes are detected.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NonPersistentQos")) {
         getterName = "getNonPersistentQos";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNonPersistentQos";
         }

         currentResult = new PropertyDescriptor("NonPersistentQos", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("NonPersistentQos", currentResult);
         currentResult.setValue("description", "<p>Specifies the quality-of-service for non-persistent messages.</p> <ul> <li> <p><code>Exactly-Once</code> indicates that messages will be forwarded to the remote side once and only once except for any occurrence of server crashes.</p> </li> <li> <p><code>At-Least-Once</code> indicates that messages will be forwarded to the remote side at least once. No message will be lost except for any occurrence of server crashes. However, messages may appear in the remote endpoint more than once.</p> </li> <li> <p><code>At-Most-Once</code> indicates that messages will be forwarded to the remote side atmost once. No message will appear in the remote endpoint more than once. However, messages may get lost.</p> </li> </ul> <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         setPropertyDescriptorDefault(currentResult, "At-Least-Once");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"At-Most-Once", "At-Least-Once", "Exactly-Once"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentQos")) {
         getterName = "getPersistentQos";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentQos";
         }

         currentResult = new PropertyDescriptor("PersistentQos", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("PersistentQos", currentResult);
         currentResult.setValue("description", "<p>Specifies the quality-of-service for persistent messages.</p> <ul> <li> <p><code>Exactly-Once</code> indicates that messages will be forwarded to the remote side once and only once</p> </li> <li> <p><code>At-Least-Once</code> indicates that messages will be forwarded to the remote side at least once. Messages may appear in the remote endpoint more than once.</p> </li> <li> <p><code>At-Most-Once</code> indicates that messages will be forwarded to the remote side atmost once. No message will appear in the remote endpoint more than once. However, messages may get lost.</p> </li> </ul> <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         setPropertyDescriptorDefault(currentResult, "Exactly-Once");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"At-Most-Once", "At-Least-Once", "Exactly-Once"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteJNDIName")) {
         getterName = "getRemoteJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteJNDIName";
         }

         currentResult = new PropertyDescriptor("RemoteJNDIName", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("RemoteJNDIName", currentResult);
         currentResult.setValue("description", "<p>The remote JNDI name of the remote destination.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFErrorHandling")) {
         getterName = "getSAFErrorHandling";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAFErrorHandling";
         }

         currentResult = new PropertyDescriptor("SAFErrorHandling", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("SAFErrorHandling", currentResult);
         currentResult.setValue("description", "<p>Specifies the error handling configuration used by this SAF destination.</p>  <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeToLiveDefault")) {
         getterName = "getTimeToLiveDefault";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeToLiveDefault";
         }

         currentResult = new PropertyDescriptor("TimeToLiveDefault", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("TimeToLiveDefault", currentResult);
         currentResult.setValue("description", "<p>Specifies the default Time-to-Live value (expiration time), in milliseconds, for imported JMS messages. The expiration time set on JMS messages will override this value unless the <code>SAF Default Time-to-Live Enabled</code> field is switched on, which then overrides the expiration time in JMS messages on imported destinations.</p>  <p>Any change to this value affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
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

         currentResult = new PropertyDescriptor("UnitOfOrderRouting", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("UnitOfOrderRouting", currentResult);
         currentResult.setValue("description", "<p>Specifies the type of routing used to find a SAF agent when using the message Unit-of-Order feature.</p>  <ul> <li><p><code>Hash</code> indicates that producers use the hash code of a message Unit-of-Order to find a SAF agent.</p> </li>  <li><p><code>PathService</code> indicates that producers use the Path Service to find a SAF agent.</p> </li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
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

         currentResult = new PropertyDescriptor("UseSAFTimeToLiveDefault", SAFDestinationBean.class, getterName, setterName);
         descriptors.put("UseSAFTimeToLiveDefault", currentResult);
         currentResult.setValue("description", "<p>Controls whether the Time-to-Live (expiration time) value set on imported JMS messages will be overridden by the value specified in the <code>SAF Default Time-to-Live</code> field.</p>  <p>Any change to this parameter affects only incoming messages; stored messages are not affected.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
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
