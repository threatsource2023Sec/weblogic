package org.opensaml.saml.saml2.core.impl;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.xmlsec.signature.Signature;
import org.w3c.dom.Attr;

public abstract class RequestAbstractTypeUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      RequestAbstractType req = (RequestAbstractType)parentSAMLObject;
      if (childSAMLObject instanceof Issuer) {
         req.setIssuer((Issuer)childSAMLObject);
      } else if (childSAMLObject instanceof Signature) {
         req.setSignature((Signature)childSAMLObject);
      } else if (childSAMLObject instanceof Extensions) {
         req.setExtensions((Extensions)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      RequestAbstractType req = (RequestAbstractType)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("Version")) {
            req.setVersion(this.parseSAMLVersion(attribute));
         } else if (attribute.getLocalName().equals("ID")) {
            req.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
         } else if (attribute.getLocalName().equals("IssueInstant") && !Strings.isNullOrEmpty(attribute.getValue())) {
            req.setIssueInstant(new DateTime(attribute.getValue(), ISOChronology.getInstanceUTC()));
         } else if (attribute.getLocalName().equals("Destination")) {
            req.setDestination(attribute.getValue());
         } else if (attribute.getLocalName().equals("Consent")) {
            req.setConsent(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
