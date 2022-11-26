package weblogic.coherence.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceClusterWellKnownAddressesBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceClusterWellKnownAddressesBean.class;

   public CoherenceClusterWellKnownAddressesBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceClusterWellKnownAddressesBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String("A unique name that identifies all the well known addresses for a Coherence cluster. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("CoherenceClusterWellKnownAddresses")) {
         String getterName = "getCoherenceClusterWellKnownAddresses";
         String setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterWellKnownAddresses", CoherenceClusterWellKnownAddressesBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceClusterWellKnownAddresses", currentResult);
         currentResult.setValue("description", "An array of CoherenceWellKnownAddress beans, each of which represents a well known address. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCoherenceClusterWellKnownAddress");
         currentResult.setValue("creator", "createCoherenceClusterWellKnownAddress");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceClusterWellKnownAddressesBean.class.getMethod("createCoherenceClusterWellKnownAddress", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a new CoherenceWellKnownAddress bean representing the specified well known address and adds it to the list of currently existing beans. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterWellKnownAddresses");
      }

      mth = CoherenceClusterWellKnownAddressesBean.class.getMethod("destroyCoherenceClusterWellKnownAddress", CoherenceClusterWellKnownAddressBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the bean representing the specified well known address and removes it from the list of currently existing beans. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceClusterWellKnownAddresses");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceClusterWellKnownAddressesBean.class.getMethod("lookupCoherenceClusterWellKnownAddress", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The bean representing the specified well known address. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherenceClusterWellKnownAddresses");
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
