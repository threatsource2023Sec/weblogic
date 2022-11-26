package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface StatusCode extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "StatusCode";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode", "saml1p");
   String TYPE_LOCAL_NAME = "StatusCodeType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCodeType", "saml1p");
   QName SUCCESS = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Success", "saml1p");
   QName VERSION_MISMATCH = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "VersionMismatch", "saml1p");
   QName REQUESTER = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Requester", "saml1p");
   QName RESPONDER = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Responder", "saml1p");
   QName REQUEST_VERSION_TOO_HIGH = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestVersionTooHigh", "saml1p");
   QName REQUEST_VERSION_TOO_LOW = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestVersionTooLow", "saml1p");
   QName REQUEST_VERSION_DEPRECATED = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestVersionDeprecated", "saml1p");
   QName TOO_MANY_RESPONSES = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "TooManyResponses", "saml1p");
   QName REQUEST_DENIED = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "RequestDenied", "saml1p");
   QName RESOURCE_NOT_RECOGNIZED = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "ResourceNotRecognized", "saml1p");
   String VALUE_ATTRIB_NAME = "Value";

   QName getValue();

   void setValue(QName var1);

   StatusCode getStatusCode();

   void setStatusCode(StatusCode var1);
}
