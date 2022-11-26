package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFResourceBeanImplBeanInfo extends WLDFBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFResourceBean.class;

   public WLDFResourceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFResourceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFResourceBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>The top of the WebLogic Diagnostic Framework (WLDF) module bean tree.</p>  <p>All WLDF modules have a WLDFResourceBean as their root bean (a bean with no parent). </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFResourceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("Harvester")) {
         getterName = "getHarvester";
         setterName = null;
         currentResult = new PropertyDescriptor("Harvester", WLDFResourceBean.class, getterName, (String)setterName);
         descriptors.put("Harvester", currentResult);
         currentResult.setValue("description", "<p>The Diagnostic Harvester configuration for this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Instrumentation")) {
         getterName = "getInstrumentation";
         setterName = null;
         currentResult = new PropertyDescriptor("Instrumentation", WLDFResourceBean.class, getterName, (String)setterName);
         descriptors.put("Instrumentation", currentResult);
         currentResult.setValue("description", "<p>The Diagnostic Instrumentation configuration for this deployment.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WatchNotification")) {
         getterName = "getWatchNotification";
         setterName = null;
         currentResult = new PropertyDescriptor("WatchNotification", WLDFResourceBean.class, getterName, (String)setterName);
         descriptors.put("WatchNotification", currentResult);
         currentResult.setValue("description", "<p>The Diagnostic Policy and Action configuration for this deployment.</p> ");
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
