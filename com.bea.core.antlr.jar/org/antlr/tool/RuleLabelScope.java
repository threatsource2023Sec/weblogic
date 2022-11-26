package org.antlr.tool;

import org.antlr.runtime.Token;

public class RuleLabelScope extends AttributeScope {
   public static AttributeScope predefinedRulePropertiesScope = new AttributeScope("RulePredefined", (Token)null) {
      {
         this.addAttribute("text", (String)null);
         this.addAttribute("start", (String)null);
         this.addAttribute("stop", (String)null);
         this.addAttribute("tree", (String)null);
         this.addAttribute("st", (String)null);
         this.isPredefinedRuleScope = true;
      }
   };
   public static AttributeScope predefinedTreeRulePropertiesScope = new AttributeScope("RulePredefined", (Token)null) {
      {
         this.addAttribute("text", (String)null);
         this.addAttribute("start", (String)null);
         this.addAttribute("tree", (String)null);
         this.addAttribute("st", (String)null);
         this.isPredefinedRuleScope = true;
      }
   };
   public static AttributeScope predefinedLexerRulePropertiesScope = new AttributeScope("LexerRulePredefined", (Token)null) {
      {
         this.addAttribute("text", (String)null);
         this.addAttribute("type", (String)null);
         this.addAttribute("line", (String)null);
         this.addAttribute("index", (String)null);
         this.addAttribute("pos", (String)null);
         this.addAttribute("channel", (String)null);
         this.addAttribute("start", (String)null);
         this.addAttribute("stop", (String)null);
         this.addAttribute("int", (String)null);
         this.isPredefinedLexerRuleScope = true;
      }
   };
   public static AttributeScope[] grammarTypeToRulePropertiesScope;
   public Rule referencedRule;

   public RuleLabelScope(Rule referencedRule, Token actionToken) {
      super("ref_" + referencedRule.name, actionToken);
      this.referencedRule = referencedRule;
   }

   public Attribute getAttribute(String name) {
      AttributeScope rulePropertiesScope = grammarTypeToRulePropertiesScope[this.grammar.type];
      if (rulePropertiesScope.getAttribute(name) != null) {
         return rulePropertiesScope.getAttribute(name);
      } else {
         return this.referencedRule.returnScope != null ? this.referencedRule.returnScope.getAttribute(name) : null;
      }
   }

   static {
      grammarTypeToRulePropertiesScope = new AttributeScope[]{null, predefinedLexerRulePropertiesScope, predefinedRulePropertiesScope, predefinedTreeRulePropertiesScope, predefinedRulePropertiesScope};
   }
}
