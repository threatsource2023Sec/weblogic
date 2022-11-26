package org.opensaml.saml.ext.saml2mdrpi;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface RegistrationInfo extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "RegistrationInfo";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationInfo", "mdrpi");
   String TYPE_LOCAL_NAME = "RegistrationInfoType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:rpi", "RegistrationInfoType", "mdrpi");
   String REGISTRATION_AUTHORITY_ATTRIB_NAME = "registrationAuthority";
   String REGISTRATION_INSTANT_ATTRIB_NAME = "registrationInstant";

   String getRegistrationAuthority();

   void setRegistrationAuthority(String var1);

   DateTime getRegistrationInstant();

   void setRegistrationInstant(DateTime var1);

   List getRegistrationPolicies();
}
