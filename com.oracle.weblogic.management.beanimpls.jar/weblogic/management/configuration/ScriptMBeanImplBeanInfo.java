package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class ScriptMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ScriptMBean.class;

   public ScriptMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ScriptMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ScriptMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ScriptMBean");
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

         currentResult = new PropertyDescriptor("Arguments", ScriptMBean.class, getterName, setterName);
         descriptors.put("Arguments", currentResult);
         currentResult.setValue("description", "The arguments to the command script as well as for the error-handler script ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Environment")) {
         getterName = "getEnvironment";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnvironment";
         }

         currentResult = new PropertyDescriptor("Environment", ScriptMBean.class, getterName, setterName);
         descriptors.put("Environment", currentResult);
         currentResult.setValue("description", "Returns the properties used to create environment for the script sub-process. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberOfRetriesAllowed")) {
         getterName = "getNumberOfRetriesAllowed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumberOfRetriesAllowed";
         }

         currentResult = new PropertyDescriptor("NumberOfRetriesAllowed", ScriptMBean.class, getterName, setterName);
         descriptors.put("NumberOfRetriesAllowed", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PathToErrorHandlerScript")) {
         getterName = "getPathToErrorHandlerScript";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPathToErrorHandlerScript";
         }

         currentResult = new PropertyDescriptor("PathToErrorHandlerScript", ScriptMBean.class, getterName, setterName);
         descriptors.put("PathToErrorHandlerScript", currentResult);
         currentResult.setValue("description", "Returns the path to the error handler script to be executed. If null or empty, the script will not be executed. The value should be the path to the script program. If the command is not set, the error handler is effectively disabled. Note that the error handler script is executed using the same arguments and and environment as the command script. When specified as a relative path, it will be relative to <code><i>DomainDir</i>/bin/scripts</code> directory. When specified as an absolute path, the script must be under <code><i>DomainDir</i>/bin/scripts</code> directory. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PathToScript")) {
         getterName = "getPathToScript";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPathToScript";
         }

         currentResult = new PropertyDescriptor("PathToScript", ScriptMBean.class, getterName, setterName);
         descriptors.put("PathToScript", currentResult);
         currentResult.setValue("description", "Returns the path to the script to be executed. If null or empty, the script will not be executed. The value should be the path to the script program. If the command is not set, the script is effectively disabled. When specified as a relative path, it will be relative to <code><i>DomainDir</i>/bin/scripts</code> directory. When specified as an absolute path, the script must be under <code><i>DomainDir</i>/bin/scripts</code> directory. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryDelayInMillis")) {
         getterName = "getRetryDelayInMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryDelayInMillis";
         }

         currentResult = new PropertyDescriptor("RetryDelayInMillis", ScriptMBean.class, getterName, setterName);
         descriptors.put("RetryDelayInMillis", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Long(2000L));
         currentResult.setValue("legalMin", new Long(0L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeoutInSeconds")) {
         getterName = "getTimeoutInSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTimeoutInSeconds";
         }

         currentResult = new PropertyDescriptor("TimeoutInSeconds", ScriptMBean.class, getterName, setterName);
         descriptors.put("TimeoutInSeconds", currentResult);
         currentResult.setValue("description", "Returns the timeout interval for script execution in seconds. A zero or negative timeout will imply no timeout. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkingDirectory")) {
         getterName = "getWorkingDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkingDirectory";
         }

         currentResult = new PropertyDescriptor("WorkingDirectory", ScriptMBean.class, getterName, setterName);
         descriptors.put("WorkingDirectory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreFailures")) {
         getterName = "isIgnoreFailures";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreFailures";
         }

         currentResult = new PropertyDescriptor("IgnoreFailures", ScriptMBean.class, getterName, setterName);
         descriptors.put("IgnoreFailures", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
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
