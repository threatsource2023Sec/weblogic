package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface ParticipantType extends WSTrustObject {
   String TYPE_LOCAL_NAME = "ParticipantType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ParticipantType", "wst");

   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(XMLObject var1);
}
