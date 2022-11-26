package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface SignChallengeType extends AttributeExtensibleXMLObject, ElementExtensibleXMLObject, WSTrustObject {
   String TYPE_LOCAL_NAME = "SignChallengeType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallengeType", "wst");

   Challenge getChallenge();

   void setChallenge(Challenge var1);
}
