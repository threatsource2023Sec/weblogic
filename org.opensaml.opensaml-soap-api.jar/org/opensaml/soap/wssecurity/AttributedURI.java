package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSURI;

public interface AttributedURI extends XSURI, IdBearing, AttributeExtensibleXMLObject, WSSecurityObject {
   String TYPE_LOCAL_NAME = "AttributedURI";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "AttributedURI", "wsu");
}
