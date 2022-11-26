package org.opensaml.xmlsec;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class WhitelistBlacklistParameters {
   @Nonnull
   @NonnullElements
   private Collection whiteListedAlgorithmURIs = Collections.emptySet();
   @Nonnull
   @NonnullElements
   private Collection blackListedAlgorithmURIs = Collections.emptySet();

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   public Collection getWhitelistedAlgorithms() {
      return ImmutableSet.copyOf(this.whiteListedAlgorithmURIs);
   }

   public void setWhitelistedAlgorithms(@Nullable Collection uris) {
      if (uris == null) {
         this.whiteListedAlgorithmURIs = Collections.emptySet();
      } else {
         this.whiteListedAlgorithmURIs = new HashSet(StringSupport.normalizeStringCollection(uris));
      }
   }

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   public Collection getBlacklistedAlgorithms() {
      return ImmutableSet.copyOf(this.blackListedAlgorithmURIs);
   }

   public void setBlacklistedAlgorithms(@Nonnull @NonnullElements Collection uris) {
      if (uris == null) {
         this.blackListedAlgorithmURIs = Collections.emptySet();
      } else {
         this.blackListedAlgorithmURIs = new HashSet(StringSupport.normalizeStringCollection(uris));
      }
   }
}
