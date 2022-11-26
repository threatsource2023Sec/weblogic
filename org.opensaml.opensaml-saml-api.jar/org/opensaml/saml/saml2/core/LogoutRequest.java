package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;

public interface LogoutRequest extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "LogoutRequest";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutRequest", "saml2p");
   String TYPE_LOCAL_NAME = "LogoutRequestType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "LogoutRequestType", "saml2p");
   String REASON_ATTRIB_NAME = "Reason";
   String NOT_ON_OR_AFTER_ATTRIB_NAME = "NotOnOrAfter";
   String USER_REASON = "urn:oasis:names:tc:SAML:2.0:logout:user";
   String ADMIN_REASON = "urn:oasis:names:tc:SAML:2.0:logout:admin";
   String GLOBAL_TIMEOUT_REASON = "urn:oasis:names:tc:SAML:2.0:logout:global-timeout";
   String SP_TIMEOUT_REASON = "urn:oasis:names:tc:SAML:2.0:logout:sp-timeout";

   String getReason();

   void setReason(String var1);

   DateTime getNotOnOrAfter();

   void setNotOnOrAfter(DateTime var1);

   BaseID getBaseID();

   void setBaseID(BaseID var1);

   NameID getNameID();

   void setNameID(NameID var1);

   EncryptedID getEncryptedID();

   void setEncryptedID(EncryptedID var1);

   List getSessionIndexes();
}
