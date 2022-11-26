package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class ClassDeploymentMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClassDeploymentMBean.class;

   public ClassDeploymentMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClassDeploymentMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ClassDeploymentMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Temporary MBean for startup and shutdown classes. In the near future these will be turned into components.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ClassDeploymentMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Arguments")) {
         getterName = "getArguments";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArguments";
         }

         currentResult = new PropertyDescriptor("Arguments", ClassDeploymentMBean.class, getterName, setterName);
         descriptors.put("Arguments", currentResult);
         currentResult.setValue("description", "<p>Arguments that a server uses to initialize a class.</p>  <p>Separate multiple arguments with a comma. For example:</p>  <p><code>first=MyFirstName,last=MyLastName</code></p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClassName";
         }

         currentResult = new PropertyDescriptor("ClassName", ClassDeploymentMBean.class, getterName, setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of a class to load and run. The class must be on the server's classpath.</p>  <p>For example, <code>mycompany.mypackage.myclass</code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
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
