package org.python.antlr.ast;

import java.util.ArrayList;
import java.util.Iterator;
import org.python.antlr.AST;
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
import org.python.core.Visitproc;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedNew;
import org.python.expose.ExposedType;

@ExposedType(
   name = "_ast.comprehension",
   base = AST.class
)
public class comprehension extends PythonTree {
   public static final PyType TYPE;
   private expr target;
   private expr iter;
   private java.util.List ifs;
   private static final PyString[] fields;
   private static final PyString[] attributes;
   public PyObject __dict__;

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

   public java.util.List getInternalIfs() {
      return this.ifs;
   }

   public PyObject getIfs() {
      return new AstList(this.ifs, AstAdapters.exprAdapter);
   }

   public void setIfs(PyObject ifs) {
      this.ifs = AstAdapters.py2exprList(ifs);
   }

   public PyString[] get_fields() {
      return fields;
   }

   public PyString[] get_attributes() {
      return attributes;
   }

   public comprehension(PyType subType) {
      super(subType);
   }

   public comprehension() {
      this(TYPE);
   }

   @ExposedNew
   public void comprehension___init__(PyObject[] args, String[] keywords) {
      ArgParser ap = new ArgParser("comprehension", args, keywords, new String[]{"target", "iter", "ifs"}, 3, true);
      this.setTarget(ap.getPyObject(0, Py.None));
      this.setIter(ap.getPyObject(1, Py.None));
      this.setIfs(ap.getPyObject(2, Py.None));
   }

   public comprehension(PyObject target, PyObject iter, PyObject ifs) {
      this.setTarget(target);
      this.setIter(iter);
      this.setIfs(ifs);
   }

   public comprehension(Token token, expr target, expr iter, java.util.List ifs) {
      super(token);
      this.target = target;
      this.addChild(target);
      this.iter = iter;
      this.addChild(iter);
      this.ifs = ifs;
      if (ifs == null) {
         this.ifs = new ArrayList();
      }

      Iterator var5 = this.ifs.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public comprehension(Integer ttype, Token token, expr target, expr iter, java.util.List ifs) {
      super(ttype, token);
      this.target = target;
      this.addChild(target);
      this.iter = iter;
      this.addChild(iter);
      this.ifs = ifs;
      if (ifs == null) {
         this.ifs = new ArrayList();
      }

      Iterator var6 = this.ifs.iterator();

      while(var6.hasNext()) {
         PythonTree t = (PythonTree)var6.next();
         this.addChild(t);
      }

   }

   public comprehension(PythonTree tree, expr target, expr iter, java.util.List ifs) {
      super(tree);
      this.target = target;
      this.addChild(target);
      this.iter = iter;
      this.addChild(iter);
      this.ifs = ifs;
      if (ifs == null) {
         this.ifs = new ArrayList();
      }

      Iterator var5 = this.ifs.iterator();

      while(var5.hasNext()) {
         PythonTree t = (PythonTree)var5.next();
         this.addChild(t);
      }

   }

   public String toString() {
      return "comprehension";
   }

   public String toStringTree() {
      StringBuffer sb = new StringBuffer("comprehension(");
      sb.append("target=");
      sb.append(this.dumpThis(this.target));
      sb.append(",");
      sb.append("iter=");
      sb.append(this.dumpThis(this.iter));
      sb.append(",");
      sb.append("ifs=");
      sb.append(this.dumpThis(this.ifs));
      sb.append(",");
      sb.append(")");
      return sb.toString();
   }

   public Object accept(VisitorIF visitor) throws Exception {
      this.traverse(visitor);
      return null;
   }

   public void traverse(VisitorIF visitor) throws Exception {
      if (this.target != null) {
         this.target.accept(visitor);
      }

      if (this.iter != null) {
         this.iter.accept(visitor);
      }

      if (this.ifs != null) {
         Iterator var2 = this.ifs.iterator();

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

   public int traverse(Visitproc visit, Object arg) {
      int retVal = super.traverse(visit, arg);
      if (retVal != 0) {
         return retVal;
      } else {
         if (this.iter != null) {
            retVal = visit.visit(this.iter, arg);
            if (retVal != 0) {
               return retVal;
            }
         }

         if (this.ifs != null) {
            Iterator var4 = this.ifs.iterator();

            while(var4.hasNext()) {
               PyObject ob = (PyObject)var4.next();
               if (ob != null) {
                  retVal = visit.visit(ob, arg);
                  if (retVal != 0) {
                     return retVal;
                  }
               }
            }
         }

         return this.target != null ? visit.visit(this.target, arg) : 0;
      }
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob == null) {
         return false;
      } else if (this.ifs != null && this.ifs.contains(ob)) {
         return true;
      } else {
         return ob == this.iter || ob == this.target || super.refersDirectlyTo(ob);
      }
   }

   static {
      PyType.addBuilder(comprehension.class, new PyExposer());
      TYPE = PyType.fromClass(comprehension.class);
      fields = new PyString[]{new PyString("target"), new PyString("iter"), new PyString("ifs")};
      attributes = new PyString[0];
   }

   private static class comprehension___init___exposer extends PyBuiltinMethod {
      public comprehension___init___exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public comprehension___init___exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new comprehension___init___exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         ((comprehension)this.self).comprehension___init__(var1, var2);
         return Py.None;
      }
   }

   private static class repr_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public repr_descriptor() {
         super("repr", String.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         String var10000 = ((comprehension)var1).toString();
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

   private static class ifs_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public ifs_descriptor() {
         super("ifs", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((comprehension)var1).getIfs();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((comprehension)var1).setIfs((PyObject)var2);
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
         return ((comprehension)var1).getIter();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((comprehension)var1).setIter((PyObject)var2);
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
         return ((comprehension)var1).getDict();
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
         return ((comprehension)var1).get_fields();
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
         return ((comprehension)var1).get_attributes();
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
         return ((comprehension)var1).getTarget();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public void invokeSet(PyObject var1, Object var2) {
         ((comprehension)var1).setTarget((PyObject)var2);
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
         comprehension var4 = new comprehension(this.for_type);
         if (var1) {
            var4.comprehension___init__(var2, var3);
         }

         return var4;
      }

      public PyObject createOfSubtype(PyType var1) {
         return new comprehensionDerived(var1);
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new comprehension___init___exposer("__init__")};
         PyDataDescr[] var2 = new PyDataDescr[]{new repr_descriptor(), new ifs_descriptor(), new iter_descriptor(), new __dict___descriptor(), new _fields_descriptor(), new _attributes_descriptor(), new target_descriptor()};
         super("_ast.comprehension", comprehension.class, AST.class, (boolean)1, (String)null, var1, var2, new exposed___new__());
      }
   }
}
