package org.opensaml;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAuthenticationQuery extends SAMLSubjectQuery implements Cloneable {
   protected String authMethod = null;

   public SAMLAuthenticationQuery() {
   }

   public SAMLAuthenticationQuery(SAMLSubject var1, String var2) throws SAMLException {
      super(var1);
      this.authMethod = var2;
   }

   public SAMLAuthenticationQuery(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAuthenticationQuery(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery")) {
         QName var2 = QName.getQNameAttribute(var1, "http://www.w3.org/2001/XMLSchema-instance", "type");
         if (!XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "Query") || !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "SubjectQuery") || var2 == null || !"urn:oasis:names:tc:SAML:1.0:protocol".equals(var2.getNamespaceURI()) || !"AuthenticationQueryType".equals(var2.getLocalName())) {
            throw new MalformedException(SAMLException.REQUESTER, "SAMLAuthenticationQuery.fromDOM() requires samlp:AuthenticationQuery at root");
         }
      }

      this.authMethod = var1.getAttributeNS((String)null, "AuthenticationMethod");
      this.checkValidity();
   }

   public String getAuthMethod() {
      return this.authMethod;
   }

   public void setAuthMethod(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("authMethod cannot be null or empty");
      } else {
         this.authMethod = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "AuthenticationMethod").setNodeValue(var1);
         }

      }
   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      if ((this.root = super.toDOM(var1, var2)) != null) {
         if (var2) {
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:protocol");
         }

         return this.root;
      } else {
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:protocol");
         }

         if (!XML.isEmpty(this.authMethod)) {
            var3.setAttributeNS((String)null, "AuthenticationMethod", this.authMethod);
         }

         var3.appendChild(this.subject.toDOM(var1));
         return this.root = var3;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}
