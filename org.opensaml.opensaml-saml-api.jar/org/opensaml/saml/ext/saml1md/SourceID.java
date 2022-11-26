package org.opensaml.saml.ext.saml1md;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;

public interface SourceID extends SAMLObject, XSString {
   String DEFAULT_ELEMENT_LOCAL_NAME = "SourceID";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:profiles:v1metadata", "SourceID", "saml1md");
}
