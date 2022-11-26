package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class LongAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final long value;

   public LongAnnotationValue(ConstPool constPool, String name, long value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addLongEntry(value);
   }

   public char getTag() {
      return 'J';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public long getValue() {
      return this.value;
   }
}
