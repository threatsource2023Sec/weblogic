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
   name = "_ast.Attribute",
   base = expr.class
)
public class Attribute extends expr implements Context {
   public static final PyType TYPE;
   private expr value;
   private String attr;
   private expr_contextType ctx;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;
   private Name attrName;

   public expr getInternalValue() {
      return this.value;
   }

   public PyObject getValue() {
      return this.value;
   }

   public void setValue(PyObject value) {
      this.value = AstAdapters.py2expr(value);
   }

   public String getInternalAttr() {
      return this.attr;
   }

   public PyObject getAttr() {
      return (PyObject)(this.attr == null ? Py.None : new PyString(this.attr));
   }

   public void setAttr(PyObject attr) {
      this.attr = AstAdapters.py2identifier(attr);
   }

   public expr_contextType getInternalCtx() {
      return this.ctx;
   }

   public PyObject getCtx() {
      return AstAdapters.expr_context2py(this.ctx);
   }

   public void setCtx(PyObject ctx) {
      this.ctx = AstAdapters.py2expr_context(ctx);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Attribute(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Attribute() {
      this(TYPE);
   }

   @ExposedNew
   public void Attribute___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Attribute", args, keywords, new String[]{"value", "attr", "ctx", "lineno", "col_offset"}, 3, true);
      this.setValue(ap.getPyObject(0, Py.None));
      this.setAttr(ap.getPyObject(1, Py.None));
      this.setCtx(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Attribute(PyObject value, PyObject attr, PyObject ctx) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setValue(value);
      this.setAttr(attr);
      this.setCtx(ctx);
   }

   public Attribute(Token token, expr value, String attr, expr_contextType ctx) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.value = value;
      this.addChild(value);
      this.attr = attr;
      this.ctx = ctx;
   }

   public Attribute(Integer ttype, Token token, expr value, String attr, expr_contextType ctx) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.value = value;
      this.addChild(value);
      this.attr = attr;
      this.ctx = ctx;
   }

   public Attribute(PythonTree tree, expr value, String attr, expr_contextType ctx) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.value = value;
      this.addChild(value);
      this.attr = attr;
      this.ctx = ctx;
   }

   public String toString() {
      return "Attribute";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Attribute(");
      sb.append("value=");
      sb.append(this.dumpThis(this.value));
      sb.append(",");
      sb.append("attr=");
      sb.append(this.dumpThis(this.attr));
      sb.append(",");
      sb.append("ctx=");
      sb.append(this.dumpThis(this.ctx));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitAttribute(this);
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

   public void setContext(expr_contextType c) {
      this.ctx = c;
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

   public Name getInternalAttrName() {
      return this.attrName;
   }

   public Attribute(Token token, expr value, Name attr, expr_contextType ctx) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.value = value;
      this.addChild(value);
      this.attr = attr.getText();
      this.attrName = attr;
      this.ctx = ctx;
   }

   public Attribute(Integer ttype, Token token, expr value, Name attr, expr_contextType ctx) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.value = value;
      this.addChild(value);
      this.attr = attr.getText();
      this.attrName = attr;
      this.ctx = ctx;
   }

   static {
      PyType.addBuilder(Attribute.class, new PyExposer());
      TYPE = PyType.fromClass(Attribute.class);
      fields = new PyString[]{new PyString("value"), new PyString("attr"), new PyString("ctx")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Attribute___init___exposer extends PyBuiltinMethod {
      public Attribute___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Attribute___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Attribute___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Attribute)this.self).Attribute___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Attribute)var1).toString();
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
         return Py.newInteger(((Attribute)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Attribute)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class ctx_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public ctx_descriptor() {
         super("ctx", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Attribute)var1).getCtx();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Attribute)var1).setCtx((PyObject)var2);
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
         return Py.newInteger(((Attribute)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Attribute)var1).setCol_offset((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class attr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public attr_descriptor() {
         super("attr", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Attribute)var1).getAttr();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Attribute)var1).setAttr((PyObject)var2);
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
         return ((Attribute)var1).getDict();
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
         return ((Attribute)var1).getValue();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Attribute)var1).setValue((PyObject)var2);
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
         return ((Attribute)var1).get_fields();
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
         return ((Attribute)var1).get_attributes();
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
         Attribute var4 = new Attribute(this.for_type);
         if (var1) {
            var4.Attribute___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new AttributeDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Attribute___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new ctx_descriptor(), new col_offset_descriptor(), new attr_descriptor(), new __dict___descriptor(), new value_descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Attribute", Attribute.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
