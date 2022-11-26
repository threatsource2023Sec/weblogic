package org.opensaml.saml.saml2.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.Status;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

public abstract class StatusResponseTypeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      StatusResponseType sr = (StatusResponseType)parentSAMLObject;
      if (childSAMLObject instanceof Issuer) {
         sr.setIssuer((Issuer)childSAMLObject);
      } else if (childSAMLObject instanceof Signature) {
         sr.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof Extensions) {
         sr.setExtensions((Extensions)childSAMLObject);
      } else if (childSAMLObject instanceof Status) {
         sr.setStatus((Status)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      StatusResponseType sr = (StatusResponseType)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Version")) {
            sr.setVersion(this.parseSAMLVersion(attribute));
         } else if (attribute.getLocalName().equals("ID")) {
            sr.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
         } else if (attribute.getLocalName().equals("InResponseTo")) {
            sr.setInResponseTo(attribute.getValue());
         } else if (attribute.getLocalName().equals("IssueInstant") && !Strings.isNullOrEmpty(attribute.getValue())) {
            sr.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("Destination")) {
            sr.setDestination(attribute.getValue());
         } else if (attribute.getLocalName().equals("Consent")) {
            sr.setConsent(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
