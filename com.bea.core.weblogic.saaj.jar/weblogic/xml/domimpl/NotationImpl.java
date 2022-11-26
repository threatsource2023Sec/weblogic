package weblogic.xml.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Notation;

class NotationImpl extends ParentNode implements Notation {
   protected String name;
   protected String publicId;
   protected String systemId;
   protected String baseURI;

   public NotationImpl(DocumentImpl ownerDoc, String name) {
      super(ownerDoc);
      this.name = name;
   }

   public short getNodeType() {
      return 12;
   }

   public String getNodeName() {
      return this.name;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public void setPublicId(String id) {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         this.publicId = id;
      }
   }

   public void setSystemId(String id) {
      if (this.isReadOnly()) {
         throw new DOMException((short)7, "NO_MODIFICATION_ALLOWED_ERR");
      } else {
         this.systemId = id;
      }
   }

   public String getBaseURI() {
      return this.baseURI;
   }

   public void setBaseURI(String uri) {
      this.baseURI = uri;
   }
}
