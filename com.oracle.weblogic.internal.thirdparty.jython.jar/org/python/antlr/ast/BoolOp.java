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
   name = "_ast.BoolOp",
   base = expr.class
)
public class BoolOp extends expr {
   public static final PyType TYPE;
   private boolopType op;
   private java.util.List values;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public boolopType getInternalOp() {
      return this.op;
   }

   public PyObject getOp() {
      return AstAdapters.boolop2py(this.op);
   }

   public void setOp(PyObject op) {
      this.op = AstAdapters.py2boolop(op);
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

   public BoolOp(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public BoolOp() {
      this(TYPE);
   }

   @ExposedNew
   public void BoolOp___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("BoolOp", args, keywords, new String[]{"op", "values", "lineno", "col_offset"}, 2, true);
      this.setOp(ap.getPyObject(0, Py.None));
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

   public BoolOp(PyObject op, PyObject values) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setOp(op);
      this.setValues(values);
   }

   public BoolOp(Token token, boolopType op, java.util.List values) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.op = op;
      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      Iterator var4 = this.values.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public BoolOp(Integer ttype, Token token, boolopType op, java.util.List values) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.op = op;
      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      Iterator var5 = this.values.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public BoolOp(PythonTree tree, boolopType op, java.util.List values) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.op = op;
      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      Iterator var4 = this.values.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "BoolOp";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("BoolOp(");
      sb.append("op=");
      sb.append(this.dumpThis(this.op));
      sb.append(",");
      sb.append("values=");
      sb.append(this.dumpThis(this.values));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitBoolOp(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.values != null) {
         Iterator var2 = this.values.iterator();

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
      PyType.addBuilder(BoolOp.class, new PyExposer());
      TYPE = PyType.fromClass(BoolOp.class);
      fields = new PyString[]{new PyString("op"), new PyString("values")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class BoolOp___init___exposer extends PyBuiltinMethod {
      public BoolOp___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BoolOp___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BoolOp___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((BoolOp)this.self).BoolOp___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((BoolOp)var1).toString();
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

   private static class op_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public op_descriptor() {
         super("op", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((BoolOp)var1).getOp();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BoolOp)var1).setOp((PyObject)var2);
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
         return Py.newInteger(((BoolOp)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BoolOp)var1).setLineno((Integer)var2);
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
         return ((BoolOp)var1).getValues();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BoolOp)var1).setValues((PyObject)var2);
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
         return Py.newInteger(((BoolOp)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BoolOp)var1).setCol_offset((Integer)var2);
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
         return ((BoolOp)var1).getDict();
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
         return ((BoolOp)var1).get_fields();
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
         return ((BoolOp)var1).get_attributes();
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
         BoolOp var4 = new BoolOp(this.for_type);
         if (var1) {
            var4.BoolOp___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new BoolOpDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new BoolOp___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new op_descriptor(), new lineno_descriptor(), new values_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.BoolOp", BoolOp.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
