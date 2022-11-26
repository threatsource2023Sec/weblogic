package org.opensaml.saml.ext.saml2mdui;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.saml.common.SAMLObject;

public interface Logo extends LangBearing, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Logo";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "Logo", "mdui");
   String TYPE_LOCAL_NAME = "LogoType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "LogoType", "mdui");
   String HEIGHT_ATTR_NAME = "height";
   String WIDTH_ATTR_NAME = "width";

   String getURL();

   void setURL(String var1);

   Integer getHeight();

   void setHeight(Integer var1);

   Integer getWidth();

   void setWidth(Integer var1);
}
