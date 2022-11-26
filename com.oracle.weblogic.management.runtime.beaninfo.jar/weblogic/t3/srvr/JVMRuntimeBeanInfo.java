package weblogic.t3.srvr;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JVMRuntimeMBean;

public class JVMRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JVMRuntimeMBean.class;

   public JVMRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JVMRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.t3.srvr.JVMRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.t3.srvr");
      String description = (new String("<p>Provides methods for retrieving information about the Java Virtual Machine (JVM) within with the current server instance is running. You cannot change the JVM's operating parameters while the JVM is active. Instead, use the startup options that are described in the JVM's documentation.</p>  <p>The WebLogic JVM contains only one of these Runtime MBeans:</p> <ul> <li> <p>If the JVM is an instance of a JRockit JDK, then the JVM contains <code>JRockitRuntime MBean</code>.</p></li>  <li> <p> Otherwise, it contains the <code>JVMRuntimeMBean</code>.</p></li> </ul> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JVMRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("HeapFreeCurrent")) {
         getterName = "getHeapFreeCurrent";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapFreeCurrent", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HeapFreeCurrent", currentResult);
         currentResult.setValue("description", "<p>The current amount of memory (in bytes) that is available in the JVM heap.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HeapFreePercent")) {
         getterName = "getHeapFreePercent";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapFreePercent", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HeapFreePercent", currentResult);
         currentResult.setValue("description", "<p>Percentage of the maximum memory that is free.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HeapSizeCurrent")) {
         getterName = "getHeapSizeCurrent";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapSizeCurrent", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HeapSizeCurrent", currentResult);
         currentResult.setValue("description", "<p>The current size (in bytes) of the JVM heap.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HeapSizeMax")) {
         getterName = "getHeapSizeMax";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapSizeMax", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HeapSizeMax", currentResult);
         currentResult.setValue("description", "<p>The maximum free memory configured for this JVM.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaVMVendor")) {
         getterName = "getJavaVMVendor";
         setterName = null;
         currentResult = new PropertyDescriptor("JavaVMVendor", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JavaVMVendor", currentResult);
         currentResult.setValue("description", "<p>Returns the vendor of the JVM.</p>  <p>The vendor of the JVM that this server runs.</p>  system property java.vm.vendor is returned ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaVendor")) {
         getterName = "getJavaVendor";
         setterName = null;
         currentResult = new PropertyDescriptor("JavaVendor", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JavaVendor", currentResult);
         currentResult.setValue("description", "<p>Returns the vendor of Java.</p>  <p>The vendor of Java that this server runs.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaVersion")) {
         getterName = "getJavaVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("JavaVersion", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JavaVersion", currentResult);
         currentResult.setValue("description", "<p>The Java version of the JVM.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OSName")) {
         getterName = "getOSName";
         setterName = null;
         currentResult = new PropertyDescriptor("OSName", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OSName", currentResult);
         currentResult.setValue("description", "<p>Returns the operating system on which the JVM is running.</p>  <p>The operating system on which the JVM is running.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OSVersion")) {
         getterName = "getOSVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("OSVersion", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OSVersion", currentResult);
         currentResult.setValue("description", "<p>The version of the operating system on which the JVM is running.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadStackDump")) {
         getterName = "getThreadStackDump";
         setterName = null;
         currentResult = new PropertyDescriptor("ThreadStackDump", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ThreadStackDump", currentResult);
         currentResult.setValue("description", "<p>JVM thread dump. Thread dump is available only on 1.5 VM</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Uptime")) {
         getterName = "getUptime";
         setterName = null;
         currentResult = new PropertyDescriptor("Uptime", JVMRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Uptime", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds that the Virtual Machine has been running.</p> ");
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
      Method mth = JVMRuntimeMBean.class.getMethod("runGC");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts garbage collection and finalization algorithms within the JVM.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
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
