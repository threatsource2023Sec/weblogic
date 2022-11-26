package org.opensaml.saml.ext.samlpthrpty;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.NameIDType;

public interface RespondTo extends NameIDType, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RespondTo";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:protocol:ext:third-party", "RespondTo", "thrpty");
}
