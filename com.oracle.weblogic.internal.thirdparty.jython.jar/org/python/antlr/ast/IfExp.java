package org.python.antlr.ast;

import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.expr;
import org.python.antlr.runtime.Token;
import org.python.core.ArgParser;
import org.python.core.Py;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyDataDescr;
import org.python.core.PyObject;
import org.python.core.PyOverridableNew;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyType;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.IfExp",
   base = expr.class
)
public class IfExp extends expr {
   public static final PyType TYPE;
   private expr test;
   private expr body;
   private expr orelse;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalTest() {
      return this.test;
   }

   public PyObject getTest() {
      return this.test;
   }

   public void setTest(PyObject test) {
      this.test = AstAdapters.py2expr(test);
   }

   public expr getInternalBody() {
      return this.body;
   }

   public PyObject getBody() {
      return this.body;
   }

   public void setBody(PyObject body) {
      this.body = AstAdapters.py2expr(body);
   }

   public expr getInternalOrelse() {
      return this.orelse;
   }

   public PyObject getOrelse() {
      return this.orelse;
   }

   public void setOrelse(PyObject orelse) {
      this.orelse = AstAdapters.py2expr(orelse);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public IfExp(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public IfExp() {
      this(TYPE);
   }

   @ExposedNew
   public void IfExp___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("IfExp", args, keywords, new String[]{"test", "body", "orelse", "lineno", "col_offset"}, 3, true);
      this.setTest(ap.getPyObject(0, Py.None));
      this.setBody(ap.getPyObject(1, Py.None));
      this.setOrelse(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public IfExp(PyObject test, PyObject body, PyObject orelse) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setTest(test);
      this.setBody(body);
      this.setOrelse(orelse);
   }

   public IfExp(Token token, expr test, expr body, expr orelse) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.body = body;
      this.addChild(body);
      this.orelse = orelse;
      this.addChild(orelse);
   }

   public IfExp(Integer ttype, Token token, expr test, expr body, expr orelse) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.body = body;
      this.addChild(body);
      this.orelse = orelse;
      this.addChild(orelse);
   }

   public IfExp(PythonTree tree, expr test, expr body, expr orelse) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.body = body;
      this.addChild(body);
      this.orelse = orelse;
      this.addChild(orelse);
   }

   public String toString() {
      return "IfExp";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("IfExp(");
      sb.append("test=");
      sb.append(this.dumpThis(this.test));
      sb.append(",");
      sb.append("body=");
      sb.append(this.dumpThis(this.body));
      sb.append(",");
      sb.append("orelse=");
      sb.append(this.dumpThis(this.orelse));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitIfExp(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.test != null) {
         this.test.accept(visitor);
      }

      if (this.body != null) {
         this.body.accept(visitor);
      }

      if (this.orelse != null) {
         this.orelse.accept(visitor);
      }

   }

   public PyObject fastGetDict() {
      this.ensureDict();
      return this.__dict__;
   }

   public PyObject getDict() {
      return this.fastGetDict();
   }

   private void ensureDict() {
      if (this.__dict__ == null) {
         this.__dict__ = new PyStringMap();
      }

   }

   public int getLineno() {
      return this.lineno != -1 ? this.lineno : this.getLine();
   }

   public void setLineno(int num) {
      this.lineno = num;
   }

   public int getCol_offset() {
      return this.col_offset != -1 ? this.col_offset : this.getCharPositionInLine();
   }

   public void setCol_offset(int num) {
      this.col_offset = num;
   }

   static {
      PyType.addBuilder(IfExp.class, new PyExposer());
      TYPE = PyType.fromClass(IfExp.class);
      fields = new PyString[]{new PyString("test"), new PyString("body"), new PyString("orelse")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class IfExp___init___exposer extends PyBuiltinMethod {
      public IfExp___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public IfExp___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new IfExp___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((IfExp)this.self).IfExp___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((IfExp)var1).toString();
         return var10000 == null ? Py.None : Py.newString(var10000);
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class orelse_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public orelse_descriptor() {
         super("orelse", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((IfExp)var1).getOrelse();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((IfExp)var1).setOrelse((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class lineno_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public lineno_descriptor() {
         super("lineno", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((IfExp)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((IfExp)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class test_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public test_descriptor() {
         super("test", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((IfExp)var1).getTest();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((IfExp)var1).setTest((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class col_offset_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public col_offset_descriptor() {
         super("col_offset", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((IfExp)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((IfExp)var1).setCol_offset((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class body_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public body_descriptor() {
         super("body", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((IfExp)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((IfExp)var1).setBody((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((IfExp)var1).getDict();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class _fields_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _fields_descriptor() {
         super("_fields", PyString[].class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((IfExp)var1).get_fields();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class _attributes_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public _attributes_descriptor() {
         super("_attributes", PyString[].class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((IfExp)var1).get_attributes();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         IfExp var4 = new IfExp(this.for_type);
         if (var1) {
            var4.IfExp___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new IfExpDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new IfExp___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new orelse_descriptor(), new lineno_descriptor(), new test_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.IfExp", IfExp.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
