package org.python.tests;

public class RedundantInterfaceDeclarations {
   public static class ExtraClassAndString extends Implementation implements ClassArg, StringArg {
   }

   public static class ExtraStringAndClass extends Implementation implements StringArg, ClassArg {
   }

   public static class ExtraClass extends Implementation implements ClassArg {
   }

   public static class ExtraString extends Implementation implements StringArg {
   }

   public static class Implementation extends AbstractImplementation implements StringArg {
      public String call(String name) {
         return "String";
      }

      public String call(int arg) {
         return "int";
      }
   }

   public abstract static class AbstractImplementation implements StringArg, IntArg {
      public String call(Class arg) {
         return "Class";
      }
   }

   public interface StringArg extends ClassArg {
      String call(String var1);
   }

   public interface ClassArg {
      String call(Class var1);
   }

   public interface IntArg {
      String call(int var1);
   }
}
