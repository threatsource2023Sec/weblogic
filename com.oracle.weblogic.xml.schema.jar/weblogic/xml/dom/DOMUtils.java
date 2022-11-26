package weblogic.xml.dom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public final class DOMUtils {
   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";
   private static final boolean debug = false;

   public static List getValuesByTagName(Element parentElement, String tagName) throws DOMProcessingException {
      List elts = getElementsByTagName(parentElement, tagName);
      Iterator i = elts.iterator();
      List vals = new ArrayList(elts.size());

      while(i.hasNext()) {
         vals.add(getTextData((Element)i.next()));
      }

      return vals;
   }

   public static List getElementsByTagName(Element parentElement, String tagName) throws DOMProcessingException {
      List elts = getOptionalElementsByTagName(parentElement, tagName);
      if (elts.size() == 0) {
         throw new ChildCountException(1, parentElement.getNodeName(), tagName, 0);
      } else {
         return elts;
      }
   }

   public static String getOptionalValueByTagName(Element parentElement, String tagName) throws DOMProcessingException {
      Element elt = getOptionalElementByTagName(parentElement, tagName);
      return elt == null ? null : getTextData(elt);
   }

   public static String getValueByTagName(Element parentElement, String tagName) throws DOMProcessingException {
      Element elt = getElementByTagName(parentElement, tagName);
      return getTextData(elt);
   }

   public static String getValueByTagNameNS(Element parentElement, String ns, String tagName) throws DOMProcessingException {
      Element elt = getElementByTagNameNS(parentElement, ns, tagName);
      return getTextData(elt);
   }

   public static String getOptionalValueByTagNameNS(Element parentElement, String ns, String tagName) throws DOMProcessingException {
      Element elt = getOptionalElementByTagNameNS(parentElement, ns, tagName);
      return elt == null ? null : getTextData(elt);
   }

   public static Element getElementByTagName(Element parentElement, String tagName) throws DOMProcessingException {
      Element element = getOptionalElementByTagName(parentElement, tagName);
      if (element == null) {
         throw new ChildCountException(1, parentElement.getNodeName(), tagName, 0);
      } else {
         return element;
      }
   }

   public static Element getOptionalElementByTagName(Element parentElement, String tagName) throws DOMProcessingException {
      List subelts = getOptionalElementsByTagName(parentElement, tagName);
      int nsubelts = subelts.size();
      switch (nsubelts) {
         case 0:
            return null;
         case 1:
            return (Element)subelts.get(0);
         default:
            throw new ChildCountException(2, parentElement.getNodeName(), tagName, nsubelts);
      }
   }

   public static List getOptionalElementsByLocalName(Element parentElement, String localName) throws DOMProcessingException {
      NodeList subelts = parentElement.getChildNodes();
      List validElts = new ArrayList();

      for(int i = 0; i < subelts.getLength(); ++i) {
         Node n = subelts.item(i);
         if (n.getNodeType() == 1) {
            Element elt = (Element)n;
            if (elt.getLocalName().equals(localName)) {
               validElts.add(elt);
            }
         }
      }

      return validElts;
   }

   public static Element getOptionalElementByLocalName(Element parentElement, String localName) throws DOMProcessingException {
      List subelts = getOptionalElementsByLocalName(parentElement, localName);
      int nsubelts = subelts.size();
      switch (nsubelts) {
         case 0:
            return null;
         case 1:
            return (Element)subelts.get(0);
         default:
            throw new ChildCountException(2, parentElement.getNodeName(), localName, nsubelts);
      }
   }

   public static Element getElementByTagNameNS(Element parentElement, String ns, String tagName) throws DOMProcessingException {
      Element element = getOptionalElementByTagNameNS(parentElement, ns, tagName);
      if (element == null) {
         throw new ChildCountException(1, parentElement.getNodeName(), tagName, 0);
      } else {
         return element;
      }
   }

   public static List getOptionalElementsByTagNameNS(Element parentElement, String ns, String tagName) {
      ArrayList list = new ArrayList();

      for(Node node = parentElement.getFirstChild(); node != null; node = node.getNextSibling()) {
         if (node instanceof Element) {
            Element elt = (Element)node;
            if (ns.equals(elt.getNamespaceURI()) && tagName.equals(elt.getLocalName())) {
               list.add(elt);
            }
         }
      }

      return list;
   }

   public static Element getOptionalElementByTagNameNS(Element parentElement, String ns, String tagName) throws DOMProcessingException {
      for(Node node = parentElement.getFirstChild(); node != null; node = node.getNextSibling()) {
         if (node instanceof Element) {
            Element elt = (Element)node;
            if (ns.equals(elt.getNamespaceURI()) && tagName.equals(elt.getLocalName())) {
               return elt;
            }
         }
      }

      return null;
   }

   public static List getOptionalElementsByTagName(Element parentElement, String tagName) throws DOMProcessingException {
      NodeList subelts = parentElement.getChildNodes();
      List validElts = new ArrayList();

      for(int i = 0; i < subelts.getLength(); ++i) {
         Node n = subelts.item(i);
         if (n.getNodeType() == 1) {
            Element elt = (Element)n;
            if (elt.getTagName().equals(tagName)) {
               validElts.add(elt);
            }
         }
      }

      return validElts;
   }

   public static String getTextData(Node terminalNode) throws DOMProcessingException {
      StringBuffer valbuf = new StringBuffer(80);
      NodeList tt = terminalNode.getChildNodes();

      for(int i = 0; i < tt.getLength(); ++i) {
         Node n = tt.item(i);
         if (n.getNodeType() == 3 || n.getNodeType() == 4) {
            Text txtNode = (Text)n;
            valbuf.append(txtNode.getData().trim());
         }
      }

      return new String(valbuf.toString());
   }

   public static List getTextDataValues(NodeList terminalNodes) throws DOMProcessingException {
      int len = terminalNodes.getLength();
      List vals = new ArrayList(len);

      for(int i = 0; i < len; ++i) {
         vals.add(getTextData(terminalNodes.item(i)));
      }

      return vals;
   }

   public static List getTextDataValues(List terminalNodes) throws DOMProcessingException {
      int len = terminalNodes.size();
      List vals = new ArrayList(len);

      for(int i = 0; i < len; ++i) {
         vals.add(getTextData((Node)terminalNodes.get(i)));
      }

      return vals;
   }

   public static boolean elementIsOneOf(Element elt, String[] validTags) {
      String tagName = elt.getTagName();

      for(int i = 0; i < validTags.length; ++i) {
         if (validTags[i].equals(tagName)) {
            return true;
         }
      }

      return false;
   }

   public static boolean elementHas(Element elt, String tagName) {
      return getElementCount(elt, tagName) > 0;
   }

   public static int getElementCount(Element elt, String tagName) {
      NodeList children = elt.getChildNodes();
      int matchingChildCount = 0;
      int i = 0;

      for(int nchildren = children.getLength(); i < nchildren; ++i) {
         Node n = children.item(i);
         if (n.getNodeType() == 1 && tagName.equals(((Element)n).getTagName())) {
            ++matchingChildCount;
         }
      }

      return matchingChildCount;
   }

   public static Element addValue(Element element, String tagName, String value) {
      Element childElement = element.getOwnerDocument().createElement(tagName);
      childElement.appendChild(element.getOwnerDocument().createTextNode(value));
      element.appendChild(childElement);
      return childElement;
   }

   public static Element addValueNS(Element element, String ns, String tagName, String value) {
      Element childElement = element.getOwnerDocument().createElementNS(ns, tagName);
      childElement.appendChild(element.getOwnerDocument().createTextNode(value));
      element.appendChild(childElement);
      return childElement;
   }

   public static void addNamespaceDeclaration(Element element, String prefix, String uri) {
      addNamespaceDeclaration(element, prefix, uri, true);
   }

   public static void addNamespaceDeclaration(Element element, String prefix, String uri, boolean allowDups) {
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, uri);
   }

   public static String getNamespaceURI(Element element, String prefix) {
      return element.getAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix);
   }

   public static void setDefaultNamespace(Element element, String uri) {
      element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", uri);
   }

   public static void addTextData(Element element, String value) {
      element.appendChild(element.getOwnerDocument().createTextNode(value));
   }

   public static void addEmptyElement(Element element, String tagName) {
      element.appendChild(element.getOwnerDocument().createElement(tagName));
   }

   public static void copyNodes(Element element, NodeList nodes) throws DOMProcessingException {
      for(int i = 0; i < nodes.getLength(); ++i) {
         element.appendChild(element.getOwnerDocument().importNode(nodes.item(i), true));
      }

   }

   public static boolean isNameSpaceUriEmpty(Node n) {
      String nsUri = n.getNamespaceURI();
      return nsUri == null || nsUri.length() == 0;
   }

   public static boolean isNameSpaceUriEmpty(QName n) {
      String nsUri = n.getNamespaceURI();
      return nsUri == null || nsUri.length() == 0;
   }

   public static String getAttributeValueAsString(Element elt, QName attrName) {
      String nsURI = attrName.getNamespaceURI();
      String value = null;
      if (nsURI != null && nsURI.length() > 0) {
         value = elt.getAttributeNS(nsURI, attrName.getLocalPart());
      } else {
         value = elt.getAttribute(attrName.getLocalPart());
      }

      return value;
   }

   public static boolean equalsQName(Node node, QName name) {
      if (!node.getLocalName().equals(name.getLocalPart())) {
         return false;
      } else {
         boolean nodeNsUriEmpty = isNameSpaceUriEmpty(node);
         boolean nameNsUriEmpty = isNameSpaceUriEmpty(name);
         if (nodeNsUriEmpty) {
            return nameNsUriEmpty;
         } else {
            return nameNsUriEmpty ? nodeNsUriEmpty : node.getNamespaceURI().equals(name.getNamespaceURI());
         }
      }
   }

   public static Element getFirstElement(Node top, QName eltName) {
      if (top.getNodeType() == 1 && equalsQName(top, eltName)) {
         return (Element)top;
      } else {
         for(Node n = top.getFirstChild(); n != null; n = n.getNextSibling()) {
            Element found = getFirstElement(n, eltName);
            if (found != null) {
               return found;
            }
         }

         return null;
      }
   }

   public static String getTextContent(Element elt, boolean trimWhitespace) {
      StringBuffer valbuf = new StringBuffer();
      NodeList tt = elt.getChildNodes();

      for(int i = 0; i < tt.getLength(); ++i) {
         Node n = tt.item(i);
         if (n.getNodeType() == 3 || n.getNodeType() == 4) {
            Text txtNode = (Text)n;
            if (trimWhitespace) {
               valbuf.append(txtNode.getData().trim());
            } else {
               valbuf.append(txtNode.getData());
            }
         }
      }

      return new String(valbuf.toString());
   }
}
