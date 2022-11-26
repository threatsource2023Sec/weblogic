package com.googlecode.cqengine.codegen.support;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.List;

public class GeneratedAttributeSupport {
   public static Byte valueOf(byte value) {
      return value;
   }

   public static Short valueOf(short value) {
      return value;
   }

   public static Integer valueOf(int value) {
      return value;
   }

   public static Long valueOf(long value) {
      return value;
   }

   public static Float valueOf(float value) {
      return value;
   }

   public static Double valueOf(double value) {
      return value;
   }

   public static Boolean valueOf(boolean value) {
      return value;
   }

   public static Character valueOf(char value) {
      return value;
   }

   public static List valueOf(byte[] value) {
      return wrapArray(value);
   }

   public static List valueOf(short[] value) {
      return wrapArray(value);
   }

   public static List valueOf(int[] value) {
      return wrapArray(value);
   }

   public static List valueOf(long[] value) {
      return wrapArray(value);
   }

   public static List valueOf(float[] value) {
      return wrapArray(value);
   }

   public static List valueOf(double[] value) {
      return wrapArray(value);
   }

   public static List valueOf(boolean[] value) {
      return wrapArray(value);
   }

   public static List valueOf(char[] value) {
      return wrapArray(value);
   }

   public static List valueOf(Object[] value) {
      return wrapArray(value);
   }

   public static List valueOf(List value) {
      return value;
   }

   public static Object valueOf(Object value) {
      return value;
   }

   static List wrapArray(final Object array) {
      return new AbstractList() {
         public Object get(int index) {
            Object result = Array.get(array, index);
            return result;
         }

         public int size() {
            return Array.getLength(array);
         }

         public Object set(int index, Object element) {
            Object existing = this.get(index);
            Array.set(array, index, element);
            return existing;
         }
      };
   }

   GeneratedAttributeSupport() {
   }
}
