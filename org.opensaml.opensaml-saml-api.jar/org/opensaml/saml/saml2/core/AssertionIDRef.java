package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AssertionIDRef extends SAMLObject, Evidentiary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AssertionIDRef";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionIDRef", "saml2");

   String getAssertionID();

   void setAssertionID(String var1);
}
