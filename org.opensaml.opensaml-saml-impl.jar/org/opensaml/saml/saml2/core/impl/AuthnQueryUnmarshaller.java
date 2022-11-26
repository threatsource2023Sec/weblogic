package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml2.core.AuthnQuery;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.w3c.dom.Attr;

public class AuthnQueryUnmarshaller extends SubjectQueryUnmarshaller {
   protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
      AuthnQuery query = (AuthnQuery)parentSAMLObject;
      if (childSAMLObject instanceof RequestedAuthnContext) {
         query.setRequestedAuthnContext((RequestedAuthnContext)childSAMLObject);
      } else {
         super.processChildElement(parentSAMLObject, childSAMLObject);
      }

   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      AuthnQuery query = (AuthnQuery)samlObject;
      if (attribute.getLocalName().equals("SessionIndex") && attribute.getNamespaceURI() == null) {
         query.setSessionIndex(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
