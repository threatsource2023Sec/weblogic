package org.apache.xml.security.signature;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SignatureProperty extends SignatureElementProxy {
   public SignatureProperty(Document doc, String target) {
      this(doc, target, (String)null);
   }

   public SignatureProperty(Document doc, String target, String id) {
      super(doc);
      this.setTarget(target);
      this.setId(id);
   }

   public SignatureProperty(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public void setId(String id) {
      if (id != null) {
         this.setLocalIdAttribute("Id", id);
      }

   }

   public String getId() {
      return this.getLocalAttribute("Id");
   }

   public void setTarget(String target) {
      if (target != null) {
         this.setLocalAttribute("Target", target);
      }

   }

   public String getTarget() {
      return this.getLocalAttribute("Target");
   }

   public Node appendChild(Node node) {
      this.appendSelf(node);
      return node;
   }

   public String getBaseLocalName() {
      return "SignatureProperty";
   }
}
