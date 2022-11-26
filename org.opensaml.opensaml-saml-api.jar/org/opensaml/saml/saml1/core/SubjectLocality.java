package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectLocality extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectLocality";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality", "saml1");
   String TYPE_LOCAL_NAME = "SubjectLocalityType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocalityType", "saml1");
   String IPADDRESS_ATTRIB_NAME = "IPAddress";
   String DNSADDRESS_ATTRIB_NAME = "DNSAddress";

   String getIPAddress();

   void setIPAddress(String var1);

   String getDNSAddress();

   void setDNSAddress(String var1);
}
