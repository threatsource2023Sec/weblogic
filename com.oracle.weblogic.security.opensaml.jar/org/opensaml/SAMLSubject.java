package org.opensaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLSubject extends SAMLObject implements Cloneable {
   protected SAMLNameIdentifier nameId = null;
   protected ArrayList confirmationMethods = new ArrayList();
   protected Element confirmationData = null;
   protected KeyInfoHolder keyInfo = null;
   public static final String CONF_ARTIFACT = "urn:oasis:names:tc:SAML:1.0:cm:artifact";
   public static final String CONF_BEARER = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
   public static final String CONF_HOLDER_KEY = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
   public static final String CONF_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches";

   public SAMLSubject() {
   }

   public SAMLSubject(SAMLNameIdentifier var1, Collection var2, Element var3, Object var4) throws SAMLException {
      this.nameId = var1;
      this.confirmationData = var3;
      if (var2 != null) {
         this.confirmationMethods.addAll(var2);
      }

      if (var4 != null) {
         if (!(var4 instanceof Element)) {
            throw new MalformedException("SAMLSubject() unable to handle the provided keyInfo type");
         }

         this.keyInfo = new KeyInfoHolder((Element)var4);
      }

   }

   public SAMLSubject(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLSubject(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Subject")) {
         throw new MalformedException("SAMLSubject.fromDOM() requires saml:Subject at root");
      } else {
         Element var2 = XML.getFirstChildElement(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
         if (var2 != null) {
            this.nameId = new SAMLNameIdentifier(var2);
            var2 = XML.getNextSiblingElement(var2);
         }

         if (var2 != null && XML.isElementNamed(var2, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation")) {
            Element var3;
            for(var3 = XML.getFirstChildElement(var2); var3 != null && XML.isElementNamed(var3, "urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod"); var3 = XML.getNextSiblingElement(var3)) {
               this.confirmationMethods.add(var3.getFirstChild().getNodeValue());
            }

            if (var3 != null && XML.isElementNamed(var3, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationData")) {
               this.confirmationData = var3;
               var3 = XML.getNextSiblingElement(var3);
            }

            if (var3 != null && XML.isElementNamed(var3, "http://www.w3.org/2000/09/xmldsig#", "KeyInfo")) {
               this.keyInfo = new KeyInfoHolder(var3);
            }
         }

         this.checkValidity();
      }
   }

   public SAMLNameIdentifier getName() {
      return this.nameId;
   }

   public void setName(SAMLNameIdentifier var1) throws SAMLException {
      if (this.root != null) {
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
         if (var2 != null) {
            this.root.removeChild(var2);
         }

         if (var1 != null) {
            this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), this.root.getFirstChild());
         }
      }

      this.nameId = var1;
   }

   public Iterator getConfirmationMethods() {
      return this.confirmationMethods.iterator();
   }

   public void setConfirmationMethods(Collection var1) {
      while(this.confirmationMethods.size() > 0) {
         this.removeConfirmationMethod(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addConfirmationMethod((String)var2.next());
         }
      }

   }

   public void addConfirmationMethod(String var1) {
      if (!XML.isEmpty(var1)) {
         if (this.root != null) {
            Element var2 = this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod");
            var2.appendChild(this.root.getOwnerDocument().createTextNode(var1));
            Element var3 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
            Element var4;
            if (var3 == null) {
               var4 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
               if (var4 == null) {
                  var3 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation"), this.root.getFirstChild());
               } else {
                  var3 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation"), var4.getNextSibling());
               }

               var3.appendChild(var2);
            } else {
               var4 = XML.getLastChildElement(var3, "urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod");
               if (var4 == null) {
                  var3.insertBefore(var2, var3.getFirstChild());
               } else {
                  var3.insertBefore(var2, var4.getNextSibling());
               }
            }
         }

         this.confirmationMethods.add(var1);
      } else {
         throw new IllegalArgumentException("confirmationMethod cannot be null or empty");
      }
   }

   public void removeConfirmationMethod(int var1) throws IndexOutOfBoundsException {
      this.confirmationMethods.remove(var1);
      if (this.root != null) {
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
         if (this.confirmationMethods.size() == 0 && this.confirmationData == null && this.keyInfo == null) {
            this.root.removeChild(var2);
            return;
         }

         Element var3;
         for(var3 = XML.getFirstChildElement(var2); var3 != null && var1 > 0; --var1) {
            var3 = XML.getNextSiblingElement(var3);
         }

         if (var3 == null) {
            throw new IndexOutOfBoundsException();
         }

         var2.removeChild(var3);
      }

   }

   public Element getConfirmationData() {
      return this.confirmationData;
   }

   public void setConfirmationData(Element var1) {
      if (var1 != null && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationData")) {
         throw new IllegalArgumentException("confirmationData must be a saml:SubjectConfirmationData element");
      } else {
         if (this.root != null) {
            Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
            if (this.confirmationData != null) {
               var2.removeChild(this.confirmationData);
               if (var1 == null && this.keyInfo == null && this.confirmationMethods.size() == 0) {
                  this.root.removeChild(var2);
               }
            }

            if (var1 != null) {
               Element var3;
               if (var2 == null) {
                  var3 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
                  if (var3 == null) {
                     var2 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation"), this.root.getFirstChild());
                  } else {
                     var2 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation"), var3.getNextSibling());
                  }

                  var2.appendChild(this.root.getOwnerDocument().adoptNode(var1));
               } else {
                  var3 = XML.getLastChildElement(var2, "urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod");
                  if (var3 == null) {
                     var2.insertBefore(this.root.getOwnerDocument().adoptNode(var1), var2.getFirstChild());
                  } else {
                     var2.insertBefore(this.root.getOwnerDocument().adoptNode(var1), var3.getNextSibling());
                  }
               }
            }
         }

         this.confirmationData = var1;
      }
   }

   public Element getKeyInfo() {
      return this.keyInfo != null ? this.keyInfo.getElement() : null;
   }

   public Object getNativeKeyInfo() {
      return null;
   }

   public void setKeyInfo(Object var1) throws SAMLException {
      if (var1 != null && !(var1 instanceof Element)) {
         throw new IllegalArgumentException("keyInfo must be a ds:KeyInfo element");
      } else {
         KeyInfoHolder var2 = null;
         if (var1 instanceof Element) {
            if (this.root != null) {
               var2 = new KeyInfoHolder((Element)this.root.getOwnerDocument().adoptNode((Node)var1));
            } else {
               var2 = new KeyInfoHolder((Element)var1);
            }
         }

         if (this.root != null) {
            Element var3 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
            if (this.keyInfo != null) {
               var3.removeChild(this.keyInfo.getElement());
               if (this.confirmationData == null && var1 == null && this.confirmationMethods.size() == 0) {
                  this.root.removeChild(var3);
               }
            }

            if (var1 != null) {
               Element var4;
               if (var3 == null) {
                  var4 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
                  if (var4 == null) {
                     var3 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation"), this.root.getFirstChild());
                  } else {
                     var3 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation"), var4.getNextSibling());
                  }

                  var3.appendChild(var2.getElement());
               } else if (this.confirmationData == null) {
                  var4 = XML.getLastChildElement(var3, "urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod");
                  if (var4 == null) {
                     var3.insertBefore(var2.getElement(), var3.getFirstChild());
                  } else {
                     var3.insertBefore(var2.getElement(), var4.getNextSibling());
                  }
               } else {
                  var3.insertBefore(var2.getElement(), this.confirmationData.getNextSibling());
               }
            }
         }

         this.keyInfo = var2;
      }
   }

   public Node toDOM() throws SAMLException {
      if (this.confirmationData != null) {
         return this.toDOM(this.confirmationData.getOwnerDocument());
      } else {
         return this.keyInfo != null ? this.toDOM(this.keyInfo.getElement().getOwnerDocument()) : super.toDOM();
      }
   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      if ((this.root = super.toDOM(var1, var2)) != null) {
         if (var2) {
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         return this.root;
      } else {
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Subject");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         if (this.nameId != null) {
            var3.appendChild(this.nameId.toDOM(var1, false));
         }

         if (this.confirmationMethods.size() > 0) {
            Element var4 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
            Iterator var5 = this.confirmationMethods.iterator();

            while(var5.hasNext()) {
               var4.appendChild(var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod")).appendChild(var1.createTextNode((String)var5.next()));
            }

            if (this.confirmationData != null) {
               var4.appendChild(var1.adoptNode(this.confirmationData));
            }

            if (this.keyInfo != null) {
               Element var6 = this.keyInfo.getElement();
               Node var7 = var1.adoptNode(var6);
               if (var7 == null) {
                  var7 = var1.importNode(var6, true);
               }

               var4.appendChild(var7);
            }

            var3.appendChild(var4);
         }

         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (this.nameId != null || this.confirmationMethods != null && this.confirmationMethods.size() != 0) {
         if (this.confirmationData != null && !XML.isElementNamed(this.confirmationData, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationData")) {
            throw new MalformedException("Subject is invalid, requires that confirmation data be a saml:SubjectConfirmationData element");
         }
      } else {
         throw new MalformedException("Subject is invalid, requires either NameIdentifier or at least one ConfirmationMethod");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLSubject var1 = (SAMLSubject)super.clone();
      if (this.nameId != null) {
         var1.nameId = (SAMLNameIdentifier)this.nameId.clone();
      }

      var1.confirmationMethods = (ArrayList)this.confirmationMethods.clone();
      if (this.confirmationData != null) {
         var1.confirmationData = (Element)this.confirmationData.cloneNode(true);
      }

      if (this.keyInfo != null) {
         var1.keyInfo = new KeyInfoHolder((Element)this.keyInfo.getElement().cloneNode(true));
      }

      return var1;
   }

   private class KeyInfoHolder {
      private Element element;

      public Element getElement() {
         return this.element;
      }

      KeyInfoHolder(Element var2) {
         this.element = var2;
      }
   }
}
