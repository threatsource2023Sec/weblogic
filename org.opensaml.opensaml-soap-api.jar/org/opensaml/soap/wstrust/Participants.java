package org.opensaml.soap.wstrust;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface Participants extends ElementExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Participants";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Participants", "wst");
   String TYPE_LOCAL_NAME = "ParticipantsType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ParticipantsType", "wst");

   Primary getPrimary();

   void setPrimary(Primary var1);

   List getParticipants();
}
