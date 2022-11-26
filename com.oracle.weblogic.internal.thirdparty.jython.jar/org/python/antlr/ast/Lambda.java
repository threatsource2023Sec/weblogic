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
   name = "_ast.Lambda",
   base = expr.class
)
public class Lambda extends expr {
   public static final PyType TYPE;
   private arguments args;
   private expr body;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public arguments getInternalArgs() {
      return this.args;
   }

   public PyObject getArgs() {
      return this.args;
   }

   public void setArgs(PyObject args) {
      this.args = AstAdapters.py2arguments(args);
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

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Lambda(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Lambda() {
      this(TYPE);
   }

   @ExposedNew
   public void Lambda___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Lambda", args, keywords, new String[]{"args", "body", "lineno", "col_offset"}, 2, true);
      this.setArgs(ap.getPyObject(0, Py.None));
      this.setBody(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Lambda(PyObject args, PyObject body) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setArgs(args);
      this.setBody(body);
   }

   public Lambda(Token token, arguments args, expr body) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.args = args;
      this.body = body;
      this.addChild(body);
   }

   public Lambda(Integer ttype, Token token, arguments args, expr body) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.args = args;
      this.body = body;
      this.addChild(body);
   }

   public Lambda(PythonTree tree, arguments args, expr body) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.args = args;
      this.body = body;
      this.addChild(body);
   }

   public String toString() {
      return "Lambda";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Lambda(");
      sb.append("args=");
      sb.append(this.dumpThis(this.args));
      sb.append(",");
      sb.append("body=");
      sb.append(this.dumpThis(this.body));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitLambda(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.args != null) {
         this.args.accept(visitor);
      }

      if (this.body != null) {
         this.body.accept(visitor);
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
      PyType.addBuilder(Lambda.class, new PyExposer());
      TYPE = PyType.fromClass(Lambda.class);
      fields = new PyString[]{new PyString("args"), new PyString("body")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Lambda___init___exposer extends PyBuiltinMethod {
      public Lambda___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Lambda___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Lambda___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Lambda)this.self).Lambda___init__(var1, var2);
         return Py.None;
      }
   }

   private static class args_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public args_descriptor() {
         super("args", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Lambda)var1).getArgs();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Lambda)var1).setArgs((PyObject)var2);
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
         String var10000 = ((Lambda)var1).toString();
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
         return Py.newInteger(((Lambda)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Lambda)var1).setLineno((Integer)var2);
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
         return Py.newInteger(((Lambda)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Lambda)var1).setCol_offset((Integer)var2);
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
         return ((Lambda)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Lambda)var1).setBody((PyObject)var2);
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
         return ((Lambda)var1).getDict();
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
         return ((Lambda)var1).get_fields();
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
         return ((Lambda)var1).get_attributes();
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
         Lambda var4 = new Lambda(this.for_type);
         if (var1) {
            var4.Lambda___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new LambdaDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Lambda___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new args_descriptor(), new repr_descriptor(), new lineno_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Lambda", Lambda.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
