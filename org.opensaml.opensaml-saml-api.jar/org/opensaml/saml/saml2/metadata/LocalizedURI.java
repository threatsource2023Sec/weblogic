package org.opensaml.saml.saml2.metadata;

import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.saml.common.SAMLObject;

public interface LocalizedURI extends XSURI, LangBearing, SAMLObject {
   String TYPE_LOCAL_NAME = "localizedURIType";
}
