package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class ShortAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final short value;

   public ShortAnnotationValue(ConstPool constPool, String name, short value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addIntegerEntry(value);
   }

   public char getTag() {
      return 'S';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public short getValue() {
      return this.value;
   }
}
