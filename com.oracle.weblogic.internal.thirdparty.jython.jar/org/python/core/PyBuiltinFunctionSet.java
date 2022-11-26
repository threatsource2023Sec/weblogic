package org.python.core;

@Untraversable
public class PyBuiltinFunctionSet extends PyBuiltinFunctionNarrow {
   protected final int index;

   public PyBuiltinFunctionSet(String name, int index) {
      this(name, index, 1);
   }

   public PyBuiltinFunctionSet(String name, int index, int numargs) {
      this(name, index, numargs, numargs);
   }

   public PyBuiltinFunctionSet(String name, int index, int minargs, int maxargs) {
      this(name, index, minargs, maxargs, (String)null);
   }

   public PyBuiltinFunctionSet(String name, int index, int minargs, int maxargs, String doc) {
      super(name, minargs, maxargs, doc);
      this.index = index;
   }
}
