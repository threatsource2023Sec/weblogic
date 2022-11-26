package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.xmlsec.encryption.EncryptedData;

public interface EncryptedElementType extends SAMLObject {
   String TYPE_LOCAL_NAME = "EncryptedElementType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedElementType", "saml2");

   EncryptedData getEncryptedData();

   void setEncryptedData(EncryptedData var1);

   List getEncryptedKeys();
}
