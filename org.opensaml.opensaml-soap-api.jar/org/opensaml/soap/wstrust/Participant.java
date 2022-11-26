package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface Participant extends ParticipantType {
   String ELEMENT_LOCAL_NAME = "Participant";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Participant", "wst");
}
