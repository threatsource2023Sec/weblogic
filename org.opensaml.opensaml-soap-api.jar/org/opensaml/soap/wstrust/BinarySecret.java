package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSBase64Binary;

public interface BinarySecret extends XSBase64Binary, AttributeExtensibleXMLObject, WSTrustObject {
   String ELEMENT_LOCAL_NAME = "BinarySecret";
   QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecret", "wst");
   String TYPE_LOCAL_NAME = "BinarySecretType";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecretType", "wst");
   String TYPE_ATTRIB_NAME = "Type";
   String TYPE_ASYMMETRIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/AsymmetricKey";
   String TYPE_SYMMETRIC_KEY = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/SymmetricKey";
   String TYPE_NONCE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Nonce";

   String getType();

   void setType(String var1);
}
