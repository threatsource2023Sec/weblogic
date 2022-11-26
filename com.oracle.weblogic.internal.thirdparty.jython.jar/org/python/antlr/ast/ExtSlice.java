package org.python.antlr.ast;

import java.util.ArrayList;
import java.util.Iterator;
import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.slice;
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
   name = "_ast.ExtSlice",
   base = slice.class
)
public class ExtSlice extends slice {
   public static final PyType TYPE;
   private java.util.List dims;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;

   public java.util.List getInternalDims() {
      return this.dims;
   }

   public PyObject getDims() {
      return new AstList(this.dims, AstAdapters.sliceAdapter);
   }

   public void setDims(PyObject dims) {
      this.dims = AstAdapters.py2sliceList(dims);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public ExtSlice(PyType subType) {
      super(subType);
   }

   public ExtSlice() {
      this(TYPE);
   }

   @ExposedNew
   public void ExtSlice___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("ExtSlice", args, keywords, new String[]{"dims"}, 1, true);
      this.setDims(ap.getPyObject(0, Py.None));
   }

   public ExtSlice(PyObject dims) {
      this.setDims(dims);
   }

   public ExtSlice(Token token, java.util.List dims) {
      super(token);
      this.dims = dims;
      if (dims == null) {
         this.dims = new ArrayList();
      }

      Iterator var3 = this.dims.iterator();

      while(var3.hasNext()) {
         PythonTree t = (PythonTree)var3.next();
         this.addChild(t);
      }

   }

   public ExtSlice(Integer ttype, Token token, java.util.List dims) {
      super(ttype, token);
      this.dims = dims;
      if (dims == null) {
         this.dims = new ArrayList();
      }

      Iterator var4 = this.dims.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public ExtSlice(PythonTree tree, java.util.List dims) {
      super(tree);
      this.dims = dims;
      if (dims == null) {
         this.dims = new ArrayList();
      }

      Iterator var3 = this.dims.iterator();

      while(var3.hasNext()) {
         PythonTree t = (PythonTree)var3.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "ExtSlice";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("ExtSlice(");
      sb.append("dims=");
      sb.append(this.dumpThis(this.dims));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitExtSlice(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.dims != null) {
         Iterator var2 = this.dims.iterator();

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

   static {
      PyType.addBuilder(ExtSlice.class, new PyExposer());
      TYPE = PyType.fromClass(ExtSlice.class);
      fields = new PyString[]{new PyString("dims")};
      attributes = new PyString[0];
   }

   private static class ExtSlice___init___exposer extends PyBuiltinMethod {
      public ExtSlice___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public ExtSlice___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ExtSlice___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((ExtSlice)this.self).ExtSlice___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((ExtSlice)var1).toString();
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

   private static class dims_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public dims_descriptor() {
         super("dims", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((ExtSlice)var1).getDims();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ExtSlice)var1).setDims((PyObject)var2);
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
         return ((ExtSlice)var1).getDict();
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
         return ((ExtSlice)var1).get_fields();
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
         return ((ExtSlice)var1).get_attributes();
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
         ExtSlice var4 = new ExtSlice(this.for_type);
         if (var1) {
            var4.ExtSlice___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new ExtSliceDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new ExtSlice___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new dims_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.ExtSlice", ExtSlice.class, slice.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
