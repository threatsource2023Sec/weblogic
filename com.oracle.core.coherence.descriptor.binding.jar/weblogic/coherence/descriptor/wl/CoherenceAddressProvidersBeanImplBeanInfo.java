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

public class CoherenceAddressProvidersBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceAddressProvidersBean.class;

   public CoherenceAddressProvidersBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceAddressProvidersBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.CoherenceAddressProvidersBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String("A unique name that identifies all the address providers for a Coherence cluster. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.CoherenceAddressProvidersBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("CoherenceAddressProviders")) {
         String getterName = "getCoherenceAddressProviders";
         String setterName = null;
         currentResult = new PropertyDescriptor("CoherenceAddressProviders", CoherenceAddressProvidersBean.class, getterName, (String)setterName);
         descriptors.put("CoherenceAddressProviders", currentResult);
         currentResult.setValue("description", "An array of CoherenceAddressProvider beans, each of which represents an address provider. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCoherenceAddressProvider");
         currentResult.setValue("destroyer", "destroyCoherenceAddressProvider");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceAddressProvidersBean.class.getMethod("createCoherenceAddressProvider", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a new CoherenceAddressProvider bean representing the specified address provider and adds it to the list of currently existing beans. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceAddressProviders");
      }

      mth = CoherenceAddressProvidersBean.class.getMethod("destroyCoherenceAddressProvider", CoherenceAddressProviderBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the bean representing the specified address provider and removes it from the list of currently existing beans. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CoherenceAddressProviders");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceAddressProvidersBean.class.getMethod("lookupCoherenceAddressProvider", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "The bean representing the specified address provider. ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CoherenceAddressProviders");
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
