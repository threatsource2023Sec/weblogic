package org.opensaml;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAction extends SAMLObject implements Cloneable {
   public static final String SAML_ACTION_NAMESPACE_RWEDC = "urn:oasis:names:tc:SAML:1.0:action:rwedc";
   public static final String SAML_ACTION_NAMESPACE_RWEDC_NEG = "urn:oasis:names:tc:SAML:1.0:action:rwedc-negation";
   public static final String SAML_ACTION_NAMESPACE_GHPP = "urn:oasis:names:tc:SAML:1.0:action:ghpp";
   public static final String SAML_ACTION_NAMESPACE_UNIX = "urn:oasis:names:tc:SAML:1.0:action:unix";
   private String namespace = null;
   private String data = null;

   public SAMLAction() {
   }

   public SAMLAction(String var1, String var2) throws SAMLException {
      this.namespace = var1;
      this.data = var2;
   }

   public SAMLAction(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAction(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Action")) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLAction() requires saml:Action at root");
      } else {
         this.namespace = var1.getAttributeNS((String)null, "Namespace");
         this.data = var1.getFirstChild().getNodeValue();
         this.checkValidity();
      }
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String getData() {
      return this.data;
   }

   public void setNamespace(String var1) {
      if (this.root != null) {
         if (!XML.isEmpty(var1)) {
            ((Element)this.root).setAttributeNS((String)null, "Namespace", var1);
         } else if (!XML.isEmpty(this.namespace)) {
            ((Element)this.root).removeAttributeNS((String)null, "Namespace");
         }
      }

      this.namespace = var1;
   }

   public void setData(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("data cannot be null or empty");
      } else {
         this.data = var1;
         if (this.root != null) {
            this.root.getFirstChild().setNodeValue(var1);
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
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Action");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         if (!XML.isEmpty(this.namespace)) {
            var3.setAttributeNS((String)null, "Namespace", this.namespace);
         }

         var3.appendChild(var1.createTextNode(this.data));
         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (XML.isEmpty(this.data)) {
         throw new MalformedException("Action is invalid, data must have a value");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }
}
