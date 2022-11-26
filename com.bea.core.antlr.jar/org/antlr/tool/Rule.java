package org.antlr.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.analysis.NFAState;
import org.antlr.codegen.CodeGenerator;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;

public class Rule {
   public static final boolean supportsLabelOptimization = false;
   public String name;
   public int index;
   public String modifier;
   public NFAState startState;
   public NFAState stopState;
   protected Map options;
   public static final Set legalOptions = new HashSet() {
      {
         this.add("k");
         this.add("greedy");
         this.add("memoize");
         this.add("backtrack");
      }
   };
   public GrammarAST tree;
   public Grammar grammar;
   public GrammarAST argActionAST;
   public GrammarAST EORNode;
   public AttributeScope returnScope;
   public AttributeScope parameterScope;
   public AttributeScope ruleScope;
   public List useScopes;
   public Set throwsSpec;
   public LinkedHashMap tokenLabels;
   public LinkedHashMap wildcardTreeLabels;
   public LinkedHashMap wildcardTreeListLabels;
   public LinkedHashMap charLabels;
   public LinkedHashMap ruleLabels;
   public LinkedHashMap tokenListLabels;
   public LinkedHashMap ruleListLabels;
   protected Map labelNameSpace = new HashMap();
   protected Map actions = new HashMap();
   protected List inlineActions = new ArrayList();
   public int numberOfAlts;
   protected Map[] altToTokenRefMap;
   protected Map[] altToRuleRefMap;
   public boolean referencedPredefinedRuleAttributes = false;
   public boolean isSynPred = false;
   public boolean imported = false;

   public Rule(Grammar grammar, String ruleName, int ruleIndex, int numberOfAlts) {
      this.name = ruleName;
      this.index = ruleIndex;
      this.numberOfAlts = numberOfAlts;
      this.grammar = grammar;
      this.throwsSpec = new HashSet();
      this.altToTokenRefMap = (Map[])(new Map[numberOfAlts + 1]);
      this.altToRuleRefMap = (Map[])(new Map[numberOfAlts + 1]);

      for(int alt = 1; alt <= numberOfAlts; ++alt) {
         this.altToTokenRefMap[alt] = new HashMap();
         this.altToRuleRefMap[alt] = new HashMap();
      }

   }

   public static int getRuleType(String ruleName) {
      if (ruleName != null && ruleName.length() != 0) {
         return Character.isUpperCase(ruleName.charAt(0)) ? 1 : 2;
      } else {
         throw new IllegalArgumentException("The specified rule name is not valid.");
      }
   }

   public void defineLabel(Token label, GrammarAST elementRef, int type) {
      Grammar.LabelElementPair pair = this.grammar.new LabelElementPair(label, elementRef);
      pair.type = type;
      this.labelNameSpace.put(label.getText(), pair);
      switch (type) {
         case 1:
            if (this.ruleLabels == null) {
               this.ruleLabels = new LinkedHashMap();
            }

            this.ruleLabels.put(label.getText(), pair);
            break;
         case 2:
            if (this.tokenLabels == null) {
               this.tokenLabels = new LinkedHashMap();
            }

            this.tokenLabels.put(label.getText(), pair);
            break;
         case 3:
            if (this.ruleListLabels == null) {
               this.ruleListLabels = new LinkedHashMap();
            }

            this.ruleListLabels.put(label.getText(), pair);
            break;
         case 4:
            if (this.tokenListLabels == null) {
               this.tokenListLabels = new LinkedHashMap();
            }

            this.tokenListLabels.put(label.getText(), pair);
            break;
         case 5:
            if (this.charLabels == null) {
               this.charLabels = new LinkedHashMap();
            }

            this.charLabels.put(label.getText(), pair);
            break;
         case 6:
            if (this.wildcardTreeLabels == null) {
               this.wildcardTreeLabels = new LinkedHashMap();
            }

            this.wildcardTreeLabels.put(label.getText(), pair);
            break;
         case 7:
            if (this.wildcardTreeListLabels == null) {
               this.wildcardTreeListLabels = new LinkedHashMap();
            }

            this.wildcardTreeListLabels.put(label.getText(), pair);
      }

   }

   public Grammar.LabelElementPair getLabel(String name) {
      return (Grammar.LabelElementPair)this.labelNameSpace.get(name);
   }

   public Grammar.LabelElementPair getTokenLabel(String name) {
      Grammar.LabelElementPair pair = null;
      return (Grammar.LabelElementPair)(this.tokenLabels != null ? (Grammar.LabelElementPair)this.tokenLabels.get(name) : pair);
   }

   public Map getRuleLabels() {
      return this.ruleLabels;
   }

   public Map getRuleListLabels() {
      return this.ruleListLabels;
   }

