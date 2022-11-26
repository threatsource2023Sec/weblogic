package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.AuthenticatingAuthority;
import org.w3c.dom.Element;

public class AuthenticatingAuthorityMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      AuthenticatingAuthority authenticatingAuthority = (AuthenticatingAuthority)samlObject;
      ElementSupport.appendTextContent(domElement, authenticatingAuthority.getURI());
   }
}
