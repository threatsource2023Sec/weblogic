package weblogic.xml.domimpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public class DocumentImpl extends ParentNode implements Document {
   private static final long serialVersionUID = -136828341982895308L;
   protected boolean errorChecking;
   protected DocumentTypeImpl docType;
   protected ElementBase docElement;
   private Map identifiers;
   private String encoding;
   private String version;
   private boolean isStandalone;
   private static final int[] kidOK = new int[13];
   private static final String XML_VERSION_10 = "1.0";

   public DocumentImpl() {
      this((DocumentType)null);
   }

   public DocumentImpl(DocumentType doctype) {
      super((DocumentImpl)null);
      this.errorChecking = true;
      this.ownerDocument = this;
      if (doctype != null) {
         DocumentTypeImpl doctypeImpl;
         try {
            doctypeImpl = (DocumentTypeImpl)doctype;
         } catch (ClassCastException var5) {
            String msg = "WRONG_DOCUMENT_ERR";
            throw new DOMException((short)4, msg);
         }

         doctypeImpl.ownerDocument = this;
         this.appendChild(doctype);
      }

   }

   protected boolean isKidOK(Node parent, Node child) {
      return 0 != (kidOK[parent.getNodeType()] & 1 << child.getNodeType());
   }

   public final Document getOwnerDocument() {
      return null;
   }

   public short getNodeType() {
      return 9;
   }

   public String getNodeName() {
      return "#document";
   }

   public Node cloneNode(boolean deep) {
      DocumentImpl newdoc = new DocumentImpl();
      this.cloneNode(newdoc, deep);
      return newdoc;
   }

   public Object clone() throws CloneNotSupportedException {
      DocumentImpl newdoc = (DocumentImpl)super.clone();
      newdoc.docType = null;
      newdoc.docElement = null;
      return newdoc;
   }

   protected void cloneNode(DocumentImpl newdoc, boolean deep) {
      if (deep) {
         Map reversedIdentifiers = null;
         if (this.identifiers != null) {
            reversedIdentifiers = new HashMap();
            Iterator itr = this.identifiers.keySet().iterator();

            while(itr.hasNext()) {
               Object elementId = itr.next();
               reversedIdentifiers.put(this.identifiers.get(elementId), elementId);
            }
         }

         for(ChildNode kid = this.firstChild; kid != null; kid = kid.nextSibling) {
            newdoc.appendChild(newdoc.importNode(kid, true, true, reversedIdentifiers));
         }
      }

      newdoc.errorChecking = this.errorChecking;
   }

   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
      int type = newChild.getNodeType();
      if (!this.errorChecking || (type != 1 || this.docElement == null) && (type != 10 || this.docType == null)) {
         if (newChild.getOwnerDocument() == null && newChild instanceof DocumentTypeImpl) {
            ((DocumentTypeImpl)newChild).ownerDocument = this;
         }

         super.insertBefore(newChild, refChild);
         if (type == 1) {
            this.docElement = (ElementBase)newChild;
         } else if (type == 10) {
            this.docType = (DocumentTypeImpl)newChild;
         }

         return newChild;
      } else {
         throw new DOMException((short)3, "HIERARCHY_REQUEST_ERR");
      }
   }

   public Node removeChild(Node oldChild) throws DOMException {
      super.removeChild(oldChild);
      int type = oldChild.getNodeType();
      if (type == 1) {
         this.docElement = null;
      } else if (type == 10) {
         this.docType = null;
      }

      return oldChild;
   }

   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
      if (newChild.getOwnerDocument() == null && newChild instanceof DocumentTypeImpl) {
         ((DocumentTypeImpl)newChild).ownerDocument = this;
      }

      super.replaceChild(newChild, oldChild);
      int type = oldChild.getNodeType();
      if (type == 1) {
         this.docElement = (ElementImpl)newChild;
      } else if (type == 10) {
         this.docType = (DocumentTypeImpl)newChild;
      }

      return oldChild;
   }

   public String getTextContent() throws DOMException {
      throw new AssertionError("UNIMP");
   }

   public void setTextContent(String textContent) throws DOMException {
      throw new AssertionError("UNIMP");
   }

   public Attr createAttribute(String name) throws DOMException {
      return new AttrImpl(this, name);
   }

   public EntityReference createEntityReference(String name) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Entity createEntity(String name) throws DOMException {
      if (this.errorChecking && !isXMLName(name)) {
         throw new DOMException((short)5, "INVALID_CHARACTER_ERR");
      } else {
         return new EntityImpl(this, name);
      }
   }

   public NodeList getElementsByTagName(String tagname) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Node importNode(Node importedNode, boolean deep) throws DOMException {
      return this.importNode(importedNode, deep, false, (Map)null);
   }

   public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
      return new ElementNSImpl(this, namespaceURI, qualifiedName);
   }

   public ElementNSImpl createElementNS(String namespaceURI, String localName, String prefix) throws DOMException {
      return new ElementNSImpl(this, namespaceURI, localName, prefix);
   }

   protected ElementNSImpl createElementNS(String namespaceURI, String localName, String prefix, int num_atts) throws DOMException {
      return new ElementNSImpl(this, namespaceURI, localName, prefix, num_atts);
   }

   public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
      if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
         return (Attr)("xmlns".equals(qualifiedName) ? this.createDefaultNSAttribute() : new NSAttr(this, "http://www.w3.org/2000/xmlns/", qualifiedName));
      } else {
         return new AttrNSImpl(this, namespaceURI, qualifiedName);
      }
   }

   public Attr createAttributeNS(String namespaceURI, String localName, String prefix) throws DOMException {
      if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
         return "xmlns".equals(localName) ? this.createDefaultNSAttribute() : this.createNSAttribute(localName);
      } else {
         return new AttrNSImpl(this, namespaceURI, localName, prefix);
      }
   }

   public Attr createNonNSAttributeNS(String namespaceURI, String localName, String prefix) throws DOMException {
      assert !"http://www.w3.org/2000/xmlns/".equals(namespaceURI);

      assert !"xmlns".equals(localName);

      return new AttrNSImpl(this, namespaceURI, localName, prefix);
   }

   public Attr createNSAttribute(String namespace_prefix) {
      assert !"xmlns".equals(namespace_prefix);

      return new NSAttr(this, namespace_prefix);
   }

   public Attr createDefaultNSAttribute() {
      return new DefaultNSAttr(this);
   }

   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Element getElementById(String elementId) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public String getInputEncoding() {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public String getXmlEncoding() {
      return this.encoding;
   }

   void setXmlEncoding(String enc) {
      this.encoding = enc;
   }

   public boolean getXmlStandalone() {
      return this.isStandalone;
   }

   public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
      this.isStandalone = xmlStandalone;
   }

   public String getXmlVersion() {
      return this.version == null ? "1.0" : this.version;
   }

   public void setXmlVersion(String xmlVersion) throws DOMException {
      if (!"1.0".equals(xmlVersion)) {
         throw new DOMException((short)9, "this dom only supports xml version 1.0");
      } else {
         this.version = "1.0";
      }
   }

   public boolean getStrictErrorChecking() {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void setStrictErrorChecking(boolean strictErrorChecking) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public String getDocumentURI() {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void setDocumentURI(String documentURI) {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Node adoptNode(Node source) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public DOMConfiguration getDomConfig() {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public void normalizeDocument() {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
      throw new AssertionError("UNIMPLEMENTED");
   }

   public CDATASection createCDATASection(String data) throws DOMException {
      return new CDATASectionImpl(this, data);
   }

   public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
      return new ProcessingInstructionImpl(this, target, data);
   }

   public Notation createNotation(String name) throws DOMException {
      if (this.errorChecking && !isXMLName(name)) {
         throw new DOMException((short)5, "INVALID_CHARACTER_ERR");
      } else {
         return new NotationImpl(this, name);
      }
   }

   public Comment createComment(String data) {
      return new CommentImpl(this, data);
   }

   public DocumentFragment createDocumentFragment() {
      return new DocumentFragmentImpl(this);
   }

   public DocumentType createDocumentType(String qualifiedName, String publicID, String systemID) throws DOMException {
      return new DocumentTypeImpl(this, qualifiedName, publicID, systemID);
   }

   public Text createTextNode(String data) {
      return new TextImpl(this, data);
   }

   public DocumentType getDoctype() {
      return null;
   }

   public DOMImplementation getImplementation() {
      return DOMImplementationImpl.getInstance();
   }

   public Element getDocumentElement() {
      return this.docElement;
   }

   public Element createElement(String tagName) throws DOMException {
      return new ElementImpl(this, tagName);
   }

   protected final void checkQName(String prefix, String local) {
   }

   protected final void checkNamespaceWF(String qname, int colon1, int colon2) {
      if (this.errorChecking) {
         if (colon1 == 0 || colon1 == qname.length() - 1 || colon2 != colon1) {
            throw new DOMException((short)14, "NAMESPACE_ERR");
         }
      }
   }

   protected final void checkDOMNSErr(String prefix, String namespace) {
      if (this.errorChecking) {
         if (namespace == null) {
            throw new DOMException((short)14, "NAMESPACE_ERR");
         }

         if (prefix.equals("xml") && !namespace.equals("http://www.w3.org/XML/1998/namespace")) {
            throw new DOMException((short)14, "NAMESPACE_ERR");
         }

         if (prefix.equals("xmlns") && !namespace.equals("http://www.w3.org/2000/xmlns/") || !prefix.equals("xmlns") && namespace.equals("http://www.w3.org/2000/xmlns/")) {
            throw new DOMException((short)14, "NAMESPACE_ERR");
         }
      }

   }

   protected void removeIdentifier(String idName) {
      if (this.identifiers != null) {
         this.identifiers.remove(idName);
      }
   }

   protected void putIdentifier(String idName, Element element) {
      if (element == null) {
         this.removeIdentifier(idName);
      } else {
         if (this.identifiers == null) {
            this.identifiers = new HashMap();
         }

         this.identifiers.put(idName, element);
      }
   }

   protected Element getIdentifier(String idName) {
      if (this.identifiers == null) {
         return null;
      } else {
         Element elem = (Element)this.identifiers.get(idName);
         if (elem != null) {
            for(Node parent = elem.getParentNode(); parent != null; parent = parent.getParentNode()) {
               if (parent == this) {
                  return elem;
               }
            }
         }

         return null;
      }
   }

   private Node importNode(Node source, boolean deep, boolean cloningDoc, Map reversedIdentifiers) throws DOMException {
      Node newnode = null;
      int type = source.getNodeType();
      NamedNodeMap smap;
      int i;
      switch (type) {
         case 1:
            boolean domLevel20 = source.getOwnerDocument().getImplementation().hasFeature("XML", "2.0");
            Object newElement;
            if (domLevel20 && source.getLocalName() != null) {
               newElement = this.createElementNS(source.getNamespaceURI(), source.getLocalName(), source.getPrefix());
            } else {
               newElement = this.createElement(source.getNodeName());
               if (newElement instanceof ElementBase) {
                  ((ElementBase)newElement).setImportedFromNSAwareElement(false);
               }
            }

            smap = source.getAttributes();
            if (smap != null) {
               int length = smap.getLength();

               for(i = 0; i < length; ++i) {
                  Attr attr = (Attr)smap.item(i);
                  if (attr.getSpecified() || cloningDoc) {
                     Attr newAttr = (Attr)this.importNode(attr, true, cloningDoc, reversedIdentifiers);
                     if (domLevel20 && attr.getLocalName() != null) {
                        ((Element)newElement).setAttributeNodeNS(newAttr);
                     } else {
                        ((Element)newElement).setAttributeNode(newAttr);
                     }
                  }
               }
            }

            if (reversedIdentifiers != null) {
               Object elementId = reversedIdentifiers.get(source);
               if (elementId != null) {
                  if (this.identifiers == null) {
                     this.identifiers = new HashMap();
                  }

                  this.identifiers.put(elementId, newElement);
               }
            }

            newnode = newElement;
            break;
         case 2:
            if (source.getOwnerDocument().getImplementation().hasFeature("XML", "2.0")) {
               if (source.getLocalName() == null) {
                  newnode = this.createAttribute(source.getNodeName());
               } else {
                  newnode = this.createAttributeNS(source.getNamespaceURI(), source.getLocalName(), source.getPrefix());
               }
            } else {
               newnode = this.createAttribute(source.getNodeName());
            }

            if (source instanceof AttrImpl) {
               AttrImpl attr = (AttrImpl)source;
               if (attr.hasStringValue()) {
                  AttrImpl newattr = (AttrImpl)newnode;
                  newattr.setValue(attr.getValue());
                  deep = false;
               } else {
                  deep = true;
               }
            } else if (source.getFirstChild() == null) {
               ((Node)newnode).setNodeValue(source.getNodeValue());
               deep = false;
            } else {
               deep = true;
            }
            break;
         case 3:
            newnode = this.createTextNode(source.getNodeValue());
            break;
         case 4:
            newnode = this.createCDATASection(source.getNodeValue());
            break;
         case 5:
            newnode = this.createEntityReference(source.getNodeName());
            deep = false;
            break;
         case 6:
            Entity srcentity = (Entity)source;
            EntityImpl newentity = (EntityImpl)this.createEntity(source.getNodeName());
            newentity.setPublicId(srcentity.getPublicId());
            newentity.setSystemId(srcentity.getSystemId());
            newentity.setNotationName(srcentity.getNotationName());
            newentity.isReadOnly(false);
            newnode = newentity;
            break;
         case 7:
            newnode = this.createProcessingInstruction(source.getNodeName(), source.getNodeValue());
            break;
         case 8:
            newnode = this.createComment(source.getNodeValue());
            break;
         case 9:
         default:
            throw new DOMException((short)9, "NOT_SUPPORTED_ERR");
         case 10:
            if (!cloningDoc) {
               throw new DOMException((short)9, "NOT_SUPPORTED_ERR");
            }

            DocumentType srcdoctype = (DocumentType)source;
            DocumentTypeImpl newdoctype = (DocumentTypeImpl)this.createDocumentType(srcdoctype.getNodeName(), srcdoctype.getPublicId(), srcdoctype.getSystemId());
            smap = srcdoctype.getEntities();
            NamedNodeMap tmap = newdoctype.getEntities();
            if (smap != null) {
               for(i = 0; i < smap.getLength(); ++i) {
                  tmap.setNamedItem(this.importNode(smap.item(i), true, true, reversedIdentifiers));
               }
            }

            smap = srcdoctype.getNotations();
            tmap = newdoctype.getNotations();
            if (smap != null) {
               for(i = 0; i < smap.getLength(); ++i) {
                  tmap.setNamedItem(this.importNode(smap.item(i), true, true, reversedIdentifiers));
               }
            }

            newnode = newdoctype;
            break;
         case 11:
            newnode = this.createDocumentFragment();
            break;
         case 12:
            Notation srcnotation = (Notation)source;
            NotationImpl newnotation = (NotationImpl)this.createNotation(source.getNodeName());
            newnotation.setPublicId(srcnotation.getPublicId());
            newnotation.setSystemId(srcnotation.getSystemId());
            newnode = newnotation;
      }

      if (deep) {
         for(Node srckid = source.getFirstChild(); srckid != null; srckid = srckid.getNextSibling()) {
            ((Node)newnode).appendChild(this.importNode(srckid, true, cloningDoc, reversedIdentifiers));
         }
      }

      if (((Node)newnode).getNodeType() == 6) {
         ((NodeImpl)newnode).setReadOnly(true, true);
      }

      return (Node)newnode;
   }

   public static final boolean isXMLName(String s) {
      return s != null;
   }

   protected ElementNSImpl createAndAppendElement(NodeImpl parent, String uri, String localName, String prefix, int total_atts) {
      assert this == parent.ownerDocument();

      ElementNSImpl el = this.createElementNS(uri, localName, prefix, total_atts);
      parent.appendChild(el);
      return el;
   }

   static {
      kidOK[9] = 1410;
      kidOK[11] = kidOK[6] = kidOK[5] = kidOK[1] = 442;
      kidOK[2] = 40;
      kidOK[10] = kidOK[7] = kidOK[8] = kidOK[3] = kidOK[4] = kidOK[12] = 0;
   }
}
