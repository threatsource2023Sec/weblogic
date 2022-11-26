package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AttributeProfile extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeProfile";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeProfile", "md");
   String TYPE_LOCAL_NAME = "AttributeProfileType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeProfileType", "md");

   String getProfileURI();

   void setProfileURI(String var1);
}
