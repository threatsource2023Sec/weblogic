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
   name = "_ast.Compare",
   base = expr.class
)
public class Compare extends expr {
   public static final PyType TYPE;
   private expr left;
   private java.util.List ops;
   private java.util.List comparators;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;
   private int lineno;
   private int col_offset;

   public expr getInternalLeft() {
      return this.left;
   }

   public PyObject getLeft() {
      return this.left;
   }

   public void setLeft(PyObject left) {
      this.left = AstAdapters.py2expr(left);
   }

   public java.util.List getInternalOps() {
      return this.ops;
   }

   public PyObject getOps() {
      return new AstList(this.ops, AstAdapters.cmpopAdapter);
   }

   public void setOps(PyObject ops) {
      this.ops = AstAdapters.py2cmpopList(ops);
   }

   public java.util.List getInternalComparators() {
      return this.comparators;
   }

   public PyObject getComparators() {
      return new AstList(this.comparators, AstAdapters.exprAdapter);
   }

   public void setComparators(PyObject comparators) {
      this.comparators = AstAdapters.py2exprList(comparators);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public Compare(PyType subType) {
      super(subType);
      this.lineno = -1;
      this.col_offset = -1;
   }

   public Compare() {
      this(TYPE);
   }

   @ExposedNew
   public void Compare___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("Compare", args, keywords, new String[]{"left", "ops", "comparators", "lineno", "col_offset"}, 3, true);
      this.setLeft(ap.getPyObject(0, Py.None));
      this.setOps(ap.getPyObject(1, Py.None));
      this.setComparators(ap.getPyObject(2, Py.None));
      int lin = ap.getInt(3, -1);
      if (lin != -1) {
         this.setLineno(lin);
      }

      int col = ap.getInt(4, -1);
      if (col != -1) {
         this.setLineno(col);
      }

   }

   public Compare(PyObject left, PyObject ops, PyObject comparators) {
      this.lineno = -1;
      this.col_offset = -1;
      this.setLeft(left);
      this.setOps(ops);
      this.setComparators(comparators);
   }

   public Compare(Token token, expr left, java.util.List ops, java.util.List comparators) {
      super(token);
      this.lineno = -1;
      this.col_offset = -1;
      this.left = left;
      this.addChild(left);
      this.ops = ops;
      this.comparators = comparators;
      if (comparators == null) {
         this.comparators = new ArrayList();
      }

      Iterator var5 = this.comparators.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public Compare(Integer ttype, Token token, expr left, java.util.List ops, java.util.List comparators) {
      super(ttype, token);
      this.lineno = -1;
      this.col_offset = -1;
      this.left = left;
      this.addChild(left);
      this.ops = ops;
      this.comparators = comparators;
      if (comparators == null) {
         this.comparators = new ArrayList();
      }

      Iterator var6 = this.comparators.iterator();

      while(var6.hasNext()) {
         PythonTree t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public Compare(PythonTree tree, expr left, java.util.List ops, java.util.List comparators) {
      super(tree);
      this.lineno = -1;
      this.col_offset = -1;
      this.left = left;
      this.addChild(left);
      this.ops = ops;
      this.comparators = comparators;
      if (comparators == null) {
         this.comparators = new ArrayList();
      }

      Iterator var5 = this.comparators.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "Compare";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("Compare(");
      sb.append("left=");
      sb.append(this.dumpThis(this.left));
      sb.append(",");
      sb.append("ops=");
      sb.append(this.dumpThis(this.ops));
      sb.append(",");
      sb.append("comparators=");
      sb.append(this.dumpThis(this.comparators));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      return visitor.visitCompare(this);
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.left != null) {
         this.left.accept(visitor);
      }

      if (this.comparators != null) {
         Iterator var2 = this.comparators.iterator();

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
      PyType.addBuilder(Compare.class, new PyExposer());
      TYPE = PyType.fromClass(Compare.class);
      fields = new PyString[]{new PyString("left"), new PyString("ops"), new PyString("comparators")};
      attributes = new PyString[]{new PyString("lineno"), new PyString("col_offset")};
   }

   private static class Compare___init___exposer extends PyBuiltinMethod {
      public Compare___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public Compare___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new Compare___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((Compare)this.self).Compare___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((Compare)var1).toString();
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

   private static class ops_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public ops_descriptor() {
         super("ops", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Compare)var1).getOps();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Compare)var1).setOps((PyObject)var2);
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
         return Py.newInteger(((Compare)var1).getLineno());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Compare)var1).setLineno((Integer)var2);
      }

      public boolean implementsDescrSet() {
         return true;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class left_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public left_descriptor() {
         super("left", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Compare)var1).getLeft();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Compare)var1).setLeft((PyObject)var2);
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
         return Py.newInteger(((Compare)var1).getCol_offset());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Compare)var1).setCol_offset((Integer)var2);
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
         return ((Compare)var1).getDict();
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
         return ((Compare)var1).get_fields();
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
         return ((Compare)var1).get_attributes();
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

   private static class comparators_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public comparators_descriptor() {
         super("comparators", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((Compare)var1).getComparators();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((Compare)var1).setComparators((PyObject)var2);
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
         Compare var4 = new Compare(this.for_type);
         if (var1) {
            var4.Compare___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new CompareDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new Compare___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new ops_descriptor(), new lineno_descriptor(), new left_descriptor(), new col_offset_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor(), new comparators_descriptor()};
         super("_ast.Compare", Compare.class, expr.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
