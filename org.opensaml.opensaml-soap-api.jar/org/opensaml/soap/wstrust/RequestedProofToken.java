package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface RequestedProofToken extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestedProofToken";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedProofToken", "wst");
   String TYPE_LOCAL_NAME = "RequestedProofTokenType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedProofTokenType", "wst");

   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(XMLObject var1);
}
