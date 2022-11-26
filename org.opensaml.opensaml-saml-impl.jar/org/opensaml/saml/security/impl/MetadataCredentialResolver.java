package org.opensaml.saml.security.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LockableClassToInstanceMultiMap;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.MutableCredential;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.credential.impl.AbstractCriteriaFilteringCredentialResolver;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataCredentialResolver extends AbstractCriteriaFilteringCredentialResolver implements InitializableComponent {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(MetadataCredentialResolver.class);
   @Nullable
   private RoleDescriptorResolver roleDescriptorResolver;
   @NonnullAfterInit
   private KeyInfoCredentialResolver keyInfoCredentialResolver;
   private boolean isInitialized;

   public boolean isInitialized() {
      return this.isInitialized;
   }

   public void initialize() throws ComponentInitializationException {
      if (this.getKeyInfoCredentialResolver() == null) {
         throw new ComponentInitializationException("A KeyInfoCredentialResolver instance is required");
      } else {
         if (this.getRoleDescriptorResolver() == null) {
            this.log.info("RoleDescriptorResolver was not supplied, credentials may only be resolved via RoleDescriptorCriterion");
         }

         this.isInitialized = true;
      }
   }

   @Nullable
   public RoleDescriptorResolver getRoleDescriptorResolver() {
      return this.roleDescriptorResolver;
   }

   public void setRoleDescriptorResolver(@Nullable RoleDescriptorResolver resolver) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.roleDescriptorResolver = resolver;
   }

   @NonnullAfterInit
   public KeyInfoCredentialResolver getKeyInfoCredentialResolver() {
      return this.keyInfoCredentialResolver;
   }

   public void setKeyInfoCredentialResolver(@Nonnull KeyInfoCredentialResolver resolver) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keyInfoCredentialResolver = (KeyInfoCredentialResolver)Constraint.isNotNull(resolver, "KeyInfoCredentialResolver may not be null");
   }

   @Nonnull
   protected Iterable resolveFromSource(@Nonnull CriteriaSet criteriaSet) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
      UsageType usage = this.getEffectiveUsageInput(criteriaSet);
      if (criteriaSet.contains(RoleDescriptorCriterion.class)) {
         RoleDescriptor roleDescriptor = ((RoleDescriptorCriterion)criteriaSet.get(RoleDescriptorCriterion.class)).getRole();
         return this.resolveFromRoleDescriptor(criteriaSet, roleDescriptor, usage);
      } else if (criteriaSet.contains(EntityIdCriterion.class) && criteriaSet.contains(EntityRoleCriterion.class)) {
         if (this.getRoleDescriptorResolver() == null) {
            throw new ResolverException("EntityID and role input were supplied but no RoleDescriptorResolver is configured");
         } else {
            String entityID = ((EntityIdCriterion)criteriaSet.get(EntityIdCriterion.class)).getEntityId();
            QName role = ((EntityRoleCriterion)criteriaSet.get(EntityRoleCriterion.class)).getRole();
            String protocol = null;
            ProtocolCriterion protocolCriteria = (ProtocolCriterion)criteriaSet.get(ProtocolCriterion.class);
            if (protocolCriteria != null) {
               protocol = protocolCriteria.getProtocol();
            }

            return this.resolveFromMetadata(criteriaSet, entityID, role, protocol, usage);
         }
      } else {
         throw new ResolverException("Criteria contained neither RoleDescriptorCriterion nor EntityIdCriterion + EntityRoleCriterion, could not perform resolution");
      }
   }

   @Nonnull
   protected UsageType getEffectiveUsageInput(@Nonnull CriteriaSet criteriaSet) {
      UsageCriterion usageCriteria = (UsageCriterion)criteriaSet.get(UsageCriterion.class);
      return usageCriteria != null ? usageCriteria.getUsage() : UsageType.UNSPECIFIED;
   }

   @Nonnull
   protected Collection resolveFromRoleDescriptor(@Nonnull CriteriaSet criteriaSet, @Nonnull RoleDescriptor roleDescriptor, @Nonnull UsageType usage) throws ResolverException {
      String entityID = null;
      if (roleDescriptor.getParent() instanceof EntityDescriptor) {
         entityID = ((EntityDescriptor)roleDescriptor.getParent()).getEntityID();
      }

      this.log.debug("Resolving credentials from supplied RoleDescriptor using usage: {}.  Effective entityID was: {}", usage, entityID);
      HashSet credentials = new HashSet(3);
      this.processRoleDescriptor(credentials, roleDescriptor, entityID, usage);
      return credentials;
   }

   @Nonnull
   protected Collection resolveFromMetadata(@Nonnull CriteriaSet criteriaSet, @Nonnull @NotEmpty String entityID, @Nonnull QName role, @Nullable String protocol, @Nonnull UsageType usage) throws ResolverException {
      this.log.debug("Resolving credentials from metadata using entityID: {}, role: {}, protocol: {}, usage: {}", new Object[]{entityID, role, protocol, usage});
      HashSet credentials = new HashSet(3);
      Iterable roleDescriptors = this.getRoleDescriptors(criteriaSet, entityID, role, protocol);
      Iterator var8 = roleDescriptors.iterator();

      while(var8.hasNext()) {
         RoleDescriptor roleDescriptor = (RoleDescriptor)var8.next();
         this.processRoleDescriptor(credentials, roleDescriptor, entityID, usage);
      }

      return credentials;
   }

   protected void processRoleDescriptor(@Nonnull HashSet accumulator, @Nonnull RoleDescriptor roleDescriptor, @Nullable String entityID, @Nonnull UsageType usage) throws ResolverException {
      List keyDescriptors = roleDescriptor.getKeyDescriptors();
      Iterator var6 = keyDescriptors.iterator();

      while(var6.hasNext()) {
         KeyDescriptor keyDescriptor = (KeyDescriptor)var6.next();
         UsageType mdUsage = keyDescriptor.getUse();
         if (mdUsage == null) {
            mdUsage = UsageType.UNSPECIFIED;
         }

         if (this.matchUsage(mdUsage, usage) && keyDescriptor.getKeyInfo() != null) {
            this.extractCredentials(accumulator, keyDescriptor, entityID, mdUsage);
         }
      }

   }

   protected void extractCredentials(@Nonnull HashSet accumulator, @Nonnull KeyDescriptor keyDescriptor, @Nullable String entityID, @Nonnull UsageType mdUsage) throws ResolverException {
      LockableClassToInstanceMultiMap keyDescriptorObjectMetadata = keyDescriptor.getObjectMetadata();
      ReadWriteLock rwlock = keyDescriptorObjectMetadata.getReadWriteLock();

      List cachedCreds;
      label158: {
         try {
            rwlock.readLock().lock();
            cachedCreds = keyDescriptorObjectMetadata.get(Credential.class);
            if (cachedCreds.isEmpty()) {
               this.log.debug("Found no cached credentials in KeyDescriptor object metadata, resolving from KeyInfo");
               break label158;
            }

            this.log.debug("Resolved cached credentials from KeyDescriptor object metadata");
            accumulator.addAll(cachedCreds);
         } finally {
            rwlock.readLock().unlock();
         }

         return;
      }

      try {
         rwlock.writeLock().lock();
         cachedCreds = keyDescriptorObjectMetadata.get(Credential.class);
         if (cachedCreds.isEmpty()) {
            List newCreds = new ArrayList();
            CriteriaSet critSet = new CriteriaSet();
            critSet.add(new KeyInfoCriterion(keyDescriptor.getKeyInfo()));
            Iterable resolvedCreds = this.getKeyInfoCredentialResolver().resolve(critSet);
            Iterator var11 = resolvedCreds.iterator();

            while(var11.hasNext()) {
               Credential cred = (Credential)var11.next();
               if (cred instanceof MutableCredential) {
                  MutableCredential mutableCred = (MutableCredential)cred;
                  mutableCred.setEntityId(entityID);
                  mutableCred.setUsageType(mdUsage);
               }

               cred.getCredentialContextSet().add(new SAMLMDCredentialContext(keyDescriptor));
               newCreds.add(cred);
            }

            keyDescriptorObjectMetadata.putAll(newCreds);
            accumulator.addAll(newCreds);
            return;
         }

         this.log.debug("Credentials were resolved and cached by another thread while this thread was waiting on the write lock");
         accumulator.addAll(cachedCreds);
      } finally {
         rwlock.writeLock().unlock();
      }

   }

   protected boolean matchUsage(@Nonnull UsageType metadataUsage, @Nonnull UsageType criteriaUsage) {
      if (metadataUsage != UsageType.UNSPECIFIED && criteriaUsage != UsageType.UNSPECIFIED) {
         return metadataUsage == criteriaUsage;
      } else {
         return true;
      }
   }

   @Nonnull
   protected Iterable getRoleDescriptors(@Nonnull CriteriaSet criteriaSet, @Nonnull String entityID, @Nonnull QName role, @Nullable String protocol) throws ResolverException {
      if (this.getRoleDescriptorResolver() == null) {
         throw new ResolverException("No RoleDescriptorResolver is configured");
      } else {
         try {
            if (this.log.isDebugEnabled()) {
               this.log.debug("Retrieving role descriptor metadata for entity '{}' in role '{}' for protocol '{}'", new Object[]{entityID, role, protocol});
            }

            CriteriaSet criteria = new CriteriaSet(new Criterion[]{new EntityIdCriterion(entityID), new EntityRoleCriterion(role)});
            if (protocol != null) {
               criteria.add(new ProtocolCriterion(protocol));
            }

            return this.getRoleDescriptorResolver().resolve(criteria);
         } catch (ResolverException var6) {
            this.log.error("Unable to resolve information from metadata", var6);
            throw new ResolverException("Unable to resolve information from metadata", var6);
         }
      }
   }
}
