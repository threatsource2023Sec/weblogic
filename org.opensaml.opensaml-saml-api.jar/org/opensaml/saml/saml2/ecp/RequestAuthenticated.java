package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface RequestAuthenticated extends SAMLObject, MustUnderstandBearing, ActorBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RequestAuthenticated";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestAuthenticated", "ecp");
   String TYPE_LOCAL_NAME = "RequestAuthenticatedType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RequestAuthenticatedType", "ecp");
}
