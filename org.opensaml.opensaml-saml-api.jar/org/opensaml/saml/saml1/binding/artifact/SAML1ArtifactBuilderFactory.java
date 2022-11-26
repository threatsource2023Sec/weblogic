package org.opensaml.saml.saml1.binding.artifact;

import java.util.HashMap;
import java.util.Map;
import net.shibboleth.utilities.java.support.codec.Base64Support;

public class SAML1ArtifactBuilderFactory {
   private Map artifactBuilders = new HashMap(2);

   public SAML1ArtifactBuilderFactory() {
      this.artifactBuilders.put(new String(SAML1ArtifactType0001.TYPE_CODE), new SAML1ArtifactType0001Builder());
      this.artifactBuilders.put(new String(SAML1ArtifactType0002.TYPE_CODE), new SAML1ArtifactType0002Builder());
   }

   public Map getArtifactBuilders() {
      return this.artifactBuilders;
   }

   public SAML1ArtifactBuilder getArtifactBuilder(byte[] type) {
      return (SAML1ArtifactBuilder)this.artifactBuilders.get(new String(type));
   }

   public AbstractSAML1Artifact buildArtifact(String base64Artifact) {
      return this.buildArtifact(Base64Support.decode(base64Artifact));
   }

   public AbstractSAML1Artifact buildArtifact(byte[] artifact) {
      if (artifact == null) {
         return null;
      } else {
         byte[] type = new byte[]{artifact[0], artifact[1]};
         SAML1ArtifactBuilder artifactBuilder = this.getArtifactBuilder(type);
         if (artifactBuilder == null) {
            throw new IllegalArgumentException("Saw unsupported artifact type: " + new String(type));
         } else {
            return artifactBuilder.buildArtifact(artifact);
         }
      }
   }
}
