package org.opensaml.xmlsec.impl;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration.Precedence;

public class BasicWhitelistBlacklistConfiguration implements WhitelistBlacklistConfiguration {
   public static final WhitelistBlacklistConfiguration.Precedence DEFAULT_PRECEDENCE;
   private Collection whitelist = Collections.emptySet();
   private boolean whitelistMerge;
   private Collection blacklist = Collections.emptySet();
   private boolean blacklistMerge;
   private WhitelistBlacklistConfiguration.Precedence precedence;

   public BasicWhitelistBlacklistConfiguration() {
      this.precedence = DEFAULT_PRECEDENCE;
      this.blacklistMerge = true;
      this.whitelistMerge = false;
   }

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   public Collection getWhitelistedAlgorithms() {
      return ImmutableSet.copyOf(this.whitelist);
   }

   public void setWhitelistedAlgorithms(@Nullable Collection uris) {
      if (uris == null) {
         this.whitelist = Collections.emptySet();
      } else {
         this.whitelist = new HashSet(StringSupport.normalizeStringCollection(uris));
      }
   }

   public boolean isWhitelistMerge() {
      return this.whitelistMerge;
   }

   public void setWhitelistMerge(boolean flag) {
      this.whitelistMerge = flag;
   }

   @Nonnull
   @NonnullElements
   @NotLive
   @Unmodifiable
   public Collection getBlacklistedAlgorithms() {
      return ImmutableSet.copyOf(this.blacklist);
   }

   public void setBlacklistedAlgorithms(@Nullable Collection uris) {
      if (uris == null) {
         this.blacklist = Collections.emptySet();
      } else {
         this.blacklist = new HashSet(StringSupport.normalizeStringCollection(uris));
      }
   }

   public boolean isBlacklistMerge() {
      return this.blacklistMerge;
   }

   public void setBlacklistMerge(boolean flag) {
      this.blacklistMerge = flag;
   }

   @Nonnull
   public WhitelistBlacklistConfiguration.Precedence getWhitelistBlacklistPrecedence() {
      return this.precedence;
   }

   public void setWhitelistBlacklistPrecedence(@Nonnull WhitelistBlacklistConfiguration.Precedence value) {
      this.precedence = (WhitelistBlacklistConfiguration.Precedence)Constraint.isNotNull(value, "Precedence may not be null");
   }

   static {
      DEFAULT_PRECEDENCE = Precedence.WHITELIST;
   }
}
