package org.opensaml.saml.ext.samlec;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.MustUnderstandBearing;

public interface GeneratedKey extends XSBase64Binary, SAMLObject, MustUnderstandBearing, ActorBearing {
   String DEFAULT_ELEMENT_LOCAL_NAME = "GeneratedKey";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:ietf:params:xml:ns:samlec", "GeneratedKey", "samlec");
   String TYPE_LOCAL_NAME = "GeneratedKeyType";
   QName TYPE_NAME = new QName("urn:ietf:params:xml:ns:samlec", "GeneratedKeyType", "samlec");
}
