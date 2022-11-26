package org.python.antlr;

import java.util.ArrayList;
import java.util.List;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.VisitorIF;
import org.python.antlr.runtime.CommonToken;
import org.python.antlr.runtime.Token;
import org.python.antlr.runtime.tree.CommonTree;
import org.python.core.PyObject;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;

public class PythonTree extends AST implements Traverseproc {
   public boolean from_future_checked = false;
   private int charStartIndex = -1;
   private int charStopIndex = -1;
   private CommonTree node;
   private PythonTree parent;
   protected List children;

   public PythonTree() {
      this.node = new CommonTree();
   }

   public PythonTree(PyType subType) {
      super(subType);
      this.node = new CommonTree();
   }

   public PythonTree(Token t) {
      this.node = new CommonTree(t);
   }

   public PythonTree(int ttype, Token t) {
      CommonToken c = new CommonToken(ttype, t.getText());
      c.setLine(t.getLine());
      c.setTokenIndex(t.getTokenIndex());
      c.setCharPositionInLine(t.getCharPositionInLine());
      c.setChannel(t.getChannel());
      c.setStartIndex(((CommonToken)t).getStartIndex());
      c.setStopIndex(((CommonToken)t).getStopIndex());
      this.node = new CommonTree(c);
   }

   public PythonTree(PythonTree tree) {
      this.node = new CommonTree(tree.getNode());
      this.charStartIndex = tree.getCharStartIndex();
      this.charStopIndex = tree.getCharStopIndex();
   }

   public CommonTree getNode() {
      return this.node;
   }

   public Token getToken() {
      return this.node.getToken();
   }

   public PythonTree dupNode() {
      return new PythonTree(this);
   }

   public boolean isNil() {
      return this.node.isNil();
   }

   public int getAntlrType() {
      return this.node.getType();
   }

   public String getText() {
      return this.node.getText();
   }

   public int getLine() {
      if (this.node.getToken() != null && this.node.getToken().getLine() != 0) {
         return this.node.getToken().getLine();
      } else {
         return this.getChildCount() > 0 ? this.getChild(0).getLine() : 1;
      }
   }

   public int getCharPositionInLine() {
      Token token = this.node.getToken();
      if (token != null && token.getCharPositionInLine() != -1) {
         return token != null && token.getCharPositionInLine() == -2 ? -1 : token.getCharPositionInLine();
      } else {
         return this.getChildCount() > 0 ? this.getChild(0).getCharPositionInLine() : 0;
      }
   }

   public int getTokenStartIndex() {
      return this.node.getTokenStartIndex();
   }

   public void setTokenStartIndex(int index) {
      this.node.setTokenStartIndex(index);
   }

   public int getTokenStopIndex() {
      return this.node.getTokenStopIndex();
   }

   public void setTokenStopIndex(int index) {
      this.node.setTokenStopIndex(index);
   }

   public int getCharStartIndex() {
      return this.charStartIndex == -1 && this.node.getToken() != null ? ((CommonToken)this.node.getToken()).getStartIndex() : this.charStartIndex;
   }

   public void setCharStartIndex(int index) {
      this.charStartIndex = index;
   }

   public int getCharStopIndex() {
      return this.charStopIndex == -1 && this.node.getToken() != null ? ((CommonToken)this.node.getToken()).getStopIndex() + 1 : this.charStopIndex;
   }

   public void setCharStopIndex(int index) {
      this.charStopIndex = index;
   }

   public int getChildIndex() {
      return this.node.getChildIndex();
   }

   public PythonTree getParent() {
      return this.parent;
   }

   public void setParent(PythonTree t) {
      this.parent = t;
   }

   public void setChildIndex(int index) {
      this.node.setChildIndex(index);
   }

   public static String dottedNameListToString(List names) {
      if (names == null) {
         return "";
      } else {
         StringBuilder sb = new StringBuilder();
         boolean leadingDot = true;
         int i = 0;

         for(int len = names.size(); i < len; ++i) {
            Name name = (Name)names.get(i);
            String id = name.getInternalId();
            if (id != null) {
               if (!".".equals(id)) {
                  leadingDot = false;
               }

               sb.append(id);
               if (i < len - 1 && !leadingDot) {
                  sb.append(".");
               }
            }
         }

         return sb.toString();
      }
   }

   public String toString() {
      if (this.isNil()) {
         return "None";
      } else if (this.getAntlrType() == 0) {
         return "<errornode>";
      } else {
         return this.node.getToken() == null ? null : this.node.getToken().getText() + "(" + this.getLine() + "," + this.getCharPositionInLine() + ")";
      }
   }

   public String toStringTree() {
      if (this.children != null && this.children.size() != 0) {
         StringBuffer buf = new StringBuffer();
         if (!this.isNil()) {
            buf.append("(");
            buf.append(this.toString());
            buf.append(' ');
         }

         for(int i = 0; this.children != null && i < this.children.size(); ++i) {
            PythonTree t = (PythonTree)this.children.get(i);
            if (i > 0) {
               buf.append(' ');
            }

            buf.append(t.toStringTree());
         }

         if (!this.isNil()) {
            buf.append(")");
         }

         return buf.toString();
      } else {
         return this.toString();
      }
   }

   protected String dumpThis(String s) {
      return s;
   }

   protected String dumpThis(Object o) {
      return o instanceof PythonTree ? ((PythonTree)o).toStringTree() : String.valueOf(o);
   }

