package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface GeolocationHint extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "GeolocationHint";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "GeolocationHint", "mdui");

   String getHint();

   void setHint(String var1);
}
