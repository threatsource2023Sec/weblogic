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
   name = "_ast.SetComp",
   base = expr.class
)
public class SetComp extends expr {
   public static final PyType TYPE = PyType.fromClass(SetComp.class);
   private expr elt;
   private java.util.List generators;
   private static final PyString[] fields = new PyString[]{new PyString("elt"), new PyString("generators")};
   private static final PyString[] attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalElt() {
      return this.elt;
   }

   @ExposedGet(
      name = "elt"
   )
   public PyObject getElt() {
      return this.elt;
   }

   @ExposedSet(
      name = "elt"
   )
   public void setElt(PyObject elt) {
      this.elt = AstAdapters.py2expr(elt);
   }

   public java.util.List getInternalGenerators() {
      return this.generators;
   }

   @ExposedGet(
      name = "generators"
   )
   public PyObject getGenerators() {
      return new AstList(this.generators, AstAdapters.comprehensionAdapter);
   }

   @ExposedSet(
      name = "generators"
   )
   public void setGenerators(PyObject generators) {
      this.generators = AstAdapters.py2comprehensionList(generators);
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

   public SetComp(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public SetComp() {
      this(TYPE);
   }

   @ExposedNew
   @ExposedMethod
   public void SetComp___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("SetComp", args, keywords, new String[]{"elt", "generators", "lineno", "col_offset"}, 2, true);
      this.setElt(ap.getPyObject(0, Py.None));
      this.setGenerators(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public SetComp(PyObject elt, PyObject generators) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setElt(elt);
      this.setGenerators(generators);
   }

   public SetComp(Token token, expr elt, java.util.List generators) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.elt = elt;
      this.addChild(elt);
      this.generators = generators;
      if (generators == null) {
         this.generators = new ArrayList();
      }

      Iterator var4 = this.generators.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public SetComp(Integer ttype, Token token, expr elt, java.util.List generators) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.elt = elt;
      this.addChild(elt);
      this.generators = generators;
      if (generators == null) {
         this.generators = new ArrayList();
      }

      Iterator var5 = this.generators.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public SetComp(PythonTree tree, expr elt, java.util.List generators) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.elt = elt;
      this.addChild(elt);
      this.generators = generators;
      if (generators == null) {
         this.generators = new ArrayList();
      }

      Iterator var4 = this.generators.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   @ExposedGet(
      name = "repr"
   )
   public String toString() {
      return "SetComp";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("SetComp(");
      sb.append("elt=");
      sb.append(this.dumpThis(this.elt));
      sb.append(",");
      sb.append("generators=");
      sb.append(this.dumpThis(this.generators));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitSetComp(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.elt != null) {
         this.elt.accept(visitor);
      }

      if (this.generators != null) {
         Iterator var2 = this.generators.iterator();

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
