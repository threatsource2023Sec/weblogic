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
import org.w3c.dom.Text;

public class SAMLRequest extends SAMLSignedObject implements Cloneable {
   protected String requestId = (new SAMLIdentifier()).toString();
   protected Date issueInstant = new Date();
   protected ArrayList respondWiths = new ArrayList();
   protected SAMLQuery query = null;
   protected ArrayList assertionIdRefs = new ArrayList();
   protected ArrayList artifacts = new ArrayList();

   protected Node insertSignature() throws SAMLException {
      Element var1;
      for(var1 = XML.getFirstChildElement(this.root); var1 != null && XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith"); var1 = XML.getNextSiblingElement(var1)) {
      }

      return var1;
   }

   public SAMLRequest() {
   }

   public SAMLRequest(Collection var1, SAMLQuery var2, Collection var3, Collection var4) throws SAMLException {
      this.query = var2;
      if (var3 != null) {
         this.assertionIdRefs.addAll(var3);
      }

      if (var4 != null) {
         this.artifacts.addAll(var4);
      }

      if (var1 != null) {
         this.respondWiths.addAll(var1);
      }

   }

   public SAMLRequest(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLRequest(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "Request")) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLRequest.fromDOM() requires samlp:Request at root");
      } else if (Integer.parseInt(var1.getAttributeNS((String)null, "MajorVersion")) != 1) {
         throw new MalformedException(SAMLException.VERSION, "SAMLRequest() detected incompatible request major version of " + var1.getAttributeNS((String)null, "MajorVersion"));
      } else {
         this.requestId = var1.getAttributeNS((String)null, "RequestID");
         var1.setIdAttributeNode(var1.getAttributeNodeNS((String)null, "RequestID"), true);

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
            throw new MalformedException(SAMLException.REQUESTER, "SAMLRequest() detected an invalid datetime while parsing request", var5);
         }

         Element var6;
         for(var6 = XML.getFirstChildElement(var1); var6 != null && XML.isElementNamed(var6, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith"); var6 = XML.getNextSiblingElement(var6)) {
            this.respondWiths.add(QName.getQNameTextNode((Text)var6.getFirstChild()));
         }

         if (XML.isElementNamed(var6, "http://www.w3.org/2000/09/xmldsig#", "Signature")) {
            var6 = XML.getNextSiblingElement(var6);
         }

         if (XML.isElementNamed(var6, "urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference")) {
            while(var6 != null) {
               this.assertionIdRefs.add(var6.getFirstChild().getNodeValue());
               var6 = XML.getNextSiblingElement(var6, "urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
            }
         } else if (XML.isElementNamed(var6, "urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact")) {
            while(var6 != null) {
               this.artifacts.add(var6.getFirstChild().getNodeValue());
               var6 = XML.getNextSiblingElement(var6, "urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact");
            }
         } else {
            this.query = SAMLQuery.getInstance(var6);
         }

         this.checkValidity();
      }
   }

   public String getId() {
      return this.requestId;
   }

   public void setId(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("id cannot be null");
      } else {
         this.requestId = var1;
         if (this.root != null) {
            this.unsign();
            ((Element)this.root).getAttributeNodeNS((String)null, "RequestID").setNodeValue(var1);
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

   public Iterator getRespondWiths() {
      return this.respondWiths.iterator();
   }

   public void setRespondWiths(Collection var1) {
      while(this.respondWiths.size() > 0) {
         this.removeRespondWith(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addRespondWith((QName)var2.next());
         }
      }

   }

   public void addRespondWith(QName var1) {
      if (var1 != null) {
         if (this.root != null) {
            this.unsign();
            Element var2 = this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith");
            String var3 = var1.getNamespaceURI();
            if (var3 == null) {
               var3 = "";
            }

            if (!"urn:oasis:names:tc:SAML:1.0:assertion".equals(var3)) {
               var2.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:rw", var3);
               var3 = "rw:";
            } else {
               var3 = "saml:";
            }

            var2.appendChild(this.root.getOwnerDocument().createTextNode(var3 + var1.getLocalName()));
            Element var4 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith");
            if (var4 == null) {
               this.root.insertBefore(var2, this.root.getFirstChild());
            } else {
               this.root.insertBefore(var2, var4.getNextSibling());
            }
         }

         this.respondWiths.add(var1);
      } else {
         throw new IllegalArgumentException("respondWith cannot be null");
      }
   }

   public void removeRespondWith(int var1) throws IndexOutOfBoundsException {
      this.respondWiths.remove(var1);
      if (this.root != null) {
         this.unsign();

         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith"); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith");
         }

         if (var2 == null) {
            throw new IndexOutOfBoundsException();
         }

         this.root.removeChild(var2);
      }

   }

   public SAMLQuery getQuery() {
      return this.query;
   }

   public void setQuery(SAMLQuery var1) throws SAMLException {
      if (var1 != null) {
         this.setAssertionIdRefs((Collection)null);
         this.setArtifacts((Collection)null);
      }

      if (this.root != null) {
         this.unsign();
         Element var2 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith");
         if (var1 != null) {
            if (this.query != null) {
               this.root.replaceChild(var1.toDOM(this.root.getOwnerDocument()), this.query.root);
            } else if (var2 == null) {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), this.root.getFirstChild());
            } else {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), var2.getNextSibling());
            }
         } else {
            this.root.removeChild(this.query.root);
         }
      }

      this.query = var1;
   }

   public Iterator getAssertionIdRefs() {
      return this.assertionIdRefs.iterator();
   }

   public void addAssertionIdRef(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("ref cannot be null or empty");
      } else {
         try {
            this.setQuery((SAMLQuery)null);
            this.setArtifacts((Collection)null);
         } catch (SAMLException var5) {
         }

         if (this.root != null) {
            this.unsign();
            Document var2 = this.root.getOwnerDocument();
            Element var3 = var2.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "saml:AssertionIDReference");
            var3.appendChild(var2.createTextNode(var1));
            Element var4 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith");
            if (var4 == null) {
               this.root.insertBefore(var3, this.root.getFirstChild());
            } else {
               this.root.insertBefore(var3, var4.getNextSibling());
            }
         }

         this.assertionIdRefs.add(var1);
      }
   }

   public void setAssertionIdRefs(Collection var1) {
      while(this.assertionIdRefs.size() > 0) {
         this.removeAssertionIdRef(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addAssertionIdRef((String)var2.next());
         }
      }

   }

   public void removeAssertionIdRef(int var1) throws IndexOutOfBoundsException {
      this.assertionIdRefs.remove(var1);
      if (this.root != null) {
         this.unsign();

         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference"); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2, "urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
         }

         if (var2 == null) {
            throw new IndexOutOfBoundsException();
         }

         this.root.removeChild(var2);
      }

   }

   public Iterator getArtifacts() {
      return this.artifacts.iterator();
   }

   public void setArtifacts(Collection var1) {
      while(this.artifacts.size() > 0) {
         this.removeArtifact(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addArtifact((String)var2.next());
         }
      }

   }

   public void addArtifact(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("artifact cannot be null or empty");
      } else {
         try {
            this.setQuery((SAMLQuery)null);
            this.setAssertionIdRefs((Collection)null);
         } catch (SAMLException var5) {
         }

         if (this.root != null) {
            this.unsign();
            Document var2 = this.root.getOwnerDocument();
            Element var3 = var2.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact");
            var3.appendChild(var2.createTextNode(var1));
            Element var4 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith");
            if (var4 == null) {
               this.root.insertBefore(var3, this.root.getFirstChild());
            } else {
               this.root.insertBefore(var3, var4.getNextSibling());
            }
         }

         this.artifacts.add(var1);
      }
   }

   public void removeArtifact(int var1) throws IndexOutOfBoundsException {
      this.artifacts.remove(var1);
      if (this.root != null) {
         this.unsign();

         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact"); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2, "urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact");
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
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "Request");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:protocol");
         }

         var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:samlp", "urn:oasis:names:tc:SAML:1.0:protocol");
         var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:saml", "urn:oasis:names:tc:SAML:1.0:assertion");
         var3.setAttributeNS((String)null, "MajorVersion", "1");
         var3.setAttributeNS((String)null, "MinorVersion", this.config.getBooleanProperty("org.opensaml.compatibility-mode") ? "0" : "1");
         var3.setAttributeNS((String)null, "RequestID", this.requestId);
         var3.setIdAttributeNS((String)null, "RequestID", true);
         SimpleDateFormat var4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
         var4.setTimeZone(TimeZone.getTimeZone("GMT"));
         var3.setAttributeNS((String)null, "IssueInstant", var4.format(this.issueInstant));
         Iterator var5 = this.respondWiths.iterator();

         while(var5.hasNext()) {
            QName var6 = (QName)var5.next();
            Element var7 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith");
            String var8 = var6.getNamespaceURI();
            if (var8 == null) {
               var8 = "";
            }

            if (!"urn:oasis:names:tc:SAML:1.0:assertion".equals(var8)) {
               var7.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:rw", var8);
               var8 = "rw:";
            } else {
               var8 = "saml:";
            }

            var7.appendChild(var1.createTextNode(var8 + var6.getLocalName()));
            var3.appendChild(var7);
         }

         if (this.query != null) {
            var3.appendChild(this.query.toDOM(var1, false));
         } else if (this.assertionIdRefs.size() > 0) {
            var5 = this.assertionIdRefs.iterator();

            while(var5.hasNext()) {
               var3.appendChild(var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "saml:AssertionIDReference")).appendChild(var1.createTextNode((String)var5.next()));
            }
         } else {
            var5 = this.artifacts.iterator();

            while(var5.hasNext()) {
               var3.appendChild(var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact")).appendChild(var1.createTextNode((String)var5.next()));
            }
         }

         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (this.query == null && this.assertionIdRefs.size() == 0 && this.artifacts.size() == 0) {
         throw new MalformedException("Request is invalid, must have Query, assertion references, or artifacts");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLRequest var1 = (SAMLRequest)super.clone();
      var1.respondWiths = (ArrayList)this.respondWiths.clone();
      var1.query = (SAMLQuery)this.query.clone();
      var1.assertionIdRefs = (ArrayList)this.assertionIdRefs.clone();
      var1.artifacts = (ArrayList)this.artifacts.clone();
      return var1;
   }
}
