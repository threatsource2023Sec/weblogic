package org.python.google.common.escape;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   emulated = true
)
final class Platform {
   private static final ThreadLocal DEST_TL = new ThreadLocal() {
      protected char[] initialValue() {
         return new char[1024];
      }
   };

   private Platform() {
   }

   static char[] charBufferFromThreadLocal() {
      return (char[])DEST_TL.get();
   }
}
