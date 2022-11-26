package weblogic.xml.saaj;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.DetailEntry;
import javax.xml.soap.Name;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import weblogic.xml.dom.NamespaceUtils;
import weblogic.xml.domimpl.AttrBase;
import weblogic.xml.domimpl.ChildNode;
import weblogic.xml.domimpl.CommentImpl;
import weblogic.xml.domimpl.DocumentImpl;
import weblogic.xml.domimpl.ElementBase;
import weblogic.xml.domimpl.ElementNSImpl;
import weblogic.xml.domimpl.NodeImpl;
import weblogic.xml.saaj.util.NSIterator;
import weblogic.xml.util.EmptyIterator;

class SOAPElementImpl extends ElementNSImpl implements SOAPElement, SOAPBodyElement, SaajNode, DetailEntry {
   private Name name;
   static final long serialVersionUID = -7848444999187313569L;

   SOAPElementImpl(DocumentImpl ownerDocument, String namespaceURI, String qualifiedName) throws DOMException {
      super(ownerDocument, namespaceURI, qualifiedName);
   }

   SOAPElementImpl(DocumentImpl ownerDocument, String namespaceURI, String localName, String prefix) throws DOMException {
      super(ownerDocument, namespaceURI, localName, prefix);
   }

   SOAPElementImpl(DocumentImpl ownerDocument, String namespaceURI, String localName, String prefix, int numAttrs) throws DOMException {
      super(ownerDocument, namespaceURI, localName, prefix, numAttrs);
   }

   SOAPElementImpl(DocumentImpl ownerDocument, QName name) throws DOMException {
      super(ownerDocument, name.getNamespaceURI(), name.getLocalPart(), name.getPrefix());
   }

   SOAPElementImpl(DocumentImpl ownerDocument, Name name) throws DOMException {
      super(ownerDocument, name.getURI(), name.getLocalName(), name.getPrefix());
      this.name = name;
   }

   SOAPElementImpl(DocumentImpl ownerDocument, Element domElement) throws DOMException {
      super(ownerDocument, domElement.getNamespaceURI(), domElement.getLocalName(), domElement.getPrefix());
      this.copyAttributes(this, domElement);
      this.copyChildren(this, domElement);
   }

   public void removeContents() {
      ChildNode tempNode;
      for(ChildNode currentNode = this.firstChild; currentNode != null; currentNode = tempNode) {
         tempNode = currentNode.nextSibling();
         if (currentNode instanceof Node) {
            ((Node)currentNode).detachNode();
         } else {
            org.w3c.dom.Node parent = currentNode.getParentNode();
            if (parent != null) {
               parent.removeChild(currentNode);
            }
         }
      }

   }

   public String getEncodingStyle() {
      String encoding = this.getAttributeNS(this.getNamespaceURI(), "encodingStyle");
      return encoding.equals("") ? null : encoding;
   }

   public void setEncodingStyle(String encoding) throws SOAPException {
      if ("http://www.w3.org/2003/05/soap-envelope".equals(this.getNamespaceURI())) {
         throw new SOAPException("It's not allowed to change the encoding of SOAP 1.2 message");
      } else {
         this.checkValidEncoding(encoding);
         Attr att = this.getAttributeNodeNS(this.getNamespaceURI(), "encodingStyle");
         if (att == null) {
            this.setAttributeNS(this.getNamespaceURI(), "encodingStyle", encoding);
         } else {
            att.setValue(encoding);
         }

      }
   }

   private void checkValidEncoding(String encoding) {
      if (!"http://schemas.xmlsoap.org/soap/encoding/".equals(encoding) && !"http://www.w3.org/2003/05/soap-encoding".equals(encoding)) {
         if ("BOGUS".equals(encoding)) {
            throw new IllegalArgumentException("invalid soap encoding uri: " + encoding);
         } else {
            try {
               new URI(encoding);
            } catch (URISyntaxException var3) {
               throw new IllegalArgumentException("invalid soap encoding uri: " + encoding, var3);
            }
         }
      }
   }

   public boolean removeNamespaceDeclaration(String prefix) {
      Attr declaration = this.getNamespaceAttr(prefix);
      if (declaration == null) {
         return false;
      } else {
         this.removeAttributeNode(declaration);
         return true;
      }
   }

