package org.opensaml.saml.ext.samlec;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;

public interface EncType extends XSString, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncType";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:ietf:params:xml:ns:samlec", "EncType", "samlec");
}
