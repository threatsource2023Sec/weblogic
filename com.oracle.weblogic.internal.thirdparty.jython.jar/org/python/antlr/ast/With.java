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
   name = "_ast.With",
   base = stmt.class
)
public class With extends stmt {
   public static final PyType TYPE;
   private expr context_expr;
   private expr optional_vars;
   private java.util.List body;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalContext_expr() {
      return this.context_expr;
   }

   public PyObject getContext_expr() {
      return this.context_expr;
   }

   public void setContext_expr(PyObject context_expr) {
      this.context_expr = AstAdapters.py2expr(context_expr);
   }

   public expr getInternalOptional_vars() {
      return this.optional_vars;
   }

   public PyObject getOptional_vars() {
      return this.optional_vars;
   }

   public void setOptional_vars(PyObject optional_vars) {
      this.optional_vars = AstAdapters.py2expr(optional_vars);
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

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public With(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public With() {
      this(TYPE);
   }

   @ExposedNew
   public void With___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("With", args, keywords, new String[]{"context_expr", "optional_vars", "body", "lineno", "col_offset"}, 3, true);
      this.setContext_expr(ap.getPyObject(0, Py.None));
      this.setOptional_vars(ap.getPyObject(1, Py.None));
      this.setBody(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public With(PyObject context_expr, PyObject optional_vars, PyObject body) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setContext_expr(context_expr);
      this.setOptional_vars(optional_vars);
      this.setBody(body);
   }

   public With(Token token, expr context_expr, expr optional_vars, java.util.List body) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.context_expr = context_expr;
      this.addChild(context_expr);
      this.optional_vars = optional_vars;
      this.addChild(optional_vars);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var5 = this.body.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public With(Integer ttype, Token token, expr context_expr, expr optional_vars, java.util.List body) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.context_expr = context_expr;
      this.addChild(context_expr);
      this.optional_vars = optional_vars;
      this.addChild(optional_vars);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var6 = this.body.iterator();

      while(var6.hasNext()) {
         PythonTree t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public With(PythonTree tree, expr context_expr, expr optional_vars, java.util.List body) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.context_expr = context_expr;
      this.addChild(context_expr);
      this.optional_vars = optional_vars;
      this.addChild(optional_vars);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var5 = this.body.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "With";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("With(");
      sb.append("context_expr=");
      sb.append(this.dumpThis(this.context_expr));
      sb.append(",");
      sb.append("optional_vars=");
      sb.append(this.dumpThis(this.optional_vars));
      sb.append(",");
      sb.append("body=");
      sb.append(this.dumpThis(this.body));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitWith(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.context_expr != null) {
         this.context_expr.accept(visitor);
      }

      if (this.optional_vars != null) {
         this.optional_vars.accept(visitor);
      }

      if (this.body != null) {
         Iterator var2 = this.body.iterator();

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
      PyType.addBuilder(With.class, new PyExposer());
      TYPE = PyType.fromClass(With.class);
      fields = new PyString[]{new PyString("context_expr"), new PyString("optional_vars"), new PyString("body")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class With___init___exposer extends PyBuiltinMethod {
      public With___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public With___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new With___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((With)this.self).With___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((With)var1).toString();
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
         return Py.newInteger(((With)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((With)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class optional_vars_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public optional_vars_descriptor() {
         super("optional_vars", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((With)var1).getOptional_vars();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((With)var1).setOptional_vars((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class context_expr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public context_expr_descriptor() {
         super("context_expr", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((With)var1).getContext_expr();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((With)var1).setContext_expr((PyObject)var2);
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
         return Py.newInteger(((With)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((With)var1).setCol_offset((Integer)var2);
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
         return ((With)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((With)var1).setBody((PyObject)var2);
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
         return ((With)var1).getDict();
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
         return ((With)var1).get_fields();
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
         return ((With)var1).get_attributes();
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
         With var4 = new With(this.for_type);
         if (var1) {
            var4.With___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new WithDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new With___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new optional_vars_descriptor(), new context_expr_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.With", With.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
