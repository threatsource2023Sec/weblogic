package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Condition extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Condition";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Condition", "saml2");
   String TYPE_LOCAL_NAME = "ConditionAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ConditionAbstractType", "saml2");
}
