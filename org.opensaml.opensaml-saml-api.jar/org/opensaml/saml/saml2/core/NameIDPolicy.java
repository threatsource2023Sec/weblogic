package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.SAMLObject;

public interface NameIDPolicy extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDPolicy";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDPolicy", "saml2p");
   String TYPE_LOCAL_NAME = "NameIDPolicyType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDPolicyType", "saml2p");
   String FORMAT_ATTRIB_NAME = "Format";
   String SP_NAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";
   String ALLOW_CREATE_ATTRIB_NAME = "AllowCreate";

   String getFormat();

   void setFormat(String var1);

   String getSPNameQualifier();

   void setSPNameQualifier(String var1);

   Boolean getAllowCreate();

   XSBooleanValue getAllowCreateXSBoolean();

   void setAllowCreate(Boolean var1);

   void setAllowCreate(XSBooleanValue var1);
}
