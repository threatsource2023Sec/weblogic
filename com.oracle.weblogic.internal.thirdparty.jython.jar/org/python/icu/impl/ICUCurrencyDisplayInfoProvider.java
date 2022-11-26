package org.python.icu.impl;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public class ICUCurrencyDisplayInfoProvider implements CurrencyData.CurrencyDisplayInfoProvider {
   public CurrencyData.CurrencyDisplayInfo getInstance(ULocale locale, boolean withFallback) {
      ICUResourceBundle rb;
      if (withFallback) {
         rb = ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/curr", locale, ICUResourceBundle.OpenType.LOCALE_DEFAULT_ROOT);
      } else {
         try {
            rb = ICUResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/curr", locale, ICUResourceBundle.OpenType.LOCALE_ONLY);
         } catch (MissingResourceException var5) {
            return null;
         }
      }

      return new ICUCurrencyDisplayInfo(rb, withFallback);
   }

   public boolean hasData() {
      return true;
   }

   static class ICUCurrencyDisplayInfo extends CurrencyData.CurrencyDisplayInfo {
      private final boolean fallback;
      private final ICUResourceBundle rb;
      private final ICUResourceBundle currencies;
      private final ICUResourceBundle plurals;
      private SoftReference _symbolMapRef;
      private SoftReference _nameMapRef;

      public ICUCurrencyDisplayInfo(ICUResourceBundle rb, boolean fallback) {
         this.fallback = fallback;
         this.rb = rb;
         this.currencies = rb.findTopLevel("Currencies");
         this.plurals = rb.findTopLevel("CurrencyPlurals");
      }

      public ULocale getULocale() {
         return this.rb.getULocale();
      }

      public String getName(String isoCode) {
         return this.getName(isoCode, false);
      }

      public String getSymbol(String isoCode) {
         return this.getName(isoCode, true);
      }

      private String getName(String isoCode, boolean symbolName) {
         if (this.currencies != null) {
            ICUResourceBundle result = this.currencies.findWithFallback(isoCode);
            if (result != null) {
               if (!this.fallback && !this.rb.isRoot() && result.isRoot()) {
                  return null;
               }

               return result.getString(symbolName ? 0 : 1);
            }
         }

         return this.fallback ? isoCode : null;
      }

      public String getPluralName(String isoCode, String pluralKey) {
         if (this.plurals != null) {
            ICUResourceBundle pluralsBundle = this.plurals.findWithFallback(isoCode);
            if (pluralsBundle != null) {
               String pluralName = pluralsBundle.findStringWithFallback(pluralKey);
               if (pluralName == null) {
                  if (!this.fallback) {
                     return null;
                  }

                  pluralName = pluralsBundle.findStringWithFallback("other");
                  if (pluralName == null) {
                     return this.getName(isoCode);
                  }
               }

               return pluralName;
            }
         }

         return this.fallback ? this.getName(isoCode) : null;
      }

      public Map symbolMap() {
         Map map = this._symbolMapRef == null ? null : (Map)this._symbolMapRef.get();
         if (map == null) {
            map = this._createSymbolMap();
            this._symbolMapRef = new SoftReference(map);
         }

         return map;
      }

      public Map nameMap() {
         Map map = this._nameMapRef == null ? null : (Map)this._nameMapRef.get();
         if (map == null) {
            map = this._createNameMap();
            this._nameMapRef = new SoftReference(map);
         }

         return map;
      }

      public Map getUnitPatterns() {
         Map result = new HashMap();

         for(ULocale locale = this.rb.getULocale(); locale != null; locale = locale.getFallback()) {
            ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/curr", locale);
            if (r != null) {
               ICUResourceBundle cr = r.findWithFallback("CurrencyUnitPatterns");
               if (cr != null) {
                  int index = 0;

                  for(int size = cr.getSize(); index < size; ++index) {
                     ICUResourceBundle b = (ICUResourceBundle)cr.get(index);
                     String key = b.getKey();
                     if (!result.containsKey(key)) {
                        result.put(key, b.getString());
                     }
                  }
               }
            }
         }

         return Collections.unmodifiableMap(result);
      }

      public CurrencyData.CurrencyFormatInfo getFormatInfo(String isoCode) {
         ICUResourceBundle crb = this.currencies.findWithFallback(isoCode);
         if (crb != null && crb.getSize() > 2) {
            crb = crb.at(2);
            if (crb != null) {
               String pattern = crb.getString(0);
               String separator = crb.getString(1);
               String groupingSeparator = crb.getString(2);
               return new CurrencyData.CurrencyFormatInfo(pattern, separator, groupingSeparator);
            }
         }

         return null;
      }

      public CurrencyData.CurrencySpacingInfo getSpacingInfo() {
         SpacingInfoSink sink = new SpacingInfoSink();
         this.rb.getAllItemsWithFallback("currencySpacing", sink);
         return sink.getSpacingInfo(this.fallback);
      }

      private Map _createSymbolMap() {
         Map result = new HashMap();

         for(ULocale locale = this.rb.getULocale(); locale != null; locale = locale.getFallback()) {
            ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/curr", locale);
            ICUResourceBundle curr = bundle.findTopLevel("Currencies");
            if (curr != null) {
               for(int i = 0; i < curr.getSize(); ++i) {
                  ICUResourceBundle item = curr.at(i);
                  String isoCode = item.getKey();
                  if (!result.containsKey(isoCode)) {
                     result.put(isoCode, isoCode);
                     String symbol = item.getString(0);
                     result.put(symbol, isoCode);
                  }
               }
            }
         }

         return Collections.unmodifiableMap(result);
      }

      private Map _createNameMap() {
         Map result = new TreeMap(String.CASE_INSENSITIVE_ORDER);
         Set visited = new HashSet();
         Map visitedPlurals = new HashMap();

         for(ULocale locale = this.rb.getULocale(); locale != null; locale = locale.getFallback()) {
            ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/curr", locale);
            ICUResourceBundle curr = bundle.findTopLevel("Currencies");
            String isoCode;
            if (curr != null) {
               for(int i = 0; i < curr.getSize(); ++i) {
                  ICUResourceBundle item = curr.at(i);
                  String isoCode = item.getKey();
                  if (!visited.contains(isoCode)) {
                     visited.add(isoCode);
                     isoCode = item.getString(1);
                     result.put(isoCode, isoCode);
                  }
               }
            }

            ICUResourceBundle plurals = bundle.findTopLevel("CurrencyPlurals");
            if (plurals != null) {
               for(int i = 0; i < plurals.getSize(); ++i) {
                  ICUResourceBundle item = plurals.at(i);
                  isoCode = item.getKey();
                  Set pluralSet = (Set)visitedPlurals.get(isoCode);
                  if (pluralSet == null) {
                     pluralSet = new HashSet();
                     visitedPlurals.put(isoCode, pluralSet);
                  }

                  for(int j = 0; j < item.getSize(); ++j) {
                     ICUResourceBundle plural = item.at(j);
                     String pluralType = plural.getKey();
                     if (!((Set)pluralSet).contains(pluralType)) {
                        String pluralName = plural.getString();
                        result.put(pluralName, isoCode);
                        ((Set)pluralSet).add(pluralType);
                     }
                  }
               }
            }
         }

         return Collections.unmodifiableMap(result);
      }

      private final class SpacingInfoSink extends UResource.Sink {
         CurrencyData.CurrencySpacingInfo spacingInfo;
         boolean hasBeforeCurrency;
         boolean hasAfterCurrency;

         private SpacingInfoSink() {
            this.spacingInfo = new CurrencyData.CurrencySpacingInfo();
            this.hasBeforeCurrency = false;
            this.hasAfterCurrency = false;
         }

         public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
            UResource.Table spacingTypesTable = value.getTable();

            for(int i = 0; spacingTypesTable.getKeyAndValue(i, key, value); ++i) {
               CurrencyData.CurrencySpacingInfo.SpacingType type;
               if (key.contentEquals("beforeCurrency")) {
                  type = CurrencyData.CurrencySpacingInfo.SpacingType.BEFORE;
                  this.hasBeforeCurrency = true;
               } else {
                  if (!key.contentEquals("afterCurrency")) {
                     continue;
                  }

                  type = CurrencyData.CurrencySpacingInfo.SpacingType.AFTER;
                  this.hasAfterCurrency = true;
               }

               UResource.Table patternsTable = value.getTable();

               for(int j = 0; patternsTable.getKeyAndValue(j, key, value); ++j) {
                  CurrencyData.CurrencySpacingInfo.SpacingPattern pattern;
                  if (key.contentEquals("currencyMatch")) {
                     pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.CURRENCY_MATCH;
                  } else if (key.contentEquals("surroundingMatch")) {
                     pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.SURROUNDING_MATCH;
                  } else {
                     if (!key.contentEquals("insertBetween")) {
                        continue;
                     }

                     pattern = CurrencyData.CurrencySpacingInfo.SpacingPattern.INSERT_BETWEEN;
                  }

                  this.spacingInfo.setSymbolIfNull(type, pattern, value.getString());
               }
            }

         }

         CurrencyData.CurrencySpacingInfo getSpacingInfo(boolean fallback) {
            if (this.hasBeforeCurrency && this.hasAfterCurrency) {
               return this.spacingInfo;
            } else {
               return fallback ? CurrencyData.CurrencySpacingInfo.DEFAULT : null;
            }
         }

         // $FF: synthetic method
         SpacingInfoSink(Object x1) {
            this();
         }
      }
   }
}
