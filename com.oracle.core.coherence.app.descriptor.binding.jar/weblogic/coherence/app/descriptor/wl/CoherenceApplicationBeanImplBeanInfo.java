package weblogic.coherence.app.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceApplicationBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceApplicationBean.class;

   public CoherenceApplicationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceApplicationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.app.descriptor.wl.CoherenceApplicationBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.coherence.app.descriptor.wl");
      String description = (new String("The top of the Coherence Application bean tree. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ApplicationLifecycleListener")) {
         getterName = "getApplicationLifecycleListener";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationLifecycleListener", CoherenceApplicationBean.class, getterName, (String)setterName);
         descriptors.put("ApplicationLifecycleListener", currentResult);
         currentResult.setValue("description", "<p>The Coherence ApplicationLifecycleEventListener parameters that have been defined for this CoherenceApplicationBean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheConfigurationRef")) {
         getterName = "getCacheConfigurationRef";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheConfigurationRef", CoherenceApplicationBean.class, getterName, (String)setterName);
         descriptors.put("CacheConfigurationRef", currentResult);
         currentResult.setValue("description", "<p>The Coherence Cache Configuration parameters that have been defined for this CoherenceApplicationBean. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigurableCacheFactoryConfig")) {
         getterName = "getConfigurableCacheFactoryConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurableCacheFactoryConfig", CoherenceApplicationBean.class, getterName, (String)setterName);
         descriptors.put("ConfigurableCacheFactoryConfig", currentResult);
         currentResult.setValue("description", "<p>The Coherence ApplicationLifecycleEventListener parameters that have been defined for this CoherenceApplicationBean. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PofConfigurationRef")) {
         getterName = "getPofConfigurationRef";
         setterName = null;
         currentResult = new PropertyDescriptor("PofConfigurationRef", CoherenceApplicationBean.class, getterName, (String)setterName);
         descriptors.put("PofConfigurationRef", currentResult);
         currentResult.setValue("description", "<p>The Coherence pof Configuration parameters that have been defined for this CoherenceApplicationBean. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
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
