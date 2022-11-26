package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ResourceManagerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ResourceManagerMBean.class;

   public ResourceManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ResourceManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ResourceManagerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p> A {@code ResourceManagerMBean} represents a resource consumption management policy. A policy controls and governs the use of shared resources in a Server runtime, by a Partition that the policy is attached to. A resource manager holds information on constraints and recourse actions for different resources in a Server instance. </p> <p> A system administrator defines a resource manager and configures child MBeans that represent particular combinations of shares and usage limits of various resources in the Server runtime. </p> <p> The system administrator may then assign the resource manager to one or more Partitions to ensure fair allocation of shared resources to collocated Domain Partitions, and to ensure that one Partition doesn't exhaust shared resources (potentially affecting other collocated Partitions). </p> <p> The set of resources on which resource management policies can be assigned to, and the child MBeans through which they can be configured are: </p> <ol> <li>File Open: {@link FileOpenMBean}</li> <li>Heap Retained: {@link HeapRetainedMBean}</li> <li>CPU Utilization: {@link CpuUtilizationMBean}</li> </ol> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ResourceManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CpuUtilization")) {
         getterName = "getCpuUtilization";
         setterName = null;
         currentResult = new PropertyDescriptor("CpuUtilization", ResourceManagerMBean.class, getterName, (String)setterName);
         descriptors.put("CpuUtilization", currentResult);
         currentResult.setValue("description", "Gets the \"CPU Utilization\" policy for this resource manager. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCpuUtilization");
         currentResult.setValue("creator", "createCpuUtilization");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileOpen")) {
         getterName = "getFileOpen";
         setterName = null;
         currentResult = new PropertyDescriptor("FileOpen", ResourceManagerMBean.class, getterName, (String)setterName);
         descriptors.put("FileOpen", currentResult);
         currentResult.setValue("description", "Gets the \"File Open\" policy for this resource manager. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFileOpen");
         currentResult.setValue("destroyer", "destroyFileOpen");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HeapRetained")) {
         getterName = "getHeapRetained";
         setterName = null;
         currentResult = new PropertyDescriptor("HeapRetained", ResourceManagerMBean.class, getterName, (String)setterName);
         descriptors.put("HeapRetained", currentResult);
         currentResult.setValue("description", "Gets the \"Heap Retained\" policy for this resource manager. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createHeapRetained");
         currentResult.setValue("destroyer", "destroyHeapRetained");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("RestartLoopProtection")) {
         getterName = "getRestartLoopProtection";
         setterName = null;
         currentResult = new PropertyDescriptor("RestartLoopProtection", ResourceManagerMBean.class, getterName, (String)setterName);
         descriptors.put("RestartLoopProtection", currentResult);
         currentResult.setValue("description", "Gets the \"Restart Loop Protection\"  for this resource manager. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ResourceManagerMBean.class.getMethod("createFileOpen", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Policy to be created ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is the factory method for creating \"File Open\" policy for this resource manager. The new {@code FileOpenMBean} which is created will have this resource manager as its parent and must be destroyed with the {@link ResourceManagerMBean#destroyFileOpen(FileOpenMBean)} method.  The \"File open\" policy governs the number of open files. This includes files that have been opened through {@code java.io} ( {@code FileInputStream, FileOutputStream, RandomAccessFile} etc) and {@code java.nio} (NIO file channels) APIs. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileOpen");
      }

      mth = ResourceManagerMBean.class.getMethod("destroyFileOpen", FileOpenMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("fileOpenMBean", "The FileOpenMBean to be removed from the resource manager. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a \"File Open\" policy corresponding to the {code fileOpenMBean} parameter, which is a child of this resource manager. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileOpen");
      }

      mth = ResourceManagerMBean.class.getMethod("createHeapRetained", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Policy to be created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is the factory method for creating \"Heap Retained\" policy for this resource manager. The new {@code HeapRetainedMBean} which is created will have this resource manager as its parent and must be destroyed with the {@link ResourceManagerMBean#destroyHeapRetained(HeapRetainedMBean)} method.  The \"Heap Retained\" policy tracks the amount of Heap memory retained(in use) by the Partition. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HeapRetained");
      }

      mth = ResourceManagerMBean.class.getMethod("destroyHeapRetained", HeapRetainedMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("heapRetainedMBean", "The HeapRetainedMBean to be removed from the resource manager. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a \"Heap Retained\" policy corresponding to the {code heapRetainedMBean} parameter, which is a child of this resource manager. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HeapRetained");
      }

      mth = ResourceManagerMBean.class.getMethod("createCpuUtilization", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the Policy to be created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is the factory method for creating \"CPU Utilization\" policy for this resource manager. The new {@code CpuUtilizationMBean} which is created will have this resource manager as its parent and must be destroyed with the {@link ResourceManagerMBean#destroyCpuUtilization(CpuUtilizationMBean)} method.  The \"CPU Utilization\" resource type tracks the percentage of CPU time utilized by a Domain Partition with respect to the available CPU time to the Server runtime. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CpuUtilization");
      }

      mth = ResourceManagerMBean.class.getMethod("destroyCpuUtilization", CpuUtilizationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cpuUtilizationMBean", "The CpuUtilizationMBean to be removed from the resource manager. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a \"CPU Utilization\" policy corresponding to the {code cpuUtilizationMBean} parameter, which is a child of this resource manager. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CpuUtilization");
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
