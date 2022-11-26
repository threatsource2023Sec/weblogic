package com.bea.xml.stream.test;

public class GlobalCounter {
   public int count = 0;
   public static GlobalCounter counter;

   protected GlobalCounter() {
   }

   public static GlobalCounter getInstance() {
      if (counter == null) {
         counter = new GlobalCounter();
         return counter;
      } else {
         return counter;
      }
   }

   public void increment() {
      ++this.count;
   }

   public int getCount() {
      return this.count;
   }
}
