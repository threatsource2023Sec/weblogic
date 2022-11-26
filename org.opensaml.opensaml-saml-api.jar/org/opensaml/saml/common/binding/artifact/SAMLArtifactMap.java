package org.opensaml.saml.common.binding.artifact;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.saml.common.SAMLObject;

public interface SAMLArtifactMap {
   boolean contains(@Nonnull @NotEmpty String var1) throws IOException;

   void put(@Nonnull @NotEmpty String var1, @Nonnull @NotEmpty String var2, @Nonnull @NotEmpty String var3, @Nonnull SAMLObject var4) throws IOException;

   @Nullable
   SAMLArtifactMapEntry get(@Nonnull @NotEmpty String var1) throws IOException;

   void remove(@Nonnull @NotEmpty String var1) throws IOException;

   public interface SAMLArtifactMapEntryFactory {
      @Nonnull
      SAMLArtifactMapEntry newEntry(@Nonnull @NotEmpty String var1, @Nonnull @NotEmpty String var2, @Nonnull @NotEmpty String var3, @Nonnull SAMLObject var4);
   }

   public interface SAMLArtifactMapEntry {
      @Nonnull
      @NotEmpty
      String getArtifact();

      @Nonnull
      @NotEmpty
      String getIssuerId();

      @Nonnull
      @NotEmpty
      String getRelyingPartyId();

      @Nonnull
      SAMLObject getSamlMessage();
   }
}
