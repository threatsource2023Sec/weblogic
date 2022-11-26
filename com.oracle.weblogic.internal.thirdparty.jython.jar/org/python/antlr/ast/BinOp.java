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
   name = "_ast.BinOp",
   base = expr.class
)
public class BinOp extends expr {
   public static final PyType TYPE;
   private expr left;
   private operatorType op;
   private expr right;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalLeft() {
      return this.left;
   }

   public PyObject getLeft() {
      return this.left;
   }

   public void setLeft(PyObject left) {
      this.left = AstAdapters.py2expr(left);
   }

   public operatorType getInternalOp() {
      return this.op;
   }

   public PyObject getOp() {
      return AstAdapters.operator2py(this.op);
   }

   public void setOp(PyObject op) {
      this.op = AstAdapters.py2operator(op);
   }

   public expr getInternalRight() {
      return this.right;
   }

   public PyObject getRight() {
      return this.right;
   }

   public void setRight(PyObject right) {
      this.right = AstAdapters.py2expr(right);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public BinOp(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public BinOp() {
      this(TYPE);
   }

   @ExposedNew
   public void BinOp___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("BinOp", args, keywords, new String[]{"left", "op", "right", "lineno", "col_offset"}, 3, true);
      this.setLeft(ap.getPyObject(0, Py.None));
      this.setOp(ap.getPyObject(1, Py.None));
      this.setRight(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public BinOp(PyObject left, PyObject op, PyObject right) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setLeft(left);
      this.setOp(op);
      this.setRight(right);
   }

   public BinOp(Token token, expr left, operatorType op, expr right) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.left = left;
      this.addChild(left);
      this.op = op;
      this.right = right;
      this.addChild(right);
   }

   public BinOp(Integer ttype, Token token, expr left, operatorType op, expr right) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.left = left;
      this.addChild(left);
      this.op = op;
      this.right = right;
      this.addChild(right);
   }

   public BinOp(PythonTree tree, expr left, operatorType op, expr right) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.left = left;
      this.addChild(left);
      this.op = op;
      this.right = right;
      this.addChild(right);
   }

   public String toString() {
      return "BinOp";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("BinOp(");
      sb.append("left=");
      sb.append(this.dumpThis(this.left));
      sb.append(",");
      sb.append("op=");
      sb.append(this.dumpThis(this.op));
      sb.append(",");
      sb.append("right=");
      sb.append(this.dumpThis(this.right));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitBinOp(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.left != null) {
         this.left.accept(visitor);
      }

      if (this.right != null) {
         this.right.accept(visitor);
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
      PyType.addBuilder(BinOp.class, new PyExposer());
      TYPE = PyType.fromClass(BinOp.class);
      fields = new PyString[]{new PyString("left"), new PyString("op"), new PyString("right")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class BinOp___init___exposer extends PyBuiltinMethod {
      public BinOp___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public BinOp___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new BinOp___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((BinOp)this.self).BinOp___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((BinOp)var1).toString();
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
         return ((BinOp)var1).getOp();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BinOp)var1).setOp((PyObject)var2);
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
         return Py.newInteger(((BinOp)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BinOp)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class left_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public left_descriptor() {
         super("left", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((BinOp)var1).getLeft();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BinOp)var1).setLeft((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class right_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public right_descriptor() {
         super("right", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((BinOp)var1).getRight();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BinOp)var1).setRight((PyObject)var2);
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
         return Py.newInteger(((BinOp)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((BinOp)var1).setCol_offset((Integer)var2);
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
         return ((BinOp)var1).getDict();
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
         return ((BinOp)var1).get_fields();
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
         return ((BinOp)var1).get_attributes();
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
         BinOp var4 = new BinOp(this.for_type);
         if (var1) {
            var4.BinOp___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new BinOpDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new BinOp___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new op_descriptor(), new lineno_descriptor(), new left_descriptor(), new right_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.BinOp", BinOp.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
