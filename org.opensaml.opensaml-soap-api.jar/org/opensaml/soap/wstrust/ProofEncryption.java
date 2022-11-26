package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface ProofEncryption extends WSTrustObject {
   String ELEMENT_LOCAL_NAME = "ProofEncryption";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryption", "wst");
   String TYPE_LOCAL_NAME = "ProofEncryptionType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryptionType", "wst");

   XMLObject getUnknownXMLObject();

   void setUnknownXMLObject(XMLObject var1);
}
