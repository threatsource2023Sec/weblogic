package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class XMLRegistryMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = XMLRegistryMBean.class;

   public XMLRegistryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XMLRegistryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.XMLRegistryMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Configure the behavior of JAXP (Java API for XML Parsing) in the server.</p>  <p>You configure this behavior by creating XML Registries that specify the default DOM and Sax factory implementation class, transformer factory implementation class, external entity resolution and caching.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.XMLRegistryMBean");
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

         currentResult = new PropertyDescriptor("DocumentBuilderFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("DocumentBuilderFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>DocumentBuilderFactory</code> interface.</p>  <p>The <code>javax.xml.parsers.DocumentBuilderFactory</code> factory API enables applications deployed to WebLogic Server to obtain an XML parser that produces DOM object trees from XML documents.</p>  <p>The built-in WebLogic Server DOM factory implementation class is <code>com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a DOM parser.  You can change this default by updating this value.</p>  <p>Return the class name of the default DocumentBuilderFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicDocumentBuilderFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntitySpecRegistryEntries")) {
         getterName = "getEntitySpecRegistryEntries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntitySpecRegistryEntries";
         }

         currentResult = new PropertyDescriptor("EntitySpecRegistryEntries", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("EntitySpecRegistryEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a list of EntitySpec registry entries.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addEntitySpecRegistryEntry");
         currentResult.setValue("remover", "removeEntitySpecRegistryEntry");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ParserSelectRegistryEntries")) {
         getterName = "getParserSelectRegistryEntries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParserSelectRegistryEntries";
         }

         currentResult = new PropertyDescriptor("ParserSelectRegistryEntries", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("ParserSelectRegistryEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the set of ParserSelect registry entries.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addParserSelectRegistryEntry");
         currentResult.setValue("remover", "removeParserSelectRegistryEntry");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RegistryEntries")) {
         getterName = "getRegistryEntries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRegistryEntries";
         }

         currentResult = new PropertyDescriptor("RegistryEntries", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("RegistryEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the set of pre-Silversword style registry entries.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addRegistryEntry");
         currentResult.setValue("deprecated", "7.0.0.0 replaced by {@link weblogic.management.configuration.XMLRegistryMBean} ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAXParserFactory")) {
         getterName = "getSAXParserFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSAXParserFactory";
         }

         currentResult = new PropertyDescriptor("SAXParserFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("SAXParserFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>SAXParserFactory</code> interface.</p>  <p>The <code>javax.xml.parsers.SAXParserFactory</code> factory API enables applications deployed to WebLogic Server to configure and obtain a SAX-based XML parser to parse XML documents.</p>  <p>The built-in WebLogic Server SAX factory implementation class is <code>com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a SAX parser.  You can change this default by updating this value.</p>  <p>Return the class name of the default SAXParserFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicSAXParserFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SchemaFactory")) {
         getterName = "getSchemaFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSchemaFactory";
         }

         currentResult = new PropertyDescriptor("SchemaFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("SchemaFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>SchemaFactory</code> interface.</p>  <p>The <code>javax.xml.validation.SchemaFactory</code> factory API enables applications deployed to WebLogic Server to configure and obtain a <code>Schema</code> object used to validate XML document using schema.</p>  <p>The built-in WebLogic Server Schema factory implementation class is <code>com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a Schema object.  You can change this default by updating this value.</p>   <p>Return the class name of the default SchemaFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicSchemaFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("TransformerFactory")) {
         getterName = "getTransformerFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransformerFactory";
         }

         currentResult = new PropertyDescriptor("TransformerFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("TransformerFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>TransformerFactory</code> interface.</p>  <p>The <code>javax.xml.transform.TransformerFactory</code> factory API enables applications deployed to WebLogic Server to configure and obtain a <code>Transformer</code> object used to transform XML data into another format.</p>  <p>The built-in WebLogic Server Transformer factory implementation class is <code>com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryIml</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a Transformer object.  You can change this default by updating this value.</p>   <p>Return the class name of the default TransformerFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicTransformerFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WhenToCache")) {
         getterName = "getWhenToCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWhenToCache";
         }

         currentResult = new PropertyDescriptor("WhenToCache", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("WhenToCache", currentResult);
         currentResult.setValue("description", "<p>Specifies when WebLogic Server should cache external entities that it retrieves from the Web.</p>  <p>When WebLogic Server resolves an external entity within an XML file and retrieves the entity from the Web, you can specify that WebLogic Server cache this entity only when the entity is first referenced, when WebLogic Server first starts up, or not at all.</p> ");
         setPropertyDescriptorDefault(currentResult, "cache-on-reference");
         currentResult.setValue("legalValues", new Object[]{"cache-on-reference", "cache-at-initialization", "cache-never"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLEntitySpecRegistryEntries")) {
         getterName = "getXMLEntitySpecRegistryEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("XMLEntitySpecRegistryEntries", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("XMLEntitySpecRegistryEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a list of EntitySpec registry entries.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createXMLEntitySpecRegistryEntry");
         currentResult.setValue("destroyer", "destroyXMLEntitySpecRegistryEntry");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLEventFactory")) {
         getterName = "getXMLEventFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLEventFactory";
         }

         currentResult = new PropertyDescriptor("XMLEventFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("XMLEventFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>XMLEventFactory</code> interface.</p>  <p>The <code>javax.xml.stream.XMLEventFactory</code> factory API enables applications deployed to WebLogic Server to configure and obtain a <code>XMLEvent</code> object used to parse or build XML streams.</p>  <p>The built-in WebLogic Server XMLEvent factory implementation class is <code>com.ctc.wstx.stax.WstxEventFactory</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a XMLEvent object.  You can change this default by updating this value.</p>   <p>Return the class name of the default XMLEventFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicXMLEventFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLInputFactory")) {
         getterName = "getXMLInputFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLInputFactory";
         }

         currentResult = new PropertyDescriptor("XMLInputFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("XMLInputFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>XMLInputFactory</code> interface.</p>  <p>The <code>javax.xml.stream.XMLInputFactory</code> factory API enables applications deployed to WebLogic Server to configure and obtain a <code>XMLEventReader/XMLStreamReader</code> object used to read XML streams.</p>  <p>The built-in WebLogic Server XML Input factory implementation class is <code>com.ctc.wstx.stax.WstxInputFactory</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a XMLEventReader/XMLStreamReader object. You can change this default by updating this value.</p>   <p>Return the class name of the default XMLInputFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicXMLInputFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLOutputFactory")) {
         getterName = "getXMLOutputFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLOutputFactory";
         }

         currentResult = new PropertyDescriptor("XMLOutputFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("XMLOutputFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>XMLOutputFactory</code> interface.</p>  <p>The <code>javax.xml.stream.XMLOutputFactory</code> factory API enables applications deployed to WebLogic Server to configure and obtain a <code>XMLEventWriter/XMLStreamWriter</code> object used to write XML streams.</p>  <p>The built-in WebLogic Server XML Output factory implementation class is <code>com.ctc.wstx.stax.WstxOutputFactory</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a XMLEventWriter/XMLStreamWriter object. You can change this default by updating this value.</p>   <p>Return the class name of the default XMLOutputFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicXMLOutputFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLParserSelectRegistryEntries")) {
         getterName = "getXMLParserSelectRegistryEntries";
         setterName = null;
         currentResult = new PropertyDescriptor("XMLParserSelectRegistryEntries", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("XMLParserSelectRegistryEntries", currentResult);
         currentResult.setValue("description", "<p>Provides a list of the set of ParserSelect registry entries.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyXMLParserSelectRegistryEntry");
         currentResult.setValue("creator", "createXMLParserSelectRegistryEntry");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XpathFactory")) {
         getterName = "getXpathFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXpathFactory";
         }

         currentResult = new PropertyDescriptor("XpathFactory", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("XpathFactory", currentResult);
         currentResult.setValue("description", "<p>The fully qualified name of the class that implements the <code>XPathFactory</code> interface.</p>  <p>The <code>javax.xml.xpath.XPathFactory</code> factory API enables applications deployed to WebLogic Server to configure and obtain a <code>XPath</code> object used to search XML elements.</p>  <p>The built-in WebLogic Server XPath factory implementation class is <code>com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl</code>. This is the factory class applications deployed to WebLogic Server get by default when they request a XPath object.  You can change this default by updating this value.</p>   <p>Return the class name of the default XPathFactory</p> ");
         setPropertyDescriptorDefault(currentResult, "weblogic.xml.jaxp.WebLogicXPathFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("HandleEntityInvalidation")) {
         getterName = "isHandleEntityInvalidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHandleEntityInvalidation";
         }

         currentResult = new PropertyDescriptor("HandleEntityInvalidation", XMLRegistryMBean.class, getterName, setterName);
         descriptors.put("HandleEntityInvalidation", currentResult);
         currentResult.setValue("description", "<p>Whether cached DTD/schema is invalidated when parsing error is encountered.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = XMLRegistryMBean.class.getMethod("createXMLParserSelectRegistryEntry", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>creates an XMLParserSelectRegistryEntryMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLParserSelectRegistryEntries");
      }

      mth = XMLRegistryMBean.class.getMethod("destroyXMLParserSelectRegistryEntry", XMLParserSelectRegistryEntryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("entry", "XMLParserSelectRegistryEntry object ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLParserSelectRegistryEntries");
      }

      mth = XMLRegistryMBean.class.getMethod("createXMLEntitySpecRegistryEntry", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLEntitySpecRegistryEntries");
      }

      mth = XMLRegistryMBean.class.getMethod("destroyXMLEntitySpecRegistryEntry", XMLEntitySpecRegistryEntryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("entry", "XMLEntitySpecRegistryEntry object ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLEntitySpecRegistryEntries");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = XMLRegistryMBean.class.getMethod("addRegistryEntry", XMLRegistryEntryMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("entry", "The feature to be added to the RegistryEntry attribute ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "7.0.0.0 replaced by {@link weblogic.management.configuration.XMLRegistryMBean} ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a new pre-Silversword style registry entry</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "RegistryEntries");
      }

      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = XMLRegistryMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = XMLRegistryMBean.class.getMethod("addParserSelectRegistryEntry", XMLParserSelectRegistryEntryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("entry", "The feature to be added to the ParserSelectRegistryEntry attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a new ParserSelect registry entry</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ParserSelectRegistryEntries");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = XMLRegistryMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = XMLRegistryMBean.class.getMethod("removeParserSelectRegistryEntry", XMLParserSelectRegistryEntryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("entry", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a ParserSelect registry entry</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "ParserSelectRegistryEntries");
      }

      mth = XMLRegistryMBean.class.getMethod("addEntitySpecRegistryEntry", XMLEntitySpecRegistryEntryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("entry", "The feature to be added to the EntitySpecRegistryEntry attribute ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Adds a new EntitySpec registry entry</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "EntitySpecRegistryEntries");
      }

      mth = XMLRegistryMBean.class.getMethod("removeEntitySpecRegistryEntry", XMLEntitySpecRegistryEntryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("entry", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a EntitySpec registry entry</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "EntitySpecRegistryEntries");
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = XMLRegistryMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = XMLRegistryMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

      mth = XMLRegistryMBean.class.getMethod("findParserSelectMBeanByKey", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("publicID", (String)null), createParameterDescriptor("systemID", (String)null), createParameterDescriptor("rootTag", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns an parser select registry entry given the entry's key.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = XMLRegistryMBean.class.getMethod("findEntitySpecMBeanByKey", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("publicID", (String)null), createParameterDescriptor("systemID", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns an entity spec registry entry given the entry's key.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

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
