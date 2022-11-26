package org.opensaml.soap.wspolicy;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.soap.wssecurity.IdBearing;

public interface Policy extends OperatorContentType, AttributeExtensibleXMLObject, IdBearing {
   String ELEMENT_LOCAL_NAME = "Policy";
   QName ELEMENT_NAME = new QName("http://schemas.xmlsoap.org/ws/2004/09/policy", "Policy", "wsp");
   String NAME_ATTRIB_NAME = "Name";

   String getName();

   void setName(String var1);
}
