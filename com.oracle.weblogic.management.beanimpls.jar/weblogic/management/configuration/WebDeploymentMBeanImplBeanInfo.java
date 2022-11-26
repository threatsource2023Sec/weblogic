package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WebDeploymentMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WebDeploymentMBean.class;

   public WebDeploymentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WebDeploymentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WebDeploymentMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>A Web Deployment is any MBean that may be deployed on one or more target or WebServers. Deployments of this type get deployed on web servers. Any target specified through the \"Targets\" attribute of the deployment are deployed on the default web server of that deployment. Targets specified through the \"WebServers\" attribute of the deployment are specified in the targeted Web Server.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WebDeploymentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("7.0.0.0.", (String)null, this.targetVersion) && !descriptors.containsKey("VirtualHosts")) {
         getterName = "getVirtualHosts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVirtualHosts";
         }

         currentResult = new PropertyDescriptor("VirtualHosts", WebDeploymentMBean.class, getterName, setterName);
         descriptors.put("VirtualHosts", currentResult);
         currentResult.setValue("description", "<p>Provides a means to target your deployments to specific virtual hosts.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("remover", "removeVirtualHost");
         currentResult.setValue("adder", "addVirtualHost");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0.");
      }

      if (!descriptors.containsKey("WebServers")) {
         getterName = "getWebServers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWebServers";
         }

         currentResult = new PropertyDescriptor("WebServers", WebDeploymentMBean.class, getterName, setterName);
         descriptors.put("WebServers", currentResult);
         currentResult.setValue("description", "<p>Returns a list of the targets on which this deployment is deployed.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addWebServer");
         currentResult.setValue("remover", "removeWebServer");
         currentResult.setValue("deprecated", "7.0.0.0 This attribute is being replaced by VirtualHosts attribute. To target  an actual web server, the ComponentMBean.Targets attribute should be used. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WebDeploymentMBean.class.getMethod("addWebServer", WebServerMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the WebServer attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "7.0.0.0 This attribute is being replaced by VirtualHosts attribute ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This adds a target to the list of web servers to which a deployment may be targeted.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "WebServers");
      }

      mth = WebDeploymentMBean.class.getMethod("removeWebServer", WebServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "7.0.0.0 This attribute is being replaced by VirtualHosts attribute ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This removes a target from the list of web servers which may be targeted for deployments.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "WebServers");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0.", (String)null, this.targetVersion)) {
         mth = WebDeploymentMBean.class.getMethod("addVirtualHost", VirtualHostMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", "The feature to be added to the VirtualHost attribute ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0.");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Used to add a virtual host to the list of virtual hosts to which deployments may be targeted.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "VirtualHosts");
            currentResult.setValue("since", "7.0.0.0.");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0.", (String)null, this.targetVersion)) {
         mth = WebDeploymentMBean.class.getMethod("removeVirtualHost", VirtualHostMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0.");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Used to remove a virtual host from the list of virtual hosts to which deployments may be targeted.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "VirtualHosts");
            currentResult.setValue("since", "7.0.0.0.");
         }
      }

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
