package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AudienceRestrictionCondition extends Condition {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AudienceRestrictionCondition";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition", "saml1");
   String TYPE_LOCAL_NAME = "AudienceRestrictionConditionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionConditionType", "saml1");

   List getAudiences();
}
