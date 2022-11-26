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
   name = "_ast.Raise",
   base = stmt.class
)
public class Raise extends stmt {
   public static final PyType TYPE;
   private expr type;
   private expr inst;
   private expr tback;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalType() {
      return this.type;
   }

   public PyObject getExceptType() {
      return this.type;
   }

   public void setExceptType(PyObject type) {
      this.type = AstAdapters.py2expr(type);
   }

   public expr getInternalInst() {
      return this.inst;
   }

   public PyObject getInst() {
      return this.inst;
   }

   public void setInst(PyObject inst) {
      this.inst = AstAdapters.py2expr(inst);
   }

   public expr getInternalTback() {
      return this.tback;
   }

   public PyObject getTback() {
      return this.tback;
   }

   public void setTback(PyObject tback) {
      this.tback = AstAdapters.py2expr(tback);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Raise(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Raise() {
      this(TYPE);
   }

   @ExposedNew
   public void Raise___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Raise", args, keywords, new String[]{"type", "inst", "tback", "lineno", "col_offset"}, 3, true);
      this.setExceptType(ap.getPyObject(0, Py.None));
      this.setInst(ap.getPyObject(1, Py.None));
      this.setTback(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Raise(PyObject type, PyObject inst, PyObject tback) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setExceptType(type);
      this.setInst(inst);
      this.setTback(tback);
   }

   public Raise(Token token, expr type, expr inst, expr tback) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.type = type;
      this.addChild(type);
      this.inst = inst;
      this.addChild(inst);
      this.tback = tback;
      this.addChild(tback);
   }

   public Raise(Integer ttype, Token token, expr type, expr inst, expr tback) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.type = type;
      this.addChild(type);
      this.inst = inst;
      this.addChild(inst);
      this.tback = tback;
      this.addChild(tback);
   }

   public Raise(PythonTree tree, expr type, expr inst, expr tback) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.type = type;
      this.addChild(type);
      this.inst = inst;
      this.addChild(inst);
      this.tback = tback;
      this.addChild(tback);
   }

   public String toString() {
      return "Raise";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Raise(");
      sb.append("type=");
      sb.append(this.dumpThis(this.type));
      sb.append(",");
      sb.append("inst=");
      sb.append(this.dumpThis(this.inst));
      sb.append(",");
      sb.append("tback=");
      sb.append(this.dumpThis(this.tback));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitRaise(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.type != null) {
         this.type.accept(visitor);
      }

      if (this.inst != null) {
         this.inst.accept(visitor);
      }

      if (this.tback != null) {
         this.tback.accept(visitor);
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
      PyType.addBuilder(Raise.class, new PyExposer());
      TYPE = PyType.fromClass(Raise.class);
      fields = new PyString[]{new PyString("type"), new PyString("inst"), new PyString("tback")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Raise___init___exposer extends PyBuiltinMethod {
      public Raise___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Raise___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Raise___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Raise)this.self).Raise___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Raise)var1).toString();
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
         return Py.newInteger(((Raise)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Raise)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class inst_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public inst_descriptor() {
         super("inst", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Raise)var1).getInst();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Raise)var1).setInst((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class tback_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public tback_descriptor() {
         super("tback", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Raise)var1).getTback();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Raise)var1).setTback((PyObject)var2);
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
         return Py.newInteger(((Raise)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Raise)var1).setCol_offset((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class type_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public type_descriptor() {
         super("type", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Raise)var1).getExceptType();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Raise)var1).setExceptType((PyObject)var2);
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
         return ((Raise)var1).getDict();
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
         return ((Raise)var1).get_fields();
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
         return ((Raise)var1).get_attributes();
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
         Raise var4 = new Raise(this.for_type);
         if (var1) {
            var4.Raise___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new RaiseDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Raise___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new inst_descriptor(), new tback_descriptor(), new col_offset_descriptor(), new type_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Raise", Raise.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
