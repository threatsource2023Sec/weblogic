package org.opensaml.saml.saml2.binding.artifact;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLArtifactContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAML2ArtifactType0004Builder implements SAML2ArtifactBuilder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAML2ArtifactType0004Builder.class);

   @Nullable
   public SAML2ArtifactType0004 buildArtifact(@Nonnull @NotEmpty byte[] artifact) {
      return SAML2ArtifactType0004.parseArtifact(artifact);
   }

   @Nullable
   public SAML2ArtifactType0004 buildArtifact(@Nonnull MessageContext requestContext) {
      try {
         String sourceId = this.getSourceEntityId(requestContext);
         if (sourceId == null) {
            return null;
         } else {
            Integer arsIndex = this.getArsEndpointIndex(requestContext);
            if (arsIndex == null) {
               return null;
            } else {
               byte[] endpointIndex = this.intToByteArray(arsIndex);
               byte[] trimmedIndex = new byte[]{endpointIndex[2], endpointIndex[3]};
               MessageDigest sha1Digester = MessageDigest.getInstance("SHA-1");
               byte[] source = sha1Digester.digest(sourceId.getBytes());
               SecureRandom handleGenerator = SecureRandom.getInstance("SHA1PRNG");
               byte[] assertionHandle = new byte[20];
               handleGenerator.nextBytes(assertionHandle);
               return new SAML2ArtifactType0004(trimmedIndex, source, assertionHandle);
            }
         }
      } catch (NoSuchAlgorithmException var10) {
         this.log.warn("JVM does not support required cryptography algorithms: SHA-1/SHA1PRNG.", var10);
         return null;
      }
   }

   @Nullable
   protected SAMLArtifactContext getArtifactContext(@Nonnull MessageContext requestContext) {
      return (SAMLArtifactContext)requestContext.getSubcontext(SAMLArtifactContext.class);
   }

   @Nullable
   protected Integer getArsEndpointIndex(@Nonnull MessageContext requestContext) {
      SAMLArtifactContext artifactContext = this.getArtifactContext(requestContext);
      if (artifactContext != null && artifactContext.getSourceArtifactResolutionServiceEndpointIndex() != null) {
         return artifactContext.getSourceArtifactResolutionServiceEndpointIndex();
      } else {
         this.log.warn("No artifact resolution service endpoint index is available");
         return null;
      }
   }

   @Nullable
   protected String getSourceEntityId(@Nonnull MessageContext requestContext) {
      SAMLArtifactContext artifactContext = this.getArtifactContext(requestContext);
      if (artifactContext != null) {
         if (artifactContext.getSourceEntityId() != null) {
            return artifactContext.getSourceEntityId();
         }

         this.log.warn("SAMLArtifactContext did not contain a source entityID");
      } else {
         this.log.warn("Message context did not contain a SAMLArtifactContext");
      }

      return null;
   }

   @Nonnull
   @NotEmpty
   private byte[] intToByteArray(int integer) {
      byte[] intBytes = new byte[]{(byte)((integer & -16777216) >>> 24), (byte)((integer & 16711680) >>> 16), (byte)((integer & '\uff00') >>> 8), (byte)(integer & 255)};
      return intBytes;
   }
}
