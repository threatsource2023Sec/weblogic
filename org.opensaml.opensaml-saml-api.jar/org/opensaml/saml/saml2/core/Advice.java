package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Advice extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Advice";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Advice", "saml2");
   String TYPE_LOCAL_NAME = "AdviceType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AdviceType", "saml2");

   List getChildren();

   List getChildren(QName var1);

   List getAssertionIDReferences();

   List getAssertionURIReferences();

   List getAssertions();

   List getEncryptedAssertions();
}
