package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiedInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.CriterionPredicateRegistry;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.resolver.ResolverSupport;
import org.opensaml.core.criterion.SatisfyAnyCriterion;
import org.opensaml.saml.criterion.EntityRoleCriterion;
import org.opensaml.saml.criterion.ProtocolCriterion;
import org.opensaml.saml.metadata.criteria.role.EvaluableRoleDescriptorCriterion;
import org.opensaml.saml.metadata.criteria.role.impl.RoleDescriptorCriterionPredicateRegistry;
import org.opensaml.saml.metadata.resolver.MetadataResolver;
import org.opensaml.saml.metadata.resolver.RoleDescriptorResolver;
import org.opensaml.saml.saml2.common.IsTimeboundSAMLObjectValidPredicate;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredicateRoleDescriptorResolver extends AbstractIdentifiedInitializableComponent implements RoleDescriptorResolver {
   private static final Predicate IS_VALID_PREDICATE = new IsTimeboundSAMLObjectValidPredicate();
   private Logger log = LoggerFactory.getLogger(PredicateRoleDescriptorResolver.class);
   private boolean requireValidMetadata;
   private MetadataResolver entityDescriptorResolver;
   private boolean satisfyAnyPredicates;
   private CriterionPredicateRegistry criterionPredicateRegistry;
   private boolean useDefaultPredicateRegistry;
   private boolean resolveViaPredicatesOnly;

   public PredicateRoleDescriptorResolver(@Nonnull MetadataResolver mdResolver) {
      this.entityDescriptorResolver = (MetadataResolver)Constraint.isNotNull(mdResolver, "Resolver for EntityDescriptors may not be null");
      this.setId(UUID.randomUUID().toString());
      this.requireValidMetadata = true;
      this.useDefaultPredicateRegistry = true;
   }

   public boolean isRequireValidMetadata() {
      return this.requireValidMetadata;
   }

   public void setRequireValidMetadata(boolean require) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.requireValidMetadata = require;
   }

   public boolean isSatisfyAnyPredicates() {
      return this.satisfyAnyPredicates;
   }

   public void setSatisfyAnyPredicates(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.satisfyAnyPredicates = flag;
   }

   @NonnullAfterInit
   public CriterionPredicateRegistry getCriterionPredicateRegistry() {
      return this.criterionPredicateRegistry;
   }

   public void setCriterionPredicateRegistry(@Nullable CriterionPredicateRegistry registry) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.criterionPredicateRegistry = registry;
   }

   public boolean isUseDefaultPredicateRegistry() {
      return this.useDefaultPredicateRegistry;
   }

   public void setUseDefaultPredicateRegistry(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.useDefaultPredicateRegistry = flag;
   }

   public boolean isResolveViaPredicatesOnly() {
      return this.resolveViaPredicatesOnly;
   }

   public void setResolveViaPredicatesOnly(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.resolveViaPredicatesOnly = flag;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.getCriterionPredicateRegistry() == null && this.isUseDefaultPredicateRegistry()) {
         this.setCriterionPredicateRegistry(new RoleDescriptorCriterionPredicateRegistry());
      }

   }

   @Nullable
   public RoleDescriptor resolveSingle(CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      Iterable iterable = this.resolve(criteria);
      if (iterable != null) {
         Iterator iterator = iterable.iterator();
         if (iterator != null && iterator.hasNext()) {
            return (RoleDescriptor)iterator.next();
         }
      }

      return null;
   }

   @Nonnull
   public Iterable resolve(CriteriaSet criteria) throws ResolverException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      Iterable entityDescriptorsSource = this.entityDescriptorResolver.resolve(criteria);
      if (!entityDescriptorsSource.iterator().hasNext()) {
         this.log.debug("Resolved no EntityDescriptors via underlying MetadataResolver, returning empty collection");
         return Collections.emptySet();
      } else {
         if (this.log.isDebugEnabled()) {
            this.log.debug("Resolved {} source EntityDescriptors", Iterables.size(entityDescriptorsSource));
         }

         Predicate predicate = this.isRequireValidMetadata() ? IS_VALID_PREDICATE : Predicates.alwaysTrue();
         Iterable candidates;
         if (this.haveRoleCriteria(criteria)) {
            candidates = this.getCandidatesByRoleAndProtocol(entityDescriptorsSource, criteria);
            if (this.log.isDebugEnabled()) {
               this.log.debug("Resolved {} RoleDescriptor candidates via role criteria, performing predicate filtering", Iterables.size(candidates));
            }

            return this.predicateFilterCandidates(Iterables.filter(candidates, predicate), criteria, false);
         } else if (this.isResolveViaPredicatesOnly()) {
            candidates = this.getAllCandidates(entityDescriptorsSource);
            if (this.log.isDebugEnabled()) {
               this.log.debug("Resolved {} RoleDescriptor total candidates for predicate-only resolution", Iterables.size(candidates));
            }

            return this.predicateFilterCandidates(Iterables.filter(candidates, predicate), criteria, true);
         } else {
            this.log.debug("Found no role criteria and predicate-only resolution is disabled, returning empty collection");
            return Collections.emptySet();
         }
      }
   }

   protected boolean haveRoleCriteria(@Nonnull CriteriaSet criteria) {
      return criteria.contains(EntityRoleCriterion.class);
   }

   protected Iterable getCandidatesByRoleAndProtocol(@Nonnull Iterable entityDescriptors, @Nonnull CriteriaSet criteria) {
      EntityRoleCriterion roleCriterion = (EntityRoleCriterion)Constraint.isNotNull(criteria.get(EntityRoleCriterion.class), "EntityRoleCriterion was not supplied");
      ProtocolCriterion protocolCriterion = (ProtocolCriterion)criteria.get(ProtocolCriterion.class);
      ArrayList aggregate = new ArrayList();
      Iterator var6 = entityDescriptors.iterator();

      while(var6.hasNext()) {
         EntityDescriptor entityDescriptor = (EntityDescriptor)var6.next();
         if (protocolCriterion != null) {
            aggregate.add(entityDescriptor.getRoleDescriptors(roleCriterion.getRole(), protocolCriterion.getProtocol()));
         } else {
            aggregate.add(entityDescriptor.getRoleDescriptors(roleCriterion.getRole()));
         }
      }

      return Iterables.concat(aggregate);
   }

   protected Iterable getAllCandidates(@Nonnull Iterable entityDescriptors) {
      ArrayList aggregate = new ArrayList();
      Iterator var3 = entityDescriptors.iterator();

      while(var3.hasNext()) {
         EntityDescriptor entityDescriptor = (EntityDescriptor)var3.next();
         aggregate.add(entityDescriptor.getRoleDescriptors());
      }

      return Iterables.concat(aggregate);
   }

   protected Iterable predicateFilterCandidates(@Nonnull Iterable candidates, @Nonnull CriteriaSet criteria, boolean onEmptyPredicatesReturnEmpty) throws ResolverException {
      if (!candidates.iterator().hasNext()) {
         this.log.debug("Candidates iteration was empty, nothing to filter via predicates");
         return Collections.emptySet();
      } else {
         this.log.debug("Attempting to filter candidate RoleDescriptors via resolved Predicates");
         Set predicates = ResolverSupport.getPredicates(criteria, EvaluableRoleDescriptorCriterion.class, this.getCriterionPredicateRegistry());
         this.log.trace("Resolved {} Predicates: {}", predicates.size(), predicates);
         SatisfyAnyCriterion satisfyAnyCriterion = (SatisfyAnyCriterion)criteria.get(SatisfyAnyCriterion.class);
         boolean satisfyAny;
         if (satisfyAnyCriterion != null) {
            this.log.trace("CriteriaSet contained SatisfyAnyCriterion");
            satisfyAny = satisfyAnyCriterion.isSatisfyAny();
         } else {
            this.log.trace("CriteriaSet did NOT contain SatisfyAnyCriterion");
            satisfyAny = this.isSatisfyAnyPredicates();
         }

         this.log.trace("Effective satisyAny value: {}", satisfyAny);
         Iterable result = ResolverSupport.getFilteredIterable(candidates, predicates, satisfyAny, onEmptyPredicatesReturnEmpty);
         if (this.log.isDebugEnabled()) {
            this.log.debug("After predicate filtering {} RoleDescriptors remain", Iterables.size(result));
         }

         return result;
      }
   }
}
