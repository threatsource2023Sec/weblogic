package weblogic.diagnostics.instrumentation;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.WLDFInstrumentationRuntimeMBean;

public class InstrumentationRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFInstrumentationRuntimeMBean.class;

   public InstrumentationRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public InstrumentationRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.instrumentation.InstrumentationRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.instrumentation");
      String description = (new String("<p>This interface defines various methods for accessing runtime information about the diagnostic instrumentation system.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFInstrumentationRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CallJoinpointCount")) {
         getterName = "getCallJoinpointCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CallJoinpointCount", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CallJoinpointCount", currentResult);
         currentResult.setValue("description", "<p>The number of affected CALL joinpoints for all classes that were inspected. (CALL joinpoints are on the caller side.)</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClassweaveAbortCount")) {
         getterName = "getClassweaveAbortCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassweaveAbortCount", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ClassweaveAbortCount", currentResult);
         currentResult.setValue("description", "<p>Number of classes for which the class weaving aborted with some exceptional situation.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutionJoinpointCount")) {
         getterName = "getExecutionJoinpointCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutionJoinpointCount", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutionJoinpointCount", currentResult);
         currentResult.setValue("description", "<p>The number of affected EXECUTION joinpoints for all classes that were inspected. (EXECUTION joinpoints are on the callee side.)</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InspectedClassesCount")) {
         getterName = "getInspectedClassesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InspectedClassesCount", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InspectedClassesCount", currentResult);
         currentResult.setValue("description", "<p>The number of classes inspected for weaving (weaving is the insertion of diagnostic code).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxWeavingTime")) {
         getterName = "getMaxWeavingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxWeavingTime", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxWeavingTime", currentResult);
         currentResult.setValue("description", "<p>For all classes, the weaving time in nanoseconds for the class that required the most time to process (includes the time spent both for inspection and for modification).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MethodInvocationStatistics")) {
         getterName = "getMethodInvocationStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("MethodInvocationStatistics", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MethodInvocationStatistics", currentResult);
         currentResult.setValue("description", "<p>Map containing the method invocation statistics for this scope. It is a nested Map structure. The first level Map is keyed by the fully qualified class names within the instrumentation scope. It yields another Map containing the method data within an instrumented class. The method data Map is keyed by the method name and it yields another Map structure that is keyed by the method signatures. Method signature key is represented by a comma separated list of the input parameters. Each method signature key's value is the ultimate statistics Map object that contains entries with predefined keys: count, min, max, avg, sum, sum_of_squares, and std_deviation. The value for these keys indicate the associated metric.</p>  <p>When specifying this attribute as part of a variable within a WLDF policy expression, you must explicitly declare the WLDFInstrumentationRuntime type. Otherwise, the system can't determine the type when validating the attribute expression, and the expression won't work.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map");
         currentResult.setValue("harvesterAttributeNormalizerClass", "weblogic.diagnostics.instrumentation.HarvesterAttributeNormalizer");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3", (String)null, this.targetVersion) && !descriptors.containsKey("MethodMemoryAllocationStatistics")) {
         getterName = "getMethodMemoryAllocationStatistics";
         setterName = null;
         currentResult = new PropertyDescriptor("MethodMemoryAllocationStatistics", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MethodMemoryAllocationStatistics", currentResult);
         currentResult.setValue("description", "<p>Map containing the method memory allocation statistics for this scope. It is a nested Map structure. The first level Map is keyed by the fully qualified class names within the instrumentation scope. It yields another Map containing the method data within an instrumented class. The method data Map is keyed by the method name and it yields another Map structure that is keyed by the method signatures. Method signature key is represented by a comma separated list of the input parameters. Each method signature key's value is the ultimate statistics Map object that contains entries with predefined keys: count, min, max, avg, sum, sum_of_squares, and std_deviation. The value for these keys indicate the associated metric.</p>  <p>When specifying this attribute as part of a variable within a WLDF policy expression, you must explicitly declare the WLDFInstrumentationRuntime type. Otherwise, the system can't determine the type when validating the attribute expression, and the expression won't work.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Map");
         currentResult.setValue("harvesterAttributeNormalizerClass", "weblogic.diagnostics.instrumentation.HarvesterAttributeNormalizer");
      }

      if (!descriptors.containsKey("MinWeavingTime")) {
         getterName = "getMinWeavingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinWeavingTime", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinWeavingTime", currentResult);
         currentResult.setValue("description", "<p>For all classes, the weaving time in nanoseconds for the class that required the least time to process (includes the time spent both for inspection and for modification).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModifiedClassesCount")) {
         getterName = "getModifiedClassesCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ModifiedClassesCount", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModifiedClassesCount", currentResult);
         currentResult.setValue("description", "<p>The number of modified classes (classes where diagnostic code has been inserted).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalWeavingTime")) {
         getterName = "getTotalWeavingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalWeavingTime", WLDFInstrumentationRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalWeavingTime", currentResult);
         currentResult.setValue("description", "<p>For all classes, the total weaving time in nanoseconds for processing (includes the time spent both for inspection and for modification).</p> ");
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
      Method mth = WLDFInstrumentationRuntimeMBean.class.getMethod("getMethodInvocationStatisticsData", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("expr", "Expression conforming to the harvester syntax for the MethodInvocationStatistics property without the attribute name prefix. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Drills down into the nested MethodInvocationStatistics Map structure and returns the object at the specified level. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
      }

      mth = WLDFInstrumentationRuntimeMBean.class.getMethod("resetMethodInvocationStatisticsData", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("expr", "Expression conforming to the harvester syntax for the MethodInvocationStatistics property without the attribute name prefix. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Resets the nested MethodInvocationStatistics structure and reinitializes the underlying metrics. ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3", (String)null, this.targetVersion)) {
         mth = WLDFInstrumentationRuntimeMBean.class.getMethod("getMethodMemoryAllocationStatisticsData", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("expr", "Expression conforming to the harvester syntax for the MethodMemoryAllocationStatistics property without the attribute name prefix. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
            currentResult.setValue("since", "10.3.3");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Drills down into the nested MethodMemoryAllocationStatistics Map structure and returns the object at the specified level. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.3");
            currentResult.setValue("excludeFromRest", "No default REST mapping for Object");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3", (String)null, this.targetVersion)) {
         mth = WLDFInstrumentationRuntimeMBean.class.getMethod("resetMethodMemoryAllocationStatisticsData", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("expr", "Expression conforming to the harvester syntax for the MethodMemoryAllocationStatistics property without the attribute name prefix. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.3");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Resets the nested MethodMemoryAllocationStatistics structure and reinitializes the underlying metrics. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "10.3.3");
         }
      }

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