   protected Attr getNamespaceAttr(String prefix) {
      NSIterator eachNamespace = this.getNamespaceContextNodes();
      if (!"".equals(prefix)) {
         prefix = ":" + prefix;
      }

      while(eachNamespace.hasNext()) {
         Attr namespaceDecl = eachNamespace.nextNamespaceAttr();
         if (!"".equals(prefix)) {
            if (namespaceDecl.getNodeName().endsWith(prefix)) {
               return namespaceDecl;
            }
         } else if (namespaceDecl.getNodeName().equals("xmlns")) {
            return namespaceDecl;
         }
      }

      return null;
   }

   private NSIterator getNamespaceContextNodes() {
      return this.getNamespaceContextNodes(true);
   }

   private NSIterator getNamespaceContextNodes(boolean traverseStack) {
      return new NSIterator(this, traverseStack);
   }

   public Iterator getAllAttributes() {
      return new AttributesIterator(this);
   }

   public Iterator getAllAttributesAsQNames() {
      return this.getAllAttributes();
   }

   public Iterator getChildElements() {
      return (Iterator)(!this.hasChildNodes() ? EmptyIterator.getInstance() : new ChildElementIterator(this));
   }

   public Iterator getNamespacePrefixes() {
      return !this.hasAttributes() ? EmptyIterator.getInstance() : this.getNSPrefixes(false);
   }

   public Iterator getVisibleNamespacePrefixes() {
      return this.getNSPrefixes(false);
   }

   protected String findPrefixInTree(String nsURI) {
      for(org.w3c.dom.Node curr = this; curr != null; curr = ((org.w3c.dom.Node)curr).getParentNode()) {
         String pfx = NamespaceUtils.getPrefixOnElement((org.w3c.dom.Node)curr, nsURI, true);
         if (pfx != null) {
            return pfx;
         }
      }

      return null;
   }

   public Name getElementName() {
      if (this.name == null) {
         String uri = this.getNamespaceURI();
         if (uri == null) {
            return new NameImpl(this.getLocalName());
         }

         this.name = new NameImpl(this.getLocalName(), this.getPrefix(), uri);
      }

      return this.name;
   }

   public boolean removeAttribute(Name name) {
      String uri = fixEmptyNamespaceURI(name.getURI());
      Attr att = this.getAttributeNodeNS(uri, name.getLocalName());
      if (att == null) {
         return false;
      } else {
         this.removeAttributeNode(att);
         return true;
      }
   }

   public String getAttributeValue(Name name) {
      String uri = fixEmptyNamespaceURI(name.getURI());
      Attr att = this.getAttributeNodeNS(uri, name.getLocalName());
      return att == null ? null : att.getValue();
   }

   public Iterator getChildElements(Name name) {
      return (Iterator)(!this.hasChildNodes() ? EmptyIterator.getInstance() : new NamedChildElementIterator(name, this));
   }

   protected ElementBase firstElementChild() {
      return firstElementChild(this.firstChild());
   }

   protected static ElementBase firstElementChild(ChildNode node) {
      for(ChildNode curr = node; curr != null; curr = curr.nextSibling()) {
         if (curr.getNodeType() == 1) {
            return (ElementBase)curr;
         }
      }

      return null;
   }

   protected static ElementBase nextElementSibling(ChildNode node) {
      for(ChildNode curr = node.nextSibling(); curr != null; curr = curr.nextSibling()) {
         if (curr.getNodeType() == 1) {
            return (ElementBase)curr;
         }
      }

      return null;
   }

   public SOAPElement addChildElement(String localname) throws SOAPException {
      return this.addChildElement(localname, (String)null, (String)null);
   }

   public SOAPElement addTextNode(String value) throws SOAPException {
      TextImpl text = new TextImpl(this.ownerDocument, value);
      this.appendChild(text);
      return this;
   }

   public SOAPElement addChildElement(Name name) throws SOAPException {
      SOAPElementImpl se = new SOAPElementImpl(this.ownerDocument, name);
      this.appendChild(se);
      return se;
   }

   public SOAPElement addChildElement(SOAPElement element) throws SOAPException {
      String elementURI = element.getNamespaceURI();
      if ("http://schemas.xmlsoap.org/soap/envelope/".equals(elementURI)) {
         throw new SOAPException("Cannot add fragments which contain elements which are in the SOAP namespace");
      } else {
         SOAPElementImpl soapElement = (SOAPElementImpl)this.importElement(element);
         this.appendChild(soapElement);
         return soapElement;
      }
   }

