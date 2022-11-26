package weblogic.xml.domimpl;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;
import weblogic.xml.util.EmptyIterator;

public abstract class ElementBase extends ParentNode implements Element, NamespaceContext {
   private static final long serialVersionUID = 2420490060933683769L;
   protected AttributeMap attributes;
   protected boolean isImportedFromNsEle = true;
   private static final List XML_NS_PREFIX_LIST = Collections.singletonList("xml");
   private static final List XMLNS_ATTRIBUTE_LIST = Collections.singletonList("xmlns");

   protected ElementBase(DocumentImpl ownerDocument) throws DOMException {
      super(ownerDocument);
   }

   protected ElementBase(DocumentImpl ownerDocument, int num_attrs) throws DOMException {
      super(ownerDocument);
      this.attributes = new AttributeMap(this, num_attrs);
   }

   public void deepCopyAttributes(ElementBase dest) {
      if (this.attributes == null) {
         dest.attributes = null;
      } else {
         dest.attributes = this.attributes.cloneMap(dest);
      }

   }

   public final short getNodeType() {
      return 1;
   }

   public NamedNodeMap getAttributes() {
      if (this.attributes == null) {
         this.attributes = new AttributeMap(this);
      }

      return this.attributes;
   }

   public boolean hasAttributes() {
      if (this.attributes == null) {
         return false;
      } else {
         return this.attributes.getLength() > 0;
      }
   }

   public String getAttribute(String name) {
      if (this.attributes == null) {
         return "";
      } else {
         Attr attr = (Attr)((Attr)this.attributes.getNamedItem(name));
         return attr == null ? "" : attr.getValue();
      }
   }

   public void setAttribute(String name, String value) throws DOMException {
      if (this.ownerDocument.errorChecking && this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         Attr newAttr = this.getAttributeNode(name);
         if (newAttr == null) {
            newAttr = this.getOwnerDocument().createAttribute(name);
            if (this.attributes == null) {
               this.attributes = new AttributeMap(this);
            }

            newAttr.setNodeValue(value);
            this.attributes.setNamedItem(newAttr);
         } else {
            newAttr.setNodeValue(value);
         }

      }
   }

   public void removeAttribute(String name) throws DOMException {
      if (this.ownerDocument.errorChecking && this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else if (this.attributes != null) {
         this.attributes.safeRemoveNamedItem(name);
      }
   }

   public Attr getAttributeNode(String name) {
      return this.attributes == null ? null : (Attr)this.attributes.getNamedItem(name);
   }

   public Attr setAttributeNode(Attr newAttr) throws DOMException {
      if (this.ownerDocument.errorChecking) {
         if (this.isReadOnly()) {
            throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
         }

         if (newAttr.getOwnerDocument() != this.ownerDocument) {
            throw new DOMException((short)4, "WRONG_DOCUMENT_ERR");
         }
      }

      if (this.attributes == null) {
         this.attributes = new AttributeMap(this);
      }

      return (Attr)this.attributes.setNamedItem(newAttr);
   }

   public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
      if (this.ownerDocument.errorChecking && this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else if (this.attributes == null) {
         throw new DOMException((short)8, "NOT_FOUND_ERR");
      } else {
         return (Attr)this.attributes.removeItem(oldAttr, true);
      }
   }

   public NodeList getElementsByTagName(String name) {
      return new DeepNodeListImpl(this, name);
   }

   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
      return new DeepNodeListImpl(this, namespaceURI, localName);
   }

