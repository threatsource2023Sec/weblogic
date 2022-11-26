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
   name = "_ast.Print",
   base = stmt.class
)
public class Print extends stmt {
   public static final PyType TYPE;
   private expr dest;
   private java.util.List values;
   private Boolean nl;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalDest() {
      return this.dest;
   }

   public PyObject getDest() {
      return this.dest;
   }

   public void setDest(PyObject dest) {
      this.dest = AstAdapters.py2expr(dest);
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

   public Boolean getInternalNl() {
      return this.nl;
   }

   public PyObject getNl() {
      return this.nl ? Py.True : Py.False;
   }

   public void setNl(PyObject nl) {
      this.nl = AstAdapters.py2bool(nl);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Print(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Print() {
      this(TYPE);
   }

   @ExposedNew
   public void Print___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Print", args, keywords, new String[]{"dest", "values", "nl", "lineno", "col_offset"}, 3, true);
      this.setDest(ap.getPyObject(0, Py.None));
      this.setValues(ap.getPyObject(1, Py.None));
      this.setNl(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Print(PyObject dest, PyObject values, PyObject nl) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setDest(dest);
      this.setValues(values);
      this.setNl(nl);
   }

   public Print(Token token, expr dest, java.util.List values, Boolean nl) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.dest = dest;
      this.addChild(dest);
      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      Iterator var5 = this.values.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.nl = nl;
   }

   public Print(Integer ttype, Token token, expr dest, java.util.List values, Boolean nl) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.dest = dest;
      this.addChild(dest);
      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      Iterator var6 = this.values.iterator();

      while(var6.hasNext()) {
         PythonTree t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.nl = nl;
   }

   public Print(PythonTree tree, expr dest, java.util.List values, Boolean nl) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.dest = dest;
      this.addChild(dest);
      this.values = values;
      if (values == null) {
         this.values = new ArrayList();
      }

      Iterator var5 = this.values.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.nl = nl;
   }

   public String toString() {
      return "Print";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Print(");
      sb.append("dest=");
      sb.append(this.dumpThis(this.dest));
      sb.append(",");
      sb.append("values=");
      sb.append(this.dumpThis(this.values));
      sb.append(",");
      sb.append("nl=");
      sb.append(this.dumpThis(this.nl));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitPrint(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.dest != null) {
         this.dest.accept(visitor);
      }

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
      PyType.addBuilder(Print.class, new PyExposer());
      TYPE = PyType.fromClass(Print.class);
      fields = new PyString[]{new PyString("dest"), new PyString("values"), new PyString("nl")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Print___init___exposer extends PyBuiltinMethod {
      public Print___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Print___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Print___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Print)this.self).Print___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Print)var1).toString();
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
         return Py.newInteger(((Print)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Print)var1).setLineno((Integer)var2);
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
         return ((Print)var1).getValues();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Print)var1).setValues((PyObject)var2);
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
         return Py.newInteger(((Print)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Print)var1).setCol_offset((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class dest_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public dest_descriptor() {
         super("dest", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Print)var1).getDest();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Print)var1).setDest((PyObject)var2);
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
         return ((Print)var1).getDict();
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
         return ((Print)var1).get_fields();
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
         return ((Print)var1).get_attributes();
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

   private static class nl_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public nl_descriptor() {
         super("nl", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Print)var1).getNl();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Print)var1).setNl((PyObject)var2);
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
         Print var4 = new Print(this.for_type);
         if (var1) {
            var4.Print___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new PrintDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Print___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new values_descriptor(), new col_offset_descriptor(), new dest_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor(), new nl_descriptor()};
         super("_ast.Print", Print.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