   protected Element importElement(Element element) {
      Document document = this.getOwnerDocument();
      Document oldDocument = element.getOwnerDocument();
      return !oldDocument.equals(document) ? (Element)document.importNode(element, true) : element;
   }

   public SOAPElement addChildElement(String localname, String prefix) throws SOAPException {
      String uri = this.lookupNamespaceURI(prefix);
      if (uri == null) {
         throw new SOAPException("unable to find namespace for prefix: " + prefix);
      } else {
         return this.addChildElement(localname, prefix, uri);
      }
   }

   public SOAPElement addNamespaceDeclaration(String prefix, String uri) throws SOAPException {
      if (uri == null) {
         throw new IllegalArgumentException("namespace uri cannot be null");
      } else {
         if (prefix != null && prefix.length() > 0) {
            NamespaceUtils.defineNamespace(this, prefix, uri);
         } else {
            NamespaceUtils.defineDefaultNamespace(this, uri);
         }

         return this;
      }
   }

   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
      Attr attributeNS = this.ownerDocument.createAttributeNS(name.getURI(), name.getLocalName(), name.getPrefix());
      attributeNS.setValue(value);
      this.setAttributeNodeNS(attributeNS);
      return this;
   }

   public SOAPElement addChildElement(String localname, String prefix, String uri) throws SOAPException {
      SOAPElementImpl se = new SOAPElementImpl(this.ownerDocument, uri, localname, prefix);
      this.appendChild(se);
      return se;
   }

   public void detachNode() {
      NodeImpl parent = this.parentNode();
      if (parent != null) {
         parent.removeChild(this);
      }

   }

   public void recycleNode() {
      this.detachNode();
   }

   public String getValue() {
      return this.getTextContent();
   }

   public void setValue(String s) {
      if (!this.hasChildNodes()) {
         TextImpl textNode = (TextImpl)this.getOwnerDocument().createTextNode(s);
         textNode.isSaajTyped(true);
         this.appendChild(textNode);
      } else {
         ChildNode node = this.firstChild();
         if (node.nextSibling() != null || 3 != node.getNodeType()) {
            throw new IllegalStateException("node must have only one child node and it must be of type Text");
         }

         ((TextImpl)node).setValue(s);
      }

   }

   public SOAPElement getParentElement() {
      return (SOAPElement)this.getParentNode();
   }

   public void setParentElement(SOAPElement element) throws SOAPException {
      if (element == null) {
         throw new SOAPException("null element not allowed");
      } else {
         element.addChildElement(this);
      }
   }

   public SOAPElementImpl createAndAppendSaajElement(NodeImpl parent, String namespaceURI, String localName, String prefix, int numAttrs) {
      assert this == parent;

      SOAPElementImpl soapElement = new SOAPElementImpl(this.ownerDocument, namespaceURI, localName, prefix);
      soapElement.isSaajTyped(true);
      this.appendChild(soapElement);
      return soapElement;
   }

   public ChildNode fixChildSaajType(ChildNode child) {
      if (child.getNodeType() == 1) {
         assert child instanceof SOAPElementImpl;

         if (!(child instanceof SOAPElementImpl)) {
            String msg = "not soaplelem impl: " + child.getClass() + " this=" + this + " lname=" + child.getLocalName();
            throw new AssertionError(msg);
         } else {
            SOAPElementImpl soapElement = (SOAPElementImpl)child;
            soapElement.isSaajTyped(true);
            return soapElement;
         }
      } else if (child.getNodeType() == 3) {
         assert child instanceof TextImpl;

         TextImpl textElement = (TextImpl)child;
         textElement.isSaajTyped(true);
         return textElement;
      } else {
         return child;
      }
   }

   protected final SaajNode updateSaajChild(SOAPElementImpl newChild, ElementBase src) {
      this.copyAttributes(newChild, src);
      this.copyChildren(newChild, src);
      newChild.isSaajTyped(true);
      this.replaceChild(newChild, src);
      return newChild;
   }

   void copyAttributes(SOAPElementImpl newChild, Element src) {
      Document ownerDoc = newChild.getOwnerDocument();
      NamedNodeMap attrMap = src.getAttributes();

      for(int i = 0; i < attrMap.getLength(); ++i) {
         Attr nextAttr = (Attr)attrMap.item(i);
         Attr importedAttr = (Attr)ownerDoc.importNode(nextAttr, true);
         newChild.setAttributeNodeNS(importedAttr);
      }

   }

   void copyChildren(SOAPElementImpl newChild, Element domElement) {
      if (domElement.hasChildNodes()) {
         for(ChildNode node = (ChildNode)domElement.getFirstChild(); node != null; node = node.nextSibling()) {
            switch (node.getNodeType()) {
               case 1:
                  int attrCount = node.hasAttributes() ? 0 : node.getAttributes().getLength();
                  SOAPElementImpl nc = newChild.createAndAppendSaajElement(newChild, node.getNamespaceURI(), node.getLocalName(), node.getPrefix(), attrCount);
                  ElementBase element = (ElementBase)node;
                  this.copyAttributes(nc, element);
                  this.copyChildren(nc, element);
                  break;
               case 3:
                  TextImpl text = new TextImpl(newChild.ownerDocument, node.getNodeValue());
                  newChild.appendChild(text);
                  break;
               case 8:
                  CommentImpl comment = new CommentImpl(newChild.ownerDocument, node.getNodeValue());
                  newChild.appendChild(comment);
                  break;
               default:
                  newChild.appendChild(node);
            }
         }

      }
   }

   public Iterator getChildElements(QName qname) {
      return this.getChildElements((Name)(new NameImpl(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI())));
   }

   public boolean removeAttribute(QName qname) {
      Attr attr = this.getAttributeNodeNS(fixEmptyNamespaceURI(qname.getNamespaceURI()), qname.getLocalPart());
      if (attr != null) {
         this.removeAttributeNode(attr);
         return true;
      } else {
         return false;
      }
   }

   public SOAPElement setElementQName(QName newName) throws SOAPException {
      SOAPElement element = ((SOAPElementImpl)this.getParentElement()).addChildElement(newName);
      this.detachNode();
      return element;
   }

   public QName getElementQName() {
      return new QName(this.getNamespaceURI(), this.getLocalName(), this.getPrefix() == null ? "" : this.getPrefix());
   }

   public QName createQName(String localName, String prefix) throws SOAPException {
      String ns = this.getNamespaceURI(prefix);
      if (ns == null) {
         throw new SOAPException("Unknown prefix: " + prefix);
      } else {
         return new QName(ns, localName, prefix);
      }
   }

   public String getAttributeValue(QName qname) {
      String result = this.getAttributeNS(fixEmptyNamespaceURI(qname.getNamespaceURI()), qname.getLocalPart());
      return "".equals(result) ? null : result;
   }

   public SOAPElement addAttribute(QName qname, String value) throws SOAPException {
      return this.addAttribute((Name)(new NameImpl(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI())), value);
   }

   public SOAPElement addChildElement(QName qname) throws SOAPException {
      return this.addChildElement(qname.getLocalPart(), qname.getPrefix(), qname.getNamespaceURI());
   }

   protected String qnameToString(String localName, String prefix, String namespaceURI) throws SOAPException {
      prefix = this.findPrefix(prefix, namespaceURI);
      String colonizedName;
      if (prefix.equals("")) {
         colonizedName = localName;
      } else {
         colonizedName = prefix + ":" + localName;
      }

      return colonizedName;
   }

   private String findPrefix(String prefix, String uri) throws SOAPException {
      String tmpPrefix;
      if (prefix != null && !prefix.equals("")) {
         tmpPrefix = this.lookupNamespaceURIInternal(prefix, false);
         if (uri == null) {
            throw new SOAPException("fault code cannot have prefix but no uri");
         }

         if (!uri.equals(tmpPrefix)) {
            this.addNamespaceDeclaration(prefix, uri);
         }
      } else if (uri != null && !uri.equals("")) {
         tmpPrefix = this.findPrefixInTree(uri);
         if (tmpPrefix != null) {
            prefix = tmpPrefix;
         } else if (!this.isSoap11()) {
            Iterator it = this.getNamespacePrefixes();
            Set prefixes = new HashSet();

            while(it.hasNext()) {
               String next = it.next().toString();
               prefixes.add(next);
            }

            for(int i = 0; i < prefixes.size(); ++i) {
               String p = "ns" + i;
               if (!prefixes.contains(p)) {
                  prefix = p;
                  break;
               }
            }

            if (prefix == null || prefix.equals("")) {
               prefix = "ns0";
            }

            this.addNamespaceDeclaration(prefix, uri);
         }
      } else {
         prefix = "";
      }

      return prefix;
   }

   boolean isSoap11() {
      return isSoap11(this.getNamespaceURI());
   }

   static boolean isSoap11(String namespaceURI) {
      return "http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceURI);
   }

   protected Iterator getNSPrefixes(boolean deep) {
      return new PrefixIterator(this.getNamespaceContextNodes(deep));
   }

   private static class PrefixIterator implements Iterator {
      private String next;
      private String last;
      private final NSIterator eachNamespace;

      public PrefixIterator(NSIterator eachNamespace) {
         this.eachNamespace = eachNamespace;
      }

      void findNext() {
         while(this.next == null && this.eachNamespace.hasNext()) {
            String attributeKey = this.eachNamespace.nextNamespaceAttr().getNodeName();
            if (attributeKey.startsWith("xmlns:")) {
               this.next = attributeKey.substring("xmlns:".length());
            }
         }

      }

      public void remove() {
         if (this.last == null) {
            throw new IllegalStateException();
         } else {
            this.eachNamespace.remove();
            this.next = null;
            this.last = null;
         }
      }

      public boolean hasNext() {
         this.findNext();
         return this.next != null;
      }

      public Object next() {
         this.findNext();
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            this.last = this.next;
            this.next = null;
            return this.last;
         }
      }
   }

   private static class NamedChildElementIterator implements Iterator {
      private final Name match;
      private final ChildElementIterator childElementIterator;
      private SOAPElementImpl next;

      public NamedChildElementIterator(Name name, SOAPElementImpl elem) {
         this.match = name;
         this.childElementIterator = new ChildElementIterator(elem);
         this.findNext();
      }

      public boolean hasNext() {
         return this.next != null;
      }

      public Object next() {
         SOAPElementImpl val = this.next;
         if (val == null) {
            throw new NoSuchElementException();
         } else {
            this.next = this.findNext();
            return val;
         }
      }

      private SOAPElementImpl findNext() {
         ChildNode curr = null;

         do {
            if (!this.childElementIterator.hasNext()) {
               return this.next = null;
            }

            curr = this.childElementIterator.nextInternal();
         } while(1 != curr.getNodeType() || !this.matches(curr));

         return this.next = (SOAPElementImpl)curr;
      }

      private boolean matches(ChildNode element) {
         String elem_lname = element.getLocalName();

         assert elem_lname != null;

         String elementUri = element.getNamespaceURI();
         elementUri = elementUri != null ? elementUri : "";
         return elementUri.equals(this.match.getURI()) && elem_lname.equals(this.match.getLocalName());
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   protected final class AttributesIterator implements Iterator {
      private final NamedNodeMap attributes;
      private int currAtt;

      public AttributesIterator(SOAPElementImpl element) {
         this.attributes = element.getAttributes();
      }

      public boolean hasNext() {
         for(int len = this.attributes.getLength(); this.currAtt < len; ++this.currAtt) {
            AttrBase att = (AttrBase)this.attributes.item(this.currAtt);
            if (!att.isNamespaceAttribute()) {
               return true;
            }
         }

         return false;
      }

      public Object next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            AttrBase att = (AttrBase)this.attributes.item(this.currAtt++);
            String lname = att.getLocalName();
            return lname == null ? new NameImpl(att.getNodeName()) : new NameImpl(lname, att.getPrefix(), att.getNamespaceURI());
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static final class ChildElementIterator implements Iterator {
      private final SOAPElementImpl element;
      private ChildNode next;
      private ChildNode nextNext;
      private ChildNode last;

      public ChildElementIterator(SOAPElementImpl soapElement) {
         this.element = soapElement;
         this.next = this.element.firstChild;
         this.nextNext = null;
         this.last = null;
      }

      public void remove() {
         if (this.last == null) {
            throw new IllegalStateException();
         } else {
            org.w3c.dom.Node target = this.last;
            this.last = null;
            this.element.removeChild(target);
         }
      }

      public boolean hasNext() {
         if (this.next != null) {
            return true;
         } else {
            if (this.next == null && this.nextNext != null) {
               this.next = this.nextNext;
            }

            return this.next != null;
         }
      }

      public Object next() {
         return this.nextInternal();
      }

      public ChildNode nextInternal() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.last = this.next;
            this.next = null;
            if (!this.last.isSaajTyped()) {
               this.last = this.element.fixChildSaajType(this.last);

               assert this.last.isSaajTyped();
            }

            this.nextNext = this.last.nextSibling();
            return this.last;
         }
      }
   }
}
