package org.python.core;

import org.python.core.stringlib.IntegerFormatter;

@Untraversable
class BinFunction extends PyBuiltinFunction {
   BinFunction() {
      super("bin", "bin(number)\n\nReturn the binary representation of an integer or long integer.");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("bin", args, kwds, new String[]{"number"}, 1);
      ap.noKeywords();
      return IntegerFormatter.bin(ap.getPyObject(0));
   }
}
