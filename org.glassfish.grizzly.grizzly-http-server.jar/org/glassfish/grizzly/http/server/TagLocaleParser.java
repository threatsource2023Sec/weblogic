package org.glassfish.grizzly.http.server;

import java.util.Locale;

class TagLocaleParser implements LocaleParser {
   public Locale parseLocale(String source) {
      Locale l = Locale.forLanguageTag(source);
      return l.getLanguage().isEmpty() ? null : l;
   }
}
