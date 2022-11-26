package org.python.antlr.ast;

import java.util.ArrayList;
import java.util.Iterator;
import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.expr;
import org.python.antlr.base.stmt;
import org.python.antlr.runtime.Token;
import org.python.core.ArgParser;
import org.python.core.AstList;
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
   name = "_ast.If",
   base = stmt.class
)
public class If extends stmt {
   public static final PyType TYPE;
   private expr test;
   private java.util.List body;
   private java.util.List orelse;
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

   public java.util.List getInternalBody() {
      return this.body;
   }

   public PyObject getBody() {
      return new AstList(this.body, AstAdapters.stmtAdapter);
   }

   public void setBody(PyObject body) {
      this.body = AstAdapters.py2stmtList(body);
   }

   public java.util.List getInternalOrelse() {
      return this.orelse;
   }

   public PyObject getOrelse() {
      return new AstList(this.orelse, AstAdapters.stmtAdapter);
   }

   public void setOrelse(PyObject orelse) {
      this.orelse = AstAdapters.py2stmtList(orelse);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public If(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public If() {
      this(TYPE);
   }

   @ExposedNew
   public void If___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("If", args, keywords, new String[]{"test", "body", "orelse", "lineno", "col_offset"}, 3, true);
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

   public If(PyObject test, PyObject body, PyObject orelse) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setTest(test);
      this.setBody(body);
      this.setOrelse(orelse);
   }

   public If(Token token, expr test, java.util.List body, java.util.List orelse) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var5 = this.body.iterator();

      PythonTree t;
      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.orelse = orelse;
      if (orelse == null) {
         this.orelse = new ArrayList();
      }

      var5 = this.orelse.iterator();

      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public If(Integer ttype, Token token, expr test, java.util.List body, java.util.List orelse) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var6 = this.body.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.orelse = orelse;
      if (orelse == null) {
         this.orelse = new ArrayList();
      }

      var6 = this.orelse.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public If(PythonTree tree, expr test, java.util.List body, java.util.List orelse) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.test = test;
      this.addChild(test);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var5 = this.body.iterator();

      PythonTree t;
      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.orelse = orelse;
      if (orelse == null) {
         this.orelse = new ArrayList();
      }

      var5 = this.orelse.iterator();

      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "If";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("If(");
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
      return visitor.visitIf(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.test != null) {
         this.test.accept(visitor);
      }

      Iterator var2;
      PythonTree t;
      if (this.body != null) {
         var2 = this.body.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

      if (this.orelse != null) {
         var2 = this.orelse.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
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
      PyType.addBuilder(If.class, new PyExposer());
      TYPE = PyType.fromClass(If.class);
      fields = new PyString[]{new PyString("test"), new PyString("body"), new PyString("orelse")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class If___init___exposer extends PyBuiltinMethod {
      public If___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public If___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new If___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((If)this.self).If___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((If)var1).toString();
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
         return ((If)var1).getOrelse();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((If)var1).setOrelse((PyObject)var2);
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
         return Py.newInteger(((If)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((If)var1).setLineno((Integer)var2);
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
         return ((If)var1).getTest();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((If)var1).setTest((PyObject)var2);
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
         return Py.newInteger(((If)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((If)var1).setCol_offset((Integer)var2);
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
         return ((If)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((If)var1).setBody((PyObject)var2);
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
         return ((If)var1).getDict();
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
         return ((If)var1).get_fields();
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
         return ((If)var1).get_attributes();
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
         If var4 = new If(this.for_type);
         if (var1) {
            var4.If___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new IfDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new If___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new orelse_descriptor(), new lineno_descriptor(), new test_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.If", If.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
