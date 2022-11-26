package org.python.tests.props;

public class PropShadow {
   public static class Derived extends Base {
      public int getFoo() {
         return 3;
      }

      public int getBaz() {
         return 4;
      }
   }

   public static class Base {
      public int foo() {
         return 1;
      }

      public int bar() {
         return 2;
      }
   }
}
