package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface RequesterID extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RequesterID";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "RequesterID", "saml2p");

   String getRequesterID();

   void setRequesterID(String var1);
}
