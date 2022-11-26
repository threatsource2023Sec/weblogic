package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Company extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Company";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "Company", "md");
   String TYPE_LOCAL_NAME = "CompanyType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "CompanyType", "md");

   String getName();

   void setName(String var1);
}
