package org.apache.xml.security.signature;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ObjectContainer extends SignatureElementProxy {
   public ObjectContainer(Document doc) {
      super(doc);
   }

   public ObjectContainer(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public void setId(String Id) {
      if (Id != null) {
         this.setLocalIdAttribute("Id", Id);
      }

   }

   public String getId() {
      return this.getLocalAttribute("Id");
   }

   public void setMimeType(String MimeType) {
      if (MimeType != null) {
         this.setLocalAttribute("MimeType", MimeType);
      }

   }

   public String getMimeType() {
      return this.getLocalAttribute("MimeType");
   }

   public void setEncoding(String Encoding) {
      if (Encoding != null) {
         this.setLocalAttribute("Encoding", Encoding);
      }

   }

   public String getEncoding() {
      return this.getLocalAttribute("Encoding");
   }

   public Node appendChild(Node node) {
      this.appendSelf(node);
      return node;
   }

   public String getBaseLocalName() {
      return "Object";
   }
}
