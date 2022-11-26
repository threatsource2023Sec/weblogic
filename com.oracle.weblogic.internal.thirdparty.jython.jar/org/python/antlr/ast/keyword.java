package org.python.antlr.ast;

import org.python.antlr.AST;
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
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.keyword",
   base = AST.class
)
public class keyword extends PythonTree {
   public static final PyType TYPE;
   private String arg;
   private expr value;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;

   public String getInternalArg() {
      return this.arg;
   }

   public PyObject getArg() {
      return (PyObject)(this.arg == null ? Py.None : new PyString(this.arg));
   }

   public void setArg(PyObject arg) {
      this.arg = AstAdapters.py2identifier(arg);
   }

   public expr getInternalValue() {
      return this.value;
   }

   public PyObject getValue() {
      return this.value;
   }

   public void setValue(PyObject value) {
      this.value = AstAdapters.py2expr(value);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public keyword(PyType subType) {
      super(subType);
   }

   public keyword() {
      this(TYPE);
   }

   @ExposedNew
   public void keyword___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("keyword", args, keywords, new String[]{"arg", "value"}, 2, true);
      this.setArg(ap.getPyObject(0, Py.None));
      this.setValue(ap.getPyObject(1, Py.None));
   }

   public keyword(PyObject arg, PyObject value) {
      this.setArg(arg);
      this.setValue(value);
   }

   public keyword(Token token, String arg, expr value) {
      super(token);
      this.arg = arg;
      this.value = value;
      this.addChild(value);
   }

   public keyword(Integer ttype, Token token, String arg, expr value) {
      super(ttype, token);
      this.arg = arg;
      this.value = value;
      this.addChild(value);
   }

   public keyword(PythonTree tree, String arg, expr value) {
      super(tree);
      this.arg = arg;
      this.value = value;
      this.addChild(value);
   }

   public String toString() {
      return "keyword";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("keyword(");
      sb.append("arg=");
      sb.append(this.dumpThis(this.arg));
      sb.append(",");
      sb.append("value=");
      sb.append(this.dumpThis(this.value));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      this.traverse(visitor);
      return null;
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.value != null) {
         this.value.accept(visitor);
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

   public int traverse(Visitproc visit, Object arg) {
      return this.value != null ? visit.visit(this.value, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.value || super.refersDirectlyTo(ob));
   }

   static {
      PyType.addBuilder(keyword.class, new PyExposer());
      TYPE = PyType.fromClass(keyword.class);
      fields = new PyString[]{new PyString("arg"), new PyString("value")};
      attributes = new PyString[0];
   }

   private static class keyword___init___exposer extends PyBuiltinMethod {
      public keyword___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public keyword___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new keyword___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((keyword)this.self).keyword___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((keyword)var1).toString();
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

   private static class arg_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public arg_descriptor() {
         super("arg", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((keyword)var1).getArg();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((keyword)var1).setArg((PyObject)var2);
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
         return ((keyword)var1).getDict();
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

   private static class value_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public value_descriptor() {
         super("value", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((keyword)var1).getValue();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((keyword)var1).setValue((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
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
         return ((keyword)var1).get_fields();
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
         return ((keyword)var1).get_attributes();
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
         keyword var4 = new keyword(this.for_type);
         if (var1) {
            var4.keyword___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new keywordDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new keyword___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new arg_descriptor(), new __dict___descriptor(), new value_descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.keyword", keyword.class, AST.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
