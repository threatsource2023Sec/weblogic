package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SurName extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SurName";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "SurName", "md");

   String getName();

   void setName(String var1);
}
