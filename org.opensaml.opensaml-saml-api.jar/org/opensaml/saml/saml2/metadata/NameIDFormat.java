package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface NameIDFormat extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDFormat";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "NameIDFormat", "md");
   String TYPE_LOCAL_NAME = "NameIDFormatType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "NameIDFormatType", "md");

   String getFormat();

   void setFormat(String var1);
}
