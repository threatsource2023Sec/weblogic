package org.python.antlr.ast;

import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.expr;
import org.python.antlr.base.stmt;
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
   name = "_ast.Assert",
   base = stmt.class
)
public class Assert extends stmt {
   public static final PyType TYPE;
   private expr test;
   private expr msg;
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

   public expr getInternalMsg() {
      return this.msg;
   }

   public PyObject getMsg() {
      return this.msg;
   }

   public void setMsg(PyObject msg) {
      this.msg = AstAdapters.py2expr(msg);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Assert(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Assert() {
      this(TYPE);
   }

   @ExposedNew
   public void Assert___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Assert", args, keywords, new String[]{"test", "msg", "lineno", "col_offset"}, 2, true);
      this.setTest(ap.getPyObject(0, Py.None));
      this.setMsg(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Assert(PyObject test, PyObject msg) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setTest(test);
      this.setMsg(msg);
   }

   public Assert(Token token, expr test, expr msg) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.msg = msg;
      this.addChild(msg);
   }

   public Assert(Integer ttype, Token token, expr test, expr msg) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.msg = msg;
      this.addChild(msg);
   }

   public Assert(PythonTree tree, expr test, expr msg) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.msg = msg;
      this.addChild(msg);
   }

   public String toString() {
      return "Assert";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Assert(");
      sb.append("test=");
      sb.append(this.dumpThis(this.test));
      sb.append(",");
      sb.append("msg=");
      sb.append(this.dumpThis(this.msg));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitAssert(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.test != null) {
         this.test.accept(visitor);
      }

      if (this.msg != null) {
         this.msg.accept(visitor);
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
      PyType.addBuilder(Assert.class, new PyExposer());
      TYPE = PyType.fromClass(Assert.class);
      fields = new PyString[]{new PyString("test"), new PyString("msg")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Assert___init___exposer extends PyBuiltinMethod {
      public Assert___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Assert___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Assert___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Assert)this.self).Assert___init__(var1, var2);
         return Py.None;
      }
   }

   private static class msg_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public msg_descriptor() {
         super("msg", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Assert)var1).getMsg();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assert)var1).setMsg((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Assert)var1).toString();
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

   private static class lineno_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public lineno_descriptor() {
         super("lineno", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((Assert)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assert)var1).setLineno((Integer)var2);
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
         return ((Assert)var1).getTest();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assert)var1).setTest((PyObject)var2);
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
         return Py.newInteger(((Assert)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assert)var1).setCol_offset((Integer)var2);
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
         return ((Assert)var1).getDict();
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
         return ((Assert)var1).get_fields();
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
         return ((Assert)var1).get_attributes();
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
         Assert var4 = new Assert(this.for_type);
         if (var1) {
            var4.Assert___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new AssertDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Assert___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new msg_descriptor(), new repr_descriptor(), new lineno_descriptor(), new test_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Assert", Assert.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
