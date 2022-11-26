package weblogic.security.utils;

import com.bea.common.security.ApiLogger;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Iterator;
import org.opensaml.SAMLAssertion;
import org.opensaml.SAMLException;
import org.opensaml.SAMLNameIdentifier;
import org.opensaml.SAMLSubject;
import org.opensaml.SAMLSubjectStatement;
import org.opensaml.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

class SAMLAssertionInfoImpl implements SAMLAssertionInfo {
   private static final boolean disableValidate = Boolean.getBoolean("weblogic.security.saml.assertioninfo.disableValidate");
   private SAMLAssertion assertion;
   private String version;
   private SAMLSubject subject = null;

   SAMLAssertionInfoImpl(String assertionString) throws SAXException {
      try {
         Document doc = XML.parserPool.parse(new ByteArrayInputStream(assertionString.getBytes()));
         Element assertionElement = doc.getDocumentElement();
         this.init(assertionElement);
      } catch (Exception var4) {
         throw new SAXException(ApiLogger.getFailedToGetSAMLAssertionInfo(var4.getMessage()), var4);
      }
   }

   SAMLAssertionInfoImpl(Element assertionElement) throws SAXException {
      try {
         if (!disableValidate) {
            XML.parserPool.validate(assertionElement);
         }

         this.init(assertionElement);
      } catch (Exception var3) {
         throw new SAXException(ApiLogger.getFailedToGetSAMLAssertionInfo(var3.getMessage()), var3);
      }
   }

   public String getId() {
      return this.assertion.getId();
   }

   public String getSubjectName() {
      String name = null;
      if (this.subject != null) {
         SAMLNameIdentifier nameId = this.subject.getName();
         if (nameId != null) {
            name = nameId.getName();
         }
      }

      return name;
   }

   public String getSubjectConfirmationMethod() {
      String method = null;
      if (this.subject != null) {
         Iterator methods = this.subject.getConfirmationMethods();
         if (methods.hasNext()) {
            method = (String)methods.next();
         }
      }

      return method;
   }

   public Element getSubjectKeyInfo() {
      return this.subject == null ? null : this.subject.getKeyInfo();
   }

   public String getVersion() {
      return this.version;
   }

   public Date getNotBefore() {
      return this.assertion.getNotBefore();
   }

   public Date getNotOnOrAfter() {
      return this.assertion.getNotOnOrAfter();
   }

   private void init(Element assertionElement) throws SAMLException {
      this.assertion = new SAMLAssertion(assertionElement);
      String majorVersion = assertionElement.getAttributeNS((String)null, "MajorVersion");
      String minorVersion = assertionElement.getAttributeNS((String)null, "MinorVersion");
      this.version = majorVersion + "." + minorVersion;
      Iterator stmtItr = this.assertion.getStatements();

      while(stmtItr.hasNext() && this.subject == null) {
         Object stmt = stmtItr.next();
         if (stmt instanceof SAMLSubjectStatement) {
            SAMLSubjectStatement subjStmt = (SAMLSubjectStatement)stmt;
            this.subject = subjStmt.getSubject();
         }
      }

   }
}
