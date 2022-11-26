package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface GetComplete extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "GetComplete";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "GetComplete", "saml2p");

   String getGetComplete();

   void setGetComplete(String var1);
}
