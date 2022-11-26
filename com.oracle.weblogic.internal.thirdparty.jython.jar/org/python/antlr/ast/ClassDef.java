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
   name = "_ast.ClassDef",
   base = stmt.class
)
public class ClassDef extends stmt {
   public static final PyType TYPE;
   private String name;
   private java.util.List bases;
   private java.util.List body;
   private java.util.List decorator_list;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;
   private Name nameNode;

   public String getInternalName() {
      return this.name;
   }

   public PyObject getName() {
      return (PyObject)(this.name == null ? Py.None : new PyString(this.name));
   }

   public void setName(PyObject name) {
      this.name = AstAdapters.py2identifier(name);
   }

   public java.util.List getInternalBases() {
      return this.bases;
   }

   public PyObject getBases() {
      return new AstList(this.bases, AstAdapters.exprAdapter);
   }

   public void setBases(PyObject bases) {
      this.bases = AstAdapters.py2exprList(bases);
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

   public java.util.List getInternalDecorator_list() {
      return this.decorator_list;
   }

   public PyObject getDecorator_list() {
      return new AstList(this.decorator_list, AstAdapters.exprAdapter);
   }

   public void setDecorator_list(PyObject decorator_list) {
      this.decorator_list = AstAdapters.py2exprList(decorator_list);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public ClassDef(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public ClassDef() {
      this(TYPE);
   }

   @ExposedNew
   public void ClassDef___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("ClassDef", args, keywords, new String[]{"name", "bases", "body", "decorator_list", "lineno", "col_offset"}, 4, true);
      this.setName(ap.getPyObject(0, Py.None));
      this.setBases(ap.getPyObject(1, Py.None));
      this.setBody(ap.getPyObject(2, Py.None));
      this.setDecorator_list(ap.getPyObject(3, Py.None));
      int lin = ap.getInt(4, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(5, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public ClassDef(PyObject name, PyObject bases, PyObject body, PyObject decorator_list) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setName(name);
      this.setBases(bases);
      this.setBody(body);
      this.setDecorator_list(decorator_list);
   }

   public ClassDef(Token token, String name, java.util.List bases, java.util.List body, java.util.List decorator_list) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.name = name;
      this.bases = bases;
      if (bases == null) {
         this.bases = new ArrayList();
      }

      Iterator var6 = this.bases.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      var6 = this.body.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.decorator_list = decorator_list;
      if (decorator_list == null) {
         this.decorator_list = new ArrayList();
      }

      var6 = this.decorator_list.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public ClassDef(Integer ttype, Token token, String name, java.util.List bases, java.util.List body, java.util.List decorator_list) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.name = name;
      this.bases = bases;
      if (bases == null) {
         this.bases = new ArrayList();
      }

      Iterator var7 = this.bases.iterator();

      PythonTree t;
      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      var7 = this.body.iterator();

      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

      this.decorator_list = decorator_list;
      if (decorator_list == null) {
         this.decorator_list = new ArrayList();
      }

      var7 = this.decorator_list.iterator();

      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

   }

   public ClassDef(PythonTree tree, String name, java.util.List bases, java.util.List body, java.util.List decorator_list) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.name = name;
      this.bases = bases;
      if (bases == null) {
         this.bases = new ArrayList();
      }

      Iterator var6 = this.bases.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      var6 = this.body.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.decorator_list = decorator_list;
      if (decorator_list == null) {
         this.decorator_list = new ArrayList();
      }

      var6 = this.decorator_list.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "ClassDef";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("ClassDef(");
      sb.append("name=");
      sb.append(this.dumpThis(this.name));
      sb.append(",");
      sb.append("bases=");
      sb.append(this.dumpThis(this.bases));
      sb.append(",");
      sb.append("body=");
      sb.append(this.dumpThis(this.body));
      sb.append(",");
      sb.append("decorator_list=");
      sb.append(this.dumpThis(this.decorator_list));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitClassDef(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      Iterator var2;
      PythonTree t;
      if (this.bases != null) {
         var2 = this.bases.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

      if (this.body != null) {
         var2 = this.body.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

      if (this.decorator_list != null) {
         var2 = this.decorator_list.iterator();

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

   public Name getInternalNameNode() {
      return this.nameNode;
   }

   public ClassDef(Token token, Name name, java.util.List bases, java.util.List body, java.util.List decorator_list) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.name = name.getText();
      this.nameNode = name;
      this.bases = bases;
      if (bases == null) {
         this.bases = new ArrayList();
      }

      Iterator var6 = this.bases.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.body = body;
      if (body == null) {
         this.body = new ArrayList();
      }

      var6 = this.body.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.decorator_list = decorator_list;
      if (decorator_list == null) {
         this.decorator_list = new ArrayList();
      }

      var6 = this.decorator_list.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   static {
      PyType.addBuilder(ClassDef.class, new PyExposer());
      TYPE = PyType.fromClass(ClassDef.class);
      fields = new PyString[]{new PyString("name"), new PyString("bases"), new PyString("body"), new PyString("decorator_list")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class ClassDef___init___exposer extends PyBuiltinMethod {
      public ClassDef___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public ClassDef___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ClassDef___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((ClassDef)this.self).ClassDef___init__(var1, var2);
         return Py.None;
      }
   }

   private static class bases_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public bases_descriptor() {
         super("bases", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((ClassDef)var1).getBases();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ClassDef)var1).setBases((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((ClassDef)var1).toString();
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
         return Py.newInteger(((ClassDef)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ClassDef)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class decorator_list_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public decorator_list_descriptor() {
         super("decorator_list", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((ClassDef)var1).getDecorator_list();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ClassDef)var1).setDecorator_list((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class name_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public name_descriptor() {
         super("name", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((ClassDef)var1).getName();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ClassDef)var1).setName((PyObject)var2);
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
         return Py.newInteger(((ClassDef)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ClassDef)var1).setCol_offset((Integer)var2);
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
         return ((ClassDef)var1).getBody();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ClassDef)var1).setBody((PyObject)var2);
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
         return ((ClassDef)var1).getDict();
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
         return ((ClassDef)var1).get_fields();
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
         return ((ClassDef)var1).get_attributes();
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
         ClassDef var4 = new ClassDef(this.for_type);
         if (var1) {
            var4.ClassDef___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new ClassDefDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new ClassDef___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new bases_descriptor(), new repr_descriptor(), new lineno_descriptor(), new decorator_list_descriptor(), new name_descriptor(), new col_offset_descriptor(), new body_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.ClassDef", ClassDef.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
