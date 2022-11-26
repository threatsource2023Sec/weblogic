package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.opensaml.saml.saml2.core.Scoping;
import org.opensaml.saml.saml2.core.Subject;
import org.w3c.dom.Attr;

public class AuthnRequestUnmarshaller extends RequestAbstractTypeUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AuthnRequest req = (AuthnRequest)parentSAMLObject;
      if (childSAMLObject instanceof Subject) {
         req.setSubject((Subject)childSAMLObject);
      } else if (childSAMLObject instanceof NameIDPolicy) {
         req.setNameIDPolicy((NameIDPolicy)childSAMLObject);
      } else if (childSAMLObject instanceof Conditions) {
         req.setConditions((Conditions)childSAMLObject);
      } else if (childSAMLObject instanceof RequestedAuthnContext) {
         req.setRequestedAuthnContext((RequestedAuthnContext)childSAMLObject);
      } else if (childSAMLObject instanceof Scoping) {
         req.setScoping((Scoping)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthnRequest req = (AuthnRequest)samlObject;
      if (attribute.getNamespaceURI() == null) {
         if (attribute.getLocalName().equals("ForceAuthn")) {
            req.setForceAuthn(XSBooleanValue.valueOf(attribute.getValue()));
         } else if (attribute.getLocalName().equals("IsPassive")) {
            req.setIsPassive(XSBooleanValue.valueOf(attribute.getValue()));
         } else if (attribute.getLocalName().equals("ProtocolBinding")) {
            req.setProtocolBinding(attribute.getValue());
         } else if (attribute.getLocalName().equals("AssertionConsumerServiceIndex")) {
            req.setAssertionConsumerServiceIndex(Integer.valueOf(attribute.getValue()));
         } else if (attribute.getLocalName().equals("AssertionConsumerServiceURL")) {
            req.setAssertionConsumerServiceURL(attribute.getValue());
         } else if (attribute.getLocalName().equals("AttributeConsumingServiceIndex")) {
            req.setAttributeConsumingServiceIndex(Integer.valueOf(attribute.getValue()));
         } else if (attribute.getLocalName().equals("ProviderName")) {
            req.setProviderName(attribute.getValue());
         } else {
            super.processAttribute(samlObject, attribute);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
