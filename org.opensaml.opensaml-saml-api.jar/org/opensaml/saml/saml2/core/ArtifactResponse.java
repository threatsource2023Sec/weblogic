package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface ArtifactResponse extends StatusResponseType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ArtifactResponse";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResponse", "saml2p");
   String TYPE_LOCAL_NAME = "ArtifactResponseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResponseType", "saml2p");

   SAMLObject getMessage();

   void setMessage(SAMLObject var1);
}
