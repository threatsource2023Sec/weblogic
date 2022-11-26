package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Advice;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class AssertionUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   public XMLObject unmarshall(Element domElement) throws UnmarshallingException {
      Assertion assertion = (Assertion)super.unmarshall(domElement);
      if (assertion.getMinorVersion() != 0 && !Strings.isNullOrEmpty(assertion.getID())) {
         domElement.setIdAttributeNS((String)null, "AssertionID", true);
      }

      return assertion;
   }

   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      Assertion assertion = (Assertion)parentSAMLObject;
      if (childSAMLObject instanceof Signature) {
         assertion.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof Conditions) {
         assertion.setConditions((Conditions)childSAMLObject);
      } else if (childSAMLObject instanceof Advice) {
         assertion.setAdvice((Advice)childSAMLObject);
      } else if (childSAMLObject instanceof Statement) {
         assertion.getStatements().add((Statement)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Assertion assertion = (Assertion)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if ("AssertionID".equals(attribute.getLocalName())) {
            assertion.setID(attribute.getValue());
         } else if ("Issuer".equals(attribute.getLocalName())) {
            assertion.setIssuer(attribute.getValue());
         } else if ("IssueInstant".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
            assertion.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else {
            int minor;
            if ("MajorVersion".equals(attribute.getLocalName())) {
               try {
                  minor = Integer.parseInt(attribute.getValue());
                  if (minor != 1) {
                     throw new UnmarshallingException("MajorVersion was invalid, must be 1");
                  }
               } catch (NumberFormatException var7) {
                  throw new UnmarshallingException(var7);
               }
            } else if ("MinorVersion".equals(attribute.getLocalName())) {
               try {
                  minor = Integer.parseInt(attribute.getValue());
               } catch (NumberFormatException var6) {
                  throw new UnmarshallingException(var6);
               }

               if (minor == 0) {
                  assertion.setVersion(SAMLVersion.VERSION_10);
               } else if (minor == 1) {
                  assertion.setVersion(SAMLVersion.VERSION_11);
               }
            } else {
               super.processAttribute(samlObject, attribute);
            }
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
