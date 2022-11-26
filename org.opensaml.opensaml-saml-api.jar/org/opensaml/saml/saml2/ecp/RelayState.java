package org.opensaml.saml.saml2.ecp;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface RelayState extends XSString, SAMLObject, MustUnderstandBearing, ActorBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RelayState";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RelayState", "ecp");
   String TYPE_LOCAL_NAME = "RelayStateType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:profiles:SSO:ecp", "RelayStateType", "ecp");
}
