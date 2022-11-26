package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ClientSAFBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClientSAFBean.class;

   public ClientSAFBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClientSAFBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ClientSAFBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("The top of the JMS module bean tree. <p> JMS modules all have a JMSBean as their root bean (a bean with no parent).  The schema namespace that corresponds to this bean is \"http://xmlns.oracle.com/weblogic/weblogic-jms\" </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ClientSAFBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConnectionFactories")) {
         getterName = "getConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactories", ClientSAFBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionFactories", currentResult);
         currentResult.setValue("description", "Gets all connection factory beans found in the JMS module  <p> Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConnectionFactory");
         currentResult.setValue("creator", "createConnectionFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentStore")) {
         getterName = "getPersistentStore";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistentStore", ClientSAFBean.class, getterName, (String)setterName);
         descriptors.put("PersistentStore", currentResult);
         currentResult.setValue("description", "Gets the configuration information about the default persistent store.  Note that the name for this persistent store must always be \"default-store.\"  All of the other attributes may be configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFAgent")) {
         getterName = "getSAFAgent";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFAgent", ClientSAFBean.class, getterName, (String)setterName);
         descriptors.put("SAFAgent", currentResult);
         currentResult.setValue("description", "Gets the configuration information for the default SAF agent. The name of the default saf agent must always be \"default-agent\" And the store must always be \"default-store\".  All other field may be configured here. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFErrorHandlings")) {
         getterName = "getSAFErrorHandlings";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFErrorHandlings", ClientSAFBean.class, getterName, (String)setterName);
         descriptors.put("SAFErrorHandlings", currentResult);
         currentResult.setValue("description", "Get an array of SAFErrorHandlingBean defined in this module <p> SAFErrorHandlingBena defines what has to be done for messages that cannot be forwarded by SAFAgents </P> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSAFErrorHandling");
         currentResult.setValue("destroyer", "destroySAFErrorHandling");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFImportedDestinations")) {
         getterName = "getSAFImportedDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFImportedDestinations", ClientSAFBean.class, getterName, (String)setterName);
         descriptors.put("SAFImportedDestinations", currentResult);
         currentResult.setValue("description", "Gets all SAFImportedDestinationsBean found in the JMS module  <p> SAFImportedDestinationsBean defines a set of SAFQueues and or SAFTopics imported in the local cluster from the remote Cluster or server </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSAFImportedDestinations");
         currentResult.setValue("destroyer", "destroySAFImportedDestinations");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFRemoteContexts")) {
         getterName = "getSAFRemoteContexts";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFRemoteContexts", ClientSAFBean.class, getterName, (String)setterName);
         descriptors.put("SAFRemoteContexts", currentResult);
         currentResult.setValue("description", "Get an array of SAFRemoteContextBean for this JMS module <p> SAFRemoteContextBean defines the parameters to be used by the JMS Imported Destinations (SAFQueue or SAFTopic) to connect ot the remote Cluster. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySAFRemoteContext");
         currentResult.setValue("creator", "createSAFRemoteContext");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ClientSAFBean.class.getMethod("createConnectionFactory", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the connection factory to add to this JMS module ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a connection factory bean and adds it to this JMS module  <p> Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionFactories");
      }

      mth = ClientSAFBean.class.getMethod("destroyConnectionFactory", JMSConnectionFactoryBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("connectionFactory", "The connection factory bean to remove from this JMS module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a connection factory bean from this JMS module  <p> Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionFactories");
      }

      mth = ClientSAFBean.class.getMethod("createSAFImportedDestinations", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAFImportedDestinations to be created. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create a SAFImportedDestinationsBean in this JMSModule <p> SAFImportedDestinationsBean defines a set of SAFQueues and or SAFTopics imported in the local cluster from the remote Cluster or server </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFImportedDestinations");
      }

      mth = ClientSAFBean.class.getMethod("destroySAFImportedDestinations", SAFImportedDestinationsBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safImportedDestinations", "SAFImportedDestinationsBean defined in this module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy a SAFImportedDestinationsBean in this JMS Module <p> SAFImportedDestinationsBean defines a set of SAFQueues and or SAFTopics imported in the local cluster from the remote Cluster or server </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFImportedDestinations");
      }

      mth = ClientSAFBean.class.getMethod("createSAFRemoteContext", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAFRemoteContextBean to be created. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create SAFRemoteContextBean in this module <p> SAFRemoteContextBean defines the parameters to be used by the JMS Imported Destinations (SAFQueue or SAFTopic) to connect ot the remote Cluster. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFRemoteContexts");
      }

      mth = ClientSAFBean.class.getMethod("destroySAFRemoteContext", SAFRemoteContextBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safRemoteContext", "SAFRemoteContextBean in this module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy SAFRemoteContextBean in this module <p> SAFRemoteContextBean defines the parameters to be used by the JMS Imported Destinations (SAFQueue or SAFTopic) to connect ot the remote Cluster. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFRemoteContexts");
      }

      mth = ClientSAFBean.class.getMethod("createSAFErrorHandling", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAFErrorHandlingBean to be created. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Create SAFErrorHandlingBean in this module <p> SAFErrorHandlingBeaa defines what has to be done for messages that cannot be forwarded by SAFAgents </P> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFErrorHandlings");
      }

      mth = ClientSAFBean.class.getMethod("destroySAFErrorHandling", SAFErrorHandlingBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("safErrorHandling", "SAFErrorhandlingBean defined in this module ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroy SAFErrorHandlingBean defined in this module <p> SAFErrorHandlingBean defines what has to be done for messages that cannot be forwarded by SAFAgents </P> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFErrorHandlings");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ClientSAFBean.class.getMethod("lookupConnectionFactory", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the connection factory to locate ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a connection factory bean with the given name  <p> Connection factories are used to create connections for JMS clients.  Connection factories can configure properties of the connections returned to the JMS client. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ConnectionFactories");
      }

      mth = ClientSAFBean.class.getMethod("lookupSAFImportedDestinations", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF imported destinations to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a SAF Imported Destinations bean with the given name <p> SAFImportedDestinationsBean defines a set of SAFQueues and or SAFTopics imported in the local cluster from the remote Cluster or server </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFImportedDestinations");
      }

      mth = ClientSAFBean.class.getMethod("lookupSAFRemoteContext", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF Remote Context to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a SAF Remote Context bean with the given name <p> SAFRemoteContextBean defines the parameters to be used by the JMS Imported Destinations (SAFQueue or SAFTopic) to connect ot the remote Cluster. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFRemoteContexts");
      }

      mth = ClientSAFBean.class.getMethod("lookupSAFErrorHandling", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the SAF Error Handling to locate ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Locates a SAF Error Handling bean with the given name <p> SAFErrorHandlingBean defines what has to be done for messages that cannot be forwarded by SAFAgents </P> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFErrorHandlings");
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
