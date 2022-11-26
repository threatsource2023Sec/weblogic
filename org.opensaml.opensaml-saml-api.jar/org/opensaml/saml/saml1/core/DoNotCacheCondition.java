package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;

public interface DoNotCacheCondition extends Condition {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DoNotCacheCondition";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition", "saml1");
   String TYPE_LOCAL_NAME = "DoNotCacheConditionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheConditionType", "saml1");
}
