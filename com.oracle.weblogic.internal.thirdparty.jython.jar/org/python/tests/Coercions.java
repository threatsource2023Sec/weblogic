package org.python.tests;

import java.io.Serializable;
import java.util.Iterator;
import org.python.core.PyObject;

public class Coercions {
   public String takeInt(int i) {
      return "" + i;
   }

   public String takeInteger(Integer i) {
      return "" + i;
   }

   public String takeNumber(Number n) {
      return "" + n;
   }

   public String takePyObjInst(PyObject[] args) {
      return "" + args.length;
   }

   public static String takeArray(float[] f) {
      return "float";
   }

   public static String takeArray(double[] d) {
      return "double";
   }

   public static String takePyObj(PyObject[] args) {
      return "" + args.length;
   }

   public static String takeArray(Object[] obj) {
      return "Object[]";
   }

   public static String takeArray(Object obj) {
      return "Object";
   }

   public static String takeArray(SubVisible[] vis) {
      return "SubVisible[]";
   }

   public static String takeArray(OtherSubVisible[] vis) {
      return "OtherSubVisible[]";
   }

   public static String takeArray(Visible[] vis) {
      return "Visible[]";
   }

   public String tellClassNameObject(Object o) {
      return o.getClass().toString();
   }

   public String tellClassNameSerializable(Serializable o) {
      return o.getClass().toString();
   }

   public static String take(int i) {
      return "take with int arg: " + i;
   }

   public static String take(char c) {
      return "take with char arg: " + c;
   }

   public static String take(boolean b) {
      return "take with boolean arg: " + b;
   }

   public static String take(byte bt) {
      return "take with byte arg: " + bt;
   }

   public static int takeIterable(Iterable it) {
      int sum = 0;

      Integer integer;
      for(Iterator var2 = it.iterator(); var2.hasNext(); sum += integer) {
         integer = (Integer)var2.next();
      }

      return sum;
   }

   public static boolean takeBoolIterable(Iterable it) {
      Iterator var1 = it.iterator();

      Boolean integer;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         integer = (Boolean)var1.next();
      } while(integer);

      return false;
   }

   public static void runRunnable(Runnable r) {
      r.run();
   }
}
