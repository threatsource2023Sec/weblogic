package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class InstantFormatter implements Formatter {
   public Instant parse(String text, Locale locale) throws ParseException {
      return text.length() > 0 && Character.isAlphabetic(text.charAt(0)) ? Instant.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(text)) : Instant.parse(text);
   }

   public String print(Instant object, Locale locale) {
      return object.toString();
   }
}
