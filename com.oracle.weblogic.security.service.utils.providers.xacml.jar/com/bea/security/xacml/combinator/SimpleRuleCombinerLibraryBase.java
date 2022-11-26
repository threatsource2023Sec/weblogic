package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.RuleCombinerEvaluator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SimpleRuleCombinerLibraryBase implements SimpleRuleCombinerEvaluatorLibrary {
   private Map registry = new HashMap();

   protected void register(URI identifier, SimpleRuleCombinerEvaluatorFactory sff) {
      this.registry.put(identifier, sff);
   }

   public RuleCombinerEvaluator createCombiner(URI identifier, List arguments) {
      SimpleRuleCombinerEvaluatorFactory sff = (SimpleRuleCombinerEvaluatorFactory)this.registry.get(identifier);
      return sff != null ? sff.createCombiner(arguments) : null;
   }
}
