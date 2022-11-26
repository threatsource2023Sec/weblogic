package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class ByteAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final byte value;

   public ByteAnnotationValue(ConstPool constPool, String name, byte value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addIntegerEntry(value);
   }

   public char getTag() {
      return 'B';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public byte getValue() {
      return this.value;
   }
}
