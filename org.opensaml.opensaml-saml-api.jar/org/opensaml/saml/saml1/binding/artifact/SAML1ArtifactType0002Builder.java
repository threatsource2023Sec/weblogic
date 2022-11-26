package org.opensaml.saml.saml1.binding.artifact;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.messaging.context.SAMLArtifactContext;
import org.opensaml.saml.saml1.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAML1ArtifactType0002Builder implements SAML1ArtifactBuilder {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SAML1ArtifactType0002Builder.class);

   @Nullable
   public SAML1ArtifactType0002 buildArtifact(@Nonnull @NotEmpty byte[] artifact) {
      try {
         return SAML1ArtifactType0002.parseArtifact(artifact);
      } catch (IllegalArgumentException var3) {
         this.log.warn("Error parsing type 2 artifact", var3);
         return null;
      }
   }

   @Nullable
   public SAML1ArtifactType0002 buildArtifact(@Nonnull MessageContext requestContext, @Nonnull Assertion assertion) {
      try {
         String sourceLocation = this.getArsEndpointUrl(requestContext);
         if (sourceLocation == null) {
            return null;
         } else {
            SecureRandom handleGenerator = SecureRandom.getInstance("SHA1PRNG");
            byte[] assertionHandle = new byte[20];
            handleGenerator.nextBytes(assertionHandle);
            return new SAML1ArtifactType0002(assertionHandle, sourceLocation);
         }
      } catch (NoSuchAlgorithmException var6) {
         this.log.warn("JVM does not support required cryptography algorithms: SHA1PRNG.", var6);
         return null;
      }
   }

   @Nullable
   protected SAMLArtifactContext getArtifactContext(@Nonnull MessageContext requestContext) {
      return (SAMLArtifactContext)requestContext.getSubcontext(SAMLArtifactContext.class);
   }

   @Nullable
   protected String getArsEndpointUrl(@Nonnull MessageContext requestContext) {
      SAMLArtifactContext artifactContext = this.getArtifactContext(requestContext);
      if (artifactContext != null && artifactContext.getSourceArtifactResolutionServiceEndpointURL() != null) {
         return artifactContext.getSourceArtifactResolutionServiceEndpointURL();
      } else {
         this.log.warn("No artifact resolution service endpoint URL is available");
         return null;
      }
   }
}
