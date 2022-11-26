package org.opensaml.saml.saml2.common;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface CacheableSAMLObject extends SAMLObject {
   String CACHE_DURATION_ATTRIB_NAME = "cacheDuration";
   QName CACHE_DURATION_ATTRIB_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "cacheDuration", "md");

   Long getCacheDuration();

   void setCacheDuration(Long var1);
}
