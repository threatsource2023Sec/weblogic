package org.jboss.classfilewriter.attributes;

import java.io.IOException;
import org.jboss.classfilewriter.WritableEntry;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public abstract class Attribute implements WritableEntry {
   private final String name;
   private final int nameIndex;
   protected final ConstPool constPool;

   public Attribute(String name, ConstPool constPool) {
      this.name = name;
      this.nameIndex = constPool.addUtf8Entry(name);
      this.constPool = constPool;
   }

   public void write(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeShort(this.nameIndex);
      this.writeData(stream);
   }

   public abstract void writeData(ByteArrayDataOutputStream var1) throws IOException;

   public String getName() {
      return this.name;
   }
}
