package org.opensaml;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLDoNotCacheCondition extends SAMLCondition implements Cloneable {
   public SAMLDoNotCacheCondition() {
   }

   public SAMLDoNotCacheCondition(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLDoNotCacheCondition(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition")) {
         QName var2 = QName.getQNameAttribute(var1, "http://www.w3.org/2001/XMLSchema-instance", "type");
         if (!XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Condition") || var2 == null || !"urn:oasis:names:tc:SAML:1.0:assertion".equals(var2.getNamespaceURI()) || !"DoNotCacheConditionType".equals(var2.getLocalName())) {
            throw new MalformedException(SAMLException.RESPONDER, "SAMLDoNotCacheCondition() requires saml:DoNotCacheCondition at root");
         }
      }

   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      if ((this.root = super.toDOM(var1, var2)) != null) {
         if (var2) {
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         return this.root;
      } else {
         this.root = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition");
         if (var2) {
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         return this.root;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}
