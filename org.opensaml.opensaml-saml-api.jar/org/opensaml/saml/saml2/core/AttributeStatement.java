package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AttributeStatement extends Statement {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AttributeStatement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement", "saml2");
   String TYPE_LOCAL_NAME = "AttributeStatementType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatementType", "saml2");

   List getAttributes();

   List getEncryptedAttributes();
}
