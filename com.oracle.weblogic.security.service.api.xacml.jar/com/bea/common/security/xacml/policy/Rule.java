package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.io.PrintStream;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Rule extends PolicySchemaObject {
   private static final long serialVersionUID = 1790096905544252513L;
   public static final String PERMIT = "Permit";
   public static final String DENY = "Deny";
   private String description;
   private Target target;
   private Condition condition;
   private String ruleId;
   private boolean isEffectPermit;

   public Rule(String ruleId, boolean isEffectPermit) {
      this(ruleId, isEffectPermit, (String)null);
   }

   public Rule(String ruleId, boolean isEffectPermit, String description) {
      this(ruleId, isEffectPermit, (Target)null, (Condition)null, description);
   }

   public Rule(String ruleId, boolean isEffectPermit, Target target) {
      this(ruleId, isEffectPermit, target, (Condition)null, (String)null);
   }

   public Rule(String ruleId, boolean isEffectPermit, Condition condition) {
      this(ruleId, isEffectPermit, (Target)null, condition, (String)null);
   }

   public Rule(String ruleId, boolean isEffectPermit, Target target, Condition condition) {
      this(ruleId, isEffectPermit, target, condition, (String)null);
   }

   public Rule(String ruleId, boolean isEffectPermit, Target target, Condition condition, String description) {
      this.ruleId = ruleId;
      this.isEffectPermit = isEffectPermit;
      this.target = target;
      this.condition = condition;
      this.description = description;
   }

   public Rule(AttributeRegistry registry, Node root) throws URISyntaxException, DocumentParseException {
      NamedNodeMap attrs = root.getAttributes();
      this.ruleId = attrs.getNamedItem("RuleId").getNodeValue();
      this.isEffectPermit = "Permit".equals(attrs.getNamedItem("Effect").getNodeValue());
      NodeList children = root.getChildNodes();

      for(int i = 0; i < children.getLength(); ++i) {
         Node child = children.item(i);
         String cname = this.getLocalName(child);
         if (cname.equals("Description")) {
            this.description = child.getFirstChild().getNodeValue();
         } else if (cname.equals("Target")) {
            this.target = new Target(registry, child);
         } else if (cname.equals("Condition")) {
            this.condition = new Condition(registry, child);
         }
      }

   }

   public String getElementName() {
      return "Rule";
   }

   public void encodeAttributes(PrintStream ps) {
      ps.print(" RuleId=\"");
      ps.print(this.escapeXML(this.ruleId));
      ps.print("\"");
      ps.print(" Effect=\"");
      ps.print(this.isEffectPermit ? "Permit" : "Deny");
      ps.print("\"");
   }

   public boolean hasChildren() {
      return true;
   }

   public void encodeChildren(Map nsMap, PrintStream ps) {
      if (this.description != null) {
         ps.print("<Description>");
         ps.print(this.description);
         ps.print("</Description>");
      }

      if (this.target != null) {
         this.target.encode(nsMap, ps);
      }

      if (this.condition != null) {
         this.condition.encode(nsMap, ps);
      }

   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Rule)) {
         return false;
      } else {
         Rule o = (Rule)other;
         return (this.target == o.target || this.target == null && o.target.isEmpty() || o.target == null && this.target.isEmpty() || this.target != null && this.target.equals(o.target)) && (this.condition == o.condition || this.condition != null && this.condition.equals(o.condition)) && (this.ruleId == o.ruleId || this.ruleId != null && this.ruleId.equals(o.ruleId)) && this.isEffectPermit == o.isEffectPermit;
      }
   }

   public int internalHashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.target);
      result = HashCodeUtil.hash(result, this.condition);
      result = HashCodeUtil.hash(result, this.ruleId);
      result = HashCodeUtil.hash(result, this.isEffectPermit);
      return result;
   }

   public String getDescription() {
      return this.description;
   }

   public Target getTarget() {
      return this.target;
   }

   public Condition getCondition() {
      return this.condition;
   }

   public String getRuleId() {
      return this.ruleId;
   }

   public boolean isEffectPermit() {
      return this.isEffectPermit;
   }
}
