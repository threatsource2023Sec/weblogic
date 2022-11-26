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
   name = "_ast.DictComp",
   base = expr.class
)
public class DictComp extends expr {
   public static final PyType TYPE = PyType.fromClass(DictComp.class);
   private expr key;
   private expr value;
   private java.util.List generators;
   private static final PyString[] fields = new PyString[]{new PyString("key"), new PyString("value"), new PyString("generators")};
   private static final PyString[] attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalKey() {
      return this.key;
   }

   @ExposedGet(
      name = "key"
   )
   public PyObject getKey() {
      return this.key;
   }

   @ExposedSet(
      name = "key"
   )
   public void setKey(PyObject key) {
      this.key = AstAdapters.py2expr(key);
   }

   public expr getInternalValue() {
      return this.value;
   }

   @ExposedGet(
      name = "value"
   )
   public PyObject getValue() {
      return this.value;
   }

   @ExposedSet(
      name = "value"
   )
   public void setValue(PyObject value) {
      this.value = AstAdapters.py2expr(value);
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

   public DictComp(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public DictComp() {
      this(TYPE);
   }

   @ExposedNew
   @ExposedMethod
   public void DictComp___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("DictComp", args, keywords, new String[]{"key", "value", "generators", "lineno", "col_offset"}, 3, true);
      this.setKey(ap.getPyObject(0, Py.None));
      this.setValue(ap.getPyObject(1, Py.None));
      this.setGenerators(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public DictComp(PyObject key, PyObject value, PyObject generators) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setKey(key);
      this.setValue(value);
      this.setGenerators(generators);
   }

   public DictComp(Token token, expr key, expr value, java.util.List generators) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.key = key;
      this.addChild(key);
      this.value = value;
      this.addChild(value);
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

   public DictComp(Integer ttype, Token token, expr key, expr value, java.util.List generators) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.key = key;
      this.addChild(key);
      this.value = value;
      this.addChild(value);
      this.generators = generators;
      if (generators == null) {
         this.generators = new ArrayList();
      }

      Iterator var6 = this.generators.iterator();

      while(var6.hasNext()) {
         PythonTree t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public DictComp(PythonTree tree, expr key, expr value, java.util.List generators) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.key = key;
      this.addChild(key);
      this.value = value;
      this.addChild(value);
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

   @ExposedGet(
      name = "repr"
   )
   public String toString() {
      return "DictComp";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("DictComp(");
      sb.append("key=");
      sb.append(this.dumpThis(this.key));
      sb.append(",");
      sb.append("value=");
      sb.append(this.dumpThis(this.value));
      sb.append(",");
      sb.append("generators=");
      sb.append(this.dumpThis(this.generators));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitDictComp(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.key != null) {
         this.key.accept(visitor);
      }

      if (this.value != null) {
         this.value.accept(visitor);
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
