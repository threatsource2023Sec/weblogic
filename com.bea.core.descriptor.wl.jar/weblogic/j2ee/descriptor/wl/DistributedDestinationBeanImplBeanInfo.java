package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DistributedDestinationBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DistributedDestinationBean.class;

   public DistributedDestinationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DistributedDestinationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DistributedDestinationBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "10.3.4.0, since Weighted Distributed Destination has been deprecated and replaced by Uniform Distributed Destination ");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("This bean contains all the attributes of distributed destinations that are common between distributed queues and distributed topics. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DistributedDestinationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDIName";
         }

         currentResult = new PropertyDescriptor("JNDIName", DistributedDestinationBean.class, getterName, setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "<p>The name used to bind a virtual destination to the JNDI tree. Applications can use the JNDI name to look up the virtual destination.</p> <p>If not specified, then the destination is not bound into the JNDI namespace.</p> <p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoadBalancingPolicy")) {
         getterName = "getLoadBalancingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadBalancingPolicy";
         }

         currentResult = new PropertyDescriptor("LoadBalancingPolicy", DistributedDestinationBean.class, getterName, setterName);
         descriptors.put("LoadBalancingPolicy", currentResult);
         currentResult.setValue("description", "<p>Determines the load balancing policy for producers sending messages to a distributed destination in order to balance the message load across the members of the distributed set. </p>  <ul> <li> <b>Round-Robin</b> - The system maintains an ordering of physical topic members within the set by distributing the messaging load across the topic members one at a time in the order that they are defined in the configuration file. Each WebLogic Server maintains an identical ordering, but may be at a different point within the ordering. If weights are assigned to any of the topic members in the set, then those members appear multiple times in the ordering. </li>   <li> <b>Random</b> - The weight assigned to the topic members is used to compute a weighted distribution for the members of the set. The messaging load is distributed across the topic members by pseudo-randomly accessing the distribution. In the short run, the load will not be directly proportional to the weight. In the long run, the distribution will approach the limit of the distribution. A pure random distribution can be achieved by setting all the weights to the same value, which is typically set to 1.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "Round-Robin");
         currentResult.setValue("legalValues", new Object[]{"Round-Robin", "Random"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFExportPolicy")) {
         getterName = "getSAFExportPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAFExportPolicy";
         }

         currentResult = new PropertyDescriptor("SAFExportPolicy", DistributedDestinationBean.class, getterName, setterName);
         descriptors.put("SAFExportPolicy", currentResult);
         currentResult.setValue("description", "<p> The SAF Export Policy controls which applications can send JMS messages to this destination through Store-and-Forward.</p>  <ul>  <li> <b>All</b> - This destination allows everyone to send JMS messages to it through SAF.</li>  <li> <b>None</b> - This destination does not allow anyone to send JMS messages from a remote server to it through SAF.</li>  </ul> <p> This attribute is dynamically configurable. A dynamic change of this attribute will affect only messages sent after the update has been made. </p> ");
         setPropertyDescriptorDefault(currentResult, "All");
         currentResult.setValue("legalValues", new Object[]{"All", "None"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnitOfOrderRouting")) {
         getterName = "getUnitOfOrderRouting";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnitOfOrderRouting";
         }

         currentResult = new PropertyDescriptor("UnitOfOrderRouting", DistributedDestinationBean.class, getterName, setterName);
         descriptors.put("UnitOfOrderRouting", currentResult);
         currentResult.setValue("description", "<p>Gets the \"unit-of-order-routing\" element. </p>  <p>A WLMessageProducer with Unit of Order  uses the \"unit-of-order-routing\" element rather than the \"load-balancing-policy\" criteria to select the Distributed Destination Member. </p>  <p>Unit of Order may have been set programmatically with WLProducer, or administratively on the ConnectionFactory or Destination.  <ul> <li> <b>Hash</b> - producers will compute the Distributed Destination Member from the hash code of the Unit of Order. <li> <b>PathService</b> - the configured Path Service will determine the Distributed Destination Member. </ul> </p> ");
         setPropertyDescriptorDefault(currentResult, "Hash");
         currentResult.setValue("legalValues", new Object[]{"Hash", "PathService"});
         currentResult.setValue("dynamic", Boolean.FALSE);
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
