package org.apache.jcp.xml.dsig.internal.dom;

import java.security.spec.AlgorithmParameterSpec;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
import javax.xml.crypto.dsig.spec.XPathType;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DOMUtils {
   private DOMUtils() {
   }

   public static Document getOwnerDocument(Node node) {
      return node.getNodeType() == 9 ? (Document)node : node.getOwnerDocument();
   }

   public static String getQNameString(String prefix, String localName) {
      String qName = prefix != null && prefix.length() != 0 ? prefix + ":" + localName : localName;
      return qName;
   }

   public static Element createElement(Document doc, String tag, String nsURI, String prefix) {
      String qName = prefix != null && prefix.length() != 0 ? prefix + ":" + tag : tag;
      return doc.createElementNS(nsURI, qName);
   }

   public static void setAttribute(Element elem, String name, String value) {
      if (value != null) {
         elem.setAttributeNS((String)null, name, value);
      }
   }

   public static void setAttributeID(Element elem, String name, String value) {
      if (value != null) {
         elem.setAttributeNS((String)null, name, value);
         elem.setIdAttributeNS((String)null, name, true);
      }
   }

   public static Element getFirstChildElement(Node node) {
      Node child;
      for(child = node.getFirstChild(); child != null && child.getNodeType() != 1; child = child.getNextSibling()) {
      }

      return (Element)child;
   }

   /** @deprecated */
   @Deprecated
   public static Element getFirstChildElement(Node node, String localName) throws MarshalException {
      return verifyElement(getFirstChildElement(node), localName);
   }

   public static Element getFirstChildElement(Node node, String localName, String namespaceURI) throws MarshalException {
      return verifyElement(getFirstChildElement(node), localName, namespaceURI);
   }

   private static Element verifyElement(Element elem, String localName) throws MarshalException {
      if (elem == null) {
         throw new MarshalException("Missing " + localName + " element");
      } else {
         String name = elem.getLocalName();
         if (!name.equals(localName)) {
            throw new MarshalException("Invalid element name: " + name + ", expected " + localName);
         } else {
            return elem;
         }
      }
   }

   private static Element verifyElement(Element elem, String localName, String namespaceURI) throws MarshalException {
      if (elem == null) {
         throw new MarshalException("Missing " + localName + " element");
      } else {
         String name = elem.getLocalName();
         String namespace = elem.getNamespaceURI();
         if (name.equals(localName) && (namespace != null || namespaceURI == null) && (namespace == null || namespace.equals(namespaceURI))) {
            return elem;
         } else {
            throw new MarshalException("Invalid element name: " + namespace + ":" + name + ", expected " + namespaceURI + ":" + localName);
         }
      }
   }

   public static Element getLastChildElement(Node node) {
      Node child;
      for(child = node.getLastChild(); child != null && child.getNodeType() != 1; child = child.getPreviousSibling()) {
      }

      return (Element)child;
   }

   public static Element getNextSiblingElement(Node node) {
      Node sibling;
      for(sibling = node.getNextSibling(); sibling != null && sibling.getNodeType() != 1; sibling = sibling.getNextSibling()) {
      }

      return (Element)sibling;
   }

   /** @deprecated */
   @Deprecated
   public static Element getNextSiblingElement(Node node, String localName) throws MarshalException {
      return verifyElement(getNextSiblingElement(node), localName);
   }

   public static Element getNextSiblingElement(Node node, String localName, String namespaceURI) throws MarshalException {
      return verifyElement(getNextSiblingElement(node), localName, namespaceURI);
   }

   public static String getAttributeValue(Element elem, String name) {
      Attr attr = elem.getAttributeNodeNS((String)null, name);
      return attr == null ? null : attr.getValue();
   }

   public static String getIdAttributeValue(Element elem, String name) {
      Attr attr = elem.getAttributeNodeNS((String)null, name);
      if (attr != null && !attr.isId()) {
         elem.setIdAttributeNode(attr, true);
      }

      return attr == null ? null : attr.getValue();
   }

   public static Set nodeSet(NodeList nl) {
      return new NodeSet(nl);
   }

   public static String getNSPrefix(XMLCryptoContext context, String nsURI) {
      return context != null ? context.getNamespacePrefix(nsURI, context.getDefaultNamespacePrefix()) : null;
   }

   public static String getSignaturePrefix(XMLCryptoContext context) {
      return getNSPrefix(context, "http://www.w3.org/2000/09/xmldsig#");
   }

   public static void removeAllChildren(Node node) {
      Node firstChild = node.getFirstChild();

      while(firstChild != null) {
         Node nodeToRemove = firstChild;
         firstChild = firstChild.getNextSibling();
         node.removeChild(nodeToRemove);
      }

   }

   public static boolean nodesEqual(Node thisNode, Node otherNode) {
      if (thisNode == otherNode) {
         return true;
      } else {
         return thisNode.getNodeType() == otherNode.getNodeType();
      }
   }

   public static void appendChild(Node parent, Node child) {
      Document ownerDoc = getOwnerDocument(parent);
      if (child.getOwnerDocument() != ownerDoc) {
         parent.appendChild(ownerDoc.importNode(child, true));
      } else {
         parent.appendChild(child);
      }

   }

   public static boolean paramsEqual(AlgorithmParameterSpec spec1, AlgorithmParameterSpec spec2) {
      if (spec1 == spec2) {
         return true;
      } else if (spec1 instanceof XPathFilter2ParameterSpec && spec2 instanceof XPathFilter2ParameterSpec) {
         return paramsEqual((XPathFilter2ParameterSpec)spec1, (XPathFilter2ParameterSpec)spec2);
      } else if (spec1 instanceof ExcC14NParameterSpec && spec2 instanceof ExcC14NParameterSpec) {
         return paramsEqual((ExcC14NParameterSpec)spec1, (ExcC14NParameterSpec)spec2);
      } else if (spec1 instanceof XPathFilterParameterSpec && spec2 instanceof XPathFilterParameterSpec) {
         return paramsEqual((XPathFilterParameterSpec)spec1, (XPathFilterParameterSpec)spec2);
      } else {
         return spec1 instanceof XSLTTransformParameterSpec && spec2 instanceof XSLTTransformParameterSpec ? paramsEqual((XSLTTransformParameterSpec)spec1, (XSLTTransformParameterSpec)spec2) : false;
      }
   }

   private static boolean paramsEqual(XPathFilter2ParameterSpec spec1, XPathFilter2ParameterSpec spec2) {
      List types = spec1.getXPathList();
      List otypes = spec2.getXPathList();
      int size = types.size();
      if (size != otypes.size()) {
         return false;
      } else {
         for(int i = 0; i < size; ++i) {
            XPathType type = (XPathType)types.get(i);
            XPathType otype = (XPathType)otypes.get(i);
            if (!type.getExpression().equals(otype.getExpression()) || !type.getNamespaceMap().equals(otype.getNamespaceMap()) || type.getFilter() != otype.getFilter()) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean paramsEqual(ExcC14NParameterSpec spec1, ExcC14NParameterSpec spec2) {
      return spec1.getPrefixList().equals(spec2.getPrefixList());
   }

   private static boolean paramsEqual(XPathFilterParameterSpec spec1, XPathFilterParameterSpec spec2) {
      return spec1.getXPath().equals(spec2.getXPath()) && spec1.getNamespaceMap().equals(spec2.getNamespaceMap());
   }

   private static boolean paramsEqual(XSLTTransformParameterSpec spec1, XSLTTransformParameterSpec spec2) {
      XMLStructure ostylesheet = spec2.getStylesheet();
      if (!(ostylesheet instanceof javax.xml.crypto.dom.DOMStructure)) {
         return false;
      } else {
         Node ostylesheetElem = ((javax.xml.crypto.dom.DOMStructure)ostylesheet).getNode();
         XMLStructure stylesheet = spec1.getStylesheet();
         Node stylesheetElem = ((javax.xml.crypto.dom.DOMStructure)stylesheet).getNode();
         return nodesEqual(stylesheetElem, ostylesheetElem);
      }
   }

   public static boolean isNamespace(Node node) {
      short nodeType = node.getNodeType();
      if (nodeType == 2) {
         String namespaceURI = node.getNamespaceURI();
         return "http://www.w3.org/2000/xmlns/".equals(namespaceURI);
      } else {
         return false;
      }
   }

   static class NodeSet extends AbstractSet {
      private NodeList nl;

      public NodeSet(NodeList nl) {
         this.nl = nl;
      }

      public int size() {
         return this.nl.getLength();
      }

      public Iterator iterator() {
         return new Iterator() {
            private int index;

            public void remove() {
               throw new UnsupportedOperationException();
            }

            public Node next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return NodeSet.this.nl.item(this.index++);
               }
            }

            public boolean hasNext() {
               return this.index < NodeSet.this.nl.getLength();
            }
         };
      }
   }
}
