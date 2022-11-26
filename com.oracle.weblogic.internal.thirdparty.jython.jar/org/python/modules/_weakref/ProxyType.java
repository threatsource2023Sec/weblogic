package org.python.modules._weakref;

import org.python.core.PyBuiltinMethod;
import org.python.core.PyComplex;
import org.python.core.PyDataDescr;
import org.python.core.PyFloat;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyType;
import org.python.core.PyUnicode;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposedType;

@ExposedType(
   name = "weakproxy",
   isBaseType = false
)
public class ProxyType extends AbstractReference {
   public static final PyType TYPE;

   public ProxyType(PyType subType, ReferenceBackend ref, PyObject callback) {
      super(subType, ref, callback);
   }

   public ProxyType(ReferenceBackend ref, PyObject callback) {
      this(TYPE, ref, callback);
   }

   public boolean __nonzero__() {
      return this.py().__nonzero__();
   }

   public int __len__() {
      return this.py().__len__();
   }

   public PyObject __finditem__(PyObject key) {
      return this.py().__finditem__(key);
   }

   public void __setitem__(PyObject key, PyObject value) {
      this.py().__setitem__(key, value);
   }

   public void __delitem__(PyObject key) {
      this.py().__delitem__(key);
   }

   public PyObject __getslice__(PyObject start, PyObject stop, PyObject step) {
      return this.py().__getslice__(start, stop, step);
   }

   public void __setslice__(PyObject start, PyObject stop, PyObject step, PyObject value) {
      this.py().__setslice__(start, stop, step, value);
   }

   public void __delslice__(PyObject start, PyObject stop, PyObject step) {
      this.py().__delslice__(start, stop, step);
   }

   public PyObject __findattr_ex__(String name) {
      return this.py().__findattr_ex__(name);
   }

   public void __setattr__(String name, PyObject value) {
      this.py().__setattr__(name, value);
   }

   public void __delattr__(String name) {
      this.py().__delattr__(name);
   }

   public PyObject __iter__() {
      return this.py().__iter__();
   }

   public PyString __str__() {
      return this.py().__str__();
   }

   public PyUnicode __unicode__() {
      return this.py().__unicode__();
   }

   public PyString __hex__() {
      return this.py().__hex__();
   }

   public PyString __oct__() {
      return this.py().__oct__();
   }

   public PyObject __int__() {
      return this.py().__int__();
   }

   public PyFloat __float__() {
      return this.py().__float__();
   }

   public PyObject __long__() {
      return this.py().__long__();
   }

   public PyComplex __complex__() {
      return this.py().__complex__();
   }

   public PyObject __pos__() {
      return this.py().__pos__();
   }

   public PyObject __neg__() {
      return this.py().__neg__();
   }

   public PyObject __abs__() {
      return this.py().__abs__();
   }

   public PyObject __invert__() {
      return this.py().__invert__();
   }

   public boolean __contains__(PyObject o) {
      return this.py().__contains__(o);
   }

   public PyObject __index__() {
      return this.py().__index__();
   }

   public PyObject __add__(PyObject o) {
      return this.py().__add__(o);
   }

   public PyObject __radd__(PyObject o) {
      return this.py().__radd__(o);
   }

   public PyObject __iadd__(PyObject o) {
      return this.py().__iadd__(o);
   }

   public PyObject __sub__(PyObject o) {
      return this.py().__sub__(o);
   }

   public PyObject __rsub__(PyObject o) {
      return this.py().__rsub__(o);
   }

   public PyObject __isub__(PyObject o) {
      return this.py().__isub__(o);
   }

   public PyObject __mul__(PyObject o) {
      return this.py().__mul__(o);
   }

   public PyObject __rmul__(PyObject o) {
      return this.py().__rmul__(o);
   }

   public PyObject __imul__(PyObject o) {
      return this.py().__imul__(o);
   }

   public PyObject __div__(PyObject o) {
      return this.py().__div__(o);
   }

   public PyObject __floordiv__(PyObject o) {
      return this.py().__floordiv__(o);
   }

   public PyObject __rdiv__(PyObject o) {
      return this.py().__rdiv__(o);
   }

   public PyObject __idiv__(PyObject o) {
      return this.py().__idiv__(o);
   }

   public PyObject __ifloordiv__(PyObject o) {
      return this.py().__ifloordiv__(o);
   }

   public PyObject __mod__(PyObject o) {
      return this.py().__mod__(o);
   }

   public PyObject __rmod__(PyObject o) {
      return this.py().__rmod__(o);
   }

   public PyObject __imod__(PyObject o) {
      return this.py().__imod__(o);
   }

   public PyObject __divmod__(PyObject o) {
      return this.py().__divmod__(o);
   }

   public PyObject __rdivmod__(PyObject o) {
      return this.py().__rdivmod__(o);
   }

   public PyObject __pow__(PyObject o) {
      return this.py().__pow__(o);
   }

   public PyObject __rpow__(PyObject o) {
      return this.py().__rpow__(o);
   }

   public PyObject __ipow__(PyObject o) {
      return this.py().__ipow__(o);
   }

   public PyObject __lshift__(PyObject o) {
      return this.py().__lshift__(o);
   }

   public PyObject __rlshift__(PyObject o) {
      return this.py().__rlshift__(o);
   }

   public PyObject __ilshift__(PyObject o) {
      return this.py().__ilshift__(o);
   }

   public PyObject __rshift__(PyObject o) {
      return this.py().__rshift__(o);
   }

   public PyObject __rrshift__(PyObject o) {
      return this.py().__rrshift__(o);
   }

   public PyObject __irshift__(PyObject o) {
      return this.py().__irshift__(o);
   }

   public PyObject __and__(PyObject o) {
      return this.py().__and__(o);
   }

   public PyObject __rand__(PyObject o) {
      return this.py().__rand__(o);
   }

   public PyObject __iand__(PyObject o) {
      return this.py().__iand__(o);
   }

   public PyObject __or__(PyObject o) {
      return this.py().__or__(o);
   }

   public PyObject __ror__(PyObject o) {
      return this.py().__ror__(o);
   }

   public PyObject __ior__(PyObject o) {
      return this.py().__ior__(o);
   }

   public PyObject __xor__(PyObject o) {
      return this.py().__xor__(o);
   }

   public PyObject __rxor__(PyObject o) {
      return this.py().__rxor__(o);
   }

   public PyObject __ixor__(PyObject o) {
      return this.py().__ixor__(o);
   }

   static {
      PyType.addBuilder(ProxyType.class, new PyExposer());
      TYPE = PyType.fromClass(ProxyType.class);
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[0];
         PyDataDescr[] var2 = new PyDataDescr[0];
         super("weakproxy", ProxyType.class, Object.class, (boolean)0, (String)null, var1, var2, (PyNewWrapper)null);
      }
   }
}
