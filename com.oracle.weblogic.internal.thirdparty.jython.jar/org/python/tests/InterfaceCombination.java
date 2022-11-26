package org.python.tests;

public class InterfaceCombination {
   public static final String NO_ARG_RESULT = "no_arg_result";
   public static final String ONE_ARG_RESULT = "one_arg_result";
   public static final String TWO_ARG_RESULT = "two_arg_result";

   public static Object newImplementation() {
      return new Implementation();
   }

   private static class Implementation extends Base implements IFace, IIFace, Hidden {
      private Implementation() {
      }

      public String getValue(String one, String two, String three) {
         return three;
      }

      public String getValue() {
         return "no_arg_result";
      }

      public String getValue(String name) {
         return "one_arg_result";
      }

      public void internalMethod() {
      }

      // $FF: synthetic method
      Implementation(Object x0) {
         this();
      }
   }

   public static class Base {
      public String getValue(String one, String two) {
         return "two_arg_result";
      }
   }

   interface Hidden {
      void internalMethod();
   }

   public interface IIFace {
      String getValue(String var1);
   }

   public interface IFace {
      String getValue();
   }
}
