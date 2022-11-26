package weblogic.cluster.messaging.internal.server;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.UnicastMessagingRuntimeMBean;

public class UnicastMessagingRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UnicastMessagingRuntimeMBean.class;

   public UnicastMessagingRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UnicastMessagingRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.messaging.internal.server.UnicastMessagingRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.cluster.messaging.internal.server");
      String description = (new String("Monitoring information when unicast messaging is turned on ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.UnicastMessagingRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DiscoveredGroupLeaders")) {
         getterName = "getDiscoveredGroupLeaders";
         setterName = null;
         currentResult = new PropertyDescriptor("DiscoveredGroupLeaders", UnicastMessagingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DiscoveredGroupLeaders", currentResult);
         currentResult.setValue("description", "Running group leader names ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Groups")) {
         getterName = "getGroups";
         setterName = null;
         currentResult = new PropertyDescriptor("Groups", UnicastMessagingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Groups", currentResult);
         currentResult.setValue("description", "Formatted group list ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalGroupLeaderName")) {
         getterName = "getLocalGroupLeaderName";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalGroupLeaderName", UnicastMessagingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LocalGroupLeaderName", currentResult);
         currentResult.setValue("description", "Name of the local group leader ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteGroupsDiscoveredCount")) {
         getterName = "getRemoteGroupsDiscoveredCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RemoteGroupsDiscoveredCount", UnicastMessagingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RemoteGroupsDiscoveredCount", currentResult);
         currentResult.setValue("description", "Returns the total groups discovered by this server ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalGroupsCount")) {
         getterName = "getTotalGroupsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalGroupsCount", UnicastMessagingRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalGroupsCount", currentResult);
         currentResult.setValue("description", "Total configured groups - running and not running ");
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
