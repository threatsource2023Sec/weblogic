package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectStatement extends SAMLObject, Statement {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectStatement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement", "saml1");
   String TYPE_LOCAL_NAME = "SubjectStatementAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatementAbstractType", "saml1");

   Subject getSubject();

   void setSubject(Subject var1);
}
