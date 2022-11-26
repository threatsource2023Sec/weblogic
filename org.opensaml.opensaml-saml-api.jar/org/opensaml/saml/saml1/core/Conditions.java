package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface Conditions extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Conditions";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions", "saml1");
   String TYPE_LOCAL_NAME = "ConditionsType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConditionsType", "saml1");
   String NOTBEFORE_ATTRIB_NAME = "NotBefore";
   String NOTONORAFTER_ATTRIB_NAME = "NotOnOrAfter";

   DateTime getNotBefore();

   void setNotBefore(DateTime var1);

   DateTime getNotOnOrAfter();

   void setNotOnOrAfter(DateTime var1);

   List getConditions();

   List getConditions(QName var1);

   List getAudienceRestrictionConditions();

   List getDoNotCacheConditions();
}
