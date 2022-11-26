package com.bea.core.repackaged.springframework.core.serializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class DefaultSerializer implements Serializer {
   public void serialize(Object object, OutputStream outputStream) throws IOException {
      if (!(object instanceof Serializable)) {
         throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires a Serializable payload but received an object of type [" + object.getClass().getName() + "]");
      } else {
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
         objectOutputStream.writeObject(object);
         objectOutputStream.flush();
      }
   }
}
