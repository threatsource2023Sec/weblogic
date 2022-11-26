package org.python.antlr.ast;

import java.util.ArrayList;
import java.util.Iterator;
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
   name = "_ast.TryFinally",
   base = stmt.class
)
public class TryFinally extends stmt {
   public static final PyType TYPE;
   private java.util.List body;
   private java.util.List finalbody;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public java.util.List getInternalBody() {
      return this.body;
   }

   public PyObject getBody() {
      return new AstList(this.body, AstAdapters.stmtAdapter);
   }

   public void setBody(PyObject body) {
      this.body = AstAdapters.py2stmtList(body);
   }

   public java.util.List getInternalFinalbody() {
      return this.finalbody;
   }

   public PyObject getFinalbody() {
      return new AstList(this.finalbody, AstAdapters.stmtAdapter);
   }

   public void setFinalbody(PyObject finalbody) {
      this.finalbody = AstAdapters.py2stmtList(finalbody);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public TryFinally(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public TryFinally() {
      this(TYPE);
   }

   @ExposedNew
   public void TryFinally___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("TryFinally", args, keywords, new String[]{"body", "finalbody", "lineno", "col_offset"}, 2, true);
      this.setBody(ap.getPyObject(0, Py.None));
      this.setFinalbody(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public TryFinally(PyObject body, PyObject finalbody) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setBody(body);
      this.setFinalbody(finalbody);
   }

   public TryFinally(Token token, java.util.List body, java.util.List finalbody) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var4 = this.body.iterator();

      PythonTree t;
      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

      this.finalbody = finalbody;
      if (finalbody == null) {
         this.finalbody = new ArrayList();
      }

      var4 = this.finalbody.iterator();

      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public TryFinally(Integer ttype, Token token, java.util.List body, java.util.List finalbody) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var5 = this.body.iterator();

      PythonTree t;
      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.finalbody = finalbody;
      if (finalbody == null) {
         this.finalbody = new ArrayList();
      }

      var5 = this.finalbody.iterator();

      while(var5.hasNext()) {
         t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public TryFinally(PythonTree tree, java.util.List body, java.util.List finalbody) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var4 = this.body.iterator();

      PythonTree t;
      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

      this.finalbody = finalbody;
      if (finalbody == null) {
         this.finalbody = new ArrayList();
      }

      var4 = this.finalbody.iterator();

      while(var4.hasNext()) {
         t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "TryFinally";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("TryFinally(");
      sb.append("body=");
      sb.append(this.dumpThis(this.body));
      sb.append(",");
      sb.append("finalbody=");
      sb.append(this.dumpThis(this.finalbody));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitTryFinally(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      Iterator var2;
      PythonTree t;
      if (this.body != null) {
         var2 = this.body.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

      if (this.finalbody != null) {
         var2 = this.finalbody.iterator();

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
      PyType.addBuilder(TryFinally.class, new PyExposer());
      TYPE = PyType.fromClass(TryFinally.class);
      fields = new PyString[]{new PyString("body"), new PyString("finalbody")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class TryFinally___init___exposer extends PyBuiltinMethod {
      public TryFinally___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public TryFinally___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new TryFinally___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((TryFinally)this.self).TryFinally___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((TryFinally)var1).toString();
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
         return Py.newInteger(((TryFinally)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((TryFinally)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class finalbody_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public finalbody_descriptor() {
         super("finalbody", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((TryFinally)var1).getFinalbody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((TryFinally)var1).setFinalbody((PyObject)var2);
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
         return Py.newInteger(((TryFinally)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((TryFinally)var1).setCol_offset((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class body_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public body_descriptor() {
         super("body", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((TryFinally)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((TryFinally)var1).setBody((PyObject)var2);
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
         return ((TryFinally)var1).getDict();
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
         return ((TryFinally)var1).get_fields();
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
         return ((TryFinally)var1).get_attributes();
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
         TryFinally var4 = new TryFinally(this.for_type);
         if (var1) {
            var4.TryFinally___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new TryFinallyDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new TryFinally___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new finalbody_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.TryFinally", TryFinally.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
