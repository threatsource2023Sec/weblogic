package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;
import org.jboss.classfilewriter.WritableEntry;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public abstract class ConstPoolEntry implements WritableEntry {
   public final void write(ByteArrayDataOutputStream stream) throws IOException {
      stream.writeByte(this.getType().getTag());
      this.writeData(stream);
   }

   public abstract void writeData(DataOutputStream var1) throws IOException;

   public abstract ConstPoolEntryType getType();
}
