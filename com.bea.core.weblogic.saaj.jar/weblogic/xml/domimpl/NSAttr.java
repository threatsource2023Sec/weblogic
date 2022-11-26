package weblogic.xml.domimpl;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;

final class NSAttr extends NSAttrBase implements Attr {
   protected final String ns_prefix;

   protected NSAttr(DocumentImpl ownerDocument, String namespace_uri, String qualifiedName) {
      super(ownerDocument);

      assert "http://www.w3.org/2000/xmlns/".equals(namespace_uri);

      assert !"xmlns".equals(qualifiedName);

      int colon1 = qualifiedName.indexOf(58);
      int colon2 = qualifiedName.lastIndexOf(58);
      this.ownerDocument().checkNamespaceWF(qualifiedName, colon1, colon2);
      if (colon1 < 0) {
         throw new DOMException((short)14, "NAMESPACE_ERR");
      } else {
         assert "xmlns".equals(qualifiedName.substring(0, colon1));

         this.ns_prefix = qualifiedName.substring(colon2 + 1);

         assert !"xmlns".equals(this.ns_prefix);

         assert this.ns_prefix != null;

         this.ownerDocument().checkQName("xmlns", this.ns_prefix);
         this.ownerDocument().checkDOMNSErr("xmlns", namespace_uri);
      }
   }

   protected NSAttr(DocumentImpl ownerDocument, String namespace_prefix) {
      super(ownerDocument);
      this.ns_prefix = namespace_prefix;

      assert !"xmlns".equals(this.ns_prefix);

      assert this.ns_prefix != null;

   }

   public boolean definesNamespacePrefix(String prefix) {
      return this.ns_prefix.equals(prefix);
   }

   public String getNodeName() {
      return this.getQualifiedName();
   }

   public String getName() {
      return this.getQualifiedName();
   }

   public String getLocalName() {
      return this.ns_prefix;
   }

   public String getNamespaceURI() {
      return "http://www.w3.org/2000/xmlns/";
   }

   public String getPrefix() {
      return "xmlns";
   }

   private String getQualifiedName() {
      return "xmlns:" + this.ns_prefix;
   }
}
