package com.bea.security.xacml.cache;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.PolicyEvaluator;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.Record;
import java.util.Collection;

public class PolicyEvaluatorCache {
   private Configuration config;

   public PolicyEvaluatorCache(Configuration config) {
      this.config = config;
   }

   public PolicyEvaluator getEvaluator(Record r) throws EvaluationPlanException, URISyntaxException {
      try {
         PolicyEvaluator pe = r.getEvaluator(this);
         if (pe == null) {
            Collection designatorMatches = r.getDesignatorMatches();
            if (r.getReference() instanceof PolicyIdReference) {
               pe = this.config.getPolicyEvaluatorRegistry().getEvaluator((Policy)r.getReferent(), designatorMatches, this.config);
            } else {
               pe = this.config.getPolicyEvaluatorRegistry().getEvaluator((PolicySet)r.getReferent(), designatorMatches, this.config);
            }

            r.setEvaluator(this, pe);
         }

         return pe;
      } catch (DocumentParseException var4) {
         throw new EvaluationPlanException(var4);
      } catch (PolicyStoreException var5) {
         throw new EvaluationPlanException(var5);
      }
   }
}
