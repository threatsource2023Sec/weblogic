package weblogic.xml.domimpl;

import org.w3c.dom.Entity;
import org.w3c.dom.Node;

class EntityImpl extends ParentNode implements Entity {
   protected String name;
   protected String publicId;
   protected String systemId;
   protected String encoding;
   protected String inputEncoding;
   protected String version;
   protected String notationName;
   protected String baseURI;

   public EntityImpl(DocumentImpl ownerDoc, String name) {
      super(ownerDoc);
      this.name = name;
      this.isReadOnly(true);
   }

   public short getNodeType() {
      return 6;
   }

   public String getNodeName() {
      return this.name;
   }

   public Node cloneNode(boolean deep) {
      EntityImpl newentity = (EntityImpl)super.cloneNode(deep);
      newentity.setReadOnly(true, deep);
      return newentity;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public String getXmlVersion() {
      return this.version;
   }

   public String getXmlEncoding() {
      return this.encoding;
   }

   public String getNotationName() {
      return this.notationName;
   }

   public void setPublicId(String id) {
      this.publicId = id;
   }

   public void setXmlEncoding(String value) {
      this.encoding = value;
   }

   public String getInputEncoding() {
      return this.inputEncoding;
   }

   public void setInputEncoding(String inputEncoding) {
      this.inputEncoding = inputEncoding;
   }

   public void setXmlVersion(String value) {
      this.version = value;
   }

   public void setSystemId(String id) {
      this.systemId = id;
   }

   public void setNotationName(String name) {
      this.notationName = name;
   }

   public String getBaseURI() {
      return this.baseURI != null ? this.baseURI : ((DocumentImpl)this.getOwnerDocument()).getBaseURI();
   }

   public void setBaseURI(String uri) {
      this.baseURI = uri;
   }
}
