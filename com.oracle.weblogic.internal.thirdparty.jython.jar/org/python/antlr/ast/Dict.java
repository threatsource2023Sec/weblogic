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
   name = "_ast.Dict",
   base = expr.class
)
public class Dict extends expr {
   public static final PyType TYPE;
   private java.util.List keys;
   private java.util.List values;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public java.util.List getInternalKeys() {
      return this.keys;
   }

   public PyObject getKeys() {
      return new AstList(this.keys, AstAdapters.exprAdapter);
   }

   public void setKeys(PyObject keys) {
      this.keys = AstAdapters.py2exprList(keys);
   }

   public java.util.List getInternalValues() {
      return this.values;
   }

   public PyObject getValues() {
      return new AstList(this.values, AstAdapters.exprAdapter);
   }

   public void setValues(PyObject values) {
      this.values = AstAdapters.py2exprList(values);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Dict(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Dict() {
      this(TYPE);
   }

   @ExposedNew
   public void Dict___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Dict", args, keywords, new String[]{"keys", "values", "lineno", "col_offset"}, 2, true);
      this.setKeys(ap.getPyObject(0, Py.None));
      this.setValues(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Dict(PyObject keys, PyObject values) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setKeys(keys);
      this.setValues(values);
   }

   public Dict(Token token, java.util.List keys, java.util.List values) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.keys = keys;
      if (keys == null) {
         this.keys = new ArrayList();
      }

      Iterator var4 = this.keys.iterator();

      PythonTree t;
      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      var4 = this.values.iterator();

      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public Dict(Integer ttype, Token token, java.util.List keys, java.util.List values) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.keys = keys;
      if (keys == null) {
         this.keys = new ArrayList();
      }

      Iterator var5 = this.keys.iterator();

      PythonTree t;
      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      var5 = this.values.iterator();

      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public Dict(PythonTree tree, java.util.List keys, java.util.List values) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.keys = keys;
      if (keys == null) {
         this.keys = new ArrayList();
      }

      Iterator var4 = this.keys.iterator();

      PythonTree t;
      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      var4 = this.values.iterator();

      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "Dict";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Dict(");
      sb.append("keys=");
      sb.append(this.dumpThis(this.keys));
      sb.append(",");
      sb.append("values=");
      sb.append(this.dumpThis(this.values));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitDict(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      Iterator var2;
      PythonTree t;
      if (this.keys != null) {
         var2 = this.keys.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

      if (this.values != null) {
         var2 = this.values.iterator();

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
      PyType.addBuilder(Dict.class, new PyExposer());
      TYPE = PyType.fromClass(Dict.class);
      fields = new PyString[]{new PyString("keys"), new PyString("values")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Dict___init___exposer extends PyBuiltinMethod {
      public Dict___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Dict___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Dict___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Dict)this.self).Dict___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Dict)var1).toString();
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
         return Py.newInteger(((Dict)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Dict)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class keys_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public keys_descriptor() {
         super("keys", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Dict)var1).getKeys();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Dict)var1).setKeys((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class values_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public values_descriptor() {
         super("values", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Dict)var1).getValues();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Dict)var1).setValues((PyObject)var2);
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
         return Py.newInteger(((Dict)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Dict)var1).setCol_offset((Integer)var2);
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
         return ((Dict)var1).getDict();
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
         return ((Dict)var1).get_fields();
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
         return ((Dict)var1).get_attributes();
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
         Dict var4 = new Dict(this.for_type);
         if (var1) {
            var4.Dict___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new DictDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Dict___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new keys_descriptor(), new values_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Dict", Dict.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
