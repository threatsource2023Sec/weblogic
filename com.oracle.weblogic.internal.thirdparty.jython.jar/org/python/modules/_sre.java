package org.python.modules;

import java.util.Iterator;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.modules.sre.PatternObject;
import org.python.modules.sre.SRE_STATE;

public class _sre {
   public static int MAGIC = 20031017;
   public static int MAXREPEAT = 65535;
   public static int CODESIZE = 4;

   public static PatternObject compile(PyString pattern, int flags, PyObject code, int groups, PyObject groupindex, PyObject indexgroup) {
      int[] ccode = new int[code.__len__()];
      int i = 0;

      PyObject item;
      for(Iterator var8 = code.asIterable().iterator(); var8.hasNext(); ccode[i++] = (int)item.asLong()) {
         item = (PyObject)var8.next();
      }

      return new PatternObject(pattern, flags, ccode, groups, groupindex, indexgroup);
   }

   public static int getcodesize() {
      return CODESIZE;
   }

   public static int getlower(int ch, int flags) {
      return SRE_STATE.getlower(ch, flags);
   }
}
