package org.opensaml.saml.ext.saml2mdui;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface UIInfo extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "UIInfo";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "UIInfo", "mdui");
   String TYPE_LOCAL_NAME = "UIInfoType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ui", "UIInfoType", "mdui");
   String LANG_ATTRIB_NAME = "lang";

   List getDisplayNames();

   List getKeywords();

   List getDescriptions();

   List getLogos();

   List getInformationURLs();

   List getPrivacyStatementURLs();

   List getXMLObjects();

   List getXMLObjects(QName var1);
}
