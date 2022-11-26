package weblogic.xml.saaj.mime4j.message;

import java.io.IOException;
import java.io.OutputStream;
import weblogic.xml.saaj.mime4j.field.ContentTransferEncodingField;
import weblogic.xml.saaj.mime4j.field.ContentTypeField;

public abstract class Entity {
   private Header header = null;
   private Body body = null;
   private Entity parent = null;

   public Entity getParent() {
      return this.parent;
   }

   public void setParent(Entity parent) {
      this.parent = parent;
   }

   public Header getHeader() {
      return this.header;
   }

   public void setHeader(Header header) {
      this.header = header;
   }

   public Body getBody() {
      return this.body;
   }

   public void setBody(Body body) {
      this.body = body;
      body.setParent(this);
   }

   public String getMimeType() {
      ContentTypeField child = (ContentTypeField)this.getHeader().getField("Content-Type");
      ContentTypeField parent = this.getParent() != null ? (ContentTypeField)this.getParent().getHeader().getField("Content-Type") : null;
      return ContentTypeField.getMimeType(child, parent);
   }

   public String getCharset() {
      return ContentTypeField.getCharset((ContentTypeField)this.getHeader().getField("Content-Type"));
   }

   public String getContentTransferEncoding() {
      ContentTransferEncodingField f = (ContentTransferEncodingField)this.getHeader().getField("Content-Transfer-Encoding");
      return ContentTransferEncodingField.getEncoding(f);
   }

   public boolean isMimeType(String type) {
      return this.getMimeType().equalsIgnoreCase(type);
   }

   public boolean isMultipart() {
      ContentTypeField f = (ContentTypeField)this.getHeader().getField("Content-Type");
      return f != null && f.getBoundary() != null && this.getMimeType().startsWith("multipart/");
   }

   public abstract void writeTo(OutputStream var1) throws IOException;
}
