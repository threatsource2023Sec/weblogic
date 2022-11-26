package org.apache.xml.security.keys.content;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.Signature11ElementProxy;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KeyInfoReference extends Signature11ElementProxy implements KeyInfoContent {
   public KeyInfoReference(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public KeyInfoReference(Document doc, String uri) {
      super(doc);
      this.setLocalAttribute("URI", uri);
   }

   public Attr getURIAttr() {
      return this.getElement().getAttributeNodeNS((String)null, "URI");
   }

   public String getURI() {
      return this.getURIAttr().getNodeValue();
   }

   public void setId(String id) {
      this.setLocalIdAttribute("Id", id);
   }

   public String getId() {
      return this.getLocalAttribute("Id");
   }

   public String getBaseLocalName() {
      return "KeyInfoReference";
   }
}
