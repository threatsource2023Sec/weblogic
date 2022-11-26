package weblogic.servlet.cluster;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ReplicationRuntimeBeanInfo;
import weblogic.management.runtime.WANReplicationRuntimeMBean;

public class WANReplicationRuntimeBeanInfo extends ReplicationRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = WANReplicationRuntimeMBean.class;

   public WANReplicationRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WANReplicationRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.servlet.cluster.WANReplicationRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.servlet.cluster");
      String description = (new String("RuntimeMBean for WAN Replication ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WANReplicationRuntimeMBean");
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
         currentResult = new PropertyDescriptor("DetailedSecondariesDistribution", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DetailedSecondariesDistribution", currentResult);
         currentResult.setValue("description", "<p>Provides the names of the remote servers (such as myserver) for which the local server is hosting secondary objects. The name is appended with a number to indicate the number of secondaries hosted on behalf of that server.</p> ");
      }

      if (!descriptors.containsKey("NumberOfSessionsFlushedToTheDatabase")) {
         getterName = "getNumberOfSessionsFlushedToTheDatabase";
         setterName = null;
         currentResult = new PropertyDescriptor("NumberOfSessionsFlushedToTheDatabase", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumberOfSessionsFlushedToTheDatabase", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberOfSessionsRetrievedFromTheDatabase")) {
         getterName = "getNumberOfSessionsRetrievedFromTheDatabase";
         setterName = null;
         currentResult = new PropertyDescriptor("NumberOfSessionsRetrievedFromTheDatabase", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumberOfSessionsRetrievedFromTheDatabase", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrimaryCount")) {
         getterName = "getPrimaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrimaryCount", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrimaryCount", currentResult);
         currentResult.setValue("description", "<p>Provides the number of object that the local server hosts as primaries.</p>  <p>Answer the number of object that the local server hosts as primaries.</p> ");
      }

      if (!descriptors.containsKey("RemoteClusterReachable")) {
         getterName = "getRemoteClusterReachable";
         setterName = null;
         currentResult = new PropertyDescriptor("RemoteClusterReachable", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RemoteClusterReachable", currentResult);
         currentResult.setValue("description", "Answer if the remote cluster is reachable or not. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondaryCount")) {
         getterName = "getSecondaryCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryCount", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryCount", currentResult);
         currentResult.setValue("description", "<p>Answer the number of object that the local server hosts as secondaries.</p> ");
      }

      if (!descriptors.containsKey("SecondaryServerDetails")) {
         getterName = "getSecondaryServerDetails";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryServerDetails", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryServerDetails", currentResult);
         currentResult.setValue("description", " ");
      }

      if (!descriptors.containsKey("SecondaryServerName")) {
         getterName = "getSecondaryServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("SecondaryServerName", WANReplicationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SecondaryServerName", currentResult);
         currentResult.setValue("description", "<p>Answer the current secondary server </p> ");
         currentResult.setValue("deprecated", "10.3.0.0 deprecated in favor of getSecondaryServerDetails method ");
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
      Method mth = WANReplicationRuntimeMBean.class.getMethod("cleanupExpiredSessionsInTheDatabase");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Clean up expired sessions in the database</p> ");
         currentResult.setValue("role", "operation");
      }

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
