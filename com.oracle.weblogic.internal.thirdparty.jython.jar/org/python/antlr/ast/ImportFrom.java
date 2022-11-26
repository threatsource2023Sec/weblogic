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
   name = "_ast.ImportFrom",
   base = stmt.class
)
public class ImportFrom extends stmt {
   public static final PyType TYPE;
   private String module;
   private java.util.List names;
   private Integer level;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;
   private java.util.List moduleNames;

   public String getInternalModule() {
      return this.module;
   }

   public PyObject getModule() {
      return (PyObject)(this.module == null ? Py.None : new PyString(this.module));
   }

   public void setModule(PyObject module) {
      this.module = AstAdapters.py2identifier(module);
   }

   public java.util.List getInternalNames() {
      return this.names;
   }

   public PyObject getNames() {
      return new AstList(this.names, AstAdapters.aliasAdapter);
   }

   public void setNames(PyObject names) {
      this.names = AstAdapters.py2aliasList(names);
   }

   public Integer getInternalLevel() {
      return this.level;
   }

   public PyObject getLevel() {
      return Py.newInteger(this.level);
   }

   public void setLevel(PyObject level) {
      this.level = AstAdapters.py2int(level);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public ImportFrom(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public ImportFrom() {
      this(TYPE);
   }

   @ExposedNew
   public void ImportFrom___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("ImportFrom", args, keywords, new String[]{"module", "names", "level", "lineno", "col_offset"}, 3, true);
      this.setModule(ap.getPyObject(0, Py.None));
      this.setNames(ap.getPyObject(1, Py.None));
      this.setLevel(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public ImportFrom(PyObject module, PyObject names, PyObject level) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setModule(module);
      this.setNames(names);
      this.setLevel(level);
   }

   public ImportFrom(Token token, String module, java.util.List names, Integer level) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.module = module;
      this.names = names;
      if (names == null) {
         this.names = new ArrayList();
      }

      Iterator var5 = this.names.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.level = level;
   }

   public ImportFrom(Integer ttype, Token token, String module, java.util.List names, Integer level) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.module = module;
      this.names = names;
      if (names == null) {
         this.names = new ArrayList();
      }

      Iterator var6 = this.names.iterator();

      while(var6.hasNext()) {
         PythonTree t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.level = level;
   }

   public ImportFrom(PythonTree tree, String module, java.util.List names, Integer level) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.module = module;
      this.names = names;
      if (names == null) {
         this.names = new ArrayList();
      }

      Iterator var5 = this.names.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

      this.level = level;
   }

   public String toString() {
      return "ImportFrom";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("ImportFrom(");
      sb.append("module=");
      sb.append(this.dumpThis(this.module));
      sb.append(",");
      sb.append("names=");
      sb.append(this.dumpThis(this.names));
      sb.append(",");
      sb.append("level=");
      sb.append(this.dumpThis(this.level));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitImportFrom(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.names != null) {
         Iterator var2 = this.names.iterator();

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

   public java.util.List getInternalModuleNames() {
      return this.moduleNames;
   }

   public ImportFrom(Token token, String module, java.util.List moduleNames, java.util.List names, Integer level) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.module = module;
      this.names = names;
      if (names == null) {
         this.names = new ArrayList();
      }

      Iterator var6 = this.names.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.moduleNames = moduleNames;
      if (moduleNames == null) {
         this.moduleNames = new ArrayList();
      }

      var6 = this.moduleNames.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.level = level;
   }

   static {
      PyType.addBuilder(ImportFrom.class, new PyExposer());
      TYPE = PyType.fromClass(ImportFrom.class);
      fields = new PyString[]{new PyString("module"), new PyString("names"), new PyString("level")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class ImportFrom___init___exposer extends PyBuiltinMethod {
      public ImportFrom___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public ImportFrom___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new ImportFrom___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((ImportFrom)this.self).ImportFrom___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((ImportFrom)var1).toString();
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
         return ((ImportFrom)var1).getNames();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ImportFrom)var1).setNames((PyObject)var2);
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
         return Py.newInteger(((ImportFrom)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ImportFrom)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class level_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public level_descriptor() {
         super("level", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((ImportFrom)var1).getLevel();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ImportFrom)var1).setLevel((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class module_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public module_descriptor() {
         super("module", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((ImportFrom)var1).getModule();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ImportFrom)var1).setModule((PyObject)var2);
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
         return Py.newInteger(((ImportFrom)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((ImportFrom)var1).setCol_offset((Integer)var2);
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
         return ((ImportFrom)var1).getDict();
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
         return ((ImportFrom)var1).get_fields();
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
         return ((ImportFrom)var1).get_attributes();
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
         ImportFrom var4 = new ImportFrom(this.for_type);
         if (var1) {
            var4.ImportFrom___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new ImportFromDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new ImportFrom___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new names_descriptor(), new lineno_descriptor(), new level_descriptor(), new module_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.ImportFrom", ImportFrom.class, stmt.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
