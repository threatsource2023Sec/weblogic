package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface Conditions extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Conditions";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Conditions", "saml2");
   String TYPE_LOCAL_NAME = "ConditionsType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ConditionsType", "saml2");
   String NOT_BEFORE_ATTRIB_NAME = "NotBefore";
   String NOT_ON_OR_AFTER_ATTRIB_NAME = "NotOnOrAfter";

   DateTime getNotBefore();

   void setNotBefore(DateTime var1);

   DateTime getNotOnOrAfter();

   void setNotOnOrAfter(DateTime var1);

   List getConditions();

   List getAudienceRestrictions();

   OneTimeUse getOneTimeUse();

   ProxyRestriction getProxyRestriction();
}
