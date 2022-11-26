package jnr.netdb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jnr.ffi.Pointer;

class StringUtil {
   public static final List getNullTerminatedStringArray(Pointer ptr) {
      if (ptr != null && ptr.getPointer(0L) != null) {
         int pointerSize = ptr.getRuntime().addressSize();
         List array = new ArrayList();

         Pointer p;
         for(int off = 0; (p = ptr.getPointer((long)off)) != null; off += pointerSize) {
            array.add(p.getString(0L));
         }

         return array;
      } else {
         return Collections.emptyList();
      }
   }
}
