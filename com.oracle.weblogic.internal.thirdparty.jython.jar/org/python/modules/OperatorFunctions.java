package org.python.modules;

import org.python.core.Py;
import org.python.core.PyBuiltinFunctionSet;
import org.python.core.PyObject;
import org.python.core.Untraversable;

@Untraversable
class OperatorFunctions extends PyBuiltinFunctionSet {
   public static final PyObject module = Py.newString("operator");

   public OperatorFunctions(String name, int index, int argcount) {
      this(name, index, argcount, argcount);
   }

   public OperatorFunctions(String name, int index, int minargs, int maxargs) {
      super(name, index, minargs, maxargs);
   }

   public PyObject getModule() {
      return module;
   }

   public PyObject __call__(PyObject arg1) {
      switch (this.index) {
         case 10:
            return arg1.__abs__();
         case 11:
            return arg1.__invert__();
         case 12:
            return arg1.__neg__();
         case 13:
            return arg1.__not__();
         case 14:
            return arg1.__pos__();
         case 15:
            return Py.newBoolean(arg1.__nonzero__());
         case 16:
            return Py.newBoolean(arg1.isCallable());
         case 17:
            return Py.newBoolean(arg1.isMappingType());
         case 18:
            return Py.newBoolean(arg1.isNumberType());
         case 19:
            return Py.newBoolean(arg1.isSequenceType());
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         default:
            throw this.info.unexpectedCall(1, false);
         case 32:
            return arg1.__invert__();
         case 52:
            return arg1.__index__();
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2) {
      switch (this.index) {
         case 0:
            return arg1._add(arg2);
         case 1:
            return arg1._and(arg2);
         case 2:
            return arg1._div(arg2);
         case 3:
            return arg1._lshift(arg2);
         case 4:
            return arg1._mod(arg2);
         case 5:
            return arg1._mul(arg2);
         case 6:
            return arg1._or(arg2);
         case 7:
            return arg1._rshift(arg2);
         case 8:
            return arg1._sub(arg2);
         case 9:
            return arg1._xor(arg2);
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 22:
         case 24:
         case 25:
         case 26:
         case 32:
         default:
            throw this.info.unexpectedCall(2, false);
         case 20:
            return Py.newBoolean(arg1.__contains__(arg2));
         case 21:
            arg1.__delitem__(arg2);
            return Py.None;
         case 23:
            return arg1.__getitem__(arg2);
         case 27:
            return arg1._ge(arg2);
         case 28:
            return arg1._le(arg2);
         case 29:
            return arg1._eq(arg2);
         case 30:
            return arg1._floordiv(arg2);
         case 31:
            return arg1._gt(arg2);
         case 33:
            return arg1._lt(arg2);
         case 34:
            return arg1._ne(arg2);
         case 35:
            return arg1._truediv(arg2);
         case 36:
            return arg1._pow(arg2);
         case 37:
            return arg1._is(arg2);
         case 38:
            return arg1._isnot(arg2);
         case 39:
            return arg1._iadd(arg2);
         case 40:
            return arg1._iand(arg2);
         case 41:
            return arg1._idiv(arg2);
         case 42:
            return arg1._ifloordiv(arg2);
         case 43:
            return arg1._ilshift(arg2);
         case 44:
            return arg1._imod(arg2);
         case 45:
            return arg1._imul(arg2);
         case 46:
            return arg1._ior(arg2);
         case 47:
            return arg1._ipow(arg2);
         case 48:
            return arg1._irshift(arg2);
         case 49:
            return arg1._isub(arg2);
         case 50:
            return arg1._itruediv(arg2);
         case 51:
            return arg1._ixor(arg2);
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3) {
      switch (this.index) {
         case 22:
            arg1.__delslice__(arg2.__index__(), arg3.__index__());
            return Py.None;
         case 23:
         default:
            throw this.info.unexpectedCall(3, false);
         case 24:
            return arg1.__getslice__(arg2.__index__(), arg3.__index__());
         case 25:
            arg1.__setitem__(arg2, arg3);
            return Py.None;
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4) {
      switch (this.index) {
         case 26:
            arg1.__setslice__(arg2.__index__(), arg3.__index__(), arg4);
            return Py.None;
         default:
            throw this.info.unexpectedCall(4, false);
      }
   }
}
