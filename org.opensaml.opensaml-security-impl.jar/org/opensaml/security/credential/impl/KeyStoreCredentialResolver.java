package org.opensaml.security.credential.impl;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.UnrecoverableEntryException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.x509.BasicX509Credential;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStoreCredentialResolver extends AbstractCriteriaFilteringCredentialResolver {
   private final Logger log;
   private final KeyStore keyStore;
   private final Map keyPasswords;
   private final UsageType keystoreUsage;

   public KeyStoreCredentialResolver(@Nonnull KeyStore store, @Nonnull Map passwords) {
      this(store, passwords, (UsageType)null);
   }

   public KeyStoreCredentialResolver(@Nonnull KeyStore store, @Nonnull Map passwords, @Nullable UsageType usage) {
      this.log = LoggerFactory.getLogger(KeyStoreCredentialResolver.class);
      this.keyStore = (KeyStore)Constraint.isNotNull(store, "Provided key store cannot be null");
      this.keyPasswords = (Map)Constraint.isNotNull(passwords, "Password map cannot be null");

      try {
         store.size();
      } catch (KeyStoreException var5) {
         throw new IllegalStateException("Keystore has not been initialized.");
      }

      if (usage != null) {
         this.keystoreUsage = usage;
      } else {
         this.keystoreUsage = UsageType.UNSPECIFIED;
      }

   }

   @Nonnull
   protected Iterable resolveFromSource(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      this.checkCriteriaRequirements(criteriaSet);
      String entityID = ((EntityIdCriterion)criteriaSet.get(EntityIdCriterion.class)).getEntityId();
      UsageCriterion usageCriteria = (UsageCriterion)criteriaSet.get(UsageCriterion.class);
      UsageType usage;
      if (usageCriteria != null) {
         usage = usageCriteria.getUsage();
      } else {
         usage = UsageType.UNSPECIFIED;
      }

      if (!this.matchUsage(this.keystoreUsage, usage)) {
         this.log.debug("Specified usage criteria {} does not match keystore usage {}", usage, this.keystoreUsage);
         this.log.debug("Can not resolve credentials from this keystore");
         return Collections.emptySet();
      } else {
         KeyStore.PasswordProtection keyPassword = null;
         if (this.keyPasswords.containsKey(entityID)) {
            keyPassword = new KeyStore.PasswordProtection(((String)this.keyPasswords.get(entityID)).toCharArray());
         }

         KeyStore.Entry keyStoreEntry = null;

         try {
            keyStoreEntry = this.keyStore.getEntry(entityID, keyPassword);
         } catch (UnrecoverableEntryException var8) {
            this.log.error("Unable to retrieve keystore entry for entityID (keystore alias): {}", entityID);
            this.log.error("Check for invalid keystore entityID/alias entry password");
            throw new ResolverException("Could not retrieve entry from keystore", var8);
         } catch (GeneralSecurityException var9) {
            this.log.error("Unable to retrieve keystore entry for entityID (keystore alias): {}", entityID, var9);
            throw new ResolverException("Could not retrieve entry from keystore", var9);
         }

         if (keyStoreEntry == null) {
            this.log.debug("Keystore entry for entityID (keystore alias) {} does not exist", entityID);
            return Collections.emptySet();
         } else {
            Credential credential = this.buildCredential(keyStoreEntry, entityID, this.keystoreUsage);
            return Collections.singleton(credential);
         }
      }
   }

   protected void checkCriteriaRequirements(@Nullable CriteriaSet criteriaSet) {
      if (criteriaSet == null || criteriaSet.get(EntityIdCriterion.class) == null) {
         this.log.error("EntityIDCriterion was not specified in the criteria set, resolution cannot be attempted");
         throw new IllegalArgumentException("No EntityIDCriterion was available in criteria set");
      }
   }

   protected boolean matchUsage(@Nonnull UsageType keyStoreUsage, @Nonnull UsageType criteriaUsage) {
      if (keyStoreUsage != UsageType.UNSPECIFIED && criteriaUsage != UsageType.UNSPECIFIED) {
         return keyStoreUsage == criteriaUsage;
      } else {
         return true;
      }
   }

   @Nonnull
   protected Credential buildCredential(@Nonnull KeyStore.Entry keyStoreEntry, @Nonnull String entityID, @Nonnull UsageType usage) throws ResolverException {
      this.log.debug("Building credential from keystore entry for entityID {}, usage type {}", entityID, usage);
      if (keyStoreEntry instanceof KeyStore.PrivateKeyEntry) {
         return this.processPrivateKeyEntry((KeyStore.PrivateKeyEntry)keyStoreEntry, entityID, this.keystoreUsage);
      } else if (keyStoreEntry instanceof KeyStore.TrustedCertificateEntry) {
         return this.processTrustedCertificateEntry((KeyStore.TrustedCertificateEntry)keyStoreEntry, entityID, this.keystoreUsage);
      } else if (keyStoreEntry instanceof KeyStore.SecretKeyEntry) {
         return this.processSecretKeyEntry((KeyStore.SecretKeyEntry)keyStoreEntry, entityID, this.keystoreUsage);
      } else {
         throw new ResolverException("KeyStore entry was of an unsupported type: " + keyStoreEntry.getClass().getName());
      }
   }

   protected X509Credential processTrustedCertificateEntry(@Nonnull KeyStore.TrustedCertificateEntry trustedCertEntry, @Nonnull String entityID, @Nonnull UsageType usage) {
      this.log.debug("Processing TrustedCertificateEntry from keystore");
      X509Certificate cert = (X509Certificate)trustedCertEntry.getTrustedCertificate();
      BasicX509Credential credential = new BasicX509Credential(cert);
      credential.setEntityId(entityID);
      credential.setUsageType(usage);
      ArrayList certChain = new ArrayList();
      certChain.add(cert);
      credential.setEntityCertificateChain(certChain);
      return credential;
   }

   protected X509Credential processPrivateKeyEntry(@Nonnull KeyStore.PrivateKeyEntry privateKeyEntry, @Nonnull String entityID, @Nonnull UsageType usage) {
      this.log.debug("Processing PrivateKeyEntry from keystore");
      BasicX509Credential credential = new BasicX509Credential((X509Certificate)privateKeyEntry.getCertificate(), privateKeyEntry.getPrivateKey());
      credential.setEntityId(entityID);
      credential.setUsageType(usage);
      credential.setEntityCertificateChain(Arrays.asList((X509Certificate[])((X509Certificate[])privateKeyEntry.getCertificateChain())));
      return credential;
   }

   protected Credential processSecretKeyEntry(@Nonnull KeyStore.SecretKeyEntry secretKeyEntry, @Nonnull String entityID, @Nonnull UsageType usage) {
      this.log.debug("Processing SecretKeyEntry from keystore");
      BasicCredential credential = new BasicCredential(secretKeyEntry.getSecretKey());
      credential.setEntityId(entityID);
      credential.setUsageType(usage);
      return credential;
   }
}
