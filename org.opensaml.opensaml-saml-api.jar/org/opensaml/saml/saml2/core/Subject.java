package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Subject extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Subject";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Subject", "saml2");
   String TYPE_LOCAL_NAME = "SubjectType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectType", "saml2");

   BaseID getBaseID();

   void setBaseID(BaseID var1);

   NameID getNameID();

   void setNameID(NameID var1);

   EncryptedID getEncryptedID();

   void setEncryptedID(EncryptedID var1);

   List getSubjectConfirmations();
}
