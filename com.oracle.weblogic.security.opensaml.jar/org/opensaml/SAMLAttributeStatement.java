package org.opensaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAttributeStatement extends SAMLSubjectStatement implements Cloneable {
   protected ArrayList attrs = new ArrayList();

   public SAMLAttributeStatement() {
   }

   public SAMLAttributeStatement(SAMLSubject var1, Collection var2) throws SAMLException {
      super(var1);
      this.attrs.addAll(var2);
   }

   public SAMLAttributeStatement(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAttributeStatement(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement")) {
         QName var2 = QName.getQNameAttribute(var1, "http://www.w3.org/2001/XMLSchema-instance", "type");
         if (!XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Statement") || !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement") || var2 == null || !"urn:oasis:names:tc:SAML:1.0:assertion".equals(var2.getNamespaceURI()) || !"AttributeStatementType".equals(var2.getLocalName())) {
            throw new MalformedException(SAMLException.REQUESTER, "SAMLAttributeStatement() requires saml:AttributeStatement at root");
         }
      }

      for(Element var5 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Attribute"); var5 != null; var5 = XML.getNextSiblingElement(var5, "urn:oasis:names:tc:SAML:1.0:assertion", "Attribute")) {
         try {
            this.attrs.add(SAMLAttribute.getInstance(var5));
         } catch (SAMLException var4) {
            logDebug("exception while instantiating a SAMLAttribute: " + var4.getMessage());
         }
      }

   }

   public Iterator getAttributes() {
      return this.attrs.iterator();
   }

   public void setAttributes(Collection var1) throws SAMLException {
      while(this.attrs.size() > 0) {
         this.removeAttribute(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addAttribute((SAMLAttribute)var2.next());
         }
      }

   }

   public void addAttribute(SAMLAttribute var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            Element var2 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Attribute");
            if (var2 == null) {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), this.subject.root.getNextSibling());
            } else {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), var2.getNextSibling());
            }
         }

         this.attrs.add(var1);
      } else {
         throw new IllegalArgumentException("attribute cannot be null");
      }
   }

   public void removeAttribute(int var1) {
      this.attrs.remove(var1);
      if (this.root != null) {
         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Attribute"); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2);
         }

         if (var2 == null) {
            throw new IndexOutOfBoundsException();
         }

         this.root.removeChild(var2);
      }

      if (this.attrs.size() == 0) {
         logDebug("all attributes have been removed, statement is in an illegal state");
      }

   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      if ((this.root = super.toDOM(var1, var2)) != null) {
         if (var2) {
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         }

         return this.root;
      } else {
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         var3.appendChild(this.subject.toDOM(var1, false));
         Iterator var4 = this.attrs.iterator();

         while(var4.hasNext()) {
            var3.appendChild(((SAMLAttribute)var4.next()).toDOM(var1, false));
         }

         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (this.attrs == null || this.attrs.size() == 0) {
         throw new MalformedException(SAMLException.RESPONDER, "AttributeStatement is invalid, requires at least one attribute");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLAttributeStatement var1 = (SAMLAttributeStatement)super.clone();
      Iterator var2 = this.attrs.iterator();

      while(var2.hasNext()) {
         var1.attrs.add(((SAMLAttribute)var2.next()).clone());
      }

      return var1;
   }
}
