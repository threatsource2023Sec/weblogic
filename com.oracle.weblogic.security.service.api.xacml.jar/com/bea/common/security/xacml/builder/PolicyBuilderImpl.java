package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.PolicyUtils;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.Environments;
import com.bea.common.security.xacml.policy.Obligation;
import com.bea.common.security.xacml.policy.Obligations;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyDefaults;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Rule;
import com.bea.common.security.xacml.policy.Subjects;
import com.bea.common.security.xacml.policy.Target;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import weblogic.utils.XXEUtils;

class PolicyBuilderImpl extends AbstractBuilderBase implements PolicyBuilder {
   private List obligationList = new ArrayList();
   private Map ruleMap = new LinkedHashMap();
   private URI id;
   private String version = "1.0";
   private URI ruleCombiningAlgorithm;

   PolicyBuilderImpl() {
   }

   PolicyBuilderImpl(Policy policy) throws XACMLException {
      this.init(policy);
   }

   PolicyBuilderImpl(String policyString) throws XACMLException {
      if (policyString != null && policyString.trim().length() != 0) {
         Policy policy = PolicyUtils.readPolicy(new AttributeRegistry(), policyString, XXEUtils.createDocumentBuilderFactoryInstance(), true);
         this.init(policy);
      } else {
         throw new InvalidParameterException("The policy string should not be null.");
      }
   }

   PolicyBuilderImpl(URI policyId) throws XACMLException {
      if (policyId == null) {
         throw new InvalidParameterException("The policy id should not be null.");
      } else {
         this.id = policyId;
      }
   }

   private void init(Policy policy) throws InvalidParameterException {
      if (policy == null) {
         throw new InvalidParameterException("The policy should not be null.");
      } else {
         super.init(policy.getTarget().getSubjects(), policy.getTarget().getResources(), policy.getTarget().getActions(), policy.getTarget().getEnvironments(), policy.getDescription());
         this.id = policy.getId();
         this.ruleCombiningAlgorithm = policy.getCombiningAlgId();
         this.version = policy.getVersion();
         Iterator ite = policy.getRules().iterator();

         while(ite.hasNext()) {
            Rule rule = (Rule)ite.next();
            this.ruleMap.put(rule.getRuleId(), rule);
         }

         Obligations xacmlObs = policy.getObligations();
         if (xacmlObs != null && xacmlObs.getObligations() != null) {
            this.obligationList.addAll(xacmlObs.getObligations());
         }

      }
   }

   protected PolicyBuilder getInstance() {
      return this;
   }

   public PolicyBuilder setPolicyId(String id) throws InvalidParameterException {
      if (id != null && id.trim().length() != 0) {
         try {
            this.id = new URI(id);
            return this;
         } catch (URISyntaxException var3) {
            throw new InvalidParameterException(var3);
         }
      } else {
         throw new InvalidParameterException("The policy id should not be null or empty.");
      }
   }

   public PolicyBuilder setVersion(String version) throws InvalidParameterException {
      if (version != null) {
         this.checkVersion(version);
         this.version = version;
      }

      return this;
   }

   public PolicyBuilder setRuleCombiningAlgorithm(CombiningAlgorithm algorithmId) throws InvalidParameterException {
      if (algorithmId == null) {
         throw new InvalidParameterException("The algorithm should not be null.");
      } else if (CombiningAlgorithm.ONLY_ONE_APPLICABLE_POLICY.equals(algorithmId)) {
         throw new InvalidParameterException("\"only-one-applicable\" can not be used as rule combining algorithm.");
      } else {
         this.ruleCombiningAlgorithm = algorithmId.getRuleCombiningAlgorithm();
         return this;
      }
   }

   public PolicyBuilder addObligation(Obligation obligation) {
      if (obligation != null) {
         this.obligationList.add(obligation);
      }

      return this;
   }

   public PolicyBuilder addRule(Rule rule) {
      if (rule != null) {
         this.ruleMap.put(rule.getRuleId(), rule);
      }

      return this;
   }

   public Policy getResult() throws InvalidXACMLPolicyException {
      if (this.id == null) {
         throw new InvalidXACMLPolicyException("The policy id should not be null or empty.");
      } else if (this.ruleCombiningAlgorithm == null) {
         throw new InvalidXACMLPolicyException("The rule combining algorithm should be defined.");
      } else if (this.ruleMap.size() == 0) {
         throw new InvalidXACMLPolicyException("At least one rule should be defined for the policy.");
      } else {
         Target target = this.getTarget();
         if (target == null) {
            target = new Target((Subjects)null, (Resources)null, (Actions)null, (Environments)null);
         }

         Obligations obligations = null;
         if (!this.obligationList.isEmpty()) {
            obligations = new Obligations(this.obligationList);
         }

         Policy policy = new Policy(this.id, target, this.ruleCombiningAlgorithm, this.description, this.version, (PolicyDefaults)null, (List)null, obligations, new ArrayList(this.ruleMap.values()), (List)null, (Collection)null);

         try {
            PolicyUtils.checkXACMLSchema(policy.toString());
            return policy;
         } catch (XACMLException var5) {
            throw new InvalidXACMLPolicyException(var5);
         }
      }
   }

   public String getResultAsXML() throws InvalidXACMLPolicyException {
      return this.getResult().toString();
   }

   public List removeAllRules() {
      List tmp = new ArrayList(this.ruleMap.values());
      this.ruleMap.clear();
      return tmp;
   }

   public Obligations removeObligations() {
      Obligations tmp = new Obligations(this.obligationList);
      this.obligationList = new ArrayList();
      return tmp;
   }

   public Rule removeRule(String ruleId) {
      return (Rule)this.ruleMap.remove(ruleId);
   }
}
