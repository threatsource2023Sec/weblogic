package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface NameIdentifier extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NameIdentifier";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier", "saml1");
   String TYPE_LOCAL_NAME = "NameIdentifierType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifierType", "saml1");
   String NAMEQUALIFIER_ATTRIB_NAME = "NameQualifier";
   String FORMAT_ATTRIB_NAME = "Format";
   String UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
   String EMAIL = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
   String X509_SUBJECT = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
   String WIN_DOMAIN_QUALIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";

   String getNameQualifier();

   void setNameQualifier(String var1);

   String getFormat();

   void setFormat(String var1);

   /** @deprecated */
   @Deprecated
   String getNameIdentifier();

   /** @deprecated */
   @Deprecated
   void setNameIdentifier(String var1);

   String getValue();

   void setValue(String var1);
}
