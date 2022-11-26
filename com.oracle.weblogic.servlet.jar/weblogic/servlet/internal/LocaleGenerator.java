package weblogic.servlet.internal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class LocaleGenerator {
   private boolean langtagFallbackEnabled = false;
   private static final Map ADD_HANDLING_MAP = new HashMap(4);
   private static final Map REPLACE_HANDLING_MAP = new HashMap(2);
   public static final String SYSTEM_LANGTAG_REVISION = System.getProperty("weblogic.servlet.langtagRevision");
   public static final Boolean FORCE_REGIONAL_CONVETIONS = Boolean.getBoolean("weblogic.servlet.forceRegionalLocaleConventions");

   public void setLangtagFallbackEnabled(boolean langtagFallbackEnabled) {
      this.langtagFallbackEnabled = langtagFallbackEnabled;
   }

   public boolean isLangtagFallbackEnabled() {
      return this.langtagFallbackEnabled;
   }

   public Locale[] getAddHandleLangTag(String languageTag) {
      return (Locale[])ADD_HANDLING_MAP.get(languageTag);
   }

   public Locale[] getReplaceHandleLangTag(String languageTag) {
      return (Locale[])REPLACE_HANDLING_MAP.get(languageTag);
   }

   public abstract Locale[] getLocale(String var1);

   static {
      ADD_HANDLING_MAP.put("zh-Hans-SG", new Locale[]{new Locale("zh", "SG", ""), new Locale("zh", "CN", "")});
      ADD_HANDLING_MAP.put("zh-Hant-HK", new Locale[]{new Locale("zh", "HK", ""), new Locale("zh", "TW", "")});
      ADD_HANDLING_MAP.put("zh-Hant-MO", new Locale[]{new Locale("zh", "MO", ""), new Locale("zh", "TW", "")});
      REPLACE_HANDLING_MAP.put("zh-Hans", new Locale[]{new Locale("zh", "CN", "")});
      REPLACE_HANDLING_MAP.put("zh-Hant", new Locale[]{new Locale("zh", "TW", "")});
   }
}
