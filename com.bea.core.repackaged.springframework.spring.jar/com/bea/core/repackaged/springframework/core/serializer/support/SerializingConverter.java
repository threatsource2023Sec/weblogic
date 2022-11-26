package com.bea.core.repackaged.springframework.core.serializer.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.serializer.DefaultSerializer;
import com.bea.core.repackaged.springframework.core.serializer.Serializer;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.ByteArrayOutputStream;

public class SerializingConverter implements Converter {
   private final Serializer serializer;

   public SerializingConverter() {
      this.serializer = new DefaultSerializer();
   }

   public SerializingConverter(Serializer serializer) {
      Assert.notNull(serializer, (String)"Serializer must not be null");
      this.serializer = serializer;
   }

   public byte[] convert(Object source) {
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);

      try {
         this.serializer.serialize(source, byteStream);
         return byteStream.toByteArray();
      } catch (Throwable var4) {
         throw new SerializationFailedException("Failed to serialize object using " + this.serializer.getClass().getSimpleName(), var4);
      }
   }
}
