package org.python.icu.util;

import java.io.ObjectStreamException;
import java.lang.ref.SoftReference;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import org.python.icu.impl.CacheBase;
import org.python.icu.impl.ICUCache;
import org.python.icu.impl.ICUDebug;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SimpleCache;
import org.python.icu.impl.SoftCache;
import org.python.icu.impl.TextTrieMap;
import org.python.icu.text.CurrencyDisplayNames;
import org.python.icu.text.CurrencyMetaInfo;

public class Currency extends MeasureUnit {
   private static final long serialVersionUID = -5839973855554750484L;
   private static final boolean DEBUG = ICUDebug.enabled("currency");
   private static ICUCache CURRENCY_NAME_CACHE = new SimpleCache();
   public static final int SYMBOL_NAME = 0;
   public static final int LONG_NAME = 1;
   public static final int PLURAL_LONG_NAME = 2;
   private static final EquivalenceRelation EQUIVALENT_CURRENCY_SYMBOLS = (new EquivalenceRelation()).add("¥", "￥").add("$", "﹩", "＄").add("₨", "₹").add("£", "₤");
   private static ServiceShim shim;
   private static final String EUR_STR = "EUR";
   private static final CacheBase regionCurrencyCache = new SoftCache() {
      protected Currency createInstance(String key, Void unused) {
         return Currency.loadCurrency(key);
      }
   };
   private static final ULocale UND = new ULocale("und");
   private static final String[] EMPTY_STRING_ARRAY = new String[0];
   private static final int[] POW10 = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
   private static SoftReference ALL_TENDER_CODES;
   private static SoftReference ALL_CODES_AS_SET;
   private final String isoCode;

   private static ServiceShim getShim() {
      if (shim == null) {
         try {
            Class cls = Class.forName("org.python.icu.util.CurrencyServiceShim");
            shim = (ServiceShim)cls.newInstance();
         } catch (Exception var1) {
            if (DEBUG) {
               var1.printStackTrace();
            }

            throw new RuntimeException(var1.getMessage());
         }
      }

      return shim;
   }

   public static Currency getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   public static Currency getInstance(ULocale locale) {
      String currency = locale.getKeywordValue("currency");
      if (currency != null) {
         return getInstance(currency);
      } else {
         return shim == null ? createCurrency(locale) : shim.createInstance(locale);
      }
   }

   public static String[] getAvailableCurrencyCodes(ULocale loc, Date d) {
      String region = ULocale.getRegionForSupplementalData(loc, false);
      CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.onDate(d).withRegion(region);
      List list = getTenderCurrencies(filter);
      return list.isEmpty() ? null : (String[])list.toArray(new String[list.size()]);
   }

   public static String[] getAvailableCurrencyCodes(Locale loc, Date d) {
      return getAvailableCurrencyCodes(ULocale.forLocale(loc), d);
   }

   public static Set getAvailableCurrencies() {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      List list = info.currencies(CurrencyMetaInfo.CurrencyFilter.all());
      HashSet resultSet = new HashSet(list.size());
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         String code = (String)var3.next();
         resultSet.add(getInstance(code));
      }

