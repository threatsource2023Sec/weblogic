package com.bea.common.security.xacml.builder;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.PolicyUtils;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.Environments;
import com.bea.common.security.xacml.policy.IdReference;
import com.bea.common.security.xacml.policy.Obligation;
import com.bea.common.security.xacml.policy.Obligations;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyIdReference;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.PolicySetDefaults;
import com.bea.common.security.xacml.policy.PolicySetIdReference;
import com.bea.common.security.xacml.policy.PolicySetMember;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Subjects;
import com.bea.common.security.xacml.policy.Target;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import weblogic.utils.XXEUtils;

class PolicySetBuilderImpl extends AbstractBuilderBase implements PolicySetBuilder {
   private List obligationList = new ArrayList();
   private URI id;
   private String version = "1.0";
   private URI policyCombiningAlgorithm;
   private Map policySetMemberMap = new LinkedHashMap();

   PolicySetBuilderImpl() {
   }

   PolicySetBuilderImpl(PolicySet policySet) throws XACMLException {
      this.init(policySet);
   }

   PolicySetBuilderImpl(String policySetString) throws XACMLException {
      if (policySetString != null && policySetString.trim().length() != 0) {
         PolicySet policySet = PolicyUtils.readPolicySet(new AttributeRegistry(), policySetString, XXEUtils.createDocumentBuilderFactoryInstance(), true);
         this.init(policySet);
      } else {
         throw new InvalidParameterException("The policy set string should not be null or empty.");
      }
   }

   PolicySetBuilderImpl(URI policySetId) throws XACMLException {
      if (policySetId == null) {
         throw new InvalidParameterException("The policy set id should not be null.");
      } else {
         this.id = policySetId;
      }
   }

   private void init(PolicySet policySet) throws XACMLException {
      if (policySet == null) {
         throw new InvalidParameterException("The policy set should not be null.");
      } else {
         Target target = policySet.getTarget();
         if (target != null) {
            super.init(target.getSubjects(), target.getResources(), target.getActions(), target.getEnvironments(), policySet.getDescription());
         } else {
            super.init((Subjects)null, (Resources)null, (Actions)null, (Environments)null, policySet.getDescription());
         }

         this.id = policySet.getId();
         this.version = policySet.getVersion();
         this.policyCombiningAlgorithm = policySet.getCombiningAlgId();
         List memberList = policySet.getPoliciesPolicySetsAndReferences();
         Iterator ite = memberList.iterator();

         while(ite.hasNext()) {
            PolicySetMember mem = (PolicySetMember)ite.next();
            String type;
            if (mem instanceof IdReference) {
               IdReference idRef = (IdReference)mem;
               type = idRef instanceof PolicyIdReference ? "policy" : "policyset";
               this.policySetMemberMap.put(new MemberId(idRef.getReference(), idRef.getVersion(), type), mem);
            } else if (mem instanceof AbstractPolicy) {
               AbstractPolicy absPolicy = (AbstractPolicy)mem;
               type = absPolicy instanceof Policy ? "policy" : "policyset";
               this.policySetMemberMap.put(new MemberId(absPolicy.getId(), absPolicy.getVersion(), type), mem);
            }
         }

         Obligations xacmlObs = policySet.getObligations();
         if (xacmlObs != null && xacmlObs.getObligations() != null) {
            this.obligationList.addAll(xacmlObs.getObligations());
         }

      }
   }

   protected PolicySetBuilder getInstance() {
      return this;
   }

   public PolicySetBuilder setPolicySetId(String id) throws InvalidParameterException {
      if (id != null && id.trim().length() != 0) {
         try {
            this.id = new URI(id);
            return this;
         } catch (URISyntaxException var3) {
            throw new InvalidParameterException(var3);
         }
      } else {
         throw new InvalidParameterException("The policy set id should not be null or empty.");
      }
   }

   public PolicySetBuilder setVersion(String version) throws InvalidParameterException {
      if (version != null) {
         this.checkVersion(version);
         this.version = version;
      }

      return this;
   }

   public PolicySetBuilder setPolicyCombiningAlgorithm(CombiningAlgorithm algorithmId) throws InvalidParameterException {
      if (algorithmId == null) {
         throw new InvalidParameterException("The combining algorithm should not be null.");
      } else {
         this.policyCombiningAlgorithm = algorithmId.getPolicyCombiningAlgorithm();
         return this;
      }
   }

   public PolicySetBuilder addObligation(Obligation obligation) {
      if (obligation != null) {
         this.obligationList.add(obligation);
      }

      return this;
   }

   public PolicySetBuilder addPolicy(Policy policy) {
      if (policy != null) {
         MemberId id = new MemberId(policy.getId(), policy.getVersion(), "policy");
         this.policySetMemberMap.put(id, policy);
      }

      return this;
   }

   public PolicySetBuilder addPolicyIdRef(String policyId) throws InvalidParameterException {
      return this.addPolicyIdRef(policyId, "1.0", (String)null, (String)null);
   }

   public PolicySetBuilder addPolicyIdRef(String policyId, String version, String earliestVer, String latestVer) throws InvalidParameterException {
      if (policyId != null && policyId.trim().length() != 0) {
         URI idURI = null;

         try {
            idURI = new URI(policyId);
         } catch (URISyntaxException var7) {
            throw new InvalidParameterException(var7);
         }

         this.checkVersionMatch(version);
         this.checkVersionMatch(earliestVer);
         this.checkVersionMatch(latestVer);
         PolicyIdReference ref = null;
         ref = new PolicyIdReference(idURI, version, earliestVer, latestVer);
         this.policySetMemberMap.put(new MemberId(ref.getReference(), version, "policy"), ref);
         return this;
      } else {
         throw new InvalidParameterException("The policy id should not be null or empty.");
      }
   }

