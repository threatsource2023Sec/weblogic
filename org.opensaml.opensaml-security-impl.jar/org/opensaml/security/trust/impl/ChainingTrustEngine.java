package org.opensaml.security.trust.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.security.SecurityException;
import org.opensaml.security.trust.TrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChainingTrustEngine implements TrustEngine {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(ChainingTrustEngine.class);
   @Nonnull
   @NonnullElements
   private List engines;

   public ChainingTrustEngine(@Nonnull List chain) {
      Constraint.isNotNull(chain, "TrustEngine list cannot be null");
      this.engines = new ArrayList(Collections2.filter(chain, Predicates.notNull()));
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public List getChain() {
      return ImmutableList.copyOf(this.engines);
   }

   public boolean validate(@Nonnull Object token, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
      Iterator var3 = this.engines.iterator();

      TrustEngine engine;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         engine = (TrustEngine)var3.next();
      } while(!engine.validate(token, trustBasisCriteria));

      this.log.debug("Token was trusted by chain member: {}", engine.getClass().getName());
      return true;
   }
}
