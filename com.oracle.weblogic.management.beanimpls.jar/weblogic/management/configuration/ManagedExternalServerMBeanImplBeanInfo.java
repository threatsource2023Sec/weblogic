package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ManagedExternalServerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ManagedExternalServerMBean.class;

   public ManagedExternalServerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ManagedExternalServerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ManagedExternalServerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.4.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Used to configure an external server that can be managed by Node Manager. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ManagedExternalServerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("AutoRestart")) {
         getterName = "getAutoRestart";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoRestart";
         }

         currentResult = new PropertyDescriptor("AutoRestart", ManagedExternalServerMBean.class, getterName, setterName);
         descriptors.put("AutoRestart", currentResult);
         currentResult.setValue("description", "<p> Specifies whether the Node Manager can automatically restart this server if it crashes or otherwise goes down unexpectedly. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("Machine")) {
         getterName = "getMachine";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMachine";
         }

         currentResult = new PropertyDescriptor("Machine", ManagedExternalServerMBean.class, getterName, setterName);
         descriptors.put("Machine", currentResult);
         currentResult.setValue("description", "<p> The WebLogic Server host computer (machine) on which this server is meant to run. </p>  <p> If you want to use a Node Manager to start this server, you must assign the server to a machine and you must configure the machine for the Node Manager. </p>  <p> You cannot change this value if a server instance is already running. </p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("NMSocketCreateTimeoutInMillis")) {
         getterName = "getNMSocketCreateTimeoutInMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNMSocketCreateTimeoutInMillis";
         }

         currentResult = new PropertyDescriptor("NMSocketCreateTimeoutInMillis", ManagedExternalServerMBean.class, getterName, setterName);
         descriptors.put("NMSocketCreateTimeoutInMillis", currentResult);
         currentResult.setValue("description", "Returns the timeout value to be used by NodeManagerRuntime when creating a socket connection to the agent. Default set high as SSH agent may require a high connection establishment time. ");
         setPropertyDescriptorDefault(currentResult, new Integer(180000));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("deprecated", "12.2.1.2.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ManagedExternalServerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>An alphanumeric name for this server instance. (Spaces are not valid.)</p>  <p>The name must be unique for all configuration objects in the domain. Within a domain, each server, machine, cluster, JDBC connection pool, virtual host, and any other resource type must be named uniquely and must not use the same name as the domain.</p>  <p>The server name is not used as part of the URL for applications that are deployed on the server. It is for your identification purposes only. The server name displays in the Administration Console, and if you use WebLogic Server command-line utilities or APIs, you use this name to identify the server.</p>  <p>After you have created a server, you cannot change its name. Instead, clone the server and provide a new name for the clone.</p> ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("RestartDelaySeconds")) {
         getterName = "getRestartDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartDelaySeconds";
         }

         currentResult = new PropertyDescriptor("RestartDelaySeconds", ManagedExternalServerMBean.class, getterName, setterName);
         descriptors.put("RestartDelaySeconds", currentResult);
         currentResult.setValue("description", "<p> The number of seconds the Node Manager should wait before restarting this server. </p>  <p> After killing a server process, the system might need several seconds to release the TCP port(s) the server was using. If Node Manager attempts to restart the Managed Server while its ports are still active, the startup attempt fails. </p>  <p> If AutoMigration is enabled and RestartDelaySeconds is 0, the RestartDelaySeconds is automatically set to the lease time. This prevents the server from failing to restart after migration when the previous lease is still valid. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("RestartIntervalSeconds")) {
         getterName = "getRestartIntervalSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartIntervalSeconds";
         }

         currentResult = new PropertyDescriptor("RestartIntervalSeconds", ManagedExternalServerMBean.class, getterName, setterName);
         descriptors.put("RestartIntervalSeconds", currentResult);
         currentResult.setValue("description", "<p> The number of seconds during which this server can be restarted, up to the number of times specified in RestartMax. </p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRestartMax")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(3600));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(300));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("RestartMax")) {
         getterName = "getRestartMax";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartMax";
         }

         currentResult = new PropertyDescriptor("RestartMax", ManagedExternalServerMBean.class, getterName, setterName);
         descriptors.put("RestartMax", currentResult);
         currentResult.setValue("description", "<p> The number of times that the Node Manager can restart this server within the interval specified in RestartIntervalSeconds. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
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
