package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class EnumAnnotationValue extends AnnotationValue {
   private final int valueIndex;
   private final int typeIndex;

   public EnumAnnotationValue(ConstPool constPool, String name, Enum value) {
      super(constPool, name);
      this.valueIndex = constPool.addUtf8Entry(value.name());
      this.typeIndex = constPool.addUtf8Entry(value.getDeclaringClass().getName());
   }

   public EnumAnnotationValue(ConstPool constPool, String name, String enumType, String enumValue) {
      super(constPool, name);
      this.valueIndex = constPool.addUtf8Entry(enumValue);
      this.typeIndex = constPool.addUtf8Entry(enumType);
   }

   public char getTag() {
      return 'e';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.typeIndex);
      stream.writeShort(this.valueIndex);
   }
}
