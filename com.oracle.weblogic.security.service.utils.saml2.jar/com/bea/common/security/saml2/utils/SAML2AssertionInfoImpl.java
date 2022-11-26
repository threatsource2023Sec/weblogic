package com.bea.common.security.saml2.utils;

import com.bea.common.security.ApiLogger;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import org.joda.time.DateTime;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import weblogic.security.utils.SAMLAssertionInfo;

public class SAML2AssertionInfoImpl implements SAMLAssertionInfo {
   private Assertion assertion;
   private Subject subject;
   private SubjectConfirmation subjectConfirmation;

   public SAML2AssertionInfoImpl(String assertionString) throws SAXException {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(SAML2AssertionInfoImpl.class.getClassLoader());

      try {
         ParserPool ppMgr = XMLObjectProviderRegistrySupport.getParserPool();
         Document doc = ppMgr.parse(new StringReader(assertionString));
         Element assertionElement = doc.getDocumentElement();
         this.init(assertionElement);
      } catch (Exception var9) {
         throw new SAXException(ApiLogger.getFailedToGetSAMLAssertionInfo(var9.getMessage()), var9);
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

   }

   public SAML2AssertionInfoImpl(Element assertionElement) throws SAXException {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(SAML2AssertionInfoImpl.class.getClassLoader());

      try {
         this.init(assertionElement);
      } catch (Exception var7) {
         throw new SAXException(ApiLogger.getFailedToGetSAMLAssertionInfo(var7.getMessage()), var7);
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

   }

   public String getId() {
      return this.assertion.getID();
   }

   public String getSubjectName() {
      String name = null;
      if (this.subject != null) {
         NameID nameId = this.subject.getNameID();
         if (nameId != null) {
            name = nameId.getValue();
         }
      }

      return name;
   }

   public String getSubjectConfirmationMethod() {
      return this.subjectConfirmation == null ? null : this.subjectConfirmation.getMethod();
   }

   public Element getSubjectKeyInfo() {
      XMLObject keyInfo = getKeyInfo(this.subjectConfirmation);
      if (keyInfo == null) {
         return null;
      } else {
         Element element = keyInfo.getDOM();
         if (element == null) {
            try {
               element = marshall(keyInfo);
            } catch (MarshallingException var4) {
               element = null;
            }
         }

         return element;
      }
   }

   public String getVersion() {
      return this.assertion.getVersion().toString();
   }

   public Date getNotBefore() {
      Date date = null;
      SubjectConfirmationData confirmData = getSubjectConfirmationData(this.subjectConfirmation);
      if (confirmData != null) {
         date = toDate(confirmData.getNotBefore());
      }

      return date;
   }

   public Date getNotOnOrAfter() {
      Date date = null;
      SubjectConfirmationData confirmData = getSubjectConfirmationData(this.subjectConfirmation);
      if (confirmData != null) {
         date = toDate(confirmData.getNotOnOrAfter());
      }

      return date;
   }

   static Assertion createAssertion(Element assertionElement) throws UnmarshallingException, InitializationException, SAXException, IOException {
      Assertion assertion = null;
      if (assertionElement != null) {
         InitializationService.initialize();
         UnmarshallerFactory factory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
         Unmarshaller unmarshaller = factory.getUnmarshaller(assertionElement);
         assertion = (Assertion)unmarshaller.unmarshall(assertionElement);
         SAML2SchemaValidator.validateElement(assertion.getDOM());
      }

      return assertion;
   }

   static SubjectConfirmation getSubjectConfirmation(Subject subject) {
      SubjectConfirmation confirm = null;
      if (subject != null) {
         List subConfirmList = subject.getSubjectConfirmations();

         for(Iterator subConfirmIter = subConfirmList.iterator(); subConfirmIter.hasNext() && confirm == null; confirm = (SubjectConfirmation)subConfirmIter.next()) {
         }
      }

      return confirm;
   }

   static XMLObject getKeyInfo(SubjectConfirmation subConfirm) {
      if (subConfirm == null) {
         return null;
      } else {
         SubjectConfirmationData confirmData = subConfirm.getSubjectConfirmationData();
         if (confirmData == null) {
            return null;
         } else {
            XMLObject keyInfo = null;
            List unknownXMLObjectList = confirmData.getUnknownXMLObjects();
            Iterator unknownXMLObjectIter = unknownXMLObjectList.iterator();

            while(unknownXMLObjectIter.hasNext() && keyInfo == null) {
               XMLObject xmlObj = (XMLObject)unknownXMLObjectIter.next();
               if (xmlObj instanceof KeyInfo) {
                  keyInfo = xmlObj;
               }
            }

            return keyInfo;
         }
      }
   }

   static Element marshall(XMLObject xmlObj) throws MarshallingException {
      Element element = null;
      if (xmlObj != null) {
         MarshallerFactory marshallerFactory = XMLObjectProviderRegistrySupport.getMarshallerFactory();
         Marshaller marshaller = marshallerFactory.getMarshaller(xmlObj);
         element = marshaller.marshall(xmlObj);
      }

      return element;
   }

   private static SubjectConfirmationData getSubjectConfirmationData(SubjectConfirmation subjectConfirmation) {
      return subjectConfirmation == null ? null : subjectConfirmation.getSubjectConfirmationData();
   }

   private static Date toDate(DateTime dateTime) {
      return dateTime == null ? null : dateTime.toDate();
   }

   private void init(Element assertionElement) throws UnmarshallingException, InitializationException, SAXException, IOException {
      this.assertion = createAssertion(assertionElement);
      this.subject = this.assertion.getSubject();
      this.subjectConfirmation = getSubjectConfirmation(this.subject);
   }
}
