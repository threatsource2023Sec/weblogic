package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFInstrumentationMonitorBeanImplBeanInfo extends WLDFBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFInstrumentationMonitorBean.class;

   public WLDFInstrumentationMonitorBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFInstrumentationMonitorBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>This interface defines a diagnostic monitor, which is applied at the specified locations within the included classes in an instrumentation scope.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Actions")) {
         getterName = "getActions";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setActions";
         }

         currentResult = new PropertyDescriptor("Actions", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("Actions", currentResult);
         currentResult.setValue("description", "<p>The diagnostic actions attached to this monitor. Actions are relevant only for delegating and custom monitors. Valid actions are: <code>TraceAction</code>, <code>DisplayArgumentsAction</code>, <code>MethodInvocationStatisticsAction</code>, <code>MethodMemoryAllocationStatisticsAction</code>, <code>StackDumpAction</code>, <code>ThreadDumpAction</code>, <code>TraceElapsedTimeAction</code>, and <code>TraceMemoryAllocationAction</code>.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>Optional description of this monitor.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DyeMask")) {
         getterName = "getDyeMask";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDyeMask";
         }

         currentResult = new PropertyDescriptor("DyeMask", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("DyeMask", currentResult);
         currentResult.setValue("description", "<p>The dye mask for all diagnostic actions associated with this monitor.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("Excludes")) {
         getterName = "getExcludes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExcludes";
         }

         currentResult = new PropertyDescriptor("Excludes", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("Excludes", currentResult);
         currentResult.setValue("description", "<p>Pattern expressions for classes that will be excluded for this instrumentation monitor. If specified, classes matching given patterns will not be instrumented with this monitor.</p> ");
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

         currentResult = new PropertyDescriptor("Includes", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("Includes", currentResult);
         currentResult.setValue("description", "<p>Pattern expressions for classes that are included for this instrumentation monitor. If specified, only included classes will be instrumented with this monitor. If not specified, all classes loaded within the application and which are not explicitly excluded are eligible for instrumentation with this monitor.</p> <p>A pattern can end with an asterisk (<code>*</code>), in which case it will match with all classes whose fully qualified classname starts with the prefix of the pattern. For example, <code>weblogic.rmi.*</code> will match with all classes in <code>weblogic.rmi</code> and its subpackages.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getExcludes")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocationType")) {
         getterName = "getLocationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLocationType";
         }

         currentResult = new PropertyDescriptor("LocationType", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("LocationType", currentResult);
         currentResult.setValue("description", "<p>Attached actions are applied at selected locations: <code>before</code>, <code>after</code>, or <code>around</code> pointcuts. This is relevant only for custom monitors. (A location where diagnostic code is added is called a diagnostic joinpoint. A set of joinpoints, identified by an expression, is called a pointcut.)</p> <p>Once a location type is set, it cannot be changed.</p> ");
         setPropertyDescriptorDefault(currentResult, "before");
         currentResult.setValue("legalValues", new Object[]{"before", "after", "around"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Pointcut")) {
         getterName = "getPointcut";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPointcut";
         }

         currentResult = new PropertyDescriptor("Pointcut", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("Pointcut", currentResult);
         currentResult.setValue("description", "<p>The pointcut expression for this monitor. (A location where diagnostic code is added is called a diagnostic joinpoint. A set of joinpoints, identified by an expression, is called a pointcut.)</p> <p>Setting a pointcut expression is relevant only for custom monitors; for standard and delegating monitors, this definition is implicitly defined by WLDF.</p> <p>Once a pointcut expression is set, it cannot be changed.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProperties";
         }

         currentResult = new PropertyDescriptor("Properties", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "<p>Properties for this monitor. Properties are name=value pairs, one pair per line. For example, <code>USER1=foo\nADDR1=127.0.0.1</code>.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DyeFilteringEnabled")) {
         getterName = "isDyeFilteringEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDyeFilteringEnabled";
         }

         currentResult = new PropertyDescriptor("DyeFilteringEnabled", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("DyeFilteringEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether dye filtering is enabled for the diagnostic actions associated with this monitor.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WLDFInstrumentationMonitorBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the monitor and its associated diagnostics actions are enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
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
