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

public class SAMLAuthenticationStatement extends SAMLSubjectStatement implements Cloneable {
   private static SimpleDateFormatWrapper dateFormatParserMsec = new SimpleDateFormatWrapper(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
   private static SimpleDateFormatWrapper dateFormatParserSec = new SimpleDateFormatWrapper(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
   protected String subjectIP;
   protected String subjectDNS;
   protected String authMethod;
   protected Date authInstant;
   protected ArrayList bindings;
   public static final String AuthenticationMethod_Password = "urn:oasis:names:tc:SAML:1.0:am:password";
   public static final String AuthenticationMethod_Kerberos = "urn:ietf:rfc:1510";
   public static final String AuthenticationMethod_SRP = "urn:ietf:rfc:2945";
   public static final String AuthenticationMethod_HardwareToken = "urn:oasis:names:tc:SAML:1.0:am:HardwareToken";
   public static final String AuthenticationMethod_SSL_TLS_Client = "urn:ietf:rfc:2246";
   public static final String AuthenticationMethod_X509_PublicKey = "urn:oasis:names:tc:SAML:1.0:am:X509-PKI";
   public static final String AuthenticationMethod_PGP_PublicKey = "urn:oasis:names:tc:SAML:1.0:am:PGP";
   public static final String AuthenticationMethod_SPKI_PublicKey = "urn:oasis:names:tc:SAML:1.0:am:SPKI";
   public static final String AuthenticationMethod_XKMS_PublicKey = "urn:oasis:names:tc:SAML:1.0:am:XKMS";
   public static final String AuthenticationMethod_XML_DSig = "urn:ietf:rfc:3075";
   public static final String AuthenticationMethod_Unspecified = "urn:oasis:names:tc:SAML:1.0:am:unspecified";

   public SAMLAuthenticationStatement() {
      this.subjectIP = null;
      this.subjectDNS = null;
      this.authMethod = null;
      this.authInstant = null;
      this.bindings = new ArrayList();
   }

   public SAMLAuthenticationStatement(SAMLSubject var1, String var2, Date var3, String var4, String var5, Collection var6) throws SAMLException {
      super(var1);
      this.subjectIP = null;
      this.subjectDNS = null;
      this.authMethod = null;
      this.authInstant = null;
      this.bindings = new ArrayList();
      this.subjectIP = var4;
      this.subjectDNS = var5;
      this.authMethod = var2;
      this.authInstant = var3;
      if (var6 != null) {
         this.bindings.addAll(var6);
      }

   }

   public SAMLAuthenticationStatement(SAMLSubject var1, Date var2, String var3, String var4, Collection var5) throws SAMLException {
      this(var1, "urn:oasis:names:tc:SAML:1.0:am:unspecified", var2, var3, var4, var5);
   }

   public SAMLAuthenticationStatement(Element var1) throws SAMLException {
      this.subjectIP = null;
      this.subjectDNS = null;
      this.authMethod = null;
      this.authInstant = null;
      this.bindings = new ArrayList();
      this.fromDOM(var1);
   }

   public SAMLAuthenticationStatement(InputStream var1) throws SAMLException {
      this.subjectIP = null;
      this.subjectDNS = null;
      this.authMethod = null;
      this.authInstant = null;
      this.bindings = new ArrayList();
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      QName var2;
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement")) {
         var2 = QName.getQNameAttribute(var1, "http://www.w3.org/2001/XMLSchema-instance", "type");
         if (!XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Statement") || var2 == null || !"urn:oasis:names:tc:SAML:1.0:assertion".equals(var2.getNamespaceURI()) || !"AuthenticationStatementType".equals(var2.getLocalName())) {
            throw new MalformedException(SAMLException.RESPONDER, "SAMLAuthenticationStatement() requires saml:AuthenticationStatement at root");
         }
      }

      this.authMethod = var1.getAttributeNS((String)null, "AuthenticationMethod");

      try {
         var2 = null;
         String var3 = var1.getAttributeNS((String)null, "AuthenticationInstant");
         int var4 = var3.indexOf(46);
         SimpleDateFormatWrapper var6;
         if (var4 > 0) {
            var6 = dateFormatParserMsec;
         } else {
            var6 = dateFormatParserSec;
         }

         this.authInstant = var6.parse(var3);
      } catch (ParseException var5) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLAuthenticationStatement() detected an invalid datetime while parsing statement", var5);
      }

      Element var7 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
      if (var7 != null) {
         this.subjectIP = var7.getAttributeNS((String)null, "IPAddress");
         this.subjectDNS = var7.getAttributeNS((String)null, "DNSAddress");
         var7 = XML.getNextSiblingElement(var7);
      }

      for(var7 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding"); var7 != null; var7 = XML.getNextSiblingElement(var7, "urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding")) {
         this.bindings.add(new SAMLAuthorityBinding(var7));
      }

      this.checkValidity();
   }

