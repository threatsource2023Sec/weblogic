package org.opensaml.saml.saml2.common;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface TimeBoundSAMLObject extends SAMLObject {
   String VALID_UNTIL_ATTRIB_NAME = "validUntil";
   QName VALID_UNTIL_ATTRIB_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "validUntil", "md");

   boolean isValid();

   DateTime getValidUntil();

   void setValidUntil(DateTime var1);
}
