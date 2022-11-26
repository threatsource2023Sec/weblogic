package weblogic.coherence.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherencePersistenceParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherencePersistenceParamsBean.class;

   public CoherencePersistenceParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherencePersistenceParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.CoherencePersistenceParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.1.0");
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String("Bean to override default Persistence environments in Coherence ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.CoherencePersistenceParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ActiveDirectory")) {
         getterName = "getActiveDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setActiveDirectory";
         }

         currentResult = new PropertyDescriptor("ActiveDirectory", CoherencePersistenceParamsBean.class, getterName, setterName);
         descriptors.put("ActiveDirectory", currentResult);
         currentResult.setValue("description", "<p>The active directory for the default persistence environment. If no value is specified, the directory which will be used is the coherence/active sub-directory under Domain Home directory. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultPersistenceMode")) {
         getterName = "getDefaultPersistenceMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultPersistenceMode";
         }

         currentResult = new PropertyDescriptor("DefaultPersistenceMode", CoherencePersistenceParamsBean.class, getterName, setterName);
         descriptors.put("DefaultPersistenceMode", currentResult);
         currentResult.setValue("description", "<p>The default persistence mode. </p> ");
         setPropertyDescriptorDefault(currentResult, "on-demand");
         currentResult.setValue("legalValues", new Object[]{"on-demand", "active"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SnapshotDirectory")) {
         getterName = "getSnapshotDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSnapshotDirectory";
         }

         currentResult = new PropertyDescriptor("SnapshotDirectory", CoherencePersistenceParamsBean.class, getterName, setterName);
         descriptors.put("SnapshotDirectory", currentResult);
         currentResult.setValue("description", "<p>The snapshot directory for the default persistence environment. If no value is specified, the directory which will be used is coherence/snapshot sub-directory under Domain Home directory. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TrashDirectory")) {
         getterName = "getTrashDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTrashDirectory";
         }

         currentResult = new PropertyDescriptor("TrashDirectory", CoherencePersistenceParamsBean.class, getterName, setterName);
         descriptors.put("TrashDirectory", currentResult);
         currentResult.setValue("description", "<p>The trash directory for the default persistence environment. If no value is specified, the directory which will be used is coherence/trash sub-directory under Domain Home directory. </p> ");
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
