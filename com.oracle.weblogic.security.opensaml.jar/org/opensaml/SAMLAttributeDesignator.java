package org.opensaml;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAttributeDesignator extends SAMLObject implements Cloneable {
   protected String name = null;
   protected String namespace = null;

   public SAMLAttributeDesignator() {
   }

   public SAMLAttributeDesignator(String var1, String var2) throws SAMLException {
      this.name = var1;
      this.namespace = var2;
   }

   public SAMLAttributeDesignator(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAttributeDesignator(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator")) {
         throw new MalformedException("SAMLAttributeDesignator.fromDOM() requires saml:AttributeDesignator at root");
      } else {
         this.name = var1.getAttributeNS((String)null, "AttributeName");
         this.namespace = var1.getAttributeNS((String)null, "AttributeNamespace");
         this.checkValidity();
      }
   }

   public String getName() {
      return this.name;
   }

   public void setName(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("name cannot be null");
      } else {
         this.name = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "AttributeName").setNodeValue(var1);
         }

      }
   }

   public String getNamespace() {
      return this.namespace;
   }

   public void setNamespace(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("namespace cannot be null");
      } else {
         this.namespace = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "AttributeNamespace").setNodeValue(var1);
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
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         var3.setAttributeNS((String)null, "AttributeName", this.name);
         var3.setAttributeNS((String)null, "AttributeNamespace", this.namespace);
         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (XML.isEmpty(this.name) || XML.isEmpty(this.namespace)) {
         throw new MalformedException(SAMLException.RESPONDER, "AttributeDesignator invalid, requires name and namespace");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return (SAMLAttributeDesignator)super.clone();
   }
}
