package org.python.core;

@Untraversable
class FormatFunction extends PyBuiltinFunctionNarrow {
   FormatFunction() {
      super("format", 1, 2, "format(value[, format_spec]) -> string\n\nReturns value.__format__(format_spec)\nformat_spec defaults to \"\"");
   }

   public PyObject __call__(PyObject arg1) {
      return this.__call__(arg1, Py.EmptyString);
   }

   public PyObject __call__(PyObject arg1, PyObject arg2) {
      PyObject formatted = arg1.__format__(arg2);
      if (!Py.isInstance(formatted, PyString.TYPE) && !Py.isInstance(formatted, PyUnicode.TYPE)) {
         throw Py.TypeError("instance.__format__ must return string or unicode, not " + formatted.getType().fastGetName());
      } else {
         return formatted;
      }
   }
}
