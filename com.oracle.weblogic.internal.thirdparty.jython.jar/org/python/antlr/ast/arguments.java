package org.python.antlr.ast;

import java.util.ArrayList;
import java.util.Iterator;
import org.python.antlr.AST;
import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
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
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.arguments",
   base = AST.class
)
public class arguments extends PythonTree {
   public static final PyType TYPE;
   private java.util.List args;
   private String vararg;
   private String kwarg;
   private java.util.List defaults;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private Name varargName;
   private Name kwargName;

   public java.util.List getInternalArgs() {
      return this.args;
   }

   public PyObject getArgs() {
      return new AstList(this.args, AstAdapters.exprAdapter);
   }

   public void setArgs(PyObject args) {
      this.args = AstAdapters.py2exprList(args);
   }

   public String getInternalVararg() {
      return this.vararg;
   }

   public PyObject getVararg() {
      return (PyObject)(this.vararg == null ? Py.None : new PyString(this.vararg));
   }

   public void setVararg(PyObject vararg) {
      this.vararg = AstAdapters.py2identifier(vararg);
   }

   public String getInternalKwarg() {
      return this.kwarg;
   }

   public PyObject getKwarg() {
      return (PyObject)(this.kwarg == null ? Py.None : new PyString(this.kwarg));
   }

   public void setKwarg(PyObject kwarg) {
      this.kwarg = AstAdapters.py2identifier(kwarg);
   }

   public java.util.List getInternalDefaults() {
      return this.defaults;
   }

   public PyObject getDefaults() {
      return new AstList(this.defaults, AstAdapters.exprAdapter);
   }

   public void setDefaults(PyObject defaults) {
      this.defaults = AstAdapters.py2exprList(defaults);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public arguments(PyType subType) {
      super(subType);
   }

   public arguments() {
      this(TYPE);
   }

   @ExposedNew
   public void arguments___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("arguments", args, keywords, new String[]{"args", "vararg", "kwarg", "defaults"}, 4, true);
      this.setArgs(ap.getPyObject(0, Py.None));
      this.setVararg(ap.getPyObject(1, Py.None));
      this.setKwarg(ap.getPyObject(2, Py.None));
      this.setDefaults(ap.getPyObject(3, Py.None));
   }

   public arguments(PyObject args, PyObject vararg, PyObject kwarg, PyObject defaults) {
      this.setArgs(args);
      this.setVararg(vararg);
      this.setKwarg(kwarg);
      this.setDefaults(defaults);
   }

   public arguments(Token token, java.util.List args, String vararg, String kwarg, java.util.List defaults) {
      super(token);
      this.args = args;
      if (args == null) {
         this.args = new ArrayList();
      }

      Iterator var6 = this.args.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.vararg = vararg;
      this.kwarg = kwarg;
      this.defaults = defaults;
      if (defaults == null) {
         this.defaults = new ArrayList();
      }

      var6 = this.defaults.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public arguments(Integer ttype, Token token, java.util.List args, String vararg, String kwarg, java.util.List defaults) {
      super(ttype, token);
      this.args = args;
      if (args == null) {
         this.args = new ArrayList();
      }

      Iterator var7 = this.args.iterator();

      PythonTree t;
      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

      this.vararg = vararg;
      this.kwarg = kwarg;
      this.defaults = defaults;
      if (defaults == null) {
         this.defaults = new ArrayList();
      }

      var7 = this.defaults.iterator();

      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

   }

   public arguments(PythonTree tree, java.util.List args, String vararg, String kwarg, java.util.List defaults) {
      super(tree);
      this.args = args;
      if (args == null) {
         this.args = new ArrayList();
      }

      Iterator var6 = this.args.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.vararg = vararg;
      this.kwarg = kwarg;
      this.defaults = defaults;
      if (defaults == null) {
         this.defaults = new ArrayList();
      }

      var6 = this.defaults.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "arguments";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("arguments(");
      sb.append("args=");
      sb.append(this.dumpThis(this.args));
      sb.append(",");
      sb.append("vararg=");
      sb.append(this.dumpThis(this.vararg));
      sb.append(",");
      sb.append("kwarg=");
      sb.append(this.dumpThis(this.kwarg));
      sb.append(",");
      sb.append("defaults=");
      sb.append(this.dumpThis(this.defaults));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      this.traverse(visitor);
      return null;
   }

   public void traverse(VisitorIF visitor) throws Exception {
      Iterator var2;
      PythonTree t;
      if (this.args != null) {
         var2 = this.args.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

      if (this.defaults != null) {
         var2 = this.defaults.iterator();

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

   public Name getInternalVarargName() {
      return this.varargName;
   }

   public Name getInternalKwargName() {
      return this.kwargName;
   }

   public arguments(Token token, java.util.List args, Name vararg, Name kwarg, java.util.List defaults) {
      super(token);
      this.args = args;
      if (args == null) {
         this.args = new ArrayList();
      }

      Iterator var6 = this.args.iterator();

      PythonTree t;
      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

      this.vararg = vararg == null ? null : vararg.getText();
      this.varargName = vararg;
      this.kwarg = kwarg == null ? null : kwarg.getText();
      this.kwargName = kwarg;
      this.defaults = defaults;
      if (defaults == null) {
         this.defaults = new ArrayList();
      }

      var6 = this.defaults.iterator();

      while(var6.hasNext()) {
         t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         Iterator var4;
         PyObject ob;
         if (this.args != null) {
            var4 = this.args.iterator();

            while(var4.hasNext()) {
               ob = (PyObject)var4.next();
               if (ob != null) {
                  retVal = visit.visit(ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         if (this.defaults != null) {
            var4 = this.defaults.iterator();

            while(var4.hasNext()) {
               ob = (PyObject)var4.next();
               if (ob != null) {
                  retVal = visit.visit(ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         return 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else if (this.args != null && this.args.contains(ob)) {
         return true;
      } else {
         return this.defaults != null && this.defaults.contains(ob) ? true : super.refersDirectlyTo(ob);
      }
   }

   static {
      PyType.addBuilder(arguments.class, new PyExposer());
      TYPE = PyType.fromClass(arguments.class);
      fields = new PyString[]{new PyString("args"), new PyString("vararg"), new PyString("kwarg"), new PyString("defaults")};
      attributes = new PyString[0];
   }

   private static class arguments___init___exposer extends PyBuiltinMethod {
      public arguments___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public arguments___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new arguments___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((arguments)this.self).arguments___init__(var1, var2);
         return Py.None;
      }
   }

   private static class args_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public args_descriptor() {
         super("args", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((arguments)var1).getArgs();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((arguments)var1).setArgs((PyObject)var2);
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
         String var10000 = ((arguments)var1).toString();
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

   private static class kwarg_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public kwarg_descriptor() {
         super("kwarg", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((arguments)var1).getKwarg();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((arguments)var1).setKwarg((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class defaults_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public defaults_descriptor() {
         super("defaults", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((arguments)var1).getDefaults();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((arguments)var1).setDefaults((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class vararg_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public vararg_descriptor() {
         super("vararg", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((arguments)var1).getVararg();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((arguments)var1).setVararg((PyObject)var2);
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
         return ((arguments)var1).getDict();
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
         return ((arguments)var1).get_fields();
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
         return ((arguments)var1).get_attributes();
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
         arguments var4 = new arguments(this.for_type);
         if (var1) {
            var4.arguments___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new argumentsDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new arguments___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new args_descriptor(), new repr_descriptor(), new kwarg_descriptor(), new defaults_descriptor(), new vararg_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.arguments", arguments.class, AST.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