   public String getSubjectIP() {
      return this.subjectIP;
   }

   public void setSubjectIP(String var1) {
      this.subjectIP = var1;
      if (this.root != null) {
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
         if (var2 != null) {
            var2.removeAttributeNS((String)null, "IPAddress");
            if (!XML.isEmpty(var1)) {
               var2.setAttributeNS((String)null, "IPAddress", var1);
            } else if (!var2.hasAttributes()) {
               this.root.removeChild(var2);
            }
         } else if (!XML.isEmpty(var1)) {
            var2 = this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
            var2.setAttributeNS((String)null, "IPAddress", var1);
            this.root.insertBefore(var2, this.subject.root.getNextSibling());
         }
      }

   }

   public String getSubjectDNS() {
      return this.subjectDNS;
   }

   public void setSubjectDNS(String var1) {
      this.subjectDNS = var1;
      if (this.root != null) {
         Element var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
         if (var2 != null) {
            var2.removeAttributeNS((String)null, "DNSAddress");
            if (!XML.isEmpty(var1)) {
               var2.setAttributeNS((String)null, "DNSAddress", var1);
            } else if (!var2.hasAttributes()) {
               this.root.removeChild(var2);
            }
         } else if (!XML.isEmpty(var1)) {
            var2 = this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
            var2.setAttributeNS((String)null, "DNSAddress", var1);
            this.root.insertBefore(var2, this.subject.root.getNextSibling());
         }
      }

   }

   public String getAuthMethod() {
      return this.authMethod;
   }

   public void setAuthMethod(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("authMethod cannot be null");
      } else {
         this.authMethod = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "AuthenticationMethod").setNodeValue(var1);
         }

      }
   }

   public Date getAuthInstant() {
      return this.authInstant;
   }

   public void setAuthInstant(Date var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("authInstant cannot be null");
      } else {
         if (this.root != null) {
            SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            var2.setTimeZone(TimeZone.getTimeZone("GMT"));
            ((Element)this.root).getAttributeNodeNS((String)null, "AuthenticationInstant").setNodeValue(var2.format(var1));
         }

         this.authInstant = var1;
      }
   }

   public Iterator getBindings() {
      return this.bindings.iterator();
   }

   public void setBindings(Collection var1) throws SAMLException {
      while(this.bindings.size() > 0) {
         this.removeBinding(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addBinding((SAMLAuthorityBinding)var2.next());
         }
      }

   }

   public void addBinding(SAMLAuthorityBinding var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            Node var2 = var1.toDOM(this.root.getOwnerDocument());
            Element var3 = XML.getLastChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding");
            if (var3 == null) {
               Element var4 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
               if (var4 == null) {
                  this.root.insertBefore(var2, this.subject.root.getNextSibling());
               } else {
                  this.root.insertBefore(var2, var4.getNextSibling());
               }
            } else {
               this.root.insertBefore(var2, var3.getNextSibling());
            }
         }

         this.bindings.add(var1);
      } else {
         throw new IllegalArgumentException("binding cannot be null");
      }
   }

   public void removeBinding(int var1) {
      this.bindings.remove(var1);
      if (this.root != null) {
         Element var2;
         for(var2 = XML.getFirstChildElement(this.root, "urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding"); var2 != null && var1 > 0; --var1) {
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
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         SimpleDateFormat var4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
         var4.setTimeZone(TimeZone.getTimeZone("GMT"));
         var3.setAttributeNS((String)null, "AuthenticationInstant", var4.format(this.authInstant));
         var3.setAttributeNS((String)null, "AuthenticationMethod", this.authMethod);
         var3.appendChild(this.subject.toDOM(var1, false));
         if (!XML.isEmpty(this.subjectIP) || !XML.isEmpty(this.subjectDNS)) {
            Element var5 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
            if (!XML.isEmpty(this.subjectIP)) {
               var5.setAttributeNS((String)null, "IPAddress", this.subjectIP);
            }

            if (!XML.isEmpty(this.subjectDNS)) {
               var5.setAttributeNS((String)null, "DNSAddress", this.subjectDNS);
            }

            var3.appendChild(var5);
         }

         Iterator var6 = this.bindings.iterator();

         while(var6.hasNext()) {
            var3.appendChild(((SAMLAuthorityBinding)var6.next()).toDOM(var1, false));
         }

         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (XML.isEmpty(this.authMethod) || this.authInstant == null) {
         throw new MalformedException(SAMLException.RESPONDER, "AuthenticationStatement is invalid, requires AuthenticationMethod and AuthenticationInstant");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLAuthenticationStatement var1 = (SAMLAuthenticationStatement)super.clone();
      Iterator var2 = this.bindings.iterator();

      while(var2.hasNext()) {
         var1.bindings.add(((SAMLAuthorityBinding)var2.next()).clone());
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
