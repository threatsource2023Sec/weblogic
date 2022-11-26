package org.opensaml;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLResponse extends SAMLSignedObject {
   protected String responseId = (new SAMLIdentifier()).toString();
   protected String inResponseTo = null;
   protected Date issueInstant = new Date();
   protected String recipient = null;
   protected ArrayList assertions = new ArrayList();
   protected SAMLException e = null;

   protected Node insertSignature() throws SAMLException {
      return this.root.getFirstChild();
   }

   public SAMLResponse() {
   }

   public SAMLResponse(String var1, String var2, Collection var3, SAMLException var4) throws SAMLException {
      this.inResponseTo = var1;
      this.recipient = var2;
      this.e = var4;
      if (var3 != null) {
         this.assertions.addAll(var3);
      }

   }

   public SAMLResponse(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLResponse(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "Response")) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLResponse.fromDOM() requires samlp:Response at root");
      } else if (Integer.parseInt(var1.getAttributeNS((String)null, "MajorVersion")) != 1) {
         throw new MalformedException(SAMLException.VERSION, "SAMLResponse() detected incompatible response major version of " + var1.getAttributeNS((String)null, "MajorVersion"));
      } else {
         this.responseId = var1.getAttributeNS((String)null, "ResponseID");
         var1.setIdAttributeNode(var1.getAttributeNodeNS((String)null, "ResponseID"), true);
         this.inResponseTo = var1.getAttributeNS((String)null, "InResponseTo");
         this.recipient = var1.getAttributeNS((String)null, "Recipient");

         try {
            SimpleDateFormat var2 = null;
            String var3 = var1.getAttributeNS((String)null, "IssueInstant");
            int var4 = var3.indexOf(46);
            if (var4 > 0) {
               var2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            } else {
               var2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            }

            var2.setTimeZone(TimeZone.getTimeZone("GMT"));
            this.issueInstant = var2.parse(var3);
         } catch (ParseException var5) {
            throw new MalformedException(SAMLException.RESPONDER, "SAMLResponse() detected an invalid datetime while parsing response", var5);
         }

         Element var6 = XML.getFirstChildElement(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "Status");
         this.e = SAMLException.getInstance(var6);
         Iterator var7 = this.e.getCodes();
         if (var7.hasNext() && !var7.next().equals(SAMLException.SUCCESS)) {
            throw this.e;
         } else {
            for(var6 = XML.getNextSiblingElement(var6, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion"); var6 != null; var6 = XML.getNextSiblingElement(var6, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion")) {
               this.assertions.add(new SAMLAssertion(var6));
            }

         }
      }
   }

   public String getId() {
      return this.responseId;
   }

   public void setId(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("id cannot be null");
      } else {
         this.responseId = var1;
         if (this.root != null) {
            this.unsign();
            ((Element)this.root).getAttributeNodeNS((String)null, "ResponseID").setNodeValue(var1);
         }

      }
   }

   public String getInResponseTo() {
      return this.inResponseTo;
   }

   public void setInResponseTo(String var1) {
      this.inResponseTo = var1;
      if (this.root != null) {
         ((Element)this.root).removeAttributeNS((String)null, "InResponseTo");
         if (!XML.isEmpty(var1)) {
            ((Element)this.root).setAttributeNS((String)null, "InResponseTo", var1);
         }
      }

   }

   public Date getIssueInstant() {
      return this.issueInstant;
   }

   public void setIssueInstant(Date var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("issueInstant cannot be null");
      } else {
         if (this.root != null) {
            this.unsign();
            SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            var2.setTimeZone(TimeZone.getTimeZone("GMT"));
            ((Element)this.root).getAttributeNodeNS((String)null, "IssueInstant").setNodeValue(var2.format(var1));
         }

         this.issueInstant = var1;
      }
   }

   public String getRecipient() {
      return this.recipient;
   }

   public void setRecipient(String var1) {
      this.recipient = var1;
      if (this.root != null) {
         ((Element)this.root).removeAttributeNS((String)null, "Recipient");
         if (!XML.isEmpty(var1)) {
            ((Element)this.root).setAttributeNS((String)null, "Recipient", var1);
         }
      }

   }

   public Iterator getAssertions() {
      return this.assertions.iterator();
   }

   public void setAssertions(Collection var1) throws SAMLException {
      while(this.assertions.size() > 0) {
         this.removeAssertion(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addAssertion((SAMLAssertion)var2.next());
         }
      }

   }

   public void addAssertion(SAMLAssertion var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            this.unsign();
            Element var2 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
            if (var2 == null) {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:protocol", "Status").getNextSibling());
            } else {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), var2.getNextSibling());
            }
         }

         this.assertions.add(var1);
      } else {
         throw new IllegalArgumentException("assertion cannot be null");
      }
   }

   public void removeAssertion(int var1) throws IndexOutOfBoundsException {
      this.assertions.remove(var1);
      if (this.root != null) {
         this.unsign();

         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion"); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
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
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:saml", "urn:oasis:names:tc:SAML:1.0:assertion");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:samlp", "urn:oasis:names:tc:SAML:1.0:protocol");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
         }

         return this.root;
      } else {
         SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
         var3.setTimeZone(TimeZone.getTimeZone("GMT"));
         Element var4 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "Response");
         if (var2) {
            var4.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:protocol");
         }

         var4.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:saml", "urn:oasis:names:tc:SAML:1.0:assertion");
         var4.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:samlp", "urn:oasis:names:tc:SAML:1.0:protocol");
         var4.setAttributeNS((String)null, "MajorVersion", "1");
         var4.setAttributeNS((String)null, "MinorVersion", this.config.getBooleanProperty("org.opensaml.compatibility-mode") ? "0" : "1");
         var4.setAttributeNS((String)null, "ResponseID", this.responseId);
         var4.setIdAttributeNS((String)null, "ResponseID", true);
         if (!XML.isEmpty(this.inResponseTo)) {
            var4.setAttributeNS((String)null, "InResponseTo", this.inResponseTo);
         }

         var4.setAttributeNS((String)null, "IssueInstant", var3.format(this.issueInstant));
         if (!XML.isEmpty(this.recipient)) {
            var4.setAttributeNS((String)null, "Recipient", this.recipient);
         }

         if (this.e != null) {
            var4.appendChild(this.e.toDOM(var1, false));
         } else {
            Element var5 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "Status");
            Element var6 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode");
            var6.setAttributeNS((String)null, "Value", "samlp:" + SAMLException.SUCCESS.getLocalName());
            var5.appendChild(var6);
            var4.appendChild(var5);
         }

         Iterator var7 = this.assertions.iterator();

         while(var7.hasNext()) {
            var4.appendChild(((SAMLAssertion)var7.next()).toDOM(var1));
         }

         return this.root = var4;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLResponse var1 = (SAMLResponse)super.clone();
      if (this.e != null) {
         var1.e = (SAMLException)this.e.clone();
      }

      Iterator var2 = this.assertions.iterator();

      while(var2.hasNext()) {
         var1.assertions.add(((SAMLAssertion)var2.next()).clone());
      }

      return var1;
   }
}
