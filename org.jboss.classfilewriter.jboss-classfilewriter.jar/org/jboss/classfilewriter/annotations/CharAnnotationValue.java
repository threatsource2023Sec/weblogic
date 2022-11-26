package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class CharAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final char value;

   public CharAnnotationValue(ConstPool constPool, String name, char value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addIntegerEntry(value);
   }

   public char getTag() {
      return 'C';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public char getValue() {
      return this.value;
   }
}
