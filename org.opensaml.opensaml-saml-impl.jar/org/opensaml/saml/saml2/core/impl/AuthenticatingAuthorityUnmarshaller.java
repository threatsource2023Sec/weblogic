package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthenticatingAuthority;

public class AuthenticatingAuthorityUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      AuthenticatingAuthority authenticatingAuthority = (AuthenticatingAuthority)samlObject;
      authenticatingAuthority.setURI(elementContent);
   }
}
