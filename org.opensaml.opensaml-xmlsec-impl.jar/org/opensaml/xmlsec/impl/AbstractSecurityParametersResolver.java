package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.security.Key;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Resolver;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.WhitelistBlacklistConfiguration;
import org.opensaml.xmlsec.WhitelistBlacklistParameters;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.keyinfo.NamedKeyInfoGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSecurityParametersResolver implements Resolver {
   private Logger log = LoggerFactory.getLogger(AbstractSecurityParametersResolver.class);

   @Nullable
   protected KeyInfoGenerator lookupKeyInfoGenerator(@Nonnull Credential credential, @Nullable NamedKeyInfoGeneratorManager manager, @Nullable String keyInfoProfileName) {
      Constraint.isNotNull(credential, "Credential may not be null");
      if (manager == null) {
         this.log.trace("NamedKeyInfoGeneratorManger was null, can not resolve");
         return null;
      } else {
         if (this.log.isTraceEnabled()) {
            Key key = CredentialSupport.extractSigningKey(credential);
            if (key == null) {
               key = CredentialSupport.extractEncryptionKey(credential);
            }

            this.log.trace("Attempting to resolve KeyInfoGenerator for credential with key algo '{}' of impl: {}", key != null ? key.getAlgorithm() : "n/a", credential.getClass().getName());
         }

         return KeyInfoSupport.getKeyInfoGenerator(credential, manager, keyInfoProfileName);
      }
   }

   protected void resolveAndPopulateWhiteAndBlacklists(@Nonnull WhitelistBlacklistParameters params, @Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List configs) {
      Collection whitelist = this.resolveEffectiveWhitelist(criteria, configs);
      this.log.trace("Resolved effective whitelist: {}", whitelist);
      Collection blacklist = this.resolveEffectiveBlacklist(criteria, configs);
      this.log.trace("Resolved effective blacklist: {}", blacklist);
      if (whitelist.isEmpty() && blacklist.isEmpty()) {
         this.log.trace("Both empty, nothing to populate");
      } else if (whitelist.isEmpty()) {
         this.log.trace("Whitelist empty, populating blacklist");
         params.setBlacklistedAlgorithms(blacklist);
      } else if (blacklist.isEmpty()) {
         this.log.trace("Blacklist empty, populating whitelist");
         params.setWhitelistedAlgorithms(whitelist);
      } else {
         WhitelistBlacklistConfiguration.Precedence precedence = this.resolveWhitelistBlacklistPrecedence(criteria, configs);
         this.log.trace("Resolved effective precedence: {}", precedence);
         switch (precedence) {
            case WHITELIST:
               this.log.trace("Based on precedence, populating whitelist");
               params.setWhitelistedAlgorithms(whitelist);
               break;
            case BLACKLIST:
               this.log.trace("Based on precedence, populating blacklist");
               params.setBlacklistedAlgorithms(blacklist);
               break;
            default:
               throw new IllegalArgumentException("WhitelistBlacklistPrecedence value is unknown: " + precedence);
         }

      }
   }

   @Nonnull
   protected Predicate resolveWhitelistBlacklistPredicate(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List configs) {
      Collection whitelist = this.resolveEffectiveWhitelist(criteria, configs);
      this.log.trace("Resolved effective whitelist: {}", whitelist);
      Collection blacklist = this.resolveEffectiveBlacklist(criteria, configs);
      this.log.trace("Resolved effective blacklist: {}", blacklist);
      if (whitelist.isEmpty() && blacklist.isEmpty()) {
         this.log.trace("Both empty, returning alwaysTrue predicate");
         return Predicates.alwaysTrue();
      } else if (whitelist.isEmpty()) {
         this.log.trace("Whitelist empty, returning BlacklistPredicate");
         return new BlacklistPredicate(blacklist);
      } else if (blacklist.isEmpty()) {
         this.log.trace("Blacklist empty, returning WhitelistPredicate");
         return new WhitelistPredicate(whitelist);
      } else {
         WhitelistBlacklistConfiguration.Precedence precedence = this.resolveWhitelistBlacklistPrecedence(criteria, configs);
         this.log.trace("Resolved effective precedence: {}", precedence);
         switch (precedence) {
            case WHITELIST:
               this.log.trace("Based on precedence, returning WhitelistPredicate");
               return new WhitelistPredicate(whitelist);
            case BLACKLIST:
               this.log.trace("Based on precedence, returning BlacklistPredicate");
               return new BlacklistPredicate(blacklist);
            default:
               throw new IllegalArgumentException("WhitelistBlacklistPrecedence value is unknown: " + precedence);
         }
      }
   }

   @Nonnull
   protected Collection resolveEffectiveBlacklist(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List configs) {
      LazySet accumulator = new LazySet();
      Iterator var4 = configs.iterator();

      while(var4.hasNext()) {
         WhitelistBlacklistConfiguration config = (WhitelistBlacklistConfiguration)var4.next();
         accumulator.addAll(config.getBlacklistedAlgorithms());
         if (!config.isBlacklistMerge()) {
            break;
         }
      }

      return accumulator;
   }

   @Nonnull
   protected Collection resolveEffectiveWhitelist(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List configs) {
      LazySet accumulator = new LazySet();
      Iterator var4 = configs.iterator();

      while(var4.hasNext()) {
         WhitelistBlacklistConfiguration config = (WhitelistBlacklistConfiguration)var4.next();
         accumulator.addAll(config.getWhitelistedAlgorithms());
         if (!config.isWhitelistMerge()) {
            break;
         }
      }

      return accumulator;
   }

   @Nonnull
   protected WhitelistBlacklistConfiguration.Precedence resolveWhitelistBlacklistPrecedence(@Nonnull CriteriaSet criteria, @Nonnull @NonnullElements @NotEmpty List configs) {
      return ((WhitelistBlacklistConfiguration)configs.get(0)).getWhitelistBlacklistPrecedence();
   }
}
