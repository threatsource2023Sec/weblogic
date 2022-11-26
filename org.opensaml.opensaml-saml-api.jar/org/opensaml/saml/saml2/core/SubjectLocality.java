package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectLocality extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectLocality";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectLocality", "saml2");
   String TYPE_LOCAL_NAME = "SubjectLocalityType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectLocalityType", "saml2");
   String ADDRESS_ATTRIB_NAME = "Address";
   String DNS_NAME_ATTRIB_NAME = "DNSName";

   String getAddress();

   void setAddress(String var1);

   String getDNSName();

   void setDNSName(String var1);
}
