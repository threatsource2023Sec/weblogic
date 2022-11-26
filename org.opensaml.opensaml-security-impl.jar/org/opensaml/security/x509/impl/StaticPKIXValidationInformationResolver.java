package org.opensaml.security.x509.impl;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationInformationResolver;
import org.opensaml.security.x509.TrustedNamesCriterion;

public class StaticPKIXValidationInformationResolver implements PKIXValidationInformationResolver {
   private final List pkixInfo;
   private final Set trustedNames;
   private boolean supportDynamicTrustedNames;

   public StaticPKIXValidationInformationResolver(@Nullable List info, @Nullable Set names) {
      this(info, names, false);
   }

   public StaticPKIXValidationInformationResolver(@Nullable List info, @Nullable Set names, boolean supportDynamicNames) {
      if (info != null) {
         this.pkixInfo = new ArrayList(info);
      } else {
         this.pkixInfo = Collections.EMPTY_LIST;
      }

      if (names != null) {
         this.trustedNames = new HashSet(names);
      } else {
         this.trustedNames = Collections.EMPTY_SET;
      }

      this.supportDynamicTrustedNames = supportDynamicNames;
   }

   @Nonnull
   public Set resolveTrustedNames(@Nullable CriteriaSet criteriaSet) throws ResolverException {
      if (criteriaSet == null) {
         return ImmutableSet.copyOf(this.trustedNames);
      } else {
         HashSet temp = new HashSet(this.trustedNames);
         EntityIdCriterion entityIDCriterion = (EntityIdCriterion)criteriaSet.get(EntityIdCriterion.class);
         if (entityIDCriterion != null) {
            temp.add(entityIDCriterion.getEntityId());
         }

         if (this.supportDynamicTrustedNames) {
            TrustedNamesCriterion trustedNamesCriterion = (TrustedNamesCriterion)criteriaSet.get(TrustedNamesCriterion.class);
            if (trustedNamesCriterion != null) {
               temp.addAll(trustedNamesCriterion.getTrustedNames());
            }
         }

         return ImmutableSet.copyOf(temp);
      }
   }

   public boolean supportsTrustedNameResolution() {
      return true;
   }

   @Nonnull
   public Iterable resolve(@Nullable CriteriaSet criteria) throws ResolverException {
      return this.pkixInfo;
   }

   @Nullable
   public PKIXValidationInformation resolveSingle(@Nullable CriteriaSet criteria) throws ResolverException {
      return !this.pkixInfo.isEmpty() ? (PKIXValidationInformation)this.pkixInfo.get(0) : null;
   }
}
