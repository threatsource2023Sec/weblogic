package org.python.antlr.ast;

import java.util.ArrayList;
import java.util.Iterator;
import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.expr;
import org.python.antlr.runtime.Token;
import org.python.core.ArgParser;
import org.python.core.AstList;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyType;
import org.python.expose.ExposedGet;
import org.python.expose.ExposedMethod;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedSet;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.Set",
   base = expr.class
)
public class Set extends expr {
   public static final PyType TYPE = PyType.fromClass(Set.class);
   private java.util.List elts;
   private static final PyString[] fields = new PyString[]{new PyString("elts")};
   private static final PyString[] attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public java.util.List getInternalElts() {
      return this.elts;
   }

   @ExposedGet(
      name = "elts"
   )
   public PyObject getElts() {
      return new AstList(this.elts, AstAdapters.exprAdapter);
   }

   @ExposedSet(
      name = "elts"
   )
   public void setElts(PyObject elts) {
      this.elts = AstAdapters.py2exprList(elts);
   }

   @ExposedGet(
      name = "_fields"
   )
   public PyString[] get_fields() {
      return fields;
   }

   @ExposedGet(
      name = "_attributes"
   )
   public PyString[] get_attributes() {
      return attributes;
   }

   public Set(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Set() {
      this(TYPE);
   }

   @ExposedNew
   @ExposedMethod
   public void Set___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Set", args, keywords, new String[]{"elts", "lineno", "col_offset"}, 1, true);
      this.setElts(ap.getPyObject(0, Py.None));
      int lin = ap.getInt(1, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(2, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Set(PyObject elts) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setElts(elts);
   }

   public Set(Token token, java.util.List elts) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.elts = elts;
      if (elts == null) {
         this.elts = new ArrayList();
      }

      Iterator var3 = this.elts.iterator();

      while(var3.hasNext()) {
         PythonTree t = (PythonTree)var3.next();
         this.addChild(t);
      }

   }

   public Set(Integer ttype, Token token, java.util.List elts) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.elts = elts;
      if (elts == null) {
         this.elts = new ArrayList();
      }

      Iterator var4 = this.elts.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public Set(PythonTree tree, java.util.List elts) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.elts = elts;
      if (elts == null) {
         this.elts = new ArrayList();
      }

      Iterator var3 = this.elts.iterator();

      while(var3.hasNext()) {
         PythonTree t = (PythonTree)var3.next();
         this.addChild(t);
      }

   }

   @ExposedGet(
      name = "repr"
   )
   public String toString() {
      return "Set";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Set(");
      sb.append("elts=");
      sb.append(this.dumpThis(this.elts));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitSet(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.elts != null) {
         Iterator var2 = this.elts.iterator();

         while(var2.hasNext()) {
            PythonTree t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

   }

   public PyObject fastGetDict() {
      this.ensureDict();
      return this.__dict__;
   }

   @ExposedGet(
      name = "__dict__"
   )
   public PyObject getDict() {
      return this.fastGetDict();
   }

   private void ensureDict() {
      if (this.__dict__ == null) {
         this.__dict__ = new PyStringMap();
      }

   }

   @ExposedGet(
      name = "lineno"
   )
   public int getLineno() {
      return this.lineno != -1 ? this.lineno : this.getLine();
   }

   @ExposedSet(
      name = "lineno"
   )
   public void setLineno(int num) {
      this.lineno = num;
   }

   @ExposedGet(
      name = "col_offset"
   )
   public int getCol_offset() {
      return this.col_offset != -1 ? this.col_offset : this.getCharPositionInLine();
   }

   @ExposedSet(
      name = "col_offset"
   )
   public void setCol_offset(int num) {
      this.col_offset = num;
   }
}
