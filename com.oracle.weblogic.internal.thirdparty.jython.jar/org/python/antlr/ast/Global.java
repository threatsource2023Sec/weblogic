package org.python.antlr.ast;

import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
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
   name = "_ast.Global",
   base = stmt.class
)
public class Global extends stmt {
   public static final PyType TYPE;
   private java.util.List names;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;
   private java.util.List nameNodes;

   public java.util.List getInternalNames() {
      return this.names;
   }

   public PyObject getNames() {
      return new AstList(this.names, AstAdapters.identifierAdapter);
   }

   public void setNames(PyObject names) {
      this.names = AstAdapters.py2identifierList(names);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Global(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Global() {
      this(TYPE);
   }

   @ExposedNew
   public void Global___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Global", args, keywords, new String[]{"names", "lineno", "col_offset"}, 1, true);
      this.setNames(ap.getPyObject(0, Py.None));
      int lin = ap.getInt(1, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(2, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Global(PyObject names) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setNames(names);
   }

   public Global(Token token, java.util.List names) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.names = names;
   }

   public Global(Integer ttype, Token token, java.util.List names) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.names = names;
   }

   public Global(PythonTree tree, java.util.List names) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.names = names;
   }

   public String toString() {
      return "Global";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Global(");
      sb.append("names=");
      sb.append(this.dumpThis(this.names));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitGlobal(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
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

   public java.util.List getInternalNameNodes() {
      return this.nameNodes;
   }

   public Global(Token token, java.util.List names, java.util.List nameNodes) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.names = names;
      this.nameNodes = nameNodes;
   }

   static {
      PyType.addBuilder(Global.class, new PyExposer());
      TYPE = PyType.fromClass(Global.class);
      fields = new PyString[]{new PyString("names")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Global___init___exposer extends PyBuiltinMethod {
      public Global___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Global___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Global___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Global)this.self).Global___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Global)var1).toString();
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

   private static class names_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public names_descriptor() {
         super("names", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Global)var1).getNames();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Global)var1).setNames((PyObject)var2);
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
         return Py.newInteger(((Global)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Global)var1).setLineno((Integer)var2);
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
         return Py.newInteger(((Global)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Global)var1).setCol_offset((Integer)var2);
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
         return ((Global)var1).getDict();
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
         return ((Global)var1).get_fields();
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
         return ((Global)var1).get_attributes();
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
         Global var4 = new Global(this.for_type);
         if (var1) {
            var4.Global___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new GlobalDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Global___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new names_descriptor(), new lineno_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Global", Global.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
