package org.opensaml.saml.saml2.core;

public interface NameIDType {
   String NAME_QUALIFIER_ATTRIB_NAME = "NameQualifier";
   String SP_NAME_QUALIFIER_ATTRIB_NAME = "SPNameQualifier";
   String FORMAT_ATTRIB_NAME = "Format";
   String SPPROVIDED_ID_ATTRIB_NAME = "SPProvidedID";
   String UNSPECIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
   String EMAIL = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
   String X509_SUBJECT = "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName";
   String WIN_DOMAIN_QUALIFIED = "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName";
   String KERBEROS = "urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos";
   String ENTITY = "urn:oasis:names:tc:SAML:2.0:nameid-format:entity";
   String PERSISTENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";
   String TRANSIENT = "urn:oasis:names:tc:SAML:2.0:nameid-format:transient";
   String ENCRYPTED = "urn:oasis:names:tc:SAML:2.0:nameid-format:encrypted";

   String getValue();

   void setValue(String var1);

   String getNameQualifier();

   void setNameQualifier(String var1);

   String getSPNameQualifier();

   void setSPNameQualifier(String var1);

   String getFormat();

   void setFormat(String var1);

   String getSPProvidedID();

   void setSPProvidedID(String var1);
}
