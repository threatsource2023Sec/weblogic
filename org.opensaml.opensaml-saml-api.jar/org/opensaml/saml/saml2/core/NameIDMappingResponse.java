package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface NameIDMappingResponse extends StatusResponseType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDMappingResponse";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingResponse", "saml2p");
   String TYPE_LOCAL_NAME = "NameIDMappingResponseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingResponseType", "saml2p");

   NameID getNameID();

   void setNameID(NameID var1);

   EncryptedID getEncryptedID();

   void setEncryptedID(EncryptedID var1);
}
