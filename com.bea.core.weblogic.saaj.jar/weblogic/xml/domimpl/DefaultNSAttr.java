package weblogic.xml.domimpl;

import org.w3c.dom.Attr;

final class DefaultNSAttr extends NSAttrBase implements Attr {
   protected DefaultNSAttr(DocumentImpl ownerDocument) {
      super(ownerDocument);
   }

   public boolean definesNamespacePrefix(String prefix) {
      if ("".equals(prefix)) {
         String uri = this.getValue();
         return !"".equals(uri);
      } else {
         return false;
      }
   }

   public String getNodeName() {
      return this.getQualifiedName();
   }

   public String getName() {
      return this.getQualifiedName();
   }

   public String getLocalName() {
      return "xmlns";
   }

   public String getNamespaceURI() {
      return "http://www.w3.org/2000/xmlns/";
   }

   public String getPrefix() {
      return null;
   }

   private String getQualifiedName() {
      return "xmlns";
   }
}
