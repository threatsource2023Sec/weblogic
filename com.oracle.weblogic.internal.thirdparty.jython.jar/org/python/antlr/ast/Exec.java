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
   name = "_ast.Exec",
   base = stmt.class
)
public class Exec extends stmt {
   public static final PyType TYPE;
   private expr body;
   private expr globals;
   private expr locals;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalBody() {
      return this.body;
   }

   public PyObject getBody() {
      return this.body;
   }

   public void setBody(PyObject body) {
      this.body = AstAdapters.py2expr(body);
   }

   public expr getInternalGlobals() {
      return this.globals;
   }

   public PyObject getGlobals() {
      return this.globals;
   }

   public void setGlobals(PyObject globals) {
      this.globals = AstAdapters.py2expr(globals);
   }

   public expr getInternalLocals() {
      return this.locals;
   }

   public PyObject getLocals() {
      return this.locals;
   }

   public void setLocals(PyObject locals) {
      this.locals = AstAdapters.py2expr(locals);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Exec(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Exec() {
      this(TYPE);
   }

   @ExposedNew
   public void Exec___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Exec", args, keywords, new String[]{"body", "globals", "locals", "lineno", "col_offset"}, 3, true);
      this.setBody(ap.getPyObject(0, Py.None));
      this.setGlobals(ap.getPyObject(1, Py.None));
      this.setLocals(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Exec(PyObject body, PyObject globals, PyObject locals) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setBody(body);
      this.setGlobals(globals);
      this.setLocals(locals);
   }

   public Exec(Token token, expr body, expr globals, expr locals) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.body = body;
      this.addChild(body);
      this.globals = globals;
      this.addChild(globals);
      this.locals = locals;
      this.addChild(locals);
   }

   public Exec(Integer ttype, Token token, expr body, expr globals, expr locals) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.body = body;
      this.addChild(body);
      this.globals = globals;
      this.addChild(globals);
      this.locals = locals;
      this.addChild(locals);
   }

   public Exec(PythonTree tree, expr body, expr globals, expr locals) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.body = body;
      this.addChild(body);
      this.globals = globals;
      this.addChild(globals);
      this.locals = locals;
      this.addChild(locals);
   }

   public String toString() {
      return "Exec";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Exec(");
      sb.append("body=");
      sb.append(this.dumpThis(this.body));
      sb.append(",");
      sb.append("globals=");
      sb.append(this.dumpThis(this.globals));
      sb.append(",");
      sb.append("locals=");
      sb.append(this.dumpThis(this.locals));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitExec(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.body != null) {
         this.body.accept(visitor);
      }

      if (this.globals != null) {
         this.globals.accept(visitor);
      }

      if (this.locals != null) {
         this.locals.accept(visitor);
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
      PyType.addBuilder(Exec.class, new PyExposer());
      TYPE = PyType.fromClass(Exec.class);
      fields = new PyString[]{new PyString("body"), new PyString("globals"), new PyString("locals")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Exec___init___exposer extends PyBuiltinMethod {
      public Exec___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Exec___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Exec___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Exec)this.self).Exec___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Exec)var1).toString();
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
         return Py.newInteger(((Exec)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Exec)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class globals_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public globals_descriptor() {
         super("globals", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Exec)var1).getGlobals();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Exec)var1).setGlobals((PyObject)var2);
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
         return Py.newInteger(((Exec)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Exec)var1).setCol_offset((Integer)var2);
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
         return ((Exec)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Exec)var1).setBody((PyObject)var2);
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
         return ((Exec)var1).getDict();
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
         return ((Exec)var1).get_fields();
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
         return ((Exec)var1).get_attributes();
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

   private static class locals_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public locals_descriptor() {
         super("locals", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Exec)var1).getLocals();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Exec)var1).setLocals((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class exposed___new__ extends PyOverridableNew {
      public exposed___new__() {
      }

      public PyObject createOfType(boolean var1, PyObject[] var2, String[] var3) {
         Exec var4 = new Exec(this.for_type);
         if (var1) {
            var4.Exec___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new ExecDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Exec___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new globals_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor(), new locals_descriptor()};
         super("_ast.Exec", Exec.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
