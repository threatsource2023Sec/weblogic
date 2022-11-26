package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface StatusMessage extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "StatusMessage";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "StatusMessage", "saml2p");

   String getMessage();

   void setMessage(String var1);
}
