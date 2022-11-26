package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;

public class AlgorithmRuntimeSupportedPredicate implements Predicate {
   private AlgorithmRegistry registry;

   public AlgorithmRuntimeSupportedPredicate(AlgorithmRegistry algorithmRegistry) {
      this.registry = (AlgorithmRegistry)Constraint.isNotNull(algorithmRegistry, "AlgorithmRegistry may not be null");
   }

   public boolean apply(@Nullable String input) {
      return this.registry.isRuntimeSupported(input);
   }
}
