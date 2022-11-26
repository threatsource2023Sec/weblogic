package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

final class PropertiesToStringConverter implements Converter {
   public String convert(Properties source) {
      try {
         ByteArrayOutputStream os = new ByteArrayOutputStream(256);
         source.store(os, (String)null);
         return os.toString("ISO-8859-1");
      } catch (IOException var3) {
         throw new IllegalArgumentException("Failed to store [" + source + "] into String", var3);
      }
   }
}
