package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class StatefulSessionClusteringBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = StatefulSessionClusteringBean.class;

   public StatefulSessionClusteringBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public StatefulSessionClusteringBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.StatefulSessionClusteringBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.StatefulSessionClusteringBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("HomeCallRouterClassName")) {
         getterName = "getHomeCallRouterClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHomeCallRouterClassName";
         }

         currentResult = new PropertyDescriptor("HomeCallRouterClassName", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("HomeCallRouterClassName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HomeLoadAlgorithm")) {
         getterName = "getHomeLoadAlgorithm";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHomeLoadAlgorithm";
         }

         currentResult = new PropertyDescriptor("HomeLoadAlgorithm", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("HomeLoadAlgorithm", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalValues", new Object[]{"RoundRobin", "Random", "WeightBased", "RoundRobinAffinity", "RandomAffinity", "WeightBasedAffinity", "roundrobin", "round-robin", "random", "weightbased", "weight-based", "round-robin-affinity", "random-affinity", "weight-based-affinity"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplicationType")) {
         getterName = "getReplicationType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplicationType";
         }

         currentResult = new PropertyDescriptor("ReplicationType", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("ReplicationType", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, "None");
         currentResult.setValue("legalValues", new Object[]{"InMemory", "None", "none", "inmemory"});
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CalculateDeltaUsingReflection")) {
         getterName = "isCalculateDeltaUsingReflection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCalculateDeltaUsingReflection";
         }

         currentResult = new PropertyDescriptor("CalculateDeltaUsingReflection", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("CalculateDeltaUsingReflection", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HomeIsClusterable")) {
         getterName = "isHomeIsClusterable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHomeIsClusterable";
         }

         currentResult = new PropertyDescriptor("HomeIsClusterable", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("HomeIsClusterable", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PassivateDuringReplication")) {
         getterName = "isPassivateDuringReplication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassivateDuringReplication";
         }

         currentResult = new PropertyDescriptor("PassivateDuringReplication", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("PassivateDuringReplication", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseServersideStubs")) {
         getterName = "isUseServersideStubs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseServersideStubs";
         }

         currentResult = new PropertyDescriptor("UseServersideStubs", StatefulSessionClusteringBean.class, getterName, setterName);
         descriptors.put("UseServersideStubs", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
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
