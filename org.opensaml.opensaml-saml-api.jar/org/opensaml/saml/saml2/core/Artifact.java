package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Artifact extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Artifact";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Artifact", "saml2p");
   String TYPE_LOCAL_NAME = "ArtifactType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactType", "saml2p");

   String getArtifact();

   void setArtifact(String var1);
}
