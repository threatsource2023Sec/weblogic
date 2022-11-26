package org.opensaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAttributeQuery extends SAMLSubjectQuery implements Cloneable {
   protected String resource = null;
   protected ArrayList designators = new ArrayList();

   public SAMLAttributeQuery() {
   }

   public SAMLAttributeQuery(SAMLSubject var1, String var2, Collection var3) throws SAMLException {
      super(var1);
      this.resource = var2;
      if (var3 != null) {
         this.designators.addAll(var3);
      }

   }

   public SAMLAttributeQuery(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAttributeQuery(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQuery")) {
         QName var2 = QName.getQNameAttribute(var1, "http://www.w3.org/2001/XMLSchema-instance", "type");
         if (!XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "Query") || !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "SubjectQuery") || var2 == null || !"urn:oasis:names:tc:SAML:1.0:protocol".equals(var2.getNamespaceURI()) || !"AttributeQueryType".equals(var2.getLocalName())) {
            throw new MalformedException(SAMLException.REQUESTER, "SAMLAttributeQuery() requires samlp:AttributeQuery at root");
         }
      }

      if (var1.hasAttributeNS((String)null, "Resource")) {
         this.resource = var1.getAttributeNS((String)null, "Resource");
      }

      for(Element var3 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator"); var3 != null; var3 = XML.getNextSiblingElement(var3, "urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator")) {
         this.designators.add(new SAMLAttributeDesignator(var3));
      }

   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String var1) {
      this.resource = var1;
      if (this.root != null) {
         ((Element)this.root).removeAttributeNS((String)null, "Resource");
         if (!XML.isEmpty(var1)) {
            ((Element)this.root).setAttributeNS((String)null, "Resource", var1);
         }
      }

   }

   public Iterator getDesignators() {
      return this.designators.iterator();
   }

   public void setDesignators(Collection var1) throws SAMLException {
      while(this.designators.size() > 0) {
         this.removeDesignator(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addDesignator((SAMLAttributeDesignator)var2.next());
         }
      }

   }

   public void addDesignator(SAMLAttributeDesignator var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            var1.checkValidity();
            Element var2 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator");
            if (var2 == null) {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), this.subject.root.getNextSibling());
            } else {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), var2.getNextSibling());
            }
         }

         this.designators.add(var1);
      } else {
         throw new IllegalArgumentException("designator cannot be null");
      }
   }

   public void removeDesignator(int var1) {
      this.designators.remove(var1);
      if (this.root != null) {
         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator"); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2);
         }

         if (var2 == null) {
            throw new IndexOutOfBoundsException();
         }

         this.root.removeChild(var2);
      }

   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      if ((this.root = super.toDOM(var1, var2)) != null) {
         if (var2) {
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:protocol");
         }

         return this.root;
      } else {
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQuery");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:protocol");
         }

         if (this.resource != null && this.resource.length() > 0) {
            var3.setAttributeNS((String)null, "Resource", this.resource);
         }

         var3.appendChild(this.subject.toDOM(var1));
         Iterator var4 = this.designators.iterator();

         while(var4.hasNext()) {
            SAMLAttributeDesignator var5 = (SAMLAttributeDesignator)var4.next();
            var5.checkValidity();
            var3.appendChild(var5.toDOM(var1, true));
         }

         return this.root = var3;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLAttributeQuery var1 = (SAMLAttributeQuery)super.clone();
      Iterator var2 = this.designators.iterator();

      while(var2.hasNext()) {
         var1.designators.add(((SAMLAttributeDesignator)var2.next()).clone());
      }

      return var1;
   }
}
