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
   name = "_ast.UnaryOp",
   base = expr.class
)
public class UnaryOp extends expr {
   public static final PyType TYPE;
   private unaryopType op;
   private expr operand;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public unaryopType getInternalOp() {
      return this.op;
   }

   public PyObject getOp() {
      return AstAdapters.unaryop2py(this.op);
   }

   public void setOp(PyObject op) {
      this.op = AstAdapters.py2unaryop(op);
   }

   public expr getInternalOperand() {
      return this.operand;
   }

   public PyObject getOperand() {
      return this.operand;
   }

   public void setOperand(PyObject operand) {
      this.operand = AstAdapters.py2expr(operand);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public UnaryOp(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public UnaryOp() {
      this(TYPE);
   }

   @ExposedNew
   public void UnaryOp___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("UnaryOp", args, keywords, new String[]{"op", "operand", "lineno", "col_offset"}, 2, true);
      this.setOp(ap.getPyObject(0, Py.None));
      this.setOperand(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public UnaryOp(PyObject op, PyObject operand) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setOp(op);
      this.setOperand(operand);
   }

   public UnaryOp(Token token, unaryopType op, expr operand) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.op = op;
      this.operand = operand;
      this.addChild(operand);
   }

   public UnaryOp(Integer ttype, Token token, unaryopType op, expr operand) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.op = op;
      this.operand = operand;
      this.addChild(operand);
   }

   public UnaryOp(PythonTree tree, unaryopType op, expr operand) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.op = op;
      this.operand = operand;
      this.addChild(operand);
   }

   public String toString() {
      return "UnaryOp";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("UnaryOp(");
      sb.append("op=");
      sb.append(this.dumpThis(this.op));
      sb.append(",");
      sb.append("operand=");
      sb.append(this.dumpThis(this.operand));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitUnaryOp(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.operand != null) {
         this.operand.accept(visitor);
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
      PyType.addBuilder(UnaryOp.class, new PyExposer());
      TYPE = PyType.fromClass(UnaryOp.class);
      fields = new PyString[]{new PyString("op"), new PyString("operand")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class UnaryOp___init___exposer extends PyBuiltinMethod {
      public UnaryOp___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public UnaryOp___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new UnaryOp___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((UnaryOp)this.self).UnaryOp___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((UnaryOp)var1).toString();
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
         return ((UnaryOp)var1).getOp();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((UnaryOp)var1).setOp((PyObject)var2);
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
         return Py.newInteger(((UnaryOp)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((UnaryOp)var1).setLineno((Integer)var2);
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
         return Py.newInteger(((UnaryOp)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((UnaryOp)var1).setCol_offset((Integer)var2);
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
         return ((UnaryOp)var1).getDict();
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
         return ((UnaryOp)var1).get_fields();
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
         return ((UnaryOp)var1).get_attributes();
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

   private static class operand_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public operand_descriptor() {
         super("operand", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((UnaryOp)var1).getOperand();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((UnaryOp)var1).setOperand((PyObject)var2);
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
         UnaryOp var4 = new UnaryOp(this.for_type);
         if (var1) {
            var4.UnaryOp___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new UnaryOpDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new UnaryOp___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new op_descriptor(), new lineno_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor(), new operand_descriptor()};
         super("_ast.UnaryOp", UnaryOp.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
