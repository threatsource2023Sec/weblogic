package com.bea.core.repackaged.springframework.core.serializer;

import com.bea.core.repackaged.springframework.core.ConfigurableObjectInputStream;
import com.bea.core.repackaged.springframework.core.NestedIOException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class DefaultDeserializer implements Deserializer {
   @Nullable
   private final ClassLoader classLoader;

   public DefaultDeserializer() {
      this.classLoader = null;
   }

   public DefaultDeserializer(@Nullable ClassLoader classLoader) {
      this.classLoader = classLoader;
   }

   public Object deserialize(InputStream inputStream) throws IOException {
      ObjectInputStream objectInputStream = new ConfigurableObjectInputStream(inputStream, this.classLoader);

      try {
         return objectInputStream.readObject();
      } catch (ClassNotFoundException var4) {
         throw new NestedIOException("Failed to deserialize object type", var4);
      }
   }
}
