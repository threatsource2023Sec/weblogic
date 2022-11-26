package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface SignChallengeResponse extends SignChallengeType {
   String ELEMENT_LOCAL_NAME = "SignChallengeResponse";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallengeResponse", "wst");
}
