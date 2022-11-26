package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSURI;

public interface RelatesTo extends XSURI, AttributeExtensibleXMLObject, WSAddressingObject {
   String ELEMENT_LOCAL_NAME = "RelatesTo";
   QName ELEMENT_NAME = new QName("http://www.w3.org/2005/08/addressing", "RelatesTo", "wsa");
   String TYPE_LOCAL_NAME = "RelatesToType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "RelatesToType", "wsa");
   String RELATIONSHIP_TYPE_ATTRIB_NAME = "RelationshipType";
   String RELATIONSHIP_TYPE_REPLY = "http://www.w3.org/2005/08/addressing/reply";

   String getRelationshipType();

   void setRelationshipType(String var1);
}
