package org.jboss.classfilewriter.annotations;

import java.io.IOException;
import org.jboss.classfilewriter.WritableEntry;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public abstract class AnnotationValue implements WritableEntry {
   private final String name;
   private final int nameIndex;

   protected AnnotationValue(ConstPool constPool, String name) {
      this.name = name;
      if (name != null) {
         this.nameIndex = constPool.addUtf8Entry(name);
      } else {
         this.nameIndex = -1;
      }

   }

   public void write(ByteArrayDataOutputStream stream) throws IOException {
      if (this.nameIndex != -1) {
         stream.writeShort(this.nameIndex);
      }

      stream.writeByte(this.getTag());
      this.writeData(stream);
   }

   public abstract void writeData(ByteArrayDataOutputStream var1) throws IOException;

   public String getName() {
      return this.name;
   }

   public abstract char getTag();
}
