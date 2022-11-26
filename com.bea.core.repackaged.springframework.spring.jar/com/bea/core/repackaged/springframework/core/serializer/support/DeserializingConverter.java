package com.bea.core.repackaged.springframework.core.serializer.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.serializer.DefaultDeserializer;
import com.bea.core.repackaged.springframework.core.serializer.Deserializer;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.ByteArrayInputStream;

public class DeserializingConverter implements Converter {
   private final Deserializer deserializer;

   public DeserializingConverter() {
      this.deserializer = new DefaultDeserializer();
   }

   public DeserializingConverter(ClassLoader classLoader) {
      this.deserializer = new DefaultDeserializer(classLoader);
   }

   public DeserializingConverter(Deserializer deserializer) {
      Assert.notNull(deserializer, (String)"Deserializer must not be null");
      this.deserializer = deserializer;
   }

   public Object convert(byte[] source) {
      ByteArrayInputStream byteStream = new ByteArrayInputStream(source);

      try {
         return this.deserializer.deserialize(byteStream);
      } catch (Throwable var4) {
         throw new SerializationFailedException("Failed to deserialize payload. Is the byte array a result of corresponding serialization for " + this.deserializer.getClass().getSimpleName() + "?", var4);
      }
   }
}
