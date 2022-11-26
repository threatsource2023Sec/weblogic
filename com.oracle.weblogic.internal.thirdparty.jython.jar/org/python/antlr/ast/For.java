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
   name = "_ast.For",
   base = stmt.class
)
public class For extends stmt {
   public static final PyType TYPE;
   private expr target;
   private expr iter;
   private java.util.List body;
   private java.util.List orelse;
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

   public expr getInternalIter() {
      return this.iter;
   }

   public PyObject getIter() {
      return this.iter;
   }

   public void setIter(PyObject iter) {
      this.iter = AstAdapters.py2expr(iter);
   }

   public java.util.List getInternalBody() {
      return this.body;
   }

   public PyObject getBody() {
      return new AstList(this.body, AstAdapters.stmtAdapter);
   }

   public void setBody(PyObject body) {
      this.body = AstAdapters.py2stmtList(body);
   }

   public java.util.List getInternalOrelse() {
      return this.orelse;
   }

   public PyObject getOrelse() {
      return new AstList(this.orelse, AstAdapters.stmtAdapter);
   }

   public void setOrelse(PyObject orelse) {
      this.orelse = AstAdapters.py2stmtList(orelse);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public For(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public For() {
      this(TYPE);
   }

   @ExposedNew
   public void For___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("For", args, keywords, new String[]{"target", "iter", "body", "orelse", "lineno", "col_offset"}, 4, true);
      this.setTarget(ap.getPyObject(0, Py.None));
      this.setIter(ap.getPyObject(1, Py.None));
      this.setBody(ap.getPyObject(2, Py.None));
      this.setOrelse(ap.getPyObject(3, Py.None));
      int lin = ap.getInt(4, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(5, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public For(PyObject target, PyObject iter, PyObject body, PyObject orelse) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setTarget(target);
      this.setIter(iter);
      this.setBody(body);
      this.setOrelse(orelse);
   }

   public For(Token token, expr target, expr iter, java.util.List body, java.util.List orelse) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.target = target;
      this.addChild(target);
      this.iter = iter;
      this.addChild(iter);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var6 = this.body.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.orelse = orelse;
      if (orelse == null) {
         this.orelse = new ArrayList();
      }

      var6 = this.orelse.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public For(Integer ttype, Token token, expr target, expr iter, java.util.List body, java.util.List orelse) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.target = target;
      this.addChild(target);
      this.iter = iter;
      this.addChild(iter);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var7 = this.body.iterator();

      PythonTree t;
      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

      this.orelse = orelse;
      if (orelse == null) {
         this.orelse = new ArrayList();
      }

      var7 = this.orelse.iterator();

      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

   }

   public For(PythonTree tree, expr target, expr iter, java.util.List body, java.util.List orelse) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.target = target;
      this.addChild(target);
      this.iter = iter;
      this.addChild(iter);
      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      Iterator var6 = this.body.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.orelse = orelse;
      if (orelse == null) {
         this.orelse = new ArrayList();
      }

      var6 = this.orelse.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "For";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("For(");
      sb.append("target=");
      sb.append(this.dumpThis(this.target));
      sb.append(",");
      sb.append("iter=");
      sb.append(this.dumpThis(this.iter));
      sb.append(",");
      sb.append("body=");
      sb.append(this.dumpThis(this.body));
      sb.append(",");
      sb.append("orelse=");
      sb.append(this.dumpThis(this.orelse));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitFor(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.target != null) {
         this.target.accept(visitor);
      }

      if (this.iter != null) {
         this.iter.accept(visitor);
      }

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

      if (this.orelse != null) {
         var2 = this.orelse.iterator();

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
      PyType.addBuilder(For.class, new PyExposer());
      TYPE = PyType.fromClass(For.class);
      fields = new PyString[]{new PyString("target"), new PyString("iter"), new PyString("body"), new PyString("orelse")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class For___init___exposer extends PyBuiltinMethod {
      public For___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public For___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new For___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((For)this.self).For___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((For)var1).toString();
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

   private static class orelse_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public orelse_descriptor() {
         super("orelse", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((For)var1).getOrelse();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((For)var1).setOrelse((PyObject)var2);
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
         return Py.newInteger(((For)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((For)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class iter_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public iter_descriptor() {
         super("iter", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((For)var1).getIter();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((For)var1).setIter((PyObject)var2);
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
         return Py.newInteger(((For)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((For)var1).setCol_offset((Integer)var2);
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
         return ((For)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((For)var1).setBody((PyObject)var2);
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
         return ((For)var1).getDict();
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
         return ((For)var1).get_fields();
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
         return ((For)var1).get_attributes();
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
         return ((For)var1).getTarget();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((For)var1).setTarget((PyObject)var2);
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
         For var4 = new For(this.for_type);
         if (var1) {
            var4.For___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new ForDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new For___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new orelse_descriptor(), new lineno_descriptor(), new iter_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor(), new target_descriptor()};
         super("_ast.For", For.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
