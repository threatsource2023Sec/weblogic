package org.opensaml.saml.common.profile;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;

public final class SAMLEventIds {
   @Nonnull
   @NotEmpty
   public static final String ASYNC_LOGOUT = "AsyncLogout";
   @Nonnull
   @NotEmpty
   public static final String CHANNEL_BINDINGS_ERROR = "ChannelBindingsError";
   @Nonnull
   @NotEmpty
   public static final String DECRYPT_ASSERTION_FAILED = "DecryptAssertionFailed";
   @Nonnull
   @NotEmpty
   public static final String DECRYPT_ATTRIBUTE_FAILED = "DecryptAttributeFailed";
   @Nonnull
   @NotEmpty
   public static final String DECRYPT_NAMEID_FAILED = "DecryptNameIDFailed";
   @Nonnull
   @NotEmpty
   public static final String ENDPOINT_RESOLUTION_FAILED = "EndpointResolutionFailed";
   @Nonnull
   @NotEmpty
   public static final String INVALID_NAMEID_POLICY = "InvalidNameIDPolicy";
   @Nonnull
   @NotEmpty
   public static final String SESSION_NOT_FOUND = "SessionNotFound";
   @Nonnull
   @NotEmpty
   public static final String UNABLE_RESOLVE_ARTIFACT = "UnableToResolveArtifact";

   private SAMLEventIds() {
   }
}
