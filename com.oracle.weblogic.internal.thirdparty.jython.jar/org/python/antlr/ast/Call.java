package org.python.antlr.ast;

import java.util.ArrayList;
import java.util.Iterator;
import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.expr;
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
   name = "_ast.Call",
   base = expr.class
)
public class Call extends expr {
   public static final PyType TYPE;
   private expr func;
   private java.util.List args;
   private java.util.List keywords;
   private expr starargs;
   private expr kwargs;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalFunc() {
      return this.func;
   }

   public PyObject getFunc() {
      return this.func;
   }

   public void setFunc(PyObject func) {
      this.func = AstAdapters.py2expr(func);
   }

   public java.util.List getInternalArgs() {
      return this.args;
   }

   public PyObject getArgs() {
      return new AstList(this.args, AstAdapters.exprAdapter);
   }

   public void setArgs(PyObject args) {
      this.args = AstAdapters.py2exprList(args);
   }

   public java.util.List getInternalKeywords() {
      return this.keywords;
   }

   public PyObject getKeywords() {
      return new AstList(this.keywords, AstAdapters.keywordAdapter);
   }

   public void setKeywords(PyObject keywords) {
      this.keywords = AstAdapters.py2keywordList(keywords);
   }

   public expr getInternalStarargs() {
      return this.starargs;
   }

   public PyObject getStarargs() {
      return this.starargs;
   }

   public void setStarargs(PyObject starargs) {
      this.starargs = AstAdapters.py2expr(starargs);
   }

   public expr getInternalKwargs() {
      return this.kwargs;
   }

   public PyObject getKwargs() {
      return this.kwargs;
   }

   public void setKwargs(PyObject kwargs) {
      this.kwargs = AstAdapters.py2expr(kwargs);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Call(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Call() {
      this(TYPE);
   }

   @ExposedNew
   public void Call___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Call", args, keywords, new String[]{"func", "args", "keywords", "starargs", "kwargs", "lineno", "col_offset"}, 5, true);
      this.setFunc(ap.getPyObject(0, Py.None));
      this.setArgs(ap.getPyObject(1, Py.None));
      this.setKeywords(ap.getPyObject(2, Py.None));
      this.setStarargs(ap.getPyObject(3, Py.None));
      this.setKwargs(ap.getPyObject(4, Py.None));
      int lin = ap.getInt(5, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(6, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Call(PyObject func, PyObject args, PyObject keywords, PyObject starargs, PyObject kwargs) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setFunc(func);
      this.setArgs(args);
      this.setKeywords(keywords);
      this.setStarargs(starargs);
      this.setKwargs(kwargs);
   }

   public Call(Token token, expr func, java.util.List args, java.util.List keywords, expr starargs, expr kwargs) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.func = func;
      this.addChild(func);
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

      this.keywords = keywords;
      if (keywords == null) {
         this.keywords = new ArrayList();
      }

      var7 = this.keywords.iterator();

      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

      this.starargs = starargs;
      this.addChild(starargs);
      this.kwargs = kwargs;
      this.addChild(kwargs);
   }

   public Call(Integer ttype, Token token, expr func, java.util.List args, java.util.List keywords, expr starargs, expr kwargs) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.func = func;
      this.addChild(func);
      this.args = args;
      if (args == null) {
         this.args = new ArrayList();
      }

      Iterator var8 = this.args.iterator();

      PythonTree t;
      while(var8.hasNext()) {
         t = (PythonTree)var8.next();
         this.addChild(t);
      }

      this.keywords = keywords;
      if (keywords == null) {
         this.keywords = new ArrayList();
      }

      var8 = this.keywords.iterator();

      while(var8.hasNext()) {
         t = (PythonTree)var8.next();
         this.addChild(t);
      }

      this.starargs = starargs;
      this.addChild(starargs);
      this.kwargs = kwargs;
      this.addChild(kwargs);
   }

   public Call(PythonTree tree, expr func, java.util.List args, java.util.List keywords, expr starargs, expr kwargs) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.func = func;
      this.addChild(func);
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

      this.keywords = keywords;
      if (keywords == null) {
         this.keywords = new ArrayList();
      }

      var7 = this.keywords.iterator();

      while(var7.hasNext()) {
         t = (PythonTree)var7.next();
         this.addChild(t);
      }

      this.starargs = starargs;
      this.addChild(starargs);
      this.kwargs = kwargs;
      this.addChild(kwargs);
   }

   public String toString() {
      return "Call";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Call(");
      sb.append("func=");
      sb.append(this.dumpThis(this.func));
      sb.append(",");
      sb.append("args=");
      sb.append(this.dumpThis(this.args));
      sb.append(",");
      sb.append("keywords=");
      sb.append(this.dumpThis(this.keywords));
      sb.append(",");
      sb.append("starargs=");
      sb.append(this.dumpThis(this.starargs));
      sb.append(",");
      sb.append("kwargs=");
      sb.append(this.dumpThis(this.kwargs));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitCall(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.func != null) {
         this.func.accept(visitor);
      }

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

      if (this.keywords != null) {
         var2 = this.keywords.iterator();

         while(var2.hasNext()) {
            t = (PythonTree)var2.next();
            if (t != null) {
               t.accept(visitor);
            }
         }
      }

      if (this.starargs != null) {
         this.starargs.accept(visitor);
      }

      if (this.kwargs != null) {
         this.kwargs.accept(visitor);
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
      PyType.addBuilder(Call.class, new PyExposer());
      TYPE = PyType.fromClass(Call.class);
      fields = new PyString[]{new PyString("func"), new PyString("args"), new PyString("keywords"), new PyString("starargs"), new PyString("kwargs")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Call___init___exposer extends PyBuiltinMethod {
      public Call___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Call___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Call___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Call)this.self).Call___init__(var1, var2);
         return Py.None;
      }
   }

   private static class args_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public args_descriptor() {
         super("args", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Call)var1).getArgs();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Call)var1).setArgs((PyObject)var2);
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
         String var10000 = ((Call)var1).toString();
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
         return Py.newInteger(((Call)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Call)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class func_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public func_descriptor() {
         super("func", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Call)var1).getFunc();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Call)var1).setFunc((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class keywords_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public keywords_descriptor() {
         super("keywords", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Call)var1).getKeywords();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Call)var1).setKeywords((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class kwargs_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public kwargs_descriptor() {
         super("kwargs", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Call)var1).getKwargs();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Call)var1).setKwargs((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class starargs_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public starargs_descriptor() {
         super("starargs", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Call)var1).getStarargs();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Call)var1).setStarargs((PyObject)var2);
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
         return Py.newInteger(((Call)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Call)var1).setCol_offset((Integer)var2);
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
         return ((Call)var1).getDict();
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
         return ((Call)var1).get_fields();
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
         return ((Call)var1).get_attributes();
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
         Call var4 = new Call(this.for_type);
         if (var1) {
            var4.Call___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new CallDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Call___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new args_descriptor(), new repr_descriptor(), new lineno_descriptor(), new func_descriptor(), new keywords_descriptor(), new kwargs_descriptor(), new starargs_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Call", Call.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
