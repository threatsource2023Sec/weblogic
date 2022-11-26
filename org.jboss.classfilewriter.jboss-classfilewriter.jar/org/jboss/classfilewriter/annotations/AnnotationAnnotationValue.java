package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public class AnnotationAnnotationValue extends AnnotationValue {
   private final ClassAnnotation value;

   public AnnotationAnnotationValue(ConstPool constPool, String name, ClassAnnotation value) {
      super(constPool, name);
      this.value = value;
   }

   public char getTag() {
      return '@';
   }

   public void writeData(ByteArrayDataOutputStream stream) throws IOException {
      this.value.write(stream);
   }
}
