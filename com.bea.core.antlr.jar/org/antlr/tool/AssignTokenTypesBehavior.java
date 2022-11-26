package org.antlr.tool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.antlr.grammar.v3.AssignTokenTypesWalker;
import org.antlr.misc.Utils;
import org.antlr.runtime.tree.TreeNodeStream;

public class AssignTokenTypesBehavior extends AssignTokenTypesWalker {
   protected static final Integer UNASSIGNED = Utils.integer(-1);
   protected static final Integer UNASSIGNED_IN_PARSER_RULE = Utils.integer(-2);
   protected Map stringLiterals = new TreeMap();
   protected Map tokens = new TreeMap();
   protected Map aliases = new TreeMap();
   protected Map aliasesReverseIndex = new HashMap();
   protected Set tokenRuleDefs = new HashSet();

   public AssignTokenTypesBehavior() {
      super((TreeNodeStream)null);
   }

   protected void init(Grammar g) {
      this.grammar = g;
      this.currentRuleName = null;
      if (stringAlias == null) {
         this.initASTPatterns();
      }

   }

   protected void trackString(GrammarAST t) {
      if (this.currentRuleName == null && this.grammar.type == 1) {
         ErrorManager.grammarError(108, this.grammar, t.token, t.getText());
      } else {
         if (this.grammar.getGrammarIsRoot() && this.grammar.type == 2 && this.grammar.getTokenType(t.getText()) == -7) {
            ErrorManager.grammarError(107, this.grammar, t.token, t.getText());
         }

         if (this.grammar.type != 1) {
            if ((this.currentRuleName == null || Character.isLowerCase(this.currentRuleName.charAt(0))) && this.grammar.getTokenType(t.getText()) == -7) {
               this.stringLiterals.put(t.getText(), UNASSIGNED_IN_PARSER_RULE);
            }

         }
      }
   }

   protected void trackToken(GrammarAST t) {
      if (this.grammar.getTokenType(t.getText()) == -7 && this.tokens.get(t.getText()) == null) {
         this.tokens.put(t.getText(), UNASSIGNED);
      }

   }

   protected void trackTokenRule(GrammarAST t, GrammarAST modifier, GrammarAST block) {
      if (this.grammar.type == 1 || this.grammar.type == 4) {
         if (!Character.isUpperCase(t.getText().charAt(0))) {
            return;
         }

         if (t.getText().equals("Tokens")) {
            return;
         }

         this.grammar.composite.lexerRules.add(t.getText());
         int existing = this.grammar.getTokenType(t.getText());
         if (existing == -7) {
            this.tokens.put(t.getText(), UNASSIGNED);
         }

         if (block.hasSameTreeStructure(charAlias) || block.hasSameTreeStructure(stringAlias) || block.hasSameTreeStructure(charAlias2) || block.hasSameTreeStructure(stringAlias2)) {
            this.tokenRuleDefs.add(t.getText());
            if (this.grammar.type == 4 || this.grammar.type == 1) {
               this.alias(t, (GrammarAST)block.getChild(0).getChild(0));
            }
         }
      }

   }

   protected void alias(GrammarAST t, GrammarAST s) {
      String tokenID = t.getText();
      String literal = s.getText();
      String prevAliasLiteralID = (String)this.aliasesReverseIndex.get(literal);
      if (prevAliasLiteralID != null) {
         if (!tokenID.equals(prevAliasLiteralID)) {
            if (!this.tokenRuleDefs.contains(tokenID) || !this.tokenRuleDefs.contains(prevAliasLiteralID)) {
               ErrorManager.grammarError(158, this.grammar, t.token, tokenID + "=" + literal, prevAliasLiteralID);
            }

         }
      } else {
         int existingLiteralType = this.grammar.getTokenType(literal);
         if (existingLiteralType != -7) {
            this.tokens.put(tokenID, existingLiteralType);
         }

         String prevAliasTokenID = (String)this.aliases.get(tokenID);
         if (prevAliasTokenID != null) {
            ErrorManager.grammarError(159, this.grammar, t.token, tokenID + "=" + literal, prevAliasTokenID);
         } else {
            this.aliases.put(tokenID, literal);
            this.aliasesReverseIndex.put(literal, tokenID);
         }
      }
   }

   public void defineTokens(Grammar root) {
      this.assignTokenIDTypes(root);
      this.aliasTokenIDsAndLiterals(root);
      this.assignStringTypes(root);
      this.defineTokenNamesAndLiteralsInGrammar(root);
   }

   protected void assignStringTypes(Grammar root) {
      Iterator i$ = this.stringLiterals.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         String lit = (String)entry.getKey();
         Integer oldTypeI = (Integer)entry.getValue();
         int oldType = oldTypeI;
         if (oldType < 4) {
            Integer typeI = Utils.integer(root.getNewTokenType());
            this.stringLiterals.put(lit, typeI);
            root.defineLexerRuleForStringLiteral(lit, typeI);
         }
      }

   }

   protected void aliasTokenIDsAndLiterals(Grammar root) {
      if (root.type != 1) {
         Iterator i$ = this.aliases.entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            String tokenID = (String)entry.getKey();
            String literal = (String)entry.getValue();
            if (literal.charAt(0) == '\'' && this.stringLiterals.get(literal) != null) {
               this.stringLiterals.put(literal, this.tokens.get(tokenID));
               Integer typeI = (Integer)this.tokens.get(tokenID);
               if (!this.tokenRuleDefs.contains(tokenID)) {
                  root.defineLexerRuleForAliasedStringLiteral(tokenID, literal, typeI);
               }
            }
         }

      }
   }

   protected void assignTokenIDTypes(Grammar root) {
      Iterator i$ = this.tokens.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         String tokenID = (String)entry.getKey();
         if (entry.getValue() == UNASSIGNED) {
            this.tokens.put(tokenID, Utils.integer(root.getNewTokenType()));
         }
      }

   }

   protected void defineTokenNamesAndLiteralsInGrammar(Grammar root) {
      Iterator i$ = this.tokens.entrySet().iterator();

      Map.Entry entry;
      while(i$.hasNext()) {
         entry = (Map.Entry)i$.next();
         int ttype = (Integer)entry.getValue();
         root.defineToken((String)entry.getKey(), ttype);
      }

      i$ = this.stringLiterals.entrySet().iterator();

      while(i$.hasNext()) {
         entry = (Map.Entry)i$.next();
         String lit = (String)entry.getKey();
         int ttype = (Integer)entry.getValue();
         root.defineToken(lit, ttype);
      }

   }
}
