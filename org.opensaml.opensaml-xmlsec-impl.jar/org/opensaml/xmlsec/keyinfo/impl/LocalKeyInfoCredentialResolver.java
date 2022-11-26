package org.opensaml.xmlsec.keyinfo.impl;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.criteria.KeyNameCriterion;
import org.opensaml.security.criteria.PublicKeyCriterion;

public class LocalKeyInfoCredentialResolver extends BasicProviderKeyInfoCredentialResolver {
   private final CredentialResolver localCredResolver;

   public LocalKeyInfoCredentialResolver(@Nonnull List keyInfoProviders, @Nonnull CredentialResolver localCredentialResolver) {
      super(keyInfoProviders);
      this.localCredResolver = (CredentialResolver)Constraint.isNotNull(localCredentialResolver, "Local credential resolver cannot be null");
   }

   @Nonnull
   public CredentialResolver getLocalCredentialResolver() {
      return this.localCredResolver;
   }

   protected void postProcess(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List credentials) throws ResolverException {
      ArrayList localCreds = new ArrayList();
      Iterator var5 = credentials.iterator();

      while(var5.hasNext()) {
         Credential cred = (Credential)var5.next();
         if (this.isLocalCredential(cred)) {
            localCreds.add(cred);
         } else if (cred.getPublicKey() != null) {
            localCreds.addAll(this.resolveByPublicKey(cred.getPublicKey()));
         }
      }

      var5 = kiContext.getKeyNames().iterator();

      while(var5.hasNext()) {
         String keyName = (String)var5.next();
         localCreds.addAll(this.resolveByKeyName(keyName));
      }

      credentials.clear();
      credentials.addAll(localCreds);
   }

   protected boolean isLocalCredential(@Nonnull Credential credential) {
      return credential.getPrivateKey() != null || credential.getSecretKey() != null;
   }

   @Nonnull
   protected Collection resolveByKeyName(@Nonnull String keyName) throws ResolverException {
      ArrayList localCreds = new ArrayList();
      CriteriaSet criteriaSet = new CriteriaSet(new Criterion[]{new KeyNameCriterion(keyName)});
      Iterator var4 = this.getLocalCredentialResolver().resolve(criteriaSet).iterator();

      while(var4.hasNext()) {
         Credential cred = (Credential)var4.next();
         if (this.isLocalCredential(cred)) {
            localCreds.add(cred);
         }
      }

      return localCreds;
   }

   @Nonnull
   protected Collection resolveByPublicKey(@Nonnull PublicKey publicKey) throws ResolverException {
      ArrayList localCreds = new ArrayList();
      CriteriaSet criteriaSet = new CriteriaSet(new Criterion[]{new PublicKeyCriterion(publicKey)});
      Iterator var4 = this.getLocalCredentialResolver().resolve(criteriaSet).iterator();

      while(var4.hasNext()) {
         Credential cred = (Credential)var4.next();
         if (this.isLocalCredential(cred)) {
            localCreds.add(cred);
         }
      }

      return localCreds;
   }
}
