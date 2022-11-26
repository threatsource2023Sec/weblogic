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
   name = "_ast.AugAssign",
   base = stmt.class
)
public class AugAssign extends stmt {
   public static final PyType TYPE;
   private expr target;
   private operatorType op;
   private expr value;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalTarget() {
      return this.target;
   }

   public PyObject getTarget() {
      return this.target;
   }

   public void setTarget(PyObject target) {
      this.target = AstAdapters.py2expr(target);
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

   public AugAssign(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public AugAssign() {
      this(TYPE);
   }

   @ExposedNew
   public void AugAssign___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("AugAssign", args, keywords, new String[]{"target", "op", "value", "lineno", "col_offset"}, 3, true);
      this.setTarget(ap.getPyObject(0, Py.None));
      this.setOp(ap.getPyObject(1, Py.None));
      this.setValue(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public AugAssign(PyObject target, PyObject op, PyObject value) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setTarget(target);
      this.setOp(op);
      this.setValue(value);
   }

   public AugAssign(Token token, expr target, operatorType op, expr value) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.target = target;
      this.addChild(target);
      this.op = op;
      this.value = value;
      this.addChild(value);
   }

   public AugAssign(Integer ttype, Token token, expr target, operatorType op, expr value) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.target = target;
      this.addChild(target);
      this.op = op;
      this.value = value;
      this.addChild(value);
   }

   public AugAssign(PythonTree tree, expr target, operatorType op, expr value) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.target = target;
      this.addChild(target);
      this.op = op;
      this.value = value;
      this.addChild(value);
   }

   public String toString() {
      return "AugAssign";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("AugAssign(");
      sb.append("target=");
      sb.append(this.dumpThis(this.target));
      sb.append(",");
      sb.append("op=");
      sb.append(this.dumpThis(this.op));
      sb.append(",");
      sb.append("value=");
      sb.append(this.dumpThis(this.value));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitAugAssign(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.target != null) {
         this.target.accept(visitor);
      }

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
      PyType.addBuilder(AugAssign.class, new PyExposer());
      TYPE = PyType.fromClass(AugAssign.class);
      fields = new PyString[]{new PyString("target"), new PyString("op"), new PyString("value")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class AugAssign___init___exposer extends PyBuiltinMethod {
      public AugAssign___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public AugAssign___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new AugAssign___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((AugAssign)this.self).AugAssign___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((AugAssign)var1).toString();
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
         return ((AugAssign)var1).getOp();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((AugAssign)var1).setOp((PyObject)var2);
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
         return Py.newInteger(((AugAssign)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((AugAssign)var1).setLineno((Integer)var2);
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
         return Py.newInteger(((AugAssign)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((AugAssign)var1).setCol_offset((Integer)var2);
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
         return ((AugAssign)var1).getDict();
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
         return ((AugAssign)var1).getValue();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((AugAssign)var1).setValue((PyObject)var2);
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
         return ((AugAssign)var1).get_fields();
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
         return ((AugAssign)var1).get_attributes();
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

   private static class target_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public target_descriptor() {
         super("target", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((AugAssign)var1).getTarget();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((AugAssign)var1).setTarget((PyObject)var2);
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
         AugAssign var4 = new AugAssign(this.for_type);
         if (var1) {
            var4.AugAssign___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new AugAssignDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new AugAssign___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new op_descriptor(), new lineno_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new value_descriptor(), new _fields_descriptor(), new _attributes_descriptor(), new target_descriptor()};
         super("_ast.AugAssign", AugAssign.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
