package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface OneTimeUse extends Condition {
   String DEFAULT_ELEMENT_LOCAL_NAME = "OneTimeUse";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUse", "saml2");
   String TYPE_LOCAL_NAME = "OneTimeUseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUseType", "saml2");
}
