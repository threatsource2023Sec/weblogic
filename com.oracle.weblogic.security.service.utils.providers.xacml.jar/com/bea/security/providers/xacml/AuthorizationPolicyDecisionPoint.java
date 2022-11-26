package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.XACMLException;
import com.bea.security.providers.xacml.store.AuthorizationPolicyRegistry;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.PolicyDecision;
import com.bea.security.xacml.PolicyDecisionPoint;
import com.bea.security.xacml.PolicyEvaluator;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.Record;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AuthorizationPolicyDecisionPoint extends PolicyDecisionPoint {
   private Configuration config;

   public AuthorizationPolicyDecisionPoint(Configuration config) throws URISyntaxException {
      super(config);
      this.config = config;
   }

   public AuthorizationPolicyDecisionPoint(Configuration config, URI policyCombiningAlgId) throws URISyntaxException {
      super(config, policyCombiningAlgId);
      this.config = config;
   }

   public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
      try {
         List pes = this.getEvaluators(context);
         return pes != null && !pes.isEmpty() ? (pes.size() == 1 ? (PolicyEvaluator)pes.get(0) : this.factory.createCombiner(pes)).evaluate(context) : PolicyDecision.getNotApplicableDecision();
      } catch (IndeterminateEvaluationException var3) {
         throw var3;
      } catch (XACMLException var4) {
         throw new IndeterminateEvaluationException(var4);
      }
   }

   public boolean hasApplicablePolicies(EvaluationCtx context) throws IndeterminateEvaluationException {
      try {
         Set authzPolicy = ((AuthorizationPolicyRegistry)this.config.getPolicyRegistry()).findAuthorizationPolicy(context);
         return authzPolicy != null && !authzPolicy.isEmpty();
      } catch (PolicyStoreException var3) {
         throw new IndeterminateEvaluationException(var3);
      } catch (URISyntaxException var4) {
         throw new IndeterminateEvaluationException(var4);
      } catch (DocumentParseException var5) {
         throw new IndeterminateEvaluationException(var5);
      }
   }

   private List getEvaluators(EvaluationCtx context) throws EvaluationPlanException, PolicyStoreException, DocumentParseException, URISyntaxException {
      Set authzPolicy = ((AuthorizationPolicyRegistry)this.config.getPolicyRegistry()).findAuthorizationPolicy(context);
      if (authzPolicy == null) {
         return null;
      } else {
         List pes = new ArrayList();
         Iterator var4 = authzPolicy.iterator();

         while(var4.hasNext()) {
            Record r = (Record)var4.next();
            pes.add(this.cache.getEvaluator(r));
         }

         return pes;
      }
   }
}
