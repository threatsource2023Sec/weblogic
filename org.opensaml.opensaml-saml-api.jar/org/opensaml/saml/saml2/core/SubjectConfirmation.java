package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectConfirmation extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmation";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmation", "saml2");
   String TYPE_LOCAL_NAME = "SubjectConfirmationType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationType", "saml2");
   String METHOD_ATTRIB_NAME = "Method";
   String METHOD_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
   String METHOD_SENDER_VOUCHES = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
   String METHOD_BEARER = "urn:oasis:names:tc:SAML:2.0:cm:bearer";

   String getMethod();

   void setMethod(String var1);

   BaseID getBaseID();

   void setBaseID(BaseID var1);

   NameID getNameID();

   void setNameID(NameID var1);

   EncryptedID getEncryptedID();

   void setEncryptedID(EncryptedID var1);

   SubjectConfirmationData getSubjectConfirmationData();

   void setSubjectConfirmationData(SubjectConfirmationData var1);
}
