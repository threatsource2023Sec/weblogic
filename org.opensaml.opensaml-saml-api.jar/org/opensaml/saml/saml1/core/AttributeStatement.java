package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AttributeStatement extends SAMLObject, SubjectStatement {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeStatement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement", "saml1");
   String TYPE_LOCAL_NAME = "AttributeStatementType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatementType", "saml1");

   List getAttributes();
}
