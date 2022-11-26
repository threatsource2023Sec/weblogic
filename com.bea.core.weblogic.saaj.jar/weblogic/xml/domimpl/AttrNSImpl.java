package weblogic.xml.domimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;

final class AttrNSImpl extends AttrBase implements Attr {
   protected final String namespaceURI;
   protected final String localName;
   protected String prefix;

   protected AttrNSImpl(DocumentImpl ownerDocument, String namespace_uri, String qualifiedName) {
      super(ownerDocument);
      namespace_uri = fixEmptyNamespaceURI(namespace_uri);
      this.namespaceURI = namespace_uri;
      int colon1 = qualifiedName.indexOf(58);
      int colon2 = qualifiedName.lastIndexOf(58);
      this.ownerDocument().checkNamespaceWF(qualifiedName, colon1, colon2);
      if (colon1 < 0) {
         this.localName = qualifiedName;
         this.prefix = null;
         this.ownerDocument().checkQName((String)null, this.localName);
         if (this.ownerDocument().errorChecking && (qualifiedName.equals("xmlns") && (namespace_uri == null || !namespace_uri.equals("http://www.w3.org/2000/xmlns/")) || namespace_uri != null && namespace_uri.equals("http://www.w3.org/2000/xmlns/") && !qualifiedName.equals("xmlns"))) {
            throw new DOMException((short)14, "NAMESPACE_ERR");
         }
      } else {
         String pfx = qualifiedName.substring(0, colon1);
         this.localName = qualifiedName.substring(colon2 + 1);
         this.ownerDocument().checkQName(pfx, this.localName);
         this.ownerDocument().checkDOMNSErr(pfx, namespace_uri);
         this.prefix = pfx;
      }

   }

   protected AttrNSImpl(DocumentImpl ownerDocument, String namespaceURI, String localName, String prefix) {
      super(ownerDocument);
      this.namespaceURI = fixEmptyNamespaceURI(namespaceURI);
      this.localName = localName;
      this.prefix = prefix;
   }

   public String getNodeName() {
      return this.getQualifiedName();
   }

   public String getName() {
      return this.getQualifiedName();
   }

   public String getLocalName() {
      return this.localName;
   }

   public String getNamespaceURI() {
      return this.namespaceURI;
   }

   public String getPrefix() {
      return this.prefix;
   }

   private String getQualifiedName() {
      return this.prefix != null && this.prefix.length() != 0 ? this.prefix + ":" + this.localName : this.localName;
   }

   public boolean isNamespaceAttribute() {
      assert !"http://www.w3.org/2000/xmlns/".equals(this.getNamespaceURI());

      return false;
   }
}
