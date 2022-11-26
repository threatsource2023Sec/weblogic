package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface GivenName extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "GivenName";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "GivenName", "md");

   String getName();

   void setName(String var1);
}
