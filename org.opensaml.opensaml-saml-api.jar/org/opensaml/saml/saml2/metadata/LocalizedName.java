package org.opensaml.saml.saml2.metadata;

import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.common.SAMLObject;

public interface LocalizedName extends XSString, LangBearing, SAMLObject {
   String TYPE_LOCAL_NAME = "localizedNameType";
}
