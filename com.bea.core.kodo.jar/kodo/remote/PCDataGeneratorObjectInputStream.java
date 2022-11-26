package kodo.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import org.apache.openjpa.enhance.PCDataGenerator;

class PCDataGeneratorObjectInputStream extends ObjectInputStream {
   private final PCDataGenerator _gen;

   public PCDataGeneratorObjectInputStream(PCDataGenerator gen, InputStream in) throws IOException {
      super(in);
      this._gen = gen;
   }

   public PCDataGenerator getPCDataGenerator() {
      return this._gen;
   }
}
