package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.saml.saml2.metadata.LocalizedName;

public interface DisplayName extends LocalizedName {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DisplayName";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "DisplayName", "mdui");
}
