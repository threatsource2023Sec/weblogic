package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface BaseID extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "BaseID";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "BaseID", "saml2");
   String TYPE_LOCAL_NAME = "BaseIDAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "BaseIDAbstractType", "saml2");
   String NAME_QUALIFIER_ATTRIB_NAME = "NameQualifier";
   String SP_NAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";

   String getNameQualifier();

   void setNameQualifier(String var1);

   String getSPNameQualifier();

   void setSPNameQualifier(String var1);
}
