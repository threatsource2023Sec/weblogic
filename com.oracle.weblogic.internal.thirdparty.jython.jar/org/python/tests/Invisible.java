package org.python.tests;

public class Invisible implements VisibilityResults {
   private static int privateStaticField;
   private int privateField;
   protected static int protectedStaticField = 1;
   protected int protectedField = 2;
   static int packageStaticField = 3;
   int packageField = 4;

   Invisible() {
   }

   private static int privateStaticMethod() {
      return 7;
   }

   private int privateMethod() {
      return 7;
   }

   protected static int protectedStaticMethod(int input) {
      return 5;
   }

   protected static int protectedStaticMethod(String input) {
      return 6;
   }

   protected int protectedMethod(int input) {
      return 7;
   }

   protected int protectedMethod(String input) {
      return 8;
   }

   static int packageStaticMethod() {
      return 9;
   }

   int packageMethod() {
      return 10;
   }
}
