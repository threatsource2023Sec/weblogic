package com.bea.core.repackaged.springframework.core.serializer.support;

import com.bea.core.repackaged.springframework.core.serializer.DefaultDeserializer;
import com.bea.core.repackaged.springframework.core.serializer.DefaultSerializer;
import com.bea.core.repackaged.springframework.core.serializer.Deserializer;
import com.bea.core.repackaged.springframework.core.serializer.Serializer;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerializationDelegate implements Serializer, Deserializer {
   private final Serializer serializer;
   private final Deserializer deserializer;

   public SerializationDelegate(ClassLoader classLoader) {
      this.serializer = new DefaultSerializer();
      this.deserializer = new DefaultDeserializer(classLoader);
   }

   public SerializationDelegate(Serializer serializer, Deserializer deserializer) {
      Assert.notNull(serializer, (String)"Serializer must not be null");
      Assert.notNull(deserializer, (String)"Deserializer must not be null");
      this.serializer = serializer;
      this.deserializer = deserializer;
   }

   public void serialize(Object object, OutputStream outputStream) throws IOException {
      this.serializer.serialize(object, outputStream);
   }

   public Object deserialize(InputStream inputStream) throws IOException {
      return this.deserializer.deserialize(inputStream);
   }
}
