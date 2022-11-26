package com.bea.security.xacml.combinator;

import com.bea.common.security.xacml.URI;
import com.bea.security.xacml.PolicyEvaluator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SimplePolicyCombinerLibraryBase implements SimplePolicyCombinerEvaluatorLibrary {
   private Map registry = new HashMap();

   protected void register(URI identifier, SimplePolicyCombinerEvaluatorFactory sff) {
      this.registry.put(identifier, sff);
   }

   public PolicyEvaluator createCombiner(URI identifier, List arguments) {
      SimplePolicyCombinerEvaluatorFactory sff = (SimplePolicyCombinerEvaluatorFactory)this.registry.get(identifier);
      return sff != null ? sff.createCombiner(arguments) : null;
   }

   public SimplePolicyCombinerEvaluatorFactory getFactory(URI identifier) {
      return (SimplePolicyCombinerEvaluatorFactory)this.registry.get(identifier);
   }
}
