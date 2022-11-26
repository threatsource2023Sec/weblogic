package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AssertionURIRef extends SAMLObject, Evidentiary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionURIRef";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionURIRef", "saml2");

   String getAssertionURI();

   void setAssertionURI(String var1);
}
