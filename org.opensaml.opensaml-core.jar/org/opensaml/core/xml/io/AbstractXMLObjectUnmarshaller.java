package org.opensaml.core.xml.io;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.XMLConstants;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.Namespace;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public abstract class AbstractXMLObjectUnmarshaller implements Unmarshaller {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractXMLObjectUnmarshaller.class);
   @Nonnull
   private final XMLObjectBuilderFactory xmlObjectBuilderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
   @Nonnull
   private final UnmarshallerFactory unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();

   protected AbstractXMLObjectUnmarshaller() {
   }

   @Nonnull
   public XMLObject unmarshall(@Nonnull Element domElement) throws UnmarshallingException {
      this.log.trace("Starting to unmarshall DOM element {}", QNameSupport.getNodeQName(domElement));
      XMLObject xmlObject = this.buildXMLObject(domElement);
      if (this.log.isTraceEnabled()) {
         this.log.trace("Unmarshalling attributes of DOM Element {}", QNameSupport.getNodeQName(domElement));
      }

      NamedNodeMap attributes = domElement.getAttributes();

      for(int i = 0; i < attributes.getLength(); ++i) {
         Node attribute = attributes.item(i);
         if (attribute.getNodeType() == 2) {
            this.unmarshallAttribute(xmlObject, (Attr)attribute);
         }
      }

      if (this.log.isTraceEnabled()) {
         this.log.trace("Unmarshalling other child nodes of DOM Element {}", QNameSupport.getNodeQName(domElement));
      }

      for(Node childNode = domElement.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
         if (childNode.getNodeType() == 2) {
            this.unmarshallAttribute(xmlObject, (Attr)childNode);
         } else if (childNode.getNodeType() == 1) {
            this.unmarshallChildElement(xmlObject, (Element)childNode);
         } else if (childNode.getNodeType() == 3 || childNode.getNodeType() == 4) {
            this.unmarshallTextContent(xmlObject, (Text)childNode);
         }
      }

      xmlObject.setDOM(domElement);
      return xmlObject;
   }

   @Nonnull
   protected XMLObject buildXMLObject(@Nonnull Element domElement) throws UnmarshallingException {
      if (this.log.isTraceEnabled()) {
         this.log.trace("Building XMLObject for {}", QNameSupport.getNodeQName(domElement));
      }

      XMLObjectBuilder xmlObjectBuilder = this.xmlObjectBuilderFactory.getBuilder(domElement);
      if (xmlObjectBuilder == null) {
         xmlObjectBuilder = this.xmlObjectBuilderFactory.getBuilder(XMLObjectProviderRegistrySupport.getDefaultProviderQName());
         if (xmlObjectBuilder == null) {
            String errorMsg = "Unable to locate builder for " + QNameSupport.getNodeQName(domElement);
            this.log.error(errorMsg);
            throw new UnmarshallingException(errorMsg);
         }

         if (this.log.isTraceEnabled()) {
            this.log.trace("No builder was registered for {} but the default builder {} was available, using it.", QNameSupport.getNodeQName(domElement), xmlObjectBuilder.getClass().getName());
         }
      }

      return xmlObjectBuilder.buildObject(domElement);
   }

   protected void unmarshallAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
      QName attribName = QNameSupport.getNodeQName(attribute);
      this.log.trace("Pre-processing attribute {}", attribName);
      String attributeNamespace = StringSupport.trimOrNull(attribute.getNamespaceURI());
      if (Objects.equals(attributeNamespace, "http://www.w3.org/2000/xmlns/")) {
         this.unmarshallNamespaceAttribute(xmlObject, attribute);
      } else if (Objects.equals(attributeNamespace, "http://www.w3.org/2001/XMLSchema-instance")) {
         this.unmarshallSchemaInstanceAttributes(xmlObject, attribute);
      } else {
         if (this.log.isTraceEnabled()) {
            this.log.trace("Attribute {} is neither a schema type nor namespace, calling processAttribute()", QNameSupport.getNodeQName(attribute));
         }

         String attributeNSURI = attribute.getNamespaceURI();
         if (attributeNSURI != null) {
            String attributeNSPrefix = attribute.lookupPrefix(attributeNSURI);
            if (attributeNSPrefix == null && "http://www.w3.org/XML/1998/namespace".equals(attributeNSURI)) {
               attributeNSPrefix = "xml";
            }

            xmlObject.getNamespaceManager().registerAttributeName(attribName);
         }

         this.checkIDAttribute(attribute);
         this.processAttribute(xmlObject, attribute);
      }

   }

   protected void unmarshallNamespaceAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("{} is a namespace declaration, adding it to the list of namespaces on the XMLObject", QNameSupport.getNodeQName(attribute));
      }

      Namespace namespace;
      if (Objects.equals(attribute.getLocalName(), "xmlns")) {
         namespace = new Namespace(attribute.getValue(), (String)null);
      } else {
         namespace = new Namespace(attribute.getValue(), attribute.getLocalName());
      }

      xmlObject.getNamespaceManager().registerNamespaceDeclaration(namespace);
   }

   protected void unmarshallSchemaInstanceAttributes(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) {
      QName attribName = QNameSupport.getNodeQName(attribute);
      if (XMLConstants.XSI_TYPE_ATTRIB_NAME.equals(attribName)) {
         if (this.log.isTraceEnabled()) {
            this.log.trace("Saw XMLObject {} with an xsi:type of: {}", xmlObject.getElementQName(), attribute.getValue());
         }
      } else if (XMLConstants.XSI_SCHEMA_LOCATION_ATTRIB_NAME.equals(attribName)) {
         if (this.log.isTraceEnabled()) {
            this.log.trace("Saw XMLObject {} with an xsi:schemaLocation of: {}", xmlObject.getElementQName(), attribute.getValue());
         }

         xmlObject.setSchemaLocation(attribute.getValue());
      } else if (XMLConstants.XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME.equals(attribName)) {
         if (this.log.isTraceEnabled()) {
            this.log.trace("Saw XMLObject {} with an xsi:noNamespaceSchemaLocation of: {}", xmlObject.getElementQName(), attribute.getValue());
         }

         xmlObject.setNoNamespaceSchemaLocation(attribute.getValue());
      } else if (XMLConstants.XSI_NIL_ATTRIB_NAME.equals(attribName)) {
         if (this.log.isTraceEnabled()) {
            this.log.trace("Saw XMLObject {} with an xsi:nil of: {}", xmlObject.getElementQName(), attribute.getValue());
         }

         xmlObject.setNil(XSBooleanValue.valueOf(attribute.getValue()));
      }

   }

   protected void checkIDAttribute(@Nonnull Attr attribute) {
      QName attribName = QNameSupport.getNodeQName(attribute);
      if (XMLObjectProviderRegistrySupport.isIDAttribute(attribName) && !attribute.isId()) {
         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      }

   }

   protected void unmarshallChildElement(@Nonnull XMLObject xmlObject, @Nonnull Element childElement) throws UnmarshallingException {
      if (this.log.isTraceEnabled()) {
         this.log.trace("Unmarshalling child elements of XMLObject {}", xmlObject.getElementQName());
      }

      Unmarshaller unmarshaller = this.unmarshallerFactory.getUnmarshaller(childElement);
      if (unmarshaller == null) {
         unmarshaller = this.unmarshallerFactory.getUnmarshaller(XMLObjectProviderRegistrySupport.getDefaultProviderQName());
         if (unmarshaller == null) {
            String errorMsg = "No unmarshaller available for " + QNameSupport.getNodeQName(childElement) + ", child of " + xmlObject.getElementQName();
            this.log.error(errorMsg);
            throw new UnmarshallingException(errorMsg);
         }

         if (this.log.isTraceEnabled()) {
            this.log.trace("No unmarshaller was registered for {}, child of {}. Using default unmarshaller.", QNameSupport.getNodeQName(childElement), xmlObject.getElementQName());
         }
      }

      if (this.log.isTraceEnabled()) {
         this.log.trace("Unmarshalling child element {} with unmarshaller {}", QNameSupport.getNodeQName(childElement), unmarshaller.getClass().getName());
      }

      this.processChildElement(xmlObject, unmarshaller.unmarshall(childElement));
   }

   protected void unmarshallTextContent(@Nonnull XMLObject xmlObject, @Nonnull Text content) throws UnmarshallingException {
      String textContent = StringSupport.trimOrNull(content.getWholeText());
      if (textContent != null) {
         this.processElementContent(xmlObject, textContent);
      }

   }

   protected void processChildElement(@Nonnull XMLObject parentXMLObject, @Nonnull XMLObject childXMLObject) throws UnmarshallingException {
      this.log.debug("Ignoring unknown child element {}", childXMLObject.getElementQName());
   }

   protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
      this.log.debug("Ignoring unknown attribute {}", QNameSupport.getNodeQName(attribute));
   }

   protected void processElementContent(@Nonnull XMLObject xmlObject, @Nonnull String elementContent) {
      this.log.debug("Ignoring unknown element content {}", elementContent);
   }

   protected void processUnknownAttribute(@Nonnull AttributeExtensibleXMLObject xmlObject, @Nonnull Attr attribute) {
      XMLObjectSupport.unmarshallToAttributeMap(xmlObject.getUnknownAttributes(), attribute);
   }
}
