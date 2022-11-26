package org.antlr.tool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.antlr.analysis.NFAState;
import org.antlr.grammar.v3.AssignTokenTypesWalker;
import org.antlr.misc.Utils;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

public class CompositeGrammar {
   public static final int MIN_RULE_INDEX = 1;
   public CompositeGrammarTree delegateGrammarTreeRoot;
   protected Set refClosureBusy;
   public int stateCounter;
   protected Vector numberToStateList;
   protected int maxTokenType;
   public Map tokenIDToTypeMap;
   public Map stringLiteralToTypeMap;
   public Vector typeToStringLiteralList;
   public Vector typeToTokenList;
   protected Set lexerRules;
   protected int ruleIndex;
   protected Vector ruleIndexToRuleList;
   public boolean watchNFAConversion;

   protected void initTokenSymbolTables() {
      this.typeToTokenList.setSize(10);
      this.typeToTokenList.set(0, "<INVALID>");
      this.typeToTokenList.set(5, "<EOT>");
      this.typeToTokenList.set(3, "<SEMPRED>");
      this.typeToTokenList.set(4, "<SET>");
      this.typeToTokenList.set(2, "<EPSILON>");
      this.typeToTokenList.set(6, "EOF");
      this.typeToTokenList.set(7, "<EOR>");
      this.typeToTokenList.set(8, "DOWN");
      this.typeToTokenList.set(9, "UP");
      this.tokenIDToTypeMap.put("<INVALID>", Utils.integer(-7));
      this.tokenIDToTypeMap.put("<EOT>", Utils.integer(-2));
      this.tokenIDToTypeMap.put("<SEMPRED>", Utils.integer(-4));
      this.tokenIDToTypeMap.put("<SET>", Utils.integer(-3));
      this.tokenIDToTypeMap.put("<EPSILON>", Utils.integer(-5));
      this.tokenIDToTypeMap.put("EOF", Utils.integer(-1));
      this.tokenIDToTypeMap.put("<EOR>", Utils.integer(1));
      this.tokenIDToTypeMap.put("DOWN", Utils.integer(2));
      this.tokenIDToTypeMap.put("UP", Utils.integer(3));
   }

   public CompositeGrammar() {
      this.refClosureBusy = new HashSet();
      this.stateCounter = 0;
      this.numberToStateList = new Vector(1000);
      this.maxTokenType = 3;
      this.tokenIDToTypeMap = new LinkedHashMap();
      this.stringLiteralToTypeMap = new LinkedHashMap();
      this.typeToStringLiteralList = new Vector();
      this.typeToTokenList = new Vector();
      this.lexerRules = new HashSet();
      this.ruleIndex = 1;
      this.ruleIndexToRuleList = new Vector();
      this.watchNFAConversion = false;
      this.initTokenSymbolTables();
   }

   public CompositeGrammar(Grammar g) {
      this();
      this.setDelegationRoot(g);
   }

   public void setDelegationRoot(Grammar root) {
      this.delegateGrammarTreeRoot = new CompositeGrammarTree(root);
      root.compositeTreeNode = this.delegateGrammarTreeRoot;
   }

   public Rule getRule(String ruleName) {
      return this.delegateGrammarTreeRoot.getRule(ruleName);
   }

   public Object getOption(String key) {
      return this.delegateGrammarTreeRoot.getOption(key);
   }

   public void addGrammar(Grammar delegator, Grammar delegate) {
      if (delegator.compositeTreeNode == null) {
         delegator.compositeTreeNode = new CompositeGrammarTree(delegator);
      }

      delegator.compositeTreeNode.addChild(new CompositeGrammarTree(delegate));
      delegate.composite = this;
   }

   public Grammar getDelegator(Grammar g) {
      CompositeGrammarTree me = this.delegateGrammarTreeRoot.findNode(g);
      if (me == null) {
         return null;
      } else {
         return me.parent != null ? me.parent.grammar : null;
      }
   }

   public List getDelegates(Grammar g) {
      CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(g);
      if (t == null) {
         return null;
      } else {
         List grammars = t.getPostOrderedGrammarList();
         grammars.remove(grammars.size() - 1);
         return grammars;
      }
   }

   public List getDirectDelegates(Grammar g) {
      CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(g);
      List children = t.children;
      if (children == null) {
         return null;
      } else {
         List grammars = new ArrayList();

         for(int i = 0; children != null && i < children.size(); ++i) {
            CompositeGrammarTree child = (CompositeGrammarTree)children.get(i);
            grammars.add(child.grammar);
         }

         return grammars;
      }
   }

   public List getIndirectDelegates(Grammar g) {
      List direct = this.getDirectDelegates(g);
      List delegates = this.getDelegates(g);
      if (direct != null) {
         delegates.removeAll(direct);
      }

      return delegates;
   }

   public List getDelegators(Grammar g) {
      if (g == this.delegateGrammarTreeRoot.grammar) {
         return null;
      } else {
         List grammars = new ArrayList();
         CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(g);

         for(CompositeGrammarTree p = t.parent; p != null; p = p.parent) {
            grammars.add(0, p.grammar);
         }

         return grammars;
      }
   }

   public Set getDelegatedRules(Grammar g) {
      if (g != this.delegateGrammarTreeRoot.grammar) {
         return null;
      } else {
         Set rules = this.getAllImportedRules(g);
         Iterator it = rules.iterator();

         while(true) {
            Rule r;
            Rule localRule;
            do {
               if (!it.hasNext()) {
                  return rules;
               }

               r = (Rule)it.next();
               localRule = g.getLocallyDefinedRule(r.name);
            } while(localRule == null && !r.isSynPred);

            it.remove();
         }
      }
   }

