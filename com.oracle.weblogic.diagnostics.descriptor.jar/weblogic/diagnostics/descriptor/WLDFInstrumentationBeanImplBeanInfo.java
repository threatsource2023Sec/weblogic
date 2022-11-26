package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFInstrumentationBeanImplBeanInfo extends WLDFBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFInstrumentationBean.class;

   public WLDFInstrumentationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFInstrumentationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFInstrumentationBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Use this interface to configure server-scope and application-scope instrumentation for diagnostic monitors that will execute diagnostic code at selected locations in server or application code.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFInstrumentationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (!descriptors.containsKey("Excludes")) {
         getterName = "getExcludes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExcludes";
         }

         currentResult = new PropertyDescriptor("Excludes", WLDFInstrumentationBean.class, getterName, setterName);
         descriptors.put("Excludes", currentResult);
         currentResult.setValue("description", "<p>Pattern expressions for classes that will be excluded from this instrumentation scope. If specified, classes matching given patterns will not be instrumented.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getIncludes")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Includes")) {
         getterName = "getIncludes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIncludes";
         }

         currentResult = new PropertyDescriptor("Includes", WLDFInstrumentationBean.class, getterName, setterName);
         descriptors.put("Includes", currentResult);
         currentResult.setValue("description", "<p>Pattern expressions for classes that are included in this instrumentation scope. If specified, only included classes will be instrumented. If not specified, all classes loaded within the application and which are not explicitly excluded are eligible for instrumentation.</p> <p>A pattern can end with an asterisk (<code>*</code>), in which case it will match with all classes whose fully qualified classname starts with the prefix of the pattern. For example, <code>weblogic.rmi.*</code> will match with all classes in <code>weblogic.rmi</code> and its subpackages.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getExcludes")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFInstrumentationMonitors")) {
         getterName = "getWLDFInstrumentationMonitors";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFInstrumentationMonitors", WLDFInstrumentationBean.class, getterName, setterName);
         descriptors.put("WLDFInstrumentationMonitors", currentResult);
         currentResult.setValue("description", "<p>The diagnostic monitors defined in this instrumentation scope.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWLDFInstrumentationMonitor");
         currentResult.setValue("creator", "createWLDFInstrumentationMonitor");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WLDFInstrumentationBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>The state of the instrumentation behavior for the server or application. If <code>false</code>, there will no weaving (inserting of diagnostic code) in the application or server code during class loading. In addition, if woven classes are already loaded, disabling instrumentation will disable all the monitors in this scope.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFInstrumentationBean.class.getMethod("createWLDFInstrumentationMonitor", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "A unique name to identify the monitor in this scope. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds the specified diagnostic monitor to this instrumentation scope, which could be server or application scope.</p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getWLDFInstrumentationMonitors")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WLDFInstrumentationMonitors");
      }

      mth = WLDFInstrumentationBean.class.getMethod("destroyWLDFInstrumentationMonitor", WLDFInstrumentationMonitorBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("monitor", "The name of the monitor to remove ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("MonitorNotFoundException The named monitor does not exist in the scope of this instrumentation manager.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes the specified diagnostic monitor from this instrumentation scope.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WLDFInstrumentationMonitors");
      }

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
