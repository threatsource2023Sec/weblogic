package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class FloatAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final float value;

   public FloatAnnotationValue(ConstPool constPool, String name, float value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addFloatEntry(value);
   }

   public char getTag() {
      return 'F';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public float getValue() {
      return this.value;
   }
}
