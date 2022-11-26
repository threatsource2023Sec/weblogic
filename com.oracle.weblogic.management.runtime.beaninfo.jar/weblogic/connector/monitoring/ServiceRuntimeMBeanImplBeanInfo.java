package weblogic.connector.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;

public class ServiceRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectorServiceRuntimeMBean.class;

   public ServiceRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServiceRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.connector.monitoring.ServiceRuntimeMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "6.1.0.0");
      beanDescriptor.setValue("package", "weblogic.connector.monitoring");
      String description = (new String("This interface defines the runtime information that can be accessed at a connector service level. Runtime information can be accessed at a per resource adapter level or at an overall level. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConnectorServiceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveRACount")) {
         getterName = "getActiveRACount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveRACount", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveRACount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of resource adapters that are active.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ActiveRAs")) {
         getterName = "getActiveRAs";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveRAs", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveRAs", currentResult);
         currentResult.setValue("description", "<p>Returns an array of runtime information for all deployed and active resource adapters.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPoolCurrentCount")) {
         getterName = "getConnectionPoolCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionPoolCurrentCount", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionPoolCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of connection pools in all active RAs.<p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPools")) {
         getterName = "getConnectionPools";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionPools", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionPools", currentResult);
         currentResult.setValue("description", "Returns connection pool runtimes for all active resource adapters. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPoolsTotalCount")) {
         getterName = "getConnectionPoolsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionPoolsTotalCount", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionPoolsTotalCount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of resource adapter connection pools that have been created since server startup. This includes re-deployments.</p>  Returns the total number of deployed connection pools instantiated since server startup. ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InactiveRAs")) {
         getterName = "getInactiveRAs";
         setterName = null;
         currentResult = new PropertyDescriptor("InactiveRAs", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InactiveRAs", currentResult);
         currentResult.setValue("description", "<p>Returns an array of runtime information for all deployed and inactive resource adapters.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RACount")) {
         getterName = "getRACount";
         setterName = null;
         currentResult = new PropertyDescriptor("RACount", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RACount", currentResult);
         currentResult.setValue("description", "<p>Returns the number of resource adapters that have been deployed in the server. This count includes active RAs and Non-active RAs ( in the case of versioned RAs that are being replaced by a new version )</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RAs")) {
         getterName = "getRAs";
         setterName = null;
         currentResult = new PropertyDescriptor("RAs", ConnectorServiceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RAs", currentResult);
         currentResult.setValue("description", "<p>Returns an array of runtime information for all deployed resource adapters.</p> ");
         currentResult.setValue("relationship", "containment");
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
      Method mth = ConnectorServiceRuntimeMBean.class.getMethod("getRA", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("key", "The JNDI name of the resource adapter. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the runtime information of the resource adapter specified by the given JNDI name. A null is returned if the resource adapter cannot be found. This function returns the active RA if multiple versions of the resource adapters has been deployed</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("getConnectionPool", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("key", "by which to find the connection pool. this could be the jndi name or the resource-link. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns a ConnectorConnectionPoolRuntimeMBean that represents the statistics for a Deployed Connection Pool (a deployed connection pool is equivalent to a deployed RAR). ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("getInboundConnections", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("messageListenerType", "Message listener type. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns runtime information for the specified inbound connection. A null is returned if the inbound connection is not found.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("suspendAll", Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("props", "Properties to be passed to the resource adapters or null ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends all activities of all resource adapters.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable "), createParameterDescriptor("props", "Properties to pass on to the RA or null ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends the specified type of activity for all RAs</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("resumeAll", Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("props", "Properties to be passed to the resource adapters or null ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes all activities of all resource adapters.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("resume", Integer.TYPE, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable "), createParameterDescriptor("props", "Properties to pass on to the RA or null ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the specified type of activity for all RAs</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("suspendAll");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends all activities of all resource adapters.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("suspend", Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends the specified type of activity for all RAs.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("resumeAll");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes all activities of all resource adapters.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorServiceRuntimeMBean.class.getMethod("resume", Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the specified type of activity for all RAs.</p> ");
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
