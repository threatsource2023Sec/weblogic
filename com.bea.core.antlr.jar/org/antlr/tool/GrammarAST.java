package org.antlr.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.analysis.DFA;
import org.antlr.analysis.NFAState;
import org.antlr.misc.IntSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;
import org.stringtemplate.v4.ST;

public class GrammarAST extends CommonTree {
   static int count = 0;
   public int ID;
   private String textOverride;
   public String enclosingRuleName;
   public DFA lookaheadDFA;
   public NFAState NFAStartState;
   public NFAState NFATreeDownState;
   public NFAState followingNFAState;
   protected IntSet setValue;
   protected Map blockOptions;
   public Set rewriteRefsShallow;
   public Set rewriteRefsDeep;
   public Map terminalOptions;
   public int outerAltNum;
   public ST code;
   private static final GrammarAST DescendantDownNode = new GrammarAST(2, "DOWN");
   private static final GrammarAST DescendantUpNode = new GrammarAST(3, "UP");

   public Map getBlockOptions() {
      return this.blockOptions;
   }

   public void setBlockOptions(Map blockOptions) {
      this.blockOptions = blockOptions;
   }

   public GrammarAST() {
      this.ID = ++count;
      this.lookaheadDFA = null;
      this.NFAStartState = null;
      this.NFATreeDownState = null;
      this.followingNFAState = null;
      this.setValue = null;
   }

   public GrammarAST(int t, String txt) {
      this.ID = ++count;
      this.lookaheadDFA = null;
      this.NFAStartState = null;
      this.NFATreeDownState = null;
      this.followingNFAState = null;
      this.setValue = null;
      this.initialize(t, txt);
   }

   public GrammarAST(Token token) {
      this.ID = ++count;
      this.lookaheadDFA = null;
      this.NFAStartState = null;
      this.NFATreeDownState = null;
      this.followingNFAState = null;
      this.setValue = null;
      this.initialize(token);
   }

   public void initialize(int i, String s) {
      this.token = new CommonToken(i, s);
      this.token.setTokenIndex(-1);
   }

   public void initialize(Tree ast) {
      GrammarAST t = (GrammarAST)ast;
      this.startIndex = t.startIndex;
      this.stopIndex = t.stopIndex;
      this.token = t.token;
      this.enclosingRuleName = t.enclosingRuleName;
      this.setValue = t.setValue;
      this.blockOptions = t.blockOptions;
      this.outerAltNum = t.outerAltNum;
   }

   public void initialize(Token token) {
      this.token = token;
      if (token != null) {
         this.startIndex = token.getTokenIndex();
         this.stopIndex = this.startIndex;
      }

   }

   public DFA getLookaheadDFA() {
      return this.lookaheadDFA;
   }

   public void setLookaheadDFA(DFA lookaheadDFA) {
      this.lookaheadDFA = lookaheadDFA;
   }

   public NFAState getNFAStartState() {
      return this.NFAStartState;
   }

   public void setNFAStartState(NFAState nfaStartState) {
      this.NFAStartState = nfaStartState;
   }

   public String setBlockOption(Grammar grammar, String key, Object value) {
      if (this.blockOptions == null) {
         this.blockOptions = new HashMap();
      }

      return this.setOption(this.blockOptions, Grammar.legalBlockOptions, grammar, key, value);
   }

   public String setTerminalOption(Grammar grammar, String key, Object value) {
      if (this.terminalOptions == null) {
         this.terminalOptions = new HashMap();
      }

      return this.setOption(this.terminalOptions, Grammar.legalTokenOptions, grammar, key, value);
   }

   public String setOption(Map options, Set legalOptions, Grammar grammar, String key, Object value) {
      if (!legalOptions.contains(key)) {
         ErrorManager.grammarError(133, grammar, this.token, key);
         return null;
      } else {
         if (value instanceof String) {
            String vs = (String)value;
            if (vs.charAt(0) == '"') {
               value = vs.substring(1, vs.length() - 1);
            }
         }

         if (key.equals("k")) {
            ++grammar.numberOfManualLookaheadOptions;
         }

         if (key.equals("backtrack") && value.toString().equals("true")) {
            grammar.composite.getRootGrammar().atLeastOneBacktrackOption = true;
         }

         options.put(key, value);
         return key;
      }
   }

