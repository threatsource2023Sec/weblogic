package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface Response extends StatusResponseType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Response";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Response", "saml2p");
   String TYPE_LOCAL_NAME = "ResponseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ResponseType", "saml2p");

   List getAssertions();

   List getEncryptedAssertions();
}
