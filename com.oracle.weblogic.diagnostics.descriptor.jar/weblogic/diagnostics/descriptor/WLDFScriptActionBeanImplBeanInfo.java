package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFScriptActionBeanImplBeanInfo extends WLDFNotificationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFScriptActionBean.class;

   public WLDFScriptActionBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFScriptActionBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFScriptActionBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p> Configures an action that can execute an external command-line process.  This action type can be used to execute custom scripts when a policy rule is triggered. </p> <p> Note that any child process forked by an action of this type will have all the rights and privileges of the WebLogic Server process that spawns it.  Care must be taken that the target script is available and can be read/executed by the system-level identity that owns the WebLogic Server process. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFScriptActionBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Environment")) {
         getterName = "getEnvironment";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnvironment";
         }

         currentResult = new PropertyDescriptor("Environment", WLDFScriptActionBean.class, getterName, setterName);
         descriptors.put("Environment", currentResult);
         currentResult.setValue("description", "A map of environment variables to set for the child process. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Parameters")) {
         getterName = "getParameters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParameters";
         }

         currentResult = new PropertyDescriptor("Parameters", WLDFScriptActionBean.class, getterName, setterName);
         descriptors.put("Parameters", currentResult);
         currentResult.setValue("description", "An array of ordered command-line arguments to the target script. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PathToScript")) {
         getterName = "getPathToScript";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPathToScript";
         }

         currentResult = new PropertyDescriptor("PathToScript", WLDFScriptActionBean.class, getterName, setterName);
         descriptors.put("PathToScript", currentResult);
         currentResult.setValue("description", "The full path to the script to execute. ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkingDirectory")) {
         getterName = "getWorkingDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkingDirectory";
         }

         currentResult = new PropertyDescriptor("WorkingDirectory", WLDFScriptActionBean.class, getterName, setterName);
         descriptors.put("WorkingDirectory", currentResult);
         currentResult.setValue("description", "The working directory for the child process.  If not set the working directory will be the same as the WebLogic Server parent process. ");
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