   public Grammar.LabelElementPair getRuleLabel(String name) {
      Grammar.LabelElementPair pair = null;
      return (Grammar.LabelElementPair)(this.ruleLabels != null ? (Grammar.LabelElementPair)this.ruleLabels.get(name) : pair);
   }

   public Grammar.LabelElementPair getTokenListLabel(String name) {
      Grammar.LabelElementPair pair = null;
      return (Grammar.LabelElementPair)(this.tokenListLabels != null ? (Grammar.LabelElementPair)this.tokenListLabels.get(name) : pair);
   }

   public Grammar.LabelElementPair getRuleListLabel(String name) {
      Grammar.LabelElementPair pair = null;
      return (Grammar.LabelElementPair)(this.ruleListLabels != null ? (Grammar.LabelElementPair)this.ruleListLabels.get(name) : pair);
   }

   public void trackTokenReferenceInAlt(GrammarAST refAST, int outerAltNum) {
      List refs = (List)this.altToTokenRefMap[outerAltNum].get(refAST.getText());
      if (refs == null) {
         refs = new ArrayList();
         this.altToTokenRefMap[outerAltNum].put(refAST.getText(), refs);
      }

      ((List)refs).add(refAST);
   }

   public List getTokenRefsInAlt(String ref, int outerAltNum) {
      if (this.altToTokenRefMap[outerAltNum] != null) {
         List tokenRefASTs = (List)this.altToTokenRefMap[outerAltNum].get(ref);
         return tokenRefASTs;
      } else {
         return null;
      }
   }

   public void trackRuleReferenceInAlt(GrammarAST refAST, int outerAltNum) {
      List refs = (List)this.altToRuleRefMap[outerAltNum].get(refAST.getText());
      if (refs == null) {
         refs = new ArrayList();
         this.altToRuleRefMap[outerAltNum].put(refAST.getText(), refs);
      }

      ((List)refs).add(refAST);
   }

   public List getRuleRefsInAlt(String ref, int outerAltNum) {
      if (this.altToRuleRefMap[outerAltNum] != null) {
         List ruleRefASTs = (List)this.altToRuleRefMap[outerAltNum].get(ref);
         return ruleRefASTs;
      } else {
         return null;
      }
   }

   public Set getTokenRefsInAlt(int altNum) {
      return this.altToTokenRefMap[altNum].keySet();
   }

   public Set getAllTokenRefsInAltsWithRewrites() {
      String output = (String)this.grammar.getOption("output");
      Set tokens = new HashSet();
      if (output != null && output.equals("AST")) {
         for(int i = 1; i <= this.numberOfAlts; ++i) {
            if (this.hasRewrite(i)) {
               Map m = this.altToTokenRefMap[i];
               Iterator i$ = m.keySet().iterator();

               while(i$.hasNext()) {
                  String tokenName = (String)i$.next();
                  int ttype = this.grammar.getTokenType(tokenName);
                  String label = this.grammar.generator.getTokenTypeAsTargetLabel(ttype);
                  tokens.add(label);
               }
            }
         }

         return tokens;
      } else {
         return tokens;
      }
   }

   public Set getRuleRefsInAlt(int outerAltNum) {
      return this.altToRuleRefMap[outerAltNum].keySet();
   }

   public Set getAllRuleRefsInAltsWithRewrites() {
      Set rules = new HashSet();

      for(int i = 1; i <= this.numberOfAlts; ++i) {
         if (this.hasRewrite(i)) {
            Map m = this.altToRuleRefMap[i];
            rules.addAll(m.keySet());
         }
      }

      return rules;
   }

   public List getInlineActions() {
      return this.inlineActions;
   }

   public boolean hasRewrite(int i) {
      GrammarAST blk = this.tree.findFirstType(16);
      GrammarAST alt = blk.getBlockALT(i);
      GrammarAST rew = alt.getNextSibling();
      if (rew != null && rew.getType() == 76) {
         return true;
      } else {
         return alt.findFirstType(76) != null;
      }
   }

   public AttributeScope getAttributeScope(String name) {
      AttributeScope scope = this.getLocalAttributeScope(name);
      if (scope != null) {
         return scope;
      } else {
         if (this.ruleScope != null && this.ruleScope.getAttribute(name) != null) {
            scope = this.ruleScope;
         }

         return scope;
      }
   }

   public AttributeScope getLocalAttributeScope(String name) {
      AttributeScope scope = null;
      if (this.returnScope != null && this.returnScope.getAttribute(name) != null) {
         scope = this.returnScope;
      } else if (this.parameterScope != null && this.parameterScope.getAttribute(name) != null) {
         scope = this.parameterScope;
      } else {
         AttributeScope rulePropertiesScope = RuleLabelScope.grammarTypeToRulePropertiesScope[this.grammar.type];
         if (rulePropertiesScope.getAttribute(name) != null) {
            scope = rulePropertiesScope;
         }
      }

      return scope;
   }

