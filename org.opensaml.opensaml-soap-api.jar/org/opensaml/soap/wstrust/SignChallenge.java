package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;

public interface SignChallenge extends SignChallengeType {
   String ELEMENT_LOCAL_NAME = "SignChallenge";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallenge", "wst");
}
