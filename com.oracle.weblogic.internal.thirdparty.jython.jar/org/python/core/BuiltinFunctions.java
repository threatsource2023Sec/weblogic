package org.python.core;

import org.python.modules._functools._functools;

@Untraversable
class BuiltinFunctions extends PyBuiltinFunctionSet {
   public static final PyObject module = Py.newString("__builtin__");

   public BuiltinFunctions(String name, int index, int argcount) {
      this(name, index, argcount, argcount);
   }

   public BuiltinFunctions(String name, int index, int minargs, int maxargs) {
      super(name, index, minargs, maxargs);
   }

   public PyObject __call__() {
      switch (this.index) {
         case 4:
            return __builtin__.globals();
         case 16:
            return __builtin__.dir();
         case 24:
            return __builtin__.input();
         case 28:
            return __builtin__.locals();
         case 34:
            return Py.newString(__builtin__.raw_input());
         case 41:
            return __builtin__.vars();
         case 43:
            return __builtin__.zip();
         default:
            throw this.info.unexpectedCall(0, false);
      }
   }

   public PyObject __call__(PyObject arg1) {
      switch (this.index) {
         case 0:
            return Py.newString(__builtin__.chr(Py.py2int(arg1, "chr(): 1st arg can't be coerced to int")));
         case 1:
            return Py.newInteger(__builtin__.len(arg1));
         case 2:
            return __builtin__.range(arg1);
         case 3:
            return Py.newInteger(__builtin__.ord(arg1));
         case 4:
         case 8:
         case 10:
         case 13:
         case 15:
         case 17:
         case 20:
         case 21:
         case 22:
         case 26:
         case 28:
         case 29:
         case 33:
         case 35:
         case 38:
         case 39:
         case 40:
         case 42:
         case 44:
         default:
            throw this.info.unexpectedCall(1, false);
         case 5:
            return __builtin__.hash(arg1);
         case 6:
            return Py.newUnicode(__builtin__.unichr(arg1));
         case 7:
            return __builtin__.abs(arg1);
         case 9:
            return __builtin__.apply(arg1);
         case 11:
            return Py.newInteger(__builtin__.id(arg1));
         case 12:
            return __builtin__.sum(arg1);
         case 14:
            return Py.newBoolean(__builtin__.callable(arg1));
         case 16:
            return __builtin__.dir(arg1);
         case 18:
            return __builtin__.eval(arg1);
         case 19:
            __builtin__.execfile(Py.fileSystemDecode(arg1));
            return Py.None;
         case 23:
            return __builtin__.hex(arg1);
         case 24:
            return __builtin__.input(arg1);
         case 25:
            return __builtin__.intern(arg1);
         case 27:
            return __builtin__.iter(arg1);
         case 30:
            return this.fancyCall(new PyObject[]{arg1});
         case 31:
            return this.fancyCall(new PyObject[]{arg1});
         case 32:
            return __builtin__.oct(arg1);
         case 34:
            return Py.newString(__builtin__.raw_input(arg1));
         case 36:
            return __builtin__.reload(arg1);
         case 37:
            return __builtin__.repr(arg1);
         case 41:
            return __builtin__.vars(arg1);
         case 43:
            return this.fancyCall(new PyObject[]{arg1});
         case 45:
            return __builtin__.reversed(arg1);
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2) {
      switch (this.index) {
         case 2:
            return __builtin__.range(arg1, arg2);
         case 3:
         case 4:
         case 5:
         case 7:
         case 8:
         case 11:
         case 14:
         case 16:
         case 23:
         case 24:
         case 25:
         case 28:
         case 32:
         case 34:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         default:
            throw this.info.unexpectedCall(2, false);
         case 6:
            return Py.newInteger(__builtin__.cmp(arg1, arg2));
         case 9:
            return __builtin__.apply(arg1, arg2);
         case 10:
            return Py.newBoolean(__builtin__.isinstance(arg1, arg2));
         case 12:
            return __builtin__.sum(arg1, arg2);
         case 13:
            return __builtin__.coerce(arg1, arg2);
         case 15:
            __builtin__.delattr(arg1, arg2);
            return Py.None;
         case 17:
            return __builtin__.divmod(arg1, arg2);
         case 18:
            return __builtin__.eval(arg1, arg2);
         case 19:
            __builtin__.execfile(Py.fileSystemDecode(arg1), arg2);
            return Py.None;
         case 20:
            return __builtin__.filter(arg1, arg2);
         case 21:
            return __builtin__.getattr(arg1, arg2);
         case 22:
            return Py.newBoolean(__builtin__.hasattr(arg1, arg2));
         case 26:
            return Py.newBoolean(__builtin__.issubclass(arg1, arg2));
         case 27:
            return __builtin__.iter(arg1, arg2);
         case 29:
            return this.fancyCall(new PyObject[]{arg1, arg2});
         case 30:
            return this.fancyCall(new PyObject[]{arg1, arg2});
         case 31:
            return this.fancyCall(new PyObject[]{arg1, arg2});
         case 33:
            return __builtin__.pow(arg1, arg2);
         case 35:
            return _functools.reduce(arg1, arg2);
         case 43:
            return this.fancyCall(new PyObject[]{arg1, arg2});
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3) {
      switch (this.index) {
         case 2:
            return __builtin__.range(arg1, arg2, (PyObject)arg3);
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 20:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 32:
         case 34:
         case 36:
         case 37:
         case 38:
         case 40:
         case 41:
         case 42:
         default:
            throw this.info.unexpectedCall(3, false);
         case 9:
            try {
               PyDictionary d;
               if (arg3 instanceof PyStringMap) {
                  d = new PyDictionary();
                  d.update((PyObject)arg3);
                  arg3 = d;
               }

               d = (PyDictionary)arg3;
               return __builtin__.apply(arg1, arg2, d);
            } catch (ClassCastException var5) {
               throw Py.TypeError("apply() 3rd argument must be a dictionary with string keys");
            }
         case 18:
            return __builtin__.eval(arg1, arg2, (PyObject)arg3);
         case 19:
            __builtin__.execfile(Py.fileSystemDecode(arg1), arg2, (PyObject)arg3);
            return Py.None;
         case 21:
            return __builtin__.getattr(arg1, arg2, (PyObject)arg3);
         case 29:
            return this.fancyCall(new PyObject[]{arg1, arg2, (PyObject)arg3});
         case 30:
            return this.fancyCall(new PyObject[]{arg1, arg2, (PyObject)arg3});
         case 31:
            return this.fancyCall(new PyObject[]{arg1, arg2, (PyObject)arg3});
         case 33:
            return __builtin__.pow(arg1, arg2, (PyObject)arg3);
         case 35:
            return _functools.reduce(arg1, arg2, (PyObject)arg3);
         case 39:
            __builtin__.setattr(arg1, arg2, (PyObject)arg3);
            return Py.None;
         case 43:
            return this.fancyCall(new PyObject[]{arg1, arg2, (PyObject)arg3});
         case 44:
            return this.fancyCall(new PyObject[]{arg1, arg2, (PyObject)arg3});
      }
   }

   public PyObject __call__(PyObject arg1, PyObject arg2, PyObject arg3, PyObject arg4) {
      switch (this.index) {
         case 29:
            return this.fancyCall(new PyObject[]{arg1, arg2, arg3, arg4});
         case 30:
            return this.fancyCall(new PyObject[]{arg1, arg2, arg3, arg4});
         case 31:
            return this.fancyCall(new PyObject[]{arg1, arg2, arg3, arg4});
         case 43:
            return this.fancyCall(new PyObject[]{arg1, arg2, arg3, arg4});
         case 44:
            return this.fancyCall(new PyObject[]{arg1, arg2, arg3, arg4});
         default:
            throw this.info.unexpectedCall(4, false);
      }
   }

   public PyObject fancyCall(PyObject[] args) {
      switch (this.index) {
         case 29:
            return __builtin__.map(args);
         case 43:
            return __builtin__.zip(args);
         default:
            throw this.info.unexpectedCall(args.length, false);
      }
   }

   public PyObject getModule() {
      return module;
   }
}
