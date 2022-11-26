package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class StringAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final String value;

   public StringAnnotationValue(ConstPool constPool, String name, String value) {
      super(constPool, name);
      this.value = value;
      this.valueIndex = constPool.addUtf8Entry(value);
   }

   public char getTag() {
      return 's';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }

   public String getValue() {
      return this.value;
   }
}
