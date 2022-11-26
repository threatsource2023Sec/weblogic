package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthorityBinding extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthorityBinding";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding", "saml1");
   String TYPE_LOCAL_NAME = "AuthorityBindingType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBindingType", "saml1");
   String AUTHORITYKIND_ATTRIB_NAME = "AuthorityKind";
   String LOCATION_ATTRIB_NAME = "Location";
   String BINDING_ATTRIB_NAME = "Binding";

   QName getAuthorityKind();

   void setAuthorityKind(QName var1);

   String getLocation();

   void setLocation(String var1);

   String getBinding();

   void setBinding(String var1);
}
