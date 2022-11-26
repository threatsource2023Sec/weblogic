package com.oracle.cmm.lowertier;

import com.oracle.cmm.lowertier.pressure.EvaluationManager;
import java.util.Properties;

public final class LowerTier {
   public static final boolean initialize(Informer informer) throws IllegalStateException {
      return EvaluationManager.getEvaluationManager(informer, (Properties)null).isValid();
   }
}
