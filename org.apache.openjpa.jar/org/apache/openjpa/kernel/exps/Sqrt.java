package org.apache.openjpa.kernel.exps;

class Sqrt extends UnaryMathVal {
   public Sqrt(Val val) {
      super(val);
   }

   protected Class getType(Class c) {
      return Double.TYPE;
   }

   protected Object operate(Object o, Class c) {
      return new Double(Math.sqrt(((Number)o).doubleValue()));
   }
}
