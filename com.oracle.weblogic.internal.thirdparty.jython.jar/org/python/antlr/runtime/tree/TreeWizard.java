package org.python.antlr.runtime.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.python.antlr.runtime.Token;

public class TreeWizard {
   protected TreeAdaptor adaptor;
   protected Map tokenNameToTypeMap;

   public TreeWizard(TreeAdaptor adaptor) {
      this.adaptor = adaptor;
   }

   public TreeWizard(TreeAdaptor adaptor, Map tokenNameToTypeMap) {
      this.adaptor = adaptor;
      this.tokenNameToTypeMap = tokenNameToTypeMap;
   }

   public TreeWizard(TreeAdaptor adaptor, String[] tokenNames) {
      this.adaptor = adaptor;
      this.tokenNameToTypeMap = this.computeTokenTypes(tokenNames);
   }

   public TreeWizard(String[] tokenNames) {
      this((TreeAdaptor)null, (String[])tokenNames);
   }

   public Map computeTokenTypes(String[] tokenNames) {
      Map m = new HashMap();
      if (tokenNames == null) {
         return m;
      } else {
         for(int ttype = 4; ttype < tokenNames.length; ++ttype) {
            String name = tokenNames[ttype];
            m.put(name, new Integer(ttype));
         }

         return m;
      }
   }

   public int getTokenType(String tokenName) {
      if (this.tokenNameToTypeMap == null) {
         return 0;
      } else {
         Integer ttypeI = (Integer)this.tokenNameToTypeMap.get(tokenName);
         return ttypeI != null ? ttypeI : 0;
      }
   }

   public Map index(Object t) {
      Map m = new HashMap();
      this._index(t, m);
      return m;
   }

   protected void _index(Object t, Map m) {
      if (t != null) {
         int ttype = this.adaptor.getType(t);
         List elements = (List)m.get(new Integer(ttype));
         if (elements == null) {
            elements = new ArrayList();
            m.put(new Integer(ttype), elements);
         }

         ((List)elements).add(t);
         int n = this.adaptor.getChildCount(t);

         for(int i = 0; i < n; ++i) {
            Object child = this.adaptor.getChild(t, i);
            this._index(child, m);
         }

      }
   }

   public List find(Object t, int ttype) {
      final List nodes = new ArrayList();
      this.visit(t, ttype, new Visitor() {
         public void visit(Object t) {
            nodes.add(t);
         }
      });
      return nodes;
   }

   public List find(Object t, String pattern) {
      final List subtrees = new ArrayList();
      TreePatternLexer tokenizer = new TreePatternLexer(pattern);
      TreePatternParser parser = new TreePatternParser(tokenizer, this, new TreePatternTreeAdaptor());
      final TreePattern tpattern = (TreePattern)parser.pattern();
      if (tpattern != null && !tpattern.isNil() && tpattern.getClass() != WildcardTreePattern.class) {
         int rootTokenType = tpattern.getType();
         this.visit(t, rootTokenType, new ContextVisitor() {
            public void visit(Object t, Object parent, int childIndex, Map labels) {
               if (TreeWizard.this._parse(t, tpattern, (Map)null)) {
                  subtrees.add(t);
               }

            }
         });
         return subtrees;
      } else {
         return null;
      }
   }

   public Object findFirst(Object t, int ttype) {
      return null;
   }

   public Object findFirst(Object t, String pattern) {
      return null;
   }

   public void visit(Object t, int ttype, ContextVisitor visitor) {
      this._visit(t, (Object)null, 0, ttype, visitor);
   }

   protected void _visit(Object t, Object parent, int childIndex, int ttype, ContextVisitor visitor) {
      if (t != null) {
         if (this.adaptor.getType(t) == ttype) {
            visitor.visit(t, parent, childIndex, (Map)null);
         }

         int n = this.adaptor.getChildCount(t);

         for(int i = 0; i < n; ++i) {
            Object child = this.adaptor.getChild(t, i);
            this._visit(child, t, i, ttype, visitor);
         }

      }
   }

