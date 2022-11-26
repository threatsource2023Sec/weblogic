package org.jboss.classfilewriter.constpool;

import java.io.DataOutputStream;
import java.io.IOException;

public class Utf8Entry extends ConstPoolEntry {
   private final String data;

   public Utf8Entry(String data) {
      this.data = data;
   }

   public ConstPoolEntryType getType() {
      return ConstPoolEntryType.UTF8;
   }

   public void writeData(DataOutputStream stream) throws IOException {
      stream.writeUTF(this.data);
   }

   public String getData() {
      return this.data;
   }
}
