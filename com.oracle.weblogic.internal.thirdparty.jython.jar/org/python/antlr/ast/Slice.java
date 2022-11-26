package org.python.antlr.ast;

import org.python.antlr.PythonTree;
import org.python.antlr.adapter.AstAdapters;
import org.python.antlr.base.expr;
import org.python.antlr.base.slice;
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
   name = "_ast.Slice",
   base = slice.class
)
public class Slice extends slice {
   public static final PyType TYPE;
   private expr lower;
   private expr upper;
   private expr step;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;

   public expr getInternalLower() {
      return this.lower;
   }

   public PyObject getLower() {
      return this.lower;
   }

   public void setLower(PyObject lower) {
      this.lower = AstAdapters.py2expr(lower);
   }

   public expr getInternalUpper() {
      return this.upper;
   }

   public PyObject getUpper() {
      return this.upper;
   }

   public void setUpper(PyObject upper) {
      this.upper = AstAdapters.py2expr(upper);
   }

   public expr getInternalStep() {
      return this.step;
   }

   public PyObject getStep() {
      return this.step;
   }

   public void setStep(PyObject step) {
      this.step = AstAdapters.py2expr(step);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Slice(PyType subType) {
      super(subType);
   }

   public Slice() {
      this(TYPE);
   }

   @ExposedNew
   public void Slice___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Slice", args, keywords, new String[]{"lower", "upper", "step"}, 3, true);
      this.setLower(ap.getPyObject(0, Py.None));
      this.setUpper(ap.getPyObject(1, Py.None));
      this.setStep(ap.getPyObject(2, Py.None));
   }

   public Slice(PyObject lower, PyObject upper, PyObject step) {
      this.setLower(lower);
      this.setUpper(upper);
      this.setStep(step);
   }

   public Slice(Token token, expr lower, expr upper, expr step) {
      super(token);
      this.lower = lower;
      this.addChild(lower);
      this.upper = upper;
      this.addChild(upper);
      this.step = step;
      this.addChild(step);
   }

   public Slice(Integer ttype, Token token, expr lower, expr upper, expr step) {
      super(ttype, token);
      this.lower = lower;
      this.addChild(lower);
      this.upper = upper;
      this.addChild(upper);
      this.step = step;
      this.addChild(step);
   }

   public Slice(PythonTree tree, expr lower, expr upper, expr step) {
      super(tree);
      this.lower = lower;
      this.addChild(lower);
      this.upper = upper;
      this.addChild(upper);
      this.step = step;
      this.addChild(step);
   }

   public String toString() {
      return "Slice";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Slice(");
      sb.append("lower=");
      sb.append(this.dumpThis(this.lower));
      sb.append(",");
      sb.append("upper=");
      sb.append(this.dumpThis(this.upper));
      sb.append(",");
      sb.append("step=");
      sb.append(this.dumpThis(this.step));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitSlice(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.lower != null) {
         this.lower.accept(visitor);
      }

      if (this.upper != null) {
         this.upper.accept(visitor);
      }

      if (this.step != null) {
         this.step.accept(visitor);
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

   static {
      PyType.addBuilder(Slice.class, new PyExposer());
      TYPE = PyType.fromClass(Slice.class);
      fields = new PyString[]{new PyString("lower"), new PyString("upper"), new PyString("step")};
      attributes = new PyString[0];
   }

   private static class Slice___init___exposer extends PyBuiltinMethod {
      public Slice___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Slice___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Slice___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Slice)this.self).Slice___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Slice)var1).toString();
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

   private static class lower_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public lower_descriptor() {
         super("lower", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Slice)var1).getLower();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Slice)var1).setLower((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class upper_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public upper_descriptor() {
         super("upper", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Slice)var1).getUpper();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Slice)var1).setUpper((PyObject)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class step_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public step_descriptor() {
         super("step", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Slice)var1).getStep();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Slice)var1).setStep((PyObject)var2);
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
         return ((Slice)var1).getDict();
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
         return ((Slice)var1).get_fields();
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
         return ((Slice)var1).get_attributes();
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
         Slice var4 = new Slice(this.for_type);
         if (var1) {
            var4.Slice___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new SliceDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Slice___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new lower_descriptor(), new upper_descriptor(), new step_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor()};
         super("_ast.Slice", Slice.class, slice.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