      return resultSet;
   }

   static Currency createCurrency(ULocale loc) {
      String variant = loc.getVariant();
      if ("EURO".equals(variant)) {
         return getInstance("EUR");
      } else {
         String key = ULocale.getRegionForSupplementalData(loc, false);
         if ("PREEURO".equals(variant)) {
            key = key + '-';
         }

         return (Currency)regionCurrencyCache.getInstance(key, (Object)null);
      }
   }

   private static Currency loadCurrency(String key) {
      String region;
      boolean isPreEuro;
      if (key.endsWith("-")) {
         region = key.substring(0, key.length() - 1);
         isPreEuro = true;
      } else {
         region = key;
         isPreEuro = false;
      }

      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      List list = info.currencies(CurrencyMetaInfo.CurrencyFilter.onRegion(region));
      if (!list.isEmpty()) {
         String code = (String)list.get(0);
         if (isPreEuro && "EUR".equals(code)) {
            if (list.size() < 2) {
               return null;
            }

            code = (String)list.get(1);
         }

         return getInstance(code);
      } else {
         return null;
      }
   }

   public static Currency getInstance(String theISOCode) {
      if (theISOCode == null) {
         throw new NullPointerException("The input currency code is null.");
      } else if (!isAlpha3Code(theISOCode)) {
         throw new IllegalArgumentException("The input currency code is not 3-letter alphabetic code.");
      } else {
         return (Currency)MeasureUnit.internalGetInstance("currency", theISOCode.toUpperCase(Locale.ENGLISH));
      }
   }

   private static boolean isAlpha3Code(String code) {
      if (code.length() != 3) {
         return false;
      } else {
         for(int i = 0; i < 3; ++i) {
            char ch = code.charAt(i);
            if (ch < 'A' || ch > 'Z' && ch < 'a' || ch > 'z') {
               return false;
            }
         }

         return true;
      }
   }

   public static Object registerInstance(Currency currency, ULocale locale) {
      return getShim().registerInstance(currency, locale);
   }

   public static boolean unregister(Object registryKey) {
      if (registryKey == null) {
         throw new IllegalArgumentException("registryKey must not be null");
      } else {
         return shim == null ? false : shim.unregister(registryKey);
      }
   }

   public static Locale[] getAvailableLocales() {
      return shim == null ? ICUResourceBundle.getAvailableLocales() : shim.getAvailableLocales();
   }

   public static ULocale[] getAvailableULocales() {
      return shim == null ? ICUResourceBundle.getAvailableULocales() : shim.getAvailableULocales();
   }

   public static final String[] getKeywordValuesForLocale(String key, ULocale locale, boolean commonlyUsed) {
      if (!"currency".equals(key)) {
         return EMPTY_STRING_ARRAY;
      } else if (!commonlyUsed) {
         return (String[])getAllTenderCurrencies().toArray(new String[0]);
      } else if (UND.equals(locale)) {
         return EMPTY_STRING_ARRAY;
      } else {
         String prefRegion = ULocale.getRegionForSupplementalData(locale, true);
         CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.now().withRegion(prefRegion);
         List result = getTenderCurrencies(filter);
         return result.size() == 0 ? EMPTY_STRING_ARRAY : (String[])result.toArray(new String[result.size()]);
      }
   }

   public String getCurrencyCode() {
      return this.subType;
   }

   public int getNumericCode() {
      int result = 0;

      try {
         UResourceBundle bundle = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
         UResourceBundle codeMap = bundle.get("codeMap");
         UResourceBundle numCode = codeMap.get(this.subType);
         result = numCode.getInt();
      } catch (MissingResourceException var5) {
      }

      return result;
   }

   public String getSymbol() {
      return this.getSymbol(ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public String getSymbol(Locale loc) {
      return this.getSymbol(ULocale.forLocale(loc));
   }

   public String getSymbol(ULocale uloc) {
      return this.getName((ULocale)uloc, 0, new boolean[1]);
   }

   public String getName(Locale locale, int nameStyle, boolean[] isChoiceFormat) {
      return this.getName(ULocale.forLocale(locale), nameStyle, isChoiceFormat);
   }

   public String getName(ULocale locale, int nameStyle, boolean[] isChoiceFormat) {
      if (nameStyle != 0 && nameStyle != 1) {
         throw new IllegalArgumentException("bad name style: " + nameStyle);
      } else {
         if (isChoiceFormat != null) {
            isChoiceFormat[0] = false;
         }

         CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
         return nameStyle == 0 ? names.getSymbol(this.subType) : names.getName(this.subType);
      }
   }

   public String getName(Locale locale, int nameStyle, String pluralCount, boolean[] isChoiceFormat) {
      return this.getName(ULocale.forLocale(locale), nameStyle, pluralCount, isChoiceFormat);
   }

   public String getName(ULocale locale, int nameStyle, String pluralCount, boolean[] isChoiceFormat) {
      if (nameStyle != 2) {
         return this.getName(locale, nameStyle, isChoiceFormat);
      } else {
         if (isChoiceFormat != null) {
            isChoiceFormat[0] = false;
         }

         CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
         return names.getPluralName(this.subType, pluralCount);
      }
   }

   public String getDisplayName() {
      return this.getName((Locale)Locale.getDefault(), 1, (boolean[])null);
   }

   public String getDisplayName(Locale locale) {
      return this.getName((Locale)locale, 1, (boolean[])null);
   }

   /** @deprecated */
   @Deprecated
   public static String parse(ULocale locale, String text, int type, ParsePosition pos) {
      List currencyTrieVec = getCurrencyTrieVec(locale);
      int maxLength = false;
      String isoResult = null;
      TextTrieMap currencyNameTrie = (TextTrieMap)currencyTrieVec.get(1);
      CurrencyNameResultHandler handler = new CurrencyNameResultHandler();
      currencyNameTrie.find(text, pos.getIndex(), handler);
      isoResult = handler.getBestCurrencyISOCode();
      int maxLength = handler.getBestMatchLength();
      if (type != 1) {
         TextTrieMap currencySymbolTrie = (TextTrieMap)currencyTrieVec.get(0);
         handler = new CurrencyNameResultHandler();
         currencySymbolTrie.find(text, pos.getIndex(), handler);
         if (handler.getBestMatchLength() > maxLength) {
            isoResult = handler.getBestCurrencyISOCode();
            maxLength = handler.getBestMatchLength();
         }
      }

      int start = pos.getIndex();
      pos.setIndex(start + maxLength);
      return isoResult;
   }

   /** @deprecated */
   @Deprecated
   public static TextTrieMap.ParseState openParseState(ULocale locale, int startingCp, int type) {
      List currencyTrieVec = getCurrencyTrieVec(locale);
      return type == 1 ? ((TextTrieMap)currencyTrieVec.get(0)).openParseState(startingCp) : ((TextTrieMap)currencyTrieVec.get(1)).openParseState(startingCp);
   }

   private static List getCurrencyTrieVec(ULocale locale) {
      List currencyTrieVec = (List)CURRENCY_NAME_CACHE.get(locale);
      if (currencyTrieVec == null) {
         TextTrieMap currencyNameTrie = new TextTrieMap(true);
         TextTrieMap currencySymbolTrie = new TextTrieMap(false);
         currencyTrieVec = new ArrayList();
         ((List)currencyTrieVec).add(currencySymbolTrie);
         ((List)currencyTrieVec).add(currencyNameTrie);
         setupCurrencyTrieVec(locale, (List)currencyTrieVec);
         CURRENCY_NAME_CACHE.put(locale, currencyTrieVec);
      }

      return (List)currencyTrieVec;
   }

   private static void setupCurrencyTrieVec(ULocale locale, List trieVec) {
      TextTrieMap symTrie = (TextTrieMap)trieVec.get(0);
      TextTrieMap trie = (TextTrieMap)trieVec.get(1);
      CurrencyDisplayNames names = CurrencyDisplayNames.getInstance(locale);
      Iterator var5 = names.symbolMap().entrySet().iterator();

      Map.Entry e;
      String name;
      String isoCode;
      while(var5.hasNext()) {
         e = (Map.Entry)var5.next();
         name = (String)e.getKey();
         isoCode = (String)e.getValue();
         Iterator var9 = EQUIVALENT_CURRENCY_SYMBOLS.get(name).iterator();

         while(var9.hasNext()) {
            String equivalentSymbol = (String)var9.next();
            symTrie.put(equivalentSymbol, new CurrencyStringInfo(isoCode, name));
         }
      }

      var5 = names.nameMap().entrySet().iterator();

      while(var5.hasNext()) {
         e = (Map.Entry)var5.next();
         name = (String)e.getKey();
         isoCode = (String)e.getValue();
         trie.put(name, new CurrencyStringInfo(isoCode, name));
      }

   }

   public int getDefaultFractionDigits() {
      return this.getDefaultFractionDigits(Currency.CurrencyUsage.STANDARD);
   }

   public int getDefaultFractionDigits(CurrencyUsage Usage) {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      CurrencyMetaInfo.CurrencyDigits digits = info.currencyDigits(this.subType, Usage);
      return digits.fractionDigits;
   }

   public double getRoundingIncrement() {
      return this.getRoundingIncrement(Currency.CurrencyUsage.STANDARD);
   }

   public double getRoundingIncrement(CurrencyUsage Usage) {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      CurrencyMetaInfo.CurrencyDigits digits = info.currencyDigits(this.subType, Usage);
      int data1 = digits.roundingIncrement;
      if (data1 == 0) {
         return 0.0;
      } else {
         int data0 = digits.fractionDigits;
         return data0 >= 0 && data0 < POW10.length ? (double)data1 / (double)POW10[data0] : 0.0;
      }
   }

   public String toString() {
      return this.subType;
   }

   protected Currency(String theISOCode) {
      super("currency", theISOCode);
      this.isoCode = theISOCode;
   }

   private static synchronized List getAllTenderCurrencies() {
      List all = ALL_TENDER_CODES == null ? null : (List)ALL_TENDER_CODES.get();
      if (all == null) {
         CurrencyMetaInfo.CurrencyFilter filter = CurrencyMetaInfo.CurrencyFilter.all();
         all = Collections.unmodifiableList(getTenderCurrencies(filter));
         ALL_TENDER_CODES = new SoftReference(all);
      }

      return all;
   }

   private static synchronized Set getAllCurrenciesAsSet() {
      Set all = ALL_CODES_AS_SET == null ? null : (Set)ALL_CODES_AS_SET.get();
      if (all == null) {
         CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
         all = Collections.unmodifiableSet(new HashSet(info.currencies(CurrencyMetaInfo.CurrencyFilter.all())));
         ALL_CODES_AS_SET = new SoftReference(all);
      }

      return all;
   }

   public static boolean isAvailable(String code, Date from, Date to) {
      if (!isAlpha3Code(code)) {
         return false;
      } else if (from != null && to != null && from.after(to)) {
         throw new IllegalArgumentException("To is before from");
      } else {
         code = code.toUpperCase(Locale.ENGLISH);
         boolean isKnown = getAllCurrenciesAsSet().contains(code);
         if (!isKnown) {
            return false;
         } else if (from == null && to == null) {
            return true;
         } else {
            CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
            List allActive = info.currencies(CurrencyMetaInfo.CurrencyFilter.onDateRange(from, to).withCurrency(code));
            return allActive.contains(code);
         }
      }
   }

   private static List getTenderCurrencies(CurrencyMetaInfo.CurrencyFilter filter) {
      CurrencyMetaInfo info = CurrencyMetaInfo.getInstance();
      return info.currencies(filter.withTender());
   }

   private Object writeReplace() throws ObjectStreamException {
      return new MeasureUnit.MeasureUnitProxy(this.type, this.subType);
   }

   private Object readResolve() throws ObjectStreamException {
      return getInstance(this.isoCode);
   }

   private static final class EquivalenceRelation {
      private Map data;

      private EquivalenceRelation() {
         this.data = new HashMap();
      }

      public EquivalenceRelation add(Object... items) {
         Set group = new HashSet();
         Object[] var3 = items;
         int var4 = items.length;

         int var5;
         Object item;
         for(var5 = 0; var5 < var4; ++var5) {
            item = var3[var5];
            if (this.data.containsKey(item)) {
               throw new IllegalArgumentException("All groups passed to add must be disjoint.");
            }

            group.add(item);
         }

         var3 = items;
         var4 = items.length;

         for(var5 = 0; var5 < var4; ++var5) {
            item = var3[var5];
            this.data.put(item, group);
         }

         return this;
      }

      public Set get(Object item) {
         Set result = (Set)this.data.get(item);
         return result == null ? Collections.singleton(item) : Collections.unmodifiableSet(result);
      }

      // $FF: synthetic method
      EquivalenceRelation(Object x0) {
         this();
      }
   }

   private static class CurrencyNameResultHandler implements TextTrieMap.ResultHandler {
      private int bestMatchLength;
      private String bestCurrencyISOCode;

      private CurrencyNameResultHandler() {
      }

      public boolean handlePrefixMatch(int matchLength, Iterator values) {
         if (values.hasNext()) {
            this.bestCurrencyISOCode = ((CurrencyStringInfo)values.next()).getISOCode();
            this.bestMatchLength = matchLength;
         }

         return true;
      }

      public String getBestCurrencyISOCode() {
         return this.bestCurrencyISOCode;
      }

      public int getBestMatchLength() {
         return this.bestMatchLength;
      }

      // $FF: synthetic method
      CurrencyNameResultHandler(Object x0) {
         this();
      }
   }

   /** @deprecated */
   @Deprecated
   public static final class CurrencyStringInfo {
      private String isoCode;
      private String currencyString;

      /** @deprecated */
      @Deprecated
      public CurrencyStringInfo(String isoCode, String currencyString) {
         this.isoCode = isoCode;
         this.currencyString = currencyString;
      }

      /** @deprecated */
      @Deprecated
      public String getISOCode() {
         return this.isoCode;
      }

      /** @deprecated */
      @Deprecated
      public String getCurrencyString() {
         return this.currencyString;
      }
   }

   abstract static class ServiceShim {
      abstract ULocale[] getAvailableULocales();

      abstract Locale[] getAvailableLocales();

      abstract Currency createInstance(ULocale var1);

      abstract Object registerInstance(Currency var1, ULocale var2);

      abstract boolean unregister(Object var1);
   }

   public static enum CurrencyUsage {
      STANDARD,
      CASH;
   }
}
