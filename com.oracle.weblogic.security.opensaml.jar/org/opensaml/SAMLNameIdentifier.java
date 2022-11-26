package org.opensaml;

import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLNameIdentifier extends SAMLObject implements Cloneable {
   protected String name = null;
   protected String nameQualifier = null;
   protected String format = null;
   public static final String FORMAT_UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
   public static final String FORMAT_EMAIL = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
   public static final String FORMAT_X509 = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
   public static final String FORMAT_WINDOWS = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";

   public SAMLNameIdentifier() {
   }

   public SAMLNameIdentifier(String var1, String var2, String var3) throws SAMLException {
      this.name = var1;
      this.nameQualifier = var2;
      this.format = var3;
   }

   public SAMLNameIdentifier(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLNameIdentifier(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier")) {
         throw new MalformedException("SAMLNameIdentifier.fromDOM() requires saml:NameIdentifier at root");
      } else {
         this.nameQualifier = var1.getAttributeNS((String)null, "NameQualifier");
         this.format = var1.getAttributeNS((String)null, "Format");
         this.name = var1.getFirstChild().getNodeValue();
         this.checkValidity();
      }
   }

   public String getName() {
      return this.name;
   }

   public void setName(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("name cannot be empty");
      } else {
         if (this.root != null) {
            this.root.getFirstChild().setNodeValue(var1);
         }

         this.name = var1;
      }
   }

   public String getNameQualifier() {
      return this.nameQualifier;
   }

   public void setNameQualifier(String var1) {
      this.nameQualifier = var1;
      if (this.root != null) {
         ((Element)this.root).removeAttributeNS((String)null, "NameQualifier");
         if (!XML.isEmpty(var1)) {
            ((Element)this.root).setAttributeNS((String)null, "NameQualifier", var1);
         }
      }

   }

   public String getFormat() {
      return this.format;
   }

   public void setFormat(String var1) {
      this.format = var1;
      if (this.root != null) {
         ((Element)this.root).removeAttributeNS((String)null, "Format");
         if (!XML.isEmpty(var1)) {
            ((Element)this.root).setAttributeNS((String)null, "Format", var1);
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
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
         if (!XML.isEmpty(this.nameQualifier)) {
            var3.setAttributeNS((String)null, "NameQualifier", this.nameQualifier);
         }

         if (!XML.isEmpty(this.format)) {
            var3.setAttributeNS((String)null, "Format", this.format);
         }

         var3.appendChild(var1.createTextNode(this.name));
         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (XML.isEmpty(this.name)) {
         throw new MalformedException("NameIdentifier is invalid, requires name");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return (SAMLNameIdentifier)super.clone();
   }
}
