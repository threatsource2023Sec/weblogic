package com.bea.core.repackaged.aspectj.weaver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PersistenceSupport {
   public static void write(CompressingDataOutputStream stream, ISourceContext sourceContext) throws IOException {
      throw new IllegalStateException();
   }

   public static void write(CompressingDataOutputStream stream, Serializable serializableObject) throws IOException {
      ObjectOutputStream oos = new ObjectOutputStream(stream);
      oos.writeObject(serializableObject);
      oos.flush();
   }
}
