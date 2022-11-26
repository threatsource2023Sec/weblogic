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
   name = "_ast.GeneratorExp",
   base = expr.class
)
public class GeneratorExp extends expr {
   public static final PyType TYPE;
   private expr elt;
   private java.util.List generators;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalElt() {
      return this.elt;
   }

   public PyObject getElt() {
      return this.elt;
   }

   public void setElt(PyObject elt) {
      this.elt = AstAdapters.py2expr(elt);
   }

   public java.util.List getInternalGenerators() {
      return this.generators;
   }

   public PyObject getGenerators() {
      return new AstList(this.generators, AstAdapters.comprehensionAdapter);
   }

   public void setGenerators(PyObject generators) {
      this.generators = AstAdapters.py2comprehensionList(generators);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public GeneratorExp(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public GeneratorExp() {
      this(TYPE);
   }

   @ExposedNew
   public void GeneratorExp___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("GeneratorExp", args, keywords, new String[]{"elt", "generators", "lineno", "col_offset"}, 2, true);
      this.setElt(ap.getPyObject(0, Py.None));
      this.setGenerators(ap.getPyObject(1, Py.None));
      int lin = ap.getInt(2, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(3, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public GeneratorExp(PyObject elt, PyObject generators) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setElt(elt);
      this.setGenerators(generators);
   }

   public GeneratorExp(Token token, expr elt, java.util.List generators) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.elt = elt;
      this.addChild(elt);
      this.generators = generators;
      if (generators == null) {
         this.generators = new ArrayList();
      }

      Iterator var4 = this.generators.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public GeneratorExp(Integer ttype, Token token, expr elt, java.util.List generators) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.elt = elt;
      this.addChild(elt);
      this.generators = generators;
      if (generators == null) {
         this.generators = new ArrayList();
      }

      Iterator var5 = this.generators.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public GeneratorExp(PythonTree tree, expr elt, java.util.List generators) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.elt = elt;
      this.addChild(elt);
      this.generators = generators;
      if (generators == null) {
         this.generators = new ArrayList();
      }

      Iterator var4 = this.generators.iterator();

      while(var4.hasNext()) {
         PythonTree t = (PythonTree)var4.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "GeneratorExp";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("GeneratorExp(");
      sb.append("elt=");
      sb.append(this.dumpThis(this.elt));
      sb.append(",");
      sb.append("generators=");
      sb.append(this.dumpThis(this.generators));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitGeneratorExp(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.elt != null) {
         this.elt.accept(visitor);
      }

      if (this.generators != null) {
         Iterator var2 = this.generators.iterator();

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
      PyType.addBuilder(GeneratorExp.class, new PyExposer());
      TYPE = PyType.fromClass(GeneratorExp.class);
      fields = new PyString[]{new PyString("elt"), new PyString("generators")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class GeneratorExp___init___exposer extends PyBuiltinMethod {
      public GeneratorExp___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public GeneratorExp___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new GeneratorExp___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((GeneratorExp)this.self).GeneratorExp___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((GeneratorExp)var1).toString();
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
         return Py.newInteger(((GeneratorExp)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((GeneratorExp)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class generators_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public generators_descriptor() {
         super("generators", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((GeneratorExp)var1).getGenerators();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((GeneratorExp)var1).setGenerators((PyObject)var2);
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
         return Py.newInteger(((GeneratorExp)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((GeneratorExp)var1).setCol_offset((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class elt_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public elt_descriptor() {
         super("elt", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((GeneratorExp)var1).getElt();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((GeneratorExp)var1).setElt((PyObject)var2);
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
         return ((GeneratorExp)var1).getDict();
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
         return ((GeneratorExp)var1).get_fields();
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
         return ((GeneratorExp)var1).get_attributes();
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
         GeneratorExp var4 = new GeneratorExp(this.for_type);
         if (var1) {
            var4.GeneratorExp___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new GeneratorExpDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new GeneratorExp___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lineno_descriptor(), new generators_descriptor(), new col_offset_descriptor(), new elt_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.GeneratorExp", GeneratorExp.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
