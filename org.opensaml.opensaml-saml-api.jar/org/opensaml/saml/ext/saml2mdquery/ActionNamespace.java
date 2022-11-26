package org.opensaml.saml.ext.saml2mdquery;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.saml.common.SAMLObject;

public interface ActionNamespace extends XSURI, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ActionNamespace";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "ActionNamespace", "query");
}