   public void visit(Object t, String pattern, final ContextVisitor visitor) {
      TreePatternLexer tokenizer = new TreePatternLexer(pattern);
      TreePatternParser parser = new TreePatternParser(tokenizer, this, new TreePatternTreeAdaptor());
      final TreePattern tpattern = (TreePattern)parser.pattern();
      if (tpattern != null && !tpattern.isNil() && tpattern.getClass() != WildcardTreePattern.class) {
         final Map labels = new HashMap();
         int rootTokenType = tpattern.getType();
         this.visit(t, rootTokenType, new ContextVisitor() {
            public void visit(Object t, Object parent, int childIndex, Map unusedlabels) {
               labels.clear();
               if (TreeWizard.this._parse(t, tpattern, labels)) {
                  visitor.visit(t, parent, childIndex, labels);
               }

            }
         });
      }
   }

   public boolean parse(Object t, String pattern, Map labels) {
      TreePatternLexer tokenizer = new TreePatternLexer(pattern);
      TreePatternParser parser = new TreePatternParser(tokenizer, this, new TreePatternTreeAdaptor());
      TreePattern tpattern = (TreePattern)parser.pattern();
      boolean matched = this._parse(t, tpattern, labels);
      return matched;
   }

   public boolean parse(Object t, String pattern) {
      return this.parse(t, pattern, (Map)null);
   }

   protected boolean _parse(Object t1, TreePattern tpattern, Map labels) {
      if (t1 != null && tpattern != null) {
         if (tpattern.getClass() != WildcardTreePattern.class) {
            if (this.adaptor.getType(t1) != tpattern.getType()) {
               return false;
            }

            if (tpattern.hasTextArg && !this.adaptor.getText(t1).equals(tpattern.getText())) {
               return false;
            }
         }

         if (tpattern.label != null && labels != null) {
            labels.put(tpattern.label, t1);
         }

         int n1 = this.adaptor.getChildCount(t1);
         int n2 = tpattern.getChildCount();
         if (n1 != n2) {
            return false;
         } else {
            for(int i = 0; i < n1; ++i) {
               Object child1 = this.adaptor.getChild(t1, i);
               TreePattern child2 = (TreePattern)tpattern.getChild(i);
               if (!this._parse(child1, child2, labels)) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public Object create(String pattern) {
      TreePatternLexer tokenizer = new TreePatternLexer(pattern);
      TreePatternParser parser = new TreePatternParser(tokenizer, this, this.adaptor);
      Object t = parser.pattern();
      return t;
   }

   public static boolean equals(Object t1, Object t2, TreeAdaptor adaptor) {
      return _equals(t1, t2, adaptor);
   }

   public boolean equals(Object t1, Object t2) {
      return _equals(t1, t2, this.adaptor);
   }

   protected static boolean _equals(Object t1, Object t2, TreeAdaptor adaptor) {
      if (t1 != null && t2 != null) {
         if (adaptor.getType(t1) != adaptor.getType(t2)) {
            return false;
         } else if (!adaptor.getText(t1).equals(adaptor.getText(t2))) {
            return false;
         } else {
            int n1 = adaptor.getChildCount(t1);
            int n2 = adaptor.getChildCount(t2);
            if (n1 != n2) {
               return false;
            } else {
               for(int i = 0; i < n1; ++i) {
                  Object child1 = adaptor.getChild(t1, i);
                  Object child2 = adaptor.getChild(t2, i);
                  if (!_equals(child1, child2, adaptor)) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   public static class TreePatternTreeAdaptor extends CommonTreeAdaptor {
      public Object create(Token payload) {
         return new TreePattern(payload);
      }
   }

   public static class WildcardTreePattern extends TreePattern {
      public WildcardTreePattern(Token payload) {
         super(payload);
      }
   }

   public static class TreePattern extends CommonTree {
      public String label;
      public boolean hasTextArg;

      public TreePattern(Token payload) {
         super(payload);
      }

      public String toString() {
         return this.label != null ? "%" + this.label + ":" + super.toString() : super.toString();
      }
   }

   public abstract static class Visitor implements ContextVisitor {
      public void visit(Object t, Object parent, int childIndex, Map labels) {
         this.visit(t);
      }

      public abstract void visit(Object var1);
   }

   public interface ContextVisitor {
      void visit(Object var1, Object var2, int var3, Map var4);
   }
}
