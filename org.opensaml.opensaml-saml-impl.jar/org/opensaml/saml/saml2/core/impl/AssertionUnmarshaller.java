package org.opensaml.saml.saml2.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Statement;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

public class AssertionUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      Assertion assertion = (Assertion)parentObject;
      if (childObject instanceof Issuer) {
         assertion.setIssuer((Issuer)childObject);
      } else if (childObject instanceof Signature) {
         assertion.setSignature((Signature)childObject);
      } else if (childObject instanceof Subject) {
         assertion.setSubject((Subject)childObject);
      } else if (childObject instanceof Conditions) {
         assertion.setConditions((Conditions)childObject);
      } else if (childObject instanceof Advice) {
         assertion.setAdvice((Advice)childObject);
      } else if (childObject instanceof Statement) {
         assertion.getStatements().add((Statement)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Assertion assertion = (Assertion)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Version")) {
            assertion.setVersion(this.parseSAMLVersion(attribute));
         } else if (attribute.getLocalName().equals("IssueInstant") && !Strings.isNullOrEmpty(attribute.getValue())) {
            assertion.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("ID")) {
            assertion.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
