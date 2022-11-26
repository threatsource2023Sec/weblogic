package org.antlr.tool;

import java.util.ArrayList;
import java.util.List;

public class CompositeGrammarTree {
   protected List children;
   public Grammar grammar;
   public CompositeGrammarTree parent;

   public CompositeGrammarTree(Grammar g) {
      this.grammar = g;
   }

   public void addChild(CompositeGrammarTree t) {
      if (t != null) {
         if (this.children == null) {
            this.children = new ArrayList();
         }

         this.children.add(t);
         t.parent = this;
      }
   }

   public Rule getRule(String ruleName) {
      Rule r = this.grammar.getLocallyDefinedRule(ruleName);

      for(int i = 0; r == null && this.children != null && i < this.children.size(); ++i) {
         CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
         r = child.getRule(ruleName);
      }

      return r;
   }

   public Object getOption(String key) {
      if (this.grammar.tool != null && key != null && key.equals("language") && this.grammar.tool.forcedLanguageOption != null) {
         return this.grammar.tool.forcedLanguageOption;
      } else {
         Object o = this.grammar.getLocallyDefinedOption(key);
         if (o != null) {
            return o;
         } else {
            return this.parent != null ? this.parent.getOption(key) : null;
         }
      }
   }

   public CompositeGrammarTree findNode(Grammar g) {
      if (g == null) {
         return null;
      } else if (this.grammar == g) {
         return this;
      } else {
         CompositeGrammarTree n = null;

         for(int i = 0; n == null && this.children != null && i < this.children.size(); ++i) {
            CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
            n = child.findNode(g);
         }

         return n;
      }
   }

   public CompositeGrammarTree findNode(String grammarName) {
      if (grammarName == null) {
         return null;
      } else if (grammarName.equals(this.grammar.name)) {
         return this;
      } else {
         CompositeGrammarTree n = null;

         for(int i = 0; n == null && this.children != null && i < this.children.size(); ++i) {
            CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
            n = child.findNode(grammarName);
         }

         return n;
      }
   }

   public List getPostOrderedGrammarList() {
      List grammars = new ArrayList();
      this._getPostOrderedGrammarList(grammars);
      return grammars;
   }

   protected void _getPostOrderedGrammarList(List grammars) {
      for(int i = 0; this.children != null && i < this.children.size(); ++i) {
         CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
         child._getPostOrderedGrammarList(grammars);
      }

      grammars.add(this.grammar);
   }

   public List getPreOrderedGrammarList() {
      List grammars = new ArrayList();
      this._getPreOrderedGrammarList(grammars);
      return grammars;
   }

   protected void _getPreOrderedGrammarList(List grammars) {
      grammars.add(this.grammar);

      for(int i = 0; this.children != null && i < this.children.size(); ++i) {
         CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
         child._getPreOrderedGrammarList(grammars);
      }

   }

   public void trimLexerImportsIntoCombined() {
      if (this.grammar.type == 1 && this.parent != null && this.parent.grammar.type == 4) {
         this.parent.children.remove(this);
      }

      for(int i = 0; this.children != null && i < this.children.size(); ++i) {
         CompositeGrammarTree child = (CompositeGrammarTree)this.children.get(i);
         child.trimLexerImportsIntoCombined();
      }

   }
}
