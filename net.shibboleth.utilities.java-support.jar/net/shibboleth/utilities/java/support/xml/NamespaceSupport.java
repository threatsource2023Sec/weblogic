package net.shibboleth.utilities.java.support.xml;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class NamespaceSupport {
   private NamespaceSupport() {
   }

   public static void appendNamespaceDeclaration(@Nonnull Element element, @Nonnull String namespaceURI, @Nullable String prefix) {
      Constraint.isNotNull(element, "Element may not be null");
      String nsURI = StringSupport.trimOrNull(namespaceURI);
      String nsPrefix = StringSupport.trimOrNull(prefix);
      Constraint.isNotNull(nsURI, "namespace may not be null or empty");
      String attributeName;
      if (nsPrefix == null) {
         if (null == element.getPrefix() && !namespaceURI.equals(element.getNamespaceURI())) {
            throw new DOMException((short)15, "Cannot replace an element's default namespace");
         }

         attributeName = "xmlns";
      } else {
         if (nsPrefix.equals(element.getPrefix()) && !namespaceURI.equals(element.getNamespaceURI())) {
            throw new DOMException((short)15, "Cannot replace an element's default namespace");
         }

         attributeName = "xmlns:" + nsPrefix;
      }

      element.setAttributeNS("http://www.w3.org/2000/xmlns/", attributeName, nsURI);
   }

   @Nullable
   public static String lookupNamespaceURI(@Nonnull Element startingElement, @Nullable Element stoppingElement, @Nonnull String prefix) {
      Constraint.isNotNull(startingElement, "Starting element may not be null");
      if (startingElement.hasAttributes()) {
         NamedNodeMap map = startingElement.getAttributes();
         int length = map.getLength();

         for(int i = 0; i < length; ++i) {
            Node attr = map.item(i);
            String value = attr.getNodeValue();
            if (Objects.equals(attr.getNamespaceURI(), "http://www.w3.org/2000/xmlns/")) {
               if (Objects.equals(attr.getLocalName(), "xmlns") && prefix == null) {
                  return value;
               }

               if (Objects.equals(attr.getPrefix(), "xmlns") && Objects.equals(attr.getLocalName(), prefix)) {
                  return value;
               }
            }
         }
      }

      if (startingElement != stoppingElement) {
         Element ancestor = ElementSupport.getElementAncestor(startingElement);
         if (ancestor != null) {
            return lookupNamespaceURI(ancestor, stoppingElement, prefix);
         }
      }

      return null;
   }

   @Nullable
   public static String lookupPrefix(@Nonnull Element startingElement, @Nullable Element stopingElement, @Nullable String namespaceURI) {
      Constraint.isNotNull(startingElement, "Starting element may not be null");
      if (null == namespaceURI) {
         return null;
      } else {
         if (startingElement.hasAttributes()) {
            NamedNodeMap map = startingElement.getAttributes();
            int length = map.getLength();

            for(int i = 0; i < length; ++i) {
               Node attr = map.item(i);
               if (Objects.equals(attr.getNamespaceURI(), "http://www.w3.org/2000/xmlns/") && Objects.equals(attr.getPrefix(), "xmlns") && Objects.equals(attr.getNodeValue(), namespaceURI)) {
                  String localName = attr.getLocalName();
                  String foundNamespace = startingElement.lookupNamespaceURI(localName);
                  if (Objects.equals(foundNamespace, namespaceURI)) {
                     return localName;
                  }
               }
            }
         }

         if (startingElement != stopingElement) {
            Element ancestor = ElementSupport.getElementAncestor(startingElement);
            if (ancestor != null) {
               return lookupPrefix(ancestor, stopingElement, namespaceURI);
            }
         }

         return null;
      }
   }

   public static void rootNamespaces(@Nullable Element domElement) {
      rootNamespaces(domElement, domElement);
   }

   private static void rootNamespaces(@Nullable Element domElement, @Nullable Element upperNamespaceSearchBound) {
      if (domElement != null) {
         String namespaceURI = null;
         String namespacePrefix = domElement.getPrefix();
         boolean nsDeclaredOnElement = false;
         if (namespacePrefix == null) {
            nsDeclaredOnElement = domElement.hasAttributeNS((String)null, "xmlns");
         } else {
            nsDeclaredOnElement = domElement.hasAttributeNS("http://www.w3.org/2000/xmlns/", namespacePrefix);
         }

         if (!nsDeclaredOnElement) {
            namespaceURI = lookupNamespaceURI(domElement, upperNamespaceSearchBound, namespacePrefix);
            if (namespaceURI == null) {
               namespaceURI = lookupNamespaceURI(upperNamespaceSearchBound, (Element)null, namespacePrefix);
               if (namespaceURI != null) {
                  appendNamespaceDeclaration(domElement, namespaceURI, namespacePrefix);
               } else if (namespacePrefix != null) {
                  throw new DOMException((short)14, "Unable to resolve namespace prefix " + namespacePrefix + " found on element " + QNameSupport.getNodeQName(domElement));
               }
            }
         }

         NamedNodeMap attributes = domElement.getAttributes();

         for(int i = 0; i < attributes.getLength(); ++i) {
            namespacePrefix = null;
            namespaceURI = null;
            Node attributeNode = attributes.item(i);
            if (attributeNode.getNodeType() == 2) {
               namespacePrefix = StringSupport.trimOrNull(attributeNode.getPrefix());
               if (namespacePrefix != null && !namespacePrefix.equals("xmlns") && !namespacePrefix.equals("xml")) {
                  namespaceURI = lookupNamespaceURI(domElement, upperNamespaceSearchBound, namespacePrefix);
                  if (namespaceURI == null) {
                     namespaceURI = lookupNamespaceURI(upperNamespaceSearchBound, (Element)null, namespacePrefix);
                     if (namespaceURI == null) {
                        throw new DOMException((short)14, "Unable to resolve namespace prefix " + namespacePrefix + " found on attribute " + QNameSupport.getNodeQName(attributeNode) + " found on element " + QNameSupport.getNodeQName(domElement));
                     }

                     appendNamespaceDeclaration(domElement, namespaceURI, namespacePrefix);
                  }
               }
            }
         }

         for(Element childNode = ElementSupport.getFirstChildElement(domElement); childNode != null; childNode = ElementSupport.getNextSiblingElement(childNode)) {
            rootNamespaces(childNode, upperNamespaceSearchBound);
         }

      }
   }
}
