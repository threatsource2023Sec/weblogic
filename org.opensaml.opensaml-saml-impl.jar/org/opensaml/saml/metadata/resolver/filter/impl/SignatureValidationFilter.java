package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Function;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.security.impl.SAMLSignatureProfileValidator;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.x509.TrustedNamesCriterion;
import org.opensaml.xmlsec.signature.SignableXMLObject;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignatureValidationFilter implements MetadataFilter {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(SignatureValidationFilter.class);
   @Nonnull
   private SignatureTrustEngine signatureTrustEngine;
   private boolean requireSignedRoot;
   @Nullable
   private CriteriaSet defaultCriteria;
   @Nullable
   private SignaturePrevalidator signaturePrevalidator;
   @Nullable
   private Function dynamicTrustedNamesStrategy;

   public SignatureValidationFilter(@Nonnull SignatureTrustEngine engine) {
      Constraint.isNotNull(engine, "SignatureTrustEngine cannot be null");
      this.requireSignedRoot = true;
      this.signatureTrustEngine = engine;
      this.signaturePrevalidator = new SAMLSignatureProfileValidator();
      this.dynamicTrustedNamesStrategy = new BasicDynamicTrustedNamesStrategy();
   }

   @Nullable
   public Function getDynamicTrustedNamesStrategy() {
      return this.dynamicTrustedNamesStrategy;
   }

   public void setDynamicTrustedNamesStrategy(@Nullable Function strategy) {
      this.dynamicTrustedNamesStrategy = strategy;
   }

   @Nonnull
   public SignatureTrustEngine getSignatureTrustEngine() {
      return this.signatureTrustEngine;
   }

   @Nullable
   public SignaturePrevalidator getSignaturePrevalidator() {
      return this.signaturePrevalidator;
   }

   public void setSignaturePrevalidator(@Nullable SignaturePrevalidator validator) {
      this.signaturePrevalidator = validator;
   }

   public boolean getRequireSignedRoot() {
      return this.requireSignedRoot;
   }

   public void setRequireSignedRoot(boolean require) {
      this.requireSignedRoot = require;
   }

   /** @deprecated */
   @Deprecated
   public boolean getRequireSignature() {
      return this.getRequireSignedRoot();
   }

   /** @deprecated */
   @Deprecated
   public void setRequireSignature(boolean require) {
      this.setRequireSignedRoot(require);
   }

   @Nullable
   public CriteriaSet getDefaultCriteria() {
      return this.defaultCriteria;
   }

   public void setDefaultCriteria(@Nullable CriteriaSet newCriteria) {
      this.defaultCriteria = newCriteria;
   }

   @Nullable
   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
      if (metadata == null) {
         return null;
      } else if (!(metadata instanceof SignableXMLObject)) {
         this.log.warn("Input was not a SignableXMLObject, skipping filtering: {}", metadata.getClass().getName());
         return metadata;
      } else {
         SignableXMLObject signableMetadata = (SignableXMLObject)metadata;
         if (!signableMetadata.isSigned() && this.getRequireSignedRoot()) {
            this.log.warn("Metadata root element was unsigned and signatures are required, metadata will be filtered out.");
            return null;
         } else {
            try {
               if (signableMetadata instanceof EntityDescriptor) {
                  this.processEntityDescriptor((EntityDescriptor)signableMetadata);
               } else if (signableMetadata instanceof EntitiesDescriptor) {
                  this.processEntityGroup((EntitiesDescriptor)signableMetadata);
               } else {
                  this.log.error("Internal error, metadata object was of an unsupported type: {}", metadata.getClass().getName());
               }

               return metadata;
            } catch (Throwable var4) {
               this.log.warn("Saw fatal error validating metadata signature(s), metadata will be filtered out", var4);
               return null;
            }
         }
      }
   }

   protected void processEntityDescriptor(@Nonnull EntityDescriptor entityDescriptor) throws FilterException {
      String entityID = entityDescriptor.getEntityID();
      this.log.trace("Processing EntityDescriptor: {}", entityID);
      if (entityDescriptor.isSigned()) {
         this.verifySignature(entityDescriptor, entityID, false);
      }

      Iterator roleIter = entityDescriptor.getRoleDescriptors().iterator();

      while(roleIter.hasNext()) {
         RoleDescriptor roleChild = (RoleDescriptor)roleIter.next();
         if (!roleChild.isSigned()) {
            this.log.trace("RoleDescriptor member '{}' was not signed, skipping signature processing...", roleChild.getElementQName());
         } else {
            this.log.trace("Processing signed RoleDescriptor member: {}", roleChild.getElementQName());

            try {
               String roleID = this.getRoleIDToken(entityID, roleChild);
               this.verifySignature(roleChild, roleID, false);
            } catch (FilterException var7) {
               this.log.error("RoleDescriptor '{}' subordinate to entity '{}' failed signature verification, removing from metadata provider", roleChild.getElementQName(), entityID);
               roleIter.remove();
            }
         }
      }

      if (entityDescriptor.getAffiliationDescriptor() != null) {
         AffiliationDescriptor affiliationDescriptor = entityDescriptor.getAffiliationDescriptor();
         if (!affiliationDescriptor.isSigned()) {
            this.log.trace("AffiliationDescriptor member was not signed, skipping signature processing...");
         } else {
            this.log.trace("Processing signed AffiliationDescriptor member with owner ID: {}", affiliationDescriptor.getOwnerID());

            try {
               this.verifySignature(affiliationDescriptor, affiliationDescriptor.getOwnerID(), false);
            } catch (FilterException var6) {
               this.log.error("AffiliationDescriptor with owner ID '{}' subordinate to entity '{}' failed signature verification, removing from metadata provider", affiliationDescriptor.getOwnerID(), entityID);
               entityDescriptor.setAffiliationDescriptor((AffiliationDescriptor)null);
            }
         }
      }

   }

   protected void processEntityGroup(@Nonnull EntitiesDescriptor entitiesDescriptor) throws FilterException {
      String name = this.getGroupName(entitiesDescriptor);
      this.log.trace("Processing EntitiesDescriptor group: {}", name);
      if (entitiesDescriptor.isSigned()) {
         this.verifySignature(entitiesDescriptor, name, true);
      }

      HashSet toRemove = new HashSet();
      Iterator entityIter = entitiesDescriptor.getEntityDescriptors().iterator();

      while(entityIter.hasNext()) {
         EntityDescriptor entityChild = (EntityDescriptor)entityIter.next();
         if (!entityChild.isSigned()) {
            this.log.trace("EntityDescriptor member '{}' was not signed, skipping signature processing...", entityChild.getEntityID());
         } else {
            this.log.trace("Processing signed EntityDescriptor member: {}", entityChild.getEntityID());

            try {
               this.processEntityDescriptor(entityChild);
            } catch (FilterException var10) {
               this.log.error("EntityDescriptor '{}' failed signature verification, removing from metadata provider", entityChild.getEntityID());
               toRemove.add(entityChild);
            }
         }
      }

      if (!toRemove.isEmpty()) {
         entitiesDescriptor.getEntityDescriptors().removeAll(toRemove);
         toRemove.clear();
      }

      Iterator entitiesIter = entitiesDescriptor.getEntitiesDescriptors().iterator();

      while(entitiesIter.hasNext()) {
         EntitiesDescriptor entitiesChild = (EntitiesDescriptor)entitiesIter.next();
         String childName = this.getGroupName(entitiesChild);
         this.log.trace("Processing EntitiesDescriptor member: {}", childName);

         try {
            this.processEntityGroup(entitiesChild);
         } catch (FilterException var9) {
            this.log.error("EntitiesDescriptor '{}' failed signature verification, removing from metadata provider", childName);
            toRemove.add(entitiesChild);
         }
      }

      if (!toRemove.isEmpty()) {
         entitiesDescriptor.getEntitiesDescriptors().removeAll(toRemove);
      }

   }

   protected void verifySignature(@Nonnull SignableXMLObject signedMetadata, @Nonnull @NotEmpty String metadataEntryName, boolean isEntityGroup) throws FilterException {
      this.log.debug("Verifying signature on metadata entry: {}", metadataEntryName);
      Signature signature = signedMetadata.getSignature();
      if (signature == null) {
         this.log.warn("Signature was null, skipping processing on metadata entry: {}", metadataEntryName);
      } else {
         this.performPreValidation(signature, metadataEntryName);
         CriteriaSet criteriaSet = this.buildCriteriaSet(signedMetadata, metadataEntryName, isEntityGroup);

         try {
            if (this.getSignatureTrustEngine().validate(signature, criteriaSet)) {
               this.log.trace("Signature trust establishment succeeded for metadata entry {}", metadataEntryName);
            } else {
               this.log.error("Signature trust establishment failed for metadata entry {}", metadataEntryName);
               throw new FilterException("Signature trust establishment failed for metadata entry");
            }
         } catch (SecurityException var7) {
            this.log.error("Error processing signature verification for metadata entry '{}': {} ", metadataEntryName, var7.getMessage());
            throw new FilterException("Error processing signature verification for metadata entry", var7);
         }
      }
   }

   protected void performPreValidation(@Nonnull Signature signature, @Nonnull @NotEmpty String metadataEntryName) throws FilterException {
      if (this.getSignaturePrevalidator() != null) {
         try {
            this.getSignaturePrevalidator().validate(signature);
         } catch (SignatureException var4) {
            this.log.error("Signature on metadata entry '{}' failed signature pre-validation", metadataEntryName);
            throw new FilterException("Metadata instance signature failed signature pre-validation", var4);
         }
      }

   }

   @Nonnull
   protected CriteriaSet buildCriteriaSet(@Nonnull SignableXMLObject signedMetadata, @Nonnull @NotEmpty String metadataEntryName, boolean isEntityGroup) {
      CriteriaSet newCriteriaSet = new CriteriaSet();
      if (this.getDefaultCriteria() != null) {
         newCriteriaSet.addAll(this.getDefaultCriteria());
      }

      if (!newCriteriaSet.contains(UsageCriterion.class)) {
         newCriteriaSet.add(new UsageCriterion(UsageType.SIGNING));
      }

      if (this.getDynamicTrustedNamesStrategy() != null) {
         Set dynamicTrustedNames = (Set)this.getDynamicTrustedNamesStrategy().apply(signedMetadata);
         if (dynamicTrustedNames != null && !dynamicTrustedNames.isEmpty()) {
            newCriteriaSet.add(new TrustedNamesCriterion(dynamicTrustedNames));
         }
      }

      return newCriteriaSet;
   }

   protected String getRoleIDToken(@Nonnull @NotEmpty String entityID, @Nonnull RoleDescriptor role) {
      String roleName = role.getElementQName().getLocalPart();
      return "[Role: " + entityID + "::" + roleName + "]";
   }

   @Nonnull
   @NotEmpty
   protected String getGroupName(@Nonnull EntitiesDescriptor group) {
      String name = group.getName();
      if (name != null) {
         return name;
      } else {
         name = group.getID();
         return name != null ? name : "(unnamed)";
      }
   }
}
