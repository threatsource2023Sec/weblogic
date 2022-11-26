package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class DoubleAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final double value;

   public DoubleAnnotationValue(ConstPool constPool, String name, double value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addDoubleEntry(value);
   }

   public char getTag() {
      return 'D';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public double getValue() {
      return this.value;
   }
}
