package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface NameIDMappingRequest extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDMappingRequest";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingRequest", "saml2p");
   String TYPE_LOCAL_NAME = "NameIDMappingRequestType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingRequestType", "saml2p");

   BaseID getBaseID();

   void setBaseID(BaseID var1);

   NameID getNameID();

   void setNameID(NameID var1);

   EncryptedID getEncryptedID();

   void setEncryptedID(EncryptedID var1);

   NameIDPolicy getNameIDPolicy();

   void setNameIDPolicy(NameIDPolicy var1);
}
