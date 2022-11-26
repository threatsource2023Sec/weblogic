package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface Claims extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Claims";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Claims", "wst");
   String TYPE_LOCAL_NAME = "ClaimsType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ClaimsType", "wst");
   String DIALECT_ATTRIB_NAME = "Dialect";

   String getDialect();

   void setDialect(String var1);
}
