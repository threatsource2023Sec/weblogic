package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AudienceRestriction extends Condition {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AudienceRestriction";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestriction", "saml2");
   String TYPE_LOCAL_NAME = "AudienceRestrictionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestrictionType", "saml2");

   List getAudiences();
}
