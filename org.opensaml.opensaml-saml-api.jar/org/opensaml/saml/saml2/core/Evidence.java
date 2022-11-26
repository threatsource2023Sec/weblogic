package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Evidence extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Evidence";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Evidence", "saml2");
   String TYPE_LOCAL_NAME = "EvidenceType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EvidenceType", "saml2");

   List getAssertionIDReferences();

   List getAssertionURIReferences();

   List getAssertions();

   List getEncryptedAssertions();

   List getEvidence();
}
