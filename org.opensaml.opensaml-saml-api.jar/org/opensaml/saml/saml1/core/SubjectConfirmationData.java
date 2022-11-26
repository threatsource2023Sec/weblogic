package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface SubjectConfirmationData extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SubjectConfirmationData";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationData", "saml1");
}
