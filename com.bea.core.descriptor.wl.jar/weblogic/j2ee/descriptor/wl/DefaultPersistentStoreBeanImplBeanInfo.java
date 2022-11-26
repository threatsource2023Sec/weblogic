package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DefaultPersistentStoreBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DefaultPersistentStoreBean.class;

   public DefaultPersistentStoreBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DefaultPersistentStoreBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.DefaultPersistentStoreBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.2.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>This MBean is the parent of the GenericFileStoreMBean and GenericJDBCStoreMBean. It is not intended for deployment.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.DefaultPersistentStoreBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DirectoryPath")) {
         getterName = "getDirectoryPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDirectoryPath";
         }

         currentResult = new PropertyDescriptor("DirectoryPath", DefaultPersistentStoreBean.class, getterName, setterName);
         descriptors.put("DirectoryPath", currentResult);
         currentResult.setValue("description", "<p>Gets the value of the directory path where this store should be found</p> ");
         setPropertyDescriptorDefault(currentResult, "store/default");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Notes")) {
         getterName = "getNotes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotes";
         }

         currentResult = new PropertyDescriptor("Notes", DefaultPersistentStoreBean.class, getterName, setterName);
         descriptors.put("Notes", currentResult);
         currentResult.setValue("description", "<p>Optional information that you can include to describe this named JMS descriptor bean.</p>  <p>JMS module saves this note in the JMS descriptor file as XML PCDATA. All left angle brackets (&lt;) are converted to the XML entity <code>&amp;lt;</code>. Carriage returns/line feeds are preserved.</p>  <dl> <dt>Note:</dt>  <dd> <p>If you create or edit a note from the Administration Console, the Administration Console does not preserve carriage returns/line feeds.</p> </dd> </dl> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SynchronousWritePolicy")) {
         getterName = "getSynchronousWritePolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSynchronousWritePolicy";
         }

         currentResult = new PropertyDescriptor("SynchronousWritePolicy", DefaultPersistentStoreBean.class, getterName, setterName);
         descriptors.put("SynchronousWritePolicy", currentResult);
         currentResult.setValue("description", "<p>Sets the value of the <code>SynchronousWritePolicy</code> attribute.</p> ");
         setPropertyDescriptorDefault(currentResult, "Direct-Write");
         currentResult.setValue("legalValues", new Object[]{"Disabled", "Cache-Flush", "Direct-Write"});
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
