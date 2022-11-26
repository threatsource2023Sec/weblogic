package com.bea.core.repackaged.jdt.internal.compiler.lookup;

interface ParameterNonNullDefaultProvider {
   ParameterNonNullDefaultProvider FALSE_PROVIDER = new ParameterNonNullDefaultProvider() {
      public boolean hasNonNullDefaultForParam(int i) {
         return false;
      }

      public boolean hasAnyNonNullDefault() {
         return false;
      }
   };
   ParameterNonNullDefaultProvider TRUE_PROVIDER = new ParameterNonNullDefaultProvider() {
      public boolean hasNonNullDefaultForParam(int i) {
         return true;
      }

      public boolean hasAnyNonNullDefault() {
         return true;
      }
   };

   boolean hasAnyNonNullDefault();

   boolean hasNonNullDefaultForParam(int var1);

   public static class MixedProvider implements ParameterNonNullDefaultProvider {
      private final boolean[] result;

      public MixedProvider(boolean[] result) {
         this.result = result;
      }

      public boolean hasNonNullDefaultForParam(int i) {
         return this.result[i];
      }

      public boolean hasAnyNonNullDefault() {
         return true;
      }
   }
}
