package org.opensaml.core.xml.io;

import com.google.common.base.Strings;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.NamespaceSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.Namespace;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractXMLObjectMarshaller implements Marshaller {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractXMLObjectMarshaller.class);
   @Nonnull
   private final MarshallerFactory marshallerFactory = XMLObjectProviderRegistrySupport.getMarshallerFactory();

   protected AbstractXMLObjectMarshaller() {
   }

   @Nonnull
   public Element marshall(@Nonnull XMLObject xmlObject) throws MarshallingException {
      try {
         Document document = XMLObjectProviderRegistrySupport.getParserPool().newDocument();
         return this.marshall(xmlObject, document);
      } catch (XMLParserException var3) {
         throw new MarshallingException("Unable to create Document to place marshalled elements in", var3);
      }
   }

   @Nonnull
   public Element marshall(@Nonnull XMLObject xmlObject, @Nonnull Document document) throws MarshallingException {
      this.log.trace("Starting to marshall {}", xmlObject.getElementQName());
      if (document == null) {
         throw new MarshallingException("Given document may not be null");
      } else {
         this.log.trace("Checking if {} contains a cached DOM representation", xmlObject.getElementQName());
         Element domElement = xmlObject.getDOM();
         if (domElement != null) {
            this.prepareForAdoption(xmlObject);
            if (domElement.getOwnerDocument() != document) {
               this.log.trace("Adopting DOM of XMLObject into given Document");
               ElementSupport.adoptElement(document, domElement);
            }

            this.log.trace("Setting DOM of XMLObject as document element of given Document");
            this.setDocumentElement(document, domElement);
            return domElement;
         } else {
            this.log.trace("{} does not contain a cached DOM representation. Creating Element to marshall into.", xmlObject.getElementQName());
            domElement = ElementSupport.constructElement(document, xmlObject.getElementQName());
            this.log.trace("Setting created element as document root");
            this.setDocumentElement(document, domElement);
            domElement = this.marshallInto(xmlObject, domElement);
            this.log.trace("Setting created element to DOM cache for XMLObject {}", xmlObject.getElementQName());
            xmlObject.setDOM(domElement);
            xmlObject.releaseParentDOM(true);
            return domElement;
         }
      }
   }

   @Nonnull
   public Element marshall(@Nonnull XMLObject xmlObject, @Nonnull Element parentElement) throws MarshallingException {
      this.log.trace("Starting to marshall {} as child of {}", xmlObject.getElementQName(), QNameSupport.getNodeQName(parentElement));
      if (parentElement == null) {
         throw new MarshallingException("Given parent element is null");
      } else {
         this.log.trace("Checking if {} contains a cached DOM representation", xmlObject.getElementQName());
         Element domElement = xmlObject.getDOM();
         if (domElement != null) {
            this.log.trace("{} contains a cached DOM representation", xmlObject.getElementQName());
            this.prepareForAdoption(xmlObject);
            this.log.trace("Appending DOM of XMLObject {} as child of parent element {}", xmlObject.getElementQName(), QNameSupport.getNodeQName(parentElement));
            ElementSupport.appendChildElement(parentElement, domElement);
            return domElement;
         } else {
            this.log.trace("{} does not contain a cached DOM representation. Creating Element to marshall into.", xmlObject.getElementQName());
            Document owningDocument = parentElement.getOwnerDocument();
            domElement = ElementSupport.constructElement(owningDocument, xmlObject.getElementQName());
            this.log.trace("Appending newly created element to given parent element");
            ElementSupport.appendChildElement(parentElement, domElement);
            domElement = this.marshallInto(xmlObject, domElement);
            this.log.trace("Setting created element to DOM cache for XMLObject {}", xmlObject.getElementQName());
            xmlObject.setDOM(domElement);
            xmlObject.releaseParentDOM(true);
            return domElement;
         }
      }
   }

   protected void setDocumentElement(@Nonnull Document document, @Nonnull Element element) {
      Element documentRoot = document.getDocumentElement();
      if (documentRoot != null) {
         document.replaceChild(element, documentRoot);
      } else {
         document.appendChild(element);
      }

   }

   @Nonnull
   protected Element marshallInto(@Nonnull XMLObject xmlObject, @Nonnull Element targetElement) throws MarshallingException {
      this.log.trace("Setting namespace prefix for {} for XMLObject {}", xmlObject.getElementQName().getPrefix(), xmlObject.getElementQName());
      this.marshallNamespacePrefix(xmlObject, targetElement);
      this.marshallSchemaInstanceAttributes(xmlObject, targetElement);
      this.marshallNamespaces(xmlObject, targetElement);
      this.marshallAttributes(xmlObject, targetElement);
      this.marshallChildElements(xmlObject, targetElement);
      this.marshallElementContent(xmlObject, targetElement);
      return targetElement;
   }

   protected void marshallNamespacePrefix(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) {
      String prefix = StringSupport.trimOrNull(xmlObject.getElementQName().getPrefix());
      if (prefix != null) {
         domElement.setPrefix(prefix);
      }

   }

   protected void marshallChildElements(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
      this.log.trace("Marshalling child elements for XMLObject {}", xmlObject.getElementQName());
      List childXMLObjects = xmlObject.getOrderedChildren();
      if (childXMLObjects != null && childXMLObjects.size() > 0) {
         Iterator var4 = childXMLObjects.iterator();

         while(var4.hasNext()) {
            XMLObject childXMLObject = (XMLObject)var4.next();
            if (childXMLObject != null) {
               this.log.trace("Getting marshaller for child XMLObject {}", childXMLObject.getElementQName());
               Marshaller marshaller = this.marshallerFactory.getMarshaller(childXMLObject);
               if (marshaller == null) {
                  marshaller = this.marshallerFactory.getMarshaller(XMLObjectProviderRegistrySupport.getDefaultProviderQName());
                  if (marshaller == null) {
                     String errorMsg = "No marshaller available for " + childXMLObject.getElementQName() + ", child of " + xmlObject.getElementQName();
                     this.log.error(errorMsg);
                     throw new MarshallingException(errorMsg);
                  }

                  this.log.trace("No marshaller was registered for {}, child of {}. Using default marshaller", childXMLObject.getElementQName(), xmlObject.getElementQName());
               }

               this.log.trace("Marshalling {} and adding it to DOM", childXMLObject.getElementQName());
               marshaller.marshall(childXMLObject, domElement);
            }
         }
      } else {
         this.log.trace("No child elements to marshall for XMLObject {}", xmlObject.getElementQName());
      }

   }

   protected void marshallNamespaces(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) {
      this.log.trace("Marshalling namespace attributes for XMLObject {}", xmlObject.getElementQName());
      Set namespaces = xmlObject.getNamespaces();
      Iterator var4 = namespaces.iterator();

      while(true) {
         while(var4.hasNext()) {
            Namespace namespace = (Namespace)var4.next();
            this.log.trace("Candidate namespace from getNamespaces(): {}", namespace.toString());
            String declared;
            if (!xmlObject.getNamespaceManager().getNamespaceDeclarations().contains(namespace)) {
               this.log.trace("NamespaceManager getNamespaceDeclarations() did NOT contain namespace: {}", namespace.toString());
               if (Objects.equals(namespace.getNamespacePrefix(), "xml") || Objects.equals(namespace.getNamespaceURI(), "http://www.w3.org/XML/1998/namespace")) {
                  continue;
               }

               declared = NamespaceSupport.lookupNamespaceURI(domElement, (Element)null, namespace.getNamespacePrefix());
               this.log.trace("Lookup of prefix '{}' returned '{}'", namespace.getNamespacePrefix(), declared);
               if (declared != null && namespace.getNamespaceURI().equals(declared)) {
                  this.log.trace("Namespace {} has already been declared on an ancestor of {} no need to add it here", namespace, xmlObject.getElementQName());
                  continue;
               }
            }

            this.log.trace("Adding namespace declaration {} to {}", namespace, xmlObject.getElementQName());
            declared = StringSupport.trimOrNull(namespace.getNamespaceURI());
            String nsPrefix = StringSupport.trimOrNull(namespace.getNamespacePrefix());
            NamespaceSupport.appendNamespaceDeclaration(domElement, declared, nsPrefix);
         }

         return;
      }
   }

   protected void marshallSchemaInstanceAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
      if (!Strings.isNullOrEmpty(xmlObject.getSchemaLocation())) {
         this.log.trace("Setting xsi:schemaLocation for XMLObject {} to {}", xmlObject.getElementQName(), xmlObject.getSchemaLocation());
         domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", xmlObject.getSchemaLocation());
      }

      if (!Strings.isNullOrEmpty(xmlObject.getNoNamespaceSchemaLocation())) {
         this.log.trace("Setting xsi:noNamespaceSchemaLocation for XMLObject {} to {}", xmlObject.getElementQName(), xmlObject.getNoNamespaceSchemaLocation());
         domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:noNamespaceSchemaLocation", xmlObject.getNoNamespaceSchemaLocation());
      }

      if (xmlObject.isNilXSBoolean() != null && xmlObject.isNil()) {
         this.log.trace("Setting xsi:nil for XMLObject {} to true", xmlObject.getElementQName());
         domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:nil", xmlObject.isNilXSBoolean().toString());
      }

      QName type = xmlObject.getSchemaType();
      if (type != null) {
         this.log.trace("Setting xsi:type attribute with for XMLObject {}", xmlObject.getElementQName());
         String typeLocalName = StringSupport.trimOrNull(type.getLocalPart());
         String typePrefix = StringSupport.trimOrNull(type.getPrefix());
         if (typeLocalName == null) {
            throw new MarshallingException("The type QName on XMLObject " + xmlObject.getElementQName() + " may not have a null local name");
         } else if (type.getNamespaceURI() == null) {
            throw new MarshallingException("The type URI QName on XMLObject " + xmlObject.getElementQName() + " may not have a null namespace URI");
         } else {
            String attributeValue;
            if (typePrefix == null) {
               attributeValue = typeLocalName;
            } else {
               attributeValue = typePrefix + ":" + typeLocalName;
            }

            domElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", attributeValue);
         }
      }
   }

   protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
   }

   protected void marshallElementContent(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
   }

   private void prepareForAdoption(@Nonnull XMLObject domCachingObject) throws MarshallingException {
      if (domCachingObject.getParent() != null) {
         this.log.trace("Rooting all visible namespaces of XMLObject {} before adding it to new parent Element", domCachingObject.getElementQName());

         try {
            NamespaceSupport.rootNamespaces(domCachingObject.getDOM());
         } catch (DOMException var4) {
            String errorMsg = "Unable to root namespaces of cached DOM element, " + domCachingObject.getElementQName();
            this.log.error(errorMsg, var4);
            throw new MarshallingException(errorMsg, var4);
         }

         this.log.trace("Release DOM of XMLObject parent");
         domCachingObject.releaseParentDOM(true);
      }

   }

   protected void marshallUnknownAttributes(@Nonnull AttributeExtensibleXMLObject xmlObject, @Nonnull Element domElement) {
      XMLObjectSupport.marshallAttributeMap(xmlObject.getUnknownAttributes(), domElement);
   }
}
