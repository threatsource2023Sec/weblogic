package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface Primary extends ParticipantType {
   String ELEMENT_LOCAL_NAME = "Primary";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Primary", "wst");
}
