package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SessionIndex extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SessionIndex";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "SessionIndex", "saml2p");

   String getSessionIndex();

   void setSessionIndex(String var1);
}
