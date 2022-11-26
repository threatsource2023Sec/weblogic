package org.antlr.tool;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.runtime.Token;

public class NameSpaceChecker {
   protected Grammar grammar;

   public NameSpaceChecker(Grammar grammar) {
      this.grammar = grammar;
   }

   public void checkConflicts() {
      for(int i = 1; i < this.grammar.composite.ruleIndexToRuleList.size(); ++i) {
         Rule r = (Rule)this.grammar.composite.ruleIndexToRuleList.elementAt(i);
         if (r != null) {
            if (r.labelNameSpace != null) {
               Iterator i$ = r.labelNameSpace.values().iterator();

               while(i$.hasNext()) {
                  Grammar.LabelElementPair pair = (Grammar.LabelElementPair)i$.next();
                  this.checkForLabelConflict(r, pair.label);
               }
            }

            if (r.ruleScope != null) {
               List attributes = r.ruleScope.getAttributes();

               for(int j = 0; j < attributes.size(); ++j) {
                  Attribute attribute = (Attribute)attributes.get(j);
                  this.checkForRuleScopeAttributeConflict(r, attribute);
               }
            }

            this.checkForRuleDefinitionProblems(r);
            this.checkForRuleArgumentAndReturnValueConflicts(r);
         }
      }

      Iterator i$ = this.grammar.getGlobalScopes().values().iterator();

      while(i$.hasNext()) {
         AttributeScope scope = (AttributeScope)i$.next();
         this.checkForGlobalScopeTokenConflict(scope);
      }

      this.lookForReferencesToUndefinedSymbols();
   }

   protected void checkForRuleArgumentAndReturnValueConflicts(Rule r) {
      if (r.returnScope != null) {
         Set conflictingKeys = r.returnScope.intersection(r.parameterScope);
         if (conflictingKeys != null) {
            Iterator i$ = conflictingKeys.iterator();

            while(i$.hasNext()) {
               String key = (String)i$.next();
               ErrorManager.grammarError(126, this.grammar, r.tree.getToken(), key, r.name);
            }
         }
      }

   }

   protected void checkForRuleDefinitionProblems(Rule r) {
      String ruleName = r.name;
      Token ruleToken = r.tree.getToken();
      int msgID = 0;
      if ((this.grammar.type == 2 || this.grammar.type == 3) && Character.isUpperCase(ruleName.charAt(0))) {
         msgID = 102;
      } else if (this.grammar.type == 1 && Character.isLowerCase(ruleName.charAt(0)) && !r.isSynPred) {
         msgID = 103;
      } else if (this.grammar.getGlobalScope(ruleName) != null) {
         msgID = 118;
      }

      if (msgID != 0) {
         ErrorManager.grammarError(msgID, this.grammar, ruleToken, ruleName);
      }

   }

   protected void lookForReferencesToUndefinedSymbols() {
      Iterator i$ = this.grammar.ruleRefs.iterator();

      GrammarAST scopeAST;
      Rule rule;
      while(i$.hasNext()) {
         scopeAST = (GrammarAST)i$.next();
         Token tok = scopeAST.token;
         String ruleName = tok.getText();
         Rule localRule = this.grammar.getLocallyDefinedRule(ruleName);
         rule = this.grammar.getRule(ruleName);
         if (localRule == null && rule != null) {
            this.grammar.delegatedRuleReferences.add(rule);
            rule.imported = true;
         }

         if (rule == null && this.grammar.getTokenType(ruleName) != -1) {
            ErrorManager.grammarError(106, this.grammar, tok, ruleName);
         }
      }

      if (this.grammar.type == 4) {
         i$ = this.grammar.tokenIDRefs.iterator();

         while(i$.hasNext()) {
            Token tok = (Token)i$.next();
            String tokenID = tok.getText();
            if (!this.grammar.composite.lexerRules.contains(tokenID) && this.grammar.getTokenType(tokenID) != -1) {
               ErrorManager.grammarWarning(105, this.grammar, tok, tokenID);
            }
         }
      }

      i$ = this.grammar.scopedRuleRefs.iterator();

      while(i$.hasNext()) {
         scopeAST = (GrammarAST)i$.next();
         Grammar scopeG = this.grammar.composite.getGrammar(scopeAST.getText());
         GrammarAST refAST = (GrammarAST)scopeAST.getChild(1);
         String ruleName = refAST.getText();
         if (scopeG == null) {
            ErrorManager.grammarError(156, this.grammar, scopeAST.getToken(), scopeAST.getText(), ruleName);
         } else {
            rule = this.grammar.getRule(scopeG.name, ruleName);
            if (rule == null) {
               ErrorManager.grammarError(157, this.grammar, scopeAST.getToken(), scopeAST.getText(), ruleName);
            }
         }
      }

   }

   protected void checkForGlobalScopeTokenConflict(AttributeScope scope) {
      if (this.grammar.getTokenType(scope.getName()) != -7) {
         ErrorManager.grammarError(118, this.grammar, (Token)null, scope.getName());
      }

   }

   public void checkForRuleScopeAttributeConflict(Rule r, Attribute attribute) {
      int msgID = 0;
      Object arg2 = null;
      String attrName = attribute.name;
      if (r.name.equals(attrName)) {
         msgID = 123;
         arg2 = r.name;
      } else if (r.returnScope != null && r.returnScope.getAttribute(attrName) != null || r.parameterScope != null && r.parameterScope.getAttribute(attrName) != null) {
         msgID = 124;
         arg2 = r.name;
      }

      if (msgID != 0) {
         ErrorManager.grammarError(msgID, this.grammar, r.tree.getToken(), attrName, arg2);
      }

   }

   protected void checkForLabelConflict(Rule r, Token label) {
      int msgID = 0;
      Object arg2 = null;
      if (this.grammar.getGlobalScope(label.getText()) != null) {
         msgID = 118;
      } else if (this.grammar.getRule(label.getText()) != null) {
         msgID = 119;
      } else if (this.grammar.getTokenType(label.getText()) != -7) {
         msgID = 120;
      } else if (r.ruleScope != null && r.ruleScope.getAttribute(label.getText()) != null) {
         msgID = 121;
         arg2 = r.name;
      } else if (r.returnScope != null && r.returnScope.getAttribute(label.getText()) != null || r.parameterScope != null && r.parameterScope.getAttribute(label.getText()) != null) {
         msgID = 122;
         arg2 = r.name;
      }

      if (msgID != 0) {
         ErrorManager.grammarError(msgID, this.grammar, label, label.getText(), arg2);
      }

   }

   public boolean checkForLabelTypeMismatch(Rule r, Token label, int type) {
      Grammar.LabelElementPair prevLabelPair = (Grammar.LabelElementPair)r.labelNameSpace.get(label.getText());
      if (prevLabelPair != null && prevLabelPair.type != type) {
         String typeMismatchExpr = Grammar.LabelTypeToString[type] + "!=" + Grammar.LabelTypeToString[prevLabelPair.type];
         ErrorManager.grammarError(125, this.grammar, label, label.getText(), typeMismatchExpr);
         return true;
      } else {
         return false;
      }
   }
}
