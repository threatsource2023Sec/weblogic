package org.opensaml.core.xml.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.xml.Namespace;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLRuntimeException;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public final class XMLObjectSupport {
   private XMLObjectSupport() {
   }

   public static XMLObject cloneXMLObject(XMLObject originalXMLObject) throws MarshallingException, UnmarshallingException {
      return cloneXMLObject(originalXMLObject, XMLObjectSupport.CloneOutputOption.DropDOM);
   }

   /** @deprecated */
   @Deprecated
   @Nullable
   public static XMLObject cloneXMLObject(@Nullable XMLObject originalXMLObject, boolean rootInNewDocument) throws MarshallingException, UnmarshallingException {
      return rootInNewDocument ? cloneXMLObject(originalXMLObject, XMLObjectSupport.CloneOutputOption.RootDOMInNewDocument) : cloneXMLObject(originalXMLObject, XMLObjectSupport.CloneOutputOption.UnrootedDOM);
   }

   @Nullable
   public static XMLObject cloneXMLObject(@Nullable XMLObject originalXMLObject, @Nonnull CloneOutputOption cloneOutputOption) throws MarshallingException, UnmarshallingException {
      if (originalXMLObject == null) {
         return null;
      } else {
         Marshaller marshaller = getMarshaller(originalXMLObject);
         if (marshaller == null) {
            throw new MarshallingException("Unable to obtain Marshaller for XMLObject: " + originalXMLObject.getElementQName());
         } else {
            Element origElement = marshaller.marshall(originalXMLObject);
            Element clonedElement = null;
            switch (cloneOutputOption) {
               case RootDOMInNewDocument:
                  try {
                     Document newDocument = XMLObjectProviderRegistrySupport.getParserPool().newDocument();
                     clonedElement = (Element)newDocument.importNode(origElement, true);
                     newDocument.appendChild(clonedElement);
                     break;
                  } catch (XMLParserException var7) {
                     throw new XMLRuntimeException("Error obtaining new Document from parser pool", var7);
                  }
               case UnrootedDOM:
               case DropDOM:
                  clonedElement = (Element)origElement.cloneNode(true);
                  break;
               default:
                  throw new XMLRuntimeException("Saw unsupported value for CloneOutputOption enum: " + cloneOutputOption);
            }

            Unmarshaller unmarshaller = getUnmarshaller(clonedElement);
            if (unmarshaller == null) {
               throw new UnmarshallingException("Unable to obtain Unmarshaller for element: " + QNameSupport.getNodeQName(clonedElement));
            } else {
               XMLObject clonedXMLObject = unmarshaller.unmarshall(clonedElement);
               if (XMLObjectSupport.CloneOutputOption.DropDOM.equals(cloneOutputOption)) {
                  clonedXMLObject.releaseDOM();
                  clonedXMLObject.releaseChildrenDOM(true);
               }

               return clonedXMLObject;
            }
         }
      }
   }

   public static XMLObject unmarshallFromInputStream(ParserPool parserPool, InputStream inputStream) throws XMLParserException, UnmarshallingException {
      Logger log = getLogger();
      log.debug("Parsing InputStream into DOM document");

      try {
         Document messageDoc = parserPool.parse(inputStream);
         Element messageElem = messageDoc.getDocumentElement();
         if (log.isTraceEnabled()) {
            log.trace("Resultant DOM message was:");
            log.trace(SerializeSupport.nodeToString(messageElem));
         }

         log.debug("Unmarshalling DOM parsed from InputStream");
         Unmarshaller unmarshaller = getUnmarshaller(messageElem);
         if (unmarshaller == null) {
            log.error("Unable to unmarshall InputStream, no unmarshaller registered for element " + QNameSupport.getNodeQName(messageElem));
            throw new UnmarshallingException("Unable to unmarshall InputStream, no unmarshaller registered for element " + QNameSupport.getNodeQName(messageElem));
         } else {
            XMLObject message = unmarshaller.unmarshall(messageElem);
            log.debug("InputStream succesfully unmarshalled");
            return message;
         }
      } catch (RuntimeException var7) {
         throw new UnmarshallingException("Fatal error unmarshalling XMLObject", var7);
      }
   }

   public static XMLObject unmarshallFromReader(ParserPool parserPool, Reader reader) throws XMLParserException, UnmarshallingException {
      Logger log = getLogger();
      log.debug("Parsing Reader into DOM document");

      try {
         Document messageDoc = parserPool.parse(reader);
         Element messageElem = messageDoc.getDocumentElement();
         if (log.isTraceEnabled()) {
            log.trace("Resultant DOM message was:");
            log.trace(SerializeSupport.nodeToString(messageElem));
         }

         log.debug("Unmarshalling DOM parsed from Reader");
         Unmarshaller unmarshaller = getUnmarshaller(messageElem);
         if (unmarshaller == null) {
            log.error("Unable to unmarshall Reader, no unmarshaller registered for element " + QNameSupport.getNodeQName(messageElem));
            throw new UnmarshallingException("Unable to unmarshall Reader, no unmarshaller registered for element " + QNameSupport.getNodeQName(messageElem));
         } else {
            XMLObject message = unmarshaller.unmarshall(messageElem);
            log.debug("Reader succesfully unmarshalled");
            return message;
         }
      } catch (RuntimeException var7) {
         throw new UnmarshallingException("Fatal error unmarshalling XMLObject", var7);
      }
   }

   @Nonnull
   public static Element marshall(@Nonnull XMLObject xmlObject) throws MarshallingException {
      Logger log = getLogger();
      log.debug("Marshalling XMLObject");
      if (xmlObject.getDOM() != null) {
         log.debug("XMLObject already had cached DOM, returning that element");
         return xmlObject.getDOM();
      } else {
         Marshaller marshaller = getMarshaller(xmlObject);
         if (marshaller == null) {
            log.error("Unable to marshall XMLObject, no marshaller registered for object: " + xmlObject.getElementQName());
            throw new MarshallingException("Unable to marshall XMLObject, no marshaller registered for object: " + xmlObject.getElementQName());
         } else {
            Element messageElem = marshaller.marshall(xmlObject);
            if (log.isTraceEnabled()) {
               log.trace("Marshalled XMLObject into DOM:");
               log.trace(SerializeSupport.nodeToString(messageElem));
            }

            return messageElem;
         }
      }
   }

   public static void marshallToOutputStream(XMLObject xmlObject, OutputStream outputStream) throws MarshallingException {
      Element element = marshall(xmlObject);
      SerializeSupport.writeNode(element, outputStream);
   }

   public static String lookupNamespaceURI(XMLObject xmlObject, String prefix) {
      for(XMLObject current = xmlObject; current != null; current = current.getParent()) {
         Iterator var3 = current.getNamespaces().iterator();

         while(var3.hasNext()) {
            Namespace ns = (Namespace)var3.next();
            if (Objects.equals(ns.getNamespacePrefix(), prefix)) {
               return ns.getNamespaceURI();
            }
         }
      }

      return null;
   }

   public static String lookupNamespacePrefix(XMLObject xmlObject, String namespaceURI) {
      for(XMLObject current = xmlObject; current != null; current = current.getParent()) {
         Iterator var3 = current.getNamespaces().iterator();

         while(var3.hasNext()) {
            Namespace ns = (Namespace)var3.next();
            if (Objects.equals(ns.getNamespaceURI(), namespaceURI)) {
               return ns.getNamespacePrefix();
            }
         }
      }

      return null;
   }

   private static Logger getLogger() {
      return LoggerFactory.getLogger(XMLObjectSupport.class);
   }

   public static void marshallAttribute(QName attributeName, List attributeValues, Element domElement, boolean isIDAttribute) {
      marshallAttribute(attributeName, StringSupport.listToStringValue(attributeValues, " "), domElement, isIDAttribute);
   }

   public static void marshallAttribute(QName attributeName, String attributeValue, Element domElement, boolean isIDAttribute) {
      Document document = domElement.getOwnerDocument();
      Attr attribute = AttributeSupport.constructAttribute(document, attributeName);
      attribute.setValue(attributeValue);
      domElement.setAttributeNodeNS(attribute);
      if (isIDAttribute) {
         domElement.setIdAttributeNode(attribute, true);
      }

   }

   public static void marshallAttributeMap(AttributeMap attributeMap, Element domElement) {
      Document document = domElement.getOwnerDocument();
      Attr attribute = null;
      Iterator var4 = attributeMap.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         do {
            if (!var4.hasNext()) {
               return;
            }

            entry = (Map.Entry)var4.next();
            attribute = AttributeSupport.constructAttribute(document, (QName)entry.getKey());
            attribute.setValue((String)entry.getValue());
            domElement.setAttributeNodeNS(attribute);
         } while(!XMLObjectProviderRegistrySupport.isIDAttribute((QName)entry.getKey()) && !attributeMap.isIDAttribute((QName)entry.getKey()));

         domElement.setIdAttributeNode(attribute, true);
      }
   }

   public static void unmarshallToAttributeMap(AttributeMap attributeMap, Attr attribute) {
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      attributeMap.put(attribQName, attribute.getValue());
      if (attribute.isId() || XMLObjectProviderRegistrySupport.isIDAttribute(attribQName)) {
         attributeMap.registerID(attribQName);
      }

   }

   public static XMLObject buildXMLObject(QName elementName) {
      XMLObjectBuilder builder = getProviderRegistry().getBuilderFactory().getBuilderOrThrow(elementName);
      return builder.buildObject(elementName);
   }

   public static XMLObject buildXMLObject(QName elementName, QName typeName) {
      XMLObjectBuilder builder = getProviderRegistry().getBuilderFactory().getBuilderOrThrow(elementName);
      return builder.buildObject(elementName, typeName);
   }

   public static XMLObjectBuilder getBuilder(QName typeOrName) {
      return getProviderRegistry().getBuilderFactory().getBuilder(typeOrName);
   }

   public static Marshaller getMarshaller(QName typeOrName) {
      return getProviderRegistry().getMarshallerFactory().getMarshaller(typeOrName);
   }

   public static Marshaller getMarshaller(XMLObject xmlObject) {
      return getProviderRegistry().getMarshallerFactory().getMarshaller(xmlObject);
   }

   public static Unmarshaller getUnmarshaller(QName typeOrName) {
      return getProviderRegistry().getUnmarshallerFactory().getUnmarshaller(typeOrName);
   }

   public static Unmarshaller getUnmarshaller(Element element) {
      return getProviderRegistry().getUnmarshallerFactory().getUnmarshaller(element);
   }

   private static XMLObjectProviderRegistry getProviderRegistry() {
      XMLObjectProviderRegistry registry = (XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class);
      if (registry == null) {
         throw new XMLRuntimeException("XMLObjectProviderRegistry was not available from the ConfigurationService");
      } else {
         return registry;
      }
   }

   public static enum CloneOutputOption {
      DropDOM,
      RootDOMInNewDocument,
      UnrootedDOM;
   }
}
