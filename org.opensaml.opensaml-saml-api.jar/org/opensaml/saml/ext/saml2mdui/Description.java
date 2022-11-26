package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.LocalizedName;

public interface Description extends LocalizedName, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Description";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "Description", "mdui");
}
