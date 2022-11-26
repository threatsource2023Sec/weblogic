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

public class SAMLAssertion extends SAMLSignedObject implements Cloneable {
   private static SimpleDateFormatWrapper dateFormatParserMsec = new SimpleDateFormatWrapper(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
   private static SimpleDateFormatWrapper dateFormatParserSec = new SimpleDateFormatWrapper(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
   protected String assertionId = (new SAMLIdentifier()).toString();
   protected String issuer = null;
   protected Date issueInstant = new Date();
   protected Date notBefore = null;
   protected Date notOnOrAfter = null;
   protected ArrayList conditions = new ArrayList();
   protected ArrayList advice = new ArrayList();
   protected ArrayList statements = new ArrayList();

   protected Node insertSignature() throws SAMLException {
      return null;
   }

   public SAMLAssertion() {
   }

   public SAMLAssertion(String var1, Date var2, Date var3, Collection var4, Collection var5, Collection var6) throws SAMLException {
      this.issuer = var1;
      this.notBefore = var2;
      this.notOnOrAfter = var3;
      if (var4 != null) {
         this.conditions.addAll(var4);
      }

      if (var5 != null) {
         this.advice.addAll(var5);
      }

      if (var6 != null) {
         this.statements.addAll(var6);
      }

   }

   public SAMLAssertion(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAssertion(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion")) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLAssertion.fromDOM() requires saml:Assertion at root");
      } else if (Integer.parseInt(var1.getAttributeNS((String)null, "MajorVersion")) != 1) {
         throw new MalformedException(SAMLException.VERSION, "SAMLAssertion.fromDOM() detected incompatible assertion major version of " + var1.getAttributeNS((String)null, "MajorVersion"));
      } else {
         this.issuer = var1.getAttributeNS((String)null, "Issuer");
         this.assertionId = var1.getAttributeNS((String)null, "AssertionID");
         var1.setIdAttributeNode(var1.getAttributeNodeNS((String)null, "AssertionID"), true);

         try {
            SimpleDateFormatWrapper var2 = null;
            String var3 = var1.getAttributeNS((String)null, "IssueInstant");
            var2 = this.getDateFormat(var3);
            this.issueInstant = var2.parse(var3);

            for(Element var4 = XML.getFirstChildElement(var1); var4 != null; var4 = XML.getNextSiblingElement(var4)) {
               Element var5;
               if (XML.isElementNamed(var4, "urn:oasis:names:tc:SAML:1.0:assertion", "Conditions")) {
                  String var7;
                  if (var4.hasAttributeNS((String)null, "NotBefore")) {
                     var7 = var4.getAttributeNS((String)null, "NotBefore");
                     var2 = this.getDateFormat(var7);
                     this.notBefore = var2.parse(var7);
                  }

                  if (var4.hasAttributeNS((String)null, "NotOnOrAfter")) {
                     var7 = var4.getAttributeNS((String)null, "NotOnOrAfter");
                     var2 = this.getDateFormat(var7);
                     this.notOnOrAfter = var2.parse(var7);
                  }

                  for(var5 = XML.getFirstChildElement(var4); var5 != null; var5 = XML.getNextSiblingElement(var5)) {
                     this.conditions.add(SAMLCondition.getInstance(var5));
                  }
               } else if (XML.isElementNamed(var4, "urn:oasis:names:tc:SAML:1.0:assertion", "Advice")) {
                  for(var5 = XML.getFirstChildElement(var4); var5 != null; var5 = XML.getNextSiblingElement(var5)) {
                     if (XML.isElementNamed(var5, "urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference")) {
                        this.advice.add(var5.getFirstChild().getNodeValue());
                     } else if (XML.isElementNamed(var5, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion")) {
                        this.advice.add(new SAMLAssertion(var5));
                     } else {
                        this.advice.add(var5);
                     }
                  }
               } else if (!XML.isElementNamed(var4, "http://www.w3.org/2000/09/xmldsig#", "Signature")) {
                  this.statements.add(SAMLStatement.getInstance(var4));
               }
            }
         } catch (ParseException var6) {
            throw new MalformedException(SAMLException.RESPONDER, "SAMLAssertion.fromDOM() detected an invalid datetime while parsing assertion", var6);
         }

         this.checkValidity();
      }
   }

   private SimpleDateFormatWrapper getDateFormat(String var1) {
      SimpleDateFormatWrapper var2 = null;
      int var3 = var1.indexOf(46);
      if (var3 > 0) {
         var2 = dateFormatParserMsec;
      } else {
         var2 = dateFormatParserSec;
      }

      return var2;
   }

   public String getId() {
      return this.assertionId;
   }

   public void setId(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("id cannot be null");
      } else {
         this.assertionId = var1;
         if (this.root != null) {
            this.unsign();
            ((Element)this.root).getAttributeNodeNS((String)null, "AssertionID").setNodeValue(var1);
         }

      }
   }

   public String getIssuer() {
      return this.issuer;
   }

   public void setIssuer(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("issuer cannot be null");
      } else {
         this.issuer = var1;
         if (this.root != null) {
            this.unsign();
            ((Element)this.root).getAttributeNodeNS((String)null, "Issuer").setNodeValue(var1);
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

   public Date getNotBefore() {
      return this.notBefore;
   }

   public void setNotBefore(Date var1) {
      if (this.root != null) {
         this.unsign();
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
         if (this.notBefore != null) {
            var2.removeAttributeNS((String)null, "NotBefore");
            if (var1 == null && this.notOnOrAfter == null && this.conditions.size() == 0) {
               this.root.removeChild(var2);
            }
         }

         if (var1 != null) {
            if (var2 == null) {
               var2 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions"), this.root.getFirstChild());
            }

            SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            var3.setTimeZone(TimeZone.getTimeZone("GMT"));
            var2.setAttributeNS((String)null, "NotBefore", var3.format(var1));
         }
      }

      this.notBefore = var1;
   }

   public Date getNotOnOrAfter() {
      return this.notOnOrAfter;
   }

   public void setNotOnOrAfter(Date var1) {
      if (this.root != null) {
         this.unsign();
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
         if (this.notOnOrAfter != null) {
            var2.removeAttributeNS((String)null, "NotOnOrAfter");
            if (this.notBefore == null && var1 == null && this.conditions.size() == 0) {
               this.root.removeChild(var2);
            }
         }

         if (var1 != null) {
            if (var2 == null) {
               var2 = (Element)this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions"), this.root.getFirstChild());
            }

            SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            var3.setTimeZone(TimeZone.getTimeZone("GMT"));
            var2.setAttributeNS((String)null, "NotOnOrAfter", var3.format(var1));
         }
      }

      this.notOnOrAfter = var1;
   }

   public Iterator getConditions() {
      return this.conditions.iterator();
   }

   public void setConditions(Collection var1) throws SAMLException {
      while(this.conditions.size() > 0) {
         this.removeCondition(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addCondition((SAMLCondition)var2.next());
         }
      }

   }

   public void addCondition(SAMLCondition var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            this.unsign();
            Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
            if (var2 == null) {
               this.root.insertBefore(this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions"), this.root.getFirstChild());
            }

            var2.appendChild(var1.toDOM(this.root.getOwnerDocument()));
         }

         this.conditions.add(var1);
      } else {
         throw new IllegalArgumentException("c cannot be null");
      }
   }

   public void removeCondition(int var1) throws IndexOutOfBoundsException {
      this.conditions.remove(var1);
      if (this.root != null) {
         this.unsign();
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
         if (this.conditions.size() == 0 && this.notBefore == null && this.notOnOrAfter == null) {
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

   public Iterator getAdvice() {
      return this.advice.iterator();
   }

   public void setAdvice(Collection var1) throws SAMLException {
      while(this.advice.size() > 0) {
         this.removeAdvice(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addAdvice(var2.next());
         }
      }

   }

   public void addAdvice(Object var1) throws SAMLException {
      if (var1 == null || !(var1 instanceof String) && !(var1 instanceof SAMLAssertion) && (!(var1 instanceof Element) || ((Element)var1).getNamespaceURI().equals("urn:oasis:names:tc:SAML:1.0:assertion"))) {
         throw new IllegalArgumentException("SAMLAssertion.addAdvice() can only process Strings, SAMLAssertions, or DOM elements from a non-saml namespace");
      } else {
         if (this.root != null) {
            Document var2 = this.root.getOwnerDocument();
            Element var3 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Advice");
            if (var1 instanceof String && !XML.isEmpty((String)var1)) {
               Element var4 = var2.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
               var4.appendChild(var2.createTextNode((String)var1));
               var3.appendChild(var4);
            } else if (var1 instanceof SAMLAssertion) {
               var3.appendChild(((SAMLAssertion)var1).toDOM(var2));
            } else if (var1 instanceof Element) {
               var3.appendChild(var2.adoptNode((Element)var1));
            }
         }

         this.advice.add(var1);
      }
   }

   public void removeAdvice(int var1) throws IndexOutOfBoundsException {
      this.advice.remove(var1);
      if (this.root != null) {
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Advice");

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

   public Iterator getStatements() {
      return this.statements.iterator();
   }

   public void setStatements(Collection var1) throws SAMLException {
      while(this.statements.size() > 0) {
         this.removeStatement(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addStatement((SAMLStatement)var2.next());
         }
      }

   }

   public void addStatement(SAMLStatement var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            this.unsign();
            if (this.statements.size() > 0) {
               Node var2 = ((SAMLStatement)this.statements.get(this.statements.size() - 1)).root;
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), var2.getNextSibling());
            } else {
               this.root.appendChild(var1.toDOM(this.root.getOwnerDocument()));
            }
         }

         this.statements.add(var1);
      } else {
         throw new IllegalArgumentException("s cannot be null");
      }
   }

   public void removeStatement(int var1) throws IndexOutOfBoundsException {
      this.statements.remove(var1);
      if (this.root != null) {
         this.unsign();
         Element var2 = XML.getFirstChildElement(this.root);
         if (XML.isElementNamed(var2, "urn:oasis:names:tc:SAML:1.0:assertion", "Conditions")) {
            var2 = XML.getNextSiblingElement(var2);
         }

         if (XML.isElementNamed(var2, "urn:oasis:names:tc:SAML:1.0:assertion", "Advice")) {
            var2 = XML.getNextSiblingElement(var2);
         }

         while(var2 != null && var1 > 0) {
            var2 = XML.getNextSiblingElement(var2);
            --var1;
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
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:saml", "urn:oasis:names:tc:SAML:1.0:assertion");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:samlp", "urn:oasis:names:tc:SAML:1.0:protocol");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         }

         return this.root;
      } else if (this.issuer != null && this.issuer.length() != 0 && this.statements != null && this.statements.size() != 0) {
         SimpleDateFormat var3 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
         var3.setTimeZone(TimeZone.getTimeZone("GMT"));
         Element var4 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
         var4.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         var4.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:saml", "urn:oasis:names:tc:SAML:1.0:assertion");
         var4.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:samlp", "urn:oasis:names:tc:SAML:1.0:protocol");
         var4.setAttributeNS((String)null, "MajorVersion", "1");
         var4.setAttributeNS((String)null, "MinorVersion", this.config.getBooleanProperty("org.opensaml.compatibility-mode") ? "0" : "1");
         var4.setAttributeNS((String)null, "AssertionID", this.assertionId);
         var4.setIdAttributeNS((String)null, "AssertionID", true);
         var4.setAttributeNS((String)null, "Issuer", this.issuer);
         var4.setAttributeNS((String)null, "IssueInstant", var3.format(this.issueInstant));
         Element var5;
         Iterator var6;
         if (this.conditions.size() > 0 || this.notBefore != null || this.notOnOrAfter != null) {
            var5 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
            if (this.notBefore != null) {
               var5.setAttributeNS((String)null, "NotBefore", var3.format(this.notBefore));
            }

            if (this.notOnOrAfter != null) {
               var5.setAttributeNS((String)null, "NotOnOrAfter", var3.format(this.notOnOrAfter));
            }

            var4.appendChild(var5);
            var6 = this.conditions.iterator();

            while(var6.hasNext()) {
               var5.appendChild(((SAMLCondition)var6.next()).toDOM(var1, false));
            }
         }

         if (this.advice.size() > 0) {
            var5 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Advice");
            var6 = this.advice.iterator();

            while(true) {
               while(var6.hasNext()) {
                  Object var7 = var6.next();
                  if (var7 instanceof String && !XML.isEmpty((String)var7)) {
                     Element var8 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
                     var8.appendChild(var1.createTextNode((String)var7));
                     var5.appendChild(var8);
                  } else if (var7 instanceof SAMLAssertion) {
                     var5.appendChild(((SAMLAssertion)var7).toDOM(var1, false));
                  } else if (var7 instanceof Element) {
                     var5.appendChild(var1.adoptNode((Element)var7));
                  }
               }

               var4.appendChild(var5);
               break;
            }
         }

         Iterator var9 = this.statements.iterator();

         while(var9.hasNext()) {
            var4.appendChild(((SAMLStatement)var9.next()).toDOM(var1, false));
         }

         return this.root = var4;
      } else {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLAssertion.toDOM() requires issuer and at least one statement");
      }
   }

   public void checkValidity() throws SAMLException {
      if (XML.isEmpty(this.issuer) || this.statements.size() == 0) {
         throw new MalformedException("Assertion is invalid, must have Issuer and at least one Statement");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLAssertion var1 = (SAMLAssertion)super.clone();
      Iterator var2 = this.conditions.iterator();

      while(var2.hasNext()) {
         var1.conditions.add(((SAMLCondition)var2.next()).clone());
      }

      var2 = this.advice.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof String) {
            var1.advice.add(var3);
         } else if (var3 instanceof SAMLAssertion) {
            var1.advice.add(((SAMLAssertion)var3).clone());
         } else {
            var1.advice.add(((Element)var3).cloneNode(true));
         }
      }

      var2 = this.statements.iterator();

      while(var2.hasNext()) {
         var1.statements.add(((SAMLStatement)var2.next()).clone());
      }

      return var1;
   }

   private static class SimpleDateFormatWrapper {
      SimpleDateFormat formatter;

      SimpleDateFormatWrapper(SimpleDateFormat var1) {
         this.formatter = var1;
         this.formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
      }

      synchronized Date parse(String var1) throws ParseException {
         return this.formatter.parse(var1);
      }
   }
}
