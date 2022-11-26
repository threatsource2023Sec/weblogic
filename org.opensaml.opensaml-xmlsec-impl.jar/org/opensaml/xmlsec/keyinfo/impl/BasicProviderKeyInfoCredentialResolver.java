package org.opensaml.xmlsec.keyinfo.impl;

import java.security.Key;
import java.security.KeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.AbstractCriteriaFilteringCredentialResolver;
import org.opensaml.security.crypto.KeySupport;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCriterion;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicProviderKeyInfoCredentialResolver extends AbstractCriteriaFilteringCredentialResolver implements KeyInfoCredentialResolver {
   private final Logger log = LoggerFactory.getLogger(BasicProviderKeyInfoCredentialResolver.class);
   private final List providers = new ArrayList();

   public BasicProviderKeyInfoCredentialResolver(@Nonnull List keyInfoProviders) {
      this.providers.addAll(keyInfoProviders);
   }

   @Nonnull
   protected List getProviders() {
      return this.providers;
   }

   @Nonnull
   protected Iterable resolveFromSource(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      KeyInfoCriterion kiCriteria = null;
      if (criteriaSet != null) {
         kiCriteria = (KeyInfoCriterion)criteriaSet.get(KeyInfoCriterion.class);
      }

      if (kiCriteria == null) {
         this.log.error("No KeyInfo criteria supplied, resolver could not process");
         throw new ResolverException("Credential criteria set did not contain an instance of KeyInfoCredentialCriteria");
      } else {
         KeyInfo keyInfo = kiCriteria.getKeyInfo();
         List credentials = new ArrayList();
         KeyInfoResolutionContext kiContext = new KeyInfoResolutionContext(credentials);
         if (keyInfo != null) {
            this.processKeyInfo(keyInfo, kiContext, criteriaSet, credentials);
         } else {
            this.log.info("KeyInfo was null, any credentials will be resolved by post-processing hooks only");
         }

         this.postProcess(kiContext, criteriaSet, credentials);
         if (credentials.isEmpty()) {
            this.log.debug("No credentials were found, calling empty credentials post-processing hook");
            this.postProcessEmptyCredentials(kiContext, criteriaSet, credentials);
         }

         this.log.debug("A total of {} credentials were resolved", credentials.size());
         return credentials;
      }
   }

   private void processKeyInfo(@Nonnull KeyInfo keyInfo, @Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List credentials) throws ResolverException {
      this.initResolutionContext(kiContext, keyInfo, criteriaSet);
      Key keyValueKey = kiContext.getKey();
      HashSet keyNames = new HashSet();
      keyNames.addAll(kiContext.getKeyNames());
      this.processKeyInfoChildren(kiContext, criteriaSet, credentials);
      if (credentials.isEmpty() && keyValueKey != null) {
         Credential keyValueCredential = this.buildBasicCredential(keyValueKey, keyNames);
         if (keyValueCredential != null) {
            this.log.debug("No credentials were extracted by registered non-KeyValue handling providers, adding KeyValue credential to returned credential set");
            credentials.add(keyValueCredential);
         }
      }

   }

   protected void postProcess(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List credentials) throws ResolverException {
   }

   protected void postProcessEmptyCredentials(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List credentials) throws ResolverException {
   }

   protected void processKeyInfoChildren(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List credentials) throws ResolverException {
      Iterator var4 = kiContext.getKeyInfo().getXMLObjects().iterator();

      while(true) {
         while(true) {
            XMLObject keyInfoChild;
            do {
               do {
                  if (!var4.hasNext()) {
                     return;
                  }

                  keyInfoChild = (XMLObject)var4.next();
               } while(keyInfoChild instanceof KeyValue);
            } while(keyInfoChild instanceof DEREncodedKeyValue);

            this.log.debug("Processing KeyInfo child with QName: {}", keyInfoChild.getElementQName());
            Collection childCreds = this.processKeyInfoChild(kiContext, criteriaSet, keyInfoChild);
            if (childCreds != null && !childCreds.isEmpty()) {
               credentials.addAll(childCreds);
            } else if (keyInfoChild instanceof KeyName) {
               this.log.debug("KeyName {} did not independently produce a credential based on any registered providers", ((KeyName)keyInfoChild).getValue());
            } else {
               this.log.warn("No credentials could be extracted from KeyInfo child with QName {} by any registered provider", keyInfoChild.getElementQName());
            }
         }
      }
   }

   @Nullable
   protected Collection processKeyInfoChild(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull XMLObject keyInfoChild) throws ResolverException {
      Iterator var4 = this.getProviders().iterator();

      while(var4.hasNext()) {
         KeyInfoProvider provider = (KeyInfoProvider)var4.next();
         if (!provider.handles(keyInfoChild)) {
            this.log.debug("Provider {} doesn't handle objects of type {}, skipping", provider.getClass().getName(), keyInfoChild.getElementQName());
         } else {
            this.log.debug("Processing KeyInfo child {} with provider {}", keyInfoChild.getElementQName(), provider.getClass().getName());

            Collection creds;
            try {
               creds = provider.process(this, keyInfoChild, criteriaSet, kiContext);
            } catch (SecurityException var8) {
               throw new ResolverException("Error processing KeyInfo child element", var8);
            }

            if (creds != null && !creds.isEmpty()) {
               this.log.debug("Credentials successfully extracted from child {} by provider {}", keyInfoChild.getElementQName(), provider.getClass().getName());
               return creds;
            }
         }
      }

      return null;
   }

   protected void initResolutionContext(@Nonnull KeyInfoResolutionContext kiContext, @Nonnull KeyInfo keyInfo, @Nullable CriteriaSet criteriaSet) throws ResolverException {
      kiContext.setKeyInfo(keyInfo);
      kiContext.getKeyNames().addAll(KeyInfoSupport.getKeyNames(keyInfo));
      this.log.debug("Found {} key names: {}", kiContext.getKeyNames().size(), kiContext.getKeyNames());
      this.resolveKeyValue(kiContext, criteriaSet, keyInfo.getKeyValues());
      this.resolveKeyValue(kiContext, criteriaSet, keyInfo.getDEREncodedKeyValues());
   }

   protected void resolveKeyValue(@Nonnull KeyInfoResolutionContext kiContext, @Nullable CriteriaSet criteriaSet, @Nonnull List keyValues) throws ResolverException {
      Iterator var4 = keyValues.iterator();

      while(true) {
         Collection creds;
         do {
            XMLObject keyValue;
            do {
               if (!var4.hasNext()) {
                  return;
               }

               keyValue = (XMLObject)var4.next();
            } while(!(keyValue instanceof KeyValue) && !(keyValue instanceof DEREncodedKeyValue));

            creds = this.processKeyInfoChild(kiContext, criteriaSet, keyValue);
         } while(creds == null);

         Iterator var7 = creds.iterator();

         while(var7.hasNext()) {
            Credential cred = (Credential)var7.next();
            Key key = this.extractKeyValue(cred);
            if (key != null) {
               kiContext.setKey(key);
               this.log.debug("Found a credential based on a KeyValue/DEREncodedKeyValue having key type: {}", key.getAlgorithm());
               return;
            }
         }
      }
   }

   @Nullable
   protected Credential buildBasicCredential(@Nullable Key key, @Nonnull Set keyNames) throws ResolverException {
      if (key == null) {
         this.log.debug("Key supplied was null, could not build credential");
         return null;
      } else {
         BasicCredential basicCred = null;
         if (key instanceof PublicKey) {
            basicCred = new BasicCredential((PublicKey)key);
         } else if (key instanceof SecretKey) {
            basicCred = new BasicCredential((SecretKey)key);
         } else if (key instanceof PrivateKey) {
            PrivateKey privateKey = (PrivateKey)key;

            try {
               PublicKey publicKey = KeySupport.derivePublicKey(privateKey);
               if (publicKey != null) {
                  basicCred = new BasicCredential(publicKey, privateKey);
               } else {
                  this.log.error("Failed to derive public key from private key");
               }
            } catch (KeyException var6) {
               this.log.error("Could not derive public key from private key", var6);
            }
         } else {
            this.log.error("Key was of an unsupported type '{}'", key.getClass().getName());
         }

         if (basicCred != null) {
            basicCred.getKeyNames().addAll(keyNames);
         }

         return basicCred;
      }
   }

   @Nullable
   protected Key extractKeyValue(@Nullable Credential cred) {
      if (cred != null) {
         if (cred.getPublicKey() != null) {
            return cred.getPublicKey();
         }

         if (cred.getSecretKey() != null) {
            return cred.getSecretKey();
         }

         if (cred.getPrivateKey() != null) {
            return cred.getPrivateKey();
         }
      }

      return null;
   }
}
