package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.binding.artifact.SAMLArtifact;
import org.opensaml.saml.common.binding.artifact.SAMLSourceIDArtifact;
import org.opensaml.saml.common.binding.artifact.SAMLSourceLocationArtifact;
import org.opensaml.saml.criterion.ArtifactCriterion;
import org.opensaml.saml.ext.saml1md.SourceID;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLArtifactMetadataIndex implements MetadataIndex {
   private List indexingFunctions;

   public SAMLArtifactMetadataIndex() {
      this(Lists.newArrayList(new Function[]{new EntityIDToSHA1SourceIDIndexingFunction(), new SourceIDExtensionIndexingFunction(), new SourceLocationIndexingFunction()}));
   }

   public SAMLArtifactMetadataIndex(@Nonnull List descriptorIndexingFunctions) {
      this.indexingFunctions = new ArrayList(Collections2.filter((Collection)Constraint.isNotNull(descriptorIndexingFunctions, "EntityDescriptor indexing functions list may not be null"), Predicates.notNull()));
      Constraint.isNotEmpty(this.indexingFunctions, "EntityDescriptor indexing functions list may not be empty");
   }

   @Nullable
   public Set generateKeys(@Nonnull EntityDescriptor descriptor) {
      Constraint.isNotNull(descriptor, "EntityDescriptor was null");
      HashSet results = new HashSet();
      Iterator var3 = this.indexingFunctions.iterator();

      while(var3.hasNext()) {
         Function indexingFunction = (Function)var3.next();
         Set result = (Set)indexingFunction.apply(descriptor);
         if (result != null) {
            results.addAll(result);
         }
      }

      return results;
   }

   @Nullable
   public Set generateKeys(@Nonnull CriteriaSet criteriaSet) {
      Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
      ArtifactCriterion artifactCrit = (ArtifactCriterion)criteriaSet.get(ArtifactCriterion.class);
      if (artifactCrit != null) {
         LazySet results = new LazySet();
         SAMLArtifact artifact = artifactCrit.getArtifact();
         if (artifact instanceof SAMLSourceIDArtifact) {
            results.add(new ArtifactSourceIDMetadataIndexKey(((SAMLSourceIDArtifact)artifact).getSourceID()));
         }

         if (artifact instanceof SAMLSourceLocationArtifact) {
            results.add(new ArtifactSourceLocationMetadataIndexKey(((SAMLSourceLocationArtifact)artifact).getSourceLocation()));
         }

         return results;
      } else {
         return null;
      }
   }

   protected static class ArtifactSourceLocationMetadataIndexKey implements MetadataIndexKey {
      private Logger log = LoggerFactory.getLogger(ArtifactSourceLocationMetadataIndexKey.class);
      @Nonnull
      private final String location;
      @Nonnull
      private String canonicalizedLocation;
      private boolean isCanonicalizedLowerCase;

      public ArtifactSourceLocationMetadataIndexKey(@Nonnull @NotEmpty String sourceLocation) {
         this.location = (String)Constraint.isNotNull(StringSupport.trimOrNull(sourceLocation), "SAML artifact source location cannot be null or empty");

         try {
            this.canonicalizedLocation = MetadataIndexSupport.canonicalizeLocationURI(this.location);
         } catch (MalformedURLException var3) {
            this.log.warn("Input source location '{}' was a malformed URL, switching to lower case strategy", this.location, var3);
            this.canonicalizedLocation = this.location.toLowerCase();
            this.isCanonicalizedLowerCase = true;
         }

      }

      @Nonnull
      public String getLocation() {
         return this.location;
      }

      @Nonnull
      public String getCanonicalizedLocation() {
         return this.canonicalizedLocation;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("location", this.location).add("canonicalizedLocation", this.canonicalizedLocation).add("isCanonicalizedLowerCase", this.isCanonicalizedLowerCase).toString();
      }

      public int hashCode() {
         return this.canonicalizedLocation.hashCode();
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj instanceof ArtifactSourceLocationMetadataIndexKey) {
            ArtifactSourceLocationMetadataIndexKey other = (ArtifactSourceLocationMetadataIndexKey)obj;
            if (this.isCanonicalizedLowerCase == other.isCanonicalizedLowerCase) {
               return this.canonicalizedLocation.equals(other.canonicalizedLocation);
            } else {
               return this.isCanonicalizedLowerCase ? this.canonicalizedLocation.equals(other.location.toLowerCase()) : other.canonicalizedLocation.equals(this.location.toLowerCase());
            }
         } else {
            return false;
         }
      }
   }

   protected static class ArtifactSourceIDMetadataIndexKey implements MetadataIndexKey {
      @Nonnull
      @NotEmpty
      private final byte[] sourceID;

      public ArtifactSourceIDMetadataIndexKey(@Nonnull @NotEmpty byte[] newSourceID) {
         this.sourceID = (byte[])Constraint.isNotNull(newSourceID, "SourceID cannot be null");
         Constraint.isGreaterThan(0L, (long)this.sourceID.length, "SourceID length must be greater than zero");
      }

      @Nonnull
      @NotEmpty
      public byte[] getSourceID() {
         return this.sourceID;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("sourceID", new String(Hex.encodeHex(this.sourceID, true))).toString();
      }

      public int hashCode() {
         return Arrays.hashCode(this.sourceID);
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else {
            return obj instanceof ArtifactSourceIDMetadataIndexKey ? Arrays.equals(this.sourceID, ((ArtifactSourceIDMetadataIndexKey)obj).getSourceID()) : false;
         }
      }
   }

   public static class SourceLocationIndexingFunction implements Function {
      private Logger log = LoggerFactory.getLogger(SourceLocationIndexingFunction.class);

      public Set apply(@Nonnull EntityDescriptor descriptor) {
         if (descriptor == null) {
            return null;
         } else {
            LazySet results = new LazySet();
            Iterator var3 = descriptor.getRoleDescriptors().iterator();

            while(true) {
               RoleDescriptor roleDescriptor;
               List arsList;
               do {
                  do {
                     do {
                        if (!var3.hasNext()) {
                           return results;
                        }

                        roleDescriptor = (RoleDescriptor)var3.next();
                     } while(!(roleDescriptor instanceof SSODescriptor));

                     arsList = ((SSODescriptor)roleDescriptor).getArtifactResolutionServices();
                  } while(arsList == null);
               } while(arsList.isEmpty());

               QName role = descriptor.getSchemaType() != null ? roleDescriptor.getSchemaType() : roleDescriptor.getElementQName();
               this.log.trace("Processing ArtifactResolutionService locations for entityID '{}' with role '{}'", descriptor.getEntityID(), role);
               Iterator var7 = arsList.iterator();

               while(var7.hasNext()) {
                  ArtifactResolutionService ars = (ArtifactResolutionService)var7.next();
                  ArtifactSourceLocationMetadataIndexKey key = new ArtifactSourceLocationMetadataIndexKey(ars.getLocation());
                  this.log.trace("For entityID '{}' produced artifact source location index key: {}", descriptor.getEntityID(), key);
                  results.add(key);
               }
            }
         }
      }
   }

   public static class SourceIDExtensionIndexingFunction implements Function {
      private Logger log = LoggerFactory.getLogger(SourceIDExtensionIndexingFunction.class);

      public Set apply(@Nonnull EntityDescriptor descriptor) {
         if (descriptor == null) {
            return null;
         } else {
            LazySet results = new LazySet();
            Iterator var3 = descriptor.getRoleDescriptors().iterator();

            while(true) {
               RoleDescriptor roleDescriptor;
               List children;
               do {
                  do {
                     Extensions extensions;
                     do {
                        if (!var3.hasNext()) {
                           return results;
                        }

                        roleDescriptor = (RoleDescriptor)var3.next();
                        extensions = roleDescriptor.getExtensions();
                     } while(extensions == null);

                     children = extensions.getUnknownXMLObjects(SourceID.DEFAULT_ELEMENT_NAME);
                  } while(children == null);
               } while(children.isEmpty());

               QName role = descriptor.getSchemaType() != null ? roleDescriptor.getSchemaType() : roleDescriptor.getElementQName();
               this.log.trace("Processing SourceID extensions for entityID '{}' with role '{}'", descriptor.getEntityID(), role);
               Iterator var8 = children.iterator();

               while(var8.hasNext()) {
                  XMLObject child = (XMLObject)var8.next();
                  SourceID extSourceID = (SourceID)child;
                  String extSourceIDHex = StringSupport.trimOrNull(extSourceID.getValue());
                  if (extSourceIDHex != null) {
                     try {
                        byte[] sourceID = Hex.decodeHex(extSourceIDHex.toCharArray());
                        ArtifactSourceIDMetadataIndexKey key = new ArtifactSourceIDMetadataIndexKey(sourceID);
                        this.log.trace("For SourceID extension value '{}' produced index key: {}", extSourceIDHex, key);
                        results.add(key);
                     } catch (DecoderException var14) {
                        this.log.warn("Error decoding hexidecimal SourceID extension value '{}' for indexing", extSourceIDHex, var14);
                     }
                  }
               }
            }
         }
      }
   }

   public static class EntityIDToSHA1SourceIDIndexingFunction implements Function {
      private Logger log = LoggerFactory.getLogger(EntityIDToSHA1SourceIDIndexingFunction.class);

      public Set apply(@Nonnull EntityDescriptor descriptor) {
         if (descriptor == null) {
            return null;
         } else {
            String entityID = StringSupport.trimOrNull(descriptor.getEntityID());
            if (entityID == null) {
               return null;
            } else {
               try {
                  MessageDigest sha1Digester = MessageDigest.getInstance("SHA-1");
                  byte[] sourceID = sha1Digester.digest(entityID.getBytes("UTF-8"));
                  ArtifactSourceIDMetadataIndexKey key = new ArtifactSourceIDMetadataIndexKey(sourceID);
                  this.log.trace("For entityID '{}' produced artifact SourceID index key: {}", entityID, key);
                  return Collections.singleton(key);
               } catch (NoSuchAlgorithmException var6) {
                  this.log.error("Digest algorithm '{}' was invalid for encoding artifact SourceID", "SHA-1", var6);
                  return null;
               } catch (UnsupportedEncodingException var7) {
                  this.log.error("UTF-8 was unsupported for encoding artifact SourceID!");
                  return null;
               }
            }
         }
      }
   }
}
