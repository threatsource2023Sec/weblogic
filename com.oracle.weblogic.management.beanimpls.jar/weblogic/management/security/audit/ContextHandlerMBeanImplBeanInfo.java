package weblogic.management.security.audit;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;

public class ContextHandlerMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ContextHandlerMBean.class;

   public ContextHandlerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ContextHandlerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.audit.ContextHandlerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.security.audit");
      String description = (new String("Provides a set of attributes for ContextHandler support. An Auditor provider MBean can optionally implement this MBean. The WebLogic Server Administration Console detects when an Auditor provider implements this MBean and automatically provides a tab for using these attributes. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.audit.ContextHandlerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ActiveContextHandlerEntries")) {
         getterName = "getActiveContextHandlerEntries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setActiveContextHandlerEntries";
         }

         currentResult = new PropertyDescriptor("ActiveContextHandlerEntries", ContextHandlerMBean.class, getterName, setterName);
         descriptors.put("ActiveContextHandlerEntries", currentResult);
         currentResult.setValue("description", "Returns the ContextHandler entries that the Audit provider is currently configured to process. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SupportedContextHandlerEntries")) {
         getterName = "getSupportedContextHandlerEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("SupportedContextHandlerEntries", ContextHandlerMBean.class, getterName, setterName);
         descriptors.put("SupportedContextHandlerEntries", currentResult);
         currentResult.setValue("description", "Returns the list of all ContextHandler entries supported by the auditor. ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
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
