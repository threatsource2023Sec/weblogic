package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class ArrayAnnotationValue extends AnnotationValue {
   private final List value;

   public ArrayAnnotationValue(ConstPool constPool, String name, List value) {
      super(constPool, name);
      this.value = value;
   }

   public char getTag() {
      return '[';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.value.size());
      Iterator var2 = this.value.iterator();

      while(var2.hasNext()) {
         AnnotationValue v = (AnnotationValue)var2.next();
         v.write(stream);
      }

   }
}
