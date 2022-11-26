package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface TelephoneNumber extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "TelephoneNumber";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "TelephoneNumber", "md");

   String getNumber();

   void setNumber(String var1);
}
