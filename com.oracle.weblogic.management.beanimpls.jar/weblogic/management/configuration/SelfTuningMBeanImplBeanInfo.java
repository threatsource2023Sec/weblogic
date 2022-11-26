package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class SelfTuningMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = SelfTuningMBean.class;

   public SelfTuningMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public SelfTuningMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.SelfTuningMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("SelfTuning MBean contains the global work manager component MBeans. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.SelfTuningMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Capacities")) {
         getterName = "getCapacities";
         setterName = null;
         currentResult = new PropertyDescriptor("Capacities", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("Capacities", currentResult);
         currentResult.setValue("description", "All the capacity definitions. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCapacity");
         currentResult.setValue("destroyer", "destroyCapacity");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ContextRequestClasses")) {
         getterName = "getContextRequestClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("ContextRequestClasses", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("ContextRequestClasses", currentResult);
         currentResult.setValue("description", "All the context request classes. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyContextRequestClass");
         currentResult.setValue("creator", "createContextRequestClass");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("FairShareRequestClasses")) {
         getterName = "getFairShareRequestClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("FairShareRequestClasses", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("FairShareRequestClasses", currentResult);
         currentResult.setValue("description", "All the fair share request classes. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFairShareRequestClass");
         currentResult.setValue("destroyer", "destroyFairShareRequestClass");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxThreadsConstraints")) {
         getterName = "getMaxThreadsConstraints";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxThreadsConstraints", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("MaxThreadsConstraints", currentResult);
         currentResult.setValue("description", "All the maximum threads constraints. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMaxThreadsConstraint");
         currentResult.setValue("destroyer", "destroyMaxThreadsConstraint");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinThreadsConstraints")) {
         getterName = "getMinThreadsConstraints";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraints", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("MinThreadsConstraints", currentResult);
         currentResult.setValue("description", "All the minimum threads constraints. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMinThreadsConstraint");
         currentResult.setValue("destroyer", "destroyMinThreadsConstraint");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionFairShare")) {
         getterName = "getPartitionFairShare";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPartitionFairShare";
         }

         currentResult = new PropertyDescriptor("PartitionFairShare", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("PartitionFairShare", currentResult);
         currentResult.setValue("description", "A desired percentage of thread usage by a partition compared to the thread usage by all partitions. It is recommended that the sum of this value for all the partitions running in a WLS domain add up to 100, but it is not strictly enforced. When they do not add up to 100, WLS assigns thread-usage times to different partitions based on their relative values. This attribute is for use in the global domain only. Use the PartitionWorkManager MBean for specifying partition fair share values for partitions. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(99));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResponseTimeRequestClasses")) {
         getterName = "getResponseTimeRequestClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("ResponseTimeRequestClasses", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("ResponseTimeRequestClasses", currentResult);
         currentResult.setValue("description", "All the response time request classes. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResponseTimeRequestClass");
         currentResult.setValue("destroyer", "destroyResponseTimeRequestClass");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagers")) {
         getterName = "getWorkManagers";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagers", SelfTuningMBean.class, getterName, setterName);
         descriptors.put("WorkManagers", currentResult);
         currentResult.setValue("description", "All the defined Work Managers. <p> A note about dynamic additions and deletions of Work Managers in a running server. Only applications or modules deployed or re-deployed after the changes are made can pick up newly added Work Managers. Existing production applications resolve their dispatch-policies to Work Managers during deployment time. Once the application is exported and in production mode, the server does not swap Work Managers. The tight binding helps performance and also avoids issues such as what happens to inflight work in the old Work Manager. This attribute is marked as dynamic so that new applications (re)deployed can pick up the Work Manager changes. Please note that this applies only to resolving dispatch-policies to Work Managers. Existing attributes within a Work Manager, like fair-share and constraints, can be modified without requiring a redeploy. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWorkManager");
         currentResult.setValue("creator", "createWorkManager");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("createFairShareRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates fair share request classes. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "FairShareRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("destroyFairShareRequestClass", FairShareRequestClassMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a fair share request class with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "FairShareRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("createResponseTimeRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates the response time request classes. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResponseTimeRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("destroyResponseTimeRequestClass", ResponseTimeRequestClassMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a response time request classes with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResponseTimeRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("createContextRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates context request classes. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ContextRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("destroyContextRequestClass", ContextRequestClassMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a context request class with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ContextRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("createMinThreadsConstraint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates minimum threads constraints. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MinThreadsConstraints");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("destroyMinThreadsConstraint", MinThreadsConstraintMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a minimum threads constraint with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MinThreadsConstraints");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("createMaxThreadsConstraint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates maximum threads constraints. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MaxThreadsConstraints");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("destroyMaxThreadsConstraint", MaxThreadsConstraintMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a maximum threads constraint with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "MaxThreadsConstraints");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("createCapacity", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates capacity constraints. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Capacities");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("destroyCapacity", CapacityMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a capacity constraint with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Capacities");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("createWorkManager", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates new Work Managers. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WorkManagers");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("destroyWorkManager", WorkManagerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("c", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys Work Managers. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WorkManagers");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("lookupFairShareRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a particular fair share request classes. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "FairShareRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("lookupResponseTimeRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a particular response time request class. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ResponseTimeRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("lookupContextRequestClass", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a particular context request class. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ContextRequestClasses");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("lookupMinThreadsConstraint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a particular minimum threads constraint. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "MinThreadsConstraints");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("lookupMaxThreadsConstraint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a particular maximum threads constraint. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "MaxThreadsConstraints");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("lookupCapacity", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a particular capacity constraint. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "Capacities");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion)) {
         mth = SelfTuningMBean.class.getMethod("lookupWorkManager", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a particular Work Manager. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "WorkManagers");
            currentResult.setValue("since", "10.0.0.0");
         }
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
