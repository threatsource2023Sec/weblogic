package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ConnectionPoolParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectionPoolParamsBean.class;

   public ConnectionPoolParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionPoolParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ConnectionPoolParamsBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML connection-pool-paramsType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ConnectionPoolParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CapacityIncrement")) {
         getterName = "getCapacityIncrement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCapacityIncrement";
         }

         currentResult = new PropertyDescriptor("CapacityIncrement", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("CapacityIncrement", currentResult);
         currentResult.setValue("description", "<p>The number of connections created when new connections are added to the connection pool.</p>  <p>When there are no more available physical connections to satisfy connection requests, WebLogic Server creates this number of additional physical connections and adds them to the connection pool. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionCreationRetryFrequencySeconds")) {
         getterName = "getConnectionCreationRetryFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionCreationRetryFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ConnectionCreationRetryFrequencySeconds", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionCreationRetryFrequencySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds between attempts to establish connections to the database.</p>  <p>If you do not set this value, connection pool creation fails if the database is unavailable. If set and if the database is unavailable when the connection pool is created, WebLogic Server will attempt to create connections in the pool again after the number of seconds you specify, and will continue to attempt to create the connections until it succeeds.</p>  <p>When set to <code>0</code>, connection retry is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("ConnectionReserveTimeoutSeconds")) {
         getterName = "getConnectionReserveTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionReserveTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("ConnectionReserveTimeoutSeconds", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionReserveTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds after which a call to reserve a connection from the connection pool will timeout. <p>When set to <code>0</code>, a call will never timeout.</p> <p>When set to <code>-1</code>, a call will timeout immediately.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getHighestNumWaiters")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumUnavailable")) {
         getterName = "getHighestNumUnavailable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHighestNumUnavailable";
         }

         currentResult = new PropertyDescriptor("HighestNumUnavailable", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("HighestNumUnavailable", currentResult);
         currentResult.setValue("description", "<p>The maximum number of connections in the connection pool that can be made unavailable to applications for testing or while being refreshed.</p>  <p>Note that when the database is unavailable, this specified value could be exceeded due to factors outside the control of the connection pool.</p>  <p>When set to <code>0</code>, this feature is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumWaiters")) {
         getterName = "getHighestNumWaiters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHighestNumWaiters";
         }

         currentResult = new PropertyDescriptor("HighestNumWaiters", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("HighestNumWaiters", currentResult);
         currentResult.setValue("description", "<p>The maximum number of connection requests that can concurrently block threads while waiting to reserve a connection from the connection pool.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialCapacity")) {
         getterName = "getInitialCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialCapacity";
         }

         currentResult = new PropertyDescriptor("InitialCapacity", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("InitialCapacity", currentResult);
         currentResult.setValue("description", "<p>The number of physical connections to create when creating the connection pool. </p>  <p>If unable to create this number of connections, creation of this connection pool will fail.</p>  <p>This is also the minimum number of physical connections the connection pool will keep available.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCapacity";
         }

         currentResult = new PropertyDescriptor("MaxCapacity", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", "<p>The maximum number of physical connections that this connection pool can contain.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(15));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProfileHarvestFrequencySeconds")) {
         getterName = "getProfileHarvestFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProfileHarvestFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ProfileHarvestFrequencySeconds", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ProfileHarvestFrequencySeconds", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkFrequencySeconds")) {
         getterName = "getShrinkFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShrinkFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ShrinkFrequencySeconds", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ShrinkFrequencySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds to wait before shrinking a connection pool that has incrementally increased to meet demand. (You must also enable connection pool shrinking.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("isShrinkingEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(900));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestFrequencySeconds")) {
         getterName = "getTestFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("TestFrequencySeconds", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("TestFrequencySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds between when WebLogic Server tests unused connections. (Requires that you specify a Test Table Name.) Connections that fail the test are closed and reopened to re-establish a valid physical connection. If the test fails again, the connection is closed.</p>  <p>When set to <code>0</code>, periodic testing is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreInUseConnectionsEnabled")) {
         getterName = "isIgnoreInUseConnectionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreInUseConnectionsEnabled";
         }

         currentResult = new PropertyDescriptor("IgnoreInUseConnectionsEnabled", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("IgnoreInUseConnectionsEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the connection pool to be shutdown even if connections from the pool are still in use.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkingEnabled")) {
         getterName = "isShrinkingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShrinkingEnabled";
         }

         currentResult = new PropertyDescriptor("ShrinkingEnabled", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ShrinkingEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the connection pool to shrink back to its initial capacity or to the current number of connections in use, whichever is greater, if the connection pool detects that connections created during increased traffic are not being used.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestConnectionsOnCreate")) {
         getterName = "isTestConnectionsOnCreate";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestConnectionsOnCreate";
         }

         currentResult = new PropertyDescriptor("TestConnectionsOnCreate", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("TestConnectionsOnCreate", currentResult);
         currentResult.setValue("description", "<p>WebLogic Server to test a connection after creating it but before adding it to the list of connections available in the pool. (Requires that you specify a Test Table Name.)</p>  <p>The test adds a small delay in creating the connection, but ensures that the client receives a working connection.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestConnectionsOnRelease")) {
         getterName = "isTestConnectionsOnRelease";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestConnectionsOnRelease";
         }

         currentResult = new PropertyDescriptor("TestConnectionsOnRelease", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("TestConnectionsOnRelease", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to test a connection before returning it to the connection pool. (Requires that you specify a Test Table Name.)</p>  <p>If all connections in the pool are already in use and a client is waiting for a connection, the client's wait will be slightly longer while the connection is tested.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestConnectionsOnReserve")) {
         getterName = "isTestConnectionsOnReserve";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestConnectionsOnReserve";
         }

         currentResult = new PropertyDescriptor("TestConnectionsOnReserve", ConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("TestConnectionsOnReserve", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to test a connection before giving it to a client. (Requires that you specify a Test Table Name.)</p>  <p>The test adds a small delay in serving the client's request for a connection from the pool, but ensures that the client receives a viable connection.</p>  <p>This test is required for connection pools used in a multi data source that use the <code>failover</code> algorithm.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
