package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class NamedEntityBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = NamedEntityBean.class;

   public NamedEntityBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public NamedEntityBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.NamedEntityBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("All JMS beans that use a name as a primary key are NamedEntityBeans.  All of the named entity beans in a module must have a unique name. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.NamedEntityBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         currentResult = new PropertyDescriptor("Id", NamedEntityBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "<p> Return the unique identifier of this bean instance.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", NamedEntityBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of this entity, represented in XML as an attribute.</p>  <p> Many of the elements within the JMS module are named entities.  All of the named entities encased by a type in the XML file must be unique.  This is the identifier used to delineate a particular entity within the JMS module. </p>  <p> This attribute cannot be modified externally from the module (for example, with a deployment plan) because it may be used inside the module referentially. </p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Notes")) {
         getterName = "getNotes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotes";
         }

         currentResult = new PropertyDescriptor("Notes", NamedEntityBean.class, getterName, setterName);
         descriptors.put("Notes", currentResult);
         currentResult.setValue("description", "<p>Optional information that you can include to describe this named JMS descriptor bean.</p>  <p>JMS module saves this note in the JMS descriptor file as XML PCDATA. All left angle brackets (&lt;) are converted to the XML entity <code>&amp;lt;</code>. Carriage returns/line feeds are preserved.</p>  <dl> <dt>Note:</dt>  <dd> <p>If you create or edit a note from the Administration Console, the Administration Console does not preserve carriage returns/line feeds.</p> </dd> </dl> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
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
