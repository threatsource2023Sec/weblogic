package weblogic.xml.domimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

public class ElementNSImpl extends ElementBase implements Element {
   private static final long serialVersionUID = 800389733522015460L;
   private final String namespaceURI;
   private final String localName;
   private final String prefix;

   protected ElementNSImpl(DocumentImpl ownerDocument, String namespaceURI, String qualifiedName) throws DOMException {
      super(ownerDocument);
      this.namespaceURI = fixEmptyNamespaceURI(namespaceURI);
      String msg;
      if (qualifiedName == null) {
         msg = "NAMESPACE_ERR";
         throw new DOMException((short)14, msg);
      } else {
         int colon1 = qualifiedName.indexOf(58);
         int colon2 = qualifiedName.lastIndexOf(58);
         this.ownerDocument().checkNamespaceWF(qualifiedName, colon1, colon2);
         if (colon1 < 0) {
            this.localName = qualifiedName;
            this.prefix = null;
            this.ownerDocument().checkQName((String)null, this.localName);
            if (qualifiedName.equals("xmlns") && (this.namespaceURI == null || !this.namespaceURI.equals("http://www.w3.org/2000/xmlns/")) || this.namespaceURI != null && this.namespaceURI.equals("http://www.w3.org/2000/xmlns/") && !qualifiedName.equals("xmlns")) {
               msg = "NAMESPACE_ERR";
               throw new DOMException((short)14, msg);
            }
         } else {
            String pfx = qualifiedName.substring(0, colon1);
            if (this.namespaceURI == null || pfx.equals("xml") && !this.namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
               msg = "NAMESPACE_ERR";
               throw new DOMException((short)14, msg);
            }

            this.localName = qualifiedName.substring(colon2 + 1);
            this.prefix = pfx;
            this.ownerDocument().checkQName(pfx, this.localName);
            this.ownerDocument().checkDOMNSErr(pfx, this.namespaceURI);
         }

      }
   }

   public void declareNonDefaultNamespace(String prefix, String uri) {
      assert uri != null;

      assert prefix != null && prefix.length() > 0;

      Attr ns_att = this.ownerDocument().createNSAttribute(prefix);
      ns_att.setValue(uri);
      this.setAttributeNodeNS(ns_att);
   }

   protected ElementNSImpl(DocumentImpl ownerDocument, String namespaceURI, String localName, String prefix) throws DOMException {
      super(ownerDocument);
      this.namespaceURI = fixEmptyNamespaceURI(namespaceURI);
      this.localName = localName;
      this.prefix = prefix;
   }

   protected ElementNSImpl(DocumentImpl ownerDocument, String namespaceURI, String localName, String prefix, int num_attrs) throws DOMException {
      super(ownerDocument, num_attrs);
      this.namespaceURI = fixEmptyNamespaceURI(namespaceURI);
      this.localName = localName;
      this.prefix = prefix;
   }

   public Attr createNamespaceAttr() {
      Attr ns_att = this.prefix != null && this.prefix.length() > 0 ? this.ownerDocument().createNSAttribute(this.prefix) : this.ownerDocument().createDefaultNSAttribute();
      ns_att.setValue(this.namespaceURI);
      return ns_att;
   }

   public final String getNodeName() {
      return this.getQualifiedName();
   }

   private String getQualifiedName() {
      return this.prefix != null && this.prefix.length() != 0 ? this.prefix + ":" + this.localName : this.localName;
   }

   public final String getLocalName() {
      return this.localName;
   }

   public final String getNamespaceURI() {
      return this.namespaceURI;
   }

   public final String getPrefix() {
      return this.prefix;
   }

   public final String getTagName() {
      return this.getQualifiedName();
   }

   public String toString() {
      return this.prefix != null ? this.prefix + ":" + this.localName : (this.namespaceURI != null ? "{" + this.namespaceURI + "}" + this.localName : this.localName);
   }
}
