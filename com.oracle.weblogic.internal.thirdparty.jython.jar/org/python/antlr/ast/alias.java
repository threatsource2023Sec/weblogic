package org.python.antlr.ast;

import java.util.Arrays;
import org.python.antlr.AST;
import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
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
   name = "_ast.alias",
   base = AST.class
)
public class alias extends PythonTree {
   public static final PyType TYPE;
   private String name;
   private String asname;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private java.util.List nameNodes;
   private Name asnameNode;

   public String getInternalName() {
      return this.name;
   }

   public PyObject getName() {
      return (PyObject)(this.name == null ? Py.None : new PyString(this.name));
   }

   public void setName(PyObject name) {
      this.name = AstAdapters.py2identifier(name);
   }

   public String getInternalAsname() {
      return this.asname;
   }

   public PyObject getAsname() {
      return (PyObject)(this.asname == null ? Py.None : new PyString(this.asname));
   }

   public void setAsname(PyObject asname) {
      this.asname = AstAdapters.py2identifier(asname);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public alias(PyType subType) {
      super(subType);
   }

   public alias() {
      this(TYPE);
   }

   @ExposedNew
   public void alias___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("alias", args, keywords, new String[]{"name", "asname"}, 2, true);
      this.setName(ap.getPyObject(0, Py.None));
      this.setAsname(ap.getPyObject(1, Py.None));
   }

   public alias(PyObject name, PyObject asname) {
      this.setName(name);
      this.setAsname(asname);
   }

   public alias(Token token, String name, String asname) {
      super(token);
      this.name = name;
      this.asname = asname;
   }

   public alias(Integer ttype, Token token, String name, String asname) {
      super(ttype, token);
      this.name = name;
      this.asname = asname;
   }

   public alias(PythonTree tree, String name, String asname) {
      super(tree);
      this.name = name;
      this.asname = asname;
   }

   public String toString() {
      return "alias";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("alias(");
      sb.append("name=");
      sb.append(this.dumpThis(this.name));
      sb.append(",");
      sb.append("asname=");
      sb.append(this.dumpThis(this.asname));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      this.traverse(visitor);
      return null;
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

   public java.util.List getInternalNameNodes() {
      return this.nameNodes;
   }

   public Name getInternalAsnameNode() {
      return this.asnameNode;
   }

   public alias(Name name, Name asname) {
      this(Arrays.asList(name), asname);
   }

   public alias(java.util.List nameNodes, Name asname) {
      this.nameNodes = nameNodes;
      this.name = dottedNameListToString(nameNodes);
      if (asname != null) {
         this.asnameNode = asname;
         this.asname = asname.getInternalId();
      }

   }

   static {
      PyType.addBuilder(alias.class, new PyExposer());
      TYPE = PyType.fromClass(alias.class);
      fields = new PyString[]{new PyString("name"), new PyString("asname")};
      attributes = new PyString[0];
   }

   private static class alias___init___exposer extends PyBuiltinMethod {
      public alias___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public alias___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new alias___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((alias)this.self).alias___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((alias)var1).toString();
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

   private static class asname_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public asname_descriptor() {
         super("asname", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((alias)var1).getAsname();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((alias)var1).setAsname((PyObject)var2);
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
         return ((alias)var1).getName();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((alias)var1).setName((PyObject)var2);
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
         return ((alias)var1).getDict();
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
         return ((alias)var1).get_fields();
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
         return ((alias)var1).get_attributes();
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
         alias var4 = new alias(this.for_type);
         if (var1) {
            var4.alias___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new aliasDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new alias___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new asname_descriptor(), new name_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.alias", alias.class, AST.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
