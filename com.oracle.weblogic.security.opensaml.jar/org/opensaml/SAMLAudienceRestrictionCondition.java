package org.opensaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAudienceRestrictionCondition extends SAMLCondition implements Cloneable {
   protected ArrayList audiences = new ArrayList();

   public SAMLAudienceRestrictionCondition() {
   }

   public SAMLAudienceRestrictionCondition(Collection var1) throws SAMLException {
      if (var1 != null) {
         this.audiences.addAll(var1);
      }

   }

   public SAMLAudienceRestrictionCondition(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAudienceRestrictionCondition(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition")) {
         QName var2 = QName.getQNameAttribute(var1, "http://www.w3.org/2001/XMLSchema-instance", "type");
         if (!XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Condition") || var2 == null || !"urn:oasis:names:tc:SAML:1.0:assertion".equals(var2.getNamespaceURI()) || !"AudienceRestrictionConditionType".equals(var2.getLocalName())) {
            throw new MalformedException(SAMLException.RESPONDER, "SAMLAudienceRestrictionCondition() requires saml:AudienceRestrictionCondition at root");
         }
      }

      for(Element var4 = XML.getFirstChildElement(var1); var4 != null; var4 = XML.getNextSiblingElement(var4)) {
         String var3 = var4.getFirstChild().getNodeValue();
         if (!XML.isEmpty(var3)) {
            this.audiences.add(var3);
         }
      }

      this.checkValidity();
   }

   public Iterator getAudiences() {
      return this.audiences.iterator();
   }

   public void setAudiences(Collection var1) {
      while(this.audiences.size() > 0) {
         this.removeAudience(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addAudience((String)var2.next());
         }
      }

   }

   public void addAudience(String var1) {
      if (!XML.isEmpty(var1)) {
         if (this.root != null) {
            Element var2 = this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Audience");
            var2.appendChild(this.root.getOwnerDocument().createTextNode(var1));
            Element var3 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Audience");
            if (var3 == null) {
               this.root.insertBefore(var2, this.root.getFirstChild());
            } else {
               this.root.insertBefore(var2, var3.getNextSibling());
            }
         }

         this.audiences.add(var1);
      } else {
         throw new IllegalArgumentException("audience cannot be null or empty");
      }
   }

   public void removeAudience(int var1) {
      this.audiences.remove(var1);
      if (this.root != null) {
         Element var2;
         for(var2 = XML.getFirstChildElement(this.root); var2 != null && var1 > 0; --var1) {
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
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         return this.root;
      } else {
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         Iterator var4 = this.audiences.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if (!XML.isEmpty(var5)) {
               var3.appendChild(var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Audience")).appendChild(var1.createTextNode(var5));
            }
         }

         return this.root = var3;
      }
   }

   public boolean eval(Collection var1) {
      if (var1 != null && var1.size() != 0) {
         Iterator var2 = var1.iterator();

         do {
            if (!var2.hasNext()) {
               return false;
            }
         } while(!this.audiences.contains(var2.next()));

         return true;
      } else {
         return false;
      }
   }

   public void checkValidity() throws SAMLException {
      if (this.audiences == null || this.audiences.size() == 0) {
         throw new MalformedException(SAMLException.RESPONDER, "AudienceRestrictionCondition is invalid, requires at least one audience");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLAudienceRestrictionCondition var1 = (SAMLAudienceRestrictionCondition)super.clone();
      var1.audiences = (ArrayList)this.audiences.clone();
      return var1;
   }
}
