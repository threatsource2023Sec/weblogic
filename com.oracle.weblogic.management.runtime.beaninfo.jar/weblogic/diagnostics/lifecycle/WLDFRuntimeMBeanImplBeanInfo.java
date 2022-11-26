package weblogic.diagnostics.lifecycle;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFRuntimeMBean;

public class WLDFRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFRuntimeMBean.class;

   public WLDFRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.lifecycle.WLDFRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.lifecycle");
      String description = (new String("<p>This interface provides access to all the runtime MBeans for the WebLogic Diagnostic Framework (WLDF).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("WLDFAccessRuntime")) {
         getterName = "getWLDFAccessRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFAccessRuntime", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFAccessRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this server's view of its diagnostic accessor.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFArchiveRuntimes")) {
         getterName = "getWLDFArchiveRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFArchiveRuntimes", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFArchiveRuntimes", currentResult);
         currentResult.setValue("description", "<p>The MBeans that represent this server's view of its diagnostic archives.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFControlRuntime")) {
         getterName = "getWLDFControlRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFControlRuntime", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFControlRuntime", currentResult);
         currentResult.setValue("description", "The WLDF Control RuntimeMBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WLDFDebugPatchesRuntime")) {
         getterName = "getWLDFDebugPatchesRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFDebugPatchesRuntime", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFDebugPatchesRuntime", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("WLDFHarvesterRuntime")) {
         getterName = "getWLDFHarvesterRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFHarvesterRuntime", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFHarvesterRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this server's view of its diagnostic harvester.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("WLDFImageRuntime")) {
         getterName = "getWLDFImageRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFImageRuntime", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFImageRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this server's view of its diagnostic image source.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFInstrumentationRuntimes")) {
         getterName = "getWLDFInstrumentationRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFInstrumentationRuntimes", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFInstrumentationRuntimes", currentResult);
         currentResult.setValue("description", "<p>The MBeans that represent this server's view of its diagnostic server instrumentation.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFWatchNotificationRuntime")) {
         getterName = "getWLDFWatchNotificationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFWatchNotificationRuntime", WLDFRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFWatchNotificationRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this server's view of its diagnostic policy and action component.</p> ");
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
      Method mth = WLDFRuntimeMBean.class.getMethod("lookupWLDFInstrumentationRuntime", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up the WLDFInstrumentationRuntimeMBean with the given name. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WLDFInstrumentationRuntimes");
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
