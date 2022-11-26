package org.opensaml.soap.wsaddressing;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface EndpointReferenceType extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSAddressingObject {
   String TYPE_LOCAL_NAME = "EndpointReferenceType";
   QName TYPE_NAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointReferenceType", "wsa");

   Address getAddress();

   void setAddress(Address var1);

   Metadata getMetadata();

   void setMetadata(Metadata var1);

   ReferenceParameters getReferenceParameters();

   void setReferenceParameters(ReferenceParameters var1);
}
