package weblogic.servlet.internal;

import java.util.Locale;

public class JDK7LocaleGenerator extends LocaleGenerator {
   public Locale[] getLocale(String languageTag) {
      String trimedLangTag = languageTag.trim();
      if (this.isLangtagFallbackEnabled()) {
         if (this.getAddHandleLangTag(trimedLangTag) != null) {
            return this.getAddHandleLangTag(trimedLangTag);
         } else {
            return this.getReplaceHandleLangTag(trimedLangTag) != null ? this.getReplaceHandleLangTag(trimedLangTag) : new Locale[]{this.processFallbackLocale(Locale.forLanguageTag(languageTag.trim()))};
         }
      } else {
         return new Locale[]{Locale.forLanguageTag(languageTag.trim())};
      }
   }

   private Locale processFallbackLocale(Locale locale) {
      Locale newLocale = new Locale(locale.getLanguage(), locale.getCountry(), locale.getVariant());
      return newLocale;
   }
}
