package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class EJBContainerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = EJBContainerMBean.class;

   public EJBContainerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public EJBContainerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.EJBContainerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean is used to specify EJB container-wide settings.  These can be overridden by a specific EJBComponentMBean. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.EJBContainerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ExtraEjbcOptions")) {
         getterName = "getExtraEjbcOptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExtraEjbcOptions";
         }

         currentResult = new PropertyDescriptor("ExtraEjbcOptions", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("ExtraEjbcOptions", currentResult);
         currentResult.setValue("description", "<p>Returns the extra options passed to ejbc during the dynamic ejbc of a jar file. For example: -J-mx128m By default this value is null. If no ExtraEJBCOptions are specified on the EJBComponent, the default will be pulled from the Server.ExtraEJBCOptions.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExtraRmicOptions")) {
         getterName = "getExtraRmicOptions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExtraRmicOptions";
         }

         currentResult = new PropertyDescriptor("ExtraRmicOptions", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("ExtraRmicOptions", currentResult);
         currentResult.setValue("description", "<p>The extra options passed to rmic during server-side generation are noted here. The default for this attribute must be null. If no ExtraRmicOptions are specified on the EJBComponent, the default will be pulled from Server.ExtraRmicOptions.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ForceGeneration")) {
         getterName = "getForceGeneration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setForceGeneration";
         }

         currentResult = new PropertyDescriptor("ForceGeneration", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("ForceGeneration", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the ForceGeneration is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaCompiler")) {
         getterName = "getJavaCompiler";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompiler";
         }

         currentResult = new PropertyDescriptor("JavaCompiler", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("JavaCompiler", currentResult);
         currentResult.setValue("description", "<p>The path to the Java compiler to use to compile EJBs (e.g. \"sj\" or \"javac\"). Note: the default must be null. If no JavaCompiler is specified on this specific EJBComponent, the default will be pulled in the following order from - EJBContainerMBean - Server.JavaCompiler.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaCompilerPostClassPath")) {
         getterName = "getJavaCompilerPostClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompilerPostClassPath";
         }

         currentResult = new PropertyDescriptor("JavaCompilerPostClassPath", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("JavaCompilerPostClassPath", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the options to append to the Java compiler classpath when you compile Java code.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaCompilerPreClassPath")) {
         getterName = "getJavaCompilerPreClassPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaCompilerPreClassPath";
         }

         currentResult = new PropertyDescriptor("JavaCompilerPreClassPath", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("JavaCompilerPreClassPath", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the options to prepend to the Java compiler classpath when you compile Java code.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("secureValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepGenerated")) {
         getterName = "getKeepGenerated";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepGenerated";
         }

         currentResult = new PropertyDescriptor("KeepGenerated", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("KeepGenerated", currentResult);
         currentResult.setValue("description", "<p>indicates whether KeepGenerated is enabled and the ejbc source files will be kept.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TmpPath")) {
         getterName = "getTmpPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTmpPath";
         }

         currentResult = new PropertyDescriptor("TmpPath", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("TmpPath", currentResult);
         currentResult.setValue("description", "<p>Return the temporary directory where generated files are stored by ejbc. Deprecated: All EJB compiler output is now stored in the EJBCompilerCache subdirectory of the server staging directory. This directory should not be described as \"temporary\" since removing it would cause the EJB compiler to be rerun as necessary the next time the server is restarted.</p> ");
         setPropertyDescriptorDefault(currentResult, "tmp_ejb");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VerboseEJBDeploymentEnabled")) {
         getterName = "getVerboseEJBDeploymentEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setVerboseEJBDeploymentEnabled";
         }

         currentResult = new PropertyDescriptor("VerboseEJBDeploymentEnabled", EJBContainerMBean.class, getterName, setterName);
         descriptors.put("VerboseEJBDeploymentEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the VerboseEJBDeployment is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, "false");
         currentResult.setValue("deprecated", "Deprecated as of 10.3.3.0 in favor of {@link ServerDebugMBean#getDebugEjbDeployment()} ");
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
