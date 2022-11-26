package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class XMLParserSelectRegistryEntryMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = XMLParserSelectRegistryEntryMBean.class;

   public XMLParserSelectRegistryEntryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XMLParserSelectRegistryEntryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.XMLParserSelectRegistryEntryMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>A Parser Select Entry specifies the SAX/DOM parser factory implementation classes for a particular document type.</p>  <p>By default, WebLogic server uses either the built-in (out-of-the-box) or default SAX/DOM parser factory implementation classes when it parses an XML document.  However, you can specify that particular XML documents, based on their public IDs, system IDs, or root elements, use different parser factory implementation classes than the default.  You do this by first creating an XML Registry and then creating a Parser Select entry and specifying how to identify the document and then the desired implementation classes.</p>   <p>The XML document type is identified by one or more of the following:</p>  <ul> <li> a public ID (e.g, \"-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN\"</li> <li>a system ID (e.g, \"http://java.sun.com/j2ee/dtds/ejb-jar_2_0.dtd\")</li> <li>a document root tag name (e.g., \"ejb-jar\")</li> </ul>  <p>This configuration information is used by the WebLogic JAXP implementation to choose the appropriate parser factories (SAX and DOM).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.XMLParserSelectRegistryEntryMBean");
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

         currentResult = new PropertyDescriptor("DocumentBuilderFactory", XMLParserSelectRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("DocumentBuilderFactory", currentResult);
         currentResult.setValue("description", "<p>Specifies the fully qualified name of the class that implements the <code>DocumentBuilderFactory</code> API</p>  <p>When WebLogic Server begins to parse an XML document identified by either the public ID, system ID, or root element specified in this entry, and the applications specifies it wants a DOM parser, then WebLogic Server uses this implementation class when using the javax.xml.parsers.DocumentBuilderFactory to obtain the DOM parser.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ParserClassName")) {
         getterName = "getParserClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParserClassName";
         }

         currentResult = new PropertyDescriptor("ParserClassName", XMLParserSelectRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("ParserClassName", currentResult);
         currentResult.setValue("description", "<p>Provides the class name of any custom XML parser that is associated with this parser select entry.</p>  <p>Return class name of any custom XML parser that is associated with the registry entry.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublicId")) {
         getterName = "getPublicId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublicId";
         }

         currentResult = new PropertyDescriptor("PublicId", XMLParserSelectRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("PublicId", currentResult);
         currentResult.setValue("description", "<p>Specifies the public ID of the XML document type for which this XML registry entry is being configured.</p>  <p>When WebLogic Server starts to parse an XML document that is identified with this public ID, it uses the SAX or DOM parser factory implementation class specified by this registry entry, rather than the built-in or default, when obtaining a SAX or DOM parser.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RootElementTag")) {
         getterName = "getRootElementTag";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRootElementTag";
         }

         currentResult = new PropertyDescriptor("RootElementTag", XMLParserSelectRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("RootElementTag", currentResult);
         currentResult.setValue("description", "<p>Specifies the root element of the XML document type for which this XML registry entry is being configured.</p>  <p>When WebLogic Server starts to parse an XML document that is identified with this root element, it uses the SAX or DOM parser factory implementation class specified by this registry entry, rather than the built-in or default, when obtaining a SAX or DOM parser.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAXParserFactory")) {
         getterName = "getSAXParserFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAXParserFactory";
         }

         currentResult = new PropertyDescriptor("SAXParserFactory", XMLParserSelectRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("SAXParserFactory", currentResult);
         currentResult.setValue("description", "<p>Specifies the fully qualified name of the class that implements the <code>SAXParserFactory</code> API.</p>  <p>When WebLogic Server begins to parse an XML document identified by either the public ID, system ID, or root element specified in this entry, and the applications specifies it wants a SAX parser, then WebLogic Server uses this implementation class when using the javax.xml.parsers.SAXParserFactory to obtain the SAX parser.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemId")) {
         getterName = "getSystemId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemId";
         }

         currentResult = new PropertyDescriptor("SystemId", XMLParserSelectRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("SystemId", currentResult);
         currentResult.setValue("description", "<p>Specifies the system ID of the XML document type for which this XML registry entry is being configured.</p>  <p>When WebLogic Server starts to parse an XML document that is identified with this system ID, it uses the SAX or DOM parser factory implementation class specified by this registry entry, rather than the built-in or default, when obtaining a SAX or DOM parser.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransformerFactory")) {
         getterName = "getTransformerFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransformerFactory";
         }

         currentResult = new PropertyDescriptor("TransformerFactory", XMLParserSelectRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("TransformerFactory", currentResult);
         currentResult.setValue("description", "<p>Specifies the fully qualified name of the class that implements the <code>TransformerFactory</code> API.</p>  <p>When WebLogic Server begins to transform an XML document identified by either the public ID, system ID, or root element specified in this entry, then WebLogic Server uses this implementation class when using the javax.xml.transform.TranformerFactory factory to obtain a Transformer object.</p> ");
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
