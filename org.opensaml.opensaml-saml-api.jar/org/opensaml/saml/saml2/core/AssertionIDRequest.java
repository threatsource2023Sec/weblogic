package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;

public interface AssertionIDRequest extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionIDRequest";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "AssertionIDRequest", "saml2p");

   List getAssertionIDRefs();
}
