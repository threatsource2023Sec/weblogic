package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Policy extends AbstractPolicy {
   private static final long serialVersionUID = 6309705897148961592L;
   private List ruleCombinerParameters;
   private List rules;
   private Collection variableDefinitions;
   private PolicyDefaults defaults;

   public Policy(URI id, Target target, URI combiningAlgId, List rules) {
      super(id, target, combiningAlgId);
      this.rules = rules != null ? Collections.unmodifiableList(rules) : null;
   }

   public Policy(URI id, Target target, URI combiningAlgId, String description, List rules) {
      super(id, target, combiningAlgId, description);
      this.rules = rules != null ? Collections.unmodifiableList(rules) : null;
   }

   public Policy(URI id, Target target, URI combiningAlgId, String description, String version, List rules) {
      super(id, target, combiningAlgId, description, version);
      this.rules = rules != null ? Collections.unmodifiableList(rules) : null;
   }

   public Policy(URI id, Target target, URI combiningAlgId, String description, String version, PolicyDefaults defaults, List combinerParameters, Obligations obligations, List rules) {
      super(id, target, combiningAlgId, description, version, combinerParameters, obligations);
      this.defaults = defaults;
      this.rules = rules != null ? Collections.unmodifiableList(rules) : null;
   }

   public Policy(URI id, Target target, URI combiningAlgId, String description, String version, PolicyDefaults defaults, List combinerParameters, Obligations obligations, List rules, List ruleCombinerParameters, Collection variableDefinitions) {
      super(id, target, combiningAlgId, description, version, combinerParameters, obligations);
      this.defaults = defaults;
      this.ruleCombinerParameters = ruleCombinerParameters != null ? Collections.unmodifiableList(ruleCombinerParameters) : null;
      this.rules = rules != null ? Collections.unmodifiableList(rules) : null;
      this.variableDefinitions = variableDefinitions != null ? Collections.unmodifiableCollection(variableDefinitions) : null;
   }

   public Policy(AttributeRegistry registry, Node root) throws DocumentParseException, URISyntaxException {
      super(registry, root, "Policy", "RuleCombiningAlgId");
      this.ruleCombinerParameters = new ArrayList();
      this.rules = new ArrayList();
      this.variableDefinitions = new ArrayList();
      NodeList nodes = root.getChildNodes();

      for(int i = 0; i < nodes.getLength(); ++i) {
         Node node = nodes.item(i);
         String cname = this.getLocalName(node);
         if (cname.equals("RuleCombinerParameters")) {
            this.ruleCombinerParameters.add(new RuleCombinerParameters(registry, node));
         } else if (cname.equals("Rule")) {
            this.rules.add(new Rule(registry, node));
         } else if (cname.equals("VariableDefinition")) {
            this.variableDefinitions.add(new VariableDefinition(registry, node));
         } else if (cname.equals("PolicyDefaults")) {
            this.defaults = new PolicyDefaults(node);
         }
      }

      this.ruleCombinerParameters = this.ruleCombinerParameters.isEmpty() ? null : Collections.unmodifiableList(this.ruleCombinerParameters);
      this.rules = this.rules.isEmpty() ? null : Collections.unmodifiableList(this.rules);
      this.variableDefinitions = this.variableDefinitions.isEmpty() ? null : Collections.unmodifiableCollection(this.variableDefinitions);
   }

   protected String getPolicyPrefix() {
      return "Policy";
   }

   protected String getCombiningName() {
      return "RuleCombiningAlgId";
   }

   public IdReference getReference() {
      return new PolicyIdReference(this.getId(), this.getVersion());
   }

   public String getElementName() {
      return "Policy";
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
      if (this.ruleCombinerParameters != null) {
         it = this.ruleCombinerParameters.iterator();

         while(it.hasNext()) {
            RuleCombinerParameters cp = (RuleCombinerParameters)it.next();
            cp.encode(nsMap, ps);
         }
      }

      if (this.variableDefinitions != null) {
         it = this.variableDefinitions.iterator();

         while(it.hasNext()) {
            VariableDefinition vd = (VariableDefinition)it.next();
            vd.encode(nsMap, ps);
         }
      }

      if (this.rules != null) {
         it = this.rules.iterator();

         while(it.hasNext()) {
            Rule r = (Rule)it.next();
            r.encode(nsMap, ps);
         }
      }

      this.encodeObligations(nsMap, ps);
   }

   public boolean equals(Object other) {
      if (super.equals(other) && other instanceof Policy) {
         Policy o = (Policy)other;
         return CollectionUtil.equals(this.ruleCombinerParameters, o.ruleCombinerParameters) && CollectionUtil.equalsWithSequence(this.rules, o.rules) && CollectionUtil.equals(this.variableDefinitions, o.variableDefinitions) && (this.defaults == o.defaults || this.defaults != null && this.defaults.equals(o.defaults));
      } else {
         return false;
      }
   }

   public int internalHashCode() {
      int result = super.internalHashCode();
      result = HashCodeUtil.hash(result, this.ruleCombinerParameters);
      result = HashCodeUtil.hash(result, this.rules);
      result = HashCodeUtil.hash(result, this.variableDefinitions);
      result = HashCodeUtil.hash(result, this.defaults);
      return result;
   }

   public PolicyDefaults getDefaults() {
      return this.defaults;
   }

   public List getRuleCombinerParameters() {
      return this.ruleCombinerParameters;
   }

   public List getRules() {
      return this.rules;
   }

   public Collection getVariableDefinitions() {
      return this.variableDefinitions;
   }
}
