package weblogic.cluster.replication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.runtime.AsyncReplicationRuntimeMBean;
import weblogic.management.runtime.ReplicationRuntimeBeanInfo;

public class AsyncReplicationRuntimeBeanInfo extends ReplicationRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = AsyncReplicationRuntimeMBean.class;

   public AsyncReplicationRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public AsyncReplicationRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.cluster.replication.AsyncReplicationRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "weblogic.cluster.replication");
      String description = (new String("This MBean provides runtime statistics for async replication in a WebLogic Cluster ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.AsyncReplicationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DetailedSecondariesDistribution")) {
         getterName = "getDetailedSecondariesDistribution";
         setterName = null;
         currentResult = new PropertyDescriptor("DetailedSecondariesDistribution", AsyncReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DetailedSecondariesDistribution", currentResult);
         currentResult.setValue("description", "<p>Provides the names of the remote servers (such as myserver) for which the local server is hosting secondary objects. The name is appended with a number to indicate the number of secondaries hosted on behalf of that server.</p> ");
      }

      if (!descriptors.containsKey("LastSessionsFlushTime")) {
         getterName = "getLastSessionsFlushTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastSessionsFlushTime", AsyncReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastSessionsFlushTime", currentResult);
         currentResult.setValue("description", "<p>The last time the sessions were flushed, in milliseconds since midnight, January 1, 1970 UTC</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryCount")) {
         getterName = "getPrimaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrimaryCount", AsyncReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrimaryCount", currentResult);
         currentResult.setValue("description", "<p>Provides the number of object that the local server hosts as primaries.</p>  <p>Answer the number of object that the local server hosts as primaries.</p> ");
      }

      if (!descriptors.containsKey("SecondaryCount")) {
         getterName = "getSecondaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryCount", AsyncReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryCount", currentResult);
         currentResult.setValue("description", "<p>Answer the number of object that the local server hosts as secondaries.</p> ");
      }

      if (!descriptors.containsKey("SecondaryServerDetails")) {
         getterName = "getSecondaryServerDetails";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryServerDetails", AsyncReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryServerDetails", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("SessionsWaitingForFlushCount")) {
         getterName = "getSessionsWaitingForFlushCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SessionsWaitingForFlushCount", AsyncReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SessionsWaitingForFlushCount", currentResult);
         currentResult.setValue("description", "<p>Number of sessions which are waiting to be flushed.</p> ");
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
