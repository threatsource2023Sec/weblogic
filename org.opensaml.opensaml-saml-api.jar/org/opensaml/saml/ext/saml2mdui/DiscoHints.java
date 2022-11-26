package org.opensaml.saml.ext.saml2mdui;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface DiscoHints extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "DiscoHints";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "DiscoHints", "mdui");
   String TYPE_LOCAL_NAME = "DiscoHintsType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "DiscoHintsType", "mdui");

   List getIPHints();

   List getDomainHints();

   List getGeolocationHints();

   List getXMLObjects();

   List getXMLObjects(QName var1);
}
