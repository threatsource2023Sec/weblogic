package weblogic.server.channels;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ServerChannelRuntimeMBean;

public class ServerChannelRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServerChannelRuntimeMBean.class;

   public ServerChannelRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerChannelRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.server.channels.ServerChannelRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.server.channels");
      String description = (new String("Runtime information for NetworkAccessPoints or \"Channels\". ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServerChannelRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AcceptCount")) {
         getterName = "getAcceptCount";
         setterName = null;
         currentResult = new PropertyDescriptor("AcceptCount", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AcceptCount", currentResult);
         currentResult.setValue("description", "<p>The number of sockets that have been accepted on this channel. This includes sockets both past and present so gives a good idea of the connection rate to the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AssociatedVirtualTargetName")) {
         getterName = "getAssociatedVirtualTargetName";
         setterName = null;
         currentResult = new PropertyDescriptor("AssociatedVirtualTargetName", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AssociatedVirtualTargetName", currentResult);
         currentResult.setValue("description", "<p>Associated virtual-target name with this channel. This may be null.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("BytesReceivedCount")) {
         getterName = "getBytesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesReceivedCount", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The total number of bytes received on this channel.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BytesSentCount")) {
         getterName = "getBytesSentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("BytesSentCount", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("BytesSentCount", currentResult);
         currentResult.setValue("description", "<p>The total number of bytes sent on this channel.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChannelName")) {
         getterName = "getChannelName";
         setterName = null;
         currentResult = new PropertyDescriptor("ChannelName", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ChannelName", currentResult);
         currentResult.setValue("description", "<p>The channel name of this channel.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsCount")) {
         getterName = "getConnectionsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsCount", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsCount", currentResult);
         currentResult.setValue("description", "<p>The number of active connections and sockets associated with this channel.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesReceivedCount")) {
         getterName = "getMessagesReceivedCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesReceivedCount", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesReceivedCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages received on this channel.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesSentCount")) {
         getterName = "getMessagesSentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagesSentCount", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MessagesSentCount", currentResult);
         currentResult.setValue("description", "<p>The number of messages sent on this channel.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublicURL")) {
         getterName = "getPublicURL";
         setterName = null;
         currentResult = new PropertyDescriptor("PublicURL", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PublicURL", currentResult);
         currentResult.setValue("description", "<p>The physical URL that this channel is listening on.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerConnectionRuntimes")) {
         getterName = "getServerConnectionRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerConnectionRuntimes", ServerChannelRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServerConnectionRuntimes", currentResult);
         currentResult.setValue("description", "<p>The active connections and sockets associated with this channel.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for ServerConnectionRuntime");
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
