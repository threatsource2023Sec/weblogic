package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface Timestamp extends IdBearing, AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSSecurityObject {
   String ELEMENT_LOCAL_NAME = "Timestamp";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp", "wsu");

   Created getCreated();

   void setCreated(Created var1);

   Expires getExpires();

   void setExpires(Expires var1);
}