   public String getElementLabel(String refdSymbol, int outerAltNum, CodeGenerator generator) {
      GrammarAST uniqueRefAST;
      List ruleRefs;
      if (this.grammar.type != 1 && Character.isUpperCase(refdSymbol.charAt(0))) {
         ruleRefs = this.getTokenRefsInAlt(refdSymbol, outerAltNum);
         uniqueRefAST = (GrammarAST)ruleRefs.get(0);
      } else {
         ruleRefs = this.getRuleRefsInAlt(refdSymbol, outerAltNum);
         uniqueRefAST = (GrammarAST)ruleRefs.get(0);
      }

      if (uniqueRefAST.code == null) {
         return null;
      } else {
         String existingLabelName = (String)uniqueRefAST.code.getAttribute("label");
         String labelName;
         if (existingLabelName != null) {
            labelName = existingLabelName;
         } else {
            labelName = generator.createUniqueLabel(refdSymbol);
            CommonToken label = new CommonToken(43, labelName);
            if (this.grammar.type != 1 && Character.isUpperCase(refdSymbol.charAt(0))) {
               this.grammar.defineTokenRefLabel(this.name, label, uniqueRefAST);
            } else {
               this.grammar.defineRuleRefLabel(this.name, label, uniqueRefAST);
            }

            uniqueRefAST.code.add("label", labelName);
         }

         return labelName;
      }
   }

   public boolean getHasMultipleReturnValues() {
      return this.referencedPredefinedRuleAttributes || this.grammar.buildAST() || this.grammar.buildTemplate() || this.returnScope != null && this.returnScope.attributes.size() > 1;
   }

   public boolean getHasSingleReturnValue() {
      return !this.referencedPredefinedRuleAttributes && !this.grammar.buildAST() && !this.grammar.buildTemplate() && this.returnScope != null && this.returnScope.attributes.size() == 1;
   }

   public boolean getHasReturnValue() {
      return this.referencedPredefinedRuleAttributes || this.grammar.buildAST() || this.grammar.buildTemplate() || this.returnScope != null && this.returnScope.attributes.size() > 0;
   }

   public String getSingleValueReturnType() {
      return this.returnScope != null && this.returnScope.attributes.size() == 1 ? ((Attribute)this.returnScope.attributes.values().iterator().next()).type : null;
   }

   public String getSingleValueReturnName() {
      return this.returnScope != null && this.returnScope.attributes.size() == 1 ? ((Attribute)this.returnScope.attributes.values().iterator().next()).name : null;
   }

   public void defineNamedAction(GrammarAST ampersandAST, GrammarAST nameAST, GrammarAST actionAST) {
      String actionName = nameAST.getText();
      GrammarAST a = (GrammarAST)this.actions.get(actionName);
      if (a != null) {
         ErrorManager.grammarError(144, this.grammar, nameAST.getToken(), nameAST.getText());
      } else {
         this.actions.put(actionName, actionAST);
      }

   }

   public void trackInlineAction(GrammarAST actionAST) {
      this.inlineActions.add(actionAST);
   }

   public Map getActions() {
      return this.actions;
   }

   public void setActions(Map actions) {
      this.actions = actions;
   }

   public String setOption(String key, Object value, Token optionsStartToken) {
      if (!legalOptions.contains(key)) {
         ErrorManager.grammarError(133, this.grammar, optionsStartToken, key);
         return null;
      } else {
         if (this.options == null) {
            this.options = new HashMap();
         }

         if (key.equals("memoize") && value.toString().equals("true")) {
            this.grammar.composite.getRootGrammar().atLeastOneRuleMemoizes = true;
         }

         if (key.equals("backtrack") && value.toString().equals("true")) {
            this.grammar.composite.getRootGrammar().atLeastOneBacktrackOption = true;
         }

         if (key.equals("k")) {
            ++this.grammar.numberOfManualLookaheadOptions;
         }

         this.options.put(key, value);
         return key;
      }
   }

   public void setOptions(Map options, Token optionsStartToken) {
      if (options == null) {
         this.options = null;
      } else {
         Set keys = options.keySet();
         Iterator it = keys.iterator();

         while(it.hasNext()) {
            String optionName = (String)it.next();
            Object optionValue = options.get(optionName);
            String stored = this.setOption(optionName, optionValue, optionsStartToken);
            if (stored == null) {
               it.remove();
            }
         }

      }
   }

   public String toString() {
      return "[" + this.grammar.name + "." + this.name + ",index=" + this.index + ",line=" + this.tree.getToken().getLine() + "]";
   }
}