   public PolicySetBuilder addPolicySet(PolicySet policySet) {
      if (policySet != null) {
         MemberId id = new MemberId(policySet.getId(), policySet.getVersion(), "policy");
         this.policySetMemberMap.put(id, policySet);
      }

      return this;
   }

   public PolicySetBuilder addPolicySetIdRef(String policySetId) throws InvalidParameterException {
      return this.addPolicySetIdRef(policySetId, "1.0", (String)null, (String)null);
   }

   public PolicySetBuilder addPolicySetIdRef(String policySetId, String version, String earliestVer, String latestVer) throws InvalidParameterException {
      if (policySetId != null && policySetId.trim().length() != 0) {
         URI idURI = null;

         try {
            idURI = new URI(policySetId);
         } catch (URISyntaxException var7) {
            throw new InvalidParameterException(var7);
         }

         this.checkVersionMatch(version);
         this.checkVersionMatch(earliestVer);
         this.checkVersionMatch(latestVer);
         PolicySetIdReference ref = null;
         ref = new PolicySetIdReference(idURI, version, earliestVer, latestVer);
         this.policySetMemberMap.put(new MemberId(ref.getReference(), version, "policyset"), ref);
         return this;
      } else {
         throw new InvalidParameterException("The policy set id should not be null or empty.");
      }
   }

   public List removeAll() {
      List tmp = new ArrayList(this.policySetMemberMap.values());
      this.policySetMemberMap.clear();
      return tmp;
   }

   public Obligations removeObligations() {
      Obligations tmp = new Obligations(this.obligationList);
      this.obligationList = new ArrayList();
      return tmp;
   }

   public PolicySetMember removePolicy(String policyId) throws InvalidParameterException {
      return this.removePolicy(policyId, "1.0");
   }

   public PolicySetMember removePolicy(String policyId, String version) throws InvalidParameterException {
      if (policyId != null && policyId.trim().length() != 0) {
         version = version == null ? "1.0" : version;
         this.checkVersion(version);
         URI id = null;

         try {
            id = new URI(policyId);
         } catch (URISyntaxException var5) {
            throw new InvalidParameterException(var5);
         }

         return (PolicySetMember)this.policySetMemberMap.remove(new MemberId(id, version, "policy"));
      } else {
         throw new InvalidParameterException("The policy id should not be null or empty.");
      }
   }

   public PolicySetMember removePolicySet(String policySetId) throws InvalidParameterException {
      return this.removePolicySet(policySetId, "1.0");
   }

   public PolicySetMember removePolicySet(String policySetId, String version) throws InvalidParameterException {
      if (policySetId != null && policySetId.trim().length() != 0) {
         version = version == null ? "1.0" : version;
         this.checkVersion(version);
         URI id = null;

         try {
            id = new URI(policySetId);
         } catch (URISyntaxException var5) {
            throw new InvalidParameterException(var5);
         }

         return (PolicySetMember)this.policySetMemberMap.remove(new MemberId(id, version, "policyset"));
      } else {
         throw new InvalidParameterException("The policy set id should not be null or empty.");
      }
   }

   public PolicySet getResult() throws InvalidXACMLPolicyException {
      if (this.id == null) {
         throw new InvalidXACMLPolicyException("The policy set id should not be null or empty.");
      } else if (this.policyCombiningAlgorithm == null) {
         throw new InvalidXACMLPolicyException("The policy combining algorithm should be defined.");
      } else if (this.policySetMemberMap.size() == 0) {
         throw new InvalidXACMLPolicyException("At least one child policy or policy set should be defined for the created policy set.");
      } else {
         List memberList = new ArrayList(this.policySetMemberMap.values());
         Target target = this.getTarget();
         if (target == null) {
            target = new Target((Subjects)null, (Resources)null, (Actions)null, (Environments)null);
         }

         Obligations obligations = null;
         if (!this.obligationList.isEmpty()) {
            obligations = new Obligations(this.obligationList);
         }

         PolicySet policySet = new PolicySet(this.id, target, this.policyCombiningAlgorithm, this.description, this.version, (PolicySetDefaults)null, (List)null, obligations, memberList, (List)null, (List)null);

         try {
            PolicyUtils.checkXACMLSchema(policySet.toString());
            return policySet;
         } catch (XACMLException var6) {
            throw new InvalidXACMLPolicyException(var6);
         }
      }
   }

   public String getResultAsXML() throws InvalidXACMLPolicyException {
      return this.getResult().toString();
   }

   private static class MemberId {
      private static final String POLICY_SET = "policyset";
      private static final String POLICY = "policy";
      private URI id;
      private String version;
      private String type;

      private MemberId(URI id, String version, String type) {
         this.id = id;
         this.version = version;
         this.type = type;
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof MemberId)) {
            return false;
         } else {
            MemberId memId = (MemberId)o;
            return (this.id == null && this.id == memId.id || this.id.equals(memId.id)) && (this.version == null && this.version == memId.version || this.version.equals(memId.version)) && (this.type == null && this.type == memId.type || this.type.equals(memId.type));
         }
      }

      public int hashCode() {
         int result = 23;
         result = HashCodeUtil.hash(result, this.id);
         result = HashCodeUtil.hash(result, this.version);
         result = HashCodeUtil.hash(result, this.type);
         return result;
      }

      // $FF: synthetic method
      MemberId(URI x0, String x1, String x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
