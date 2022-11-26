package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;

public class AuthnContextDeclRefUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      AuthnContextDeclRef authnContextDeclRef = (AuthnContextDeclRef)samlObject;
      authnContextDeclRef.setAuthnContextDeclRef(elementContent);
   }
}
