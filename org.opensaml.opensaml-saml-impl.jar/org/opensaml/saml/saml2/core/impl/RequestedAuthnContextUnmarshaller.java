package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextComparisonTypeEnumeration;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.w3c.dom.Attr;

public class RequestedAuthnContextUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      RequestedAuthnContext rac = (RequestedAuthnContext)parentSAMLObject;
      if (childSAMLObject instanceof AuthnContextClassRef) {
         rac.getAuthnContextClassRefs().add((AuthnContextClassRef)childSAMLObject);
      } else if (childSAMLObject instanceof AuthnContextDeclRef) {
         rac.getAuthnContextDeclRefs().add((AuthnContextDeclRef)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      RequestedAuthnContext rac = (RequestedAuthnContext)samlObject;
      if (attribute.getLocalName().equals("Comparison") && attribute.getNamespaceURI() == null) {
         if ("exact".equals(attribute.getValue())) {
            rac.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
         } else if ("minimum".equals(attribute.getValue())) {
            rac.setComparison(AuthnContextComparisonTypeEnumeration.MINIMUM);
         } else if ("maximum".equals(attribute.getValue())) {
            rac.setComparison(AuthnContextComparisonTypeEnumeration.MAXIMUM);
         } else {
            if (!"better".equals(attribute.getValue())) {
               throw new UnmarshallingException("Saw an invalid value for Comparison attribute: " + attribute.getValue());
            }

            rac.setComparison(AuthnContextComparisonTypeEnumeration.BETTER);
         }
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
