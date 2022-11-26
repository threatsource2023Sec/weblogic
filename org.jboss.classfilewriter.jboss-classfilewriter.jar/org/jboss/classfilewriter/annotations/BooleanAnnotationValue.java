package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class BooleanAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final boolean value;

   public BooleanAnnotationValue(ConstPool constPool, String name, boolean value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addIntegerEntry(value ? 1 : 0);
   }

   public char getTag() {
      return 'Z';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public boolean getValue() {
      return this.value;
   }
}
