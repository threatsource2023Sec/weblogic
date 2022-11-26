package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class XMLRegistryEntryMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = XMLRegistryEntryMBean.class;

   public XMLRegistryEntryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XMLRegistryEntryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.XMLRegistryEntryMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("deprecated", "7.0.0.0 replaced by {@link weblogic.management.configuration.XMLRegistryMBean}. ");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This is an entry in the XML registry. An XML registry entry is configuration information associated with a particular XML document type. The document type is identified by one or more of the following:  1) a public ID (e.g, \"-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN\" 2) a system ID (e.g, \"http://java.sun.com/j2ee/dtds/ejb-jar_2_0.dtd\") 3) a document root tag name (e.g., \"ejb-jar\")  In Version 6.0 this configuration information is used by the WebLogic JAXP implementation to choose the appropriate parser factories and parsers and to set up SAX EntityResolvers. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.XMLRegistryEntryMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DocumentBuilderFactory")) {
         getterName = "getDocumentBuilderFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDocumentBuilderFactory";
         }

         currentResult = new PropertyDescriptor("DocumentBuilderFactory", XMLRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("DocumentBuilderFactory", currentResult);
         currentResult.setValue("description", "<p>Provides the class name of the DocumentBuilderFactory that is associated with the registry entry.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityPath")) {
         getterName = "getEntityPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntityPath";
         }

         currentResult = new PropertyDescriptor("EntityPath", XMLRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("EntityPath", currentResult);
         currentResult.setValue("description", "<p>Provides the path name to a local copy of an external entity (e.g., a DTD) that is associated with this registry entry.</p>  <p>Return the path name to a local copy of an external entity (e.g., a DTD) that is associated with this registry entry. This pathname is relative to one of the XML registry directories of the installation.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParserClassName")) {
         getterName = "getParserClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParserClassName";
         }

         currentResult = new PropertyDescriptor("ParserClassName", XMLRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("ParserClassName", currentResult);
         currentResult.setValue("description", "<p>Provides the class name of any custom XML parser that is associated with the registry entry.</p>  <p>Return class name of any custom XML parser that is associated with the registry entry.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublicId")) {
         getterName = "getPublicId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublicId";
         }

         currentResult = new PropertyDescriptor("PublicId", XMLRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("PublicId", currentResult);
         currentResult.setValue("description", "<p>Provides the public id of the document type represented by this registry entry.</p>  <p>Get the public id of the document type represented by this registry entry.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RootElementTag")) {
         getterName = "getRootElementTag";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRootElementTag";
         }

         currentResult = new PropertyDescriptor("RootElementTag", XMLRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("RootElementTag", currentResult);
         currentResult.setValue("description", "<p>Provides the tag name of the document root element of the document type represented by this registry entry.</p>  <p>Get the tag name of the document root element of the document type represented by this registry entry.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAXParserFactory")) {
         getterName = "getSAXParserFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAXParserFactory";
         }

         currentResult = new PropertyDescriptor("SAXParserFactory", XMLRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("SAXParserFactory", currentResult);
         currentResult.setValue("description", "<p>Provides the class name of the SAXParserFactory that is associated with the registry entry.</p>  <p>Return the class name of the SAXParserFactory that is associated with the registry entry.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemId")) {
         getterName = "getSystemId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemId";
         }

         currentResult = new PropertyDescriptor("SystemId", XMLRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("SystemId", currentResult);
         currentResult.setValue("description", "<p>Provides the system id of the document type represented by this registry entry.</p>  <p>Get the system id of the document type represented by this registry entry.</p> ");
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
