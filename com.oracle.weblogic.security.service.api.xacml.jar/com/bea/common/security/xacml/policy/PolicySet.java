package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.SchemaObject;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PolicySet extends AbstractPolicy {
   private static final long serialVersionUID = -6991240254829029111L;
   private List policyCombinerParameters;
   private List policySetCombinerParameters;
   private List policiesPolicySetsAndReferences;
   private PolicySetDefaults defaults;

   public PolicySet(URI id, Target target, URI combiningAlgId, List policiesPolicySetsAndReferences) {
      super(id, target, combiningAlgId);
      this.policiesPolicySetsAndReferences = policiesPolicySetsAndReferences != null ? Collections.unmodifiableList(policiesPolicySetsAndReferences) : null;
   }

   public PolicySet(URI id, Target target, URI combiningAlgId, String description, List policiesPolicySetsAndReferences) {
      super(id, target, combiningAlgId, description);
      this.policiesPolicySetsAndReferences = policiesPolicySetsAndReferences != null ? Collections.unmodifiableList(policiesPolicySetsAndReferences) : null;
   }

   public PolicySet(URI id, Target target, URI combiningAlgId, String description, String version, List policiesPolicySetsAndReferences) {
      super(id, target, combiningAlgId, description, version);
      this.policiesPolicySetsAndReferences = policiesPolicySetsAndReferences != null ? Collections.unmodifiableList(policiesPolicySetsAndReferences) : null;
   }

   public PolicySet(URI id, Target target, URI combiningAlgId, String description, String version, PolicySetDefaults defaults, List combinerParameters, Obligations obligations, List policiesPolicySetsAndReferences) {
      super(id, target, combiningAlgId, description, version, combinerParameters, obligations);
      this.policiesPolicySetsAndReferences = policiesPolicySetsAndReferences != null ? Collections.unmodifiableList(policiesPolicySetsAndReferences) : null;
      this.defaults = defaults;
   }

   public PolicySet(URI id, Target target, URI combiningAlgId, String description, String version, PolicySetDefaults defaults, List combinerParameters, Obligations obligations, List policiesPolicySetsAndReferences, List policyCombinerParameters, List policySetCombinerParameters) {
      super(id, target, combiningAlgId, description, version, combinerParameters, obligations);
      this.policiesPolicySetsAndReferences = policiesPolicySetsAndReferences != null ? Collections.unmodifiableList(policiesPolicySetsAndReferences) : null;
      this.policyCombinerParameters = policyCombinerParameters != null ? Collections.unmodifiableList(policyCombinerParameters) : null;
      this.policySetCombinerParameters = policySetCombinerParameters != null ? Collections.unmodifiableList(policySetCombinerParameters) : null;
      this.defaults = defaults;
   }

   public PolicySet(AttributeRegistry registry, Node root) throws DocumentParseException, URISyntaxException {
      super(registry, root, "PolicySet", "PolicyCombiningAlgId");
      this.policyCombinerParameters = new ArrayList();
      this.policySetCombinerParameters = new ArrayList();
      this.policiesPolicySetsAndReferences = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         String cname = this.getLocalName(node);
         if (cname.equals("PolicySetCombinerParameters")) {
            this.policySetCombinerParameters.add(new PolicySetCombinerParameters(registry, node));
         } else if (cname.equals("PolicyCombinerParameters")) {
            this.policyCombinerParameters.add(new PolicyCombinerParameters(registry, node));
         } else if (cname.equals("PolicySet")) {
            this.policiesPolicySetsAndReferences.add(new PolicySet(registry, node));
         } else if (cname.equals("Policy")) {
            this.policiesPolicySetsAndReferences.add(new Policy(registry, node));
         } else if (cname.equals("PolicySetIdReference")) {
            this.policiesPolicySetsAndReferences.add(new PolicySetIdReference(node));
         } else if (cname.equals("PolicyIdReference")) {
            this.policiesPolicySetsAndReferences.add(new PolicyIdReference(node));
         } else if (cname.equals("PolicySetDefaults")) {
            this.defaults = new PolicySetDefaults(node);
         }
      }

      this.policyCombinerParameters = this.policyCombinerParameters.isEmpty() ? null : Collections.unmodifiableList(this.policyCombinerParameters);
      this.policySetCombinerParameters = this.policySetCombinerParameters.isEmpty() ? null : Collections.unmodifiableList(this.policySetCombinerParameters);
      this.policiesPolicySetsAndReferences = this.policiesPolicySetsAndReferences.isEmpty() ? null : Collections.unmodifiableList(this.policiesPolicySetsAndReferences);
   }

   protected String getPolicyPrefix() {
      return "PolicySet";
   }

   protected String getCombiningName() {
      return "PolicyCombiningAlgId";
   }

   public IdReference getReference() {
      return new PolicySetIdReference(this.getId(), this.getVersion());
   }

   public String getElementName() {
      return "PolicySet";
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      this.encodeDescription(ps);
      if (this.defaults != null) {
         this.defaults.encode(nsMap, ps);
      }

      this.encodeTarget(nsMap, ps);
      this.encodeCombinerParameters(nsMap, ps);
      Iterator it;
      if (this.policyCombinerParameters != null) {
         it = this.policyCombinerParameters.iterator();

         while(it.hasNext()) {
            PolicyCombinerParameters cp = (PolicyCombinerParameters)it.next();
            cp.encode(nsMap, ps);
         }
      }

      if (this.policySetCombinerParameters != null) {
         it = this.policySetCombinerParameters.iterator();

         while(it.hasNext()) {
            PolicySetCombinerParameters cp = (PolicySetCombinerParameters)it.next();
            cp.encode(nsMap, ps);
         }
      }

      if (this.policiesPolicySetsAndReferences != null) {
         it = this.policiesPolicySetsAndReferences.iterator();

         while(it.hasNext()) {
            PolicySetMember psm = (PolicySetMember)it.next();
            ((SchemaObject)psm).encode(nsMap, ps);
         }
      }

      this.encodeObligations(nsMap, ps);
   }

   public boolean equals(Object other) {
      if (super.equals(other) && other instanceof PolicySet) {
         PolicySet o = (PolicySet)other;
         return CollectionUtil.equals(this.policyCombinerParameters, o.policyCombinerParameters) && CollectionUtil.equals(this.policySetCombinerParameters, o.policySetCombinerParameters) && CollectionUtil.equalsWithSequence(this.policiesPolicySetsAndReferences, o.policiesPolicySetsAndReferences) && (this.defaults == o.defaults || this.defaults != null && this.defaults.equals(o.defaults));
      } else {
         return false;
      }
   }

   public int internalHashCode() {
      int result = super.internalHashCode();
      result = HashCodeUtil.hash(result, this.policyCombinerParameters);
      result = HashCodeUtil.hash(result, this.policySetCombinerParameters);
      result = HashCodeUtil.hash(result, this.policiesPolicySetsAndReferences);
      result = HashCodeUtil.hash(result, this.defaults);
      return result;
   }

   public PolicySetDefaults getDefaults() {
      return this.defaults;
   }

   public List getPolicyCombinerParameters() {
      return this.policyCombinerParameters;
   }

   public List getPolicySetCombinerParameters() {
      return this.policySetCombinerParameters;
   }

   public List getPoliciesPolicySetsAndReferences() {
      return this.policiesPolicySetsAndReferences;
   }
}
