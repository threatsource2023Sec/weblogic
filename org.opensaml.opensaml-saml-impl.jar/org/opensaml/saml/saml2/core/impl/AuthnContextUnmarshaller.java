package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthenticatingAuthority;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public class AuthnContextUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
      AuthnContext authnContext = (AuthnContext)parentObject;
      if (childObject instanceof AuthnContextClassRef) {
         authnContext.setAuthnContextClassRef((AuthnContextClassRef)childObject);
      } else if (childObject instanceof AuthnContextDecl) {
         authnContext.setAuthnContextDecl((AuthnContextDecl)childObject);
      } else if (childObject instanceof AuthnContextDeclRef) {
         authnContext.setAuthnContextDeclRef((AuthnContextDeclRef)childObject);
      } else if (childObject instanceof AuthenticatingAuthority) {
         authnContext.getAuthenticatingAuthorities().add((AuthenticatingAuthority)childObject);
      } else {
         super.processChildElement(parentObject, childObject);
      }

   }
}
