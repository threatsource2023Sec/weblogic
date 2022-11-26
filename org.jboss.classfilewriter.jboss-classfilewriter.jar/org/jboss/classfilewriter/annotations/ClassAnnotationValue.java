package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class ClassAnnotationValue extends AnnotationValue {
   private final int valueIndex;

   public ClassAnnotationValue(ConstPool constPool, String name, Class value) {
      super(constPool, name);
      this.valueIndex = constPool.addUtf8Entry(DescriptorUtils.makeDescriptor(value));
   }

   public ClassAnnotationValue(ConstPool constPool, String name, String descriptor) {
      super(constPool, name);
      this.valueIndex = constPool.addUtf8Entry(descriptor);
   }

   public char getTag() {
      return 'c';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.valueIndex);
   }
}
