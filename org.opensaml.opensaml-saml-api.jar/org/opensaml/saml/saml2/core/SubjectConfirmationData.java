package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectConfirmationData extends SAMLObject, ElementExtensibleXMLObject, AttributeExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmationData";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationData", "saml2");
   String TYPE_LOCAL_NAME = "SubjectConfirmationDataType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationDataType", "saml2");
   String NOT_BEFORE_ATTRIB_NAME = "NotBefore";
   String NOT_ON_OR_AFTER_ATTRIB_NAME = "NotOnOrAfter";
   String RECIPIENT_ATTRIB_NAME = "Recipient";
   String IN_RESPONSE_TO_ATTRIB_NAME = "InResponseTo";
   String ADDRESS_ATTRIB_NAME = "Address";

   DateTime getNotBefore();

   void setNotBefore(DateTime var1);

   DateTime getNotOnOrAfter();

   void setNotOnOrAfter(DateTime var1);

   String getRecipient();

   void setRecipient(String var1);

   String getInResponseTo();

   void setInResponseTo(String var1);

   String getAddress();

   void setAddress(String var1);
}
