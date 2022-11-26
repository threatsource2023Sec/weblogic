package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface ArtifactResolve extends RequestAbstractType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ArtifactResolve";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResolve", "saml2p");
   String TYPE_LOCAL_NAME = "ArtifactResolveType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResolveType", "saml2p");

   Artifact getArtifact();

   void setArtifact(Artifact var1);
}
