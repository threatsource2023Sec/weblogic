package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Subject extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Subject";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Subject", "saml1");
   String TYPE_LOCAL_NAME = "SubjectType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectType", "saml1");

   NameIdentifier getNameIdentifier();

   void setNameIdentifier(NameIdentifier var1);

   SubjectConfirmation getSubjectConfirmation();

   void setSubjectConfirmation(SubjectConfirmation var1);
}
