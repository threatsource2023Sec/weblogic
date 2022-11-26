package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface NewID extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NewID";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NewID", "saml2p");

   String getNewID();

   void setNewID(String var1);
}
