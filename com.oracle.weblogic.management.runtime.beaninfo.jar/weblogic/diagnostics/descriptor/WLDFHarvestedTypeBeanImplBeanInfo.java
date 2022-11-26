package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFHarvestedTypeBeanImplBeanInfo extends WLDFBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFHarvestedTypeBean.class;

   public WLDFHarvestedTypeBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFHarvestedTypeBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFHarvestedTypeBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Defines the set of types (beans) that are harvested. The WLDF framework allows the harvesting of all designated server-local Weblogic Server runtime MBeans, and most customer MBeans that are registered in the local server's runtime MBean server. Configuration MBeans cannot be harvested.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFHarvestedTypeBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("HarvestedAttributes")) {
         getterName = "getHarvestedAttributes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHarvestedAttributes";
         }

         currentResult = new PropertyDescriptor("HarvestedAttributes", WLDFHarvestedTypeBean.class, getterName, setterName);
         descriptors.put("HarvestedAttributes", currentResult);
         currentResult.setValue("description", "<p>The harvested attributes for this type. If a list of attributes is provided, only those attributes are harvested; otherwise all harvestable attributes are harvested.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HarvestedInstances")) {
         getterName = "getHarvestedInstances";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHarvestedInstances";
         }

         currentResult = new PropertyDescriptor("HarvestedInstances", WLDFHarvestedTypeBean.class, getterName, setterName);
         descriptors.put("HarvestedInstances", currentResult);
         currentResult.setValue("description", "<p>The harvested instances of this type.</p>  <p>The configuration of a type can optionally provide a set of identifiers for specific instances. If this list is provided, only the provided instances are harvested; otherwise all instances of the type are harvested.</p>  <p>The identifier for an instance must be a valid JMX ObjectName or an ObjectName pattern.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WLDFHarvestedTypeBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The type name. For WebLogic Server runtime MBeans, the type name is the fully qualified name of the defining interface. For customer MBeans, the type name is the fully qualified MBean implementation class.</p> ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Namespace")) {
         getterName = "getNamespace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNamespace";
         }

         currentResult = new PropertyDescriptor("Namespace", WLDFHarvestedTypeBean.class, getterName, setterName);
         descriptors.put("Namespace", currentResult);
         currentResult.setValue("description", "<p>The namespace for the harvested type definition.</p> ");
         setPropertyDescriptorDefault(currentResult, "ServerRuntime");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"ServerRuntime", "DomainRuntime"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnabled";
         }

         currentResult = new PropertyDescriptor("Enabled", WLDFHarvestedTypeBean.class, getterName, setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this type is enabled. Note that enabling a type will have no effect unless the Harvester component is also enabled.</p>  <p>A <code>true</code> value means that this type is harvested. A <code>false</code> value indicates that that this type is not harvested.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KnownType")) {
         getterName = "isKnownType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKnownType";
         }

         currentResult = new PropertyDescriptor("KnownType", WLDFHarvestedTypeBean.class, getterName, setterName);
         descriptors.put("KnownType", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this type is known at startup.  Normally, if a type is not available, the Harvester will keep looking for it. If a type is designated as \"known\", the Harvester issues a validation fault if the type cannot be immediately resolved.</p>  <p>A <code>true</code> value means that this type is known. A <code>false</code> value indicates that this type may not be known.</p>  <p>This flag is useful for WebLogic Server types, where the type information is always available.  In this case, setting the flag to true results in earlier detection and reporting of problems.</p>  <p>This flag is optional, but is recommended for WebLogic Server types.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
