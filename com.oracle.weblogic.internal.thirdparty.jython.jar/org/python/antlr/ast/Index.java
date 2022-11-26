package org.python.antlr.ast;

import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.expr;
import org.python.antlr.base.slice;
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
   name = "_ast.Index",
   base = slice.class
)
public class Index extends slice {
   public static final PyType TYPE;
   private expr value;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;

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

   public Index(PyType subType) {
      super(subType);
   }

   public Index() {
      this(TYPE);
   }

   @ExposedNew
   public void Index___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Index", args, keywords, new String[]{"value"}, 1, true);
      this.setValue(ap.getPyObject(0, Py.None));
   }

   public Index(PyObject value) {
      this.setValue(value);
   }

   public Index(Token token, expr value) {
      super(token);
      this.value = value;
      this.addChild(value);
   }

   public Index(Integer ttype, Token token, expr value) {
      super(ttype, token);
      this.value = value;
      this.addChild(value);
   }

   public Index(PythonTree tree, expr value) {
      super(tree);
      this.value = value;
      this.addChild(value);
   }

   public String toString() {
      return "Index";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Index(");
      sb.append("value=");
      sb.append(this.dumpThis(this.value));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitIndex(this);
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

   static {
      PyType.addBuilder(Index.class, new PyExposer());
      TYPE = PyType.fromClass(Index.class);
      fields = new PyString[]{new PyString("value")};
      attributes = new PyString[0];
   }

   private static class Index___init___exposer extends PyBuiltinMethod {
      public Index___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Index___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Index___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Index)this.self).Index___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Index)var1).toString();
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

   private static class __dict___descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public __dict___descriptor() {
         super("__dict__", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Index)var1).getDict();
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
         return ((Index)var1).getValue();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Index)var1).setValue((PyObject)var2);
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
         return ((Index)var1).get_fields();
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
         return ((Index)var1).get_attributes();
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
         Index var4 = new Index(this.for_type);
         if (var1) {
            var4.Index___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new IndexDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Index___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new __dict___descriptor(), new value_descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Index", Index.class, slice.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
