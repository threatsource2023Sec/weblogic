package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;

public interface Authenticator extends ElementExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "Authenticator";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Authenticator", "wst");
   String TYPE_LOCAL_NAME = "AuthenticatorType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticatorType", "wst");

   CombinedHash getCombinedHash();

   void setCombinedHash(CombinedHash var1);
}
