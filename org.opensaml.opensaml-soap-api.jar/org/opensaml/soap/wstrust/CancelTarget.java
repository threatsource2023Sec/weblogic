package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface CancelTarget extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "CancelTarget";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CancelTarget", "wst");
   String TYPE_LOCAL_NAME = "CancelTargetType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CancelTargetType", "wst");

   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(XMLObject var1);
}
