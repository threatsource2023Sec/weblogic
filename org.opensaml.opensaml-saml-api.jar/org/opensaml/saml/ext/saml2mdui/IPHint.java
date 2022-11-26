package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface IPHint extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "IPHint";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "IPHint", "mdui");

   String getHint();

   void setHint(String var1);
}
