package org.python.icu.impl;

import java.util.Collections;
import java.util.Map;
import org.python.icu.text.CurrencyDisplayNames;
import org.python.icu.util.ULocale;

public class CurrencyData {
   public static final CurrencyDisplayInfoProvider provider;

   private CurrencyData() {
   }

   static {
      CurrencyDisplayInfoProvider temp = null;

      try {
         Class clzz = Class.forName("org.python.icu.impl.ICUCurrencyDisplayInfoProvider");
         temp = (CurrencyDisplayInfoProvider)clzz.newInstance();
      } catch (Throwable var2) {
         temp = new CurrencyDisplayInfoProvider() {
            public CurrencyDisplayInfo getInstance(ULocale locale, boolean withFallback) {
               return CurrencyData.DefaultInfo.getWithFallback(withFallback);
            }

            public boolean hasData() {
               return false;
            }
         };
      }

      provider = temp;
   }

   public static class DefaultInfo extends CurrencyDisplayInfo {
      private final boolean fallback;
      private static final CurrencyDisplayInfo FALLBACK_INSTANCE = new DefaultInfo(true);
      private static final CurrencyDisplayInfo NO_FALLBACK_INSTANCE = new DefaultInfo(false);

      private DefaultInfo(boolean fallback) {
         this.fallback = fallback;
      }

      public static final CurrencyDisplayInfo getWithFallback(boolean fallback) {
         return fallback ? FALLBACK_INSTANCE : NO_FALLBACK_INSTANCE;
      }

      public String getName(String isoCode) {
         return this.fallback ? isoCode : null;
      }

      public String getPluralName(String isoCode, String pluralType) {
         return this.fallback ? isoCode : null;
      }

      public String getSymbol(String isoCode) {
         return this.fallback ? isoCode : null;
      }

      public Map symbolMap() {
         return Collections.emptyMap();
      }

      public Map nameMap() {
         return Collections.emptyMap();
      }

      public ULocale getULocale() {
         return ULocale.ROOT;
      }

      public Map getUnitPatterns() {
         return this.fallback ? Collections.emptyMap() : null;
      }

      public CurrencyFormatInfo getFormatInfo(String isoCode) {
         return null;
      }

      public CurrencySpacingInfo getSpacingInfo() {
         return this.fallback ? CurrencyData.CurrencySpacingInfo.DEFAULT : null;
      }
   }

   public static final class CurrencySpacingInfo {
      private final String[][] symbols;
      private static final String DEFAULT_CUR_MATCH = "[:letter:]";
      private static final String DEFAULT_CTX_MATCH = "[:digit:]";
      private static final String DEFAULT_INSERT = " ";
      public static final CurrencySpacingInfo DEFAULT = new CurrencySpacingInfo(new String[]{"[:letter:]", "[:digit:]", " ", "[:letter:]", "[:digit:]", " "});

      public CurrencySpacingInfo() {
         this.symbols = new String[CurrencyData.CurrencySpacingInfo.SpacingType.COUNT.ordinal()][CurrencyData.CurrencySpacingInfo.SpacingPattern.COUNT.ordinal()];
      }

      public CurrencySpacingInfo(String... strings) {
         this.symbols = new String[CurrencyData.CurrencySpacingInfo.SpacingType.COUNT.ordinal()][CurrencyData.CurrencySpacingInfo.SpacingPattern.COUNT.ordinal()];

         assert strings.length == 6;

         int k = 0;

         for(int i = 0; i < CurrencyData.CurrencySpacingInfo.SpacingType.COUNT.ordinal(); ++i) {
            for(int j = 0; j < CurrencyData.CurrencySpacingInfo.SpacingPattern.COUNT.ordinal(); ++j) {
               this.symbols[i][j] = strings[k];
               ++k;
            }
         }

      }

      public void setSymbolIfNull(SpacingType type, SpacingPattern pattern, String value) {
         int i = type.ordinal();
         int j = pattern.ordinal();
         if (this.symbols[i][j] == null) {
            this.symbols[i][j] = value;
         }

      }

      public String[] getBeforeSymbols() {
         return this.symbols[CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE.ordinal()];
      }

      public String[] getAfterSymbols() {
         return this.symbols[CurrencyData.CurrencySpacingInfo.SpacingType.AFTER.ordinal()];
      }

      public static enum SpacingPattern {
         CURRENCY_MATCH(0),
         SURROUNDING_MATCH(1),
         INSERT_BETWEEN(2),
         COUNT;

         private SpacingPattern() {
         }

         private SpacingPattern(int value) {
            assert value == this.ordinal();

         }
      }

      public static enum SpacingType {
         BEFORE,
         AFTER,
         COUNT;
      }
   }

   public static final class CurrencyFormatInfo {
      public final String currencyPattern;
      public final String monetarySeparator;
      public final String monetaryGroupingSeparator;

      public CurrencyFormatInfo(String currencyPattern, String monetarySeparator, String monetaryGroupingSeparator) {
         this.currencyPattern = currencyPattern;
         this.monetarySeparator = monetarySeparator;
         this.monetaryGroupingSeparator = monetaryGroupingSeparator;
      }
   }

   public abstract static class CurrencyDisplayInfo extends CurrencyDisplayNames {
      public abstract Map getUnitPatterns();

      public abstract CurrencyFormatInfo getFormatInfo(String var1);

      public abstract CurrencySpacingInfo getSpacingInfo();
   }

   public interface CurrencyDisplayInfoProvider {
      CurrencyDisplayInfo getInstance(ULocale var1, boolean var2);

      boolean hasData();
   }
}
