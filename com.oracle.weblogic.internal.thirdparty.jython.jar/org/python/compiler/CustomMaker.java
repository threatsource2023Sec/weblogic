package org.python.compiler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.python.core.BytecodeLoader;
import org.python.core.Py;
import org.python.core.PyObject;

public class CustomMaker extends JavaMaker {
   public CustomMaker(Class superclass, Class[] interfaces, String pythonClass, String pythonModule, String myClass, PyObject methods) {
      super(superclass, interfaces, pythonClass, pythonModule, myClass, methods);
   }

   public void saveBytes(ByteArrayOutputStream bytes) {
   }

   public Class makeClass() {
      try {
         ByteArrayOutputStream bytes = new ByteArrayOutputStream();
         this.build(bytes);
         this.saveBytes(bytes);
         List secondary = new LinkedList(Arrays.asList(this.interfaces));
         List referents = null;
         if (secondary != null) {
            if (this.superclass != null) {
               secondary.add(0, this.superclass);
            }

            referents = secondary;
         } else if (this.superclass != null) {
            referents = new ArrayList(1);
            ((List)referents).add(this.superclass);
         }

         return BytecodeLoader.makeClass(this.myClass, (List)referents, (byte[])bytes.toByteArray());
      } catch (Exception var4) {
         throw Py.JavaError(var4);
      }
   }
}