   public Object getBlockOption(String key) {
      Object value = null;
      if (this.blockOptions != null) {
         value = this.blockOptions.get(key);
      }

      return value;
   }

   public void setOptions(Grammar grammar, Map options) {
      if (options == null) {
         this.blockOptions = null;
      } else {
         String[] keys = (String[])options.keySet().toArray(new String[options.size()]);
         String[] arr$ = keys;
         int len$ = keys.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String optionName = arr$[i$];
            String stored = this.setBlockOption(grammar, optionName, options.get(optionName));
            if (stored == null) {
               options.remove(optionName);
            }
         }

      }
   }

   public String getText() {
      if (this.textOverride != null) {
         return this.textOverride;
      } else {
         return this.token != null ? this.token.getText() : "";
      }
   }

   public void setType(int type) {
      this.token.setType(type);
   }

   public void setText(String text) {
      this.textOverride = text;
   }

   public int getType() {
      return this.token != null ? this.token.getType() : -1;
   }

   public int getLine() {
      int line = 0;
      if (this.token != null) {
         line = this.token.getLine();
      }

      if (line == 0) {
         Tree child = this.getChild(0);
         if (child != null) {
            line = child.getLine();
         }
      }

      return line;
   }

   public int getCharPositionInLine() {
      int col = 0;
      if (this.token != null) {
         col = this.token.getCharPositionInLine();
      }

      if (col == 0) {
         Tree child = this.getChild(0);
         if (child != null) {
            col = child.getCharPositionInLine();
         }
      }

      return col;
   }

   public void setLine(int line) {
      this.token.setLine(line);
   }

   public void setCharPositionInLine(int value) {
      this.token.setCharPositionInLine(value);
   }

   public IntSet getSetValue() {
      return this.setValue;
   }

   public void setSetValue(IntSet setValue) {
      this.setValue = setValue;
   }

   public GrammarAST getLastChild() {
      return this.getChildCount() == 0 ? null : (GrammarAST)this.getChild(this.getChildCount() - 1);
   }

   public GrammarAST getNextSibling() {
      return (GrammarAST)this.getParent().getChild(this.getChildIndex() + 1);
   }

   public GrammarAST getLastSibling() {
      Tree parent = this.getParent();
      return parent == null ? null : (GrammarAST)parent.getChild(parent.getChildCount() - 1);
   }

   public GrammarAST[] getChildrenAsArray() {
      List children = this.getChildren();
      return children == null ? new GrammarAST[0] : (GrammarAST[])children.toArray(new GrammarAST[children.size()]);
   }

   public static List descendants(Tree root) {
      return descendants(root, false);
   }

   public static List descendants(Tree root, boolean insertDownUpNodes) {
      List result = new ArrayList();
      int count = root.getChildCount();
      int i;
      Tree child;
      Iterator i$;
      Tree subchild;
      if (insertDownUpNodes) {
         result.add(root);
         result.add(DescendantDownNode);

         for(i = 0; i < count; ++i) {
            child = root.getChild(i);
            i$ = descendants(child, true).iterator();

            while(i$.hasNext()) {
               subchild = (Tree)i$.next();
               result.add(subchild);
            }
         }

         result.add(DescendantUpNode);
      } else {
         result.add(root);

         for(i = 0; i < count; ++i) {
            child = root.getChild(i);
            i$ = descendants(child, false).iterator();

            while(i$.hasNext()) {
               subchild = (Tree)i$.next();
               result.add(subchild);
            }
         }
      }

      return result;
   }

   public GrammarAST findFirstType(int ttype) {
      if (this.getType() == ttype) {
         return this;
      } else {
         List descendants = descendants(this);
         Iterator i$ = descendants.iterator();

         Tree child;
         do {
            if (!i$.hasNext()) {
               return null;
            }

            child = (Tree)i$.next();
         } while(child.getType() != ttype);

         return (GrammarAST)child;
      }
   }

   public List findAllType(int ttype) {
      List nodes = new ArrayList();
      this._findAllType(ttype, nodes);
      return nodes;
   }

   public void _findAllType(int ttype, List nodes) {
      if (this.getType() == ttype) {
         nodes.add(this);
      }

      for(int i = 0; i < this.getChildCount(); ++i) {
         GrammarAST child = (GrammarAST)this.getChild(i);
         child._findAllType(ttype, nodes);
      }

   }

   public boolean equals(Object ast) {
      if (this == ast) {
         return true;
      } else if (!(ast instanceof GrammarAST)) {
         return this.getType() == ((Tree)ast).getType();
      } else {
         GrammarAST t = (GrammarAST)ast;
         return this.token.getLine() == t.getLine() && this.token.getCharPositionInLine() == t.getCharPositionInLine();
      }
   }

   public int hashCode() {
      return this.token == null ? 0 : this.token.hashCode();
   }

   public boolean hasSameTreeStructure(Tree other) {
      if (this.getType() != other.getType()) {
         return false;
      } else {
         Iterator thisDescendants = descendants(this, true).iterator();
         Iterator otherDescendants = descendants(other, true).iterator();

         do {
            if (!thisDescendants.hasNext()) {
               return !otherDescendants.hasNext();
            }

            if (!otherDescendants.hasNext()) {
               return false;
            }
         } while(((Tree)thisDescendants.next()).getType() == ((Tree)otherDescendants.next()).getType());

         return false;
      }
   }

   public static GrammarAST dup(Tree t) {
      if (t == null) {
         return null;
      } else {
         GrammarAST dup_t = new GrammarAST();
         dup_t.initialize(t);
         return dup_t;
      }
   }

   public Tree dupNode() {
      return dup(this);
   }

   public static GrammarAST dupTreeNoActions(GrammarAST t, GrammarAST parent) {
      if (t == null) {
         return null;
      } else {
         GrammarAST result = (GrammarAST)t.dupNode();
         Iterator i$ = getChildrenForDupTree(t).iterator();

         while(i$.hasNext()) {
            GrammarAST subchild = (GrammarAST)i$.next();
            result.addChild(dupTreeNoActions(subchild, result));
         }

         return result;
      }
   }

   private static List getChildrenForDupTree(GrammarAST t) {
      List result = new ArrayList();

      for(int i = 0; i < t.getChildCount(); ++i) {
         GrammarAST child = (GrammarAST)t.getChild(i);
         int ttype = child.getType();
         if (ttype != 76 && ttype != 75 && ttype != 4) {
            if (ttype != 15 && ttype != 77) {
               result.add(child);
            } else {
               Iterator i$ = getChildrenForDupTree(child).iterator();

               while(i$.hasNext()) {
                  GrammarAST subchild = (GrammarAST)i$.next();
                  result.add(subchild);
               }
            }
         }
      }

      if (result.size() == 1 && ((GrammarAST)result.get(0)).getType() == 32 && t.getType() == 8) {
         result.add(0, new GrammarAST(35, "epsilon"));
      }

      return result;
   }

   public static GrammarAST dupTree(GrammarAST t) {
      if (t == null) {
         return null;
      } else {
         GrammarAST root = dup(t);

         for(int i = 0; i < t.getChildCount(); ++i) {
            GrammarAST child = (GrammarAST)t.getChild(i);
            root.addChild(dupTree(child));
         }

         return root;
      }
   }

   public void setTreeEnclosingRuleNameDeeply(String rname) {
      this.enclosingRuleName = rname;
      if (this.getChildCount() != 0) {
         Iterator i$ = this.getChildren().iterator();

         while(i$.hasNext()) {
            Object child = i$.next();
            if (child instanceof GrammarAST) {
               GrammarAST grammarAST = (GrammarAST)child;
               grammarAST.setTreeEnclosingRuleNameDeeply(rname);
            }
         }

      }
   }

   public String toStringList() {
      String result = this.toStringTree();
      if (this.getNextSibling() != null) {
         result = result + ' ' + this.getNextSibling().toStringList();
      }

      return result;
   }

   public void setTokenBoundaries(Token startToken, Token stopToken) {
      if (startToken != null) {
         this.startIndex = startToken.getTokenIndex();
      }

      if (stopToken != null) {
         this.stopIndex = stopToken.getTokenIndex();
      }

   }

   public GrammarAST getBlockALT(int i) {
      if (this.getType() != 16) {
         return null;
      } else {
         int alts = 0;

         for(int j = 0; j < this.getChildCount(); ++j) {
            if (this.getChild(j).getType() == 8) {
               ++alts;
            }

            if (alts == i) {
               return (GrammarAST)this.getChild(j);
            }
         }

         return null;
      }
   }
}
