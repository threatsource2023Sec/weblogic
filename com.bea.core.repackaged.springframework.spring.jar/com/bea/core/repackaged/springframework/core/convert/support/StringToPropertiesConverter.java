package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

final class StringToPropertiesConverter implements Converter {
   public Properties convert(String source) {
      try {
         Properties props = new Properties();
         props.load(new ByteArrayInputStream(source.getBytes(StandardCharsets.ISO_8859_1)));
         return props;
      } catch (Exception var3) {
         throw new IllegalArgumentException("Failed to parse [" + source + "] into Properties", var3);
      }
   }
}
