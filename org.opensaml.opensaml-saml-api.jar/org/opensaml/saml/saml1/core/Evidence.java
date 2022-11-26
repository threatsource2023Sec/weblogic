package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Evidence extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Evidence";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence", "saml1");
   String TYPE_LOCAL_NAME = "EvidenceType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "EvidenceType", "saml1");

   List getEvidence();

   List getAssertionIDReferences();

   List getAssertions();
}
