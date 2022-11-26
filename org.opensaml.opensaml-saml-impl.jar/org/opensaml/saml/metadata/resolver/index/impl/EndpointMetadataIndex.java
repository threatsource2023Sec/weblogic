package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.criterion.EndpointCriterion;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.StartsWithLocationCriterion;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointMetadataIndex implements MetadataIndex {
   private Logger log = LoggerFactory.getLogger(EndpointMetadataIndex.class);
   @Nonnull
   private Predicate endpointSelectionPredicate;

   public EndpointMetadataIndex() {
      this.endpointSelectionPredicate = Predicates.alwaysTrue();
   }

   public EndpointMetadataIndex(@Nonnull Predicate endpointPredicate) {
      this.endpointSelectionPredicate = (Predicate)Constraint.isNotNull(endpointPredicate, "Endpoint selection predicate may not be null");
   }

   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set generateKeys(@Nonnull EntityDescriptor descriptor) {
      Constraint.isNotNull(descriptor, "EntityDescriptor was null");
      HashSet result = new HashSet();
      Iterator var3 = descriptor.getRoleDescriptors().iterator();

      while(var3.hasNext()) {
         RoleDescriptor role = (RoleDescriptor)var3.next();
         QName roleType = role.getSchemaType();
         if (roleType == null) {
            roleType = role.getElementQName();
         }

         Iterator var6 = role.getEndpoints().iterator();

         while(var6.hasNext()) {
            Endpoint endpoint = (Endpoint)var6.next();
            QName endpointType = endpoint.getSchemaType();
            if (endpointType == null) {
               endpointType = endpoint.getElementQName();
            }

            if (this.endpointSelectionPredicate.apply(endpoint)) {
               String location = StringSupport.trimOrNull(endpoint.getLocation());
               if (location != null) {
                  this.log.trace("Indexing Endpoint: role '{}', endpoint type '{}', location '{}'", new Object[]{roleType, endpointType, location});
                  result.add(new EndpointMetadataIndexKey(roleType, endpointType, location, false));
               }

               String responseLocation = StringSupport.trimOrNull(endpoint.getResponseLocation());
               if (responseLocation != null) {
                  this.log.trace("Indexing response Endpoint - role '{}', endpoint type '{}', response location '{}'", new Object[]{roleType, endpointType, responseLocation});
                  result.add(new EndpointMetadataIndexKey(roleType, endpointType, responseLocation, true));
               }
            }
         }
      }

      return result;
   }

   @Nullable
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Set generateKeys(@Nonnull CriteriaSet criteriaSet) {
      Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
      EntityRoleCriterion roleCrit = (EntityRoleCriterion)criteriaSet.get(EntityRoleCriterion.class);
      EndpointCriterion endpointCrit = (EndpointCriterion)criteriaSet.get(EndpointCriterion.class);
      if (roleCrit != null && endpointCrit != null) {
         HashSet result = new HashSet();
         result.addAll(this.processCriteria(criteriaSet, roleCrit.getRole(), endpointCrit.getEndpoint()));
         return result;
      } else {
         return null;
      }
   }

   @Nonnull
   private Set processCriteria(@Nonnull CriteriaSet criteriaSet, @Nonnull QName roleType, @Nonnull Endpoint endpoint) {
      HashSet result = new HashSet();
      QName endpointType = endpoint.getSchemaType();
      if (endpointType == null) {
         endpointType = endpoint.getElementQName();
      }

      String location = StringSupport.trimOrNull(endpoint.getLocation());
      if (location != null) {
         Iterator var7 = this.processLocation(criteriaSet, location).iterator();

         while(var7.hasNext()) {
            String variant = (String)var7.next();
            result.add(new EndpointMetadataIndexKey(roleType, endpointType, variant, false));
         }
      }

      String responseLocation = StringSupport.trimOrNull(endpoint.getResponseLocation());
      if (responseLocation != null) {
         Iterator var11 = this.processLocation(criteriaSet, responseLocation).iterator();

         while(var11.hasNext()) {
            String variant = (String)var11.next();
            result.add(new EndpointMetadataIndexKey(roleType, endpointType, variant, true));
         }
      }

      return result;
   }

   @Nonnull
   private Set processLocation(@Nonnull CriteriaSet criteriaSet, @Nonnull String location) {
      boolean generateStartsWithVariants = false;
      StartsWithLocationCriterion startsWithCrit = (StartsWithLocationCriterion)criteriaSet.get(StartsWithLocationCriterion.class);
      if (startsWithCrit != null) {
         generateStartsWithVariants = startsWithCrit.isMatchStartsWith();
      }

      if (generateStartsWithVariants) {
         this.log.trace("Saw indication to produce path-trimmed key variants for startsWith eval from '{}'", location);
         HashSet result = new HashSet();
         result.add(location);
         this.log.trace("Produced value '{}'", location);

         try {
            String currentURL = null;
            URLBuilder urlBuilder = new URLBuilder(location);

            for(String currentPath = MetadataIndexSupport.trimURLPathSegment(urlBuilder.getPath()); currentPath != null; currentPath = MetadataIndexSupport.trimURLPathSegment(urlBuilder.getPath())) {
               urlBuilder.setPath(currentPath);
               currentURL = urlBuilder.buildURL();
               result.add(currentURL);
               this.log.trace("Produced value '{}'", currentURL);
            }

            urlBuilder.setPath((String)null);
            currentURL = urlBuilder.buildURL();
            result.add(currentURL);
            this.log.trace("Produced value '{}'", currentURL);
         } catch (MalformedURLException var9) {
            this.log.warn("Could not parse URL '{}', will not generate path segment variants", location, var9);
         }

         return result;
      } else {
         return Collections.singleton(location);
      }
   }

   protected static class EndpointMetadataIndexKey implements MetadataIndexKey {
      private final Logger log = LoggerFactory.getLogger(EndpointMetadataIndexKey.class);
      @Nonnull
      private final QName role;
      @Nonnull
      private final QName endpoint;
      @Nonnull
      private final String location;
      private final boolean response;
      @Nonnull
      private String canonicalizedLocation;
      private boolean isCanonicalizedLowerCase;

      public EndpointMetadataIndexKey(@Nonnull QName roleType, @Nonnull QName endpointType, @Nonnull @NotEmpty String endpointLocation, boolean isResponse) {
         this.role = (QName)Constraint.isNotNull(roleType, "SAML role cannot be null");
         this.endpoint = (QName)Constraint.isNotNull(endpointType, "SAML endpoint type cannot be null");
         this.location = (String)Constraint.isNotNull(StringSupport.trimOrNull(endpointLocation), "SAML role cannot be null or empty");
         this.response = isResponse;

         try {
            this.canonicalizedLocation = MetadataIndexSupport.canonicalizeLocationURI(this.location);
         } catch (MalformedURLException var6) {
            this.log.warn("Input location '{}' was a malformed URL, switching to lower case strategy", this.location, var6);
            this.canonicalizedLocation = this.location.toLowerCase();
            this.isCanonicalizedLowerCase = true;
         }

      }

      @Nonnull
      public QName getRoleType() {
         return this.role;
      }

      @Nonnull
      public QName getEndpointType() {
         return this.endpoint;
      }

      @Nonnull
      public String getLocation() {
         return this.location;
      }

      public boolean isResponse() {
         return this.response;
      }

      @Nonnull
      public String getCanonicalizedLocation() {
         return this.canonicalizedLocation;
      }

      public String toString() {
         return MoreObjects.toStringHelper(this).add("role", this.role).add("endpoint", this.endpoint).add("location", this.location).add("isResponse", this.response).add("canonicalizedLocation", this.canonicalizedLocation).add("isCanonicalizedLowerCase", this.isCanonicalizedLowerCase).toString();
      }

      public int hashCode() {
         return Objects.hash(new Object[]{this.getRoleType(), this.getEndpointType(), this.getCanonicalizedLocation(), this.isResponse()});
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj instanceof EndpointMetadataIndexKey) {
            EndpointMetadataIndexKey other = (EndpointMetadataIndexKey)obj;
            String thisLocation = this.canonicalizedLocation;
            String otherLocation = other.canonicalizedLocation;
            if (this.isCanonicalizedLowerCase != other.isCanonicalizedLowerCase) {
               if (this.isCanonicalizedLowerCase) {
                  otherLocation = other.location.toLowerCase();
               } else {
                  thisLocation = this.location.toLowerCase();
               }
            }

            return this.role.equals(other.role) && this.endpoint.equals(other.endpoint) && thisLocation.equals(otherLocation) && this.response == other.response;
         } else {
            return false;
         }
      }
   }

   public static class DefaultEndpointSelectionPredicate implements Predicate {
      @Nonnull
      private Map endpointTypes;

      public DefaultEndpointSelectionPredicate() {
         this.endpointTypes = Collections.emptyMap();
      }

      public DefaultEndpointSelectionPredicate(@Nonnull Map indexableTypes) {
         this.endpointTypes = (Map)Constraint.isNotNull(indexableTypes, "Indexable endpoint types map was null");
      }

      public boolean apply(@Nullable Endpoint endpoint) {
         if (endpoint == null) {
            return false;
         } else {
            RoleDescriptor role = (RoleDescriptor)endpoint.getParent();
            if (role == null) {
               return false;
            } else {
               QName roleType = role.getSchemaType();
               if (roleType == null) {
                  roleType = role.getElementQName();
               }

               QName endpointType = endpoint.getSchemaType();
               if (endpointType == null) {
                  endpointType = endpoint.getElementQName();
               }

               Set indexableEndpoints = (Set)this.endpointTypes.get(roleType);
               return indexableEndpoints != null && indexableEndpoints.contains(endpointType);
            }
         }
      }
   }
}
