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
   name = "_ast.Assign",
   base = stmt.class
)
public class Assign extends stmt {
   public static final PyType TYPE;
   private java.util.List targets;
   private expr value;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public java.util.List getInternalTargets() {
      return this.targets;
   }

   public PyObject getTargets() {
      return new AstList(this.targets, AstAdapters.exprAdapter);
   }

   public void setTargets(PyObject targets) {
      this.targets = AstAdapters.py2exprList(targets);
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

   public Assign(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Assign() {
      this(TYPE);
   }

   @ExposedNew
   public void Assign___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Assign", args, keywords, new String[]{"targets", "value", "lineno", "col_offset"}, 2, true);
      this.setTargets(ap.getPyObject(0, Py.None));
      this.setValue(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Assign(PyObject targets, PyObject value) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setTargets(targets);
      this.setValue(value);
   }

   public Assign(Token token, java.util.List targets, expr value) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.targets = targets;
      if (targets == null) {
         this.targets = new ArrayList();
      }

      Iterator var4 = this.targets.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

      this.value = value;
      this.addChild(value);
   }

   public Assign(Integer ttype, Token token, java.util.List targets, expr value) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.targets = targets;
      if (targets == null) {
         this.targets = new ArrayList();
      }

      Iterator var5 = this.targets.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.value = value;
      this.addChild(value);
   }

   public Assign(PythonTree tree, java.util.List targets, expr value) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.targets = targets;
      if (targets == null) {
         this.targets = new ArrayList();
      }

      Iterator var4 = this.targets.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

      this.value = value;
      this.addChild(value);
   }

   public String toString() {
      return "Assign";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Assign(");
      sb.append("targets=");
      sb.append(this.dumpThis(this.targets));
      sb.append(",");
      sb.append("value=");
      sb.append(this.dumpThis(this.value));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitAssign(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.targets != null) {
         Iterator var2 = this.targets.iterator();

         while(var2.hasNext()) {
            PythonTree t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
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
      PyType.addBuilder(Assign.class, new PyExposer());
      TYPE = PyType.fromClass(Assign.class);
      fields = new PyString[]{new PyString("targets"), new PyString("value")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Assign___init___exposer extends PyBuiltinMethod {
      public Assign___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Assign___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Assign___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Assign)this.self).Assign___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Assign)var1).toString();
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
         return Py.newInteger(((Assign)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assign)var1).setLineno((Integer)var2);
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
         return Py.newInteger(((Assign)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assign)var1).setCol_offset((Integer)var2);
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
         return ((Assign)var1).getDict();
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

   private static class targets_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public targets_descriptor() {
         super("targets", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Assign)var1).getTargets();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assign)var1).setTargets((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
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
         return ((Assign)var1).getValue();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Assign)var1).setValue((PyObject)var2);
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
         return ((Assign)var1).get_fields();
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
         return ((Assign)var1).get_attributes();
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
         Assign var4 = new Assign(this.for_type);
         if (var1) {
            var4.Assign___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new AssignDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Assign___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new targets_descriptor(), new value_descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Assign", Assign.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
