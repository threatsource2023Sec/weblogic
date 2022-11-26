package org.mozilla.javascript;

public class PropertyException extends Exception {
   public PropertyException(String var1) {
      super(var1);
   }

   static PropertyException withMessage0(String var0) {
      return new PropertyException(Context.getMessage0(var0));
   }

   static PropertyException withMessage1(String var0, Object var1) {
      return new PropertyException(Context.getMessage1(var0, var1));
   }

   static PropertyException withMessage2(String var0, Object var1, Object var2) {
      return new PropertyException(Context.getMessage2(var0, var1, var2));
   }
}
