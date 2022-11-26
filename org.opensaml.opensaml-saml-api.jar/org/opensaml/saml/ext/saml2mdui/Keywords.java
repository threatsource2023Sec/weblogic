package org.opensaml.saml.ext.saml2mdui;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.saml.common.SAMLObject;

public interface Keywords extends SAMLObject, LangBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Keywords";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "Keywords", "mdui");
   String TYPE_LOCAL_NAME = "KeywordsType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "KeywordsType", "mdui");

   List getKeywords();

   void setKeywords(List var1);
}
