package org.opensaml.saml.saml1.binding.artifact;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLArtifactContext;
import org.opensaml.saml.saml1.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAML1ArtifactType0001Builder implements SAML1ArtifactBuilder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAML1ArtifactType0001Builder.class);

   @Nullable
   public SAML1ArtifactType0001 buildArtifact(byte[] artifact) {
      try {
         return SAML1ArtifactType0001.parseArtifact(artifact);
      } catch (IllegalArgumentException var3) {
         this.log.warn("Error parsing type 1 artifact", var3);
         return null;
      }
   }

   @Nullable
   public SAML1ArtifactType0001 buildArtifact(@Nonnull MessageContext requestContext, @Nonnull Assertion assertion) {
      String sourceId = this.getSourceEntityId(requestContext);
      if (sourceId == null) {
         return null;
      } else {
         try {
            MessageDigest sha1Digester = MessageDigest.getInstance("SHA-1");
            byte[] source = sha1Digester.digest(sourceId.getBytes());
            SecureRandom handleGenerator = SecureRandom.getInstance("SHA1PRNG");
            byte[] assertionHandle = new byte[20];
            handleGenerator.nextBytes(assertionHandle);
            return new SAML1ArtifactType0001(source, assertionHandle);
         } catch (NoSuchAlgorithmException var8) {
            this.log.warn("JVM does not support required cryptography algorithms.", var8);
            return null;
         }
      }
   }

   @Nullable
   protected SAMLArtifactContext getArtifactContext(@Nonnull MessageContext requestContext) {
      return (SAMLArtifactContext)requestContext.getSubcontext(SAMLArtifactContext.class);
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
}
