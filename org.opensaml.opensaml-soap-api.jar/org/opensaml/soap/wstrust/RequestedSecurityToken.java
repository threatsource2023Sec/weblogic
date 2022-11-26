package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface RequestedSecurityToken extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "RequestedSecurityToken";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedSecurityToken", "wst");
   String TYPE_LOCAL_NAME = "RequestedSecurityTokenType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedSecurityTokenType", "wst");

   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(XMLObject var1);
}