   public Set getAllImportedRules(Grammar g) {
      Set ruleNames = new HashSet();
      Set rules = new HashSet();
      CompositeGrammarTree subtreeRoot = this.delegateGrammarTreeRoot.findNode(g);
      List grammars = subtreeRoot.getPreOrderedGrammarList();

      for(int i = 0; i < grammars.size(); ++i) {
         Grammar delegate = (Grammar)grammars.get(i);
         Iterator i$ = delegate.getRules().iterator();

         while(i$.hasNext()) {
            Rule r = (Rule)i$.next();
            if (!ruleNames.contains(r.name)) {
               ruleNames.add(r.name);
               rules.add(r);
            }
         }
      }

      return rules;
   }

   public Grammar getRootGrammar() {
      return this.delegateGrammarTreeRoot == null ? null : this.delegateGrammarTreeRoot.grammar;
   }

   public Grammar getGrammar(String grammarName) {
      CompositeGrammarTree t = this.delegateGrammarTreeRoot.findNode(grammarName);
      return t != null ? t.grammar : null;
   }

   public int getNewNFAStateNumber() {
      return this.stateCounter++;
   }

   public void addState(NFAState state) {
      this.numberToStateList.setSize(state.stateNumber + 1);
      this.numberToStateList.set(state.stateNumber, state);
   }

   public NFAState getState(int s) {
      return (NFAState)this.numberToStateList.get(s);
   }

   public void assignTokenTypes() throws RecognitionException {
      AssignTokenTypesWalker ttypesWalker = new AssignTokenTypesBehavior();
      List grammars = this.delegateGrammarTreeRoot.getPostOrderedGrammarList();

      for(int i = 0; grammars != null && i < grammars.size(); ++i) {
         Grammar g = (Grammar)grammars.get(i);
         ttypesWalker.setTreeNodeStream(new CommonTreeNodeStream(g.getGrammarTree()));

         try {
            ttypesWalker.grammar_(g);
         } catch (RecognitionException var6) {
            ErrorManager.error(15, (Throwable)var6);
         }
      }

      ttypesWalker.defineTokens(this.delegateGrammarTreeRoot.grammar);
   }

   public void translateLeftRecursiveRules() {
      List grammars = this.delegateGrammarTreeRoot.getPostOrderedGrammarList();

      for(int i = 0; grammars != null && i < grammars.size(); ++i) {
         Grammar g = (Grammar)grammars.get(i);
         if (g.type == 2 || g.type == 4) {
            Iterator i$ = g.grammarTree.findAllType(79).iterator();

            while(i$.hasNext()) {
               GrammarAST r = (GrammarAST)i$.next();
               if (!Character.isUpperCase(r.getChild(0).getText().charAt(0)) && LeftRecursiveRuleAnalyzer.hasImmediateRecursiveRuleRefs(r, r.enclosingRuleName)) {
                  g.translateLeftRecursiveRule(r);
               }
            }
         }
      }

   }

   public void defineGrammarSymbols() {
      this.delegateGrammarTreeRoot.trimLexerImportsIntoCombined();
      List grammars = this.delegateGrammarTreeRoot.getPostOrderedGrammarList();

      int i;
      Grammar g;
      for(i = 0; grammars != null && i < grammars.size(); ++i) {
         g = (Grammar)grammars.get(i);
         g.defineGrammarSymbols();
      }

      for(i = 0; grammars != null && i < grammars.size(); ++i) {
         g = (Grammar)grammars.get(i);
         g.checkNameSpaceAndActions();
      }

      this.minimizeRuleSet();
   }

   public void createNFAs() {
      if (!ErrorManager.doNotAttemptAnalysis()) {
         List grammars = this.delegateGrammarTreeRoot.getPostOrderedGrammarList();

         int i;
         Grammar g;
         for(i = 0; grammars != null && i < grammars.size(); ++i) {
            g = (Grammar)grammars.get(i);
            g.createRuleStartAndStopNFAStates();
         }

         for(i = 0; grammars != null && i < grammars.size(); ++i) {
            g = (Grammar)grammars.get(i);
            g.buildNFA();
         }

      }
   }

   public void minimizeRuleSet() {
      Set ruleDefs = new HashSet();
      this._minimizeRuleSet(ruleDefs, this.delegateGrammarTreeRoot);
   }

   public void _minimizeRuleSet(Set ruleDefs, CompositeGrammarTree p) {
      Set localRuleDefs = new HashSet();
      Set overrides = new HashSet();
      Iterator i$ = p.grammar.getRules().iterator();

      while(i$.hasNext()) {
         Rule r = (Rule)i$.next();
         if (!ruleDefs.contains(r.name)) {
            localRuleDefs.add(r.name);
         } else if (!r.name.equals("Tokens")) {
            overrides.add(r.name);
         }
      }

      p.grammar.overriddenRules = overrides;
      ruleDefs.addAll(localRuleDefs);
      if (p.children != null) {
         i$ = p.children.iterator();

         while(i$.hasNext()) {
            CompositeGrammarTree delegate = (CompositeGrammarTree)i$.next();
            this._minimizeRuleSet(ruleDefs, delegate);
         }
      }

   }
}
