package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface ValidateTarget extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "ValidateTarget";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ValidateTarget", "wst");
   String TYPE_LOCAL_NAME = "ValidateTargetType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ValidateTargetType", "wst");

   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(XMLObject var1);
}