   public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
      if (this.attributes == null) {
         return "";
      } else {
         Attr attr = (Attr)((Attr)this.attributes.getNamedItemNS(namespaceURI, localName));
         return attr == null ? "" : attr.getValue();
      }
   }

   public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
      if (this.ownerDocument.errorChecking && this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         int index = qualifiedName.indexOf(58);
         String prefix;
         String localName;
         if (index < 0) {
            prefix = null;
            localName = qualifiedName;
         } else {
            prefix = qualifiedName.substring(0, index);
            localName = qualifiedName.substring(index + 1);
         }

         Attr newAttr = this.getAttributeNodeNS(namespaceURI, localName);
         if (newAttr == null) {
            newAttr = this.ownerDocument().createAttributeNS(namespaceURI, localName, prefix);
            if (this.attributes == null) {
               this.attributes = new AttributeMap(this);
            }

            newAttr.setNodeValue(value);
            this.attributes.setNamedItemNS(newAttr);
         } else {
            if (newAttr instanceof AttrNSImpl) {
               AttrNSImpl attr_ns = (AttrNSImpl)newAttr;
               attr_ns.prefix = prefix;
               attr_ns.value = value;
               attr_ns.hasStringValue(true);
            } else {
               newAttr = this.ownerDocument().createAttributeNS(namespaceURI, qualifiedName);
               this.attributes.setNamedItemNS(newAttr);
            }

            newAttr.setNodeValue(value);
         }

      }
   }

   public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
      if (this.ownerDocument.errorChecking && this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else if (this.attributes != null) {
         this.attributes.safeRemoveNamedItemNS(namespaceURI, localName);
      }
   }

   public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
      return this.attributes == null ? null : (Attr)this.attributes.getNamedItemNS(namespaceURI, localName);
   }

   public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
      if (this.ownerDocument.errorChecking) {
         if (this.isReadOnly()) {
            throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
         }

         if (newAttr.getOwnerDocument() != this.ownerDocument) {
            throw new DOMException((short)4, "WRONG_DOCUMENT_ERR");
         }
      }

      if (this.attributes == null) {
         this.attributes = new AttributeMap(this);
      }

      return (Attr)this.attributes.setNamedItemNS(newAttr);
   }

   public boolean hasAttribute(String name) {
      return this.getAttributeNode(name) != null;
   }

   public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
      return this.getAttributeNodeNS(namespaceURI, localName) != null;
   }

   public final TypeInfo getSchemaTypeInfo() {
      return null;
   }

   public void setIdAttribute(String name, boolean isId) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void setReadOnly(boolean readOnly, boolean deep) {
      super.setReadOnly(readOnly, deep);
      if (this.attributes != null) {
         this.attributes.setReadOnly(readOnly, true);
      }

   }

   public Node cloneNode(boolean deep) {
      ElementBase newnode = (ElementBase)super.cloneNode(deep);
      if (this.attributes != null) {
         newnode.attributes = this.attributes.cloneMap(newnode);
      }

      return newnode;
   }

   public void normalize() {
      if (!this.isNormalized()) {
         ChildNode next;
         for(ChildNode kid = this.firstChild; kid != null; kid = next) {
            next = kid.nextSibling;
            if (kid.getNodeType() == 3) {
               if (next != null && next.getNodeType() == 3) {
                  ((Text)kid).appendData(next.getNodeValue());
                  this.removeChild(next);
                  next = kid;
               } else if (kid.getNodeValue().length() == 0) {
                  this.removeChild(kid);
               }
            } else if (kid.getNodeType() == 1) {
               kid.normalize();
            }
         }

         if (this.attributes != null) {
            for(int i = 0; i < this.attributes.getLength(); ++i) {
               Node attr = this.attributes.item(i);
               attr.normalize();
            }
         }

         this.isNormalized(true);
      }
   }

   public String getNamespaceURI(String prefix) {
      if (prefix == null) {
         throw new IllegalArgumentException("null prefix");
      } else if ("xml".equals(prefix)) {
         return "http://www.w3.org/XML/1998/namespace";
      } else if ("xmlns".equals(prefix)) {
         return "http://www.w3.org/2000/xmlns/";
      } else {
         for(ElementBase el = this; el != null; el = el.getElementAncestor()) {
            String ns = el.getNamespaceOnElement(prefix);
            if (ns != null) {
               return ns;
            }
         }

         return null;
      }
   }

   public String getPrefix(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("null namespaceURI");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) {
         return "xml";
      } else if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
         return "xmlns";
      } else {
         for(ElementBase el = this; el != null; el = el.getElementAncestor()) {
            String pfx = el.getPrefixOnElement(namespaceURI, true);
            if (pfx != null) {
               return pfx;
            }
         }

         return null;
      }
   }

   public Iterator getPrefixes(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("null namespaceURI");
      } else if ("".equals(namespaceURI)) {
         throw new IllegalArgumentException("zero length namespaceURI");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) {
         return XML_NS_PREFIX_LIST.iterator();
      } else if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
         return XMLNS_ATTRIBUTE_LIST.iterator();
      } else {
         List results = null;

         for(ElementBase el = this; el != null; el = el.getElementAncestor()) {
            if (el.hasAttributes()) {
               AttributeMap atts = el.attributes;
               int i = 0;

               for(int len = atts.getLength(); i < len; ++i) {
                  AttrBase att = (AttrBase)atts.item(i);
                  if (att.isNamespaceAttribute() && namespaceURI.equals(att.getValue())) {
                     if (results == null) {
                        results = new LinkedList();
                     }

                     String ns_pfx = att.getLocalName();
                     if (!results.contains(ns_pfx)) {
                        results.add(ns_pfx);
                     }
                  }
               }
            }
         }

         if (results == null) {
            return EmptyIterator.getInstance();
         } else {
            return results.iterator();
         }
      }
   }

   public String getPrefixOnElement(String nsURI, boolean check_default_ns) {
      if (this.hasAttributes()) {
         assert nsURI != null;

         AttributeMap atts = this.attributes;
         int i = 0;

         for(int len = atts.getLength(); i < len; ++i) {
            AttrBase att = (AttrBase)atts.item(i);
            if (att.isNamespaceAttribute() && nsURI.equals(att.getValue())) {
               return att.getLocalName();
            }
         }
      }

      return null;
   }

   public String getNamespaceOnElement(String prefix) {
      assert prefix != null;

      if (this.hasAttributes()) {
         AttributeMap atts = this.attributes;
         int i = 0;

         for(int len = atts.getLength(); i < len; ++i) {
            AttrBase att = (AttrBase)atts.item(i);
            if (att.isNamespaceAttribute()) {
               NSAttrBase nsa = (NSAttrBase)att;
               if (nsa.definesNamespacePrefix(prefix)) {
                  return nsa.getValue();
               }
            }
         }
      }

      return null;
   }

   protected NamedNodeMapImpl getDefaultAttributes() {
      return null;
   }

   boolean isImportedFromNSAwareElement() {
      return this.isImportedFromNsEle;
   }

   void setImportedFromNSAwareElement(boolean importedFromNsEle) {
      this.isImportedFromNsEle = importedFromNsEle;
   }
}
