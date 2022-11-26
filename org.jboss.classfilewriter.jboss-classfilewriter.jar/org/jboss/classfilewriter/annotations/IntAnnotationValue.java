package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class IntAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final int value;

   public IntAnnotationValue(ConstPool constPool, String name, int value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addIntegerEntry(value);
   }

   public char getTag() {
      return 'I';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public int getValue() {
      return this.value;
   }
}
