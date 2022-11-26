package com.bea.security.xacml;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.cache.PolicyEvaluatorCache;
import com.bea.security.xacml.combinator.SimplePolicyCombinerEvaluatorFactory;
import com.bea.security.xacml.combinator.standard.StandardPolicyCombinerLibrary;
import com.bea.security.xacml.store.Record;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class PolicyDecisionPoint {
   private Configuration config;
   protected PolicyEvaluatorCache cache;
   protected SimplePolicyCombinerEvaluatorFactory factory;

   public PolicyDecisionPoint(Configuration config) throws URISyntaxException {
      this(config, (URI)null);
   }

   public PolicyDecisionPoint(Configuration config, URI policyCombiningAlgId) throws URISyntaxException {
      this.config = config;
      if (policyCombiningAlgId == null) {
         try {
            policyCombiningAlgId = new URI("urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides");
         } catch (java.net.URISyntaxException var4) {
            throw new URISyntaxException(var4);
         }
      }

      this.cache = new PolicyEvaluatorCache(config);
      this.factory = (new StandardPolicyCombinerLibrary()).getFactory(policyCombiningAlgId);
   }

   public PolicyEvaluator getEvaluator(Policy policy) throws EvaluationPlanException, URISyntaxException {
      return this.getEvaluator((Policy)policy, (Collection)null);
   }

   public PolicyEvaluator getEvaluator(Policy policy, Collection knownMatch) throws EvaluationPlanException, URISyntaxException {
      return this.config.getPolicyEvaluatorRegistry().getEvaluator(policy, knownMatch, this.config);
   }

   public PolicyEvaluator getEvaluator(PolicySet policy) throws EvaluationPlanException, URISyntaxException {
      return this.getEvaluator((PolicySet)policy, (Collection)null);
   }

   public PolicyEvaluator getEvaluator(PolicySet policy, Collection knownMatch) throws EvaluationPlanException, URISyntaxException {
      return this.config.getPolicyEvaluatorRegistry().getEvaluator(policy, knownMatch, this.config);
   }

   protected class PolicyEvaluatorSet {
      private final EvaluationCtx context;

      public PolicyEvaluatorSet(EvaluationCtx context) {
         this.context = context;
      }

      public PolicyEvaluatorItem createItem(Set records) {
         return new PolicyEvaluatorSetItem(records);
      }

      private class PolicyEvaluatorSetItem implements PolicyEvaluatorItem {
         private Set records;
         private PolicyEvaluator pe;

         public PolicyEvaluatorSetItem(Set records) {
            this.records = records;
         }

         public PolicyDecision evaluate() throws IndeterminateEvaluationException {
            synchronized(this) {
               if (this.pe == null) {
                  try {
                     List pes = new ArrayList();
                     Iterator var3 = this.records.iterator();

                     while(var3.hasNext()) {
                        Record r = (Record)var3.next();
                        pes.add(PolicyDecisionPoint.this.cache.getEvaluator(r));
                     }

                     this.pe = pes.size() == 1 ? (PolicyEvaluator)pes.get(0) : PolicyDecisionPoint.this.factory.createCombiner(pes);
                  } catch (URISyntaxException var6) {
                     throw new IndeterminateEvaluationException(var6);
                  } catch (EvaluationPlanException var7) {
                     throw new IndeterminateEvaluationException(var7);
                  }
               }
            }

            return this.pe.evaluate(PolicyEvaluatorSet.this.context);
         }
      }
   }
}
