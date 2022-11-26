package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface ManageNameIDRequest extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ManageNameIDRequest";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDRequest", "saml2p");
   String TYPE_LOCAL_NAME = "ManageNameIDRequestType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDRequestType", "saml2p");

   NameID getNameID();

   void setNameID(NameID var1);

   EncryptedID getEncryptedID();

   void setEncryptedID(EncryptedID var1);

   NewID getNewID();

   void setNewID(NewID var1);

   NewEncryptedID getNewEncryptedID();

   void setNewEncryptedID(NewEncryptedID var1);

   Terminate getTerminate();

   void setTerminate(Terminate var1);
}
