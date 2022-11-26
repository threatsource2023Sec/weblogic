package org.opensaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SAMLAuthorizationDecisionStatement extends SAMLSubjectStatement implements Cloneable {
   protected String resource = null;
   protected String decision = null;
   protected ArrayList actions = new ArrayList();
   protected ArrayList evidence = new ArrayList();

   public SAMLAuthorizationDecisionStatement() {
   }

   public SAMLAuthorizationDecisionStatement(SAMLSubject var1, String var2, String var3, Collection var4, Collection var5) throws SAMLException {
      super(var1);
      this.resource = var2;
      this.decision = var3;
      this.actions.addAll(var4);
      if (var5 != null) {
         this.evidence.addAll(var5);
      }

   }

   public SAMLAuthorizationDecisionStatement(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAuthorizationDecisionStatement(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement")) {
         QName var2 = QName.getQNameAttribute(var1, "http://www.w3.org/2001/XMLSchema-instance", "type");
         if (!XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Statement") || !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement") || var2 == null || !"urn:oasis:names:tc:SAML:1.0:assertion".equals(var2.getNamespaceURI()) || !"AuthorizationDecisionStatementType".equals(var2.getLocalName())) {
            throw new MalformedException(SAMLException.REQUESTER, "SAMLAuthorizationDecisionStatement.fromDOM() requires saml:AuthorizationDecisionStatement at root");
         }
      }

      this.resource = var1.getAttributeNS((String)null, "Resource");
      this.decision = var1.getAttributeNS((String)null, "Decision");

      Element var4;
      for(var4 = XML.getFirstChildElement(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Action"); var4 != null; var4 = XML.getNextSiblingElement(var4, "urn:oasis:names:tc:SAML:1.0:assertion", "Action")) {
         this.actions.add(new SAMLAction(var4));
      }

      var4 = XML.getFirstChildElement(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Evidence");
      if (var4 != null) {
         for(Element var3 = XML.getFirstChildElement(var4); var3 != null; var3 = XML.getNextSiblingElement(var3)) {
            if (XML.isElementNamed(var3, "urn:oasis:names:tc:SAML:1.0:assertion", "Assertion")) {
               this.evidence.add(new SAMLAssertion(var3));
            } else if (XML.isElementNamed(var3, "urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference")) {
               this.evidence.add(var3.getFirstChild().getNodeValue());
            }
         }
      }

      this.checkValidity();
   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("resource cannot be null");
      } else {
         this.resource = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "Resource").setNodeValue(var1);
         }

      }
   }

   public String getDecision() {
      return this.decision;
   }

   public void setDecision(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("decision cannot be null");
      } else {
         this.decision = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "Decision").setNodeValue(var1);
         }

      }
   }

   public Iterator getActions() {
      return this.actions.iterator();
   }

   public void setActions(Collection var1) throws SAMLException {
      while(this.actions.size() > 0) {
         this.removeAction(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addAction((SAMLAction)var2.next());
         }
      }

   }

   public void addAction(SAMLAction var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            Element var2 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Action");
            if (var2 == null) {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), this.subject.root.getNextSibling());
            } else {
               this.root.insertBefore(var1.toDOM(this.root.getOwnerDocument()), var2.getNextSibling());
            }
         }

         this.actions.add(var1);
      } else {
         throw new IllegalArgumentException("action cannot be null");
      }
   }

   public void removeAction(int var1) {
      this.actions.remove(var1);
      if (this.root != null) {
         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Action"); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2);
         }

         if (var2 == null) {
            throw new IndexOutOfBoundsException();
         }

         this.root.removeChild(var2);
      }

      if (this.actions.size() == 0) {
         logDebug("all actions have been removed, statement is in an illegal state");
      }

   }

   public Iterator getEvidence() {
      return this.evidence.iterator();
   }

   public void setEvidence(Collection var1) throws SAMLException {
      while(this.evidence.size() > 0) {
         this.removeEvidence(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addEvidence(var2.next());
         }
      }

   }

   public void addEvidence(Object var1) throws SAMLException {
      if (var1 == null || !(var1 instanceof String) && !(var1 instanceof SAMLAssertion)) {
         throw new IllegalArgumentException("can only add Strings or SAMLAssertions");
      } else {
         if (this.root != null) {
            Document var2 = this.root.getOwnerDocument();
            Element var3 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Evidence");
            if (var3 == null) {
               var3 = var2.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence");
               if (this.actions.size() == 0) {
                  this.root.insertBefore(var3, this.subject.root.getNextSibling());
               } else {
                  this.root.insertBefore(var3, ((SAMLAction)this.actions.get(this.actions.size() - 1)).root.getNextSibling());
               }
            }

            if (var1 instanceof String && !XML.isEmpty((String)var1)) {
               Element var4 = var2.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
               var4.appendChild(var2.createTextNode((String)var1));
               var3.appendChild(var4);
            } else if (var1 instanceof SAMLAssertion) {
               var3.appendChild(((SAMLAssertion)var1).toDOM(var2));
            }
         }

         this.evidence.add(var1);
      }
   }

   public void removeEvidence(int var1) throws IndexOutOfBoundsException {
      this.evidence.remove(var1);
      if (this.root != null) {
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "Evidence");

         Element var3;
         for(var3 = XML.getFirstChildElement(var2); var3 != null && var1 > 0; --var1) {
            var3 = XML.getNextSiblingElement(var3);
         }

         if (var3 == null) {
            throw new IndexOutOfBoundsException();
         }

         var2.removeChild(var3);
         if (this.evidence.size() == 0) {
            this.root.removeChild(var2);
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
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         var3.setAttributeNS((String)null, "Resource", this.resource);
         var3.setAttributeNS((String)null, "Decision", this.decision);
         var3.appendChild(this.subject.toDOM(var1, false));
         Iterator var4 = this.actions.iterator();

         while(var4.hasNext()) {
            var3.appendChild(((SAMLAction)var4.next()).toDOM(var1, false));
         }

         if (this.evidence.size() > 0) {
            Element var5 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence");
            var4 = this.evidence.iterator();

            while(var4.hasNext()) {
               Object var6 = var4.next();
               if (var6 instanceof SAMLAssertion) {
                  var5.appendChild(((SAMLAssertion)var6).toDOM(var1, false));
               } else if (var6 instanceof String && !XML.isEmpty((String)var6)) {
                  var5.appendChild(var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference")).appendChild(var1.createTextNode((String)var6));
               }
            }

            var3.appendChild(var5);
         }

         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      super.checkValidity();
      if (XML.isEmpty(this.resource) || XML.isEmpty(this.decision) || this.actions.size() == 0) {
         throw new MalformedException("AuthorizationDecisionStatement is invalid, must have Resource, Decision, and at least one Action");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLAuthorizationDecisionStatement var1 = (SAMLAuthorizationDecisionStatement)super.clone();
      Iterator var2 = this.actions.iterator();

      while(var2.hasNext()) {
         var1.actions.add(((SAMLAction)var2.next()).clone());
      }

      var2 = this.evidence.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof SAMLAssertion) {
            var1.evidence.add(((SAMLAssertion)var3).clone());
         } else if (var3 instanceof String) {
            var1.evidence.add(var3);
         }
      }

      return var1;
   }
}
