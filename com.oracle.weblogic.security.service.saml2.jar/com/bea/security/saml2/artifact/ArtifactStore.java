package com.bea.security.saml2.artifact;

import org.opensaml.saml.common.SAMLObject;

public interface ArtifactStore {
   String store(SAMLObject var1, String var2) throws SAML2ArtifactException;

   ArtifactDataObject retrieve(String var1) throws SAML2ArtifactException;

   void updateConfig(int var1, int var2) throws SAML2ArtifactException;
}
