package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface ProxyRestriction extends Condition {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ProxyRestriction";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ProxyRestriction", "saml2");
   String TYPE_LOCAL_NAME = "ProxyRestrictionType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ProxyRestrictionType", "saml2");
   String COUNT_ATTRIB_NAME = "Count";

   Integer getProxyCount();

   void setProxyCount(Integer var1);

   List getAudiences();
}