   protected String dumpThis(Object[] s) {
      StringBuffer sb = new StringBuffer();
      if (s == null) {
         sb.append("null");
      } else {
         sb.append("(");

         for(int i = 0; i < s.length; ++i) {
            if (i > 0) {
               sb.append(", ");
            }

            sb.append(this.dumpThis(s[i]));
         }

         sb.append(")");
      }

      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      throw new RuntimeException("Unexpected node: " + this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      throw new RuntimeException("Cannot traverse node: " + this);
   }

   public PythonTree getChild(int i) {
      return this.children != null && i < this.children.size() ? (PythonTree)this.children.get(i) : null;
   }

   public List getChildren() {
      return this.children;
   }

   public PythonTree getFirstChildWithType(int type) {
      for(int i = 0; this.children != null && i < this.children.size(); ++i) {
         PythonTree t = (PythonTree)this.children.get(i);
         if (t.getAntlrType() == type) {
            return t;
         }
      }

      return null;
   }

   public int getChildCount() {
      return this.children == null ? 0 : this.children.size();
   }

   public void addChild(PythonTree t) {
      if (t != null) {
         PythonTree childTree = t;
         if (t.isNil()) {
            if (this.children != null && this.children == t.children) {
               throw new RuntimeException("attempt to add child list to itself");
            }

            if (t.children != null) {
               if (this.children != null) {
                  int n = t.children.size();

                  for(int i = 0; i < n; ++i) {
                     PythonTree c = (PythonTree)childTree.children.get(i);
                     this.children.add(c);
                     c.setParent(this);
                     c.setChildIndex(this.children.size() - 1);
                  }
               } else {
                  this.children = t.children;
                  this.freshenParentAndChildIndexes();
               }
            }
         } else {
            if (this.children == null) {
               this.children = this.createChildrenList();
            }

            this.children.add(t);
            t.setParent(this);
            t.setChildIndex(this.children.size() - 1);
         }

      }
   }

   public void addChildren(List kids) {
      for(int i = 0; i < kids.size(); ++i) {
         PythonTree t = (PythonTree)kids.get(i);
         this.addChild(t);
      }

   }

   public void setChild(int i, PythonTree t) {
      if (t != null) {
         if (t.isNil()) {
            throw new IllegalArgumentException("Can't set single child to a list");
         } else {
            if (this.children == null) {
               this.children = this.createChildrenList();
            }

            this.children.set(i, t);
            t.setParent(this);
            t.setChildIndex(i);
         }
      }
   }

   public Object deleteChild(int i) {
      if (this.children == null) {
         return null;
      } else {
         PythonTree killed = (PythonTree)this.children.remove(i);
         this.freshenParentAndChildIndexes(i);
         return killed;
      }
   }

   public void replaceChildren(int startChildIndex, int stopChildIndex, Object t) {
      if (this.children == null) {
         throw new IllegalArgumentException("indexes invalid; no children in list");
      } else {
         int replacingHowMany = stopChildIndex - startChildIndex + 1;
         PythonTree newTree = (PythonTree)t;
         List newChildren = null;
         if (newTree.isNil()) {
            newChildren = newTree.children;
         } else {
            newChildren = new ArrayList(1);
            ((List)newChildren).add(newTree);
         }

         int replacingWithHowMany = ((List)newChildren).size();
         int numNewChildren = ((List)newChildren).size();
         int delta = replacingHowMany - replacingWithHowMany;
         int indexToDelete;
         int j;
         PythonTree child;
         if (delta == 0) {
            indexToDelete = 0;

            for(j = startChildIndex; j <= stopChildIndex; ++j) {
               child = (PythonTree)((List)newChildren).get(indexToDelete);
               this.children.set(j, child);
               child.setParent(this);
               child.setChildIndex(j);
               ++indexToDelete;
            }
         } else if (delta > 0) {
            for(indexToDelete = 0; indexToDelete < numNewChildren; ++indexToDelete) {
               this.children.set(startChildIndex + indexToDelete, ((List)newChildren).get(indexToDelete));
            }

            indexToDelete = startChildIndex + numNewChildren;

            for(j = indexToDelete; j <= stopChildIndex; ++j) {
               child = (PythonTree)this.children.remove(indexToDelete);
            }

            this.freshenParentAndChildIndexes(startChildIndex);
         } else {
            for(indexToDelete = 0; indexToDelete < replacingHowMany; ++indexToDelete) {
               this.children.set(startChildIndex + indexToDelete, ((List)newChildren).get(indexToDelete));
            }

            int var10000 = replacingWithHowMany - replacingHowMany;

            for(j = replacingHowMany; j < replacingWithHowMany; ++j) {
               this.children.add(startChildIndex + j, ((List)newChildren).get(j));
            }

            this.freshenParentAndChildIndexes(startChildIndex);
         }

      }
   }

   protected List createChildrenList() {
      return new ArrayList();
   }

   public void freshenParentAndChildIndexes() {
      this.freshenParentAndChildIndexes(0);
   }

   public void freshenParentAndChildIndexes(int offset) {
      int n = this.getChildCount();

      for(int c = offset; c < n; ++c) {
         PythonTree child = this.getChild(c);
         child.setChildIndex(c);
         child.setParent(this);
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      return this.parent != null ? visit.visit(this.parent, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && ob == this.parent;
   }
}
