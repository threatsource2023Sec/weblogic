package org.opensaml.xmlsec;

import java.util.Collection;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;

public interface WhitelistBlacklistConfiguration {
   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   Collection getWhitelistedAlgorithms();

   boolean isWhitelistMerge();

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   Collection getBlacklistedAlgorithms();

   boolean isBlacklistMerge();

   @Nonnull
   Precedence getWhitelistBlacklistPrecedence();

   public static enum Precedence {
      WHITELIST,
      BLACKLIST;
   }
}
