package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface EmailAddress extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EmailAddress";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EmailAddress", "md");
   String TYPE_LOCAL_NAME = "EmailAddressType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EmailAddressType", "md");

   String getAddress();

   void setAddress(String var1);
}
