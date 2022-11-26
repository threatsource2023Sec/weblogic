package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SubDeploymentMBeanImplBeanInfo extends TargetInfoMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SubDeploymentMBean.class;

   public SubDeploymentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SubDeploymentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SubDeploymentMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean represents an individually targetable entity within a deployment package, which is deployable on WLS. This includes : <p> Modules in an EAR </p> <p> JMS resources within a app scoped JMS module in an EAR </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SubDeploymentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", SubDeploymentMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "Unique identifier for this bean instance. It is used by the application container to match the module in the application package to the targeting information in the configuration.  <p>For modules within an EAR, the name should be the URI of the module as defined in the META-INF/application.xml deployment descriptor. There is an exception to this for web applications. See below.</p> <p>For web modules in an EAR, the name should always equal the context root of that webapp, because the URI is not always unique</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubDeployments")) {
         getterName = "getSubDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("SubDeployments", SubDeploymentMBean.class, getterName, setterName);
         descriptors.put("SubDeployments", currentResult);
         currentResult.setValue("description", "<p>The subdeployment groups within this JMS module. Subdeployments enable you to deploy some resources in a JMS module to a JMS server and other JMS resources to a server instance or cluster.</p>  <p>Standalone queues or topics can only be targeted to a single JMS server. Whereas, connection factories, uniform distributed destinations (UDDs), and foreign servers can be targeted to one or more JMS servers, one or more server instances, or to a cluster. Therefore, standalone queues or topics cannot be associated with a subdeployment if other members of the subdeployment are targeted to multiple JMS servers. However, UDDs can be associated with such subdeployments since the purpose of UDDs is to distribute its members to multiple JMS servers in a domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Untargeted")) {
         getterName = "isUntargeted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUntargeted";
         }

         currentResult = new PropertyDescriptor("Untargeted", SubDeploymentMBean.class, getterName, setterName);
         descriptors.put("Untargeted", currentResult);
         currentResult.setValue("description", "<p>Only relevant for deployments in resource group templates. Sub-deployments cannot have arbitrary targets if they are part of a deployment from resource group template. The sub-deployments, in such cases, may only be targeted to the virtual host of referring partition or stay untargeted. This flag, when specified with true, indicates that the sub-deployment must not be deployed in the referring partition</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = SubDeploymentMBean.class.getMethod("lookupSubDeployment", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SubDeployments");
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
