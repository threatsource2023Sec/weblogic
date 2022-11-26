package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface StatusCode extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusCode", "saml2p");
   String TYPE_LOCAL_NAME = "StatusCodeType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusCodeType", "saml2p");
   String VALUE_ATTRIB_NAME = "Value";
   String SUCCESS = "urn:oasis:names:tc:SAML:2.0:status:Success";
   String REQUESTER = "urn:oasis:names:tc:SAML:2.0:status:Requester";
   String RESPONDER = "urn:oasis:names:tc:SAML:2.0:status:Responder";
   String VERSION_MISMATCH = "urn:oasis:names:tc:SAML:2.0:status:VersionMismatch";
   String AUTHN_FAILED = "urn:oasis:names:tc:SAML:2.0:status:AuthnFailed";
   String INVALID_ATTR_NAME_OR_VALUE = "urn:oasis:names:tc:SAML:2.0:status:InvalidAttrNameOrValue";
   String INVALID_NAMEID_POLICY = "urn:oasis:names:tc:SAML:2.0:status:InvalidNameIDPolicy";
   String NO_AUTHN_CONTEXT = "urn:oasis:names:tc:SAML:2.0:status:NoAuthnContext";
   String NO_AVAILABLE_IDP = "urn:oasis:names:tc:SAML:2.0:status:NoAvailableIDP";
   String NO_PASSIVE = "urn:oasis:names:tc:SAML:2.0:status:NoPassive";
   String NO_SUPPORTED_IDP = "urn:oasis:names:tc:SAML:2.0:status:NoSupportedIDP";
   String PARTIAL_LOGOUT = "urn:oasis:names:tc:SAML:2.0:status:PartialLogout";
   String PROXY_COUNT_EXCEEDED = "urn:oasis:names:tc:SAML:2.0:status:ProxyCountExceeded";
   String REQUEST_DENIED = "urn:oasis:names:tc:SAML:2.0:status:RequestDenied";
   String REQUEST_UNSUPPORTED = "urn:oasis:names:tc:SAML:2.0:status:RequestUnsupported";
   String REQUEST_VERSION_DEPRECATED = "urn:oasis:names:tc:SAML:2.0:status:RequestVersionDeprecated";
   String REQUEST_VERSION_TOO_HIGH = "urn:oasis:names:tc:SAML:2.0:status:RequestVersionTooHigh";
   String REQUEST_VERSION_TOO_LOW = "urn:oasis:names:tc:SAML:2.0:status:RequestVersionTooLow";
   String RESOURCE_NOT_RECOGNIZED = "urn:oasis:names:tc:SAML:2.0:status:ResourceNotRecognized";
   String TOO_MANY_RESPONSES = "urn:oasis:names:tc:SAML:2.0:status:TooManyResponses";
   String UNKNOWN_ATTR_PROFILE = "urn:oasis:names:tc:SAML:2.0:status:UnknownAttrProfile";
   String UNKNOWN_PRINCIPAL = "urn:oasis:names:tc:SAML:2.0:status:UnknownPrincipal";
   String UNSUPPORTED_BINDING = "urn:oasis:names:tc:SAML:2.0:status:UnsupportedBinding";

   StatusCode getStatusCode();

   void setStatusCode(StatusCode var1);

   String getValue();

   void setValue(String var1);
}
