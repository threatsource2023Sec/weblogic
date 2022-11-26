package com.oracle.jrf.concurrent.impl;

final class Util {
   private Util() {
   }

   static void checkNull(Object o) {
      if (o == null) {
         throw new NullPointerException();
      }
   }
}
