package org.opensaml.saml.ext.saml2delrestrict;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;

public interface Delegate extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Delegate";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:conditions:delegation", "Delegate", "del");
   String TYPE_LOCAL_NAME = "DelegateType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:conditions:delegation", "DelegateType", "del");
   String DELEGATION_INSTANT_ATTRIB_NAME = "DelegationInstant";
   String CONFIRMATION_METHOD_ATTRIB_NAME = "ConfirmationMethod";

   BaseID getBaseID();

   void setBaseID(BaseID var1);

   NameID getNameID();

   void setNameID(NameID var1);

   EncryptedID getEncryptedID();

   void setEncryptedID(EncryptedID var1);

   DateTime getDelegationInstant();

   void setDelegationInstant(DateTime var1);

   String getConfirmationMethod();

   void setConfirmationMethod(String var1);
}
