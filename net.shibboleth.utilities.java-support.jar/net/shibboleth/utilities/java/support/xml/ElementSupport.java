package net.shibboleth.utilities.java.support.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public final class ElementSupport {
   private ElementSupport() {
   }

   public static void adoptElement(@Nonnull Document adopter, @Nonnull Element adoptee) {
      Constraint.isNotNull(adoptee, "Adoptee Element may not be null");
      Constraint.isNotNull(adopter, "Adopter Element may not be null");
      if (!adoptee.getOwnerDocument().isSameNode(adopter) && adopter.adoptNode(adoptee) == null) {
         throw new RuntimeException("DOM Element node adoption failed. This is most likely caused by the Element and Document being produced by different DOM implementations.");
      }
   }

   public static void appendChildElement(@Nonnull Element parentElement, @Nullable Element childElement) {
      if (childElement != null) {
         Constraint.isNotNull(parentElement, "Parent Element may not be null");
         Document parentDocument = parentElement.getOwnerDocument();
         if (!parentDocument.equals(childElement.getOwnerDocument())) {
            adoptElement(parentDocument, childElement);
         }

         parentElement.appendChild(childElement);
      }
   }

   public static void appendTextContent(@Nonnull Element element, @Nullable String textContent) {
      if (textContent != null) {
         Constraint.isNotNull(element, "Element may not be null");
         Text textNode = element.getOwnerDocument().createTextNode(textContent);
         element.appendChild(textNode);
      }
   }

   public static Element constructElement(@Nonnull Document document, @Nonnull QName elementName) {
      Constraint.isNotNull(elementName, "Element name can not be null");
      return constructElement(document, elementName.getNamespaceURI(), elementName.getLocalPart(), elementName.getPrefix());
   }

   public static Element constructElement(@Nonnull Document document, @Nullable String namespaceURI, @Nonnull String localName, @Nullable String prefix) {
      Constraint.isNotNull(document, "Document may not be null");
      String trimmedLocalName = (String)Constraint.isNotNull(StringSupport.trimOrNull(localName), "Element local name may not be null or empty");
      String trimmedPrefix = StringSupport.trimOrNull(prefix);
      String qualifiedName;
      if (trimmedPrefix != null) {
         qualifiedName = trimmedPrefix + ":" + StringSupport.trimOrNull(trimmedLocalName);
      } else {
         qualifiedName = StringSupport.trimOrNull(trimmedLocalName);
      }

      return document.createElementNS(StringSupport.trimOrNull(namespaceURI), qualifiedName);
   }

   @Nonnull
   public static List getChildElements(@Nullable Node root) {
      if (root == null) {
         return Collections.emptyList();
      } else {
         ArrayList children = new ArrayList();

         for(Element childNode = getFirstChildElement(root); childNode != null; childNode = getNextSiblingElement(childNode)) {
            children.add(childNode);
         }

         return children;
      }
   }

   @Nonnull
   public static List getChildElements(@Nullable Node root, @Nullable QName name) {
      return root != null && name != null ? getChildElementsByTagNameNS(root, name.getNamespaceURI(), name.getLocalPart()) : Collections.emptyList();
   }

   @Nullable
   public static Element getFirstChildElement(@Nullable Node root, @Nullable QName name) {
      List elements = getChildElements(root, name);
      return elements.size() > 0 ? (Element)elements.get(0) : null;
   }

   @Nonnull
   public static List getChildElementsByTagName(@Nullable Node root, @Nullable String localName) {
      if (root == null) {
         return Collections.emptyList();
      } else {
         ArrayList children = new ArrayList();

         for(Element childNode = getFirstChildElement(root); childNode != null; childNode = getNextSiblingElement(childNode)) {
            if (Objects.equals(childNode.getLocalName(), localName)) {
               children.add(childNode);
            }
         }

         return children;
      }
   }

   @Nonnull
   public static List getChildElementsByTagNameNS(@Nullable Node root, @Nullable String namespaceURI, @Nullable String localName) {
      if (root != null && localName != null) {
         ArrayList children = new ArrayList();

         for(Element childNode = getFirstChildElement(root); childNode != null; childNode = getNextSiblingElement(childNode)) {
            if (isElementNamed(childNode, namespaceURI, localName)) {
               children.add(childNode);
            }
         }

         return children;
      } else {
         return Collections.emptyList();
      }
   }

   @Nullable
   public static Element getElementAncestor(@Nullable Node currentNode) {
      if (currentNode == null) {
         return null;
      } else {
         Node parent = currentNode.getParentNode();
         if (parent != null) {
            return parent.getNodeType() == 1 ? (Element)parent : getElementAncestor(parent);
         } else {
            return null;
         }
      }
   }

   @Nonnull
   public static String getElementContentAsString(@Nullable Element element) {
      if (element == null) {
         return "";
      } else {
         StringBuilder builder = new StringBuilder();

         for(Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 3 || node.getNodeType() == 4) {
               builder.append(((Text)node).getNodeValue());
            }
         }

         return builder.toString();
      }
   }

   @Nonnull
   public static List getElementContentAsList(@Nullable Element element) {
      return element == null ? Collections.emptyList() : StringSupport.stringToList(getElementContentAsString(element), " \n\r\t");
   }

   @Nullable
   public static QName getElementContentAsQName(@Nullable Element element) {
      if (element == null) {
         return null;
      } else {
         String elementContent = StringSupport.trimOrNull(getElementContentAsString(element));
         if (elementContent == null) {
            return null;
         } else {
            QName result = null;
            String[] valueComponents = elementContent.split(":");
            if (valueComponents.length == 1) {
               result = QNameSupport.constructQName(element.lookupNamespaceURI((String)null), valueComponents[0], (String)null);
            } else if (valueComponents.length == 2) {
               result = QNameSupport.constructQName(element.lookupNamespaceURI(valueComponents[0]), valueComponents[1], valueComponents[0]);
            }

            return result;
         }
      }
   }

   @Nullable
   public static Element getFirstChildElement(@Nullable Node n) {
      if (n == null) {
         return null;
      } else {
         Node child;
         for(child = n.getFirstChild(); child != null && child.getNodeType() != 1; child = child.getNextSibling()) {
         }

         return child != null ? (Element)child : null;
      }
   }

   @Nonnull
   public static Map getIndexedChildElements(@Nullable Element root) {
      if (root == null) {
         return Collections.emptyMap();
      } else {
         Map children = new HashMap();

         for(Element e = getFirstChildElement(root); e != null; e = getNextSiblingElement(e)) {
            QName qname = QNameSupport.getNodeQName(e);
            List elements = (List)children.get(qname);
            if (elements == null) {
               elements = new ArrayList();
               children.put(qname, elements);
            }

            ((List)elements).add(e);
         }

         return children;
      }
   }

   @Nullable
   public static Element getNextSiblingElement(@Nullable Node n) {
      if (n == null) {
         return null;
      } else {
         Node sib;
         for(sib = n.getNextSibling(); sib != null && sib.getNodeType() != 1; sib = sib.getNextSibling()) {
         }

         return sib != null ? (Element)sib : null;
      }
   }

   public static boolean isElementNamed(@Nullable Element e, @Nullable QName name) {
      return name == null ? false : isElementNamed(e, name.getNamespaceURI(), name.getLocalPart());
   }

   public static boolean isElementNamed(@Nullable Element e, @Nullable String ns, @Nullable String localName) {
      return e != null && Objects.equals(ns, e.getNamespaceURI()) && Objects.equals(localName, e.getLocalName());
   }

   public static void setDocumentElement(@Nonnull Document document, @Nonnull Element element) {
      Constraint.isNotNull(document, "Document may not be null");
      Constraint.isNotNull(element, "Element may not be null");
      Element rootElement = document.getDocumentElement();
      if (rootElement == null) {
         adoptElement(document, element);
         document.appendChild(element);
      } else if (!rootElement.isSameNode(element)) {
         adoptElement(document, element);
         document.replaceChild(element, rootElement);
      }

   }
}
