package weblogic.t3.srvr;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JRockitRuntimeMBean;

public class JRockitRuntimeBeanInfo extends JVMRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = JRockitRuntimeMBean.class;

   public JRockitRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JRockitRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.t3.srvr.JRockitRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "since 12.1.3 ");
      beanDescriptor.setValue("package", "weblogic.t3.srvr");
      String description = (new String("Exposes runtime data about the JRockit Virtual Machine (VM) that is running the current WebLogic Server instance. You cannot change the VM's operating parameters while the VM is active. Instead, use the startup options that are described in the JRockit documentation.  <p>The WebLogic JVM contains only one of these Runtime MBeans: <ul> <li> <p>If the JVM is an instance of a JRockit JDK, then the JVM contains <code>JRockitRuntime MBean</code>.</p> </li>  <li> <p> Otherwise, it contains the <code>JVMRuntimeMBean</code>.</p> </li> </ul></p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JRockitRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AllProcessorsAverageLoad")) {
         getterName = "getAllProcessorsAverageLoad";
         setterName = null;
         currentResult = new PropertyDescriptor("AllProcessorsAverageLoad", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("AllProcessorsAverageLoad", currentResult);
         currentResult.setValue("description", "<p>A snapshot of the average load of all processors in the host computer. If the computer has only one processor, this method returns the same value as <code>getJvmProcessorLoad(0)</code>.</p>  <p>The value is returned as a double, where 1.0 represents 100% load (no idle time) and 0.0 represents 0% load (pure idle time).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreeHeap")) {
         getterName = "getFreeHeap";
         setterName = null;
         currentResult = new PropertyDescriptor("FreeHeap", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("FreeHeap", currentResult);
         currentResult.setValue("description", "<p>The amount (in bytes) of Java heap memory that is currently free in the Virtual Machine.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreePhysicalMemory")) {
         getterName = "getFreePhysicalMemory";
         setterName = null;
         currentResult = new PropertyDescriptor("FreePhysicalMemory", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("FreePhysicalMemory", currentResult);
         currentResult.setValue("description", "<p>The amount (in bytes) of physical memory that is currently free on the host computer.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GcAlgorithm")) {
         getterName = "getGcAlgorithm";
         setterName = null;
         currentResult = new PropertyDescriptor("GcAlgorithm", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("GcAlgorithm", currentResult);
         currentResult.setValue("description", "<p>The type of garbage collector (GC) that the Virtual Machine is using.</p>  <p>JRockit provides the following types of GCs:</p>  <ul> <li> <p>Generational Copying, which is suitable for testing applications on a desktop machine with a small (less then 128 MB) heap.</p> </li>  <li> <p>Single Spaced Concurrent, which reduces or eliminates pauses in the VM that are due to garbage collection. Because it trades memory throughput for reduced pause time, you generally need a larger heap size than with other GC types. If your ordinary Java threads create more garbage than this GC can collect, the VM will pause while the Java threads wait for the garbage collection to finish.</p> </li>  <li> <p>Generational Concurrent, which creates a \"nursery\" space within the heap. New objects are created within the nursery. When the nursery is full, JRockit \"stops-the-world,\" removes the dead objects from the nursery, and moves live objects to a different space within the heap. Another thread runs in the background to remove dead objects from the non-nursery space. This GC type has a higher memory throughput than a single spaced concurrent GC.</p> </li>  <li> <p>Parallel, which allocates all objects to a single spaced heap. When the heap is full, all Java threads are stopped and every CPU is used to perform a complete garbage collection of the entire heap. This behavior causes longer pause times than for the concurrent collectors but maximizes memory throughput.</p> </li> </ul> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HeapFreeCurrent")) {
         getterName = "getHeapFreeCurrent";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapFreeCurrent", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("HeapFreeCurrent", currentResult);
         currentResult.setValue("description", "<p>The current amount of memory (in bytes) that is available in the JVM heap.</p> ");
      }

      if (!descriptors.containsKey("HeapFreePercent")) {
         getterName = "getHeapFreePercent";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapFreePercent", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("HeapFreePercent", currentResult);
         currentResult.setValue("description", "<p>Percentage of the maximum memory that is free.</p> ");
      }

      if (!descriptors.containsKey("HeapSizeCurrent")) {
         getterName = "getHeapSizeCurrent";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapSizeCurrent", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("HeapSizeCurrent", currentResult);
         currentResult.setValue("description", "<p>The current size (in bytes) of the JVM heap.</p> ");
      }

      if (!descriptors.containsKey("HeapSizeMax")) {
         getterName = "getHeapSizeMax";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapSizeMax", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("HeapSizeMax", currentResult);
         currentResult.setValue("description", "<p>The maximum free memory configured for this JVM.</p> ");
      }

      if (!descriptors.containsKey("JVMDescription")) {
         getterName = "getJVMDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("JVMDescription", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("JVMDescription", currentResult);
         currentResult.setValue("description", "<p>A description of the Java Virtual Machine. For example, \"BEA WebLogic JRockit Java Virtual Machine.\"</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaVMVendor")) {
         getterName = "getJavaVMVendor";
         setterName = null;
         currentResult = new PropertyDescriptor("JavaVMVendor", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("JavaVMVendor", currentResult);
         currentResult.setValue("description", "<p>Returns the vendor of the JVM.</p>  <p>The vendor of the JVM that this server runs.</p>  system property java.vm.vendor is returned ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("JavaVendor")) {
         getterName = "getJavaVendor";
         setterName = null;
         currentResult = new PropertyDescriptor("JavaVendor", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("JavaVendor", currentResult);
         currentResult.setValue("description", "<p>Returns the vendor of Java.</p>  <p>The vendor of Java that this server runs.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("JavaVersion")) {
         getterName = "getJavaVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("JavaVersion", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("JavaVersion", currentResult);
         currentResult.setValue("description", "<p>The Java version of the JVM.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("JvmProcessorLoad")) {
         getterName = "getJvmProcessorLoad";
         setterName = null;
         currentResult = new PropertyDescriptor("JvmProcessorLoad", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("JvmProcessorLoad", currentResult);
         currentResult.setValue("description", "<p>A snapshot of the load that the Virtual Machine is placing on all processors in the host computer. If the host contains multiple processors, the value represents a snapshot of the average load.</p>  <p>The value is returned as a double, where 1.0 represents 100% load (no idle time) and 0.0 represents 0% load (pure idle time).</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGCEnd")) {
         getterName = "getLastGCEnd";
         setterName = null;
         currentResult = new PropertyDescriptor("LastGCEnd", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastGCEnd", currentResult);
         currentResult.setValue("description", "<p>The time at which the last garbage collection run ended.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastGCStart")) {
         getterName = "getLastGCStart";
         setterName = null;
         currentResult = new PropertyDescriptor("LastGCStart", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("LastGCStart", currentResult);
         currentResult.setValue("description", "<p>The time at which the last garbage collection run started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of the Java Virtual Machine. For example, \"WebLogic JRockit.\"</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberOfDaemonThreads")) {
         getterName = "getNumberOfDaemonThreads";
         setterName = null;
         currentResult = new PropertyDescriptor("NumberOfDaemonThreads", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("NumberOfDaemonThreads", currentResult);
         currentResult.setValue("description", "<p>The number of daemon Java threads currently running in the Virtual Machine across all processors.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberOfProcessors")) {
         getterName = "getNumberOfProcessors";
         setterName = null;
         currentResult = new PropertyDescriptor("NumberOfProcessors", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("NumberOfProcessors", currentResult);
         currentResult.setValue("description", "<p>The number of processors on the Virtual Machine's host computer. If this is not a Symmetric Multi Processor (SMP) system, the value will be <code>1</code>.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OSName")) {
         getterName = "getOSName";
         setterName = null;
         currentResult = new PropertyDescriptor("OSName", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("OSName", currentResult);
         currentResult.setValue("description", "<p>Returns the operating system on which the JVM is running.</p>  <p>The operating system on which the JVM is running.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("OSVersion")) {
         getterName = "getOSVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("OSVersion", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("OSVersion", currentResult);
         currentResult.setValue("description", "<p>The version of the operating system on which the JVM is running.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("PauseTimeTarget")) {
         getterName = "getPauseTimeTarget";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPauseTimeTarget";
         }

         currentResult = new PropertyDescriptor("PauseTimeTarget", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("PauseTimeTarget", currentResult);
         currentResult.setValue("description", "<p>Gets the maximum GC pause time if set</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadStackDump")) {
         getterName = "getThreadStackDump";
         setterName = null;
         currentResult = new PropertyDescriptor("ThreadStackDump", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("ThreadStackDump", currentResult);
         currentResult.setValue("description", "<p>JVM thread dump. Thread dump is available only on 1.5 VM</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("TotalGarbageCollectionCount")) {
         getterName = "getTotalGarbageCollectionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalGarbageCollectionCount", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("TotalGarbageCollectionCount", currentResult);
         currentResult.setValue("description", "<p>The number of garbage collection runs that have occurred since the Virtual Machine was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalGarbageCollectionTime")) {
         getterName = "getTotalGarbageCollectionTime";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalGarbageCollectionTime", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("TotalGarbageCollectionTime", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds that the Virtual Machine has spent on all garbage collection runs since the VM was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalHeap")) {
         getterName = "getTotalHeap";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalHeap", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("TotalHeap", currentResult);
         currentResult.setValue("description", "<p>The amount (in bytes) of memory currently allocated to the Virtual Machine's Java heap.</p>  <p>This value, which is also known as the \"heap size,\" may grow up to the specified maximum heap size.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMaxHeapSize")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNumberOfThreads")) {
         getterName = "getTotalNumberOfThreads";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNumberOfThreads", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("TotalNumberOfThreads", currentResult);
         currentResult.setValue("description", "<p>The number of Java threads (daemon and non-daemon) that are currently running in the Virtual Machine across all processors.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalNurserySize")) {
         getterName = "getTotalNurserySize";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalNurserySize", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("TotalNurserySize", currentResult);
         currentResult.setValue("description", "<p>The amount (in bytes) of memory that is currently allocated to the nursery.</p>  <p>The nursery is the area of the Java heap that the VM allocates to most objects. Instead of garbage collecting the entire heap, generational garbage collectors focus on the nursery. Because most objects die young, most of the time it is sufficient to garbage collect only the nursery and not the entire heap.</p>  <p>If you are not using a generational garbage collector, the nursery size is <code><code>0</code></code>.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalPhysicalMemory")) {
         getterName = "getTotalPhysicalMemory";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalPhysicalMemory", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("TotalPhysicalMemory", currentResult);
         currentResult.setValue("description", "<p>The amount (in bytes) of physical memory on the host computer.</p>  <p>The value does not include memory that an operating system makes available through swap space on a disk or other types of virtual memory.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Uptime")) {
         getterName = "getUptime";
         setterName = null;
         currentResult = new PropertyDescriptor("Uptime", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Uptime", currentResult);
         currentResult.setValue("description", "<p>The number of milliseconds that the Virtual Machine has been running.</p> ");
      }

      if (!descriptors.containsKey("UsedHeap")) {
         getterName = "getUsedHeap";
         setterName = null;
         currentResult = new PropertyDescriptor("UsedHeap", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("UsedHeap", currentResult);
         currentResult.setValue("description", "<p>The amount (in bytes) of Java heap memory that is currently being used by the Virtual Machine.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UsedPhysicalMemory")) {
         getterName = "getUsedPhysicalMemory";
         setterName = null;
         currentResult = new PropertyDescriptor("UsedPhysicalMemory", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("UsedPhysicalMemory", currentResult);
         currentResult.setValue("description", "<p>The amount (in bytes) of physical memory that is currently being used on the host computer.</p>  <p>The value describes the memory that is being used by all processes on the computer, not just by the Virtual Machine.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Vendor")) {
         getterName = "getVendor";
         setterName = null;
         currentResult = new PropertyDescriptor("Vendor", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Vendor", currentResult);
         currentResult.setValue("description", "<p>The name of the JVM vendor. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("Version", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "<p>The current version of Java Virtual Machine.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Concurrent")) {
         getterName = "isConcurrent";
         setterName = null;
         currentResult = new PropertyDescriptor("Concurrent", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Concurrent", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the VM's garbage collector runs in a separate Java thread concurrently with other Java threads.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GCHandlesCompaction")) {
         getterName = "isGCHandlesCompaction";
         setterName = null;
         currentResult = new PropertyDescriptor("GCHandlesCompaction", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("GCHandlesCompaction", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the VM's garbage collector compacts the Java heap.</p>  <p>Usually the heap is scattered throughout available memory. A garbage collector that compacts the heap defragments the memory space in addition to deleting unused objects.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Generational")) {
         getterName = "isGenerational";
         setterName = null;
         currentResult = new PropertyDescriptor("Generational", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Generational", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the VM's garbage collector uses a nursery space.</p>  <p>A nursery is the area of the Java heap that the VM allocates to most objects. Instead of garbage collecting the entire heap, generational garbage collectors focus on the nursery. Because most objects die young, most of the time it is sufficient to garbage collect only the nursery and not the entire heap.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Incremental")) {
         getterName = "isIncremental";
         setterName = null;
         currentResult = new PropertyDescriptor("Incremental", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Incremental", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the VM's garbage collector collects (increments) garbage as it scans the memory space and dumps the garbage at the end of its cycle.</p>  <p>With a non-incremental garbage collector, garbage is dumped as soon as it is encountered.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Parallel")) {
         getterName = "isParallel";
         setterName = null;
         currentResult = new PropertyDescriptor("Parallel", JRockitRuntimeMBean.class, getterName, setterName);
         descriptors.put("Parallel", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the VM's garbage collector is able to run in parallel on multiple processors if multiple processors are available.</p> ");
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
      Method mth = JRockitRuntimeMBean.class.getMethod("runGC");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts garbage collection and finalization algorithms within the JVM.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("isMethodTimingEnabled", Method.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("method", "the method you want to check. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Virtual Machine measures how much time it spends in a method.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("getMethodTiming", Method.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("method", "the method we wish to check. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The amount of time (in milliseconds) the Virtual Machine has spent in the method since enabling time measuring.</p>  <p>If time measuring has not been enabled for the specified method, this method returns a null value.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("isMethodInvocationCountEnabled", Method.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("method", "the method you want to check. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Virtual Machine counts how many times a method is invoked.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("getMethodInvocationCount", Method.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("method", "the method you want to check. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The number of times a method has been invoked since enabling invocation counting.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("isConstructorTimingEnabled", Constructor.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("constructor", "the constructor you want to check. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Virtual Machine measures how much time it spends in a constructor.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("getConstructorTiming", Constructor.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("constructor", "the constructor you want to check. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The amount of time (in milliseconds) the Virtual Machine has spent in the constructor since enabling time measuring.</p>  <p>If time measuring hasn't been enabled for the specified constructor, this method returns a null value.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("isConstructorInvocationCountEnabled", Constructor.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cons", "the constructor you want to check. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Virtual Machine counts how many times a constructor is invoked.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("getConstructorInvocationCount", Constructor.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("constructor", "- the constructor for which to return the invocation count. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The number of times a constructor has been invoked since enabling invocation counting.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("isExceptionCountEnabled", Class.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("throwableClass", "the exception class to get the counter for. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Indicates whether the Virtual Machine counts how many times an exception is thrown.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JRockitRuntimeMBean.class.getMethod("getExceptionCount", Class.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("throwableClass", "the exception class to get the counter for. If the  throwableClass is null, a NullPointerException will be thrown. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "10.0.0.0 There will be no replacement for this method in future, since it will not be supported by JRockit management API. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The number of times an exception type has been thrown since enabling exception counting. If exception counting has not been enabled for the specified type, the result is unspecified.</p> ");
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
