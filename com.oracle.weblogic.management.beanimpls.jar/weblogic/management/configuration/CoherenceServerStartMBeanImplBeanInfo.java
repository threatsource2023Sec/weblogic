package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class CoherenceServerStartMBeanImplBeanInfo extends ManagedExternalServerStartMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceServerStartMBean.class;

   public CoherenceServerStartMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceServerStartMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CoherenceServerStartMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("dynamic", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean is used to configure the attributes necessary to start up a server on a remote machine.</p>  <p>The following describes how NodeManager applies each property when starting the Coherence server.</p>  <p>#getJavaVendor</p> <p>The NodeManager does not pass this value invoking a java command line to start the server.  It does pass this value in the environment variable JAVA_VENDOR to the start script. </p>  <p>#getJavaHome</p> <p>This value can also be specified conveniently in the nodemanager .properties file using the coherence.startup.JavaHome or property. The NodeManager will pass this value to a start script using the JAVA_HOME environment variable.  When issuing a java command line to start the server, the NodeManager will use the java executable from the specified location. </p>   <p>#getClassPath</p> <p>The NodeManager will pass this value to a start script using the JAVA_OPTIONS environment variable containing -Djava.class.path.  When issuinga java command line to start the server, the NodeManager will pass -Djava.class.path.</p>  <p>#getMWHome</p> <p>This value can also be specified conveniently in the nodemanager .properties file using the coherence.startup.MWHome property. The NodeManager does not pass this value directly when starting a server.  However, it uses this value to construct an appropriate classpath for starting the server.</p>  <p>#getArguments</p> <p>This value can also be specified conveniently in the nodemanager .properties file using the coherence.startup.Arguments property. The NodeManager will pass this value to a start script using the JAVA_OPTIONS environment variable.  When issuing a java command line to start the server, the NodeManager will pass the arguments as options. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CoherenceServerStartMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
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
