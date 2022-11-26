package org.opensaml;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAuthorityBinding extends SAMLObject implements Cloneable {
   protected String binding = null;
   protected String location = null;
   protected QName authorityKind = null;

   public SAMLAuthorityBinding() {
   }

   public SAMLAuthorityBinding(String var1, String var2, QName var3) throws SAMLException {
      this.binding = var1;
      this.location = var2;
      this.authorityKind = var3;
   }

   public SAMLAuthorityBinding(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAuthorityBinding(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding")) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLAuthorityBinding() requires saml:AuthorityBinding at root");
      } else {
         this.binding = var1.getAttributeNS((String)null, "Binding");
         this.location = var1.getAttributeNS((String)null, "Location");
         this.authorityKind = QName.getQNameAttribute(var1, (String)null, "AuthorityKind");
         this.checkValidity();
      }
   }

   public String getBinding() {
      return this.binding;
   }

   public void setBinding(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("binding cannot be null or empty");
      } else {
         this.binding = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "Binding").setNodeValue(var1);
         }

      }
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("location cannot be null or empty");
      } else {
         this.location = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "Location").setNodeValue(var1);
         }

      }
   }

   public QName getAuthorityKind() {
      return this.authorityKind;
   }

   public void setAuthorityKind(QName var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("authorityKind cannot be null");
      } else {
         this.authorityKind = var1;
         if (this.root != null) {
            if (!"urn:oasis:names:tc:SAML:1.0:protocol".equals(var1.getNamespaceURI())) {
               ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:kind", var1.getNamespaceURI());
               ((Element)this.root).getAttributeNodeNS((String)null, "AuthorityKind").setNodeValue("kind:" + var1.getLocalName());
            } else {
               ((Element)this.root).removeAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:kind");
               ((Element)this.root).getAttributeNodeNS((String)null, "AuthorityKind").setNodeValue("samlp:" + var1.getLocalName());
            }
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
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         var3.setAttributeNS((String)null, "Binding", this.binding);
         var3.setAttributeNS((String)null, "Location", this.location);
         if (!"urn:oasis:names:tc:SAML:1.0:protocol".equals(this.authorityKind.getNamespaceURI())) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:kind", this.authorityKind.getNamespaceURI());
            var3.setAttributeNS((String)null, "AuthorityKind", "kind:" + this.authorityKind.getLocalName());
         } else {
            var3.setAttributeNS((String)null, "AuthorityKind", "samlp:" + this.authorityKind.getLocalName());
         }

         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (XML.isEmpty(this.binding) || XML.isEmpty(this.location) || this.authorityKind == null) {
         throw new MalformedException("AuthorityBinding is invalid, must have Binding, Location, and AuthorityKind");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}
